package Models;

import java.sql.Date;
import java.sql.Time;

public class ScheduleVo extends Model {

    private Time from;
    private Time to;
    private Date day;
    private AppointmentVo appointment;
    
    public ScheduleVo(int id, Time from, Time to, Date day, AppointmentVo appointment) {
        super(id);
        this.from = from;
        this.to = to;
        this.day = day;
        this.appointment = appointment;
    }
    public ScheduleVo() {}
    
    public Time getFrom() {
        return from;
    }
    public void setFrom(Time from) {
        this.from = from;
    }
    public Time getTo() {
        return to;
    }
    public void setTo(Time to) {
        this.to = to;
    }
    public Date getDay() {
        return day;
    }
    public void setDay(Date day) {
        this.day = day;
    }
    public AppointmentVo getAppointment() {
        return appointment;
    }
    public void setAppointment(AppointmentVo appointment) {
        this.appointment = appointment;
    }
    
}
