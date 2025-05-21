package fr.triobin.workshop.popups;

import java.sql.Timestamp;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.Statistic;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.CustomTextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MaintenanceReason extends CustomScene {
    private Stage stage;
    
    public MaintenanceReason() {
        super(new VBox(), 300, 200);
        Memory.confimation = false;
        VBox root = (VBox) getRoot();
        root.setStyle("-fx-padding: 20; -fx-spacing: 10;");
        
        CustomTextField reasonField = new CustomTextField();
        reasonField.setPromptText("Entrez la raison de la maintenance");
        reasonField.setMaxWidth(200);
        reasonField.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        Button confirmButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");

        confirmButton.setOnAction(event -> {
            Memory.confimation = true;
            Statistic.addStat(new Timestamp(System.currentTimeMillis()), Memory.currentMachine.getName(), Memory.currentMachine.getRefMachine(), reasonField.getText());
            this.stage.close();
        });

        cancelButton.setOnAction(event -> {
            Memory.confimation = false;
            this.stage.close();
        });

        root.getChildren().addAll(reasonField, confirmButton, cancelButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}