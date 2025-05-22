package fr.triobin.workshop.popups;

import java.util.ArrayList;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomScene;
import fr.triobin.workshop.customgui.CustomTextField;
import fr.triobin.workshop.general.Cost;
import fr.triobin.workshop.general.Operator;
import fr.triobin.workshop.general.Operator.OperatorStatus;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CreateOperatorPopup extends CustomScene {
        private Stage stage;

        public CreateOperatorPopup() {
                super(new VBox(), 500, 500);
                VBox root = (VBox) getRoot();

                CustomTextField reference = new CustomTextField();
                reference.setPromptText("Code :");
                reference.setPrefWidth(250);
                // style pour fond gris clair et bords arrondis
                reference.setStyle(
                                "-fx-background-color: #e0e0e0;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

                Label labelIdentity = new Label("Identité :");
                labelIdentity.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
                labelIdentity.setPadding(new Insets(10, 0, 0, 0));

                CustomTextField name = new CustomTextField();
                name.setPromptText("Nom");
                name.setPrefWidth(250);
                // style pour fond gris clair et bords arrondis
                name.setStyle(
                                "-fx-background-color: #e0e0e0;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

                CustomTextField surname = new CustomTextField();
                surname.setPromptText("Prénom");
                surname.setPrefWidth(250);
                // style pour fond gris clair et bords arrondis
                surname.setStyle(
                                "-fx-background-color: #e0e0e0;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

                HBox position = new HBox(10);
                position.getChildren().addAll(name, surname);

                CustomTextField password = new CustomTextField();
                password.setPromptText("Mot de passe");
                password.setPrefWidth(250);
                // style pour fond gris clair et bords arrondis
                password.setStyle(
                                "-fx-background-color: #e0e0e0;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

                HBox passwordContainer = new HBox();
                passwordContainer.getChildren().addAll(password);
                passwordContainer.setPadding(new Insets(10, 0, 0, 0));
                passwordContainer.setPrefWidth(250);

                CustomTextField costField = new CustomTextField();
                costField.setPromptText("Coût / heure");
                costField.setPrefWidth(250);
                // style pour fond gris clair et bords arrondis
                costField.setStyle(
                                "-fx-background-color: #e0e0e0;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");
                CustomCapacities.forceFloatTextFieldEffect(costField);

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
                        Memory.currentWorkshop.add(
                                        new Operator(reference.getText(), name.getText(), surname.getText(),
                                                        new ArrayList<>(), OperatorStatus.LIBRE,
                                                        password.getText(), new Cost(Float.parseFloat(costField.getText()))));
                        this.stage.close();
                });

                HBox btnContainer = new HBox();
                btnContainer.setPadding(new Insets(20, 0, 0, 0));
                btnContainer.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
                btnContainer.getChildren().add(btnCreer);

                root.getChildren().addAll(reference, labelIdentity, position, passwordContainer, costField, btnContainer);
                root.setPadding(new Insets(20));
        }

        @Override
        public void onload(Stage stage) {
                this.stage = stage;
                stage.setTitle("Ajouter un opérateur");
        }
}
