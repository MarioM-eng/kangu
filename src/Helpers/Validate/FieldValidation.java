package Helpers.Validate;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class FieldValidation {

    private TextInputControl tF;
    private Label lb;

    public FieldValidation(){}

    public FieldValidation(TextInputControl tF, Label lb){
        this.tF = tF;
        this.lb = lb;
    }

    public FieldValidation withTf(TextInputControl tF){
        this.tF = tF;
        return this;
    }

    public FieldValidation withLb(Label lb){
        this.lb = lb;
        return this;
    }

    public TextInputControl getTF() {
        return tF;
    }

    public void setTF(TextInputControl tF) {
        this.tF = tF;
    }

    public Label getLb() {
        return lb;
    }

    public void setLb(Label lb) {
        this.lb = lb;
    }

    
    
}
