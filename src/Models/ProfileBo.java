package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;

public class ProfileBo extends ModelBo<ProfileVo> {

    private static ProfileBo singleton = new ProfileBo();

    private ProfileBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static ProfileBo getInstance(){
        return singleton;
    }

    @Override
    protected List<ProfileVo> all() {
        
        List<ProfileVo> lista = new ArrayList<>();
        String query = "CALL all_profiles()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            ProfileVo profileVo;
            while(resultados.next()){
                profileVo = new ProfileVo();
                profileVo.setId(resultados.getInt("id"));
                profileVo.setName(resultados.getString("name"));
                lista.add(profileVo);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer perfiles: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer perfiles: " + e.getMessage());
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
