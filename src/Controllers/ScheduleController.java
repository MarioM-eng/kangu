package Controllers;

import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import Helpers.HelperENCRYPT;
import Models.DayBo;
import Models.DayVo;
import Models.PatientVo;
import Models.ProfileBo;
import Models.ProfileVo;
import Models.ScheduleBo;
import Models.ScheduleVo;
import Models.UserBo;
import Models.UserVo;
import Models.Relationships.ManyToMany;
import Models.Relationships.ManyToManyBo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ScheduleController implements Initializable {

    private Object param;
    ManyToManyBo<UserVo, ProfileVo, UserBo, ProfileBo> manyToManyBo;
    ManyToManyBo<DayVo,ScheduleVo,DayBo,ScheduleBo> dayScheduleBo;

    @FXML
    private HBox hBBottom;

    @FXML
    private Pane pNumber1;
    @FXML
    private Pane pNumber2;

    @FXML
    private Separator sPane;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnSave;

    @FXML
    private ComboBox<ProfileVo> cbSpecialty;
    @FXML
    private ComboBox<UserVo> cbProfession;
    @FXML
    private ComboBox<Time> cbHStart;
    @FXML
    private ComboBox<Time> cbHEnd;

    @FXML
    private GridPane gpDays;

    @FXML
    private DatePicker dpFrom;
    @FXML
    private DatePicker dpTo;

    @FXML
    private TableView<UserVo> tbElements;

    @FXML
    private Label lbSchedule;

    private ObservableList<UserVo> professionals;
    private DatePickerSkin datePickerSkin;

    public ScheduleController(Object param) {
        this.param = param;
        manyToManyBo = new ManyToManyBo<>(
            new UserVo(), 
            new ProfileVo(), 
            UserBo.getInstance(), 
            ProfileBo.getInstance());
        professionals = FXCollections.observableArrayList();
        dayScheduleBo = new ManyToManyBo<>(
            new DayVo(),
            new ScheduleVo(),
            DayBo.getInstance(),
            ScheduleBo.getInstance());
    }

    public ScheduleController() {
        manyToManyBo = new ManyToManyBo<>(
            new UserVo(), 
            new ProfileVo(), 
            UserBo.getInstance(), 
            ProfileBo.getInstance());
        professionals = FXCollections.observableArrayList();
        dayScheduleBo = new ManyToManyBo<>(
            new DayVo(),
            new ScheduleVo(),
            DayBo.getInstance(),
            ScheduleBo.getInstance());
    }

    private void add(ActionEvent actionEvent) {

        UserVo userVo = cbProfession.getSelectionModel().getSelectedItem();
        professionals.add(userVo);

    }

    private void remove(ActionEvent actionEvent) {

        UserVo userVo = cbProfession.getSelectionModel().getSelectedItem();
        professionals.remove(userVo);

    }

    private void save(ActionEvent actionEvent) {
        UserVo userVo = tbElements.getSelectionModel().getSelectedItem();

        ScheduleVo scheduleVo = new ScheduleVo();
        scheduleVo.setPatient((PatientVo) param);
        scheduleVo.setUser(userVo);
        scheduleVo.setFrom(Date.valueOf(dpFrom.getValue()));
        scheduleVo.setTo(Date.valueOf(dpTo.getValue()));
        scheduleVo.setHourFrom(cbHStart.getValue());
        scheduleVo.setHourTo(cbHEnd.getValue());
        //System.out.println(scheduleVo.getHourTo().toString());

        scheduleVo = ScheduleBo.getInstance().create(scheduleVo);

        if (scheduleVo == null) {
            System.out.println("El paciente ya está agendado con este profesional");
        }else{
            for (Node node: gpDays.getChildren()) {
                int id = Integer.parseInt(((CheckBox)node).getId());
                ManyToMany<DayVo,ScheduleVo> daySchedule = new ManyToMany<DayVo,ScheduleVo>();
                daySchedule.setElement_1(new DayVo(id,Helpers.Time.SEMANA[id]));
                daySchedule.setElement_2(scheduleVo);
                dayScheduleBo.create(daySchedule);
            }
        }

    }
    
    private void valuesDefault(){
        dpFrom.setValue(LocalDate.now());
        dpTo.setValue(null);
        for (Node node: gpDays.getChildren()) {
            ((CheckBox)node).setSelected(false);
        }
        cbHStart.getSelectionModel().clearSelection();
        cbHEnd.setItems(null);
    }

    private boolean checkHour(){
        UserVo userVo = tbElements.getSelectionModel().getSelectedItem();
        List<Time> horas = new ArrayList<>();
        for (ScheduleVo scheduleVo : ScheduleBo.getInstance().getElements()) {
            if(dpTo.getValue().getDayOfYear() > scheduleVo.getFrom().toLocalDate().getDayOfYear()){
                if(scheduleVo.getUser().equals(userVo) || scheduleVo.getPatient().equals(param)){
                    horas.add(scheduleVo.getHourFrom());
                    horas.add(scheduleVo.getHourTo());
                }
            }
        }
        return false;
    }

    private void days(){
        for (int i = 0; i < gpDays.getChildren().size(); i++) {
            CheckBox checkBox = ((CheckBox) gpDays.getChildren().get(i));
            checkBox.setId(String.valueOf(Helpers.Time.dayNumberOfWeek(Helpers.Time.SEMANA[i])));
            checkBox.setOnAction(action->{
                if(checkBox.isSelected()){
                    if(checkBox.getId().equals("8")){
                        for(Node node: gpDays.getChildren()){
                            if(!node.getId().equals("8")){
                                ((CheckBox)node).setSelected(false);
                                ((CheckBox)node).setDisable(true);
                            }
                        }
                    }
                    elegirRepeticionDeCalendario();
                } else{
                    for(Node node: gpDays.getChildren()){
                        if(!node.getId().equals("8")){
                            ((CheckBox)node).setDisable(false);
                        }
                    }
                    elegirRepeticionDeCalendario();
                }
            });
        }
    }

    private void tableComumn(TableView<UserVo> tblElements) {

        TableColumn<UserVo, String> tColumnDni = new TableColumn<>("DNI");
        tColumnDni.setMaxWidth(5000);
        tColumnDni.setCellValueFactory(data -> data.getValue().getDniProperty());

        TableColumn<UserVo, String> tColumnName = new TableColumn<>("Nombre");
        tColumnName.setMaxWidth(5000);
        tColumnName.setCellValueFactory(data -> data.getValue().getNameProperty());

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni, tColumnName));
    }

    private void filltable(TableView<UserVo> tblElements) {
        tableComumn(tblElements);
        tblElements.setItems(professionals);
    }

    private void events() {
        // Evento para que el comboBox de profesionales se llene dependiendo la
        // especialidad
        cbSpecialty.getSelectionModel().selectedItemProperty().addListener(event -> {

            ProfileVo profileVo = cbSpecialty.getSelectionModel().getSelectedItem();

            fillComboBox(cbProfession, manyToManyBo.makeAObject(UserVo.class, profileVo));

        });

        // Evento para que al seleccionar un elemento de la tabla, se active la agenda
        tbElements.getSelectionModel().selectedItemProperty().addListener(
                event -> {
                    UserVo userVo = tbElements.getSelectionModel().getSelectedItem();
                    hBBottom.setDisable(false);
                    lbSchedule.setText("Sesiones con " + userVo.getName());
                    ScheduleVo scheduleVo = null;
                    for (ScheduleVo schedule : ScheduleBo.getInstance().getElements()) {
                        if (schedule.getPatient().equals(param) && schedule.getUser().equals(userVo)) {
                            scheduleVo = schedule;
                        }
                    }
                    if(scheduleVo != null){
                        dpFrom.setValue(scheduleVo.getFrom().toLocalDate());
                        dpTo.setValue(scheduleVo.getTo().toLocalDate());
                        for (Node node: gpDays.getChildren()) {
                            CheckBox  checkbox = ((CheckBox)node);
                            for (ManyToMany<DayVo,ScheduleVo> manyToMany : dayScheduleBo.getElements()) {
                                if(manyToMany.getElement_2().equals(scheduleVo)){
                                    if(String.valueOf(manyToMany.getElement_1().getId()).equals(checkbox)){
                                        checkbox.setSelected(true);
                                    }
                                }
                            }
                        }
                        cbHStart.getSelectionModel().select(scheduleVo.getHourFrom());
                        cbHEnd.getSelectionModel().select(scheduleVo.getHourTo());
                    }else{
                        valuesDefault();
                    }
                });

        dpFrom.setOnAction(action -> {
            elegirRepeticionDeCalendario();
        });
        dpTo.setOnAction(action -> {
            elegirRepeticionDeCalendario();
        });
        /* cbRepeat.setOnAction(action -> {
            elegirRepeticionDeCalendario();
        }); */

        cbHStart.setOnAction(action->{
            if(cbHStart.getValue() != null){
                cbHEnd.setItems(FXCollections.observableArrayList(
                Time.valueOf(
                cbHStart.getValue()
                .toLocalTime()
                .plusMinutes(Helpers.Time.DURACION_MINIMA)
                ),
                Time.valueOf(
                cbHStart.getValue()
                .toLocalTime()
                .plusMinutes(Helpers.Time.DURACION_MAXIMA)
                )
                ));
            }
        });
    }

    private void elegirRepeticionDeCalendario() {
        List<Integer> repetir = new ArrayList<>();
        for (Node node: gpDays.getChildren()) {
            CheckBox  checkbox = ((CheckBox)node);
            if(checkbox.isSelected()){
                repetir.add(Helpers.Time
                .dayNumberOfWeek(checkbox.getText()));
            }
        }
        if(repetir.isEmpty()){
            repetir.add(9);
        }
        calendar(repetir);
    }

    /* private void chargeSchedule(ScheduleVo scheduleVo) {

    } */

    private void fillComboBox(ComboBox combo, ObservableList list) {
        combo.setItems(list);
    }

    private DatePicker calendarActual(List<Integer> rep) {
        DatePicker c;
        c = new DatePicker(LocalDate.now());
        c.setDayCellFactory(dp -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {

                super.updateItem(item, empty);

                this.setDisable(false);
                this.setBackground(null);
                this.setTextFill(Color.BLACK);

                // deshabilitar las fechas futuras
                if (item.isBefore(LocalDate.now())) {
                    this.setDisable(true);
                }

                // marcar los dias de quincena
                int day = item.getDayOfYear();
                if (dpTo.getValue() == null) {
                    if (day == dpFrom.getValue().getDayOfYear()) {
                        if (item.isAfter(LocalDate.now().minusDays(1))) {
                            if (item.getDayOfWeek() != DayOfWeek.SATURDAY && item.getDayOfWeek() != DayOfWeek.SUNDAY) {
                                Paint color = Color.RED;
                                BackgroundFill fill = new BackgroundFill(color, null, null);

                                this.setBackground(new Background(fill));
                                this.setTextFill(Color.WHITESMOKE);
                            }
                        }
                    }
                } else {
                    if (day >= dpFrom.getValue().getDayOfYear() && day <= dpTo.getValue().getDayOfYear()) {
                        if (item.isAfter(LocalDate.now().minusDays(1))) {
                            if (item.getDayOfWeek() != DayOfWeek.SATURDAY && item.getDayOfWeek() != DayOfWeek.SUNDAY) {
                                if(dpTo.isDisable()){
                                    dpTo.setDisable(false);
                                }
                                for (int i : rep) {
                                    if (item.getDayOfWeek().getValue() == i) {
                                        Paint color = Color.RED;
                                        BackgroundFill fill = new BackgroundFill(color, null, null);
    
                                        this.setBackground(new Background(fill));
                                        this.setTextFill(Color.WHITESMOKE);
                                    } else if (i == 8) {
                                        if(!dpTo.isDisable()){
                                            dpTo.setDisable(true);
                                        }
                                        if (item.getDayOfYear() == dpFrom.getValue().getDayOfYear()) {
                                            Paint color = Color.RED;
                                            BackgroundFill fill = new BackgroundFill(color, null, null);
    
                                            this.setBackground(new Background(fill));
                                            this.setTextFill(Color.WHITESMOKE);
                                        }
                                    } else if (i == 9) {
                                        Paint color = Color.RED;
                                        BackgroundFill fill = new BackgroundFill(color, null, null);
    
                                        this.setBackground(new Background(fill));
                                        this.setTextFill(Color.WHITESMOKE);
    
                                    }
                                }
                            }
                        }
                    }
                }

                // fines de semana en color verde
                DayOfWeek dayweek = item.getDayOfWeek();
                if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
                    this.setTextFill(Color.GREEN);
                }
            }
        });
        return c;
    }

    private void calendar(List<Integer> repetir) {
        // Se obtiene el Layout que contendrá el calendario
        VBox vBox = (VBox) pNumber1.getChildren().get(0);
        if (datePickerSkin != null) {
            vBox.getChildren().remove(datePickerSkin.getPopupContent());
        }
        datePickerSkin = new DatePickerSkin(calendarActual(repetir));
        // Este método debe devolver el Nodo que se mostrará cuando el usuario haga clic
        // en el área de 'botón' de ComboBox.
        Node popupContent = datePickerSkin.getPopupContent();
        // Se le agrega un id al calendario
        popupContent.setId("calendar");
        // Se asegura que al calendadio sea el último nodo agregado
        vBox.getChildren().add(vBox.getChildren().size(), popupContent);

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // calendar();

        dpFrom.setValue(LocalDate.now());
        elegirRepeticionDeCalendario();
        events();
        days();
        fillComboBox(cbSpecialty, ProfileBo.getInstance().getElements().filtered(
                el -> el.getId() != 1 && el.getId() != 2));

        ObservableList<Time> list = FXCollections.observableArrayList();
        for (int hora : Helpers.Time.HORAS) {
            if(hora > 7 && hora < 18){
                for (int minuto : Helpers.Time.MINUTOS) {
                    list.add(Time.valueOf(LocalTime.of(hora, minuto)));
                }
            }
        }
        cbHStart.setItems(list);

        filltable(tbElements);

        btnAdd.setOnAction(actionEvent -> {
            add(actionEvent);
        });
        btnRemove.setOnAction(actionEvent -> {
            remove(actionEvent);
        });
        btnSave.setOnAction(actionEvent -> {
            save(actionEvent);
        });

    }

}
