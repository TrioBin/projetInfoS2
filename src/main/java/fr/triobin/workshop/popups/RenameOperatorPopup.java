package fr.triobin.workshop.popups;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.general.Operator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RenameOperatorPopup extends CustomScene {
        private Stage stage;

        public RenameOperatorPopup() {
                super(new VBox(), 500, 500);
                VBox root = (VBox) getRoot();
                
                // Input field for the new name
                TextField nameField = new TextField();
                nameField.setPromptText("Enter new reference name");

                // Enter action
                nameField.setOnAction(event -> {
                        String newName = nameField.getText();
                        if (!newName.isEmpty()) {
                                // Logic to rename the operator
                                Operator operator = Memory.currentOperator;
                                operator.setCode(newName);
                                stage.close();
                        }
                });

                HBox buttonContainer = new HBox(10);
                buttonContainer.setAlignment(Pos.CENTER);
                buttonContainer.setPadding(new Insets(10));

                // Button to confirm the renaming
                Button renameButton = new Button("Rename");
                renameButton.setOnAction(event -> {
                        String newName = nameField.getText();
                        if (!newName.isEmpty()) {
                                // Logic to rename the operator
                                Operator operator = Memory.currentOperator;
                                operator.setCode(newName);
                                stage.close();
                        }
                });
                // Button to cancel the renaming
                Button cancelButton = new Button("Cancel");
                cancelButton.setOnAction(event -> stage.close());

                buttonContainer.getChildren().addAll(renameButton, cancelButton);

                // Add components to the root layout
                root.getChildren().addAll(nameField, buttonContainer);
                root.setStyle("-fx-padding: 20; -fx-spacing: 10;");
        }

        @Override
        public void onload(Stage stage) {
                this.stage = stage;
                this.stage.setTitle("Renommer l'opérateur");
        }
}
