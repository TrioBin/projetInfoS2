package fr.triobin.workshop;

import java.util.ArrayList;

import fr.triobin.workshop.Operator.OperatorStatus;

public class Memory {
    public static ArrayList<Workshop> workshops = new ArrayList<>();
    public static Workshop currentWorkshop = null;

    public static void fakeLoad() {
        Workshop workshop1 = new Workshop("Atelier 1");
        Workshop workshop2 = new Workshop("Atelier 2");

        Memory.workshops.add(workshop1);
        Memory.workshops.add(workshop2);

        workshop1.add(new Workstation("Poste 1", "Poste 1", new Position(0, 0), new ArrayList<>()));
        workshop1.add(new Workstation("Poste 2", "Poste 2", new Position(0, 1), new ArrayList<>()));
        workshop1.add(new Workstation("Poste 3", "Poste 3", new Position(0, 2), new ArrayList<>()));
        workshop1.add(new Workstation("Poste 4", "Poste 4", new Position(0, 3), new ArrayList<>()));
        workshop1.add(new Workstation("Poste 5", "Poste 5", new Position(0, 4), new ArrayList<>()));
        workshop1.add(new Workstation("Poste 6", "Poste 6", new Position(0, 5), new ArrayList<>()));
        workshop1.add(new Workstation("Poste 7", "Poste 7", new Position(0, 6), new ArrayList<>()));
        workshop1.add(new Workstation("Poste 8", "Poste 8", new Position(0, 7), new ArrayList<>()));
        workshop1.add(new Workstation("Poste 9", "Poste 9", new Position(0, 8), new ArrayList<>()));

        workshop1.add(new Operator("O1", "Nom 1", "Prénom 1", new ArrayList<>(), OperatorStatus.AVAILABLE));
        workshop1.add(new Operator("O2", "Nom 2", "Prénom 2", new ArrayList<>(), OperatorStatus.AVAILABLE));
        workshop1.add(new Operator("O3", "Nom 3", "Prénom 3", new ArrayList<>(), OperatorStatus.AVAILABLE));

        workshop2.add(new Workstation("Poste 1", "Poste 3", new Position(1, 0), new ArrayList<>()));
        workshop2.add(new Workstation("Poste 2", "Poste 4", new Position(1, 1), new ArrayList<>()));
    }
}
