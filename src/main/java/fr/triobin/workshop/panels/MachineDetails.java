package fr.triobin.workshop.panels;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.general.Machine;
import fr.triobin.workshop.general.Operation;
import fr.triobin.workshop.general.Cost;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.util.StringConverter;

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

        ListView<Operation> opsList = new ListView<Operation>(
                FXCollections.observableArrayList(Memory.currentWorkshop.getOperations()));
        opsList.setCellFactory(CheckBoxListCell.forListView(
                op -> {
                    // on crée une propriété booléenne liée à machine
                    BooleanProperty allowed = new SimpleBooleanProperty(
                            machine.getOperations().contains(op));
                    // quand on coche/décoche, on met à jour la machine
                    allowed.addListener((obs, was, isNow) -> {
                        if (isNow)
                            machine.addOperation(op);
                        else
                            machine.removeOperation(op);
                    });
                    return allowed;
                },
                new StringConverter<>() {
                    @Override
                    public String toString(Operation op) {
                        return op.getName();
                    }

                    @Override
                    public Operation fromString(String string) {
                        return null;
                    }
                }));

        TextField costField = new TextField();
        costField.setText(String.valueOf(machine.getCost().getCost()));
        CustomCapacities.forceFloatTextFieldEffect(costField);
        costField.setOnAction(event -> {
            try {
                float cost = Float.parseFloat(costField.getText());
                machine.modify(new Cost(cost));
            } catch (NumberFormatException e) {
                // Handle invalid input
                costField.setText(String.valueOf(machine.getCost().getCost()));
            }
        });

        // Add components to the VBox
        this.getChildren().addAll(machineNameLabel, statusLabel, statusComboBox, new Text("Cost:"), costField, new Text("Operations:"),
                opsList);
    }
}
