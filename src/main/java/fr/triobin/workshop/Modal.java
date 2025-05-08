package fr.triobin.workshop;

import java.util.function.Consumer;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class Modal {
    private Stage stage;
    private Stage owner;

    public Modal(Stage owner, CustomScene scene) {
        this.owner = owner;
        owner.setOpacity(0.8);
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(owner);
        this.stage.setScene(scene);
        scene.onload(this.stage);
        this.stage.show();
    }
    
    public void onClose(Consumer<Object> callback) {
        this.stage.setOnHidden(e -> {
            this.owner.setOpacity(1);
            callback.accept(null);
        });
    }
}
