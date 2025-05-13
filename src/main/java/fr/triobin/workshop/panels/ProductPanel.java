package fr.triobin.workshop.panels;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomPanel;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.popups.CreateOperatorPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class ProductPanel extends CustomPanel {
    private Stage stage;

    public ProductPanel() {
        super();
        this.setSpacing(0);
        this.setPadding(new Insets(0));
        this.setStyle("-fx-background-color: white;");

        // Partie droite (grande zone grise)
        Pane rightPane = new Pane();
        rightPane.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        // Partie gauche (zone orangée)
        VBox leftPane = new VBox(10);
        leftPane.setPadding(new Insets(20));
        leftPane.setPrefWidth(300);
        leftPane.setStyle("-fx-background-color: #facc7c; -fx-border-color: #2196f3; -fx-border-width: 2px;");

        Label titleButton = new Label("Produits");
        titleButton.setMaxWidth(Double.MAX_VALUE);
        leftPane.getChildren().add(titleButton);

        Memory.currentWorkshop.getProducts().forEach(product -> {
            Button productButton = new Button(product.getName());
            productButton.setMaxWidth(Double.MAX_VALUE);
            leftPane.getChildren().add(productButton);

            productButton.setOnAction(innerEvent -> {
                rightPane.getChildren().clear();
                rightPane.getChildren().add(new ProductDetails(product));
            });
        });
        Button addButton = new Button("+");
        addButton.setMaxWidth(Double.MAX_VALUE);

        addButton.setOnAction(event -> {
            // Create a modal
            Modal dialog = new Modal(this.stage, new CreateOperatorPopup());
            dialog.onClose(closeEvent -> {
                ((VBox) this.getChildren().get(0)).getChildren().clear();
                ((VBox) this.getChildren().get(0)).getChildren().add(titleButton);
                Memory.currentWorkshop.getProducts().forEach(product -> {
                    Button productButton = new Button(product.getName());
                    productButton.setMaxWidth(Double.MAX_VALUE);
                    leftPane.getChildren().add(productButton);

                    productButton.setOnAction(productEvent -> {
                        rightPane.getChildren().clear();
                        rightPane.getChildren().add(new ProductDetails(product));
                    });
                });
                ((VBox) this.getChildren().get(0)).getChildren().add(addButton);
            });
        });

        leftPane.getChildren().add(addButton);

        this.getChildren().addAll(leftPane, rightPane);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}