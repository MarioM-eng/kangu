package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class ResponsibleBo extends PersonBo<ResponsibleVo> {

    private static ResponsibleBo singleton = new ResponsibleBo();

    private ResponsibleBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static ResponsibleBo getInstance(){
        return singleton;
    }

    @Override
    protected List<ResponsibleVo> all() 
    {
        
        List<ResponsibleVo> lista = new ArrayList<>();
        String query = "CALL all_responsibles()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            ResponsibleVo responsible;
            while(resultados.next()){
                responsible = new ResponsibleVo();
                responsible.setId(resultados.getInt("id"));
                responsible.setName(resultados.getString("name"));
                responsible.setDni(resultados.getString("dni"));
                responsible.setCel(resultados.getString("cel"));
                responsible.setAddress(resultados.getString("address"));
                responsible.setCreatedAt(resultados.getDate("created_at"));
                responsible.setUpdatedAt(resultados.getDate("updated_at"));
                responsible.setDeletedAt(resultados.getDate("deleted_at"));
                lista.add(responsible);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer responsable del paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer responsable del paciente: " + e.getMessage());
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

    /**
     * Crea un elemento en base de datos
     * @param responsibleVo resive el elemento
     * @return el elemento. Si ya existe, retorna null
     */
    public ResponsibleVo create(ResponsibleVo responsibleVo)
    {
        if(checkPerson(responsibleVo)){
            return null;
        }

        //Se define la consulta que vamos a realizar
        String query = "CALL add_responsible(?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //La variable user la usaremos para retornar el usuario que se acaba de crear
        ResponsibleVo responsible = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setString(1, responsibleVo.getName());
            callable.setString(2, responsibleVo.getDni());
            callable.setString(3, responsibleVo.getCel());
            callable.setString(4, responsibleVo.getAddress());
            callable.registerOutParameter(5, Types.INTEGER);
            //Ejecutamos
            callable.executeUpdate();
            //Utilizar hilos aquí
            //Buscamos y guardamos en la variable responsible al responsable agregado agregado
            responsible = findById(callable.getInt(5));
            addElement(responsible);
            System.out.println("Responsable de paciente agregado");
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al agregar Responsable de paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al agregar Responsable de paciente: " + e.getMessage());
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
        return responsible;
    }

    public boolean update(ResponsibleVo responsibleVo)
    {
        //Se define la consulta que vamos a realizar
        String query = "CALL update_responsible(?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, responsibleVo.getId());
            callable.setString(2, responsibleVo.getName());
            callable.setString(3, responsibleVo.getDni());
            callable.setString(4, responsibleVo.getCel());
            callable.setString(5, responsibleVo.getAddress());
            //Ejecutamos
            if(callable.executeUpdate() != -1){
                findThroughList(responsibleVo.getId()).replace(responsibleVo);
                return true;
            }            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al actualizar responsable del paciente: " + e.getMessage());
            return false;
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al actualizar responsable del paciente: " + e.getMessage());
            return false;
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
        return false;
    }

    public boolean softDelete(ResponsibleVo responsibleVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL soft_delete_person(?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, responsibleVo.getId());
            callable.registerOutParameter(2, Types.DATE);

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                responsibleVo.setDeletedAt(callable.getDate(2));
                return true;
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al eliminar Responsable del paciente: " + e.getMessage());
            return false;
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al eliminar Responsable del paciente: " + e.getMessage());
            return false;
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
        return false;
    }
    
    public boolean recovery(ResponsibleVo responsibleVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL recovery_person(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, responsibleVo.getId());

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Recuperamos el elemento en la lista vaciando la variable deleted_at
                responsibleVo.setDeletedAt(null);
                return true;
            }else{
                System.out.println("No fue posible recuperar al paciente en la base de datos");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al recuperar responsable del paciente paciente: " + e.getMessage());
            return false;
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al recuperar responsable del paciente paciente: " + e.getMessage());
            return false;
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
        return false;
    }

    public ResponsibleVo findById(int id)
    {
        
        //Se define la consulta que vamos a realizar
        String query = "CALL find_responsible(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        ResponsibleVo responsible = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, id);
            ResultSet resultado = callable.executeQuery();
            if(resultado.first()){
                responsible = new ResponsibleVo();
                responsible.setId(resultado.getInt("person_id"));
                responsible.setName(resultado.getString("name"));
                responsible.setDni(resultado.getString("dni"));
                responsible.setCel(resultado.getString("cel"));
                responsible.setAddress(resultado.getString("address"));
                responsible.setCreatedAt(resultado.getDate("created_at"));
                responsible.setUpdatedAt(resultado.getDate("updated_at"));
                responsible.setDeletedAt(resultado.getDate("deleted_at"));
            }

            
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al buscar responsable del paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al buscar responsable del paciente: " + e.getMessage());
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
        return responsible;
    }

}
