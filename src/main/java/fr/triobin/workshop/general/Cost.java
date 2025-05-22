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
        // prix à la l'heure
        float result = this.cost * (t.getTime() / 3600000f);
        return Math.round(result * 100f) / 100f;
    }

    public float getCost() {
        return this.cost;
    }
}
