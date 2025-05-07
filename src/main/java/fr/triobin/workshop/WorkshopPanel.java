package fr.triobin.workshop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WorkshopPanel extends CustomPanel {
    public WorkshopPanel() {
        super();
        this.setPadding(new Insets(40));
        this.setStyle("-fx-background-color: white;");

        VBox atelierPanel = new VBox();
        atelierPanel.setPrefSize(300, 400);
        atelierPanel.setStyle("-fx-background-color: #D9D9D9; -fx-border-color: black;");
        atelierPanel.setAlignment(Pos.TOP_CENTER);
        atelierPanel.setPadding(new Insets(20));
        Label atelierLabel = new Label(Memory.currentWorkshop.getDesignation() + " :");
        atelierLabel.setFont(Font.font(24));
        atelierPanel.getChildren().add(atelierLabel);

        StackPane objectifsContainer = new StackPane();
        VBox objectifsPanel = new VBox(15);
        objectifsPanel.setPadding(new Insets(20));
        objectifsPanel.setStyle("-fx-background-color: #D9D9D9; -fx-border-color: black;");
        objectifsPanel.setPrefSize(400, 400);
        Label objectifsLabel = new Label("Objectifs :");
        objectifsLabel.setFont(Font.font(24));
        objectifsPanel.getChildren().add(objectifsLabel);
        for (int i = 0; i < 5; i++) {
            Region objectifCard = new Region();
            objectifCard.setPrefSize(300, 40);
            objectifCard.setStyle("-fx-background-color: #FFD68D; -fx-border-radius: 4px;");
            objectifsPanel.getChildren().add(objectifCard);
        }
        Button addButton = new Button("+");
        addButton.setStyle("-fx-background-color: #999999; -fx-text-fill: white; -fx-font-size: 20;");
        addButton.setPrefSize(40, 40);
        StackPane.setAlignment(addButton, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(addButton, new Insets(0, 10, 10, 0));
        objectifsContainer.getChildren().addAll(objectifsPanel, addButton);

        this.getChildren().addAll(atelierPanel, objectifsContainer);
    }

    @Override
    public void onload(Stage stage) {
        stage.setTitle("- Workshop");
    }
}
