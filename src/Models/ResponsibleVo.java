package Models;

import javafx.beans.property.SimpleStringProperty;

public class ResponsibleVo extends Person {

    private SimpleStringProperty cel;
    private SimpleStringProperty address;

    public ResponsibleVo(int id, String name, String dni, String cel, String address) {
        super(id,name,dni);
        this.cel = new SimpleStringProperty(cel);
        this.address = new SimpleStringProperty(address);
    }

    public ResponsibleVo() {
        super();
        this.cel = new SimpleStringProperty();
        this.address = new SimpleStringProperty();
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
