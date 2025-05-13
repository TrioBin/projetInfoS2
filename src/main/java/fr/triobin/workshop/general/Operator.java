package fr.triobin.workshop.general;

import java.util.ArrayList;

public class Operator {
    private String code;
    private String name;
    private String surname;
    private ArrayList<RefMachine> skills;
    private OperatorStatus status;
    private String password;

    public Operator(String code, String name, String surname, ArrayList<RefMachine> skills, OperatorStatus status, String password) {
        this.code = code;
        this.name = name;
        this.surname = surname;
        this.skills = skills;
        this.status = status;
        this.password = password;
    }

    public enum OperatorStatus {
        AVAILABLE, BUSY, BREAK
    }

    public void print() {
        System.out.println("- Operator: " + code);
        System.out.println("Name: " + name);
        System.out.println("Surname: " + surname);
        System.out.println("Skills:");
        for (RefMachine skill : skills) {
            System.out.println(skill);
        }
        System.out.println("Status: " + status);
    }

    public void modify(ArrayList<RefMachine> skills) {
        this.skills = skills;
    }

    public void modify(OperatorStatus status) {
        this.status = status;
    }
    public void remove(){
        this.status = OperatorStatus.BREAK;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getCode() {
        return this.code;
    }

    public ArrayList<RefMachine> getSkills() {
        return this.skills;
    }

    public OperatorStatus getStatus() {
        return this.status;
    }

    public String getPassword() {
        return this.password;
    }

    public void addSkill(RefMachine skill) {
        this.skills.add(skill);
    }

    public void removeSkill(RefMachine skill) {
        this.skills.remove(skill);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
