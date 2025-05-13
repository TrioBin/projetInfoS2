package fr.triobin.workshop.panels;

import javafx.scene.layout.VBox;

public class ProductDetails extends VBox {
    public ProductDetails(Object product) {
        super();
        this.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
        this.setPrefSize(700, 500);
        this.setSpacing(10);

    }
}
