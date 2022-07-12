package Controllers.Schedule;

import Helpers.Calendar.DateCalendar;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.Pane;

public class IntervalComponent {

    private Pane gpDays;
    private DateCalendar dateCalendar;

    public IntervalComponent (DateCalendar dateCalendar,Pane gpDays){
        this.dateCalendar = dateCalendar;
        this.gpDays = gpDays;
    }

    public IntervalComponent (){}

    public Pane getGpDays() {
        return gpDays;
    }

    public void setGpDays(Pane gpDays) {
        this.gpDays = gpDays;
    }

    public DateCalendar getDateCalendar() {
        return dateCalendar;
    }



    public void setDateCalendar(DateCalendar dateCalendar) {
        this.dateCalendar = dateCalendar;
    }



    public void valuesDefault(){
        for (Node node: getGpDays().getChildren()) {
            ((CheckBox)node).setSelected(false);
        }
        getDateCalendar().getDaysOfWeekEnabled().removeAll(getDateCalendar().getDaysOfWeekEnabled());
        getDateCalendar().getDaysOfWeekEnabled().add(Integer.valueOf(9));
    }

    public void days(){
        for (int i = 0; i < getGpDays().getChildren().size(); i++) {
            CheckBox checkBox = ((CheckBox) getGpDays().getChildren().get(i));
            checkBox.setId(String.valueOf(Helpers.Time.dayNumberOfWeek(Helpers.Time.SEMANA[i])));
            checkBox.setOnAction(
                event-> checkBoxSelected(checkBox)
            );
        }
    }

    private void checkBoxSelected(CheckBox checkBox){
        if (getDateCalendar().getDaysOfWeekEnabled().contains(Integer.valueOf(9))) {
            getDateCalendar().getDaysOfWeekEnabled().remove(Integer.valueOf(9));
        }
        Integer day = Helpers.Time.dayNumberOfWeek(checkBox.getText());
        if(day.equals(Integer.valueOf(8))){
            if(checkBox.isSelected()){
                getGpDays().getChildren().forEach(
                    element -> {
                        CheckBox cBox = (CheckBox) element;
                        Integer d = Helpers.Time.dayNumberOfWeek(cBox.getText());
                        if (d.equals(Integer.valueOf(8))) {
                            getDateCalendar().getDaysOfWeekEnabled().removeAll(getDateCalendar().getDaysOfWeekEnabled());
                            getDateCalendar().getDaysOfWeekEnabled().add(d);
                        }else{
                            cBox.setSelected(false);
                            cBox.setDisable(true);
                            if (getDateCalendar().getDaysOfWeekEnabled().contains(d)) {
                                getDateCalendar().getDaysOfWeekEnabled().remove(d);
                            }
                        }
                    });
            }else{
                getGpDays().getChildren().forEach(
                    element -> {
                        CheckBox cBox = (CheckBox) element;
                        Integer d = Helpers.Time.dayNumberOfWeek(cBox.getText());
                        if (!d.equals(Integer.valueOf(8))) {
                            cBox.setDisable(false);
                        }else{
                            getDateCalendar().getDaysOfWeekEnabled().removeAll(getDateCalendar().getDaysOfWeekEnabled());
                        }
                    });
            }
        }else{
            if (checkBox.isSelected()) {
                getDateCalendar().getDaysOfWeekEnabled().add(day);
            } else {
                if (getDateCalendar().getDaysOfWeekEnabled().contains(day)) {
                    getDateCalendar().getDaysOfWeekEnabled().remove(day);
                }
            }
        }

        if(getDateCalendar().getDaysOfWeekEnabled().isEmpty()){
            getDateCalendar().getDaysOfWeekEnabled().add(Integer.valueOf(9));
        }

        getDateCalendar().daysSelected();
    }

    protected void daysSelected(){
        for (Node node: getGpDays().getChildren()) {
            CheckBox  checkbox = ((CheckBox)node);
            if(checkbox.isSelected()){
                getDateCalendar().getDaysOfWeekEnabled().add(Helpers.Time
                .dayNumberOfWeek(checkbox.getText()));
            }else{
                getDateCalendar().getDaysOfWeekEnabled().remove(Helpers.Time
                .dayNumberOfWeek(checkbox.getText()));
            }
        }
    }
    
}
