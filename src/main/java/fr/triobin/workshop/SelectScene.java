package fr.triobin.workshop;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.general.Workshop;
import fr.triobin.workshop.popups.CreateWorkshopPopup;
import fr.triobin.workshop.popups.ImportPopup;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

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

    private StackPane createAtelier(String title) {
        VBox box = new VBox(10);
        box.setPrefSize(300, 400);
        box.setPadding(new Insets(20));
        box.setAlignment(Pos.TOP_CENTER);
        box.setStyle("-fx-background-color: #FFD68D; -fx-border-color: black;");
        CustomCapacities.hoverCursorEffect(box, Cursor.HAND);

        // Trouver l'objet Workshop correspondant
        Workshop workshop = Memory.workshops.stream()
                .filter(w -> w.getDesignation().equals(title))
                .findFirst()
                .orElse(null);

        // ----- Contenu principal -----
        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font(24));
        Label postes = new Label(workshop.getWorkstations().size() + " postes");
        Label machines = new Label(workshop.getMachines().size() + " machines");
        Label employes = new Label(workshop.getOperators().size() + " employés");
        Label money = new Label(workshop.getBank() + " €");
        box.getChildren().addAll(titleLabel, postes, machines, employes, money);

        // ----- Bouton Supprimer -----
        Button deleteBtn = new Button("❌");
        deleteBtn.setFont(Font.font(14));
        deleteBtn.setTextFill(Color.DIMGRAY);
        deleteBtn.setBackground(new Background(new BackgroundFill(
                Color.WHITE, new CornerRadii(6), Insets.EMPTY)));
        deleteBtn.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY, BorderStrokeStyle.SOLID, new CornerRadii(6), BorderWidths.DEFAULT)));
        deleteBtn.setPadding(new Insets(4));
        deleteBtn.setEffect(new DropShadow(3, Color.gray(0.4)));
        StackPane deleteWrapper = new StackPane(deleteBtn);
        deleteWrapper.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
        StackPane.setAlignment(deleteWrapper, Pos.TOP_RIGHT);
        StackPane.setMargin(deleteWrapper, new Insets(8));
        CustomCapacities.hoverCursorEffect(deleteBtn, Cursor.HAND);

        // On consomme l'événement pour ne pas déclencher le clic sur la carte
        deleteBtn.setOnAction(evt -> {
            evt.consume();
            // Supprimer de la mémoire
            Memory.workshops.remove(workshop);
            Memory.saveFile();
            // Rafraîchir la vue : on part du ScrollPane
            HBox container = (HBox) ((ScrollPane) getRoot()).getContent();
            container.getChildren().clear();
            Memory.workshops.forEach(w -> container.getChildren().add(createAtelier(w.getDesignation())));
            // et on réajoute la carte “+”
            container.getChildren().add(createAddCard());
        });

        // ----- Clic principal pour ouvrir l'atelier -----
        box.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            // si on n’a pas déjà consommé (via deleteBtn)
            if (!e.isConsumed()) {
                box.setStyle("-fx-background-color: #FFEB9C; -fx-border-color: black;");
                Memory.currentWorkshop = workshop;
                App.getInstance().changeWindow(new MainVueScene());
            }
        });

        // Comme on veut superposer le deleteBtn au VBox, on wrappe le tout dans un
        // StackPane
        StackPane wrapper = new StackPane(box);
        wrapper.getChildren().add(deleteWrapper);

        return wrapper;
    }

    private StackPane createAddCard() {
        // Container principal
        StackPane addCard = new StackPane();
        addCard.setPrefSize(300, 400);
        addCard.setPadding(new Insets(10));
        addCard.setStyle(
                "-fx-background-color: linear-gradient(to bottom right, #E8E8E8, #FFFFFF);" +
                        "-fx-background-radius: 12;" +
                        "-fx-border-radius: 12;" +
                        "-fx-border-color: #CCCCCC;");
        CustomCapacities.hoverCursorEffect(addCard, Cursor.HAND);

        // Le gros "+"
        Label plusLabel = new Label("+");
        plusLabel.setFont(Font.font(72));
        plusLabel.setTextFill(Color.GRAY);

        // Bouton "Importer" circulaire
        Button importBtn = new Button("Importer");
        importBtn.setFont(Font.font(16));
        importBtn.setTextFill(Color.DIMGRAY);

        // On met un padding interne pour que le texte ne touche pas les bords
        importBtn.setPadding(new Insets(6, 14, 6, 14));

        // Coins arrondis via le BackgroundFill
        importBtn.setBackground(new Background(new BackgroundFill(
                Color.WHITE,
                new CornerRadii(8), // rayon des coins
                Insets.EMPTY)));

        // Bord gris aux mêmes coins arrondis
        importBtn.setBorder(new Border(new BorderStroke(
                Color.LIGHTGRAY,
                BorderStrokeStyle.SOLID,
                new CornerRadii(8),
                new BorderWidths(1))));

        // Petite ombre pour le relief
        importBtn.setEffect(new DropShadow(4, Color.gray(0.4)));

        // Positionnement en haut à droite de la StackPane
        StackPane.setAlignment(importBtn, Pos.TOP_RIGHT);
        StackPane.setMargin(importBtn, new Insets(8));
        CustomCapacities.hoverCursorEffect(importBtn, Cursor.HAND);

        // Handler
        importBtn.setOnAction(evt -> {
            evt.consume();
            Stage owner = App.getInstance().getStage(); // votre fenêtre principale
            Modal dialog = new Modal(owner, new ImportPopup());
            dialog.onClose(o -> {
                refreshAteliers();
            });
        });

        // Clic principal pour créer l'atelier
        addCard.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            // si le clic n'est pas sur importBtn
            if (!e.isConsumed()) {
                // léger feedback visuel
                addCard.setStyle(
                        "-fx-background-color: linear-gradient(to bottom right, #D0D0D0, #F8F8F8);" +
                                "-fx-background-radius: 12;" +
                                "-fx-border-radius: 12;" +
                                "-fx-border-color: #BBBBBB;");
                // ta popup
                Modal dialog = new Modal(this.stage, new CreateWorkshopPopup());
                dialog.onClose(o -> {
                    HBox atelierContainer = (HBox) ((ScrollPane) getRoot()).getContent();
                    atelierContainer.getChildren().clear();
                    Memory.workshops.forEach(
                            workshop -> atelierContainer.getChildren().add(createAtelier(workshop.getDesignation())));
                    atelierContainer.getChildren().add(createAddCard());
                });
            }
        });

        addCard.getChildren().addAll(plusLabel, importBtn);
        return addCard;
    }

    private void refreshAteliers() {
        ScrollPane scroll = (ScrollPane) getRoot();
        HBox container = (HBox) scroll.getContent();
        container.getChildren().clear();
        Memory.workshops.forEach(w -> container.getChildren().add(createAtelier(w.getDesignation())));
        container.getChildren().add(createAddCard());
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