package Controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import Models.ResponsibleBo;
import Models.ResponsibleVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class ResponsibleController implements Initializable{

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
    private Button btnSearch;
    @FXML
    private Button btnAll;
    @FXML
    private Button btnCleanUp;

    @FXML
    private TableView<ResponsibleVo> tblElements;

    @FXML
    private AnchorPane anchor;

    public ResponsibleController(){
        
    }

    private void cleanUpField(ActionEvent actionEvent){

        tfDni.setText("");
        tfName.setText("");
        tfCel.setText("");
        tfAddress.setText("");
        tblElements.getSelectionModel().clearSelection();

    }

    private void create(ActionEvent actionEvent){

        ResponsibleVo responsibleVo = new ResponsibleVo();
        responsibleVo.setDni(tfDni.getText());
        responsibleVo.setName(tfName.getText());
        responsibleVo.setCel(tfCel.getText());
        responsibleVo.setAddress(tfAddress.getText());

        responsibleVo = ResponsibleBo.getInstance().create(responsibleVo);
        /* if(responsibleVo == null){
            responsibleVo = tblElements.getSelectionModel().getSelectedItem();
            if((PatientResponsibleBo.getInstance().getElements().filtered(
                el->el.getPatientVo().getId() == this.patientVo.getId() && el.getResponsibleVo().getId() == responsibleVo.getId()
                )).isEmpty()){
                this.patientVo = (PatientVo) this.anchor.getScene().getWindow().getUserData();
                PatientResponsibleBo.getInstance().create(new PatientResponsibleVo(patientVo, responsibleVo));
            }else{
                System.out.println("El acudiente "+ responsibleVo + " ya está asignado a el paciente "+ this.patientVo);
            }
        } */

    }

    private void search(){
        tblElements.setItems(ResponsibleBo.getInstance().searchThroughList(tfSearch.getText()));
    }

    private void fillTableResponsibles(TableView<ResponsibleVo> tblElements){

        TableColumn<ResponsibleVo,String> tColumnDni = new TableColumn<>("DNI");
        tColumnDni.setMinWidth(10);
        tColumnDni.setPrefWidth(80);
        tColumnDni.setMaxWidth(5000);
        tColumnDni.setCellValueFactory(data -> data.getValue().getDniProperty());

        TableColumn<ResponsibleVo,String> tColumnName = new TableColumn<>("Nombre");
        tColumnName.setMinWidth(10);
        tColumnName.setPrefWidth(179);
        tColumnName.setMaxWidth(5000);
        tColumnName.setCellValueFactory(data -> data.getValue().getNameProperty());

        TableColumn<ResponsibleVo,String> tColumnCel = new TableColumn<>("Celular");
        tColumnCel.setMinWidth(10);
        tColumnCel.setPrefWidth(113);
        tColumnCel.setMaxWidth(5000);
        tColumnCel.setCellValueFactory(new PropertyValueFactory<ResponsibleVo, String>("cel"));

        TableColumn<ResponsibleVo,String> tColumnAddress = new TableColumn<>("Dirección");
        tColumnAddress.setMinWidth(10);
        tColumnAddress.setPrefWidth(113);
        tColumnAddress.setMaxWidth(5000);
        tColumnAddress.setCellValueFactory(new PropertyValueFactory<ResponsibleVo, String>("address"));

        tblElements.setItems(ResponsibleBo.getInstance().getElements());

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni,tColumnName,tColumnCel,tColumnAddress));
    }

    private void loadElementInForm(){
        tblElements.getSelectionModel().selectedItemProperty().addListener(e->{
            ResponsibleVo responsibleVo = tblElements.getSelectionModel().getSelectedItem();
            if(responsibleVo != null){
                tfDni.setText(responsibleVo.getDni());;
                tfName.setText(responsibleVo.getName());
                tfCel.setText(responsibleVo.getCel());
                tfAddress.setText(responsibleVo.getAddress());
            }
        });
    }

    private void eventSearch(){
        tfSearch.setOnKeyTyped(actionEvent->{
            search();
        });
        tfSearch.setOnMouseClicked(actionEvent->{
            search();
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fillTableResponsibles(tblElements);

        btnAdd.setOnAction((actionEvent)->{
            create(actionEvent);
        });

        btnCleanUp.setOnAction(actionEvent->{
            cleanUpField(actionEvent);
        });

        eventSearch();

        loadElementInForm();
    }
    
}
