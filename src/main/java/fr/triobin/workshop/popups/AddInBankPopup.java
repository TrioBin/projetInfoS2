package fr.triobin.workshop.popups;

import java.sql.Timestamp;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.Statistic;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.CustomTextField;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AddInBankPopup extends CustomScene {
    private Stage stage;
    
    public AddInBankPopup() {
        super(new VBox(), 300, 200);
        Memory.confimation = false;
        VBox root = (VBox) getRoot();
        root.setStyle("-fx-padding: 20; -fx-spacing: 10;");
        
        CustomTextField moneyInput = new CustomTextField();
        moneyInput.setPromptText("Combien d'€ ?");
        moneyInput.setMaxWidth(200);
        moneyInput.setStyle("-fx-font-size: 14px; -fx-text-fill: black;");
        CustomCapacities.forceFloatTextFieldEffect(moneyInput, true);
        moneyInput.setOnAction(event -> {
            String input = moneyInput.getText();
            if (input.isEmpty()) {
                return;
            }
            float amount = Float.parseFloat(input);
            Memory.currentWorkshop.addBank(amount);
            this.stage.close();
        });

        Button confirmButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");

        confirmButton.setOnAction(event -> {
            String input = moneyInput.getText();
            if (input.isEmpty()) {
                return;
            }
            float amount = Float.parseFloat(input);
            Memory.currentWorkshop.addBank(amount);
            this.stage.close();
        });

        cancelButton.setOnAction(event -> {
            this.stage.close();
        });

        root.getChildren().addAll(moneyInput, confirmButton, cancelButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}