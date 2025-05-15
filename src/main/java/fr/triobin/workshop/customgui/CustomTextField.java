package fr.triobin.workshop.customgui;

import javafx.scene.control.TextField;

public class CustomTextField extends TextField {
    private static final String[] BannedSequence = { ",", ";", "[", "]", "__________"};

    public CustomTextField() {
        super();
        this.textProperty().addListener((observable, oldValue, newValue) -> {
            for (String sequence : BannedSequence) {
                if (newValue.contains(sequence)) {
                    setText(oldValue);
                    break;
                }
            }
            
        });
    }
}
