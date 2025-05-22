package fr.triobin.workshop.popups;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import fr.triobin.workshop.FileManager;
import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.general.NonFinishedProduct;
import fr.triobin.workshop.general.Workshop;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ImportPopup extends CustomScene {
    private Stage stage;

    public ImportPopup() {
        super(new VBox(), 300, 250); // Augmenté un peu la hauteur
        Memory.confimation = false;
        VBox root = (VBox) getRoot();
        root.setStyle("-fx-padding: 20; -fx-spacing: 10;");

        TextArea importArea = new TextArea();
        importArea.setPromptText("Entrez le code de l'atelier ici...");
        importArea.setWrapText(true);
        importArea.setPrefRowCount(6);

        Button importButton = new Button("Importer");
        importButton.setOnAction(event -> {
            String code = importArea.getText();
            if (!code.isEmpty()) {
                FileManager.readStatus = true;
                // Load the file
                ArrayList<NonFinishedProduct> nonFinishedProducts = new ArrayList<>();
                int[] lineIndex = { 0 };
                ArrayList<Workshop> workshops = new ArrayList<>();
                for (String line : code.split("\n")) {
                    if (line.isEmpty()) {
                        continue;
                    }
                    FileManager.readLine(line, lineIndex, workshops, nonFinishedProducts);
                }
                Memory.workshops.addAll(workshops);
                Memory.saveFile();
                Memory.confimation = true;
                stage.close();
            }
        });

        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(event -> {
            Memory.confimation = false;
            stage.close();
        });

        root.getChildren().addAll(new Label("Importer l'atelier"), importArea, importButton, cancelButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}
