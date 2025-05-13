package fr.triobin.workshop;

import java.util.ArrayList;

import fr.triobin.workshop.Operator.OperatorStatus;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddSkillOperatorPopup extends CustomScene {
    private Stage stage;

    public AddSkillOperatorPopup() {
        super(new VBox(), 500, 500);
        VBox root = (VBox) getRoot();
        
        this.load(root);

        root.setPadding(new Insets(20));
    }

    private void load(VBox root) {
        Memory.currentWorkshop.getMachinesRef().forEach(machine -> {
            Button refMachineButton = new Button(machine.getName());
            refMachineButton.setMaxWidth(Double.MAX_VALUE);
            root.getChildren().add(refMachineButton);

            refMachineButton.setOnAction(event -> {
                Memory.currentOperator.addSkill(machine);
                this.stage.close();
            });
        });

        Button addButton = new Button("+");
        addButton.setMaxWidth(Double.MAX_VALUE);

        addButton.setOnAction(event -> {
            // Create a modal
            Modal dialog = new Modal(this.stage, new CreateSkillPopup());
            dialog.onClose(closeEvent -> {
                ((VBox) this.getRoot()).getChildren().clear();
                this.load((VBox) this.getRoot());
            });
        });
        root.getChildren().add(addButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("Ajouter une compétence");
    }
}
