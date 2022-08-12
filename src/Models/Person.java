package Models;

import java.sql.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public abstract class Person extends ModelWithName implements Cloneable {
    
    private SimpleStringProperty dni;
    private SimpleObjectProperty<Date> createdAt,updatedAt,deletedAt;

    public Person(int id, String name, String dni, Date createdAt, Date updatedAt, Date deletedAt){
        super(id, name);
        this.dni = new SimpleStringProperty(dni);
        this.createdAt = new SimpleObjectProperty<>(createdAt);
        this.updatedAt = new SimpleObjectProperty<>(updatedAt);
        this.deletedAt = new SimpleObjectProperty<>(deletedAt);
    }

    public Person(){
        super();
        this.dni = new SimpleStringProperty();
        this.createdAt = new SimpleObjectProperty<>();
        this.updatedAt = new SimpleObjectProperty<>();
        this.deletedAt = new SimpleObjectProperty<>();
    }

    public String getDni() {
        return this.dni.get();
    }

    public SimpleStringProperty getDniProperty() {
        return this.dni;
    }

    public void setDni(String dni) {
        this.dni.set(dni);
    }

    public Date getCreatedAt() {
        return createdAt.get();
    }

    public SimpleObjectProperty<Date> getCreatedAtProperty() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt.set(createdAt);
    }

    public Date getUpdatedAt() {
        return updatedAt.get();
    }

    public SimpleObjectProperty<Date> getupdatedAtProperty() {
        return createdAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt.set(updatedAt);
    }

    public Date getDeletedAt() {
        return deletedAt.get();
    }

    public SimpleObjectProperty<Date> getDeletedAtProperty() {
        return createdAt;
    }

    public void setDeletedAt(Date deletedAt) {
        this.deletedAt.set(deletedAt);
    }

    
    
}
