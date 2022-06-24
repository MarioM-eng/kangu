package Models;

import java.sql.Date;
import java.sql.Time;

public class ScheduleVo extends Model {

    private Date from;
    private Date to;
    private Time hourFrom;
    private Time hourTo;
    private PatientVo patient;
    private UserVo user;

    public ScheduleVo(int id, Date from, Date to, Time hourFrom, Time hourTo, PatientVo patient, UserVo user) {
        super(id);
        this.from = from;
        this.to = to;
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.patient = patient;
        this.user = user;
    }
    
    public ScheduleVo() {
        this.hourFrom = Time.valueOf("0:00:00");
        this.hourTo = Time.valueOf("0:00:00");
    }

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }

    public Time getHourFrom() {
        return hourFrom;
    }

    public void setHourFrom(Time hourFrom) {
        this.hourFrom = hourFrom;
    }

    public Time getHourTo() {
        return hourTo;
    }

    public void setHourTo(Time hourTo) {
        this.hourTo = hourTo;
    }

    public PatientVo getPatient() {
        return patient;
    }

    public void setPatient(PatientVo patient) {
        this.patient = patient;
    }

    public UserVo getUser() {
        return user;
    }

    public void setUser(UserVo user) {
        this.user = user;
    }

    public void setSchedule(ScheduleVo scheduleVo) {
        super.setId(scheduleVo.getId());
        this.from = scheduleVo.getFrom();
        this.to = scheduleVo.getTo();
        this.hourFrom = scheduleVo.getHourFrom();
        this.hourTo = scheduleVo.getHourTo();
        this.patient = scheduleVo.getPatient();
        this.user = scheduleVo.getUser();
    }
    
}
