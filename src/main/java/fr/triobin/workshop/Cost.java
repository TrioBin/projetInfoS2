package fr.triobin.workshop;

import java.sql.Time;

public class Cost {
    private float cost;

    public Cost(float cost) {
        this.cost = cost;
    }

    public float calcCost(Tisme t) {
        return this.cost * t.getTime();
    }

    public float getCost() {
        return this.cost;
    }
}
