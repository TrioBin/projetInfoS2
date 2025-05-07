package fr.triobin.workshop;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainVueAvecMenuFile extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage primaryStage) {
        // Fenêtre sans bordures système
        primaryStage.initStyle(StageStyle.UNDECORATED);

        // BARRE DU HAUT
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(5, 10, 5, 10));
        topBar.setStyle("-fx-background-color: #E5E5E5;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        // Permettre de glisser la fenêtre
        topBar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        topBar.setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - xOffset);
            primaryStage.setY(event.getScreenY() - yOffset);
        });

        // --- Menu "File" déroulant ---
        MenuButton fileMenu = new MenuButton("File");
        fileMenu.setFont(Font.font(14));
        fileMenu.getItems().addAll(
            new MenuItem("Sauvegarder sous..."),
            new SeparatorMenuItem(),
            new MenuItem("Exporter"),
            new MenuItem("Importer")
        );

        // Bouton retour
        Button backButton = new Button("<");

        // Onglets
        ToggleGroup tabsGroup = new ToggleGroup();
        ToggleButton tab1 = new ToggleButton("Workshop");
        ToggleButton tab2 = new ToggleButton("Workstation");
        ToggleButton tab3 = new ToggleButton("Operator");
        tab1.setToggleGroup(tabsGroup);
        tab2.setToggleGroup(tabsGroup);
        tab3.setToggleGroup(tabsGroup);
        tab1.setSelected(true);

        // Spacer et placeholders rouges
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Region redBox1 = createRedBox();
        Region redBox2 = createRedBox();

        // Bouton de fermeture
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
        closeButton.setOnAction(e -> primaryStage.close());

        topBar.getChildren().addAll(
            fileMenu, backButton,
            tab1, tab2, tab3,
            spacer, redBox1, redBox2,
            closeButton
        );

        // CONTENU PRINCIPAL... (inchangé)
        HBox mainContent = new HBox(50);
        mainContent.setPadding(new Insets(40));
        mainContent.setStyle("-fx-background-color: white;");

        VBox atelierPanel = new VBox();
        atelierPanel.setPrefSize(300, 400);
        atelierPanel.setStyle("-fx-background-color: #D9D9D9; -fx-border-color: black;");
        atelierPanel.setAlignment(Pos.TOP_CENTER);
        atelierPanel.setPadding(new Insets(20));
        Label atelierLabel = new Label("Atelier 1 :");
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

        mainContent.getChildren().addAll(atelierPanel, objectifsContainer);

        // ASSEMBLAGE GLOBAL
        VBox root = new VBox(topBar, mainContent);
        Scene scene = new Scene(root, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("MainVueAvecMenuFile");
        primaryStage.show();
    }

    private Region createRedBox() {
        Region red = new Region();
        red.setPrefSize(20, 40);
        red.setStyle("-fx-background-color: red;");
        return red;
    }
}