package fr.triobin.workshop.general;

import java.sql.Timestamp;

import fr.triobin.workshop.Memory;

public class BankEvent {
    private Timestamp timestamp;
    private Operator operator;
    private String reason;
    private Float cost;

    public BankEvent (String date, String time, String operator, String reason, String cost) {
        this.timestamp = convertToTimestamp(date, time);
        this.operator = Memory.currentWorkshop.getOperator(operator);
        this.reason = reason;
        this.cost = Float.parseFloat(cost);
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

    public Operator getOperator() {
        return operator;
    }

    public String getReason() {
        return reason;
    }

    public Float getCost() {
        return cost;
    }
}