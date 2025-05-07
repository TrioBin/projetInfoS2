package fr.triobin.workshop;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class FileManager {
    public static ArrayList<Workshop> loadFile() {
        return null; // TODO: Implement file loading logic
    }

    public static void saveFile(ArrayList<Workshop> workshop) {
        final GsonBuilder builder = new GsonBuilder();
        final Gson gson = builder.create();
    }
}
