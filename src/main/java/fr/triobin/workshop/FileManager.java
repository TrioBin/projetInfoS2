package fr.triobin.workshop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fr.triobin.workshop.general.Workshop;
import fr.triobin.workshop.general.Workstation;
import fr.triobin.workshop.general.RefMachine;
import fr.triobin.workshop.general.SpecializedGoal;
import fr.triobin.workshop.general.Cost;
import fr.triobin.workshop.general.Machine;
import fr.triobin.workshop.general.NonFinishedProduct;
import fr.triobin.workshop.general.OPList;
import fr.triobin.workshop.general.Machine.MachineStatus;
import fr.triobin.workshop.general.Operation;
import fr.triobin.workshop.general.Position;
import fr.triobin.workshop.general.Operator;
import fr.triobin.workshop.general.Operator.OperatorStatus;
import javafx.geometry.Dimension2D;
import fr.triobin.workshop.general.Product;
import fr.triobin.workshop.general.GeneralGoal;
import fr.triobin.workshop.general.Goal;

public class FileManager {
    static final String[] loadOrder = { "workshop", "refmachine", "operation", "machine", "workstation", "operator",
            "product", "nonfinishedproduct", "goal" };
    public static final String separation = "__________";
    public static final String separator = ",";
    public static final String listSeparator = ";";

    public static Boolean readStatus = false;

    public static ArrayList<Workshop> loadFile() {
        readStatus = true;
        // Load the file
        ArrayList<NonFinishedProduct> nonFinishedProducts = new ArrayList<>();
        int[] lineIndex = { 0 };
        ArrayList<Workshop> workshops = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader("workshops.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                readLine(line, lineIndex, workshops, nonFinishedProducts);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workshops;
    }

    public static void readLine(String line, int[] lineIndex, ArrayList<Workshop> workshops,
            ArrayList<NonFinishedProduct> nonFinishedProducts) {
        if (line.equals(separation)) {
            lineIndex[0]++;
            if (lineIndex[0] >= loadOrder.length) {
                lineIndex[0] = 0;
            }
            return;
        }
        String[] parts = line.split(separator);
        switch (loadOrder[lineIndex[0]]) {
            case "workshop":
                workshops.add(new Workshop(parts[0], Float.parseFloat(parts[1])));
                break;
            case "refmachine":
                workshops.get(workshops.size() - 1).addMachineRef(new RefMachine(parts[0]));
                break;
            case "operation":
                workshops.get(workshops.size() - 1)
                        .addOperation(new Operation(parts[0], Time.valueOf(parts[1])));
                break;
            case "machine":
                RefMachine refMachine = workshops.get(workshops.size() - 1).getMachineRef(parts[0]);
                ArrayList<Operation> opList = new ArrayList<>();
                parts[7] = parts[7].replace("[", "").replace("]", "");
                String[] operations = parts[7].split(listSeparator);
                for (String operation : operations) {
                    if (operation != "") {
                        opList.add(workshops.get(workshops.size() - 1).getOperation(operation));
                    }
                }
                workshops.get(workshops.size() - 1).add(new Machine(refMachine, parts[1],
                        new Position(Float.parseFloat(parts[2]), Float.parseFloat(parts[3])),
                        new Dimension2D(Float.parseFloat(parts[4]), Float.parseFloat(parts[5])),
                        new Cost(Float.parseFloat(parts[6])), opList,
                        MachineStatus.valueOf(parts[8])));
                break;
            case "workstation":
                ArrayList<Machine> machines = new ArrayList<>();
                parts[6] = parts[6].replace("[", "").replace("]", "");
                String[] machinesList = parts[6].split(listSeparator);
                for (String machine : machinesList) {
                    if (machine != "") {
                        machines.add(workshops.get(workshops.size() - 1).getMachine(machine));
                    }
                }
                workshops.get(workshops.size() - 1).add(new Workstation(parts[0], parts[1],
                        new Position(Float.parseFloat(parts[2]), Float.parseFloat(parts[3])),
                        new Dimension2D(Float.parseFloat(parts[4]), Float.parseFloat(parts[5])), machines));
                break;
            case "operator":
                ArrayList<RefMachine> skillsList = new ArrayList<>();
                parts[3] = parts[3].replace("[", "").replace("]", "");
                String[] skills = parts[3].split(listSeparator);
                for (String skill : skills) {
                    if (skill != "") {
                        skillsList.add(workshops.get(workshops.size() - 1).getMachineRef(skill));
                    }
                }
                workshops.get(workshops.size() - 1).add(new Operator(parts[0], parts[1], parts[2],
                        skillsList, OperatorStatus.valueOf(parts[4]), parts[5], new Cost(Float.parseFloat(parts[6]))));
                break;
            case "product":
                OPList opList2 = new OPList(new ArrayList<>());
                parts[2] = parts[2].replace("[", "").replace("]", "");
                String[] operations2 = parts[2].split(listSeparator);
                for (String operation : operations2) {
                    if (operation != "") {
                        opList2.addOperation(workshops.get(workshops.size() - 1).getOperation(operation));
                    }
                }
                workshops.get(workshops.size() - 1).add(new Product(parts[0], parts[1], opList2));
                break;
            case "nonfinishedproduct":
                Product product = workshops.get(workshops.size() - 1).getProduct(parts[0]);
                nonFinishedProducts.add(new NonFinishedProduct(product));
                break;
            case "goal":
                if ("Ggoal".equals(parts[0])) {
                    Product product2 = workshops.get(workshops.size() - 1).getProduct(parts[1]);
                    workshops.get(workshops.size() - 1).add(new GeneralGoal(product2,
                            Integer.parseInt(parts[2])));
                } else if ("Sgoal".equals(parts[0])) {
                    workshops.get(workshops.size() - 1).add(new SpecializedGoal(
                            workshops.get(workshops.size() - 1).getOperation(parts[1]),
                            nonFinishedProducts.get(Integer.parseInt(parts[2]))));
                }
                break;
        }
    }

    public static void saveFile(ArrayList<Workshop> workshops) {
        if (!readStatus) {
            return;
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("workshops.txt"))) {
            String text = "";
            for (Workshop workshop : workshops) {
                text += generateWorkshop(workshop);
            }
            if (text.length() > 2 + separation.length()) {
                text = text.substring(0, text.length() - 2 - separation.length());
            }
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String generateWorkshop(Workshop workshop) {
        String text = "";
        text += workshop.getDesignation() + separator + workshop.getBank() + "\n";
        text += separation + "\n";
        for (RefMachine refMachine : workshop.getMachinesRef()) {
            text += refMachine.getName() + "\n";
        }
        text += separation + "\n";
        for (Operation operation : workshop.getOperations()) {
            text += operation.getName() + separator + operation.getTime().toString() + "\n";
        }
        text += separation + "\n";
        for (Machine machine : workshop.getMachines()) {
            String operations = "[";
            for (Operation operation : machine.getOperations()) {
                if (operation != null) {
                    operations += operation.getName() + listSeparator;
                }
            }
            if (operations.length() > 1) {
                operations = operations.substring(0, operations.length() - 1);
            }
            operations += "]";
            text += machine.getRefMachine().getName() + separator + machine.getName() + separator
                    + machine.getPosition().x + separator + machine.getPosition().y + separator
                    + machine.getDimension().getWidth() + separator + machine.getDimension().getHeight()
                    + separator
                    + machine.getCost().getCost()
                    + separator + operations + separator + machine.getStatus() + "\n";
        }
        text += separation + "\n";
        for (Workstation workstation : workshop.getWorkstations()) {
            String machines = "[";
            for (Machine machine : workstation.getMachines()) {
                if (machine != null) {
                    machines += machine.getName() + listSeparator;
                }
            }
            if (machines.length() > 1) {
                machines = machines.substring(0, machines.length() - 1);
            }
            machines += "]";
            text += workstation.getRefWorkstation() + separator + workstation.getDworkstation() + separator
                    + workstation.getPosition().x + separator + workstation.getPosition().y
                    + separator + workstation.getDimension().getWidth() + separator
                    + workstation.getDimension().getHeight()
                    + separator + machines + "\n";
        }
        text += separation + "\n";
        for (Operator operator : workshop.getOperators()) {
            String skills = "[";
            for (RefMachine skill : operator.getSkills()) {
                if (skill != null) { // Add null check
                    skills += skill.getName() + listSeparator;
                }
            }
            if (skills.length() > 1) {
                skills = skills.substring(0, skills.length() - 1);
            }
            skills += "]";
            text += operator.getCode() + separator + operator.getName() + separator + operator.getSurname()
                    + separator
                    + skills + separator + operator.getStatus() + separator +
                    operator.getPassword() + separator + operator.getCost().getCost() +
                    "\n";
        }
        text += separation + "\n";
        for (Product product : workshop.getProducts()) {
            String operations = "[";
            for (Operation operation : product.getOperation()) {
                if (operation != null) {
                    operations += operation.getName() + listSeparator;
                }
            }
            if (operations.length() > 1) {
                operations = operations.substring(0, operations.length() - 1);
            }
            operations += "]";
            text += product.getId() + separator + product.getName() + separator + operations + "\n";
        }
        text += separation + "\n";

        String goalString = "";
        ArrayList<NonFinishedProduct> nonFinishedProducts = new ArrayList<>();
        ArrayList<Integer> nonFinishedProductsHash = new ArrayList<>();
        String nonFinishedProductString = "";

        for (Goal goal : workshop.getGoals()) {
            if (goal instanceof GeneralGoal) {
                GeneralGoal ggoal = (GeneralGoal) goal;
                goalString += "Ggoal" + separator + ggoal.getProduct().getId() + separator + ggoal.getQuantity()
                        + "\n";
            } else if (goal instanceof SpecializedGoal) {
                SpecializedGoal sgoal = (SpecializedGoal) goal;
                int elementIndex = -1;
                if (!nonFinishedProductsHash.contains(sgoal.getProduct().hashCode())) {
                    nonFinishedProducts.add(sgoal.getProduct());
                    nonFinishedProductsHash.add(sgoal.getProduct().hashCode());
                    elementIndex = nonFinishedProducts.size() - 1;
                } else {
                    elementIndex = nonFinishedProductsHash.indexOf(sgoal.getProduct().hashCode());
                }
                goalString += "Sgoal" + separator + sgoal.getOperation().getName() + separator
                        + elementIndex
                        + "\n";
            }
        }

        for (NonFinishedProduct nonFinishedProduct : nonFinishedProducts) {
            nonFinishedProductString += nonFinishedProduct.getProduct().getName() + "\n";
        }

        text += nonFinishedProductString;
        text += separation + "\n";
        text += goalString;
        text += separation + "\n";
        return text;
    }
}
