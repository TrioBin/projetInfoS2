package fr.triobin.workshop.panels;

import fr.triobin.workshop.App;
import fr.triobin.workshop.Memory;
import fr.triobin.workshop.customgui.Modal;
import fr.triobin.workshop.popups.CreateOperationPopup;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProductDetails extends StackPane {

    private final ObservableList<String> operations;

    public ProductDetails(Object product) {
        super();

        VBox content = new VBox();
        content.setStyle("-fx-background-color: lightgray; -fx-border-color: black;");
        content.setPrefSize(700, 500);
        content.setSpacing(10);
        content.setPadding(new Insets(10));

        operations = FXCollections.observableArrayList("Opération A", "Opération B", "Opération C");
        ListView<String> listView = new ListView<>(operations);
        listView.setPrefHeight(400);

        ComboBox<String> inputComboBox = new ComboBox<>();
        Memory.currentWorkshop.getOperations().forEach(op -> inputComboBox.getItems().add(op.getName()));
        inputComboBox.getItems().add("+");

        inputComboBox.setOnAction(e -> {
            String selectedOp = inputComboBox.getValue();
            if ("+".equals(selectedOp)) {
                Modal dialog = new Modal(App.getStage(), new CreateOperationPopup());
                dialog.onClose(event -> {
                    inputComboBox.getItems().clear();
                    Memory.currentWorkshop.getOperations().forEach(op -> inputComboBox.getItems().add(op.getName()));
                    inputComboBox.getItems().add("+");
                    if (inputComboBox.getItems().size() > 1) {
                        inputComboBox.getSelectionModel().select(inputComboBox.getItems().size() - 2);
                    }
                });
            }
        });

        Button addButton = new Button("Ajouter");
        addButton.setOnAction(e -> {
            String selectedOp = inputComboBox.getValue();
            if (selectedOp != null && !"+".equals(selectedOp)) {
                operations.add(selectedOp);
                inputComboBox.getSelectionModel().clearSelection();
            }
        });

        HBox controls = new HBox(10, inputComboBox, addButton);
        controls.setPadding(new Insets(0, 0, 10, 0));

        content.getChildren().addAll(controls, listView);

        // 🔥 Zone corbeille en position absolue
        Label deleteZone = new Label("🗑");
        deleteZone.setAlignment(Pos.CENTER);
        deleteZone.setVisible(false);
        deleteZone.setMouseTransparent(true); // ne gêne pas les clics par défaut
        deleteZone.setLayoutX(650); // position X en pixels
        deleteZone.setLayoutY(430); // position Y en pixels
        deleteZone.setMaxSize(50, 50);
        deleteZone.setMinSize(50, 50);
        deleteZone.setStyle(
                "-fx-background-color: #ffdddd; -fx-border-color: red; -fx-border-width: 2px; -fx-font-size: 20px; -fx-text-alignment: center; -fx-alignment: center;");
        this.getChildren().addAll(content, deleteZone); // StackPane permet de superposer

        deleteZone.setOnDragOver(event -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
                deleteZone.setCursor(Cursor.HAND);
            }
            event.consume();
        });

        deleteZone.setOnDragExited(event -> {
            deleteZone.setCursor(Cursor.DEFAULT);
        });

        enableDragAndDrop(listView, deleteZone);
    }

    private String draggedItem = null;

    private void enableDragAndDrop(ListView<String> listView, Label deleteZone) {
        listView.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty || item == null ? null : item);
                }
            };

            cell.setOnDragDetected(event -> {
                if (!cell.isEmpty()) {
                    draggedItem = cell.getItem();
                    Dragboard db = cell.startDragAndDrop(TransferMode.MOVE);
                    ClipboardContent content = new ClipboardContent();
                    content.putString(draggedItem);
                    db.setContent(content);
                    deleteZone.setVisible(true);
                    deleteZone.setMouseTransparent(false);
                    event.consume();
                }
            });

            cell.setOnDragDone(event -> {
                deleteZone.setVisible(false);
                deleteZone.setMouseTransparent(true);
                draggedItem = null;
            });

            cell.setOnDragOver(event -> {
                if (event.getGestureSource() != cell &&
                        event.getDragboard().hasString()) {
                    event.acceptTransferModes(TransferMode.MOVE);
                }
                event.consume();
            });

            cell.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    int draggedIdx = listView.getItems().indexOf(db.getString());
                    int thisIdx = cell.getIndex();

                    if (draggedIdx != thisIdx) {
                        String dragged = listView.getItems().remove(draggedIdx);
                        if (thisIdx > draggedIdx)
                            thisIdx--;
                        listView.getItems().add(thisIdx, dragged);
                        listView.getSelectionModel().select(thisIdx);
                    }
                    event.setDropCompleted(true);
                    event.consume();
                }
            });

            return cell;
        });

        deleteZone.setOnDragOver(event -> {
            if (event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
                deleteZone.setStyle("-fx-background-color: red; -fx-text-fill: white;");
                deleteZone.setCursor(Cursor.HAND);
            }
            event.consume();
        });

        deleteZone.setOnDragExited(event -> {
            deleteZone.setStyle("-fx-background-color: #ffdddd; -fx-border-color: red;");
            deleteZone.setCursor(Cursor.DEFAULT);
        });

        deleteZone.setOnDragDropped(event -> {
            if (draggedItem != null) {
                listView.getItems().remove(draggedItem);
            }
            event.setDropCompleted(true);
            draggedItem = null;
            deleteZone.setVisible(false);
            deleteZone.setMouseTransparent(true);
            event.consume();
        });
    }
}
