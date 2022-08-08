package Controllers.Schedules;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.FormatStyle;
import java.util.Iterator;
import java.util.ResourceBundle;

import Controllers.Controller;
import Controllers.ScheduleController;
import Helpers.ViewsPath;
import Helpers.Alert.AlertImplement;
import Helpers.Facades.IAlert;
import Helpers.Facades.View;
import Models.AppointmentBo;
import Models.AppointmentVo;
import Models.PatientVo;
import Models.ProfileVo;
import Models.ScheduleBo;
import Models.ScheduleVo;
import Models.UserVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

public class AppointmentsController extends Controller implements Initializable{

    private AppointmentVo appointment;
    private AlertImplement alert;
    private ObservableList<AppointmentVo> lAppointmentsOfPatientAndProffessional;

    //Appointment
    @FXML private TextField tfDni;
    @FXML private ComboBox<PatientVo> cbPatient;
    @FXML private ComboBox<ProfileVo> cbSpecialty;
    @FXML private ComboBox<UserVo> cbProfession;
    @FXML private Button btnAccept;
    /************************************ */
    //Calendar
    @FXML private AnchorPane pCalendar;
    @FXML private Pane pContentNotificationToday;
    @FXML private Pane pContentNotificationNext;
    /*********************************** */

    //Semana
    @FXML private Pane pWeeks;
    @FXML private Label lDayRange;
    private ObservableList<VBox> colmns;
    /*********************************** */

    //Schedule
    @FXML private Button btnAdd;
    /*********************************** */

    private void dayRange(LocalDate monday, LocalDate friday){
        DatePicker dp = new DatePicker();
        dp.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
        lDayRange.setText(dp.getConverter().toString(monday) + " - " + dp.getConverter().toString(friday));
    }

    private Accordion createAccordion(ScheduleVo scheduleVo){
        HBox container = new HBox();
        Label content = new Label(String.format("Paciente: %s\nProfesional: %s", scheduleVo.getAppointment().getPatient(), scheduleVo.getAppointment().getUser()));
        container.getChildren().add(content);
        TitledPane titledPane = new TitledPane(String.format("%s - %s", scheduleVo.getFrom(), scheduleVo.getTo()), container);
        titledPane.setAnimated(true);
        Accordion accordion = new Accordion(titledPane);
        accordion.setId(scheduleVo.toString());
        return accordion;
    }

    /**
     * Obtiene las columnas que contendran los horarios de cada día de la semana
     */
    private void getColumnsOfDayOfWeek(){
        pWeeks.getChildren().forEach(
            column->{
                VBox schedule = (VBox) ((VBox) column).getChildren().get(1);
                colmns.add(schedule);
            }
        );
    }

    public void fillWeeks(LocalDate monday, LocalDate friday, AppointmentVo appointment){
        pWeeks.setDisable(true);
        Runnable hilo = new Runnable(){

            @Override
            public void run() {
                
                dayRange(monday,friday);
        
                //Se limpian las columnas que contienen los horarios
                if(!getColmns().isEmpty()){
                    getColmns().forEach(
                        schedule->{
                            if(schedule.getChildren().size() != 0){
                                schedule.getChildren().clear();
                            }
                        }
                    );
                }
                
                getlAppointmentsOfPatientAndProffessional().forEach(
                    apmt->{
                        for (ScheduleVo schedule : ScheduleBo.getInstance().getElementsOf(apmt).filtered(
                            element->element.getDay().getTime() >= Date.valueOf(monday).getTime() 
                                    && element.getDay().getTime() <= Date.valueOf(friday).getTime()
                        )) {
                            VBox column = getColmns().get(schedule.getDay().toLocalDate().getDayOfWeek().getValue()-1);
                            if(column.getChildren().filtered(el->el.getId().equals(schedule.toString())).size() == 0){
                                column.getChildren().add(createAccordion(schedule));
                            }
                        }
                    }
                );
                pWeeks.setDisable(false);
            }
            
        };
        hilo.run();
    }
    
    //Muestra las citas para hoy
    private void notifacionToday(){
        ScheduleBo.getInstance().getElements().forEach(
            element->{
                if(element.getDay().equals(Date.valueOf(LocalDate.now()))){
                    DatingNotificationsController noti = new DatingNotificationsController(element);
                    View.getInstance().createEmbed(noti,"notificacion_cita",pContentNotificationToday);
                }
            }
        );
        
    }

    //Muestra las citas próximas
    private void notifacionNext(){
        int count = 0;
        Iterator<ScheduleVo> iterator = ScheduleBo.getInstance().getElements().iterator();
        while (iterator.hasNext()) {
            ScheduleVo schedule = iterator.next();
            if(schedule.getDay().getTime() > Date.valueOf(LocalDate.now()).getTime()){
                DatingNotificationsController noti = new DatingNotificationsController(schedule);
                View.getInstance().createEmbed(noti,"notificacion_cita",pContentNotificationNext);
                count++;
            }
            
            if(count == 4){
                break;
            }
        }
        
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        appointment = null;
        alert = new AlertImplement();
        AppointmentComponent appointmentC = new AppointmentComponent(tfDni, cbPatient,cbSpecialty,cbProfession,btnAccept);
        appointmentC.init();
        
        CalendarComponent calendar = new CalendarComponent(pCalendar,this);
        calendar.calendar();
        LocalDate monday = calendar.firstDayOfWeek(LocalDate.now());
        LocalDate friday = calendar.lastDayOfWeek(LocalDate.now());
        dayRange(monday, friday);
        colmns = FXCollections.observableArrayList();
        getColumnsOfDayOfWeek();

        btnAccept.setOnAction(
            action->{
                setAppointment(appointmentC.create());
                if(getAppointment() != null){
                    this.lAppointmentsOfPatientAndProffessional = FXCollections.observableArrayList();
                    this.lAppointmentsOfPatientAndProffessional.addAll(AppointmentBo.getInstance().getElementsOf(getAppointment().getPatient()));
                    this.lAppointmentsOfPatientAndProffessional.addAll(AppointmentBo.getInstance().getElementsOf(getAppointment().getUser()));
                    fillWeeks(monday, friday,appointment);
                }
            }
        );

        btnAdd.setOnAction(
            action->{
                ScheduleController scheduleC = new ScheduleController();
                if(this.appointment != null){
                    scheduleC.getParams().put("day",calendar.getDaySelected());
                    scheduleC.getParams().put("appointment",this.appointment);
                    scheduleC.getParams().put("appointments",this.lAppointmentsOfPatientAndProffessional);
                    View.getInstance().createModalWithWait(scheduleC, "Horario");
                    fillWeeks(
                        calendar.firstDayOfWeek(calendar.getDaySelected()), 
                        calendar.lastDayOfWeek(calendar.getDaySelected()), 
                        getAppointment());
                }else{
                    alert.alert("Seleccione un paciente y un profesional", IAlert.SIMPLE);
                }
            }
        );

        notifacionToday();
        notifacionNext();
    }

    public AppointmentVo getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentVo appointment) {
        this.appointment = appointment;
    }

    

    public ObservableList<AppointmentVo> getlAppointmentsOfPatientAndProffessional() {
        return lAppointmentsOfPatientAndProffessional;
    }

    public void setlAppointmentsOfPatientAndProffessional(
            ObservableList<AppointmentVo> lAppointmentsOfPatientAndProffessional) {
        this.lAppointmentsOfPatientAndProffessional = lAppointmentsOfPatientAndProffessional;
    }

    public ObservableList<VBox> getColmns() {
        return colmns;
    }

    public void setColmns(ObservableList<VBox> colmns) {
        this.colmns = colmns;
    }

    

    
    
}
