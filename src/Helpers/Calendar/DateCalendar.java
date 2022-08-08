package Helpers.Calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.TemporalField;
import java.time.temporal.TemporalUnit;
import java.time.temporal.ValueRange;
import java.util.ArrayList;
import java.util.List;

import Controllers.ScheduleController;
import Controllers.Schedules.AppointmentComponent;
import Helpers.Facades.View;
import Models.ScheduleVo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    private ObservableList<LocalDate> markedDays;
    private ObservableList<ScheduleVo> daysDisabled;
    private DatePicker calendarDatePicker;
    private List<DateCell> dateCells;
    private AppointmentComponent AppointmentController;

    public DateCalendar(Pane container) {
        init(container);
    }

    

    
    public AppointmentComponent getAppointmentController() {
        return AppointmentController;
    }
    public void setAppointmentController(AppointmentComponent appointmentController) {
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

    public ObservableList<LocalDate> getMarkedDays() {
        return markedDays;
    }

    public void setMarkedDays(ObservableList<LocalDate> markedDays) {
        this.markedDays = markedDays;
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

    private void dateCellevent(LocalDate item,DateCell dateCell){
        dateCell.setOnMouseClicked(
                action -> {
                    if(action.getButton().equals(MouseButton.PRIMARY)){
                        System.out.println(firstDayOfWeek(item));
                        if(action.getClickCount() >= 2){
                            daySelected(item);
                            ScheduleController controller = new ScheduleController();
                            /* controller.getParams().put("appointment", getAppointmentController().getAppointmentVo());
                            controller.getParams().put("day", item); */
                            View.getInstance().createModalWithWait(controller, "Horario");
                            /* if(scheduleVo.getFrom() == null){
                                dayUnSelected(item);
                            } */
                        }
                    }
                }
        );
    }

    public LocalDate firstDayOfWeek(LocalDate item){
        //TemporalAdjusters.previous(DayOfWeek.MONDAY);
        item = item.with(TemporalAdjusters.previous(DayOfWeek.MONDAY));
        return item;
    }

    public LocalDate lastDayOfWeek(LocalDate item){
        //TemporalAdjusters.previous(DayOfWeek.MONDAY);
        item = item.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        return item;
    }

    /**
     * Actualiza las celdas con la lista de días marcados(markedDays) dada a 
     * partir de una lista de schedules
     * @param markedDays lista con los días marcados
     */
    public void daysSelected(ObservableList<ScheduleVo> markedDays){
        getMarkedDays().removeAll(getMarkedDays());
        markedDays.forEach(
            element->getMarkedDays().add(element.getDay().toLocalDate())
        );
        markDyas();
    }

    /**
     * Agrega a la lista de días marcados(markedDays) un día y marca la celda
     */
    private void daySelected(LocalDate day){
        if (!getMarkedDays().contains(day)) {
            getMarkedDays().add(day);
            markCell(findDateCell(day));
        }
    }
    /**
     * Agrega a la lista de días marcados(markedDays) un día y marca la celda
     */
    private void dayUnSelected(LocalDate day){
        if (getMarkedDays().contains(day)) {
            getMarkedDays().remove(day);
            unmarkCell(findDateCell(day));
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
                            (day) -> {
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
    public DateCell findDateCell(LocalDate day){
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
                        markDyas();
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
                    
                    if(getMarkedDays().contains(this.getItem())){
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
        this.markedDays = FXCollections.observableArrayList();
        this.daysDisabled = FXCollections.observableArrayList();
        this.daysOfWeekEnabled.add(9);
        calendarDatePicker = new DatePicker(LocalDate.now());
        this.dateCells = new ArrayList<>();
    }
}
