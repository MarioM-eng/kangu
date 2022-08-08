package Controllers.Schedules;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.skin.DatePickerSkin;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class CalendarComponent {

    private DatePickerSkin datePickerSkin;
    private AnchorPane container;
    private ObservableList<LocalDate> markedDays;
    private DatePicker calendarDatePicker;
    private List<DateCell> dateCells;
    private LocalDate daySelected;
    private AppointmentsController componentParent;

    public CalendarComponent(AnchorPane container) {
        init(container);
    }

    public CalendarComponent(AnchorPane container, AppointmentsController componentParent) {
        this.componentParent = componentParent;
        this.daySelected = LocalDate.now();
        init(container);
    }

    private void dateCellevent(LocalDate item,DateCell dateCell){
        dateCell.setOnMouseClicked(
                action -> {
                    if(action.getButton().equals(MouseButton.PRIMARY)){
                        selectDay(item);
                        if(componentParent.getAppointment() != null){
                            componentParent.fillWeeks(
                                firstDayOfWeek(item), 
                                lastDayOfWeek(item), 
                                componentParent.getAppointment());
                        }
                        
                        //System.out.println(firstDayOfWeek(item));
                        if(action.getClickCount() >= 2){
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
     * Marca el día seleccionado
     * @param item Día seleccionado
     */
    private void selectDay(LocalDate item){
        DateCell dateCell = existCell(item);
        if(dateCell != null){
            if(daySelected == null){
                daySelected = item;
                dateCellcolor(Color.YELLOW, dateCell);
            }else{
                DateCell dateCellDaySelected = existCell(daySelected);
                if(dateCellDaySelected != null){
                    markCell(dateCellDaySelected);
                }
                daySelected = item;
                dateCellcolor(Color.YELLOW, dateCell);
            }
        }
    }

    /**
     * Marca o desmarca la celda que se pase dependiendo si está
     * en la lista de días marcados
     * @param cell
     */
    private void markCell(DateCell cell) {
        if(markedDays.contains(cell.getItem())){
            dateCellcolor(Color.BLUE, cell);
        }else{
            dateCellcolorDefault(cell);
        }
        
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
     * Verifica si con la fecha pasada porparametro, hay una celda existente
     * @param day fecha que se quiere verificar
     * @return La celda.Si no existe, retorna null
     */
    public DateCell existCell(LocalDate day){
        for (DateCell dateCell : dateCells) {
            if(dateCell.getItem().equals(day)){
                return dateCell;
            }
        }
        return null;
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
                    if(!dateCells.contains(this)){
                        dateCells.add(this);
                    }
                    markCell(this);
                    if(daySelected != null){
                        if(this.getItem().equals(daySelected)){
                            dateCellcolor(Color.YELLOW, this);
                        }
                    }
                    dateCellevent(calendarDatePicker.getValue(),this);
                }
            }
        });
        return calendarDatePicker;
    }

    /**
     * Carga el calendario
     */
    public void calendar() {
        // Se obtiene el Layout que contendrá el calendario
        if (this.datePickerSkin != null) {
            container.getChildren().remove(datePickerSkin.getPopupContent());
        }
        DatePicker d = calendarActual();

        this.datePickerSkin = new DatePickerSkin(d);

        // Este método debe devolver el Nodo que se mostrará cuando el usuario haga clic
        // en el área de 'botón' de ComboBox.
        Node popupContent = this.datePickerSkin.getPopupContent();
        // Se le agrega un id al calendario
        popupContent.setId("calendar");
        
        // Se asegura que al calendadio sea el último nodo agregado
        AnchorPane.setLeftAnchor(popupContent, 14.0);
        AnchorPane.setRightAnchor(popupContent, 14.0);
        this.container.getChildren().add(popupContent);
    }

    

    public LocalDate getDaySelected() {
        return daySelected;
    }

    public void setDaySelected(LocalDate daySelected) {
        this.daySelected = daySelected;
    }

    private void init(AnchorPane container){
        this.container = container;
        this.markedDays = FXCollections.observableArrayList();
        calendarDatePicker = new DatePicker(LocalDate.now());
        this.dateCells = new ArrayList<>();
    }


    
}
