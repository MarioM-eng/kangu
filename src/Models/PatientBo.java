package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class PatientBo extends ModelBo<PatientVo>{

    private static PatientBo singleton = new PatientBo();

    private PatientBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static PatientBo getInstance(){
        return singleton;
    }


    @Override
    protected List<PatientVo> all() 
    {
        List<PatientVo> lista = new ArrayList<>();
        String query = "CALL all_patients()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            PatientVo patient;
            while(resultados.next()){
                patient = new PatientVo();
                patient.setId(resultados.getInt("id"));
                patient.setAge(resultados.getString("age"));
                patient.setSex(resultados.getString("sex"));
                patient.setDiagnosis(resultados.getString("diagnosis"));
                patient.setPerson(PersonBo.getInstance().findThroughList(resultados.getInt("person_id")));
                lista.add(patient);
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

    public PatientVo create(PatientVo patientVo)
    {
        //Se define la consulta que vamos a realizar
        String query = "CALL add_user(?,?,?,?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //La variable user la usaremos para retornar el usuario que se acaba de crear
        PatientVo patient = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setString(1, patientVo.getPerson().getName());
            callable.setString(2, patientVo.getPerson().getDni());
            callable.setString(3, patientVo.getAge());
            callable.setDate(4, patientVo.getDateBirth());
            callable.setString(5, patientVo.getSex());
            callable.setString(6, patientVo.getDiagnosis());
            callable.registerOutParameter(7, Types.INTEGER);

            //Ejecutamos
            if(callable.execute()){
                System.out.println("Paciente agregado");
                //Utilizar hilos aquí
                //Actualizamos la lista de usuarios almacenada en memoria
                updateList();
                //Buscamos y guardamos en la variable user al usuario agregado
                patient = findThroughList(callable.getInt(6));
            }else{
                System.out.println("Paciente no agregado");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer paciente: " + e.getMessage());
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
        return patient;
    }

    public void update(PatientVo patientVo)
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
            callable.setInt(1, patientVo.getPerson().getId());
            callable.setString(2, patientVo.getPerson().getName());
            callable.setString(3, patientVo.getPerson().getDni());
            callable.setInt(4, patientVo.getId());
            callable.setString(5, patientVo.getAge());
            callable.setDate(6, patientVo.getDateBirth());
            callable.setString(7, patientVo.getSex());
            callable.setString(8, patientVo.getDiagnosis());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("Usuario actualizado");
            //Utilizar hilos aquí
            //Actualizamos la lista de usuarios almacenada en memoria
            updateElement(patientVo);
            PersonBo.getInstance().updateElement(patientVo.getPerson());
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al actualizar paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al actualizar paciente: " + e.getMessage());
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

    public void softDelete(PatientVo patientVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL soft_delete_person(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, patientVo.getPerson().getId());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("Paciente eliminado");
            //Utilizar hilos aquí
            //Actualizamos la lista de personas almacenada en memoria
            PersonBo.getInstance().updateElement(patientVo.getPerson());
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al eliminar Paciente: " + e.getMessage());
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
    
    public void recovery(PatientVo patientVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL recovery_person(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, patientVo.getPerson().getId());

            //Ejecutamos
            callable.executeUpdate();
            System.out.println("paciente recuperado");
            //Utilizar hilos aquí
            //Actualizamos la lista de personas almacenada en memoria
            PersonBo.getInstance().updateElement(patientVo.getPerson());
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al recuperar paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al recuperar paciente: " + e.getMessage());
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
