package fr.triobin.workshop.general;

import java.lang.reflect.Array;
import java.util.ArrayList;

import fr.triobin.workshop.Memory;

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
        Memory.saveFile();
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
        Memory.saveFile();
    }

    public void modify(Cost c) {
        this.c = c;
        Memory.saveFile();
    }

    public void modify(MachineStatus status) {
        this.status = status;
        Memory.saveFile();
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void addOperation(Operation o) {
        operations.add(o);
        Memory.saveFile();
    }

    public void removeOperation(Operation o) {
        operations.remove(o);
        Memory.saveFile();
    }

    public void changeOperation(Operation o, Operation newO) {
        int index = operations.indexOf(o);
        operations.set(index, newO);
        Memory.saveFile();
    }

    public String getName() {
        return dmachine;
    }

    public MachineStatus getStatus() {
        return status;
    }

    public Cost getCost() {
        return c;
    }

    public Position getPosition() {
        return position;
    }

    public RefMachine getRefMachine() {
        return refMachine;
    }
}
