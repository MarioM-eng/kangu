package Helpers.Calendar;

import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import Controllers.Schedule.AppointmentController;
import Models.ScheduleVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class DateCalendar {

    private DatePickerSkin datePickerSkin;
    private Pane container;
    private ObservableList<Integer> daysOfWeekEnabled;
    private ObservableMap<LocalDate,ScheduleVo> markedDays;
    private ObservableList<ScheduleVo> daysDisabled;
    private LocalDate dpFrom, dpTo;
    private DatePicker calendarDatePicker;
    private List<DateCell> dateCells;
    private AppointmentController AppointmentController;

    public DateCalendar(Pane container) {
        init(container);
    }

    

    
    public AppointmentController getAppointmentController() {
        return AppointmentController;
    }
    public void setAppointmentController(AppointmentController appointmentController) {
        AppointmentController = appointmentController;
    }

    public List<DateCell> getDateCells() {
        return dateCells;
    }

    public void setDateCells(List<DateCell> dateCells) {
        this.dateCells = dateCells;
    }

    public ObservableList<Integer> getDaysOfWeekEnabled() {
        return daysOfWeekEnabled;
    }

    public void setDaysOfWeekEnabled(ObservableList<Integer> daysOfWeekEnabled) {
        this.daysOfWeekEnabled = daysOfWeekEnabled;
    }

    public ObservableMap<LocalDate,ScheduleVo> getMarkedDays() {
        return markedDays;
    }

    public void setMarkedDays(ObservableMap<LocalDate,ScheduleVo> markedDays) {
        this.markedDays = markedDays;
    }

    public LocalDate getDpFrom() {
        return dpFrom;
    }

    public void setDpFrom(LocalDate dpFrom) {
        this.dpFrom = dpFrom;
    }

    public LocalDate getDpTo() {
        return dpTo;
    }

    public void setDpTo(LocalDate dpTo) {
        this.dpTo = dpTo;
    }




    public ObservableList<ScheduleVo> getDaysDisabled() {
        return daysDisabled;
    }
    public void setDaysDisabled(ObservableList<ScheduleVo> daysDisabled) {
        this.daysDisabled = daysDisabled;
    }




    private void events(DatePicker datePicker){}

    public void eventPrueba(){
        System.out.println(getMarkedDays().size());
    }

    /**
     * Selecciona un rango entre dos fechas seleccionadas del calendario
     * @param item día seleccionado del calendario
     */
    private void selecionOfDateRange(LocalDate item){
        if (getDpFrom() == null && getDpTo() == null) {
            getMarkedDays().clear();
            setDpFrom(item);
        } else if (getDpFrom() != null && getDpTo() == null) {
            setDpTo(item);
            if (Date.valueOf(getDpFrom()).getTime() > Date.valueOf(getDpTo()).getTime()) {
                getMarkedDays().clear();
                setDpFrom(item);
                setDpTo(null);
            }
        } else if (getDpFrom() != null && getDpTo() != null) {
            getMarkedDays().clear();
            setDpFrom(item);
            setDpTo(null);
        }
        daysSelected();
    }

    private void dateCellevent(LocalDate item,DateCell dateCell){
        dateCell.setOnMouseClicked(
                action -> {
                    if(action.getButton().equals(MouseButton.PRIMARY)){
                        if(getAppointmentController().getCbRangeMode().isSelected()){
                            selecionOfDateRange(item);
                        }else{
                            daySelected(item);
                        }
                    }
                }
        );
    }

    /**
     * Actualiza las celdas con la lista de días marcados(markedDays) dada
     * @param markedDays lista con los días marcados
     */
    public void daysSelected(ObservableMap<LocalDate,ScheduleVo> markedDays){
        setMarkedDays(markedDays);
        markDyas();
    }

    /**
     * Agrega a la lista de días marcados(markedDays) un día y marca la celda
     */
    private void daySelected(LocalDate day){
        if (!getMarkedDays().containsKey(day)) {
            ScheduleVo scheduleVo = new ScheduleVo();
            scheduleVo.setDay(Date.valueOf(day));
            getMarkedDays().put(day, scheduleVo);
            markCell(findDateCell(day));
        }
    }
    /**
     * Agrega a la lista de días marcados(markedDays) un día y marca la celda
     */
    private void dayUnSelected(LocalDate day){
        if (getMarkedDays().containsKey(day)) {
            getMarkedDays().remove(day);
            unmarkCell(findDateCell(day));
        }
    }

    /**
     * LLena la lista de días marcados(markedDays) y marca las celdas
     */
    public void daysSelected(){
        ScheduleVo scheduleVo;
        if(getDpFrom() != null){
            //System.out.println(contar++);
            if (getDpTo() == null) {
                if (getDpFrom().isAfter(LocalDate.now().minusDays(1))) {
                    if (!getMarkedDays().containsKey(getDpFrom())) {
                        scheduleVo = new ScheduleVo();
                        scheduleVo.setDay(Date.valueOf(getDpFrom()));
                        getMarkedDays().put(getDpFrom(), scheduleVo);
                    }
                }
            }else{
                LocalDate day = getDpFrom();
                while (Date.valueOf(day).getTime() <= Date.valueOf(getDpTo()).getTime()) {
                    if (day.isAfter(LocalDate.now().minusDays(1))) {
                        if(!isWeekend(day.getDayOfWeek())){
                            LocalDate dayEnabled = checkDayEnabled(day);
                            if(dayEnabled != null){
                                if (!getMarkedDays().containsKey(day)) {
                                    scheduleVo = new ScheduleVo();
                                    scheduleVo.setDay(Date.valueOf(day));
                                    getMarkedDays().put(day, scheduleVo);
                                }
                            } else{
                                if (getMarkedDays().containsKey(day)) {
                                    getMarkedDays().remove(day);
                                }
                            }
                        }
                    }
                    day = day.plusDays(1);
                }
            }
        }
        markDyas();
    }
    
    /**
     * Verifica si el día seleccionado hace parte de 
     * la lista de dias de la semana seleccionados previamente.
     * @param day día de la semana
     * @return si en la lista de diás de la semana hablitados se encuentra un 9, cualquier día
     * que se pasé será retornado. En caso de que en la lista esté un 8, se retornará solo
     * la fecha del día en que comienza el rango. Si la variable <code> day </code> coincide
     * con un día de la semana que esté en la lista, se retornará ese día, de lo contrario 
     * retornará null.
     */
    private LocalDate checkDayEnabled(LocalDate item){
        if(getDaysOfWeekEnabled().contains(Integer.valueOf(8))){
            if (item.equals(getDpFrom())) {
                /* if (!getMarkedDays().contains(item)) {
                    getMarkedDays().add(item);
                } */
                return item;
            } else {
                /* if (getMarkedDays().contains(item)) {
                    getMarkedDays().remove(item);
                } */
                return null;
            }
        } else if(getDaysOfWeekEnabled().contains(Integer.valueOf(9))){
            /* if (!getMarkedDays().contains(item)) {
                getMarkedDays().add(item);
            } */
            return item;
        } else if(getDaysOfWeekEnabled().contains(Integer.valueOf(item.getDayOfWeek().getValue()))){
            /* if (!getMarkedDays().contains(item)) {
                getMarkedDays().add(item);
            } */
            return item;
        } else {
            /* if (getMarkedDays().contains(item)) {
                getMarkedDays().remove(item);
            } */
            return null;
        }
    }
    
    /**
     * Verifica si el día es fin de semana
     * @param day el día de la semana a evaluar
     * @return <code>true</code> si el día es fin de semana <code>false</code> si no
     */
    private boolean isWeekend(DayOfWeek day){
        if(day != DayOfWeek.SATURDAY && day != DayOfWeek.SUNDAY){
            return false;
        }else{
            return true;
        }
    }

    /**
     * Marca las celdas del día correspondiente de color azul en el calendario
     */
    public void markDyas() {
        if(!getDateCells().isEmpty()){
            getDateCells().forEach(
                cell -> {
                    unmarkCell(cell);
                    getMarkedDays().forEach(
                            (day,schedule) -> {
                                if (cell.getItem().equals(day)) {
                                    markCell(cell);
                                }
                            });
                });
        }else{
            getDateCells().forEach(
                cell -> {
                    unmarkCell(cell);
                });
        }
    }

    /**
     * Encuentra un DateCell dependiendo el día
     * @param day día del cúal se quiere encontrar el DateCell
     * @return el DateCell
     */
    private DateCell findDateCell(LocalDate day){
        for (DateCell dCell : getDateCells()) {
            if(dCell.getItem().equals(day)){
                return dCell;
            }
        }
        return null;
    }

    /**
     * Desmarca la celda que se pase
     * @param cell
     */
    private void unmarkCell(DateCell cell) {
        dateCellcolorDefault(cell);
        cell.getContextMenu().getItems().get(0)
        .setDisable(true);
    }

    /**
     * Marca la celda que se pase
     * @param cell
     */
    private void markCell(DateCell cell) {
        dateCellcolor(Color.BLUE, cell);
        cell.getContextMenu().getItems().get(0)
        .setDisable(false);
    }

    /**
     * Retorna el DatePicker(Calendario) con el cual tomará el DatePickerSckin
     * @return el DatePicker con sus celdas editadas
     */
    private DatePicker calendarActual() {

        calendarDatePicker.setDayCellFactory(dp -> new DateCell() {

            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                this.setDisable(false);
                dateCellcolorDefault(this);
                ContextMenu cMenuCell = new ContextMenu();
                MenuItem unmark = new MenuItem("Desmarcar");
                MenuItem unmarkAll = new MenuItem("Desmarcar todo");
                unmark.setOnAction(
                        action->{
                            dayUnSelected(this.getItem());
                        }
                    );
                unmarkAll.setOnAction(
                    action->{
                        getMarkedDays().clear();
                        setDpFrom(null);
                        setDpTo(null);
                        daysSelected();
                    }
                    );
                cMenuCell.getItems().addAll(unmark,unmarkAll);
                this.setContextMenu(cMenuCell);

                // deshabilitar las fechas anteriores
                if (item.isBefore(LocalDate.now())) {
                    this.setDisable(true);
                }
                // No agregar fines de semana y colorearlos de verde
                DayOfWeek dayweek = item.getDayOfWeek();
                if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
                    this.setDisable(true);
                    this.setTextFill(Color.GREEN);
                }else if(dayweek != DayOfWeek.SATURDAY && dayweek != DayOfWeek.SUNDAY){
                    this.setCursor(Cursor.HAND);
                    if(!getDateCells().contains(this)){
                        getDateCells().add(this);
                    }
                    
                    if(getMarkedDays().containsKey(this.getItem())){
                        markCell(this);
                    }else{
                        unmarkCell(this);
                    }
                    dateCellevent(calendarDatePicker.getValue(),this);
                }
            }
        });
        events(calendarDatePicker);
        return calendarDatePicker;
    }

    /**
     * Carga el calendario
     */
    public void calendar() {
        // Se obtiene el Layout que contendrá el calendario
        if (datePickerSkin != null) {
            container.getChildren().remove(datePickerSkin.getPopupContent());
        }
        DatePicker d = calendarActual();

        datePickerSkin = new DatePickerSkin(d);

        // Este método debe devolver el Nodo que se mostrará cuando el usuario haga clic
        // en el área de 'botón' de ComboBox.
        Node popupContent = datePickerSkin.getPopupContent();
        // Se le agrega un id al calendario
        popupContent.setId("calendar");
        
        // Se asegura que al calendadio sea el último nodo agregado
        container.getChildren().add(container.getChildren().size(), popupContent);
    }

    /**
     * Colorea una celda de un calendario
     * @param color color con el que se coloreará
     * @param dateCell Celda que se va a colorear
     */
    public void dateCellcolor(Paint color, DateCell dateCell){
        BackgroundFill fill = new BackgroundFill(color, null, null);

        dateCell.setBackground(new Background(fill));
        dateCell.setTextFill(Color.WHITESMOKE);
    }
    
    /**
     * Colorea una celda de un calendario con el color por defecto
     * @param dateCell Celda que se va a colorear
     */
    public void dateCellcolorDefault(DateCell dateCell){
        //BackgroundFill fill = new BackgroundFill(color, null, null);
        dateCell.setBackground(null);
        dateCell.setTextFill(Color.BLACK);
    }

    /**
     * Colorea una celda de un calendario
     * @param color color con el que se coloreará
     * @param dateCell Celda que se va a colorear
     */
    public void cellcolor(LocalDate day){
        BackgroundFill fill = new BackgroundFill(Color.YELLOW, null, null);
        getDateCells().forEach(
            cell->{
                if(cell.getItem().equals(day)){
                    cell.setBackground(new Background(fill));
                    cell.setTextFill(Color.WHITESMOKE);
                }
            }
        );
    }

    private void init(Pane container){
        this.container = container;
        this.daysOfWeekEnabled = FXCollections.observableArrayList();
        this.markedDays = FXCollections.observableHashMap();
        this.daysDisabled = FXCollections.observableArrayList();
        this.daysOfWeekEnabled.add(9);
        calendarDatePicker = new DatePicker(LocalDate.now());
        this.dateCells = new ArrayList<>();
    }
}
