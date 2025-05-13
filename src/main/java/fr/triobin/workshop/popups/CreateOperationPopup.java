package fr.triobin.workshop.popups;

import java.sql.Time;
import java.util.function.UnaryOperator;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.general.Operation;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateOperationPopup extends CustomScene {
    private Stage stage;

    public CreateOperationPopup() {
        super(new VBox(), 500, 500);
        VBox root = (VBox) getRoot();

        root.setPrefSize(500, 500);

        TextField nameField = new TextField();
        nameField.setPromptText("Nom de l'opération");
        nameField.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        TextField timeField = new TextField();
        timeField.setPromptText("Temps de l'opération (hh:mm:ss)");
        timeField.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String newText = change.getControlNewText();
            if (newText.matches("^\\d{0,2}(:\\d{0,2}){0,2}$")) {
                return change;
            }
            return null;
        };

        timeField.setTextFormatter(new TextFormatter<>(filter));

        // Create a button to add the operation

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> {
            String name = nameField.getText();
            String time = timeField.getText();

            // Validate the inputs
            if (name.isEmpty() || time.isEmpty()) {
                // Show an error message
                System.out.println("Veuillez remplir tous les champs.");
                return;
            }

            // Add the operation to the workshop
            Memory.currentWorkshop.addOperation(new Operation(name, Time.valueOf(time)));

            // Close the popup
            stage.close();
        });
        
        // Add the fields and button to the root
        root.getChildren().addAll(nameField, timeField, addButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}
