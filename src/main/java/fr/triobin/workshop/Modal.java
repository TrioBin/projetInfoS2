package fr.triobin.workshop;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class Modal extends Stage {
    public Modal(Stage owner, CustomScene scene) {
        super();
        this.initModality(Modality.APPLICATION_MODAL);
        this.initOwner(owner);
        this.setScene(scene);
        scene.onload(this);
        this.show();
    }
}
