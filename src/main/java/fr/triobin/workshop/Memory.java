package fr.triobin.workshop;

import java.sql.Time;
import java.util.ArrayList;

import fr.triobin.workshop.general.Machine.MachineStatus;
import fr.triobin.workshop.general.Cost;
import fr.triobin.workshop.general.GeneralGoal;
import fr.triobin.workshop.general.Machine;
import fr.triobin.workshop.general.OPList;
import fr.triobin.workshop.general.Operation;
import fr.triobin.workshop.general.Operator;
import fr.triobin.workshop.general.Position;
import fr.triobin.workshop.general.Product;
import fr.triobin.workshop.general.RefMachine;
import fr.triobin.workshop.general.Workshop;
import fr.triobin.workshop.general.Workstation;
import fr.triobin.workshop.general.Operator.OperatorStatus;

public class Memory {
    public static ArrayList<Workshop> workshops = new ArrayList<>();
    public static Workshop currentWorkshop = null;
    public static Operator currentOperator = null;
    public static Workstation currentWorkstation = null;
    public static Boolean confimation = false;

    public static void saveFile() {
        System.out.println("Saving file...");
        //FileManager.saveFile(workshops);
    }

    public static void fakeLoad() {
        Workshop workshop1 = new Workshop("Atelier 1");

        Memory.workshops.add(workshop1);

        Workstation workstation1 = new Workstation("Poste 1", "Poste 1", new Position(0, 0), new ArrayList<>());

        workshop1.add(workstation1);

        RefMachine refMachine1 = new RefMachine("RefMachine 1");

        workshop1.addMachineRef(refMachine1);

        workstation1.addMachine(new Machine(refMachine1, "Machine 1", new Position(0, 0), new Cost(0), new ArrayList<>(),
                MachineStatus.AVAILABLE));

        Operation operation1 = new Operation("Opération 1", new Time(0));
        workshop1.addOperation(operation1);

        OPList opList1 = new OPList(new ArrayList<>());
        opList1.addOperation(operation1);

        Product product1 = new Product("Produit 1", "Description 1", opList1);
        workshop1.add(product1);

        workshop1.add(new Operator("O1", "Nom 1", "Prénom 1", new ArrayList<>(), OperatorStatus.LIBRE, ""));
        workshop1.add(new Operator("O2", "Nom 2", "Prénom 2", new ArrayList<>(), OperatorStatus.LIBRE, ""));
        workshop1.add(new Operator("O3", "Nom 3", "Prénom 3", new ArrayList<>(), OperatorStatus.LIBRE, ""));

        workshop1.add(new GeneralGoal(product1, 10));
    }
}
