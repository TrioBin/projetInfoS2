package fr.triobin.workshop;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateWorkstationPopup extends CustomScene {
    private Stage stage;

    public CreateWorkstationPopup() {
        super(new VBox(), 500, 500);
        VBox root = (VBox) getRoot();

        TextField nomAtelier = new TextField();
        nomAtelier.setPromptText("Nom du Poste");
        nomAtelier.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        nomAtelier.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        // Label "Position"

        Label labelPosition = new Label("Position :");
        labelPosition.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        labelPosition.setPadding(new Insets(10, 0, 0, 0));

        TextField x = new TextField();
        x.setPromptText("X");
        x.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        x.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        TextField y = new TextField();
        y.setPromptText("Y");
        y.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        y.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        // Force X et Y à être des nombres décimaux avec 1 seule virgule ou point
        x.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*[.,]?\\d*")) {
                x.setText(oldValue); // Revenir à l'ancienne valeur si la nouvelle est invalide
            }
        });
        y.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*[.,]?\\d*")) {
                y.setText(oldValue); // Revenir à l'ancienne valeur si la nouvelle est invalide
            }
        });

        HBox position = new HBox(10);
        position.getChildren().addAll(x, y);

        // Bouton « Créer »
        Button btnCreer = new Button("Créer");
        btnCreer.setPrefWidth(100);
        // style orange et coins arrondis
        btnCreer.setStyle(
                "-fx-background-color: #ffb000;" +
                        "-fx-background-radius: 5;" +
                        "-fx-font-size: 14px;" +
                        "-fx-text-fill: black;");
        btnCreer.setOnAction(e -> {
            System.out.println("Poste créé : " + nomAtelier.getText());
            Memory.currentWorkshop.add(
                    new Workstation(nomAtelier.getText(), nomAtelier.getText(), new Position(Float.parseFloat(x.getText()), Float.parseFloat(y.getText())), new ArrayList<>()));
            this.stage.close();
        });

        HBox btnContainer = new HBox();
        btnContainer.setPadding(new Insets(20, 0, 0, 0));
        btnContainer.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        btnContainer.getChildren().add(btnCreer);

        root.getChildren().addAll(nomAtelier, labelPosition, position, btnContainer);
        root.setPadding(new Insets(20));
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("Créer un atelier");
    }
}
