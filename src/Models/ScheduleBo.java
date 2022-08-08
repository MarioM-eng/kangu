package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;
import Models.Relationships.BelongsTo;
import javafx.collections.ObservableList;

public class ScheduleBo extends ModelBo<ScheduleVo> implements BelongsTo<ScheduleVo,AppointmentVo>{

    private static ScheduleBo singleton = new ScheduleBo();

    private ScheduleBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static ScheduleBo getInstance(){
        return singleton;
    }

    @Override
    protected List<ScheduleVo> all() {
        
        List<ScheduleVo> lista = new ArrayList<>();
        String query = "CALL all_schedules()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            ScheduleVo schedule;
            while(resultados.next()){
                schedule = new ScheduleVo();
                schedule.setId(resultados.getInt("id"));
                schedule.setFrom(resultados.getTime("hour_from"));
                schedule.setTo(resultados.getTime("hour_to"));
                schedule.setDay(resultados.getDate("day"));
                schedule.setAppointment(AppointmentBo.getInstance().findThroughList(resultados.getInt("appointment_id")));
                lista.add(schedule);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer agendas: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer agendas: " + e.getMessage());
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

    public ScheduleVo create(ScheduleVo scheduleVo)
    {
        if(exist(scheduleVo)){
            return null;
        }
        //Se define la consulta que vamos a realizar
        String query = "CALL add_schedule(?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setTime(1, scheduleVo.getFrom());
            callable.setTime(2, scheduleVo.getTo());
            callable.setDate(3, scheduleVo.getDay());
            callable.setInt(4, scheduleVo.getAppointment().getId());
            callable.registerOutParameter(5, Types.INTEGER);
            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Buscamos al usuario agregado en la db
                scheduleVo = findById(callable.getInt(5));
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(addElement(scheduleVo)){
                    System.out.println("Elemento agregado");
                }else{
                    System.out.println("No fue posible agregar el elemento a la lista");
                }
            }else{
                System.out.println("No fue posible registar elemento a la base de datos");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer agenda: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer agenda: " + e.getMessage());
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
        return scheduleVo;
    }

    public void update(ScheduleVo scheduleVo)
    {
        
        //Se define la consulta que vamos a realizar
        String query = "CALL update_schedule(?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, scheduleVo.getId());
            callable.setTime(2, scheduleVo.getFrom());
            callable.setTime(3, scheduleVo.getTo());
            callable.setDate(4, scheduleVo.getDay());

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(updateElement(scheduleVo)){
                    System.out.println("Agenda actualizada");
                }else{
                    System.out.println("No fue posible actualizar el elemento a la lista");
                }
            }else{
                System.out.println("No fue posible actualizar agenda a la base de datos");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al actualizar agenda: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al actualizar agenda: " + e.getMessage());
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

    public boolean delete(ScheduleVo scheduleVo){
        boolean estado = false;
        //Se define la consulta que vamos a realizar
        String query = "CALL delete_schedule(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, scheduleVo.getId());

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                estado = true;
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(deleteElement(scheduleVo)){
                    System.out.println("Agenda elimanada");
                }else{
                    System.out.println("No fue posible eliminar el elemento a la lista");
                }
            }else{
                estado = false;
                System.out.println("No fue posible eliminar agenda a la base de datos");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al eliminar agenda: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al eliminar agenda: " + e.getMessage());
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
        return estado;
    }

    public ScheduleVo findById(int id){
        ScheduleVo scheduleVo = null;
        String query = "CALL find_schedule(?)";
        CallableStatement callable = null;
        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, id);
            ResultSet resultado = callable.executeQuery();
            if(resultado.first()){
                scheduleVo = new ScheduleVo(resultado.getInt("id"),
                resultado.getTime("hour_from"),
                resultado.getTime("hour_to"),
                resultado.getDate("day"),
                AppointmentBo.getInstance().findThroughList(resultado.getInt("appointment_id")));
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
        return scheduleVo;
    }

    private boolean exist(ScheduleVo scheduleVo){
        for (ScheduleVo schedule : getElements()) {
            if(schedule.getDay() == scheduleVo.getDay() &&
            schedule.getFrom() == scheduleVo.getFrom()){
                return true;
            }
        }

        return false;
    }

    @Override
    public ObservableList<ScheduleVo> getElementsOf(AppointmentVo element) {
        return getElements().filtered(
            el -> el.getAppointment().equals(element)
        );
    }



    
    
}
