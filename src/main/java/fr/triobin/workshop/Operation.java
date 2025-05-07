package fr.triobin.workshop;

import java.sql.Time;

public class Operation {
    private String name;
    private Machine machine;
    private Time time;

    public Operation(String name, Machine machine, Time time) {
        this.name = name;
        this.machine = machine;
        this.time = time;
    }

    public void print() {
        System.out.println("- Operation: " + name);
        System.out.println("Machine: " + machine.getName());
        System.out.println("Time: " + time);
    }

    public void modifier(Machine machine) {
        this.machine = machine;
    }

    public String getName() {
        return name;
    }
}
