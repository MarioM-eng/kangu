package Models;

import java.sql.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class UserVo extends Person{

    private SimpleStringProperty username;
    private SimpleStringProperty password;

    public UserVo(){
        super();
        this.username = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
    }

    public UserVo(int id, String name, String dni,String username, String password, Date createdAt, Date updatedAt, Date deletedAt) {
        super(id,name,dni,createdAt,updatedAt,deletedAt);
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new UserVo(
            getId(), getName(), 
            getDni(), getUsername(), 
            getPassword(), getCreatedAt(), 
            getCreatedAt(), getDeletedAt());
    }

    public void replace(UserVo userVo){
        this.setId(userVo.getId());
        this.setDni(userVo.getDni());
        this.setName(userVo.getName());
        this.setUsername(userVo.getUsername());
        this.setPassword(userVo.getPassword());
        this.setCreatedAt(userVo.getCreatedAt());
        this.setUpdatedAt(userVo.getUpdatedAt());
        this.setDeletedAt(userVo.getDeletedAt());
    }

    public void combine(UserVo userVo){
        if(userVo.getId() != 0){
            this.setId(userVo.getId());
        }
        if(!userVo.getDni().equals(null)){
            this.setDni(userVo.getDni());
        }
        if(!userVo.getName().equals(null)){
            this.setName(userVo.getName());
        }
        if(!userVo.getUsername().equals(null)){
            this.setUsername(userVo.getUsername());
        }
        if(!userVo.getPassword().equals(null)){
            this.setPassword(userVo.getPassword());
        }
        if(!userVo.getCreatedAt().equals(null)){
            this.setCreatedAt(userVo.getCreatedAt());
        }
        if(!userVo.getUpdatedAt().equals(null)){
            this.setUpdatedAt(userVo.getUpdatedAt());
        }
        if(!userVo.getDeletedAt().equals(null)){
            this.setDeletedAt(userVo.getDeletedAt());
        }
    }
    
}
