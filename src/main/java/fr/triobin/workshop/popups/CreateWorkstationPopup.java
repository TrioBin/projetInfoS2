package fr.triobin.workshop.popups;

import java.util.ArrayList;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.CustomTextField;
import fr.triobin.workshop.general.Position;
import fr.triobin.workshop.general.Workstation;
import javafx.geometry.Dimension2D;
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

        CustomTextField nomAtelier = new CustomTextField();
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

        CustomTextField x = new CustomTextField();
        x.setPromptText("X");
        x.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        x.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        CustomTextField y = new CustomTextField();
        y.setPromptText("Y");
        y.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        y.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

                        Label labelDimension = new Label("Dimension :");
        labelDimension.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        labelDimension.setPadding(new Insets(10, 0, 0, 0));

        CustomTextField width = new CustomTextField();
        width.setPromptText("X");
        width.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        width.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        CustomTextField heigth = new CustomTextField();
        heigth.setPromptText("Y");
        heigth.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        heigth.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        // Force X et Y à être des nombres décimaux avec 1 seule virgule ou point
        CustomCapacities.forceFloatTextFieldEffect(width);
        CustomCapacities.forceFloatTextFieldEffect(heigth);

        HBox position = new HBox(10);
        position.getChildren().addAll(x, y);

        HBox dimension = new HBox(10);
        position.getChildren().addAll(width, heigth);

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
            Memory.currentWorkshop.add(
                    new Workstation(nomAtelier.getText(), nomAtelier.getText(), new Position(Float.parseFloat(x.getText()), Float.parseFloat(y.getText())), new Dimension2D(Float.parseFloat(width.getText()), Float.parseFloat(heigth.getText())), new ArrayList<>()));
            this.stage.close();
        });

        HBox btnContainer = new HBox();
        btnContainer.setPadding(new Insets(20, 0, 0, 0));
        btnContainer.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        btnContainer.getChildren().add(btnCreer);

        root.getChildren().addAll(nomAtelier, labelPosition, position, labelDimension, dimension, btnContainer);
        root.setPadding(new Insets(20));
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("Créer un atelier");
    }
}
