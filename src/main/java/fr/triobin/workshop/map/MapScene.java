package fr.triobin.workshop.map;

import java.util.Timer;
import java.util.TimerTask;

import fr.triobin.workshop.MainVueScene;
import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.general.Position;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;

public class MapScene extends CustomScene {
    private static Stage stage;

    // Variables pour le pan (déplacement global)
    private double panMouseStartX;
    private double panMouseStartY;
    private double panTransStartX;
    private double panTransStartY;

    // Transformations globales (pan + zoom)
    private final Translate translate = new Translate();
    private final Scale scale = new Scale(1, 1, 0, 0);

    public MapScene(Stage stage, MainVueScene mainVue) {
        super(new Pane(), 800, 600);
        Pane root = (Pane) getRoot();
        MapScene.stage = stage;

        Timer timer = new Timer();

        // Groupe contenant la carte, sur lequel on applique pan & zoom
        Group mapGroup = new Group();
        mapGroup.getTransforms().addAll(translate, scale);
        root.getChildren().add(mapGroup);

        // Fond transparent pour capter tous les événements de la scène
        root.setPrefSize(800, 600);
        root.setStyle("-fx-background-color: transparent;");

        // Ajout des postes de travail
        Memory.currentWorkshop.getWorkstations().forEach(workstation -> {
            Rectangle r = new Rectangle(
                    workstation.getPosition().x,
                    workstation.getPosition().y,
                    workstation.getDimension().getWidth(),
                    workstation.getDimension().getHeight()
                    );
            r.setFill(Color.GREEN);

            Text label = new Text(workstation.getDworkstation());
            label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            label.setFill(Color.WHITE);
            // Positionner au centre initial
            label.setX(r.getX() + r.getWidth() / 2 - label.getLayoutBounds().getWidth() / 2);
            label.setY(r.getY() + r.getHeight() / 2 + label.getLayoutBounds().getHeight() / 4);

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // Mettre à jour la position du rectangle
                    Position pos = workstation.getPosition();
                    r.setX(pos.x);
                    r.setY(pos.y);
                    // Mettre à jour la dimension du rectangle
                    r.setWidth(workstation.getDimension().getWidth());
                    r.setHeight(workstation.getDimension().getHeight());
                    // Mettre à jour la position du label
                    label.setX(r.getX() + r.getWidth() / 2 - label.getLayoutBounds().getWidth() / 2);
                    label.setY(r.getY() + r.getHeight() / 2 + label.getLayoutBounds().getHeight() / 4);
                }
            }, 0, 2000);

            // Clic simple : affichage
            r.setOnMouseClicked(evt -> {
                if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 1) {
                    System.out.println("Workstation sélectionnée : " + workstation.getDworkstation());
                    evt.consume();
                }
            });

            // Drag individuel
            final double[] dragMouseStart = new double[2];
            final double[] dragObjStart = new double[2];
            r.setOnMousePressed(evt -> {
                if (evt.getButton() == MouseButton.PRIMARY) {
                    dragMouseStart[0] = evt.getX();
                    dragMouseStart[1] = evt.getY();
                    dragObjStart[0] = r.getX();
                    dragObjStart[1] = r.getY();
                    evt.consume();
                }
            });
            r.setOnMouseDragged(evt -> {
                if (evt.getButton() == MouseButton.PRIMARY) {
                    double dx = evt.getX() - dragMouseStart[0];
                    double dy = evt.getY() - dragMouseStart[1];
                    double x = dragObjStart[0] + dx;
                    double y = dragObjStart[1] + dy;
                    r.setX(x);
                    r.setY(y);
                    label.setX(x + r.getWidth() / 2 - label.getLayoutBounds().getWidth() / 2);
                    label.setY(y + r.getHeight() / 2 + label.getLayoutBounds().getHeight() / 4);
                    // mise à jour du modèle
                    workstation.modify(new Position((int) x, (int) y));
                    evt.consume();
                }
            });

            mapGroup.getChildren().addAll(r, label);
        });

        // Ajout des machines
        Memory.currentWorkshop.getMachines().forEach(machine -> {
            Rectangle r = new Rectangle(
                    machine.getPosition().x,
                    machine.getPosition().y,
                    machine.getDimension().getWidth(),
                    machine.getDimension().getHeight()
                    );
            r.setFill(Color.BLUE);

            Text label = new Text(machine.getName());
            label.setFont(Font.font("Arial", FontWeight.BOLD, 12));
            label.setFill(Color.WHITE);
            label.setX(r.getX() + r.getWidth() / 2 - label.getLayoutBounds().getWidth() / 2);
            label.setY(r.getY() + r.getHeight() / 2 + label.getLayoutBounds().getHeight() / 4);

            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    // Mettre à jour la position du rectangle
                    Position pos = machine.getPosition();
                    r.setX(pos.x);
                    r.setY(pos.y);
                    // Mettre à jour la dimension du rectangle
                    r.setWidth(machine.getDimension().getWidth());
                    r.setHeight(machine.getDimension().getHeight());
                    // Mettre à jour la position du label
                    label.setX(r.getX() + r.getWidth() / 2 - label.getLayoutBounds().getWidth() / 2);
                    label.setY(r.getY() + r.getHeight() / 2 + label.getLayoutBounds().getHeight() / 4);
                }
            }, 0, 2000);

            // Clic simple : affichage
            r.setOnMouseClicked(evt -> {
                if (evt.getButton() == MouseButton.PRIMARY && evt.getClickCount() == 1) {
                    System.out.println("Machine sélectionnée : " + machine.getName());
                    evt.consume();
                }
            });

            // Drag individuel
            final double[] dragOffset = new double[2];
            final double[] objStart = new double[2];
            r.setOnMousePressed(evt -> {
                if (evt.getButton() == MouseButton.PRIMARY) {
                    dragOffset[0] = evt.getX();
                    dragOffset[1] = evt.getY();
                    objStart[0] = r.getX();
                    objStart[1] = r.getY();
                    evt.consume();
                }
            });
            r.setOnMouseDragged(evt -> {
                if (evt.getButton() == MouseButton.PRIMARY) {
                    double dx = evt.getX() - dragOffset[0];
                    double dy = evt.getY() - dragOffset[1];
                    double x = objStart[0] + dx;
                    double y = objStart[1] + dy;
                    r.setX(x);
                    r.setY(y);
                    label.setX(x + r.getWidth() / 2 - label.getLayoutBounds().getWidth() / 2);
                    label.setY(y + r.getHeight() / 2 + label.getLayoutBounds().getHeight() / 4);
                    machine.setPosition(new Position((int) x, (int) y));
                    evt.consume();
                }
            });

            mapGroup.getChildren().addAll(r, label);
        });
    }

    @Override
    public void onload(Stage stage) {
        super.onload(stage);
        Scene scene = stage.getScene();

        // PAN global via clic molette
        scene.setOnMousePressed(evt -> {
            if (evt.getButton() == MouseButton.MIDDLE) {
                panMouseStartX = evt.getX();
                panMouseStartY = evt.getY();
                panTransStartX = translate.getX();
                panTransStartY = translate.getY();
                evt.consume();
            }
        });
        scene.setOnMouseDragged(evt -> {
            if (evt.isMiddleButtonDown()) {
                double deltaX = (evt.getX() - panMouseStartX) / scale.getX();
                double deltaY = (evt.getY() - panMouseStartY) / scale.getY();
                translate.setX(panTransStartX + deltaX);
                translate.setY(panTransStartY + deltaY);
                evt.consume();
            }
        });

        // ZOOM via molette
        scene.setOnScroll((ScrollEvent evt) -> {
            double factor = evt.getDeltaY() > 0 ? 1.05 : 0.95;
            double newScale = scale.getX() * factor;
            if (newScale >= 0.1 && newScale <= 10) {
                scale.setX(newScale);
                scale.setY(newScale);
            }
            evt.consume();
        });

        stage.setTitle("Map Scene");
    }
}