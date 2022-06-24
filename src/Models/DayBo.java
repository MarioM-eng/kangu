package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class DayBo extends ModelBo<DayVo> {

    private static DayBo singleton = new DayBo();

    private DayBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static DayBo getInstance(){
        return singleton;
    }

    @Override
    protected List<DayVo> all() {
        List<DayVo> lista = new ArrayList<>();
        String query = "CALL all_days()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            DayVo day;
            while(resultados.next()){
                day = new DayVo();
                day.setId(resultados.getInt("id"));
                day.setName(resultados.getString("name"));
                lista.add(day);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("1 - Error al traer días: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("2 - Error al traer días: " + e.getMessage());
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
    
}
