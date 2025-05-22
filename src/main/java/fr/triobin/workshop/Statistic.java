package fr.triobin.workshop;

import java.io.FileWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;

import fr.triobin.workshop.general.BankEvent;
import fr.triobin.workshop.general.MachineEvent;
import fr.triobin.workshop.general.Operator;
import fr.triobin.workshop.general.RefMachine;

public class Statistic {
    public static String separator = ",";

    public static void addStatMachine(Timestamp timestamp, String machineName, RefMachine refMachine, String reason) {
        try {
            // use file writer to append the statistics to a file
            FileWriter fileWriter = new FileWriter(Memory.currentWorkshop.getDesignation() + "machineStatistics.txt",
                    true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy" + separator + "HH:mm");
            String toWrite = timestamp.toLocalDateTime().format(formatter);
            toWrite += separator + machineName + separator + refMachine.getName() + separator + reason;
            fileWriter.write(toWrite + "\n");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<MachineEvent> readMachineStatistics() {
        List<MachineEvent> events = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(Memory.currentWorkshop.getDesignation() + "machineStatistics.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(separator);
                if (parts.length == 5) {
                    String date = parts[0];
                    String time = parts[1];
                    String machineName = parts[2];
                    String refMachine = parts[3];
                    String reason = parts[4];
                    events.add(new MachineEvent(date, time, machineName, refMachine, reason));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }

    public static void addStatBank(Timestamp timestamp, Operator operator, String reason, Float cost) {
        try {
            // use file writer to append the statistics to a file
            FileWriter fileWriter = new FileWriter(Memory.currentWorkshop.getDesignation() + "bankStatistics.txt",
                    true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy" + separator + "HH:mm");
            String toWrite = timestamp.toLocalDateTime().format(formatter);
            if (operator == null) {
                toWrite += separator + "null";
            } else {
                toWrite += separator + operator.getName();
            }
            toWrite += separator + reason + separator + cost;
            fileWriter.write(toWrite + "\n");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<BankEvent> readBankStatistics() {
        List<BankEvent> events = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(
                    new FileReader(Memory.currentWorkshop.getDesignation() + "bankStatistics.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(separator);
                if (parts.length == 5) {
                    String date = parts[0];
                    String time = parts[1];
                    String operator = parts[2];
                    String reason = parts[3];
                    String cost = parts[4];
                    events.add(new BankEvent(date, time, operator, reason, cost));
                }
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return events;
    }
}
