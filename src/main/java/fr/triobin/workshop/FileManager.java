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
import fr.triobin.workshop.general.OPList;
import fr.triobin.workshop.general.Machine.MachineStatus;
import fr.triobin.workshop.general.Operation;
import fr.triobin.workshop.general.Position;

public class FileManager {
    static final String[] loadOrder = { "workshop", "refmachine", "operation", "machine", "workstation", "operator" };
    static final String separation = "__________";
    static final String separator = ",";
    static final String listSeparator = ";";

    public static ArrayList<Workshop> loadFile() {
        // Load the file
        Integer lineIndex = 0;
        ArrayList<Workshop> workshops = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("workshops.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.equals(separation)) {
                    lineIndex++;
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
                        workshops.get(workshops.size() - 1).add(new Operator(parts[0], parts[1], parts[2],
                                new ArrayList<>(), OperatorStatus.valueOf(parts[3]), parts[4]));
                        break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return workshops;
    }

    public static void saveFile(ArrayList<Workshop> workshops) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("workshops.txt"))) {
            String json = "[\n";
            workshops.forEach(workshop -> {
                System.out.println(workshop);
            });

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
