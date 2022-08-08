package Models;

public class AppointmentVo extends Model {
    
    private PatientVo patient;
    private UserVo user;
    
    public AppointmentVo(int id, PatientVo patient, UserVo user) {
        super(id);
        this.patient = patient;
        this.user = user;
    }
    public AppointmentVo() {}
    
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

    @Override
    public boolean equals(Object obj) {
        AppointmentVo apppintment = (AppointmentVo) obj;
        if(apppintment.getId() != 0){
            if (apppintment.getId() == this.getId() && 
                apppintment.getUser().equals(this.getUser()) && 
                apppintment.getPatient().equals(this.getPatient())) {
                return true;
            }else{
                return false;
            }
        }else{
            if (apppintment.getUser().equals(this.getUser()) && 
                apppintment.getPatient().equals(this.getPatient())) {
                return true;
            }else{
                return false;
            }
        }
    }

}
