package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class ProfessionalBo extends ModelBo<ProfessionalVo>{

    private static ProfessionalBo singleton = new ProfessionalBo();

    private ProfessionalBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static ProfessionalBo getInstance(){
        return singleton;
    }


    @Override
    protected List<ProfessionalVo> all() 
    {
        List<ProfessionalVo> lista = new ArrayList<>();
        String query = "CALL all_professionals()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            ProfessionalVo professional;
            while(resultados.next()){
                professional = new ProfessionalVo();
                professional.setId(resultados.getInt("id"));
                professional.setName(resultados.getString("name"));
                professional.setDni(resultados.getString("dni"));
                professional.setUsername(resultados.getString("username"));
                professional.setPassword(resultados.getString("password"));
                professional.setProfession(ProfessionBo.getInstance().findThroughList(resultados.getInt("profession_id")));
                professional.setCreatedAt(resultados.getDate("created_at"));
                professional.setUpdatedAt(resultados.getDate("updated_at"));
                professional.setDeletedAt(resultados.getDate("deleted_at"));
                lista.add(professional);
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

    public ProfessionalVo create(ProfessionalVo professionalVo)
    {
        //Se define la consulta que vamos a realizar
        String query = "CALL add_profesional(?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, professionalVo.getId());
            callable.setInt(2, professionalVo.getProfession().getId());
            callable.registerOutParameter(3, Types.INTEGER);

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Buscamos al usuario agregado en la db
                professionalVo = findById(callable.getInt(3));
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(addElement(professionalVo)){
                    System.out.println("profesional agregado");
                }else{
                    System.out.println("No fue posible agregar el elemento a la lista");
                }
            }else{
                System.out.println("No fue posible registar profesional en la base de datos");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer profesional: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer profesional: " + e.getMessage());
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
        return professionalVo;
    }

    public boolean update(ProfessionalVo professionalVo)
    {
        //Se define la consulta que vamos a realizar
        String query = "CALL update_patient(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, professionalVo.getProfession().getId());

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                findThroughList(professionalVo.getId()).replace(professionalVo);
                return true;
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al actualizar profesional: " + e.getMessage());
            return false;
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al actualizar profesional: " + e.getMessage());
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

    public boolean softDelete(ProfessionalVo professionalVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL soft_delete_person(?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, professionalVo.getId());
            callable.registerOutParameter(2, Types.DATE);

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                professionalVo.setDeletedAt(callable.getDate(2));
                return true;
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al eliminar profesional: " + e.getMessage());
            return false;
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al eliminar profesional: " + e.getMessage());
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
    
    public boolean recovery(PatientVo patientVo){
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
            if(callable.executeUpdate() != -1){
                //Recuperamos el elemento en la lista vaciando la variable deleted_at
                patientVo.setDeletedAt(null);
                return true;
            }else{
                System.out.println("No fue posible recuperar al paciente en la base de datos");
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al recuperar paciente: " + e.getMessage());
            return false;
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al recuperar paciente: " + e.getMessage());
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

    public ProfessionalVo findById(int id)
    {
        
        //Se define la consulta que vamos a realizar
        String query = "CALL find_professional(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        ProfessionalVo patient = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, id);
            ResultSet resultados = callable.executeQuery();
            if(resultados.first()){
                patient = new ProfessionalVo();
                patient.setId(resultados.getInt("id"));
                patient.setName(resultados.getString("name"));
                patient.setDni(resultados.getString("dni"));
                patient.setCreatedAt(resultados.getDate("created_at"));
                patient.setUpdatedAt(resultados.getDate("updated_at"));
                patient.setDeletedAt(resultados.getDate("deleted_at"));
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
