package fr.triobin.workshop.panels;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomPanel;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.general.Product;
import fr.triobin.workshop.popups.CreateProductPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.List;

public class ProductPanel extends CustomPanel {
    private Stage stage;
    private VBox leftPane;
    private Pane rightPane;
    private Label titleLabel;
    private Button addButton;
    private List<Product> products;

    public ProductPanel() {
        super();
        this.setSpacing(0);
        this.setPadding(Insets.EMPTY);
        this.setStyle("-fx-background-color: white;");

        // Zone de détail à droite
        rightPane = new Pane();
        rightPane.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        // Zone liste produits à gauche
        leftPane = new VBox(10);
        leftPane.setPadding(new Insets(20));
        leftPane.setPrefWidth(300);
        leftPane.setStyle(
                "-fx-background-color: #facc7c; " +
                        "-fx-border-color: #2196f3; " +
                        "-fx-border-width: 2px;");

        titleLabel = new Label("Produits");
        titleLabel.setFont(Font.font(18));
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        addButton = new Button("+");
        addButton.setFont(Font.font(18));
        addButton.setMaxWidth(Double.MAX_VALUE);
        CustomCapacities.hoverCursorEffect(addButton, Cursor.HAND);
        addButton.setOnAction(e -> openCreateProductPopup());

        products = Memory.currentWorkshop.getProducts();
        buildLeftPane();

        this.getChildren().addAll(leftPane, rightPane);
    }

    private void buildLeftPane() {
        leftPane.getChildren().clear();
        leftPane.getChildren().add(titleLabel);

        for (Product product : products) {
            // bouton principal
            Button prodBtn = new Button(product.getName());
            prodBtn.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(prodBtn, Priority.ALWAYS);
            prodBtn.setOnAction(e -> {
                rightPane.getChildren().setAll(new ProductDetails(product));
            });

            // bouton supprimer
            Button delBtn = new Button("✖");
            delBtn.setFont(Font.font(8));
            delBtn.setPrefSize(24, 24);
            delBtn.setBackground(new Background(
                    new BackgroundFill(Color.WHITE, new CornerRadii(6), Insets.EMPTY)));
            delBtn.setBorder(new Border(
                    new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
                            new CornerRadii(6), BorderWidths.DEFAULT)));
            delBtn.setEffect(new DropShadow(4, Color.gray(0.4)));
            delBtn.setOnAction(evt -> {
                evt.consume();
                // Supprime le produit
                Memory.currentWorkshop.getProducts().remove(product);
                buildLeftPane();
                rightPane.getChildren().clear();
            });

            HBox row = new HBox(5, prodBtn, delBtn);
            row.setAlignment(Pos.CENTER_LEFT);
            leftPane.getChildren().add(row);
        }

        leftPane.getChildren().add(addButton);
    }

    private void openCreateProductPopup() {
        Modal dialog = new Modal(stage, new CreateProductPopup());
        dialog.onClose(o -> {
            // Recharge la liste depuis FileManager
            products = Memory.currentWorkshop.getProducts();
            buildLeftPane();
        });
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}