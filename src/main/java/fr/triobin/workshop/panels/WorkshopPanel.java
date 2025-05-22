package fr.triobin.workshop.panels;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomPanel;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.general.GeneralGoal;
import fr.triobin.workshop.general.SpecializedGoal;
import fr.triobin.workshop.popups.AddInBankPopup;
import fr.triobin.workshop.popups.CreateGeneralGoal;
import fr.triobin.workshop.popups.RenameWorkshopPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WorkshopPanel extends CustomPanel {
    private Stage stage;

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

        Label bankLabel = new Label("Argent : " + Math.round(Memory.currentWorkshop.getBank() * 100f) / 100f + " €");
        bankLabel.setFont(Font.font(18));
        Button addInBankButton = new Button("+");
        addInBankButton.setOnAction(event -> {
            Modal dialog = new Modal(this.stage, new AddInBankPopup());
            dialog.onClose((obj) -> {
                bankLabel.setText("Argent : " + Math.round(Memory.currentWorkshop.getBank() * 100f) / 100f + " €");
            });
        });
        HBox bankContainer = new HBox();
        bankContainer.setAlignment(Pos.CENTER);
        bankContainer.setSpacing(10);
        bankContainer.getChildren().addAll(bankLabel, addInBankButton);

        Button renameButton = new Button("Renommer");
        renameButton.setOnAction(event -> {
            Modal dialog = new Modal(this.stage, new RenameWorkshopPopup());
            dialog.onClose((obj) -> {
                atelierLabel.setText(Memory.currentWorkshop.getDesignation() + " :");
            });
        });

        atelierPanel.getChildren().addAll(atelierLabel, bankContainer, renameButton);

        ScrollPane objectifsContainer = new ScrollPane();
        objectifsContainer.setPrefSize(500, 400);
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
                objectifLabel = new Label(
                        ((GeneralGoal) objectif).getProduct().getName() + " " + ((GeneralGoal) objectif).getQuantity());
            } else if (objectif instanceof SpecializedGoal) {
                objectifLabel = new Label(((SpecializedGoal) objectif).getOperation().getName() + " "
                        + ((SpecializedGoal) objectif).getProduct().getProduct().getName());
            } else {
                objectifLabel = new Label("Type d'objectif inconnu");
            }
            if (Memory.currentWorkshop.getActualGoals().contains(objectif)) {
                objectifLabel.setStyle("-fx-text-fill: green;");
            } else {
                objectifLabel.setStyle("-fx-text-fill: red;");
            }
            objectifsContent.getChildren().remove(objectifPanel);
            Button deleteButton = new Button("Supprimer");
            deleteButton.setOnAction(event -> {
                if (objectif instanceof GeneralGoal) {
                    Memory.currentWorkshop.removeGoal((GeneralGoal) objectif);
                } else if (objectif instanceof SpecializedGoal) {
                    Memory.currentWorkshop.removeActualGoal((SpecializedGoal) objectif);
                }
                objectifsContent.getChildren().remove(objectifPanel);
            });
            objectifPanel.getChildren().addAll(objectifLabel, deleteButton);
            objectifsContent.getChildren().add(objectifPanel);
            CustomCapacities.showElementWhenHoverEffect(deleteButton, objectifPanel);
        });

        Button addButton = new Button("Ajouter un objectif");
        addButton.setOnAction(event -> {
            Modal dialog = new Modal(this.stage, new CreateGeneralGoal());
            dialog.onClose((obj) -> {
            });
        });
        objectifsContent.getChildren().add(addButton);

        this.getChildren().addAll(atelierPanel, objectifsContainer);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("- Workshop");
    }
}
