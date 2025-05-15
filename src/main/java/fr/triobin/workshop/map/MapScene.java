package fr.triobin.workshop.map;

import fr.triobin.workshop.customgui.CustomScene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class MapScene extends CustomScene {
    public MapScene(Stage stage) {
        super(new Pane(), 800, 600);
        Pane root = (Pane) getRoot();

        Line mur = new Line(100, 100, 300, 100);
        mur.setStrokeWidth(3);

        Rectangle piece = new Rectangle(150, 150, 200, 150);
        piece.setFill(Color.LIGHTGRAY);
        piece.setStroke(Color.BLACK);

        root.getChildren().addAll(mur, piece);
    }

    @Override
    public void onload(Stage stage) {
        stage.setTitle("Map Scene");
    }
}
