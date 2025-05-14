package fr.triobin.workshop.general;

import fr.triobin.workshop.Memory;

public class Position {
    public Float x;
    public Float y;

    public Position(Float x2, Float y2) {
        this.x = x2;
        this.y = y2;
        Memory.saveFile();
    }

    public Position(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
        Memory.saveFile();
    }

    public void print() {
        System.out.println("Position: (" + x + ", " + y + ")");
    }

    public void modify(Float x, Float y) {
        this.x = x;
        this.y = y;
        Memory.saveFile();
    }
}
