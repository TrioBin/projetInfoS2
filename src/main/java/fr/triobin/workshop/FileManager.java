package fr.triobin.workshop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class FileManager {
    public static ArrayList<Workshop> loadFile() {
        return null; // TODO: Implement file loading logic
    }

    public static void saveFile(ArrayList<Workshop> workshop) {
       try (BufferedWriter writer = new BufferedWriter(new FileWriter("workshops.csv"))) {
            for (Workshop w : workshop) {
                writer.write(w.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace(); 
        }
    }
}
