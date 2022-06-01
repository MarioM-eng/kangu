package Models;

import java.sql.Date;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class PatientVo extends Model{
    
    private SimpleObjectProperty<PersonVo> person;
    private SimpleStringProperty age;
    private SimpleObjectProperty<Date> dateBirth;
    private SimpleStringProperty sex;
    private SimpleStringProperty diagnosis;

    public PatientVo(int id, PersonVo person, String age, Date dateBirth, String sex, String diagnosis) {
        super(id);
        this.person = new SimpleObjectProperty<>(person);
        this.age = new SimpleStringProperty(age);
        this.dateBirth = new SimpleObjectProperty<>(dateBirth);
        this.sex = new SimpleStringProperty(sex);
        this.diagnosis = new SimpleStringProperty(diagnosis);
    }

    public PatientVo() {
        super();
        this.person = new SimpleObjectProperty<>();
        this.age = new SimpleStringProperty();
        this.dateBirth = new SimpleObjectProperty<>();
        this.sex = new SimpleStringProperty();
        this.diagnosis = new SimpleStringProperty();
    }

    public PersonVo getPerson() {
        return person.get();
    }

    public SimpleObjectProperty<PersonVo> getPersonProperty() {
        return person;
    }

    public void setPerson(PersonVo person) {
        this.person.set(person);
    }

    public String getAge() {
        return age.get();
    }

    public SimpleStringProperty getAgeProperty() {
        return age;
    }

    public void setAge(String age) {
        this.age.set(age);
    }

    public Date getDateBirth() {
        return dateBirth.get();
    }

    public SimpleObjectProperty<Date> getDateBirthProperty() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth.set(dateBirth);
    }

    public String getSex() {
        return sex.get();
    }

    public SimpleStringProperty getSexProperty() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex.set(sex);
    }

    public String getDiagnosis() {
        return diagnosis.get();
    }

    public SimpleStringProperty getDiagnosisProperty() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis.set(diagnosis);
    }

    
    
}
