package fr.triobin.workshop.general;

import fr.triobin.workshop.Memory;

public class SpecializedGoal extends Goal {
    private Operation operation;
    private NonFinishedProduct product;

    public SpecializedGoal(Operation operation, NonFinishedProduct product) {
        this.operation = operation;
        this.product = product;
        Memory.saveFile();
    }

    @Override
    public void print() {
        System.out.println("- SpecializedGoal: " + operation.getName() + " " + product);
    }

    public NonFinishedProduct getProduct() {
        return product;
    }

    public Operation getOperation() {
        return operation;
    }
}
