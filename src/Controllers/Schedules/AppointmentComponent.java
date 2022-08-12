package Controllers.Schedules;

import Controllers.Controller;
import Helpers.Alert.AlertImplement;
import Helpers.Facades.IAlert;
import Models.AppointmentBo;
import Models.AppointmentVo;
import Models.PatientBo;
import Models.PatientVo;
import Models.ProfileBo;
import Models.ProfileVo;
import Models.UserBo;
import Models.UserVo;
import Models.Relationships.ManyToManyBo;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class AppointmentComponent extends Controller {

    private TextField tfDni;
    private ComboBox<PatientVo> cbPatient;
    private ComboBox<ProfileVo> cbSpecialty;
    private ComboBox<UserVo> cbProfession;
    private Button btnAccept;
    private AlertImplement alert;

    private ManyToManyBo<ProfileVo,UserVo,ProfileBo,UserBo> profileUserBo;

    public AppointmentComponent(TextField tfDni, ComboBox<PatientVo> cbPatient, ComboBox<ProfileVo> cbSpecialty,
            ComboBox<UserVo> cbProfession, Button btnAccept) {
        
        this.tfDni = tfDni;
        this.cbPatient = cbPatient;
        this.cbSpecialty = cbSpecialty;
        this.cbProfession = cbProfession;
        this.btnAccept = btnAccept;
        
        alert = new AlertImplement();
        profileUserBo = new ManyToManyBo<>(new ProfileVo(), new UserVo(), ProfileBo.getInstance(), UserBo.getInstance());
    }

    public AppointmentComponent() {
        alert = new AlertImplement();
        profileUserBo = new ManyToManyBo<>(new ProfileVo(), new UserVo(), ProfileBo.getInstance(), UserBo.getInstance());
    }

    public void init() {
        cbSpecialty.setItems(
            ProfileBo.getInstance().getElements().filtered(el->el.getId() != 1 && el.getId() != 2)
        );
        events();
        
    }

    public AppointmentVo create() {
        AppointmentVo appointmentVo = null;
        if(cbPatient.getValue() != null){
            if(cbProfession.getSelectionModel().getSelectedItem() != null){
                appointmentVo = new AppointmentVo();
                appointmentVo.setPatient(cbPatient.getSelectionModel().getSelectedItem());
                appointmentVo.setUser(cbProfession.getValue());
                appointmentVo = AppointmentBo.getInstance().create(appointmentVo);
                getParams().put("appointment", appointmentVo);
            }else{
                alert.alert("Debe seleccionar un profesional", IAlert.SIMPLE);
            }
        }else{
            alert.alert("Debe seleccionar un paciente", IAlert.SIMPLE);
        }
        return appointmentVo;
    }

    private void events(){

        //Evento para cargar al paciente
        tfDni.setOnKeyTyped(
            keyEvent->{
                cbPatient.show();
                cbPatient.setItems(
                    PatientBo.getInstance().searchThroughList(tfDni.getText())
                );
            }
        );

        //Evento para cambiar el dni cuando se seleccione un paciente
        cbPatient.setOnAction(
            action->{
                if(cbPatient.getValue() != null){
                    tfDni.setText(cbPatient.getValue().getDni());
                }
            }
        );

        //Evento para cargar al profesional en el comboBox
        cbSpecialty.setOnAction(
            action->{
                ProfileVo p = cbSpecialty.getValue();
                cbProfession.setDisable(false);
                cbProfession.setItems(
                    profileUserBo.makeAObject(p)
                    );
            }
        );    
        
    }

    public TextField getTfDni() {
        return tfDni;
    }

    public void setTfDni(TextField tfDni) {
        this.tfDni = tfDni;
    }

    public ComboBox<PatientVo> getCbPatient() {
        return cbPatient;
    }

    public void setCbPatient(ComboBox<PatientVo> cbPatient) {
        this.cbPatient = cbPatient;
    }

    public ComboBox<ProfileVo> getCbSpecialty() {
        return cbSpecialty;
    }

    public void setCbSpecialty(ComboBox<ProfileVo> cbSpecialty) {
        this.cbSpecialty = cbSpecialty;
    }

    public ComboBox<UserVo> getCbProfession() {
        return cbProfession;
    }

    public void setCbProfession(ComboBox<UserVo> cbProfession) {
        this.cbProfession = cbProfession;
    }

    public Button getBtnAccept() {
        return btnAccept;
    }

    public void setBtnAccept(Button btnAccept) {
        this.btnAccept = btnAccept;
    }

    

    
    
}
