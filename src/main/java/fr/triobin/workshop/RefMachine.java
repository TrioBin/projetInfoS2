package fr.triobin.workshop;

public class RefMachine {
    private String code;

    public RefMachine(String code) {
        this.code = code;
    }

    public void print() {
        System.out.println("- RefMachine: " + code);
    }

    public String getName() {
        return code;
    }
}
