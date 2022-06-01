package Models;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ResponsibleVo extends Model {

    private SimpleObjectProperty<PersonVo> person;
    private SimpleStringProperty cel;
    private SimpleStringProperty address;

    public ResponsibleVo(int id, PersonVo person, String cel, String address) {
        super(id);
        this.person = new SimpleObjectProperty<>(person);
        this.cel = new SimpleStringProperty(cel);
        this.address = new SimpleStringProperty(address);
    }

    public ResponsibleVo() {
        super();
        this.person = new SimpleObjectProperty<>();
        this.cel = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
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

    public String getCel() {
        return cel.get();
    }

    public SimpleStringProperty getCelProperty() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel.set(cel);
    }

    public String getAddress() {
        return address.get();
    }

    public SimpleStringProperty getAddressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    
    
}
