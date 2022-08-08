package Controllers.Schedules;

import java.net.URL;
import java.time.format.FormatStyle;
import java.util.ResourceBundle;

import Controllers.Controller;
import Models.ScheduleVo;
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

    private ScheduleVo scheduleVo;

    public DatingNotificationsController(ScheduleVo scheduleVo) {
        this.scheduleVo = scheduleVo;
    }

    public ScheduleVo getScheduleVo() {
        return scheduleVo;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        DatePicker dp = new DatePicker();
        dp.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
        lbDay.setText(dp.getConverter().toString(getScheduleVo().getDay().toLocalDate()));
        lbProfessional.setText(getScheduleVo().getAppointment().getUser().toString());
        lbPatient.setText(getScheduleVo().getAppointment().getPatient().toString());
        lbFrom.setText(getScheduleVo().getFrom().toString());
        lbTo.setText(getScheduleVo().getTo().toString());
        
    }

    
}
