package fr.triobin.workshop.popups;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ResetPasswordPopup extends CustomScene {
    private Stage stage;

    public ResetPasswordPopup() {
        super(new VBox(), 300, 200);
        VBox root = (VBox) getRoot();
        root.setStyle("-fx-padding: 20; -fx-spacing: 10;");

        // Create a input field for the new password
        Label label = new Label("Entrez le nouveau mot de passe :");

        TextField passwordField = new TextField();
        passwordField.setPromptText("Nouveau mot de passe");
        passwordField.setPrefWidth(200);
        passwordField.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                String newPassword = passwordField.getText();
                // Logic to reset the password
                // For example, call a method to update the password in the database
                Memory.currentOperator.setPassword(newPassword);
                this.stage.close();
            }
        });

        // Create a button to confirm the password reset
        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(event -> {
            String newPassword = passwordField.getText();
            // Logic to reset the password
            // For example, call a method to update the password in the database
            Memory.currentOperator.setPassword(newPassword);
            this.stage.close();
        });

        // Create a button to cancel the password reset

        Button cancelButton = new Button("Annuler");
        cancelButton.setOnAction(event -> {
            this.stage.close();
        });
        
        // Add all components to the root layout
        root.getChildren().addAll(label, passwordField, confirmButton, cancelButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
    
}
