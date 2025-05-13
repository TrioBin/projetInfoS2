package fr.triobin.workshop.panels;

import fr.triobin.workshop.general.Machine;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class MachineDetails extends VBox {
    public MachineDetails(Machine machine) {
        super(10);
        this.setStyle("-fx-background-color: white;");
        this.setSpacing(0);
        this.setPrefWidth(450);
        
        // Create a label for the machine name
        Label machineNameLabel = new Label(machine.getName());
        machineNameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
        machineNameLabel.setAlignment(Pos.CENTER);
        
        // Create a label for the machine status
        Label statusLabel = new Label("Status:");
        statusLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");

        // Create a ComboBox for selecting the machine status
        ComboBox<Machine.MachineStatus> statusComboBox = new ComboBox<>();
        statusComboBox.getItems().addAll(Machine.MachineStatus.values());
        statusComboBox.setValue(machine.getStatus()); // Set the current status
        statusComboBox.setOnAction(event -> {
            Machine.MachineStatus selectedStatus = statusComboBox.getValue();
            machine.modify(selectedStatus); // Update the machine's status
        });

        // Add components to the VBox
        this.getChildren().addAll(machineNameLabel, statusLabel, statusComboBox);
    }
}
