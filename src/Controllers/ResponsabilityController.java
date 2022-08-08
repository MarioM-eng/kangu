package Controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import Helpers.Alert.AlertImplement;
import Helpers.Facades.IAlert;
import Helpers.Validate.Charact;
import Helpers.Validate.FieldValidation;
import Helpers.Validate.Range;
import Models.PatientBo;
import Models.PatientVo;
import Models.ResponsibleBo;
import Models.ResponsibleVo;
import Models.Relationships.ManyToMany;
import Models.Relationships.ManyToManyBo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ResponsabilityController implements Initializable{

    private Object param;
    ManyToManyBo<PatientVo,ResponsibleVo,PatientBo,ResponsibleBo> manyToManyBo;
    
    @FXML private TextField tfDni;
    @FXML private Label lbNoticeDni;
    @FXML private TextField tfName;
    @FXML private Label lbNoticeName;
    @FXML private TextField tfCel;
    @FXML private Label lbNoticeCel;
    @FXML private TextField tfAddress;
    @FXML private Label lbNoticeAddress;

    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnDelete;
    @FXML private Button btnCleanUp;

    @FXML private TableView<ManyToMany<PatientVo,ResponsibleVo>> tblElements;

    @FXML private AnchorPane anchor;

    AlertImplement alert;

    public ResponsabilityController(){
        manyToManyBo = new ManyToManyBo<>(new PatientVo(), new ResponsibleVo(), PatientBo.getInstance(),ResponsibleBo.getInstance());
    }

    public ResponsabilityController(Object param){
        this.param = param;
        manyToManyBo = new ManyToManyBo<>(new PatientVo(), new ResponsibleVo(), PatientBo.getInstance(),ResponsibleBo.getInstance());
    }

    private void cleanUpField(){
        tfDni.setText("");
        tfName.setText("");
        tfCel.setText("");
        tfAddress.setText("");
        tblElements.getSelectionModel().clearSelection();
    }

    private ResponsibleVo createResponsible(){
        ResponsibleVo responsibleVo = new ResponsibleVo();
        responsibleVo.setDni(tfDni.getText());
        responsibleVo.setName(tfName.getText());
        responsibleVo.setCel(tfCel.getText());
        responsibleVo.setAddress(tfAddress.getText());
        responsibleVo = ResponsibleBo.getInstance().create(responsibleVo);
        if (responsibleVo == null) {
            responsibleVo = ResponsibleBo.getInstance().getElements().filtered(
                el->el.getDni().equals(tfDni.getText())
            ).get(0);
        }
        return responsibleVo;
    }

    private boolean updateResponsible(){
        if(!validateField()){return false;}
        ResponsibleVo responsibleVo = null;
        try {
            responsibleVo = (ResponsibleVo)tblElements.getSelectionModel().getSelectedItem().getElement_2().clone();
            if(responsibleVo.getDni().equals(tfDni.getText())
                && responsibleVo.getName().equals(tfName.getText()) 
                && responsibleVo.getCel().equals(tfCel.getText())
                && responsibleVo.getAddress().equals(tfAddress.getText())){
                    alert.alert("Para actualizar debe cambiar al menos un campo", IAlert.SIMPLE);
            }else{
                responsibleVo.setDni(tfDni.getText());
                responsibleVo.setName(tfName.getText());
                responsibleVo.setCel(tfCel.getText());
                responsibleVo.setAddress(tfAddress.getText());
                if(ResponsibleBo.getInstance().update(responsibleVo)){
                    tblElements.refresh();
                    alert.alert("Acudiente actualizado con éxito", IAlert.SIMPLE);
                }else{
                    alert.alert("No se pudo actualizar Acudiente", IAlert.ERROR);
                }
            }
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    private void add(ActionEvent actionEvent){

        if(validateField()){
            PatientVo patientVo = (PatientVo) param;
            ResponsibleVo responsibleVo = createResponsible();
            ManyToMany<PatientVo,ResponsibleVo> responsability = new ManyToMany<>(patientVo,responsibleVo);
            if (responsibleVo != null) {
                if (!manyToManyBo.getElements().contains(responsability)) {
                    if(manyToManyBo.create(responsability)){
                        alert.alert(responsability.getElement_2() +" fue relacionado con "+ responsability.getElement_1(), IAlert.SIMPLE);
                    }
                } else {
                    alert.alert("El acudiente " + responsibleVo + " ya está asignado a el paciente " + patientVo, IAlert.SIMPLE);
                }
            } else {
                alert.alert("No se pudo obtener el responsable", IAlert.ERROR);
            }
        } else{
            alert.alert("Verifique los campos", IAlert.ERROR);
        }
    }

    private void remove(ActionEvent actionEvent){
        if(manyToManyBo.delete(tblElements.getSelectionModel().getSelectedItem())){
            alert.alert("Elemento eliminado", IAlert.SIMPLE);
        }else{
            alert.alert("No fue posible eliminar elemento de la base de datos", IAlert.ERROR);
        }
    }

    private void tableColumn(){
        TableColumn<ManyToMany<PatientVo,ResponsibleVo>,String> tColumnDni = new TableColumn<>("DNI");
        tColumnDni.setMinWidth(10);
        tColumnDni.setPrefWidth(80);
        tColumnDni.setMaxWidth(5000);
        tColumnDni.setCellValueFactory(data -> data.getValue().getElement_2().getDniProperty());

        TableColumn<ManyToMany<PatientVo,ResponsibleVo>,String> tColumnName = new TableColumn<>("Nombre");
        tColumnName.setMinWidth(10);
        tColumnName.setPrefWidth(179);
        tColumnName.setMaxWidth(5000);
        tColumnName.setCellValueFactory(data -> data.getValue().getElement_2().getNameProperty());

        TableColumn<ManyToMany<PatientVo,ResponsibleVo>,String> tColumnCel = new TableColumn<>("Celular");
        tColumnCel.setMinWidth(10);
        tColumnCel.setPrefWidth(113);
        tColumnCel.setMaxWidth(5000);
        tColumnCel.setCellValueFactory(data -> data.getValue().getElement_2().getCelProperty());

        TableColumn<ManyToMany<PatientVo,ResponsibleVo>,String> tColumnAddress = new TableColumn<>("Dirección");
        tColumnAddress.setMinWidth(10);
        tColumnAddress.setPrefWidth(113);
        tColumnAddress.setMaxWidth(5000);
        tColumnAddress.setCellValueFactory(data -> data.getValue().getElement_2().getAddressProperty());

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni,tColumnName,tColumnCel,tColumnAddress));
    }

    private void fillTable(){
        tblElements.setItems(manyToManyBo.getElements().filtered(
                    element->element.getElement_1().equals(param)
                ));
    }

    private void loadElementInForm(){
        tblElements.getSelectionModel().selectedItemProperty().addListener(e->{
            if(tblElements.getSelectionModel().getSelectedItem() != null){
                btnUpdate.setDisable(false);
                btnCleanUp.setDisable(false);
                ResponsibleVo responsibleVo = tblElements.getSelectionModel()
                                                    .getSelectedItem().getElement_2();
                if(responsibleVo != null){
                    tfDni.setText(responsibleVo.getDni());
                    tfName.setText(responsibleVo.getName());
                    tfCel.setText(responsibleVo.getCel());
                    tfAddress.setText(responsibleVo.getAddress());
                }
            }else{
                btnUpdate.setDisable(true);
                btnCleanUp.setDisable(true);
            }
        });
    }

    private boolean validateField(){
        boolean result = true;
        if(Charact.isEmpty(new FieldValidation(tfDni,lbNoticeDni))){result = false;}
        if(!Range.min(new FieldValidation(tfDni,lbNoticeDni),10)){result = false;}
        if(Charact.isEmpty(new FieldValidation(tfName,lbNoticeName))){result = false;}
        if(!Range.min(new FieldValidation(tfName,lbNoticeName),2)){result = false;}
        if(Charact.isEmpty(new FieldValidation(tfCel,lbNoticeCel))){result = false;}
        if(!Range.min(new FieldValidation(tfCel,lbNoticeCel),10)){result = false;}
        if(Charact.isEmpty(new FieldValidation(tfAddress,lbNoticeAddress))){result = false;}
        if(!Range.min(new FieldValidation(tfAddress,lbNoticeAddress),12)){result = false;}
        
        return result;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alert = new AlertImplement();
        tableColumn();
        fillTable();

        btnAdd.setOnAction((actionEvent)->add(actionEvent));
        btnCleanUp.setOnAction(actionEvent->cleanUpField());
        btnDelete.setOnAction(actionEvent->remove(actionEvent));
        btnUpdate.setOnAction(actionEvent->updateResponsible());

        loadElementInForm();

        Charact.numeros(new FieldValidation().withTf(tfDni));
        Range.max(tfDni, 10);
        
        Charact.letterWithSpace(new FieldValidation().withTf(tfName));
        Range.max(tfName, 50);

        Charact.numeros(new FieldValidation().withTf(tfCel));
        Range.max(tfCel, 10);

        Range.max(tfAddress, 50);
    }
    
}
