package Controllers.Schedules;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.ResourceBundle;

import Controllers.Controller;
import Models.AppointmentBo;
import Models.PatientVo;
import Models.Person;
import Models.ScheduleBo;
import Models.ScheduleVo;
import Models.UserVo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class SchedulesController extends Controller implements Initializable {

    @FXML
    private TableView<ScheduleVo> tbSchedules;

    private void columnTable(){

        TableColumn<ScheduleVo,String> tColumnDay = new TableColumn<>("Día");
        tColumnDay.setCellValueFactory(new PropertyValueFactory<ScheduleVo, String>("day"));

        TableColumn<ScheduleVo,String> tColumnFrom = new TableColumn<>("Desde");
        tColumnFrom.setCellValueFactory(new PropertyValueFactory<ScheduleVo, String>("from"));

        TableColumn<ScheduleVo,String> tColumnTo = new TableColumn<>("Hasta");
        tColumnTo.setCellValueFactory(new PropertyValueFactory<ScheduleVo, String>("to"));

        TableColumn<ScheduleVo,String> tColumnCon = new TableColumn<>("Con");
        tColumnCon.setCellValueFactory(data->data.getValue().getAppointment().getUser().getNameProperty());

        this.tbSchedules.getColumns().addAll(Arrays.asList(tColumnDay,tColumnFrom,tColumnTo,tColumnCon));
    }

    private void getSchedules(Person person) {
        AppointmentBo.getInstance()
                .getElementsOf(person)
                .forEach(
                        appointment -> {
                            ScheduleBo.getInstance().getElementsOf(appointment).forEach(
                                    schedule -> {
                                        if(schedule.getDay().getTime() >= Date.valueOf(LocalDate.now()).getTime()){
                                            this.tbSchedules.getItems().add(schedule);
                                        }
                                    });
                        });
    }

    private void fillTable(){
        UserVo user = (UserVo)getParams().get("proffessional");
        PatientVo patient = (PatientVo)getParams().get("patient");
        if(user != null){
            getSchedules(user);
        }
        if(patient != null){
            getSchedules(patient);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillTable();
        columnTable();
        
    }


    
}
