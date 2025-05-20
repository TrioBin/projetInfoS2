package fr.triobin.workshop;

import java.util.Map;

import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomPanel;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.httpserver.HTTPServer;
import fr.triobin.workshop.map.MapStage;
import fr.triobin.workshop.panels.OperatorPanel;
import fr.triobin.workshop.panels.ProductPanel;
import fr.triobin.workshop.panels.WorkshopPanel;
import fr.triobin.workshop.panels.WorkstationPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainVueScene extends CustomScene {

    public MainVueScene() {
        super(new VBox(), 1000, 500);
        VBox root = (VBox) getRoot();

        HTTPServer.start();

        MapStage mapStage = new MapStage(this);

        // BARRE DU HAUT
        HBox topBar = new HBox(10);
        topBar.setPadding(new Insets(5, 10, 5, 10));
        topBar.setStyle("-fx-background-color: #E5E5E5;");
        topBar.setAlignment(Pos.CENTER_LEFT);

        // --- Menu "File" déroulant ---
        MenuButton fileMenu = new MenuButton("File");
        fileMenu.setFont(Font.font(14));
        fileMenu.getItems().addAll(
                new MenuItem("Sauvegarder sous..."),
                new SeparatorMenuItem(),
                new MenuItem("Exporter"),
                new MenuItem("Importer"));

        // Bouton retour
        Button backButton = new Button("<");
        backButton.setOnAction(e -> {
            App app = App.getInstance();
            mapStage.close();
            HTTPServer.stop();
            app.changeWindow(new SelectScene());
        });

        // Onglets
        ToggleGroup tabsGroup = new ToggleGroup();
        ToggleButton tab1 = new ToggleButton("Atelier");
        ToggleButton tab2 = new ToggleButton("Postes");
        ToggleButton tab3 = new ToggleButton("Opérateurs");
        ToggleButton tab4 = new ToggleButton("Produits");
        tab1.setToggleGroup(tabsGroup);
        tab2.setToggleGroup(tabsGroup);
        tab3.setToggleGroup(tabsGroup);
        tab4.setToggleGroup(tabsGroup);
        tab1.setSelected(true);

        // Spacer et placeholders rouges
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        // Bouton de fermeture
        Button closeButton = new Button("X");
        closeButton.setStyle("-fx-background-color: transparent; -fx-text-fill: red;");
        closeButton.setOnAction(e -> {
            App app = App.getInstance();
            mapStage.close();
            HTTPServer.stop();
            app.getStage().close();
        });
        CustomCapacities.hoverCursorEffect(closeButton, Cursor.HAND);

        topBar.getChildren().addAll(
                fileMenu, backButton,
                tab1, tab2, tab3, tab4,
                spacer,
                closeButton);

        // CONTENU PRINCIPAL... (inchangé)
        final CustomPanel[] mainContent = { new WorkshopPanel() };
        mainContent[0].onload(App.stage);

        tab1.setOnAction(e -> {
            // remplace mainContent par le panneau d'atelier
            mainContent[0] = new WorkshopPanel();
            mainContent[0].onload(App.stage);
            root.getChildren().set(1, mainContent[0]); // Update the displayed content
        });

        tab2.setOnAction(e -> {
            // remplace mainContent par le panneau de postes
            mainContent[0] = new WorkstationPanel();
            mainContent[0].onload(App.stage);
            root.getChildren().set(1, mainContent[0]); // Update the displayed content
        });

        tab3.setOnAction(e -> {
            // remplace mainContent par le panneau d'opérateurs
            mainContent[0] = new OperatorPanel();
            mainContent[0].onload(App.stage);
            root.getChildren().set(1, mainContent[0]); // Update the displayed content
        });

        tab4.setOnAction(e -> {
            // remplace mainContent par le panneau de produits
            mainContent[0] = new ProductPanel();
            mainContent[0].onload(App.stage);
            root.getChildren().set(1, mainContent[0]); // Update the displayed content
        });

        // ASSEMBLAGE GLOBAL
        root.getChildren().addAll(topBar, mainContent[0]);
    }

    public void changePanel(CustomPanel newPanel) {
        VBox root = (VBox) getRoot();
        newPanel.onload(App.stage);
        root.getChildren().set(1, newPanel);
    }

    @Override
    public void onload(Stage stage) {
        stage.initStyle(StageStyle.UNDECORATED);
        // get topBar
        HBox topBar = (HBox) ((VBox) getRoot()).getChildren().get(0);
        CustomCapacities.dragZoneEffect(stage, topBar);

        stage.getIcons().add(new Image(getClass().getResourceAsStream("/fr/triobin/workshop/icon.png")));
    }

    public void goToMachine() {
        VBox root = (VBox) getRoot();
        ((ToggleButton) ((HBox) root.getChildren().get(0)).getChildren().get(2)).setSelected(false);
        ((ToggleButton) ((HBox) root.getChildren().get(0)).getChildren().get(3)).setSelected(true);
        ((ToggleButton) ((HBox) root.getChildren().get(0)).getChildren().get(4)).setSelected(false);
        ((ToggleButton) ((HBox) root.getChildren().get(0)).getChildren().get(5)).setSelected(false);
        root.getChildren().set(1, new WorkstationPanel());
    }
}
