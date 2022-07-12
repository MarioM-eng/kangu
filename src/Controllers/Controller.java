package Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableMap;

public abstract class Controller {
    
    private ObservableMap<String,Object> params;

    public ObservableMap<String,Object> getParams() {
        if(this.params == null){
            this.params = FXCollections.observableHashMap();
        }
        return this.params;
    }

    public void setParams(ObservableMap<String,Object> params) {
        this.params = params;
    }

}
