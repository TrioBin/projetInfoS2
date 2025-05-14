package fr.triobin.workshop.general;

import java.sql.Time;

import fr.triobin.workshop.Memory;

public class Cost {
    private float cost;

    public Cost(float cost) {
        this.cost = cost;
        Memory.saveFile();
    }

    public float calcCost(Time t) {
        return this.cost * t.getTime();
    }

    public float getCost() {
        return this.cost;
    }
}
