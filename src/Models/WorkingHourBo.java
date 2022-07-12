package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class WorkingHourBo extends ModelBo<WorkingHourVo>{

    private static WorkingHourBo singleton = new WorkingHourBo();

    private WorkingHourBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static WorkingHourBo getInstance(){
        return singleton;
    }

    @Override
    protected List<WorkingHourVo> all() {
        List<WorkingHourVo> lista = new ArrayList<>();
        String query = "CALL all_working_hours()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            WorkingHourVo workingHour;
            while(resultados.next()){
                workingHour = new WorkingHourVo();
                workingHour.setId(resultados.getInt("id"));
                workingHour.setHourFrom(resultados.getTime("hour_from"));
                workingHour.setHourTo(resultados.getTime("hour_to"));
                workingHour.setDay(resultados.getDate("day"));
                workingHour.setSchedule(ScheduleBo.getInstance()
                .findThroughList(resultados.getInt("schedule_id")));
                lista.add(workingHour);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer horas de agenda: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer horas de agenda: " + e.getMessage());
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
    
    public WorkingHourVo create(WorkingHourVo workingHourVo)
    {
        //Se define la consulta que vamos a realizar
        String query = "CALL add_working_hour(?,?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setTime(1, workingHourVo.getHourFrom());
            callable.setTime(2, workingHourVo.getHourTo());
            callable.setDate(3, workingHourVo.getDay());
            callable.registerOutParameter(4, Types.INTEGER);
            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Buscamos al usuario agregado en la db
                workingHourVo = findById(callable.getInt(4));
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(addElement(workingHourVo)){
                    System.out.println("Hora de agenda agregada");
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
        return workingHourVo;
    }

    public void update(WorkingHourVo workingHourVo)
    {
        
        //Se define la consulta que vamos a realizar
        String query = "CALL update_working_hour(?,?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, workingHourVo.getId());
            callable.setTime(2, workingHourVo.getHourFrom());
            callable.setTime(3, workingHourVo.getHourTo());

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(updateElement(workingHourVo)){
                    System.out.println("Elemento actualizado");
                }else{
                    System.out.println("No fue posible actualizar el elemento a la lista");
                }
            }else{
                System.out.println("No fue posible actualizar elemento en la base de datos");
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

    public void delete(WorkingHourVo workingHourVo){
        //Se define la consulta que vamos a realizar
        String query = "CALL delete_working_hour(?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, workingHourVo.getId());

            //Ejecutamos
            if(callable.executeUpdate() != -1){
                //Agregamos al elemento en la lista; Retorna true si se agregó exitosamente
                if(deleteElement(workingHourVo)){
                    System.out.println("Elemento elimanado");
                }else{
                    System.out.println("No fue posible eliminar el elemento");
                }
            }else{
                System.out.println("No fue posible eliminar en la base de datos");
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

    public WorkingHourVo findById(int id){
        WorkingHourVo workingHourVo = null;
        String query = "CALL find_working_hour(?)";
        CallableStatement callable = null;
        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, id);
            ResultSet resultado = callable.executeQuery();
            if(resultado.first()){
                workingHourVo = new WorkingHourVo(resultado.getInt("id"),
                resultado.getTime("hour_from"),
                resultado.getTime("hour_to"),
                resultado.getDate("day"),
                ScheduleBo.getInstance().findThroughList(resultado.getInt("schedule_id")));
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
        return workingHourVo;
    }

}
