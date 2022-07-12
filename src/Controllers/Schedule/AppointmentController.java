package Controllers.Schedule;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import Controllers.Controller;
import Controllers.ProfessionalController;
import Controllers.ScheduleController;
import Helpers.Calendar.DateCalendar;
import Helpers.Facades.View;
import Models.AppointmentBo;
import Models.AppointmentVo;
import Models.PatientVo;
import Models.ScheduleBo;
import Models.ScheduleVo;
import Models.UserVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

public class AppointmentController extends Controller implements Initializable {

    @FXML
    private Pane pNumber1;
    @FXML
    private Pane pNumber2;
    @FXML
    private Pane pCalendar;
    @FXML
    private Separator sPane;
    @FXML
    private Button btnSave;
    @FXML
    private Label lbSchedule;
    @FXML
    private CheckBox cbRangeMode;

    //WorkingHour
    @FXML
    private HBox paneProffesional;
    private ProfessionalController professional;
    /*************************** */

    //Schedule
    @FXML
    private Pane paneSchedule;
    private ScheduleController schedule;
    /******************* */

    //Intervals
    @FXML
    private GridPane gpDays;
    private IntervalComponent interval;
    /************************* */

    private DateCalendar dateCalendar;
    private AppointmentVo appointmentVo;
    ObservableList<ScheduleVo> unvailablePatientHours;

    public AppointmentController(){
        unvailablePatientHours = FXCollections.observableArrayList();
    }

    
    public AppointmentVo getAppointmentVo() {
        return appointmentVo;
    }

    public void setAppointmentVo(AppointmentVo appointmentVo) {
        this.appointmentVo = appointmentVo;
    }

    public ObservableList<ScheduleVo> getUnvailablePatientHours() {
        return unvailablePatientHours;
    }
    public void setUnvailablePatientHours(ObservableList<ScheduleVo> unvailablePatientHours) {
        this.unvailablePatientHours = unvailablePatientHours;
    }

    public ProfessionalController getProfessional() {
        return professional;
    }
    public void setProfessional(ProfessionalController professional) {
        this.professional = professional;
    }

    public ScheduleController getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleController schedule) {
        this.schedule = schedule;
    }

    public DateCalendar getDateCalendar() {
        return dateCalendar;
    }

    public void setDateCalendar(DateCalendar dateCalendar) {
        this.dateCalendar = dateCalendar;
    }

    public CheckBox getCbRangeMode() {
        return cbRangeMode;
    }
    public void setCbRangeMode(CheckBox cbRangeMode) {
        this.cbRangeMode = cbRangeMode;
    }


    /**
     * Carga los componentes de schedule y calendar
     */
    private void chargeComponents(){
        ObservableMap<LocalDate,ScheduleVo> list = FXCollections.observableHashMap();
        ScheduleVo schedule = null;
        boolean check = false;
        for (ScheduleVo sche : ScheduleBo.getInstance()
                                    .getElementsOf(
                                        getAppointmentVo()
                                        )
            ) 
        {
            list.put(sche.getDay().toLocalDate(),sche);
            if(schedule == null){
                schedule = sche;
            }
            if(schedule.getFrom().equals(sche.getFrom())){
                check = true;
            }
        }
        if(check){
            getSchedule().chargeSchedule(schedule,null);
        }
        getDateCalendar().daysSelected(list);
    }


    public void charge(){
        PatientVo patientVo = (PatientVo)getParams().get("patient");
        UserVo userVo = (UserVo)getParams().get("professional");
        AppointmentVo appointment = new AppointmentVo();
        if (patientVo != null && userVo != null) {
            appointment.setPatient(patientVo);
            appointment.setUser(userVo);

            AppointmentBo.getInstance().getElements().forEach(
                element -> {
                    if(element.getPatient().equals(appointment.getPatient()) && 
                    element.getUser().equals(appointment.getUser())){
                        appointment.setId(element.getId());
                        setAppointmentVo(element);
                    }
                }
            );
            /*Si la variable appointment tiene un id de 0(No tiene) es porque no existe
            en la base de datos*/
            if(appointment.getId() == 0){
                setAppointmentVo(appointment);
            }
            lbSchedule.setText(
                String.format("Sesiones con %s", getAppointmentVo().getUser())
                );
            System.out.println("Cita obtenida correctamente");
            chargeComponents();
        }else{
            System.out.println("No se pudo obtener la cita");
        }
    }

    private AppointmentVo create(){
        appointmentVo = AppointmentBo.getInstance().create(getAppointmentVo());
        return appointmentVo;
    }

    private void save() {
        AppointmentVo appointmentVo = create();
        if(appointmentVo != null){
            Runnable hilo = new Runnable() {

                @Override
                public void run() {

                    getDateCalendar().getMarkedDays().forEach(
                            (key, value) -> {
                                getSchedule().create(appointmentVo, value);
                            });
                    System.out.println("Proceso terminado");

                }

            };
            hilo.run();
        }else{
            System.out.println("No se pudieron crear las citas");
        }
        
    }

    private void chargeCalendar(){
        this.dateCalendar.calendar();
    }

    /**
     * Busca los appointments(citas) con las cuales está relacionado el paciente evaluado
     * Luego obtiene las schedules(horarios) de cada paciente y los que coincidan con la
     * hora establecida serán almacenados en una lista para luego retornarla
     * @return lista de horas no disponibles del paciente
     */
    private void unavailablePatientHours(){
        AppointmentBo.getInstance()
        .getElementsOf(
            getAppointmentVo().getPatient()
            ).forEach(
                appointment->{
                    ScheduleBo.getInstance().getElementsOf(appointment).forEach(
                        schedule->{
                            if(getDateCalendar().getMarkedDays().containsKey(schedule.getDay().toLocalDate())){
                                if(schedule.getFrom().equals(getSchedule().getCbHStart().getSelectionModel().getSelectedItem())){
                                    //System.out.println(schedule.getFrom());
                                    getUnvailablePatientHours().add(schedule);
                                    //patientSchedule.add(schedule);
                                }
                            }
                        }
                    );
                }
            );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        setDateCalendar(new DateCalendar(pCalendar));
        getDateCalendar().setAppointmentController(this);


        interval = new IntervalComponent(dateCalendar,this.gpDays);
        interval.days();

        getCbRangeMode().setOnAction(
            action->{
                if(getCbRangeMode().isSelected()){
                    interval.getGpDays().setDisable(false);
                }else{
                    interval.valuesDefault();
                    interval.getGpDays().setDisable(true);
                }
            }
        );
        
        
        chargeCalendar();

        setProfessional(new ProfessionalController(this));
        View.getInstance().createScene(getProfessional(), "Profesional", paneProffesional);

        setSchedule(new ScheduleController(getDateCalendar()));
        View.getInstance().createScene(this.schedule, "Horario", paneSchedule);

        btnSave.setOnAction(
            //action->save()
            action->{
                //Carga dentro de la lista unvailablePatientHours los schedules que coinciden con el paciente
                /* unavailablePatientHours();
                    getDateCalendar().getDateCells().forEach(
                        cell->{
                            getUnvailablePatientHours().forEach(
                                sche->{
                                    if(cell.getItem().equals(sche.getDay().toLocalDate())){
                                        
                                        getDateCalendar().dateCellcolor(Color.YELLOW, cell);
                                    }
                                }
                            );
                        }
                    ); */
                    /* Alert a = new Alert(AlertType.CONFIRMATION);
                    a.setTitle("Lol");
                    a.setHeaderText(null);
                    a.setContentText("Contenido");
                    a.initStyle(StageStyle.UTILITY);
                    a.showAndWait(); */
                    getDateCalendar().eventPrueba();
            }
        );
    }
    
}
