package fr.triobin.workshop.popups;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class InClipboardConfirm extends CustomScene {
    private Stage stage;
    
    public InClipboardConfirm() {
        super(new VBox(), 300, 200);
        Memory.confimation = false;
        VBox root = (VBox) getRoot();
        root.setStyle("-fx-padding: 20; -fx-spacing: 10;");

        Label confirmationLabel = new Label("Le code de l'atelier a été copié dans le presse-papier.");
        Button confirmButton = new Button("Ok");

        confirmButton.setOnAction(event -> {
            this.stage.close();
        });

        root.getChildren().addAll(confirmationLabel, confirmButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}