package fr.triobin.workshop;

import java.io.FileWriter;
import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import fr.triobin.workshop.general.RefMachine;

public class Statistic {
    public static String separator = " ";

    public static void addStat(Timestamp timestamp, String machineName, RefMachine refMachine, String reason) {
        try {
            // use file writer to append the statistics to a file
            FileWriter fileWriter = new FileWriter("statistics.txt", true);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy HH:mm");
            String toWrite = timestamp.toLocalDateTime().format(formatter);
            toWrite += separator + machineName + separator + refMachine.getName() + separator + reason;
            fileWriter.write(toWrite + "\n");
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
