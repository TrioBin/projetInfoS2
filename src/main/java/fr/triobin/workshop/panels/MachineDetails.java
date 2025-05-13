package fr.triobin.workshop.panels;

import fr.triobin.workshop.general.Machine;
import javafx.scene.layout.VBox;

public class MachineDetails extends VBox {
    public MachineDetails(Machine machine) {
        super(10);
        this.setStyle("-fx-background-color: white;");
        this.setSpacing(0);
    }
}
