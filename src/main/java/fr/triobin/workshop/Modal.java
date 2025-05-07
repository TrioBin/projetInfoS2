package fr.triobin.workshop;

import java.util.function.Consumer;

import javafx.stage.Modality;
import javafx.stage.Stage;

public class Modal {
    private Stage stage;

    public Modal(Stage owner, CustomScene scene) {
        this.stage = new Stage();
        this.stage.initModality(Modality.APPLICATION_MODAL);
        this.stage.initOwner(owner);
        this.stage.setScene(scene);
        scene.onload(this.stage);
        this.stage.show();
    }
    
    public void onClose(Consumer<Object> callback) {
        this.stage.setOnCloseRequest(e -> {
            callback.accept(null);
            this.stage.close();
        });
    }
}
