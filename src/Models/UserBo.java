package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class UserBo extends ModelBo<UserVo>{
    
    private static UserBo singleton = new UserBo();

    private UserBo(){
        super();//Se inicia el constructor padre
        updateList(all());//Se se llena o actualiza la lista de elementos
    }

    public static UserBo getInstance(){
        return singleton;
    }

    // ********************************************



    /**
     * Método para logear a un usuario
     */
    public List<UserVo> all(){

        List<UserVo> lista = new ArrayList<>();
        String query = "CALL all_users()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            UserVo usuario;
            while(resultados.next()){
                usuario = new UserVo();
                usuario.setId(resultados.getInt("id"));
                usuario.setUsername(resultados.getString("username"));
                usuario.setPassword(resultados.getString("password"));
                usuario.setPersonVo(PersonBo.getInstance().findThroughList(resultados.getInt("person_id")));
                usuario.setCreated_at(resultados.getDate("created_at"));
                usuario.setUpdate_at(resultados.getDate("update_at"));
                lista.add(usuario);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer usuarios: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer usuarios: " + e.getMessage());
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

    public UserVo find(int id){
        UserVo user = null;
        String query = "CALL find_user(?)";
        CallableStatement callable = null;
        try (Connection db = Conexion.getInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, id);
            ResultSet resultado = callable.executeQuery();
            if(resultado != null){
                user = new UserVo(resultado.getInt("id"),
                resultado.getString("username"),
                resultado.getString("password"),
                PersonBo.getInstance().findThroughList(resultado.getInt("person_id")),
                resultado.getDate("created_at"),
                resultado.getDate("update_at"));
            }else{
                System.out.println("Usuario no encontrado");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer usuario: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer usuario: " + e.getMessage());
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
        return user;
    }

}
