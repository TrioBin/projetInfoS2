package fr.triobin.workshop;

import fr.triobin.workshop.customgui.CustomScene;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class App extends Application {
    public static App instance;
    public static Stage stage;

    @Override
    public void start(Stage primaryStage) {
        //Memory.fakeLoad();
        Memory.workshops = FileManager.loadFile();

        CustomScene selectScene = new SelectScene();
        changeScene(primaryStage, selectScene);
        this.stage = primaryStage;
        this.instance = this;
    }

    public void changeWindow(CustomScene scene) {
        Stage newStage = new Stage();
        newStage.setScene(scene);
        scene.onload(newStage);
        newStage.show();
        this.stage.close();
        this.stage = newStage;
    }

    public void changeScene(Stage stage, CustomScene scene) {
        stage.setScene(scene);
        scene.onload(stage);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static App getInstance() {
        if (instance == null) {
            instance = new App();
        }
        return instance;
    }

    public static Stage getStage() {
        return stage;
    }
}
