package fr.triobin.workshop.panels;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomPanel;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.popups.CreateWorkstationPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WorkstationPanel extends CustomPanel {
    private Stage stage;

    public WorkstationPanel() {
        super();
        // Espacements généraux
        this.setSpacing(20);
        this.setStyle("-fx-background-color: white;");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPadding(new Insets(0, 40, 0, 40));

        // Grid 2 lignes × 3 colonnes
        GridPane grid = new GridPane();
        grid.setHgap(30);
        grid.setVgap(30);

        int i = 0;
        // Créer les 5 cartes postes
        for (i = 0; i < Memory.currentWorkshop.getWorkstations().size(); i++) {
            StackPane poste = createPoste(Memory.currentWorkshop.getWorkstations().get(i).getRefWorkstation());
            poste.setPrefSize(300, 100);
            grid.add(poste, i % 3, i / 3);
        }

        // La carte “+” en sixième position
        StackPane addCard = createAddCard();
        addCard.setPrefSize(300, 100);
        grid.add(addCard, i % 3, i / 3);

        scrollPane.setContent(grid);
        scrollPane.setFitToWidth(true);

        this.getChildren().add(scrollPane);
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
            Modal dialog = new Modal(this.stage, new CreateWorkstationPopup());
            dialog.onClose(o -> {
                // reload grid
                GridPane gridPane = (GridPane) ((ScrollPane) this.getChildren().get(0)).getContent();
                gridPane.getChildren().clear();
                int i = 0;
                for (i = 0; i < Memory.currentWorkshop.getWorkstations().size(); i++) {
                    StackPane poste = createPoste(Memory.currentWorkshop.getWorkstations().get(i).getRefWorkstation());
                    poste.setPrefSize(300, 100);
                    gridPane.add(poste, i % 3, i / 3);
                }
                StackPane addCard = createAddCard();
                addCard.setPrefSize(300, 100);
                gridPane.add(addCard, i % 3, i / 3);
            });
        });

        return pane;
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("- Workstation");
    }
}
