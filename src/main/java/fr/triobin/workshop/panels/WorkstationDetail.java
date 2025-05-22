package fr.triobin.workshop.panels;

import fr.triobin.workshop.App;
import fr.triobin.workshop.MainVueScene;
import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomPanel;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.general.Workstation;
import fr.triobin.workshop.popups.AddSkillOperatorPopup;
import fr.triobin.workshop.popups.ChangeWorkstationPosition;
import fr.triobin.workshop.popups.CreateMachinePopup;
import fr.triobin.workshop.popups.CreateOperatorPopup;
import fr.triobin.workshop.popups.RemoveConfirmationPopup;
import fr.triobin.workshop.popups.RenameOperatorPopup;
import fr.triobin.workshop.popups.ResetPasswordPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WorkstationDetail extends HBox {
    public WorkstationDetail(Workstation workstation) {
        super(10);

        Memory.currentWorkstation = workstation;

        this.setSpacing(0);
        this.setPadding(new Insets(0));
        this.setStyle("-fx-background-color: white;");

        // Partie droite (grande zone grise)
        Pane rightPane = new Pane();
        rightPane.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        // Partie gauche (zone orangée)
        VBox leftPane = new VBox(10);
        leftPane.setPadding(new Insets(20));
        leftPane.setPrefWidth(300);
        leftPane.setStyle("-fx-background-color: #facc7c; -fx-border-color: #2196f3; -fx-border-width: 2px;");

        Label titleButton = new Label("Machines de " + workstation.getRefWorkstation());
        titleButton.setMaxWidth(Double.MAX_VALUE);
        leftPane.getChildren().add(titleButton);

        workstation.getMachines().forEach(machine -> {
            Button machineButton = new Button(machine.getName());
            machineButton.setMaxWidth(Double.MAX_VALUE);
            leftPane.getChildren().add(machineButton);

            machineButton.setOnAction(actionEvent -> {
                rightPane.getChildren().clear();
                rightPane.getChildren().add(new MachineDetails(machine));
            });
        });

        Button addButton = new Button("+");
        addButton.setMaxWidth(Double.MAX_VALUE);

        addButton.setOnAction(event -> {
            // Create a modal
            Modal dialog = new Modal(App.getStage(), new CreateMachinePopup());
            dialog.onClose(closeEvent -> {
                ((VBox) this.getChildren().get(0)).getChildren().clear();
                ((VBox) this.getChildren().get(0)).getChildren().add(titleButton);
                workstation.getMachines().forEach(machine -> {
                    Button machineButton = new Button(machine.getName());
                    machineButton.setMaxWidth(Double.MAX_VALUE);
                    leftPane.getChildren().add(machineButton);

                    machineButton.setOnAction(actionEvent -> {
                        rightPane.getChildren().clear();
                        rightPane.getChildren().add(new MachineDetails(machine));
                    });
                });
                ((VBox) this.getChildren().get(0)).getChildren().add(addButton);
            });
        });

        Button changePositionButton = new Button("Changer position");
        changePositionButton.setMaxWidth(Double.MAX_VALUE);
        changePositionButton.setOnAction(event -> {
            // Create a modal
            Modal dialog = new Modal(App.getStage(), new ChangeWorkstationPosition());
            dialog.onClose(closeEvent -> {
                
            });
        });

        leftPane.getChildren().add(addButton);
        leftPane.getChildren().add(changePositionButton);

        this.getChildren().addAll(leftPane, rightPane);
    }

    private StackPane createStatusBox(Color color) {
        StackPane pane = new StackPane();
        pane.setPrefSize(50, 80);
        pane.setStyle("-fx-border-color: black; -fx-border-width: 3;");
        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        return pane;
    }
}
