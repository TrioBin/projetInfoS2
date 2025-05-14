package fr.triobin.workshop.popups;

import java.sql.Time;
import java.util.ArrayList;
import java.util.function.UnaryOperator;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.general.OPList;
import fr.triobin.workshop.general.Operation;
import fr.triobin.workshop.general.Product;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateProductPopup extends CustomScene {
    private Stage stage;

    public CreateProductPopup() {
        super(new VBox(), 500, 500);
        VBox root = (VBox) getRoot();

        root.setPrefSize(500, 500);

        TextField nameField = new TextField();
        nameField.setPromptText("Nom du produit");
        nameField.setStyle("-fx-background-color: white; -fx-text-fill: black;");

        // Create a button to add the operation

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> {
            String name = nameField.getText();

            // Validate the inputs
            if (name.isEmpty()) {
                // Show an error message
                System.out.println("Veuillez remplir tous les champs.");
                return;
            }

            // Add the operation to the workshop
            Memory.currentWorkshop.add(new Product(name, name, new OPList(new ArrayList<>())));

            // Close the popup
            stage.close();
        });
        
        // Add the fields and button to the root
        root.getChildren().addAll(nameField, addButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}