package fr.triobin.workshop.general;

public class SpecializedGoal extends Goal {
    private Operation operation;
    private NonFinishedProduct product;

    public SpecializedGoal(Operation operation, NonFinishedProduct product) {
        this.operation = operation;
        this.product = product;
    }

    @Override
    public void print() {
        System.out.println("- SpecializedGoal: " + operation.getName() + " " + product);
    }

    public NonFinishedProduct getProduct() {
        return product;
    }
}
