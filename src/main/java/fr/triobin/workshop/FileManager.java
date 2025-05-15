package fr.triobin.workshop;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.sql.Time;
import java.util.ArrayList;

import fr.triobin.workshop.general.Workshop;
import fr.triobin.workshop.general.Workstation;
import fr.triobin.workshop.general.RefMachine;
import fr.triobin.workshop.general.Cost;
import fr.triobin.workshop.general.Machine;
import fr.triobin.workshop.general.NonFinishedProduct;
import fr.triobin.workshop.general.OPList;
import fr.triobin.workshop.general.Machine.MachineStatus;
import fr.triobin.workshop.general.Operation;
import fr.triobin.workshop.general.Position;
import fr.triobin.workshop.general.Operator;
import fr.triobin.workshop.general.Operator.OperatorStatus;
import fr.triobin.workshop.general.Product;
import fr.triobin.workshop.general.GeneralGoal;

public class FileManager {
    static final String[] loadOrder = { "workshop", "refmachine", "operation", "machine", "workstation", "operator",
            "product", "goal" };
    static final String separation = "__________";
    static final String separator = ",";
    static final String listSeparator = ";";

    public static ArrayList<Workshop> loadFile() {
        // Load the file
        ArrayList<NonFinishedProduct> nonFinishedProducts = new ArrayList<>();
        Integer lineIndex = 0;
        ArrayList<Workshop> workshops = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("workshops.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(separation)) {
                    lineIndex++;
                    if (lineIndex >= loadOrder.length) {
                        lineIndex = 0;
                    }
                    continue;
                }
                String[] parts = line.split(separator);
                switch (loadOrder[lineIndex]) {
                    case "workshop":
                        workshops.add(new Workshop(parts[0]));
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
                        parts[5] = parts[5].replace("[", "").replace("]", "");
                        String[] operations = parts[5].split(listSeparator);
                        for (String operation : operations) {
                            opList.add(workshops.get(workshops.size() - 1).getOperation(operation));
                        }
                        workshops.get(workshops.size() - 1).add(new Machine(refMachine, parts[1],
                                new Position(Float.parseFloat(parts[2]), Float.parseFloat(parts[3])),
                                new Cost(Float.parseFloat(parts[4])), opList,
                                MachineStatus.valueOf(parts[6])));
                        break;
                    case "workstation":
                        ArrayList<Machine> machines = new ArrayList<>();
                        parts[4] = parts[4].replace("[", "").replace("]", "");
                        String[] machinesList = parts[4].split(listSeparator);
                        for (String machine : machinesList) {
                            machines.add(workshops.get(workshops.size() - 1).getMachine(machine));
                        }
                        workshops.get(workshops.size() - 1).add(new Workstation(parts[0], parts[1],
                                new Position(Float.parseFloat(parts[2]), Float.parseFloat(parts[3])), machines));
                        break;
                    case "operator":
                        ArrayList<RefMachine> skillsList = new ArrayList<>();
                        parts[3] = parts[3].replace("[", "").replace("]", "");
                        String[] skills = parts[3].split(listSeparator);
                        for (String skill : skills) {
                            skillsList.add(workshops.get(workshops.size() - 1).getMachineRef(skill));
                        }
                        workshops.get(workshops.size() - 1).add(new Operator(parts[0], parts[1], parts[2],
                                skillsList, OperatorStatus.valueOf(parts[4]), parts[5]));
                        break;
                    case "product":
                        OPList opList2 = new OPList(new ArrayList<>());
                        parts[2] = parts[2].replace("[", "").replace("]", "");
                        String[] operations2 = parts[2].split(listSeparator);
                        for (String operation : operations2) {
                            opList2.addOperation(workshops.get(workshops.size() - 1).getOperation(operation));
                        }
                        workshops.get(workshops.size() - 1).add(new Product(parts[0], parts[1], opList2));
                        break;
                    case "goal":
                        if (parts[0] == "Ggoal") {
                            workshops.get(workshops.size() - 1).add(new GeneralGoal(
                                    workshops.get(workshops.size() - 1).getProduct(parts[1]),
                                    Integer.parseInt(parts[2])));
                        } else if (parts[0] == "Sgoal") {

                            workshops.get(workshops.size() - 1).add(new GeneralGoal(
                                    workshops.get(workshops.size() - 1).getProduct(parts[1]),
                                    Integer.parseInt(parts[2])));
                        }
                        break;
                }
            }
        }
    }catch(

    IOException e)
    {
        e.printStackTrace();
    }

    return workshops;
    }

    public static void saveFile(ArrayList<Workshop> workshops) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("workshops2.txt"))) {
            String text = "";
            for (Workshop workshop : workshops) {
                text += workshop.getDesignation() + "\n";
                text += separation + "\n";
                for (RefMachine refMachine : workshop.getMachinesRef()) {
                    text += refMachine.getName() + "\n";
                }
                text += separation + "\n";
                for (Operation operation : workshop.getOperations()) {
                    text += operation.getName() + "," + operation.getTime().toString() + "\n";
                }
                text += separation + "\n";
                for (Machine machine : workshop.getMachines()) {
                    String operations = "[";
                    for (Operation operation : machine.getOperations()) {
                        operations += operation.getName() + listSeparator;
                    }
                    if (operations.length() > 1) {
                        operations = operations.substring(0, operations.length() - 1);
                    }
                    operations += "]";
                    text += machine.getRefMachine().getName() + "," + machine.getName() + ","
                            + machine.getPosition().x + "," + machine.getPosition().y + ","
                            + machine.getCost().getCost()
                            + "," + operations + "," + machine.getStatus() + "\n";
                }
                text += separation + "\n";
                for (Workstation workstation : workshop.getWorkstations()) {
                    String machines = "[";
                    for (Machine machine : workstation.getMachines()) {
                        machines += machine.getName() + listSeparator;
                    }
                    if (machines.length() > 1) {
                        machines = machines.substring(0, machines.length() - 1);
                    }
                    machines += "]";
                    text += workstation.getRefWorkstation() + "," + workstation.getDworkstation() + ","
                            + workstation.getPosition().x + "," + workstation.getPosition().y
                            + "," + machines + "\n";
                }
                text += separation + "\n";
                for (Operator operator : workshop.getOperators()) {
                    String skills = "[";
                    for (RefMachine skill : operator.getSkills()) {
                        skills += skill.getName() + listSeparator;
                    }
                    if (skills.length() > 1) {
                        skills = skills.substring(0, skills.length() - 1);
                    }
                    skills += "]";
                    text += operator.getCode() + "," + operator.getName() + "," + operator.getSurname() + ","
                            + skills + "," + operator.getStatus() + "," +
                            operator.getPassword() +
                            "\n";
                }
                text += separation + "\n";
            }
            text = text.substring(0, text.length() - 2 - separation.length());
            System.out.println(text);
            writer.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
