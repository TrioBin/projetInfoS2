package fr.triobin.workshop.panels;

import fr.triobin.workshop.App;
import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.general.Machine;
import fr.triobin.workshop.general.Workstation;
import fr.triobin.workshop.popups.CreateMachinePopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WorkstationDetail extends HBox {
    private VBox leftPane;
    private Pane rightPane;
    private Label titleLabel;
    private Button addButton;
    private Workstation workstation;
    private Stage stage;

    public WorkstationDetail(Workstation workstation) {
        super(10);
        this.workstation = workstation;
        this.setSpacing(0);
        this.setPadding(Insets.EMPTY);
        this.setStyle("-fx-background-color: white;");

        // Partie droite (détails)
        rightPane = new Pane();
        rightPane.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        // Partie gauche (liste des machines)
        leftPane = new VBox(10);
        leftPane.setPadding(new Insets(20));
        leftPane.setPrefWidth(300);
        leftPane.setStyle(
            "-fx-background-color: #facc7c; " +
            "-fx-border-color: #2196f3; " +
            "-fx-border-width: 2px;");

        titleLabel = new Label("Machines de " + workstation.getRefWorkstation());
        titleLabel.setFont(Font.font(18));
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        addButton = new Button("+");
        addButton.setFont(Font.font(18));
        addButton.setMaxWidth(Double.MAX_VALUE);
        CustomCapacities.hoverCursorEffect(addButton, Cursor.HAND);
        addButton.setOnAction(evt -> openAddMachinePopup());

        buildLeftPane();

        this.getChildren().addAll(leftPane, rightPane);
    }

    private void buildLeftPane() {
        leftPane.getChildren().clear();
        leftPane.getChildren().add(titleLabel);

        for (Machine machine : workstation.getMachines()) {
            // Bouton machine
            Button machineBtn = new Button(machine.getName());
            machineBtn.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(machineBtn, Priority.ALWAYS);
            machineBtn.setOnAction(e -> {
                rightPane.getChildren().setAll(new MachineDetails(machine));
            });

            // Bouton delete
            Button delBtn = new Button("✖");
            delBtn.setFont(Font.font(8));
            delBtn.setPrefSize(24, 24);
            delBtn.setBackground(new Background(
                new BackgroundFill(Color.WHITE, new CornerRadii(6), Insets.EMPTY)));
            delBtn.setBorder(new Border(
                new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
                                 new CornerRadii(6), BorderWidths.DEFAULT)));
            delBtn.setEffect(new DropShadow(4, Color.gray(0.4)));
            delBtn.setOnAction(evt -> {
                evt.consume();
                // Supprime la machine
                workstation.getMachines().remove(machine);
                // Met à jour la mémoire et UI
                Memory.currentWorkshop.getWorkstations().stream()
                      .filter(ws -> ws == workstation)
                      .findFirst()
                      .ifPresent(ws -> {
                          ws.getMachines().clear();
                          ws.getMachines().addAll(workstation.getMachines());
                      });
                buildLeftPane();
                rightPane.getChildren().clear();
            });

            HBox row = new HBox(5, machineBtn, delBtn);
            row.setAlignment(Pos.CENTER_LEFT);
            leftPane.getChildren().add(row);
        }

        leftPane.getChildren().add(addButton);
    }

    private void openAddMachinePopup() {
        Modal dialog = new Modal(stage, new CreateMachinePopup());
        dialog.onClose(o -> {
            // Recharge la liste des machines depuis FileManager
            buildLeftPane();
        });
    }

    public void onload(Stage stage) {
        this.stage = stage;
    }
}
