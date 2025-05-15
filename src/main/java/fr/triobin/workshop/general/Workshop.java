package fr.triobin.workshop.general;

import java.util.ArrayList;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.general.NonFinishedProduct.ProductStatus;

public class Workshop {
    private String designation;
    private ArrayList<Workstation> workstations;
    private ArrayList<Product> products;
    private ArrayList<Operator> operators;
    private ArrayList<RefMachine> machinesRef = new ArrayList<>();
    private ArrayList<Operation> operations = new ArrayList<>();
    private ArrayList<Machine> machines = new ArrayList<>();
    private ArrayList<Goal> goals;

    private ArrayList<SpecializedGoal> actualGoals;

    public Workshop(String designation) {
        this.designation = designation;
        this.workstations = new ArrayList<>();
        this.products = new ArrayList<>();
        this.operators = new ArrayList<>();
        this.goals = new ArrayList<>();
        this.actualGoals = new ArrayList<>();
        this.machinesRef = new ArrayList<>();
        Memory.saveFile();
    }

    public void add(Workstation workstation) {
        workstations.add(workstation);
        Memory.saveFile();
    }

    public void add(Product product) {
        products.add(product);
        Memory.saveFile();
    }

    public void add(Operator operator) {
        operators.add(operator);
        Memory.saveFile();
    }

    public void add(Goal goal) {
        goals.add(goal);
        Memory.saveFile();
    }

    public void print() {
        System.out.println("- Workshop: " + designation);
        System.out.println("Workstations: ");
        for (Workstation w : workstations) {
            w.print();
        }
        System.out.println("Products: ");
        for (Product p : products) {
            p.print();
        }
        System.out.println("Operators: ");
        for (Operator o : operators) {
            o.print();
        }
        System.out.println("Goals: ");
        for (Goal g : goals) {
            g.print();
        }
    }

    public void replaceToSpecializedGoalsFrom(GeneralGoal generalGoal) {
        ArrayList<SpecializedGoal> specializedGoals = generalGoal.getSpecializedGoals();
        int index = goals.indexOf(generalGoal);
        goals.remove(generalGoal);
        goals.addAll(index, specializedGoals);
        Memory.saveFile();
    }

    public SpecializedGoal getNextGoal() {
        return getNextGoal(0);
    }

    public SpecializedGoal getNextGoal(int i) {
        if (goals.size() > i) {
            Goal goal = goals.get(i);
            if (goal instanceof SpecializedGoal) {
                SpecializedGoal specializedGoal = (SpecializedGoal) goal;
                if (specializedGoal.getProduct().getStatus() == NonFinishedProduct.ProductStatus.FREE) {
                    goals.remove(i);
                    specializedGoal.getProduct().setStatus(NonFinishedProduct.ProductStatus.USED);
                    actualGoals.add(specializedGoal);
                    return (SpecializedGoal) goal;
                } else {
                    return getNextGoal(i + 1);
                }
            } else if (goal instanceof GeneralGoal) {
                replaceToSpecializedGoalsFrom((GeneralGoal) goal);
                return getNextGoal(i);
            }
        }
        return null;
    }

    public ArrayList<SpecializedGoal> getActualGoals() {
        return actualGoals;
    }

    public void removeActualGoal(SpecializedGoal goal) {
        actualGoals.remove(goal);
        goal.getProduct().setStatus(NonFinishedProduct.ProductStatus.FREE);
        Memory.saveFile();
    }

    public void removeGoal(Goal goal) {
        goals.remove(goal);
        Memory.saveFile();
    }

    public String getDesignation() {
        return designation;
    }

    public ArrayList<Workstation> getWorkstations() {
        return workstations;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public ArrayList<Operator> getOperators() {
        return operators;
    }

    public ArrayList<Goal> getGoals() {
        return goals;
    }

    public Workstation getWorkstationByRef(String ref) {
        for (Workstation workstation : workstations) {
            if (workstation.getRefWorkstation().equals(ref)) {
                return workstation;
            }
        }
        return null;
    }

    public ArrayList<RefMachine> getMachinesRef() {
        return machinesRef;
    }

    public void addMachineRef(RefMachine machine) {
        machinesRef.add(machine);
        Memory.saveFile();
    }

    public void removeMachineRef(RefMachine machine) {
        machinesRef.remove(machine);
        Memory.saveFile();
    }

    public void removeOperator(Operator operator) {
        operators.remove(operator);
        Memory.saveFile();
    }

    public void removeWorkstation(Workstation workstation) {
        workstations.remove(workstation);
        Memory.saveFile();
    }

    public ArrayList<Operation> getOperations() {
        return operations;
    }

    public void setOperations(ArrayList<Operation> operations) {
        this.operations = operations;
        Memory.saveFile();
    }

    public void addOperation(Operation operation) {
        this.operations.add(operation);
        Memory.saveFile();
    }

    public void removeOperation(Operation operation) {
        this.operations.remove(operation);
        Memory.saveFile();
    }

    public Operation getOperation(String name) {
        for (Operation operation : operations) {
            if (operation.getName().equals(name)) {
                return operation;
            }
        }
        return null;
    }

    public RefMachine getMachineRef(String name) {
        for (RefMachine machine : machinesRef) {
            if (machine.getName().equals(name)) {
                return machine;
            }
        }
        return null;
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public void add(Machine machine) {
        machines.add(machine);
        Memory.saveFile();
    }

    public void removeMachine(Machine machine) {
        machines.remove(machine);
        Memory.saveFile();
    }

    public Machine getMachine(String name) {
        for (Machine machine : machines) {
            if (machine.getName().equals(name)) {
                return machine;
            }
        }
        return null;
    }

    public Product getProduct(String name) {
        for (Product product : products) {
            if (product.getName().equals(name)) {
                return product;
            }
        }
        return null;
    }
}