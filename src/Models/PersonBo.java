package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PersonBo extends ModelBo<PersonVo> {

    private static PersonBo singleton = new PersonBo();
    private ObservableList<PersonVo> users;

    private PersonBo(){
        if(this.users == null){
            this.users = FXCollections.observableArrayList();
        }
        if(this.users.isEmpty()){
            all();
        }
    }

    public static PersonBo getInstance(){
        return singleton;
    }

    @Override
    protected List<PersonVo> all() {
        List<PersonVo> lista = new ArrayList<>();
        String query = "CALL all_users()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            PersonVo persona;
            while(resultados.next()){
                persona = new PersonVo();
                persona.setId(resultados.getInt("id"));
                persona.setName(resultados.getString("name"));
                persona.setDni(resultados.getString("dni"));
                persona.setCreated_at(resultados.getDate("created_at"));
                persona.setUpdated_at(resultados.getDate("updated_at"));
                persona.setDeleted_at(resultados.getDate("deleted_at"));
                lista.add(persona);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer personas: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer personas: " + e.getMessage());
        } finally{
            if(callable != null){
                try {
                    callable.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return lista;
    }

    
    
}
