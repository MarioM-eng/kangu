package Models;

import java.sql.Date;

public class UserVo extends Model{

    private String username;
    private String password;
    private PersonVo personVo;
    private Date created_at, update_at;

    public UserVo(){}

    public UserVo(int id,String username, String password, PersonVo personVo, Date created_at, Date update_at) {
        super(id);
        this.username = username;
        this.password = password;
        this.personVo = personVo;
        this.created_at = created_at;
        this.update_at = update_at;
    }

    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public PersonVo getPersonVo() {
        return personVo;
    }

    public void setPersonVo(PersonVo personVo) {
        this.personVo = personVo;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(Date update_at) {
        this.update_at = update_at;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        String name = personVo.getName();
        return name;
    }

    
    
}
