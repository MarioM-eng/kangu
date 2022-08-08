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

    /**
     * Reemplaza los datos de un objeto por otro
     * @param responsibleVo
     */
    public void replace(ResponsibleVo responsibleVo){
        this.setDni(responsibleVo.getDni());
        this.setName(responsibleVo.getName());
        this.setCel(responsibleVo.getCel());
        this.setAddress(responsibleVo.getAddress());
        this.setCreated_at(responsibleVo.getCreated_at());
        this.setUpdated_at(responsibleVo.getUpdated_at());
        this.setDeleted_at(responsibleVo.getDeleted_at());
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return new ResponsibleVo(getId(), getName(), getDni(), getCel(), getAddress());
    }
    
}
