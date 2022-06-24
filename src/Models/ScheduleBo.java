package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class ScheduleBo extends ModelBo<ScheduleVo> {

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
                schedule.setFrom(resultados.getDate("date_from"));
                schedule.setTo(resultados.getDate("date_to"));
                schedule.setHourFrom(resultados.getTime("h_from"));
                schedule.setHourTo(resultados.getTime("h_to"));
                schedule.setPatient(PatientBo.getInstance().findThroughList(resultados.getInt("patient_id")));
                schedule.setUser(UserBo.getInstance().findThroughList(resultados.getInt("user_id")));
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
        String query = "CALL add_schedule(?,?,?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setDate(1, scheduleVo.getFrom());
            callable.setDate(2, scheduleVo.getTo());
            callable.setTime(3, scheduleVo.getHourFrom());
            callable.setTime(4, scheduleVo.getHourTo());
            callable.setInt(5, scheduleVo.getPatient().getId());
            callable.setInt(6, scheduleVo.getUser().getId());
            callable.registerOutParameter(7, Types.INTEGER);
            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Buscamos al usuario agregado en la db
                scheduleVo = findById(callable.getInt(8));
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(addElement(scheduleVo)){
                    System.out.println("Agenda agregada");
                }else{
                    System.out.println("No fue posible agregar el elemento a la lista");
                }
            }else{
                System.out.println("No fue posible registar agenda a la base de datos");
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
        String query = "CALL update_schedule(?,?,?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, scheduleVo.getId());
            callable.setDate(2, scheduleVo.getFrom());
            callable.setDate(3, scheduleVo.getTo());
            callable.setTime(4, scheduleVo.getHourFrom());
            callable.setTime(5, scheduleVo.getHourTo());

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

    public void delete(ScheduleVo scheduleVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL soft_delete_person(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, scheduleVo.getId());
            callable.registerOutParameter(2, Types.DATE);

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(deleteElement(scheduleVo)){
                    System.out.println("Agenda elimanada");
                }else{
                    System.out.println("No fue posible eliminar el elemento a la lista");
                }
            }else{
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
                resultado.getDate("date_from"),
                resultado.getDate("date_to"),
                resultado.getTime("h_from"),
                resultado.getTime("h_to"),
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
        return scheduleVo;
    }

    private boolean exist(ScheduleVo scheduleVo){
        for (ScheduleVo schedule : getElements()) {
            if(schedule.getPatient().getId() == scheduleVo.getPatient().getId() &&
            schedule.getUser().getId() == scheduleVo.getUser().getId()){
                return true;
            }
        }

        return false;
    }

    
    
}
