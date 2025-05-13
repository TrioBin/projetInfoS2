package fr.triobin.workshop.general;

import java.util.ArrayList;

public class Workstation {
    private String refWorkstation;
    private String dworkstation;
    private Position position;
    private ArrayList<Machine> machines;

    public Workstation(String refWorkstation, String dworkstation, Position position, ArrayList<Machine> machines) {
        this.refWorkstation = refWorkstation;
        this.dworkstation = dworkstation;
        this.position = position;
        this.machines = machines;
    }

    public void print() {
        System.out.println("- Workstation: " + dworkstation);
        position.print();
        System.out.println("Machines: ");
        for (Machine m : machines) {
            m.print();
        }
    }

    public void modify(Position position) {
        this.position = position;
    }

    public void modify(ArrayList<Machine> machines) {
        this.machines = machines;
    }

    public void addMachine(Machine m) {
        machines.add(m);
    }

    public void removeMachine(Machine m) {
        machines.remove(m);
    }

    public void changeMachine(Machine m, Machine newM) {
        int index = machines.indexOf(m);
        machines.set(index, newM);
    }

    public String getRefWorkstation() {
        return refWorkstation;
    }
}
