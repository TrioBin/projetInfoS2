package fr.triobin.workshop;

import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CustomPanel extends HBox {
    public CustomPanel() {
        super(50);
    }

    public void onload(Stage stage) {
        // Override this method in subclasses to add custom behavior on load
    }
}
