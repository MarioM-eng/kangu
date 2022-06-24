package Controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class ResponsabilityController implements Initializable{

    private Object param;
    ManyToManyBo<PatientVo,ResponsibleVo,PatientBo,ResponsibleBo> manyToManyBo;
    
    @FXML
    private TextField tfDni;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfCel;
    @FXML
    private TextField tfAddress;
    @FXML
    private TextField tfSearch;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAll;
    @FXML
    private Button btnCleanUp;

    @FXML
    private TableView<ManyToMany<PatientVo,ResponsibleVo>> tblElements;

    @FXML
    private AnchorPane anchor;

    public ResponsabilityController(){
        manyToManyBo = new ManyToManyBo<>(new PatientVo(), new ResponsibleVo(), PatientBo.getInstance(),ResponsibleBo.getInstance());
    }

    public ResponsabilityController(Object param){
        this.param = param;
        manyToManyBo = new ManyToManyBo<>(new PatientVo(), new ResponsibleVo(), PatientBo.getInstance(),ResponsibleBo.getInstance());
    }

    private void cleanUpField(ActionEvent actionEvent){

        tfDni.setText("");
        tfName.setText("");
        tfCel.setText("");
        tfAddress.setText("");
        tblElements.getSelectionModel().clearSelection();

    }

    private void all(ActionEvent actionEvent){
        tblElements.setItems(manyToManyBo.getElements());
    }

    private void add(ActionEvent actionEvent){

        ResponsibleVo responsibleVo = new ResponsibleVo();
        responsibleVo.setDni(tfDni.getText());
        responsibleVo.setName(tfName.getText());
        responsibleVo.setCel(tfCel.getText());
        responsibleVo.setAddress(tfAddress.getText());

        responsibleVo = ResponsibleBo.getInstance().create(responsibleVo);
        PatientVo patientVo = (PatientVo) param;
        ManyToMany<PatientVo,ResponsibleVo> responsability = new ManyToMany<>(patientVo,responsibleVo);
        
        if(responsibleVo == null){
            responsibleVo = manyToManyBo.getElements().filtered(
                element-> element.getElement_2().getDni().equals(tfDni.getText())
            ).get(0).getElement_2();

            responsability.setElement_2(responsibleVo);

            if(!manyToManyBo.getElements().contains(responsability)){
                manyToManyBo.create(responsability);
            }else{
                System.out.println("El acudiente "+ responsibleVo + " ya está asignado a el paciente "+ patientVo);
            }
        }else{
            
            manyToManyBo.create(responsability);
        } 

    }

    private void remove(ActionEvent actionEvent){

        manyToManyBo.delete(tblElements.getSelectionModel().getSelectedItem());
        tblElements.refresh();
    }

    private void fillTable(TableView<ManyToMany<PatientVo,ResponsibleVo>> tblElements){

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

        tblElements.setItems(manyToManyBo.getElements().filtered(
                    element->element.getElement_1().equals(param)
                ));

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni,tColumnName,tColumnCel,tColumnAddress));
    }

    private void loadElementInForm(){
        tblElements.getSelectionModel().selectedItemProperty().addListener(e->{
            ResponsibleVo responsibleVo = tblElements.getSelectionModel()
                                                    .getSelectedItem().getElement_2();
            if(responsibleVo != null){
                tfDni.setText(responsibleVo.getDni());;
                tfName.setText(responsibleVo.getName());
                tfCel.setText(responsibleVo.getCel());
                tfAddress.setText(responsibleVo.getAddress());
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillTable(tblElements);

        btnAdd.setOnAction((actionEvent)->{
            add(actionEvent);
        });

        btnCleanUp.setOnAction(actionEvent->{
            cleanUpField(actionEvent);
        });

        btnRemove.setOnAction(actionEvent->{
            remove(actionEvent);
        });
        btnAll.setOnAction(actionEvent->{
            all(actionEvent);
        });

        loadElementInForm();
    }
    
}
