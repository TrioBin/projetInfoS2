package fr.triobin.workshop.general;

import fr.triobin.workshop.Memory;

public class RefMachine {
    private String code;

    public RefMachine(String code) {
        this.code = code;
        Memory.saveFile();
    }

    public void print() {
        System.out.println("- RefMachine: " + code);
    }

    public String getName() {
        return code;
    }
}
