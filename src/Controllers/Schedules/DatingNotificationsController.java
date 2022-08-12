package Controllers.Schedules;

import java.net.URL;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import Controllers.Controller;
import Models.ScheduleVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.util.converter.LocalDateStringConverter;

public class DatingNotificationsController extends Controller implements Initializable {

    @FXML private Label lbDay;
    @FXML private Label lbProfessional;
    @FXML private Label lbPatient;
    @FXML private Label lbFrom;
    @FXML private Label lbTo;

    public static ObservableList<ScheduleVo> schedules = FXCollections.observableArrayList();

    public DatingNotificationsController(ScheduleVo scheduleVo) {
        schedules.add(scheduleVo);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatePicker dp = new DatePicker();
        dp.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
        if(!schedules.isEmpty()){
            ScheduleVo sche = schedules.get(schedules.size()-1);
            lbDay.setText(dp.getConverter().toString(sche.getDay().toLocalDate()));
            lbProfessional.setText(sche.getAppointment().getUser().toString());
            lbPatient.setText(sche.getAppointment().getPatient().toString());
            lbFrom.setText(sche.getFrom().toString());
            lbTo.setText(sche.getTo().toString());
        }
    }

    
}
