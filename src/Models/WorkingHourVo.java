package Models;

import java.sql.Date;
import java.sql.Time;
/* import java.time.LocalDate;
import java.time.LocalTime; */

public class WorkingHourVo extends Model{

    private Time hourFrom;
    private Time hourTo;
    private Date day;
    private ScheduleVo schedule;

    public WorkingHourVo(int id, Time hourFrom, Time hourTo, Date day, ScheduleVo schedule) {
        super(id);
        this.hourFrom = hourFrom;
        this.hourTo = hourTo;
        this.day = day;
        this.schedule = schedule;
    }
    /* public WorkingHourVo() {
        this.hourFrom = Time.valueOf(LocalTime.now());
        this.hourTo = Time.valueOf(LocalTime.now());;
        this.day = Date.valueOf(LocalDate.now());
        this.schedule = new ScheduleVo();
    } */

    public WorkingHourVo() {}

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

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

    public ScheduleVo getSchedule() {
        return schedule;
    }

    public void setSchedule(ScheduleVo schedule) {
        this.schedule = schedule;
    }

    public void setWorkingHourVo(WorkingHourVo workingHourVo) {
        super.setId(workingHourVo.getId());
        this.hourFrom = workingHourVo.getHourFrom();
        this.hourTo = workingHourVo.getHourTo();
        this.day = workingHourVo.getDay();
        this.schedule = workingHourVo.getSchedule();
    }
    
}
