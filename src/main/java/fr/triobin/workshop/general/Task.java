package fr.triobin.workshop.general;

public class Task {
    private SpecializedGoal goal;
    private Machine machine;
    private Integer productHash;

    public Task(SpecializedGoal goal, Machine machine) {
        this.goal = goal;
        this.machine = machine;
        this.productHash = goal.getProduct().hashCode();
    }

    public SpecializedGoal getGoal() {
        return goal;
    }

    public void setGoal(SpecializedGoal goal) {
        this.goal = goal;
    }

    public Machine getMachine() {
        return machine;
    }

    public void setMachine(Machine machine) {
        this.machine = machine;
    }

    public Integer getProductHash() {
        return productHash;
    }
}
