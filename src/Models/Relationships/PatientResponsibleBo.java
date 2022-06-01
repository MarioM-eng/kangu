package Models.Relationships;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Conexion.Conexion;
import Models.ModelBo;
import Models.PatientBo;
import Models.PatientVo;
import Models.ResponsibleBo;
import Models.ResponsibleVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PatientResponsibleBo extends ModelBo<PatientResponsibleVo> {

    private static PatientResponsibleBo singleton = new PatientResponsibleBo();

    private PatientResponsibleBo(){
        super();//Se inicia el constructor padre
        updateList();//Se llena o actualiza la lista de elementos
    }

    public static PatientResponsibleBo getInstance(){
        return singleton;
    }

    @Override
    protected List<PatientResponsibleVo> all() 
    {
        List<PatientResponsibleVo> lista = new ArrayList<>();
        String query = "CALL all_patient_responsible()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            PatientResponsibleVo patientResponsibleVo;
            while(resultados.next()){
                patientResponsibleVo = new PatientResponsibleVo();
                patientResponsibleVo.setId(resultados.getInt("id"));
                patientResponsibleVo.setPatientVo(PatientBo.getInstance().findThroughList(resultados.getInt("patient_id")));
                patientResponsibleVo.setResponsibleVo(ResponsibleBo.getInstance().findThroughList(resultados.getInt("responsible_id")));
                lista.add(patientResponsibleVo);
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

    public void create(PatientVo patientVo, ResponsibleVo responsibleVo)
    {

        String query = "CALL add_add_patient_responsible(?,?)";
        CallableStatement callable = null;

        try (Connection db = Conexion.getInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, patientVo.getId());
            callable.setInt(2, responsibleVo.getId());
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al intentar agregar responsable de paciente: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al intentar agregar responsable de paciente: " + e.getMessage());
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
    
    // ************************************

    public ObservableList<ResponsibleVo> responsiblesOfPatient(PatientVo patientVo){

        ObservableList<ResponsibleVo> lista = FXCollections.observableArrayList();
        for (PatientResponsibleVo patientResponsibleVo : getElements()) {
            if (patientResponsibleVo.getPatientVo().getId() == patientVo.getId()) {
                lista.add(patientResponsibleVo.getResponsibleVo());
            }
        }
        return lista;
    }

    public ObservableList<PatientVo> patientsOfResponsible(ResponsibleVo responsibleVo){

        ObservableList<PatientVo> lista = FXCollections.observableArrayList();
        for (PatientResponsibleVo patientResponsibleVo : getElements()) {
            if (patientResponsibleVo.getResponsibleVo().getId() == responsibleVo.getId()) {
                lista.add(patientResponsibleVo.getPatientVo());
            }
        }
        return lista;
    }
}
