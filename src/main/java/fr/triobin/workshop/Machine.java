package fr.triobin.workshop;

import java.util.ArrayList;

public class Machine {
    private RefMachine refMachine;
    private String dmachine;
    private Position position;
    private Cost c;
    private ArrayList<Operation> operations;
    private MachineStatus status;

    public Machine(RefMachine refMachine, String dmachine, Position position, Cost c, ArrayList<Operation> operations, MachineStatus status) {
        this.refMachine = refMachine;
        this.dmachine = dmachine;
        this.position = position;
        this.c = c;
        this.operations = operations;
        this.status = status;
    }

    public enum MachineStatus {
        AVAILABLE,
        USED,
        MAINTENANCE
    }

    public void print() {
        System.out.println("- Machine: " + dmachine);
        System.out.println("RefMachine: " + refMachine.getName());
        System.out.println("Position: " + position.x + " " + position.y);
        System.out.println("Cost: " + c.getCost());
        System.out.println("Status: " + status);
        System.out.println("Operations: ");
        for (Operation o : operations) {
            o.print();
        }
    }

    public void modify(Position position) {
        this.position = position;
    }

    public void modify(Cost c) {
        this.c = c;
    }

    public void modify(MachineStatus status) {
        this.status = status;
    }

    public void addOperation(Operation o) {
        operations.add(o);
    }

    public void removeOperation(Operation o) {
        operations.remove(o);
    }

    public void changeOperation(Operation o, Operation newO) {
        int index = operations.indexOf(o);
        operations.set(index, newO);
    }

    public String getName() {
        return dmachine;
    }
}
