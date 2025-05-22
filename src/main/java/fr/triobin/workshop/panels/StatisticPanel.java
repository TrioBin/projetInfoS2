package fr.triobin.workshop.panels;

import fr.triobin.workshop.Statistic;
import fr.triobin.workshop.customgui.CustomPanel;
import fr.triobin.workshop.general.BankEvent;
import fr.triobin.workshop.general.MachineEvent;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class StatisticPanel extends CustomPanel {
    private TableView<MachineEvent> table;

    public StatisticPanel() {
        super();
        this.setPadding(new Insets(40));
        this.setStyle("-fx-background-color: white;");
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(20));
        vbox.setSpacing(10);
    
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Machine", "Banque");
        comboBox.setValue("Machine");
        comboBox.setOnAction(event -> {
            String selected = comboBox.getValue();
            if (selected.equals("Machine")) {
                vbox.getChildren().clear();
                vbox.getChildren().addAll(comboBox, createMachineStatisticPanel());
            } else {
                vbox.getChildren().clear();
                vbox.getChildren().addAll(comboBox, createBankStatisticPanel());
            }
        });

        vbox.getChildren().addAll(comboBox, createMachineStatisticPanel());
        this.getChildren().add(vbox);
    }

    private BorderPane createMachineStatisticPanel() {
        // Récupération des données
        List<MachineEvent> events = Statistic.readMachineStatistics();
        ObservableList<MachineEvent> masterList = FXCollections.observableArrayList(events);

        // 1) FilteredList pour le search
        FilteredList<MachineEvent> filteredData = new FilteredList<>(masterList, e -> true);

        // 2) Création du champ de recherche
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher…");
        searchField.textProperty().addListener((obs, oldV, newV) -> {
            String lower = newV.toLowerCase().trim();
            filteredData.setPredicate(event -> {
                if (lower.isEmpty())
                    return true;
                // on teste tous les champs visibles
                if (event.getMachineName().toLowerCase().contains(lower))
                    return true;
                if (event.getRefMachine().getName().toLowerCase().contains(lower))
                    return true;
                if (event.getReason().toLowerCase().contains(lower))
                    return true;
                // date/heure sous forme textuelle
                String dt = event.getTimestamp()
                        .toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        .toLowerCase();
                return dt.contains(lower);
            });
        });

        HBox searchBox = new HBox(10, new Label("Recherche :"), searchField);
        searchBox.setPadding(new Insets(0, 0, 10, 0));

        // 3) TableView et colonnes (identique à votre code)
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<MachineEvent, Timestamp> colDate = new TableColumn<>("Date / Heure");
        colDate.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getTimestamp()));
        colDate.setCellFactory(tc -> new TableCell<>() {
            private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            @Override
            protected void updateItem(Timestamp ts, boolean empty) {
                super.updateItem(ts, empty);
                setText(empty || ts == null
                        ? null
                        : ts.toLocalDateTime().format(fmt));
            }
        });

        TableColumn<MachineEvent, String> colMachine = new TableColumn<>("Machine");
        colMachine.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getMachineName()));

        TableColumn<MachineEvent, String> colRef = new TableColumn<>("Réf. Machine");
        colRef.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getRefMachine().getName()));

        TableColumn<MachineEvent, String> colReason = new TableColumn<>("Raison");
        colReason.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getReason()));

        table.getColumns().addAll(colDate, colMachine, colRef, colReason);

        // 4) SortedList pour préserver le tri des colonnes
        SortedList<MachineEvent> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());

        // 5) On applique à la table
        table.setItems(sortedData);

        // 6) Montage dans un BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(searchBox);
        borderPane.setCenter(table);

        // 7) Prendre toute la largeur
        table.prefWidthProperty()
                .bind(this.widthProperty()
                        .subtract(getPadding().getLeft() + getPadding().getRight()));

        return borderPane;
    }

    private BorderPane createBankStatisticPanel() {
        // Récupération des données
        List<BankEvent> events = Statistic.readBankStatistics();
        ObservableList<BankEvent> masterList = FXCollections.observableArrayList(events);

        // 1) FilteredList pour le search
        FilteredList<BankEvent> filteredData = new FilteredList<>(masterList, e -> true);

        // 2) Création du champ de recherche
        TextField searchField = new TextField();
        searchField.setPromptText("Rechercher…");
        searchField.textProperty().addListener((obs, oldV, newV) -> {
            String lower = newV.toLowerCase().trim();
            filteredData.setPredicate(event -> {
                if (lower.isEmpty())
                    return true;
                // on teste tous les champs visibles
                if (event.getOperator().getName().toLowerCase().contains(lower))
                    return true;
                if (String.valueOf(event.getCost()).toLowerCase().contains(lower))
                    return true;
                if (event.getReason().toLowerCase().contains(lower))
                    return true;
                // date/heure sous forme textuelle
                String dt = event.getTimestamp()
                        .toLocalDateTime()
                        .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))
                        .toLowerCase();
                return dt.contains(lower);
            });
        });

        HBox searchBox = new HBox(10, new Label("Recherche :"), searchField);
        searchBox.setPadding(new Insets(0, 0, 10, 0));

        // 3) TableView et colonnes (identique à votre code)
        TableView<BankEvent> bankTable = new TableView<>();
        bankTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<BankEvent, Timestamp> colDate = new TableColumn<>("Date / Heure");
        colDate.setCellValueFactory(cell -> new SimpleObjectProperty<>(cell.getValue().getTimestamp()));
        colDate.setCellFactory(tc -> new TableCell<>() {
            private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            @Override
            protected void updateItem(Timestamp ts, boolean empty) {
                super.updateItem(ts, empty);
                setText(empty || ts == null
                        ? null
                        : ts.toLocalDateTime().format(fmt));
            }
        });

        TableColumn<BankEvent, String> colMachine = new TableColumn<>("Operateur");
        colMachine.setCellValueFactory(cell -> {
            if (cell.getValue() == null || cell.getValue().getOperator() == null) {
                return new SimpleStringProperty("null");
            } else {
                return new SimpleStringProperty(cell.getValue().getOperator().getCode());
            }
        });

        TableColumn<BankEvent, String> colRef = new TableColumn<>("Raison");
        colRef.setCellValueFactory(cell -> new SimpleStringProperty(cell.getValue().getReason()));

        TableColumn<BankEvent, String> colReason = new TableColumn<>("Cout");
        colReason.setCellValueFactory(cell -> new SimpleStringProperty(String.valueOf(cell.getValue().getCost())));

        bankTable.getColumns().addAll(colDate, colMachine, colRef, colReason);

        // 4) SortedList pour préserver le tri des colonnes
        SortedList<BankEvent> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(bankTable.comparatorProperty());

        // 5) On applique à la table
        bankTable.setItems(sortedData);

        // 6) Montage dans un BorderPane
        BorderPane borderPane = new BorderPane();
        borderPane.setTop(searchBox);
        borderPane.setCenter(bankTable);

        // 7) Prendre toute la largeur
        bankTable.prefWidthProperty()
                .bind(this.widthProperty()
                        .subtract(getPadding().getLeft() + getPadding().getRight()));

        return borderPane;
    }
}
