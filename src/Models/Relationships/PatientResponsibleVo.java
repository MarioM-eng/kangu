package Models.Relationships;

import Models.Model;
import Models.PatientVo;
import Models.ResponsibleVo;

public class PatientResponsibleVo extends Model {

    private PatientVo patientVo;
    private ResponsibleVo responsibleVo;

    public PatientResponsibleVo(int id,PatientVo patientVo, ResponsibleVo responsibleVo) {
        super(id);
        this.patientVo = patientVo;
        this.responsibleVo = responsibleVo;
    }

    public PatientResponsibleVo() {super();}

    public PatientVo getPatientVo() {
        return patientVo;
    }
    public void setPatientVo(PatientVo patientVo) {
        this.patientVo = patientVo;
    }
    public ResponsibleVo getResponsibleVo() {
        return responsibleVo;
    }
    public void setResponsibleVo(ResponsibleVo responsibleVo) {
        this.responsibleVo = responsibleVo;
    }

    
    
}
