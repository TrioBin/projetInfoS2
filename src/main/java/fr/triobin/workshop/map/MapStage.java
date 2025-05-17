package fr.triobin.workshop.map;

import fr.triobin.workshop.MainVueScene;
import fr.triobin.workshop.customgui.CustomScene;
import javafx.stage.Stage;

public class MapStage {
    private static Stage instance;

    public MapStage(MainVueScene mainVue) {
        this.instance = new Stage();
        changeScene(instance, new MapScene(instance, mainVue));
    }

    public void changeScene(Stage stage, CustomScene scene) {
        stage.setScene(scene);
        scene.onload(stage);
        stage.show();
    }

    public void close() {
        instance.close();
    }
}
