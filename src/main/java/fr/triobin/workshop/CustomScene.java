package fr.triobin.workshop;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CustomScene extends Scene {
    public CustomScene(Node scrollPane, int i, int j) {
        super((Parent) scrollPane, (double) i, (double) j);
    }

    public void onload(Stage stage) {
        // Override this method in subclasses to add custom behavior on load
    }
}
