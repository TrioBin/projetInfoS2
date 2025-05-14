package fr.triobin.workshop.general;

import java.util.ArrayList;

import fr.triobin.workshop.Memory;

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
        Memory.saveFile();
    }

    public enum OperatorStatus {
        LIBRE, EN_PAUSE, TRAVAILLANT, BREAK
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
        Memory.saveFile();
    }

    public void modify(OperatorStatus status) {
        this.status = status;
        Memory.saveFile();
    }
    public void remove(){
        this.status = OperatorStatus.BREAK;
        Memory.saveFile();
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
        Memory.saveFile();
    }

    public void removeSkill(RefMachine skill) {
        this.skills.remove(skill);
        Memory.saveFile();
    }

    public void setCode(String code) {
        this.code = code;
        Memory.saveFile();
    }

    public void setPassword(String password) {
        this.password = password;
        Memory.saveFile();
    }
}
