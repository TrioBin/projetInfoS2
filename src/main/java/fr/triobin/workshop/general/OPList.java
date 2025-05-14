package fr.triobin.workshop.general;

import java.util.ArrayList;

import fr.triobin.workshop.Memory;

public class OPList implements Iterable<Operation> {
    private ArrayList<Operation> operations;

    @Override
    public java.util.Iterator<Operation> iterator() {
        return operations.iterator();
    }

    public OPList(ArrayList<Operation> operations) {
        this.operations = operations;
        Memory.saveFile();
    }

    public void print() {
        System.out.println("- OPList:");
        for (Operation operation : operations) {
            operation.print();
        }
    }

    public void modifier(ArrayList<Operation> operations) {
        this.operations = operations;
        Memory.saveFile();
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
        Memory.saveFile();
    }

    public void removeOperation(Integer operation) {
        operations.remove(operation);
        Memory.saveFile();
    }
}
