package fr.triobin.workshop;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import fr.triobin.workshop.general.Workshop;

public class FileManager {
    public static ArrayList<Workshop> loadFile() {
        ObjectMapper mapper = new ObjectMapper();
        ArrayList<Workshop> obj = mapper.readValue("", Workshop[].class);
        return null; // TODO: Implement file loading logic
    }

    public static void saveFile(ArrayList<Workshop> workshop) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("workshops.json"))) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(workshop);
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
