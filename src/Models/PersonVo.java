package Models;

import java.sql.Date;

public class PersonVo extends ModelWithName {
    
    private String dni;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;

    public PersonVo(int id, String name, String dni){
        super(id, name);
        this.dni = dni;
    }

    public PersonVo(){}

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }

    
    
}
