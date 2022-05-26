package Helpers.Validate;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FieldValidation {

    private TextField tF;
    private Label lb;

    public FieldValidation(){}

    public FieldValidation withTf(TextField tF){
        this.tF = tF;
        return this;
    }

    public FieldValidation withLb(Label lb){
        this.lb = lb;
        return this;
    }

    public TextField getTF() {
        return tF;
    }

    public void setTF(TextField tF) {
        this.tF = tF;
    }

    public Label getLb() {
        return lb;
    }

    public void setLb(Label lb) {
        this.lb = lb;
    }

    
    
}
