package fr.triobin.workshop.popups;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.CustomTextField;
import fr.triobin.workshop.general.GeneralGoal;
import fr.triobin.workshop.general.Product;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateGeneralGoal extends CustomScene {
    private Stage stage;

    public CreateGeneralGoal() {
        super(new VBox(), 300, 400);
        VBox vbox = (VBox) this.getRoot();

        Label titleLabel = new Label("Produit :");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");

        ComboBox<String> productComboBox = new ComboBox<>();
        Memory.currentWorkshop.getProducts().forEach(product -> productComboBox.getItems().add(product.getName()));
        productComboBox.getSelectionModel().selectFirst();

        CustomTextField quantityField = new CustomTextField();
        quantityField.setPromptText("Quantité");

        // Bouton pour ajouter l'objectif
        Button addButton = new Button("Ajouter");
        addButton.setOnAction(event -> {
            Product productName = Memory.currentWorkshop.getProducts()
                    .get(productComboBox.getSelectionModel().getSelectedIndex());
            String quantityText = quantityField.getText();

            // Valider les entrées
            if (quantityText.isEmpty()) {
                // Afficher un message d'erreur
                System.out.println("Veuillez remplir tous les champs.");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                // Ajouter l'objectif général à l'atelier
                Memory.currentWorkshop.add(new GeneralGoal(productName, quantity));
                stage.close();
            } catch (NumberFormatException e) {
                // Afficher un message d'erreur
                System.out.println("La quantité doit être un nombre entier.");
            }
        });

        // Ajouter les éléments à la VBox
        vbox.getChildren().addAll(titleLabel, productComboBox, quantityField, addButton);
        vbox.setSpacing(10);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("Ajouter un objectif général");
    }
}