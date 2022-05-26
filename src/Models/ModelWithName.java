package Models;

import javafx.beans.property.SimpleStringProperty;

public abstract class ModelWithName extends Model {
    
    private SimpleStringProperty name;

    public ModelWithName(int id, String name){
        super(id);
        this.name = new SimpleStringProperty(name);
    }  
    
    public ModelWithName(){
        this.name = new SimpleStringProperty();
    }  

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty getNameProperty() {
        return name;
    }


    public void setName(String name) {
        this.name.set(name);
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return getName();
    }
}
