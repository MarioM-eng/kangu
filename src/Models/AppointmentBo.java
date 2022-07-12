package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;
import Models.Relationships.HasMany;
import javafx.collections.ObservableList;

public class AppointmentBo extends ModelBo<AppointmentVo> implements HasMany<AppointmentVo> {

    private static AppointmentBo singleton = new AppointmentBo();

    private AppointmentBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static AppointmentBo getInstance(){
        return singleton;
    }

    @Override
    protected List<AppointmentVo> all() {
        
        List<AppointmentVo> lista = new ArrayList<>();
        String query = "CALL all_appointments()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            AppointmentVo appointment;
            while(resultados.next()){
                appointment = new AppointmentVo();
                appointment.setId(resultados.getInt("id"));
                appointment.setPatient(PatientBo.getInstance().findThroughList(resultados.getInt("patient_id")));
                appointment.setUser(UserBo.getInstance().findThroughList(resultados.getInt("user_id")));
                lista.add(appointment);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer elementos: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer elementos: " + e.getMessage());
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

    public AppointmentVo create(AppointmentVo appointmentVo)
    {

        if(exist(appointmentVo)){
            return null;
        }
        //Se define la consulta que vamos a realizar
        String query = "CALL add_appointment(?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, appointmentVo.getPatient().getId());
            callable.setInt(2, appointmentVo.getUser().getId());
            callable.registerOutParameter(3, Types.INTEGER);
            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Buscamos al usuario agregado en la db
                appointmentVo = findById(callable.getInt(3));
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(addElement(appointmentVo)){
                    System.out.println("Agenda elemento");
                }else{
                    System.out.println("No fue posible agregar el elemento a la lista");
                }
            }else{
                System.out.println("No fue posible registar elemento a la base de datos");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer elemento: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer elemento: " + e.getMessage());
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
        return appointmentVo;
    }

    public void update(AppointmentVo appointmentVo)
    {
        
        //Se define la consulta que vamos a realizar
        String query = "CALL update_appointment(?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, appointmentVo.getId());
            callable.setInt(2, appointmentVo.getPatient().getId());
            callable.setInt(3,  appointmentVo.getUser().getId());

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(updateElement(appointmentVo)){
                    System.out.println("Agenda actualizada");
                }else{
                    System.out.println("No fue posible actualizar el elemento a la lista");
                }
            }else{
                System.out.println("No fue posible actualizar elemento a la base de datos");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al actualizar elemento: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al actualizar elemento: " + e.getMessage());
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

    public void delete(AppointmentVo appointmentVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL delete_appointment(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, appointmentVo.getId());
            callable.registerOutParameter(2, Types.DATE);

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(deleteElement(appointmentVo)){
                    System.out.println("Elemento elimanada");
                }else{
                    System.out.println("No fue posible eliminar el elemento a la lista");
                }
            }else{
                System.out.println("No fue posible eliminar elemento a la base de datos");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al eliminar elemento: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al eliminar elemento: " + e.getMessage());
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

    public AppointmentVo findById(int id){
        AppointmentVo appointmentVo = null;
        String query = "CALL find_appointment(?)";
        CallableStatement callable = null;
        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, id);
            ResultSet resultado = callable.executeQuery();
            if(resultado.first()){
                appointmentVo = new AppointmentVo(resultado.getInt("id"),
                PatientBo.getInstance().findThroughList(resultado.getInt("patient_id")),
                UserBo.getInstance().findThroughList(resultado.getInt("user_id")));
            }else{
                System.out.println("Elemento no encontrado");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer elemento de la base de datos: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer elemento de la base de datos: " + e.getMessage());
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
        return appointmentVo;
    }

    private boolean exist(AppointmentVo appointmentVo){
        for (AppointmentVo appointment : getElements()) {
            if(appointment.getPatient().getId() == appointmentVo.getPatient().getId() &&
            appointment.getUser().getId() == appointmentVo.getUser().getId()){
                return true;
            }
        }

        return false;
    }

    @Override
    public ObservableList<AppointmentVo> getElementsOf(Object object) {
        if(object.equals(UserVo.class)){
            return getElements().filtered(
            element -> element.getUser().equals(object)
        );
        }else{
            return getElements().filtered(
            element -> element.getPatient().equals(object)
        );
        }
    }

    
}
