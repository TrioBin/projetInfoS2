package fr.triobin.workshop;

import java.util.ArrayList;

public class OPList implements Iterable<Operation> {
    private ArrayList<Operation> operations;

    @Override
    public java.util.Iterator<Operation> iterator() {
        return operations.iterator();
    }

    public OPList(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    public void print() {
        System.out.println("- OPList:");
        for (Operation operation : operations) {
            operation.print();
        }
    }

    public void modifier(ArrayList<Operation> operations) {
        this.operations = operations;
    }

    public void addOperation(Operation operation) {
        operations.add(operation);
    }

    public void removeOperation(Integer operation) {
        operations.remove(operation);
    }
}
