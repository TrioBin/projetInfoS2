package fr.triobin.workshop.popups;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.CustomTextField;
import fr.triobin.workshop.general.Workshop;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class CreateWorkshopPopup extends CustomScene {
    private Stage stage;

    public CreateWorkshopPopup() {
        super(new HBox(), 500, 500);
        HBox root = (HBox) getRoot();

        CustomTextField nomAtelier = new CustomTextField();
        nomAtelier.setPromptText("Nom de l’atelier");
        nomAtelier.setPrefWidth(250);
        // style pour fond gris clair et bords arrondis
        nomAtelier.setStyle(
                "-fx-background-color: #e0e0e0;" +
                        "-fx-background-radius: 5;" +
                        "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

        // Bouton « Créer »
        Button btnCreer = new Button("Créer");
        btnCreer.setPrefWidth(100);
        // style orange et coins arrondis
        btnCreer.setStyle(
                "-fx-background-color: #ffb000;" +
                        "-fx-background-radius: 5;" +
                        "-fx-font-size: 14px;" +
                        "-fx-text-fill: black;");
        btnCreer.setOnAction(e -> {
            Memory.workshops.add(new Workshop(nomAtelier.getText(), 0f));
            Memory.saveFile();
            this.stage.close();
        });

        root.getChildren().addAll(nomAtelier, btnCreer);
        root.setPadding(new Insets(20));
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("Créer un atelier");
    }
}
