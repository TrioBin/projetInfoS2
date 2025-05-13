package fr.triobin.workshop.panels;

import fr.triobin.workshop.App;
import fr.triobin.workshop.MainVueScene;
import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.general.Operator;
import fr.triobin.workshop.popups.AddSkillOperatorPopup;
import fr.triobin.workshop.popups.RemoveConfirmationPopup;
import fr.triobin.workshop.popups.RenameOperatorPopup;
import fr.triobin.workshop.popups.ResetPasswordPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class OperatorDetails extends VBox {
    private Operator operator;

    public OperatorDetails(Operator operator) {
        super(10);

        Memory.currentOperator = operator;

        this.setPadding(new Insets(10));
        this.setPrefHeight(500);
        this.setPrefWidth(700);

        // Titre
        Label title = new Label(operator.getName() + " " + operator.getSurname() + " - " + operator.getCode());
        title.setFont(Font.font(20));
        title.setMaxWidth(Double.MAX_VALUE);
        title.setStyle("-fx-alignment: center;-fx-background-color: #f4c16b;");
        this.getChildren().add(title);

        // Contenu principal : Status + Machine
        HBox content = new HBox(20);

        // ========== Partie gauche ==========
        VBox leftPane = new VBox(15);
        leftPane.setPrefWidth(350);

        // Status work station
        VBox statusBox = new VBox(10);
        statusBox.setStyle("-fx-background-color: #f4c16b;");
        Label statusLabel = new Label("Status de l'operateur");
        statusLabel.setStyle(
                "-fx-background-color: #dcdcdc; -fx-padding: 5; -fx-effect: dropshadow(gaussian, gray, 2, 0.5, 1, 1);");

        HBox statuses = new HBox(10);
        statuses.getChildren().addAll(
                createStatusBox(Color.LIGHTGREEN),
                createStatusBox(Color.ORANGE),
                createStatusBox(Color.RED));

        statusBox.getChildren().addAll(statusLabel, statuses);

        // Options
        VBox optionsBox = new VBox(5);
        optionsBox.setPrefWidth(200);
        optionsBox.setStyle("-fx-background-color: #f4c16b;");
        Label optionLabel = new Label("Option");
        optionLabel.setStyle(
                "-fx-background-color: #dcdcdc; -fx-padding: 5; -fx-effect: dropshadow(gaussian, gray, 2, 0.5, 1, 1);");

        Button renameBtn = new Button("Renommer");
        renameBtn.setStyle("-fx-background-color: #fff5dc;");

        renameBtn.setOnAction(event -> {
            // Create a modal
            Modal dialog = new Modal(App.getInstance().stage, new RenameOperatorPopup());
            dialog.onClose(closeEvent -> {
                ((MainVueScene) App.getStage().getScene()).changePanel(new OperatorPanel());
            });
        });

        Button deleteBtn = new Button("Supprimer");
        deleteBtn.setStyle("-fx-background-color: #f26c64;");

        deleteBtn.setOnAction(event -> {
            // Create a modal
            Modal dialog = new Modal(App.getInstance().stage, new RemoveConfirmationPopup());
            dialog.onClose(closeEvent -> {
                if (Memory.confimation) {
                    Memory.currentWorkshop.removeOperator(operator);
                    ((MainVueScene) App.getStage().getScene()).changePanel(new OperatorPanel());
                }
            });
        });

        Button resetBtn = new Button("Reinitialiser MDP");
        optionsBox.getChildren().addAll(optionLabel, renameBtn, deleteBtn, resetBtn);

        resetBtn.setOnAction(event -> {
            // Create a modal
            Modal dialog = new Modal(App.getInstance().stage, new ResetPasswordPopup());
        });

        // Coût horaire
        VBox costBox = new VBox();
        costBox.setPrefWidth(150);
        costBox.setStyle("-fx-background-color: #f4c16b;");
        Label costLabel = new Label("Coût horaire");
        costLabel.setStyle(
                "-fx-background-color: #dcdcdc; -fx-padding: 5; -fx-effect: dropshadow(gaussian, gray, 2, 0.5, 1, 1);");
        costBox.getChildren().add(costLabel);

        HBox OptBoxContent = new HBox(10);
        OptBoxContent.setPrefWidth(350);
        OptBoxContent.setAlignment(Pos.CENTER);

        OptBoxContent.getChildren().addAll(
                optionsBox, costBox);

        leftPane.getChildren().addAll(statusBox, OptBoxContent);

        // ========== Partie droite ==========
        VBox rightPane = new VBox(10);
        rightPane.setStyle("-fx-background-color: #f4c16b;");
        rightPane.setPrefWidth(350);
        Label machineActionLabel = new Label("Machine action");
        machineActionLabel.setStyle(
                "-fx-background-color: #dcdcdc; -fx-padding: 5; -fx-effect: dropshadow(gaussian, gray, 2, 0.5, 1, 1);");

        rightPane.getChildren().add(machineActionLabel);

        operator.getSkills().forEach(refMachine -> {
            HBox actionBox = new HBox(10);
            Text action = new Text(refMachine.getName());
            CustomCapacities.hoverStrikethroughEffect(action, actionBox);
            actionBox.setStyle("-fx-background-color: #fff5dc;");
            actionBox.setMaxWidth(Double.MAX_VALUE);
            actionBox.setAlignment(Pos.CENTER);
            actionBox.getChildren().add(action);
            rightPane.getChildren().add(actionBox);

            actionBox.setOnMouseClicked(e -> {
                operator.removeSkill(refMachine);
                rightPane.getChildren().remove(actionBox);
            });
        });

        Button addAction = new Button("+");
        addAction.setMaxWidth(Double.MAX_VALUE);

        addAction.setOnAction(event -> {
            // Create a modal
            Modal dialog = new Modal(App.getInstance().stage, new AddSkillOperatorPopup());
            dialog.onClose(closeEvent -> {
                rightPane.getChildren().clear();
                rightPane.getChildren().add(machineActionLabel);
                operator.getSkills().forEach(refMachine -> {
                    HBox actionBox = new HBox(10);
                    Text action = new Text(refMachine.getName());
                    CustomCapacities.hoverStrikethroughEffect(action, actionBox);
                    actionBox.setStyle("-fx-background-color: #fff5dc;");
                    actionBox.setMaxWidth(Double.MAX_VALUE);
                    actionBox.setAlignment(Pos.CENTER);
                    actionBox.getChildren().add(action);
                    rightPane.getChildren().add(actionBox);

                    actionBox.setOnMouseClicked(e -> {
                        operator.removeSkill(refMachine);
                        rightPane.getChildren().remove(actionBox);
                    });
                });
                rightPane.getChildren().add(addAction);
            });
        });

        rightPane.getChildren().add(addAction);

        content.getChildren().addAll(leftPane, rightPane);

        this.getChildren().add(content);
    }

    private StackPane createStatusBox(Color color) {
        StackPane pane = new StackPane();
        pane.setPrefSize(50, 80);
        pane.setStyle("-fx-border-color: black; -fx-border-width: 3;");
        pane.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));
        return pane;
    }
}
