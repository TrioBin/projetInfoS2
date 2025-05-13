package fr.triobin.workshop.general;

import java.sql.Time;

public class Operation {
    private String name;
    private Time time;

    public Operation(String name, Machine machine, Time time) {
        this.name = name;
        this.time = time;
    }

    public void print() {
        System.out.println("- Operation: " + name);
        System.out.println("Time: " + time);
    }

    public String getName() {
        return name;
    }
}
