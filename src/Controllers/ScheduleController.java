package Controllers;

import java.net.URL;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ResourceBundle;

import Models.AppointmentBo;
import Models.ScheduleBo;
import Models.ScheduleVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class ScheduleController extends Controller implements Initializable{

    @FXML
    private ComboBox<Time> cbHStart;
    @FXML
    private ComboBox<Time> cbHEnd;
    @FXML
    private TextField tfDay;
    @FXML
    private Button btnSave;

    private Pane parent;
    private ScheduleVo schedule;
    


    public ScheduleController() {}
    public ScheduleController(ScheduleVo schedule) {
        this.schedule = schedule;
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
    

    public void create(ScheduleVo scheduleVo){

        scheduleVo.setFrom(cbHStart.getSelectionModel().getSelectedItem());
        scheduleVo.setTo(cbHEnd.getSelectionModel().getSelectedItem());
        if (!ScheduleBo.getInstance().create(scheduleVo)) {
            System.out.println("Horario del día " + scheduleVo.getDay() + " no se pudo crear");
        }
        
    }

    public void chargeHours(ScheduleVo scheduleVo){
        cbHStart.getSelectionModel().select(scheduleVo.getFrom());
        cbHEnd.getSelectionModel().select(scheduleVo.getTo());
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

    private void fillNotAvaible(ScheduleVo scheduleVo) {
        AppointmentBo.getInstance()
                .getElementsOf(
                        scheduleVo.getAppointment().getPatient())
                .forEach(
                        appointment -> {
                            ScheduleBo.getInstance().getElementsOf(appointment).forEach(
                                    schedule -> {
                                        if (scheduleVo.getDay().equals(schedule.getDay())) {
                                            cbHStart.getItems().removeIf(
                                                ac->ac.getTime() <= schedule.getTo().getTime());
                                        }
                                    });
                        });
    }

    public void edit(ScheduleVo scheduleVo){
        if(scheduleVo != null){
            getTfDay().setText(scheduleVo.getDay().toString());
            fillNotAvaible(scheduleVo);
            chargeHours(scheduleVo);
            btnSave.setOnAction(
                action->{
                    scheduleVo.setFrom(cbHStart.getSelectionModel().getSelectedItem());
                    scheduleVo.setTo(cbHEnd.getSelectionModel().getSelectedItem());
                    
                }
            );
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parent = (Pane)btnSave.getParent();
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
        if(this.schedule != null){
            edit(this.schedule);
        }else{
            parent.getChildren().remove(tfDay);
            parent.getChildren().remove(btnSave);
        }
        
    }

    
}
