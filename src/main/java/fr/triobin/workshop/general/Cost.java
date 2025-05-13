package fr.triobin.workshop.general;

import java.sql.Time;

public class Cost {
    private float cost;

    public Cost(float cost) {
        this.cost = cost;
    }

    public float calcCost(Time t) {
        return this.cost * t.getTime();
    }

    public float getCost() {
        return this.cost;
    }
}
