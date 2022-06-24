package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class UserBo extends PersonBo<UserVo>{
    
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

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            UserVo usuario;
            while(resultados.next()){
                usuario = new UserVo();
                usuario.setId(resultados.getInt("id"));
                usuario.setName(resultados.getString("name"));
                usuario.setDni(resultados.getString("dni"));
                usuario.setUsername(resultados.getString("username"));
                usuario.setPassword(resultados.getString("password"));
                usuario.setCreated_at(resultados.getDate("created_at"));
                usuario.setUpdated_at(resultados.getDate("updated_at"));
                usuario.setDeleted_at(resultados.getDate("deleted_at"));
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

    public UserVo findById(int id){
        UserVo user = null;
        String query = "CALL find_user(?)";
        CallableStatement callable = null;
        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, id);
            ResultSet resultado = callable.executeQuery();
            if(resultado.first()){
                user = new UserVo(resultado.getInt("id"),
                resultado.getString("name"),
                resultado.getString("dni"),
                resultado.getString("username"),
                resultado.getString("password"),
                resultado.getDate("created_at"),
                resultado.getDate("updated_at"),
                resultado.getDate("deleted_at"));
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
        
        if(checkPerson(userVo)){
            return null;
        }

        //Se define la consulta que vamos a realizar
        String query = "CALL add_user(?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setString(1, userVo.getName());
            callable.setString(2, userVo.getDni());
            callable.setString(3, userVo.getUsername());
            callable.setString(4, userVo.getPassword());
            callable.registerOutParameter(5, Types.INTEGER);
            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Buscamos al usuario agregado en la db
                userVo = findById(callable.getInt(5));
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(addElement(userVo)){
                    System.out.println("Usuario agregado");
                }else{
                    System.out.println("No fue posible agregar el elemento a la lista");
                }
            }else{
                System.out.println("No fue posible registar usuario a la base de datos");
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
        return userVo;
    }

    public void update(UserVo userVo)
    {
        
        //Se define la consulta que vamos a realizar
        String query = "CALL update_user(?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, userVo.getId());
            callable.setString(2, userVo.getName());
            callable.setString(3, userVo.getDni());
            callable.setString(4, userVo.getUsername());
            callable.setString(5, userVo.getPassword());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("Usuario actualizado");
            //Utilizar hilos aquí
            //Actualizamos la lista de usuarios almacenada en memoria
            updateElement(userVo);
            
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
        String query = "CALL soft_delete_person(?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, userVo.getId());
            callable.registerOutParameter(2, Types.DATE);

            //Ejecutamos
            callable.executeUpdate();
            //Utilizar hilos aquí
            //Actualizamos la lista de personas almacenada en memoria
            userVo.setDeleted_at(callable.getDate(2));
            updateElement(userVo);
            System.out.println("Usuario eliminado");
            
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

    public void recovery(UserVo userVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL recovery_person(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, userVo.getId());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("Usuario recuperado");
            //Utilizar hilos aquí
            //Actualizamos la lista de personas almacenada en memoria
            updateElement(userVo);
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al recuperar usuario: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al recuperar usuario: " + e.getMessage());
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

    public boolean checkUserName(String userName){
        for (UserVo user : UserBo.getInstance().getElements()) {
            if(user.getUsername().equals(userName)){
                return true;
            }
        }
        return false;
    }
}
