package fr.triobin.workshop.panels;

import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.CustomCapacities;
import fr.triobin.workshop.customgui.CustomPanel;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.popups.CreateWorkstationPopup;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WorkstationPanel extends CustomPanel {
    private Stage stage;
    private VBox leftPane;
    private Pane rightPane;
    private Label titleLabel;
    private Button addButton;

    public WorkstationPanel() {
        super();
        this.setSpacing(0);
        this.setPadding(Insets.EMPTY);
        this.setStyle("-fx-background-color: white;");

        // initialisation des panneaux
        rightPane = new Pane();
        rightPane.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
        HBox.setHgrow(rightPane, Priority.ALWAYS);

        leftPane = new VBox(10);
        leftPane.setPadding(new Insets(20));
        leftPane.setPrefWidth(300);
        leftPane.setStyle(
            "-fx-background-color: #facc7c; " +
            "-fx-border-color: #2196f3; " +
            "-fx-border-width: 2px;");

        // Titre et bouton d'ajout
        titleLabel = new Label("Postes de travail");
        titleLabel.setFont(Font.font(18));
        titleLabel.setMaxWidth(Double.MAX_VALUE);

        addButton = new Button("+");
        addButton.setMaxWidth(Double.MAX_VALUE);
        addButton.setFont(Font.font(18));
        CustomCapacities.hoverCursorEffect(addButton, Cursor.HAND);
        addButton.setOnAction(evt -> openCreatePopup());

        // construction initiale du volet gauche
        rebuildLeftPane();

        this.getChildren().addAll(leftPane, rightPane);
    }

    private void rebuildLeftPane() {
        leftPane.getChildren().clear();
        leftPane.getChildren().add(titleLabel);

        for (var workstation : Memory.currentWorkshop.getWorkstations()) {
            // bouton principal
            Button wsBtn = new Button(workstation.getRefWorkstation());
            wsBtn.setMaxWidth(Double.MAX_VALUE);
            HBox.setHgrow(wsBtn, Priority.ALWAYS);
            wsBtn.setOnAction(e -> {
                WorkstationDetail workstationDetail = new WorkstationDetail(workstation);
                rightPane.getChildren().setAll(workstationDetail);
                workstationDetail.onload(stage);
            });

            // bouton supprimer
            Button delBtn = new Button("✖");
            delBtn.setFont(Font.font(8));
            delBtn.setPrefSize(24, 24);
            delBtn.setBackground(new Background(
                new BackgroundFill(Color.WHITE, new CornerRadii(4), Insets.EMPTY)));
            delBtn.setBorder(new Border(
                new BorderStroke(Color.LIGHTGRAY, BorderStrokeStyle.SOLID,
                                 new CornerRadii(4), BorderWidths.DEFAULT)));
            delBtn.setEffect(new DropShadow(3, Color.gray(0.4)));
            delBtn.setOnAction(evt -> {
                evt.consume();
                Memory.currentWorkshop.getWorkstations().remove(workstation);
                rebuildLeftPane();
                rightPane.getChildren().clear();
            });

            HBox row = new HBox(5, wsBtn, delBtn);
            row.setAlignment(Pos.CENTER_LEFT);
            leftPane.getChildren().add(row);
        }

        leftPane.getChildren().add(addButton);
    }

    private void openCreatePopup() {
        Modal dialog = new Modal(stage, new CreateWorkstationPopup());
        dialog.onClose(o -> {
            rebuildLeftPane();
        });
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
    }
}
