package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class UserBo extends ModelBo<UserVo>{
    
    private static UserBo singleton = new UserBo();

    private UserBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
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

    public UserVo create(UserVo userVo)
    {
        //Se define la consulta que vamos a realizar
        String query = "CALL add_user(?,?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //La variable user la usaremos para retornar el usuario que se acaba de crear
        UserVo user = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setString(1, userVo.getPersonVo().getName());
            callable.setString(2, userVo.getPersonVo().getDni());
            callable.setDate(3, userVo.getPersonVo().getCreated_at());
            callable.setString(4, userVo.getUsername());
            callable.setString(5, userVo.getPassword());
            callable.registerOutParameter(6, Types.INTEGER);

            //Ejecutamos
            if(callable.execute()){
                System.out.println("Usuario agregado");
                //Utilizar hilos aquí
                //Actualizamos la lista de usuarios almacenada en memoria
                updateList();
                //Buscamos y guardamos en la variable user al usuario agregado
                user = find(callable.getInt(6));
            }else{
                System.out.println("Usuario no agregado");
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

    public void update(UserVo userVo)
    {
        
        //Se define la consulta que vamos a realizar
        String query = "CALL update_user(?,?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, userVo.getPersonVo().getId());
            callable.setString(2, userVo.getPersonVo().getName());
            callable.setString(3, userVo.getPersonVo().getDni());
            callable.setInt(4, userVo.getId());
            callable.setString(5, userVo.getUsername());
            callable.setString(6, userVo.getPassword());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("Usuario actualizado");
            //Utilizar hilos aquí
            //Actualizamos la lista de usuarios almacenada en memoria
            updateElement(userVo);
            PersonBo.getInstance().updateElement(userVo.getPersonVo());
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al actualizar usuario: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al actualizar usuario: " + e.getMessage());
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

    }

    public void softDelete(UserVo userVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL soft_delete_person(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, userVo.getPersonVo().getId());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("Usuario eliminado");
            //Utilizar hilos aquí
            //Actualizamos la lista de personas almacenada en memoria
            PersonBo.getInstance().updateElement(userVo.getPersonVo());
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al eliminar usuario: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al eliminar usuario: " + e.getMessage());
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
    }
}
