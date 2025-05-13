package fr.triobin.workshop;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class CreateSkillPopup extends CustomScene {
    private Stage stage;

    public CreateSkillPopup() {
        super(new VBox(), 500, 500);
        VBox root = (VBox) getRoot();

        // Add your UI components here
        Label title = new Label("Ajouter une compétence");
        title.setFont(Font.font(20));
        root.getChildren().add(title);
        
        // Add a TextField for the skill name
        TextField skillNameField = new TextField();
        skillNameField.setPromptText("Nom de la compétence");

        root.getChildren().add(skillNameField);
        // Add a Button to confirm the addition of the skill
        Button addButton = new Button("Ajouter");

        addButton.setOnAction(event -> {
            String skillName = skillNameField.getText();
            if (!skillName.isEmpty()) {
                Memory.currentWorkshop.addMachineRef(new RefMachine(skillName));
                this.stage.close();
            } else {
                // Show an error message
                System.out.println("Veuillez entrer un nom de compétence valide.");
            }
        });

        root.getChildren().add(addButton);

        root.setPadding(new Insets(20));
    }

    @Override
    public void onload(Stage stage) {
        this.stage = stage;
        stage.setTitle("Ajouter une compétence");
    }
    
}
