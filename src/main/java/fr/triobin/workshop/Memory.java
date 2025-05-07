package fr.triobin.workshop;

import java.util.ArrayList;

public class Memory {
    public static ArrayList<Workshop> workshops = new ArrayList<>();

    public static void fakeLoad() {
        workshops.add(new Workshop("Atelier 1"));
        workshops.add(new Workshop("Atelier 2"));
        workshops.add(new Workshop("Atelier 3"));
    }
}
