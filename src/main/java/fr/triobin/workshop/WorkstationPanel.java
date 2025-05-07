package fr.triobin.workshop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WorkstationPanel extends CustomPanel {
    public WorkstationPanel() {
        super();
        // Espacements généraux
        this.setSpacing(20);
        this.setPadding(new Insets(40));
        this.setStyle("-fx-background-color: white;");

        // Grid 2 lignes × 3 colonnes
        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(30);

        // Créer les 5 cartes postes
        for (int i = 0; i < 5; i++) {
            StackPane poste = createPoste("Nom du poste");
            grid.add(poste, i % 3, i / 3);
        }

        // La carte “+” en sixième position
        StackPane addCard = createAddCard();
        grid.add(addCard, 2, 1);

        this.getChildren().add(grid);
    }

    private StackPane createPoste(String title) {
        StackPane pane = new StackPane();
        pane.setPrefSize(300, 200);
        pane.setStyle("-fx-background-color: #FFD68D; -fx-border-color: black;");

        Label label = new Label(title);
        label.setFont(Font.font(20));
        pane.getChildren().add(label);
        StackPane.setAlignment(label, Pos.CENTER);

        CustomCapacities.hoverCursorEffect(pane, Cursor.HAND);

        // Clique éventuel
        pane.setOnMouseClicked(e -> {
            System.out.println("Poste sélectionné : " + title);
            // TODO → navigation vers la vue détaillée du poste
        });

        return pane;
    }

    private StackPane createAddCard() {
        StackPane pane = new StackPane();
        pane.setPrefSize(300, 200);
        pane.setStyle("-fx-background-color: #FFD68D; -fx-border-color: black;");

        Label plus = new Label("+");
        plus.setFont(Font.font(60));
        pane.getChildren().add(plus);
        StackPane.setAlignment(plus, Pos.CENTER);

        CustomCapacities.hoverCursorEffect(pane, Cursor.HAND);

        pane.setOnMouseClicked(e -> {
            System.out.println("Créer un nouveau poste");
            // TODO → ouvrir la popup / nouvelle fenêtre de création
        });

        return pane;
    }

    @Override
    public void onload(Stage stage) {
        stage.setTitle("- Workstation");
    }
}
