package fr.triobin.workshop.popups;

import java.util.ArrayList;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.general.Cost;
import fr.triobin.workshop.general.Machine;
import fr.triobin.workshop.general.Operation;
import fr.triobin.workshop.general.Position;
import fr.triobin.workshop.general.RefMachine;
import fr.triobin.workshop.general.Workstation;
import fr.triobin.workshop.general.Machine.MachineStatus;
import javafx.geometry.Dimension2D;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateMachinePopup extends CustomScene {
    private Stage stage;

    public CreateMachinePopup() {
        super(new VBox(), 500, 500);
        VBox root = (VBox) getRoot();

        TextField nomMachine = new TextField();
        nomMachine.setPromptText("Nom de la machine");
        nomMachine.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        nomMachine.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        // Combox pour choisir le type de machine
        ComboBox<String> typeMachine = new ComboBox<>();
        for (RefMachine refMachine : Memory.currentWorkshop.getMachinesRef()) {
            typeMachine.getItems().add(refMachine.getName());
        }
        typeMachine.getItems().add("+");

        typeMachine.setOnAction(e -> {
            String selectedItem = typeMachine.getSelectionModel().getSelectedItem();
            if (selectedItem != null && selectedItem.equals("+")) {
                // Ouvrir une popup pour ajouter une nouvelle référence de machine
                Modal dialog = new Modal(this.stage, new CreateSkillPopup());
                dialog.onClose(closeEvent -> {
                    typeMachine.getItems().clear();
                    for (RefMachine refMachine : Memory.currentWorkshop.getMachinesRef()) {
                        typeMachine.getItems().add(refMachine.getName());
                    }
                    typeMachine.getItems().add("+");
                    typeMachine.getSelectionModel().select(typeMachine.getItems().size() - 2);
                });
            }
        });

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
        CustomCapacities.forceFloatTextFieldEffect(x);
        CustomCapacities.forceFloatTextFieldEffect(y);

        HBox position = new HBox(10);
        position.getChildren().addAll(x, y);

        Label labelDimension = new Label("Dimension :");
        labelDimension.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        labelDimension.setPadding(new Insets(10, 0, 0, 0));

        TextField width = new TextField();
        width.setPromptText("X");
        width.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        width.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        TextField heigth = new TextField();
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

        HBox dimension = new HBox(10);
        dimension.getChildren().addAll(width, heigth);

        // input cout horaire
        TextField coutHoraire = new TextField();
        coutHoraire.setPromptText("Coût horaire");
        coutHoraire.setPrefWidth(250);
        CustomCapacities.forceFloatTextFieldEffect(coutHoraire);

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
            System.out.println("Poste créé : " + nomMachine.getText());
            Memory.currentWorkstation
                    .addMachine(new Machine(
                            Memory.currentWorkshop.getMachinesRef()
                                    .get(typeMachine.getSelectionModel().getSelectedIndex()),
                            nomMachine.getText(),
                            new Position(Float.parseFloat(x.getText()), Float.parseFloat(y.getText())),
                            new Dimension2D(Float.parseFloat(width.getText()), Float.parseFloat(heigth.getText())),
                            new Cost(Float.parseFloat(coutHoraire.getText())), new ArrayList<>(),
                            MachineStatus.AVAILABLE));
            this.stage.close();
        });

        HBox btnContainer = new HBox();
        btnContainer.setPadding(new Insets(20, 0, 0, 0));
        btnContainer.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
        btnContainer.getChildren().add(btnCreer);

        // Ajout des éléments au conteneur principal
        root.getChildren().addAll(nomMachine, typeMachine, labelPosition, position, labelDimension, dimension, coutHoraire, btnContainer);
        root.setPadding(new Insets(20));
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("Créer un atelier");
    }
}