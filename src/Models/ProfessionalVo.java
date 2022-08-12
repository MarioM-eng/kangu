package Models;

import java.sql.Date;

import javafx.beans.property.SimpleObjectProperty;

public class ProfessionalVo extends UserVo {

    private SimpleObjectProperty<ProfessionVo> profession;

    public ProfessionalVo(int id, String name, String dni, String username, String password, Date created_at,
            Date updated_at, Date deleted_at, ProfessionVo profession) {
        super(id, name, dni, username, password, created_at, updated_at, deleted_at);
        this.profession.set(profession);
    }

    public ProfessionalVo(ProfessionVo profession) {
        this.profession.set(profession);
    }

    public ProfessionalVo() {}

    public ProfessionVo getProfession() {
        return profession.get();
    }
    public SimpleObjectProperty<ProfessionVo> getProfessionProperty() {
        return profession;
    }
    public void setProfession(ProfessionVo profession) {
        this.profession.set(profession);
    }

    public void replace(ProfessionalVo professionalVo){
        super.replace(professionalVo);
        this.setProfession(professionalVo.getProfession());
    }
    
}
