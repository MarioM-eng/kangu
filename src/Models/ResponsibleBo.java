package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class ResponsibleBo extends ModelBo<ResponsibleVo> {

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

        try (Connection db = Conexion.getInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            ResponsibleVo responsible;
            while(resultados.next()){
                responsible = new ResponsibleVo();
                responsible.setId(resultados.getInt("id"));
                responsible.setCel(resultados.getString("cel"));
                responsible.setAddress(resultados.getString("address"));
                responsible.setPerson(PersonBo.getInstance().findThroughList(resultados.getInt("person_id")));
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

    public ResponsibleVo create(ResponsibleVo responsibleVo)
    {
        //Se define la consulta que vamos a realizar
        String query = "CALL add_responsible(?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //La variable user la usaremos para retornar el usuario que se acaba de crear
        ResponsibleVo responsible = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setString(1, responsibleVo.getPerson().getName());
            callable.setString(2, responsibleVo.getPerson().getDni());
            callable.setString(3, responsibleVo.getCel());
            callable.setString(4, responsibleVo.getAddress());
            callable.registerOutParameter(5, Types.INTEGER);

            //Ejecutamos
            if(callable.execute()){
                System.out.println("Responsable de paciente agregado");
                //Utilizar hilos aquí
                //Actualizamos la lista de usuarios almacenada en memoria
                updateList();
                //Buscamos y guardamos en la variable user al usuario agregado
                responsible = findThroughList(callable.getInt(6));
            }else{
                System.out.println("Responsable de paciente no agregado");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer Responsable de paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer Responsable de paciente: " + e.getMessage());
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

    public void update(ResponsibleVo responsibleVo)
    {
        
        //Se define la consulta que vamos a realizar
        String query = "CALL update_patient(?,?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, responsibleVo.getPerson().getId());
            callable.setString(2, responsibleVo.getPerson().getName());
            callable.setString(3, responsibleVo.getPerson().getDni());
            callable.setInt(4, responsibleVo.getId());
            callable.setString(5, responsibleVo.getCel());
            callable.setString(6, responsibleVo.getAddress());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("Usuario actualizado");
            //Utilizar hilos aquí
            //Actualizamos la lista de usuarios almacenada en memoria
            updateElement(responsibleVo);
            PersonBo.getInstance().updateElement(responsibleVo.getPerson());
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al actualizar responsable del paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al actualizar responsable del paciente: " + e.getMessage());
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

    public void softDelete(ResponsibleVo responsibleVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL soft_delete_person(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, responsibleVo.getPerson().getId());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("Responsable del paciente eliminado");
            //Utilizar hilos aquí
            //Actualizamos la lista de personas almacenada en memoria
            PersonBo.getInstance().updateElement(responsibleVo.getPerson());
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al eliminar Responsable del paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al eliminar Responsable del paciente: " + e.getMessage());
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
    
    public void recovery(ResponsibleVo responsibleVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL recovery_person(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, responsibleVo.getPerson().getId());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("Responsable del paciente recuperado");
            //Utilizar hilos aquí
            //Actualizamos la lista de personas almacenada en memoria
            PersonBo.getInstance().updateElement(responsibleVo.getPerson());
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al recuperar responsable del paciente paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al recuperar responsable del paciente paciente: " + e.getMessage());
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
