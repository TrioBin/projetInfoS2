package fr.triobin.workshop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.function.Consumer;

public class SelectScene extends CustomScene {

    public SelectScene() {
        super(new ScrollPane(), 1000, 500);

        // ScrollPane horizontal
        ScrollPane scrollPane = (ScrollPane) getRoot();
        HBox atelierContainer = new HBox(30);
        atelierContainer.setPadding(new Insets(30));
        atelierContainer.setStyle("-fx-background-color: white;");
        atelierContainer.setAlignment(Pos.CENTER_LEFT);

        // Ajouter les ateliers avec callback
        atelierContainer.getChildren().addAll(
            createAtelier("Atelier 1"),
            createAtelier("Atelier 2"),
            createAddCard()
        );

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

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font(24));
        Label machines = new Label("... machines");
        Label postes   = new Label("... postes");
        Label employes = new Label("... employés");
        box.getChildren().addAll(titleLabel, machines, postes, employes);

        // === Interaction au clic ===
        box.setOnMouseClicked((MouseEvent e) -> {
            // Effet visuel (optionnel)
            box.setStyle("-fx-background-color: #FFEB9C; -fx-border-color: black;");
            
            System.out.println("Atelier sélectionné : " + title);

            // get App instance and change window
            App app = App.getInstance();
            app.changeWindow(new MainVue());
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
            System.out.println("Créer un nouvel atelier");
        });

        return addCard;
    }

    public void onload(Stage stage) {
        stage.setTitle("Sélectionnez un atelier");
    }
}