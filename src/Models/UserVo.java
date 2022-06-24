package Models;

import java.sql.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserVo extends Person{

    private SimpleStringProperty username;
    private SimpleStringProperty password;
    private SimpleObjectProperty<Date> created_at, updated_at, deleted_at;

    public UserVo(){
        super();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.created_at = new SimpleObjectProperty<>();
        this.updated_at = new SimpleObjectProperty<>();
        this.deleted_at = new SimpleObjectProperty<>();
    }

    public UserVo(int id, String name, String dni,String username, String password, Date created_at, Date updated_at, Date deleted_at) {
        super(id,name,dni);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
        this.created_at = new SimpleObjectProperty<>(created_at);
        this.updated_at = new SimpleObjectProperty<>(updated_at);
        this.deleted_at = new SimpleObjectProperty<>(deleted_at);
    }

    

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty getUsernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public SimpleStringProperty getPasswordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public Date getCreated_at() {
        return created_at.get();
    }

    public SimpleObjectProperty<Date> getCreated_atProperty() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at.set(created_at);
    }

    public Date getUpdated_at() {
        return updated_at.get();
    }

    public SimpleObjectProperty<Date> getUpdated_atProperty() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at.set(updated_at);
    }

    public Date getDeleted_at() {
        return deleted_at.get();
    }

    public SimpleObjectProperty<Date> getDeleted_atProperty() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at.set(deleted_at);
    }

    
    
}
