package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class PersonBo extends ModelBo<PersonVo> {

    private static PersonBo singleton = new PersonBo();
    public static int c = 0;

    private PersonBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static PersonBo getInstance(){
        return singleton;
    }

    @Override
    protected List<PersonVo> all() {
        List<PersonVo> lista = new ArrayList<>();
        String query = "CALL all_persons()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
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
