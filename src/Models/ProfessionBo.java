package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class ProfessionBo extends ModelBo<ProfessionVo>{

    private static ProfessionBo singleton = new ProfessionBo();

    private ProfessionBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static ProfessionBo getInstance(){
        return singleton;
    }

    @Override
    protected List<ProfessionVo> all() {
        
        List<ProfessionVo> lista = new ArrayList<>();
        String query = "CALL all_professions()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            ProfessionVo profession;
            while(resultados.next()){
                profession = new ProfessionVo();
                profession.setId(resultados.getInt("id"));
                profession.setName(resultados.getString("name"));
                lista.add(profession);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer profesiones: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer profesiones: " + e.getMessage());
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
