package fr.triobin.workshop;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.stage.Stage;

public class CustomCapacities {
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void hoverCursorEffect(Node element, Cursor cursor) {
        element.setOnMouseEntered(e -> element.setCursor(cursor));
        element.setOnMouseExited(e -> element.setCursor(Cursor.DEFAULT));
    }

    public static void dragZoneEffect(Stage stage, Node element) {
        element.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        element.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }
}
