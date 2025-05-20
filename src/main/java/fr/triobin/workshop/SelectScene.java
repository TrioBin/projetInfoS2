package fr.triobin.workshop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.function.Consumer;

import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.general.Workshop;
import fr.triobin.workshop.popups.CreateWorkshopPopup;

public class SelectScene extends CustomScene {
    Stage stage;

    public SelectScene() {
        super(new ScrollPane(), 1000, 500);

        // ScrollPane horizontal
        ScrollPane scrollPane = (ScrollPane) getRoot();
        HBox atelierContainer = new HBox(30);
        atelierContainer.setPadding(new Insets(30));
        atelierContainer.setStyle("-fx-background-color: white;");
        atelierContainer.setAlignment(Pos.CENTER_LEFT);

        Memory.workshops.forEach(workshop -> {
            atelierContainer.getChildren().add(createAtelier(workshop.getDesignation()));
        });

        // Ajouter les ateliers avec callback
        atelierContainer.getChildren().add(createAddCard());

        scrollPane.setContent(atelierContainer);
        scrollPane.setFitToHeight(true);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setPannable(true);
    }

    private VBox createAtelier(String title) {
        VBox box = new VBox(10);
        box.setPrefSize(300, 400);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-background-color: #FFD68D; -fx-border-color: black;");

        CustomCapacities.hoverCursorEffect(box, Cursor.HAND);

        Workshop workshop = Memory.workshops.stream()
                .filter(w -> w.getDesignation().equals(title))
                .findFirst()
                .orElse(null);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font(24));
        Label machines = new Label("... machines");
        Label postes = new Label(workshop.getWorkstations().size()+" postes");
        Label employes = new Label(workshop.getOperators().size()+" employés");
        box.getChildren().addAll(titleLabel, machines, postes, employes);

        // === Interaction au clic ===
        box.setOnMouseClicked((MouseEvent e) -> {
            // Effet visuel (optionnel)
            box.setStyle("-fx-background-color: #FFEB9C; -fx-border-color: black;");

            Memory.currentWorkshop = Memory.workshops.stream()
                    .filter(w -> w.getDesignation().equals(title))
                    .findFirst()
                    .orElse(null);

            // get App instance and change window
            App app = App.getInstance();
            app.changeWindow(new MainVueScene());
        });

        return box;
    }

    private StackPane createAddCard() {
        StackPane addCard = new StackPane();
        addCard.setPrefSize(300, 400);
        addCard.setStyle("-fx-background-color: #D9D9D9; -fx-border-color: black;");
        Label plusLabel = new Label("+");
        plusLabel.setFont(Font.font(60));
        addCard.getChildren().add(plusLabel);

        CustomCapacities.hoverCursorEffect(addCard, Cursor.HAND);

        // Ici aussi tu peux ajouter un clic pour créer dynamiquement un atelier
        addCard.setOnMouseClicked((MouseEvent e) -> {
            // Effet visuel (optionnel)
            addCard.setStyle("-fx-background-color: #BFBFBF; -fx-border-color: black;");

            // Action de création d'atelier
            Modal dialog = new Modal(this.stage, new CreateWorkshopPopup());
            dialog.onClose(o -> {
                // Clear the scrollPane content and re-add all workshops
                HBox atelierContainer = (HBox) ((ScrollPane) getRoot()).getContent();
                atelierContainer.getChildren().clear();
                Memory.workshops.forEach(workshop -> {
                    atelierContainer.getChildren().add(createAtelier(workshop.getDesignation()));
                });
                atelierContainer.getChildren().add(createAddCard());
            });
        });

        return addCard;
    }

    public void onload(Stage stage) {
        stage.setTitle("Sélectionnez un atelier");
        this.stage = stage;
        stage.setOnCloseRequest(e -> {
            System.exit(0);
        });
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/fr/triobin/workshop/icon.png")));
    }
}