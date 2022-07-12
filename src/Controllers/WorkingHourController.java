package Controllers;

import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import Models.ScheduleVo;
import Models.WorkingHourBo;
import Models.WorkingHourVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class WorkingHourController extends Controller implements Initializable {

    @FXML
    private ComboBox<Time> cbHStart;
    @FXML
    private ComboBox<Time> cbHEnd;
    @FXML
    private TextField tfDay;
    
    private WorkingHourVo workingHourGeneral;

    //private WorkingHourVo workingHourVo;
    private ObservableList<ScheduleVo> listPatientSchedule;
    private Boolean hourCheck;

    public WorkingHourController() {
    }

    public ObservableList<ScheduleVo> getPatientSchedule(){
        return listPatientSchedule;
    }

    public void setPatientSchedule(ObservableList<ScheduleVo> list){
        this.listPatientSchedule = list;
    }

    public WorkingHourVo getWorkingHourGeneral(){
        return this.workingHourGeneral;
    }

    private void setWorkingHourGeneral(WorkingHourVo workingHourGeneral){
        this.workingHourGeneral = workingHourGeneral;
    }

    /**
     * Retorna el </code>true</code> si el método </code>HourCheckEquals()</code> es verdadero
     * @return la variable hourCheck
     */
    private boolean getHourCheck(){
        if(this.hourCheck == null){
            this.hourCheck = HourCheckEquals();
        }
        return this.hourCheck;
    }

    public WorkingHourVo create(ScheduleVo scheduleVo){
        WorkingHourVo workingHour = new WorkingHourVo();
        workingHour.setHourFrom(cbHStart.getSelectionModel().getSelectedItem());
        workingHour.setHourTo(cbHEnd.getSelectionModel().getSelectedItem());
        workingHour.setSchedule(scheduleVo);
        return workingHour;
    }

    /**
     * Retorna la lista de las horas de la cita cada día
     * @return
     */
    private List<WorkingHourVo> patientHours(){
        List<WorkingHourVo> patientHours = new ArrayList<>();
        
        for (ScheduleVo scheduleVo : getPatientSchedule()) 
        {
            /* patientHours.addAll(
                    WorkingHourBo
                    .getInstance()
                    .getElementsOf(scheduleVo)
                    ); */
        }
        return patientHours;
    }

    /**
     * Verifica si el horario puesto por el usuario se cruza con alguno del paciente
     * @return los días en que las citas se cruzan
     */
    /* private List<WorkingHourVo> patientCalendarCheck(){
        List<WorkingHourVo> Notavailable = new ArrayList<>();
        for (WorkingHourVo workingHour : patientHours()) {
            Date dateFrom = this.workingHourVo.getSchedule().getFrom();
            Date dateTo = this.workingHourVo.getSchedule().getTo();
            if(workingHour.getDay().getTime() >= dateFrom.getTime() 
            && workingHour.getDay().getTime() <=  dateTo.getTime()){
                if(this.workingHourVo.getHourFrom().getTime() < workingHour.getHourTo().getTime()){
                    Notavailable.add(workingHour);
                }
            }
        }
        return Notavailable;
    } */

    /**
     * Retorna la verdadero o false si las horas se repiten cada día en una agenda
     * @return true si repite, falso si no
     */
    private boolean HourCheckEquals() {
        boolean equals = false;
        for (WorkingHourVo workingHour : patientHours()) {
            if (getWorkingHourGeneral() == null) {
                setWorkingHourGeneral(workingHour);
            }
            if (workingHour.getHourFrom().getTime() == getWorkingHourGeneral().getHourFrom().getTime()) {
                equals = true;
            } else {
                equals = false;
                setWorkingHourGeneral(null);
                break;
            }
        }
        return equals;
    }

    private boolean removeHours(Time removeFrom, Time removeTo){
        if(getHourCheck()){
            cbHStart.getItems().removeAll(cbHStart.getItems().filtered(
                element-> 
                element.getTime() >= removeFrom.getTime() &&
                element.getTime() < removeTo.getTime()
            ));
            return true;
        }
        return false;
    }

    public void valuesDefault(){
        cbHStart.getSelectionModel().clearSelection();
        cbHEnd.setItems(null);
        tfDay.setText("");
    }

    public void chargeWorkSchedules(){
        HourCheckEquals();
        if(getWorkingHourGeneral() != null){
            this.tfDay.setText("Siempre");
            this.cbHStart.getSelectionModel().select(getWorkingHourGeneral().getHourFrom());
            this.cbHEnd.getSelectionModel().select(getWorkingHourGeneral().getHourTo());
        }
    }

    private void events(){
        this.cbHStart.setOnAction(action->{
            if(this.cbHStart.getValue() != null){
                this.cbHEnd.setItems(FXCollections.observableArrayList(
                Time.valueOf(
                this.cbHStart.getValue()
                .toLocalTime()
                .plusMinutes(Helpers.Time.DURACION_MINIMA)
                ),
                Time.valueOf(
                this.cbHStart.getValue()
                .toLocalTime()
                .plusMinutes(Helpers.Time.DURACION_MAXIMA)
                )
                ));
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Time> list = FXCollections.observableArrayList();
        for (int hora : Helpers.Time.HORAS) {
            if(hora > 7 && hora < 18){
                for (int minuto : Helpers.Time.MINUTOS) {
                    list.add(Time.valueOf(LocalTime.of(hora, minuto)));
                }
            }
        }
        cbHStart.setItems(list);
        events();
        chargeWorkSchedules();
        //getHourCheck();
        
        
    }
    
}
