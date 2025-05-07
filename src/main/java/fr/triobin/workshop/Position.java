package fr.triobin.workshop;

public class Position {
    public int x;
    public int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void print() {
        System.out.println("Position: (" + x + ", " + y + ")");
    }

    public void modify(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
