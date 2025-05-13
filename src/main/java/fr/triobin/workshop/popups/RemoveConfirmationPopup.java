package fr.triobin.workshop.popups;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RemoveConfirmationPopup extends CustomScene {
    private Stage stage;
    
    public RemoveConfirmationPopup() {
        super(new VBox(), 300, 200);
        Memory.confimation = false;
        VBox root = (VBox) getRoot();
        root.setStyle("-fx-padding: 20; -fx-spacing: 10;");

        Label confirmationLabel = new Label("Etes-vous sûr ?");
        Button confirmButton = new Button("Confirmer");
        Button cancelButton = new Button("Annuler");

        confirmButton.setOnAction(event -> {
            Memory.confimation = true;
            this.stage.close();
        });

        cancelButton.setOnAction(event -> {
            Memory.confimation = false;
            this.stage.close();
        });

        root.getChildren().addAll(confirmationLabel, confirmButton, cancelButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}
