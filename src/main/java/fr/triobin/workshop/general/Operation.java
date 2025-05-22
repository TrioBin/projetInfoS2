package fr.triobin.workshop.general;

import java.sql.Time;
import java.util.ArrayList;

import fr.triobin.workshop.Memory;

public class Operation {
    private String name;
    private Time time;

    public Operation(String name, Time time) {
        this.name = name;
        this.time = time;
        Memory.saveFile();
    }

    public void print() {
        System.out.println("- Operation: " + name);
        System.out.println("Time: " + time);
    }

    public String getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }
}
