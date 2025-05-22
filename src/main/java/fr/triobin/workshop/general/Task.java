package fr.triobin.workshop.general;

import java.sql.Timestamp;
import java.time.Instant;

public class Task {
    private SpecializedGoal goal;
    private Machine machine;
    private Integer productHash;
    private Timestamp startTime;

    public Task(SpecializedGoal goal, Machine machine, Timestamp startTime) {
        this.goal = goal;
        this.machine = machine;
        this.productHash = goal.getProduct().hashCode();
        this.startTime = startTime;
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

    public Timestamp getStartTime() {
        return startTime;
    }
}
