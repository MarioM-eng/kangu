package Controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.ResourceBundle;

import Helpers.Alert.AlertImplement;
import Helpers.Facades.IAlert;
import Models.AppointmentVo;
import Models.ScheduleBo;
import Models.ScheduleVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.util.converter.LocalDateStringConverter;

public class ScheduleController extends Controller implements Initializable{

    private AlertImplement alert;
    
    @FXML private ComboBox<Time> cbHStart;
    @FXML private ComboBox<Time> cbHEnd;
    @FXML private TextField tfDay;
    @FXML private Button btnSave;
    @FXML private Button btnDelete;

    @FXML private VBox vbScheduleContainer;
    @FXML private TableView<ScheduleVo> tbSchedules;
    @FXML private Label lbPatient;
    @FXML private Label lbProfessional;
    @FXML private TextField tfProfessional;
    @FXML private TextField tfPatient;
    
    public ScheduleController() { alert = new AlertImplement(); }

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
    

    public ScheduleVo create(){
        ScheduleVo scheduleVo = null;
        LocalDate day = (LocalDate)getParams().get("day");
        AppointmentVo appointmentVo = (AppointmentVo) getParams().get("appointment");
        Time from = cbHStart.getSelectionModel().getSelectedItem();
        Time to = cbHEnd.getSelectionModel().getSelectedItem();

        if (from != null) {
            if (to != null) {
                scheduleVo = new ScheduleVo();
                scheduleVo.setFrom(cbHStart.getSelectionModel().getSelectedItem());
                scheduleVo.setTo(cbHEnd.getSelectionModel().getSelectedItem());
                scheduleVo.setAppointment(appointmentVo);
                scheduleVo.setDay(Date.valueOf(day));
                scheduleVo = ScheduleBo.getInstance().create(scheduleVo);
                if (scheduleVo == null) {
                    alert.alert("Horario del día " + day + " no se pudo crear", IAlert.ERROR);
                }else{
                    fillNotAvaibleSchedule(scheduleVo.getDay().toLocalDate(),scheduleVo.getAppointment());
                    alert.alert("Horario agregado exitosamente", IAlert.SIMPLE);
                }
            } else {
                alert.alert("La hora de finalización no debe estar vacia", IAlert.ERROR);
            }
        } else {
            alert.alert("La hora de inicio no debe estar vacia", IAlert.ERROR);
        }

        return scheduleVo;
        
    }

    /**
     * Elimina uno o varios Schedule(Horario). (Utiliza Hilos)
     */
    private void delete(){
        vbScheduleContainer.setDisable(true);
        Runnable hilo = new Runnable() {

            @Override
            public void run() {
                
                ObservableList<ScheduleVo> deleteSchedules = tbSchedules.getSelectionModel().getSelectedItems();
                    if(deleteSchedules.size() > 0){
                        if(deleteSchedules.size() > 1){
                            alert.alert("¿Está seguro que quiere eliminar " + deleteSchedules.size() + " elementos?", IAlert.CONFIRMATION);
                            if(alert.getResponse()){
                                while(deleteSchedules.iterator().hasNext()){
                                    ScheduleVo schedule = deleteSchedules.iterator().next();
                                    if(!ScheduleBo.getInstance().delete(schedule)){
                                        alert.alert("La agenda " + schedule.getId() + " no pudo ser eliminada", IAlert.SIMPLE);
                                    }else{
                                        addHoursCombo(schedule);
                                        tbSchedules.getItems().remove(schedule);
                                    }
                                }
                                alert.alert("Elementos eliminados", IAlert.SIMPLE);
                            }
                        }else{
                            alert.alert("¿Está seguro que quiere eliminar la agenda?", IAlert.CONFIRMATION);
                            if(alert.getResponse()){
                                ScheduleVo schedule = deleteSchedules.get(0);
                                if(!ScheduleBo.getInstance().delete(schedule)){
                                    alert.alert("La agenda " + schedule.getId() + " no pudo ser eliminada", IAlert.SIMPLE);
                                }else{
                                    addHoursCombo(schedule);
                                    tbSchedules.getItems().remove(schedule);
                                    alert.alert("Agenda eliminada correctamente", IAlert.SIMPLE);
                                }
                            }
                        }
                        vbScheduleContainer.setDisable(false);
                    }
                
            }
            
        };
        hilo.run();
    }

    private void events(){
        //Evento para llenar comboboxs
        this.cbHStart.setOnAction(action->{
                if(this.cbHEnd.getSelectionModel().getSelectedItem() != null){
                    this.cbHEnd.setValue(null);
                }
                if(this.cbHStart.getValue() != null){
                    this.cbHEnd.setItems(FXCollections.observableArrayList(
                        Time.valueOf(
                                this.cbHStart.getValue()
                                        .toLocalTime()
                                        .plusMinutes(Helpers.Time.DURACION_MINIMA)),
                        Time.valueOf(
                                this.cbHStart.getValue()
                                        .toLocalTime()
                                        .plusMinutes(Helpers.Time.DURACION_MAXIMA))));
                }
            
        });
    }

    private void fillTableSchedules(){

        TableColumn<ScheduleVo,Integer> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<ScheduleVo, Integer>("id"));

        TableColumn<ScheduleVo,Time> from = new TableColumn<>("Desde");
        from.setCellValueFactory(new PropertyValueFactory<ScheduleVo, Time>("from"));

        TableColumn<ScheduleVo,Time> to = new TableColumn<>("Hasta");
        to.setCellValueFactory(new PropertyValueFactory<ScheduleVo, Time>("to"));

        TableColumn<ScheduleVo,String> patient = new TableColumn<>("Paciente");
        patient.setCellValueFactory(data -> data.getValue().getAppointment().getPatient().getNameProperty());

        TableColumn<ScheduleVo,String> professional = new TableColumn<>("Profesional");
        professional.setCellValueFactory(data -> data.getValue().getAppointment().getUser().getNameProperty());

        this.tbSchedules.getColumns().addAll(Arrays.asList(from,to,patient,professional));
        //Permite la selección múltiple 
        this.tbSchedules.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

    }

    /**
     * Quita del combobox de hora de inicio las horas que ya esten ocupadas y una hora antes de comenzar
     * @param schedule
     */
    private void removeHoursCombo(ScheduleVo schedule){
        if(cbHStart.getItems().contains(schedule.getFrom())){
            Time desde = Time.valueOf(schedule.getFrom().toLocalTime().minusHours(1));
            cbHStart.getItems().removeIf(
                ac -> ac.getTime() > desde.getTime() && ac.getTime() < schedule.getTo().getTime());
        }
    }

    /**
     * Agrega al combobox de hora de inicio las horas que no esten ocupadas y una hora antes de comenzar
     * @param schedule
     */
    private void addHoursCombo(ScheduleVo schedule) {
        Time desde = Time.valueOf(schedule.getFrom().toLocalTime().minusHours(1));
        if(!cbHStart.getItems().contains(desde)){
            desde = schedule.getFrom();
            int aux = 0;
            cbHStart.getItems().add(aux,desde);
            while (desde.getTime() < schedule.getTo().getTime()) {
                aux++; cbHStart.getItems().add(aux,desde);
                desde = Time.valueOf(desde.toLocalTime().plusMinutes(10));
            }
        }else{
            desde = Time.valueOf(desde.toLocalTime().plusMinutes(10));
            while (desde.getTime() < schedule.getTo().getTime()) {
                int index = cbHStart.getItems().indexOf(Time.valueOf(desde.toLocalTime().minusMinutes(10)));
                if (!cbHStart.getItems().contains(desde)) {
                    cbHStart.getItems().add((index+1),desde);
                }
                desde = Time.valueOf(desde.toLocalTime().plusMinutes(10));
            }
        }
    }
    
    /**
     * Carga los Schedules existentes para ese appointment(patient-proffessional)
     * @param day
     * @param appointmentVo
     */
    private void fillNotAvaibleSchedule(LocalDate day, AppointmentVo appointmentVo) {

        ObservableList<AppointmentVo> lAppointments = (ObservableList<AppointmentVo>) getParams().get("appointments");
        lAppointments.forEach(
            apmt->{
                ScheduleBo.getInstance().getElementsOf(apmt).forEach(
                schedule -> {
                    if (day.equals(schedule.getDay().toLocalDate())) {
                        
                        if(appointmentVo.equals(apmt)){
                            if(!this.tbSchedules.getItems().contains(schedule)){
                                this.tbSchedules.getItems().add(schedule);
                            }
                        }
                        removeHoursCombo(schedule);
                    }
                });
            }
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        LocalDate day = (LocalDate)getParams().get("day");
        AppointmentVo appointmentVo = (AppointmentVo)getParams().get("appointment");
        DatePicker dp = new DatePicker();
        dp.setConverter(new LocalDateStringConverter(FormatStyle.FULL));
        getTfDay().setText(dp.getConverter().toString(day));
        this.tfProfessional.setText(appointmentVo.getUser().toString());
        this.tfPatient.setText(appointmentVo.getPatient().toString());
        //parent = (Pane)btnSave.getParent();
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
        fillNotAvaibleSchedule(day,appointmentVo);
        fillTableSchedules();
        btnSave.setOnAction(
            action->create()
        );
        btnDelete.setOnAction(
                action -> {
                    delete();
                });
        
    }

    
}
