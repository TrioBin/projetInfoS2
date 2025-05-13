package fr.triobin.workshop.popups;

import java.util.ArrayList;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomScene;
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

                TextField reference = new TextField();
                reference.setPromptText("Référence");
                reference.setPrefWidth(250);
                // style pour fond gris clair et bords arrondis
                reference.setStyle(
                                "-fx-background-color: #e0e0e0;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

                Label labelIdentity = new Label("Identité :");
                labelIdentity.setStyle("-fx-font-size: 16px; -fx-text-fill: black;");
                labelIdentity.setPadding(new Insets(10, 0, 0, 0));

                TextField name = new TextField();
                name.setPromptText("Nom");
                name.setPrefWidth(250);
                // style pour fond gris clair et bords arrondis
                name.setStyle(
                                "-fx-background-color: #e0e0e0;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

                TextField surname = new TextField();
                surname.setPromptText("Prénom");
                surname.setPrefWidth(250);
                // style pour fond gris clair et bords arrondis
                surname.setStyle(
                                "-fx-background-color: #e0e0e0;" +
                                                "-fx-background-radius: 5;" +
                                                "-fx-prompt-text-fill: derive(-fx-control-inner-background, -30%);");

                HBox position = new HBox(10);
                position.getChildren().addAll(name, surname);

                TextField password = new TextField();
                password.setPromptText("Password");
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
                        System.out.println("Poste créé : " + reference.getText());
                        Memory.currentWorkshop.add(
                                        new Operator(reference.getText(), name.getText(), surname.getText(),
                                                        new ArrayList<>(), OperatorStatus.LIBRE,
                                                        password.getText()));
                        this.stage.close();
                });

                HBox btnContainer = new HBox();
                btnContainer.setPadding(new Insets(20, 0, 0, 0));
                btnContainer.setAlignment(javafx.geometry.Pos.TOP_RIGHT);
                btnContainer.getChildren().add(btnCreer);

                root.getChildren().addAll(reference, labelIdentity, position, passwordContainer, btnContainer);
                root.setPadding(new Insets(20));
        }

        @Override
        public void onload(Stage stage) {
                this.stage = stage;
                stage.setTitle("Ajouter un opérateur");
        }
}
