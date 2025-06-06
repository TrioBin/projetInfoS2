package fr.triobin.workshop.popups;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.CustomTextField;
import fr.triobin.workshop.general.Operator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenameWorkshopPopup extends CustomScene {
    private Stage stage;

    public RenameWorkshopPopup() {
        super(new VBox(), 500, 500);
        VBox root = (VBox) getRoot();

        // Input field for the new name
        CustomTextField nameField = new CustomTextField();
        nameField.setPromptText("Entrer le nouveau nom de l'atelier");

        // Enter action
        nameField.setOnAction(event -> {
            String newName = nameField.getText();
            if (!newName.isEmpty()) {
                // Rename files
                Path source = Paths.get(Memory.currentWorkshop.getDesignation() + "bankStatistics.txt");
                Path target = Paths.get(newName + "bankStatistics.txt");

                try {
                    // ATOMIC_MOVE tente un déplacement atomique si le système de fichiers le permet
                    Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
                } catch (IOException e) {
                    System.err.println("Erreur lors du renommage : " + e.getMessage());
                }

                Path source2 = Paths.get(Memory.currentWorkshop.getDesignation() + "machineStatistics.txt");
                Path target2 = Paths.get(newName + "machineStatistics.txt");

                try {
                    // ATOMIC_MOVE tente un déplacement atomique si le système de fichiers le permet
                    Files.move(source2, target2, StandardCopyOption.ATOMIC_MOVE);
                } catch (IOException e) {
                    System.err.println("Erreur lors du renommage : " + e.getMessage());
                }
                Memory.currentWorkshop.setDesignation(newName);
                stage.close();
            }
        });

        HBox buttonContainer = new HBox(10);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10));

        // Button to confirm the renaming
        Button renameButton = new Button("Renommer");
        renameButton.setOnAction(event -> {
            String newName = nameField.getText();
            if (!newName.isEmpty()) {
                // Rename files
                Path source = Paths.get(Memory.currentWorkshop.getDesignation() + "bankStatistics.txt");
                Path target = Paths.get(newName + "bankStatistics.txt");

                try {
                    // ATOMIC_MOVE tente un déplacement atomique si le système de fichiers le permet
                    Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);
                } catch (IOException e) {
                    System.err.println("Erreur lors du renommage : " + e.getMessage());
                }

                Path source2 = Paths.get(Memory.currentWorkshop.getDesignation() + "machineStatistics.txt");
                Path target2 = Paths.get(newName + "machineStatistics.txt");

                try {
                    // ATOMIC_MOVE tente un déplacement atomique si le système de fichiers le permet
                    Files.move(source2, target2, StandardCopyOption.ATOMIC_MOVE);
                } catch (IOException e) {
                    System.err.println("Erreur lors du renommage : " + e.getMessage());
                }
                Memory.currentWorkshop.setDesignation(newName);
                stage.close();
            }
        });
        // Button to cancel the renaming
        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(event -> stage.close());

        buttonContainer.getChildren().addAll(renameButton, cancelButton);

        // Add components to the root layout
        root.getChildren().addAll(nameField, buttonContainer);
        root.setStyle("-fx-padding: 20; -fx-spacing: 10;");
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        this.stage.setTitle("Renommer l'opérateur");
    }
}
