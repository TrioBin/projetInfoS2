package fr.triobin.workshop.popups;

import java.nio.Buffer;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.CustomTextField;
import fr.triobin.workshop.general.Position;
import javafx.geometry.Dimension2D;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ChangeWorkstationPosition extends CustomScene {
    private Stage stage;

    public ChangeWorkstationPosition() {
        super(new VBox(), 300, 400);
        VBox root = (VBox) this.getRoot();

        root.setStyle("-fx-background-color: white;");
        root.setSpacing(10);

        HBox positionBox = new HBox();

        CustomTextField xField = new CustomTextField();
        xField.setText(String.valueOf(Memory.currentWorkstation.getPosition().x));
        CustomCapacities.forceFloatTextFieldEffect(xField);

        CustomTextField yField = new CustomTextField();
        yField.setText(String.valueOf(Memory.currentWorkstation.getPosition().y));
        CustomCapacities.forceFloatTextFieldEffect(yField);
        positionBox.getChildren().addAll(new Label("Position : "), xField, yField);

        HBox dimensionBox = new HBox();

        CustomTextField wField = new CustomTextField();
        wField.setText(String.valueOf(Memory.currentWorkstation.getDimension().getWidth()));
        CustomCapacities.forceFloatTextFieldEffect(wField);

        CustomTextField hField = new CustomTextField();
        hField.setText(String.valueOf(Memory.currentWorkstation.getDimension().getHeight()));
        CustomCapacities.forceFloatTextFieldEffect(hField);
        dimensionBox.getChildren().addAll(new Label("Dimension : "), wField, hField);

        // Button to confirm the change
        Button confirmButton = new Button("Confirmer");
        confirmButton.setOnAction(e -> {
            try {
                float x = Float.parseFloat(xField.getText());
                float y = Float.parseFloat(yField.getText());
                float w = Float.parseFloat(wField.getText());
                float h = Float.parseFloat(hField.getText());

                Memory.currentWorkstation.modify(new Position(x, y));
                Memory.currentWorkstation.modify(new Dimension2D(h, w));

                Memory.saveFile();
                stage.close();
            } catch (NumberFormatException ex) {
                System.out.println("Erreur : " + ex.getMessage());
            }
        });

        root.getChildren().addAll(positionBox, dimensionBox, confirmButton);
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("Changer la position");
    }
}
