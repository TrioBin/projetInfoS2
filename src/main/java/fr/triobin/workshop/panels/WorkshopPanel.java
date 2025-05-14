package fr.triobin.workshop.panels;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomPanel;
import fr.triobin.workshop.general.GeneralGoal;
import fr.triobin.workshop.general.SpecializedGoal;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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

        ScrollPane objectifsContainer = new ScrollPane();
        objectifsContainer.setPrefSize(300, 400);
        objectifsContainer.setStyle("-fx-background-color: #D9D9D9; -fx-border-color: black;");
        VBox objectifsContent = new VBox();
        objectifsContent.setSpacing(10);
        objectifsContent.setPadding(new Insets(10));
        objectifsContainer.setContent(objectifsContent);

        Memory.currentWorkshop.getGoals().forEach(objectif -> {
            StackPane objectifPanel = new StackPane();
            objectifPanel.setPrefSize(300, 50);
            objectifPanel.setStyle("-fx-background-color: #D9D9D9; -fx-border-color: black;");
            objectifPanel.setAlignment(Pos.CENTER);
            objectifPanel.setPadding(new Insets(10));
            Label objectifLabel = new Label();
            if (objectif instanceof GeneralGoal) {
            objectifLabel = new Label(((GeneralGoal) objectif).getProduct().getName() + " " + ((GeneralGoal) objectif).getQuantity());
            } else if (objectif instanceof SpecializedGoal) {
            objectifLabel = new Label(((SpecializedGoal) objectif).getOperation().getName() + " " + ((SpecializedGoal) objectif).getProduct().getProduct().getName());
            } else {
                objectifLabel = new Label("Unknown goal type");
            }
                objectifsContent.getChildren().remove(objectifPanel);
            Button deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
            objectifsContent.getChildren().add(objectifPanel);
                objectifsContent.getChildren().remove(objectifPanel);
            });
            objectifPanel.getChildren().addAll(objectifLabel, deleteButton);
            objectifsContent.getChildren().add(objectifPanel);
        });

        this.getChildren().addAll(atelierPanel, objectifsContainer);
    }

    @Override
    public void onload(Stage stage) {
        stage.setTitle("- Workshop");
    }
}
