package Models;

import javafx.beans.property.SimpleIntegerProperty;

public abstract class Model {
    
    private SimpleIntegerProperty id;

    public Model(int id){
        this.id = new SimpleIntegerProperty(id);
    }

    public Model(){
        this.id = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public SimpleIntegerProperty getIdProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    

}
