package fr.triobin.workshop.general;

import java.sql.Timestamp;

public class MachineEvent {
    private Timestamp timestamp;
    private String machineName;
    private RefMachine refMachine;
    private String reason;

    public MachineEvent (String date, String time, String machineName, String refMachine, String reason) {
        this.timestamp = convertToTimestamp(date, time);
        this.machineName = machineName;
        this.refMachine = new RefMachine(refMachine);
        this.reason = reason;
    }

    public static Timestamp convertToTimestamp(String date, String heure) {
        String dateHeure = date + " " + heure;
        
        dateHeure = dateHeure.replaceAll("(\\d{2})(\\d{2})(\\d{4})", "$3-$2-$1");
        dateHeure = dateHeure.replaceAll("(\\d{2}):(\\d{2})", "$1:$2:00");
        // Conversion en Timestamp
        return Timestamp.valueOf(dateHeure);
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getMachineName() {
        return machineName;
    }

    public RefMachine getRefMachine() {
        return refMachine;
    }

    public String getReason() {
        return reason;
    }
}
