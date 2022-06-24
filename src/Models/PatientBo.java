package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class PatientBo extends PersonBo<PatientVo>{

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

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            PatientVo patient;
            while(resultados.next()){
                patient = new PatientVo();
                patient.setId(resultados.getInt("id"));
                patient.setName(resultados.getString("name"));
                patient.setDni(resultados.getString("dni"));
                patient.setAge(resultados.getString("age"));
                patient.setSex(resultados.getString("sex"));
                patient.setDateBirth(resultados.getDate("date_birth"));
                patient.setDiagnosis(resultados.getString("diagnosis"));
                patient.setCreated_at(resultados.getDate("created_at"));
                patient.setUpdated_at(resultados.getDate("updated_at"));
                patient.setDeleted_at(resultados.getDate("deleted_at"));
                lista.add(patient);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("1 - Error al traer pacientes: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("2 - Error al traer pacientes: " + e.getMessage());
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

        if(checkPerson(patientVo)){
            return null;
        }

        //Se define la consulta que vamos a realizar
        String query = "CALL add_patient(?,?,?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //La variable user la usaremos para retornar el usuario que se acaba de crear
        PatientVo patient = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setString(1, patientVo.getName());
            callable.setString(2, patientVo.getDni());
            callable.setString(3, patientVo.getAge());
            callable.setDate(4, patientVo.getDateBirth());
            callable.setString(5, patientVo.getSex());
            callable.setString(6, patientVo.getDiagnosis());
            callable.registerOutParameter(7, Types.INTEGER);

            //Ejecutamos
            callable.executeUpdate();
            //Utilizar hilos aquí
            //Buscamos y guardamos en la variable responsible al responsable agregado agregado
            patientVo = findById(callable.getInt(7));
            addElement(patientVo);
            System.out.println("Paciente agregado");
            
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
        String query = "CALL update_patient(?,?,?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, patientVo.getId());
            callable.setString(2, patientVo.getName());
            callable.setString(3, patientVo.getDni());
            callable.setString(4, patientVo.getAge());
            callable.setDate(5, patientVo.getDateBirth());
            callable.setString(6, patientVo.getSex());
            callable.setString(7, patientVo.getDiagnosis());

            //Ejecutamos
            callable.executeUpdate();
            //Utilizar hilos aquí
            //Actualizamos la lista de usuarios almacenada en memoria
            updateElement(patientVo);
            System.out.println("Paciente actualizado");
            
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
        String query = "CALL soft_delete_person(?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, patientVo.getId());
            callable.registerOutParameter(2, Types.DATE);

            //Ejecutamos
            callable.executeUpdate();
            //Utilizar hilos aquí
            //Actualizamos la lista de pacientes almacenada en memoria
            patientVo.setDeleted_at(callable.getDate(2));
            updateElement(patientVo);
            System.out.println("Paciente eliminado");
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al eliminar paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al eliminar paciente: " + e.getMessage());
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
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, patientVo.getId());

            //Ejecutamos
            callable.executeUpdate();
            //Utilizar hilos aquí
            //Actualizamos la lista de pacientes almacenada en memoria
            patientVo.setDeleted_at(null);
            updateElement(patientVo);
            System.out.println("paciente recuperado");
            
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

    public PatientVo findById(int id)
    {
        
        //Se define la consulta que vamos a realizar
        String query = "CALL find_patient(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        PatientVo patient = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, id);
            ResultSet resultados = callable.executeQuery();
            if(resultados.first()){
                patient = new PatientVo();
                patient.setId(resultados.getInt("id"));
                patient.setName(resultados.getString("name"));
                patient.setDni(resultados.getString("dni"));
                patient.setAge(resultados.getString("age"));
                patient.setSex(resultados.getString("sex"));
                patient.setDateBirth(resultados.getDate("date_birth"));
                patient.setDiagnosis(resultados.getString("diagnosis"));
                patient.setCreated_at(resultados.getDate("created_at"));
                patient.setUpdated_at(resultados.getDate("updated_at"));
                patient.setDeleted_at(resultados.getDate("deleted_at"));
            }

            
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al buscar paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al buscar paciente: " + e.getMessage());
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

}
