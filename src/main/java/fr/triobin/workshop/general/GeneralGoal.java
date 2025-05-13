package fr.triobin.workshop.general;

import java.util.ArrayList;

public class GeneralGoal extends Goal {
    private Product product;
    private int quantity;

    public GeneralGoal(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public void print() {
        System.out.println("- GeneralGoal: " + product.getName() + " " + quantity);
    }

    public ArrayList<SpecializedGoal> getSpecializedGoals() {
        ArrayList<SpecializedGoal> goals = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            NonFinishedProduct tempProduct = new NonFinishedProduct(product);
            for (Operation operation : product.getOperations()) {
                goals.add(new SpecializedGoal(operation, tempProduct));
            }
        }
        return goals;
    }
}
