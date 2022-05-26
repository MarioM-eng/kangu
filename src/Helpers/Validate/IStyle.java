package Helpers.Validate;

import javafx.collections.ObservableList;

public interface IStyle {

    public final static String LABEL_RED = "red-color-text";
    public final static String LABEL_GREEN = "green-color-text";
    public final static String BORDER_ERROR = "border-error";
    public final static String BORDER_GOOD = "border-good";

    public void error(ObservableList<String> textFieldStyleCLass, ObservableList<String> labelStyleCLass);
    public void good(ObservableList<String> textFieldStyleCLass, ObservableList<String> labelStyleCLass);
    
}
