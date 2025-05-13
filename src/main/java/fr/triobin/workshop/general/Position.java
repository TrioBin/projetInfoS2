package fr.triobin.workshop.general;

public class Position {
    public Float x;
    public Float y;

    public Position(Float x2, Float y2) {
        this.x = x2;
        this.y = y2;
    }

    public Position(int x, int y) {
        this.x = (float) x;
        this.y = (float) y;
    }

    public void print() {
        System.out.println("Position: (" + x + ", " + y + ")");
    }

    public void modify(Float x, Float y) {
        this.x = x;
        this.y = y;
    }
}
