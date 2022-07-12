package Controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ResourceBundle;

import Helpers.Calendar.DateCalendar;
import Models.AppointmentVo;
import Models.ScheduleBo;
import Models.ScheduleVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class ScheduleController extends Controller implements Initializable{

    @FXML
    private ComboBox<Time> cbHStart;
    @FXML
    private ComboBox<Time> cbHEnd;
    @FXML
    private TextField tfDay;
    
    private DateCalendar dateCalendar;


    public ScheduleController(DateCalendar dateCalendar) {
        this.dateCalendar = dateCalendar;
    }

    public ComboBox<Time> getCbHStart() {
        return cbHStart;
    }
    public void setCbHStart(ComboBox<Time> cbHStart) {
        this.cbHStart = cbHStart;
    }

    public ComboBox<Time> getCbHEnd() {
        return cbHEnd;
    }
    public void setCbHEnd(ComboBox<Time> cbHEnd) {
        this.cbHEnd = cbHEnd;
    }

    public TextField getTfDay() {
        return tfDay;
    }
    public void setTfDay(TextField tfDay) {
        this.tfDay = tfDay;
    }

    public DateCalendar getDateCalendar() {
        return dateCalendar;
    }
    public void setDateCalendar(DateCalendar dateCalendar) {
        this.dateCalendar = dateCalendar;
    }

    public void create(AppointmentVo appointmentVo, ScheduleVo scheduleVo){

        scheduleVo.setFrom(cbHStart.getSelectionModel().getSelectedItem());
        scheduleVo.setTo(cbHEnd.getSelectionModel().getSelectedItem());
        scheduleVo.setAppointment(appointmentVo);
        if (!ScheduleBo.getInstance().create(scheduleVo)) {
            System.out.println("Horario del día " + scheduleVo.getDay() + " no se pudo crear");
        }
        
    }

    /* private void valuesDefault(){
        cbHStart.getSelectionModel().clearSelection();
        cbHEnd.setItems(null);
        tfDay.setText("");
    } */

    public void chargeSchedule(ScheduleVo schedule,Date day){
        chargeHours(schedule);
        if(day == null){
            tfDay.setText("General");
        }else{
            tfDay.setText(schedule.getDay().toString());
        }
    }

    private void chargeHours(ScheduleVo scheduleVo){
        cbHStart.getSelectionModel().select(scheduleVo.getFrom());
        cbHEnd.getSelectionModel().select(scheduleVo.getTo());
        /* cbHEnd.setOnAction(
            action->System.out.println("Hola")
        ); */
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
        //chargeSchedule();
        //rechargeCalendar();
        
    }

    
}
