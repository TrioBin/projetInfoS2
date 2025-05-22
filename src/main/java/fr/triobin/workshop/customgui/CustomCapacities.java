package fr.triobin.workshop.customgui;

import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

public class CustomCapacities {
    private static double xOffset = 0;
    private static double yOffset = 0;

    public static void hoverCursorEffect(Node element, Cursor cursor) {
        element.setOnMouseEntered(e -> element.setCursor(cursor));
        element.setOnMouseExited(e -> element.setCursor(Cursor.DEFAULT));
    }

    public static void hoverStrikethroughEffect(Node element) {
        element.setOnMouseEntered(e -> element.setStyle("-fx-strikethrough: true;"));
        element.setOnMouseExited(e -> element.setStyle("-fx-strikethrough: false;"));
    }

    public static void hoverStrikethroughEffect(Node element, Node hoverElement) {
        hoverElement.setOnMouseEntered(e -> element.setStyle("-fx-strikethrough: true;"));
        hoverElement.setOnMouseExited(e -> element.setStyle("-fx-strikethrough: false;"));
    }

    public static void forceFloatTextFieldEffect(TextField element, Boolean allowNegative) {
        element.setTextFormatter(new TextFormatter<>(change -> {
            String newText = change.getControlNewText();
            String regex = allowNegative ? "[-]?\\d*\\.?\\d*" : "\\d*\\.?\\d*";
            if (newText.matches(regex)) {
                return change;
            }
            return null;
        }));
    }

    public static void forceFloatTextFieldEffect(TextField element) {
        forceFloatTextFieldEffect(element, false);
    }

    public static void showElementWhenHoverEffect(Node element, Node hoverElement) {
        element.setVisible(false);
        hoverElement.setOnMouseEntered(e -> element.setVisible(true));
        hoverElement.setOnMouseExited(e -> element.setVisible(false));
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
