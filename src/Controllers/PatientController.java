package Controllers;

import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.ResourceBundle;

import Helpers.ViewsPath;
import Helpers.ViewCreator.WindowBuild;
import Models.PatientBo;
import Models.PatientVo;
import Models.ResponsibleVo;
import Models.Relationships.PatientResponsibleBo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class PatientController implements Initializable {

    @FXML
    private TextField tfDni;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField searchField;

    @FXML
    private DatePicker dpDate;

    @FXML
    private TextArea tfDiag;

    @FXML
    private RadioButton rbMan;
    @FXML
    private RadioButton rbWoman;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnAdd2;
    @FXML
    private Button btnSearch;
    @FXML
    private Button btnAll;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnRecovery;
    @FXML
    private Button btnPaperBin;

    @FXML
    private TableView<PatientVo> tblElements;
    @FXML
    private TableView<ResponsibleVo> tblElements2;

    WindowBuild windowBuild;

    private void addResponsible(ActionEvent actionEvent){
        String title = "Acudiente";
        String logo = "Images/logo.jpeg";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        if(windowBuild == null){
            windowBuild = WindowBuild.getNewInstance();
            windowBuild.withStage(new Stage()).withTitle(title).withUrl(ruta).withLogo(logo).build();
        }
        windowBuild.show();
    }

    private void fillTablePatients(TableView<PatientVo> tblElements){

        TableColumn<PatientVo,String> tColumnDni = new TableColumn<>("DNI");
        tColumnDni.setMinWidth(10);
        tColumnDni.setPrefWidth(80);
        tColumnDni.setMaxWidth(5000);
        tColumnDni.setCellValueFactory(data -> data.getValue().getPersonProperty().get().getDniProperty());

        TableColumn<PatientVo,String> tColumnName = new TableColumn<>("Nombre");
        tColumnName.setMinWidth(10);
        tColumnName.setPrefWidth(179);
        tColumnName.setMaxWidth(5000);
        tColumnName.setCellValueFactory(data -> data.getValue().getPerson().getNameProperty());

        TableColumn<PatientVo,String> tColumnAge = new TableColumn<>("Edad");
        tColumnAge.setMinWidth(10);
        tColumnAge.setPrefWidth(90);
        tColumnAge.setMaxWidth(5000);
        tColumnAge.setCellValueFactory(new PropertyValueFactory<PatientVo, String>("age"));

        TableColumn<PatientVo,Date> tColumnDateB = new TableColumn<>("Fecha de nacimiento");
        tColumnDateB.setMinWidth(10);
        tColumnDateB.setPrefWidth(113);
        tColumnDateB.setMaxWidth(5000);
        tColumnDateB.setCellValueFactory(data -> data.getValue().getDateBirthProperty());

        TableColumn<PatientVo,String> tColumnSex = new TableColumn<>("Sexo");
        tColumnSex.setMinWidth(10);
        tColumnSex.setPrefWidth(90);
        tColumnSex.setMaxWidth(5000);
        tColumnSex.setCellValueFactory(new PropertyValueFactory<PatientVo, String>("sex"));

        TableColumn<PatientVo,String> tColumnDiag = new TableColumn<>("Diagnostico");
        tColumnDiag.setMinWidth(10);
        tColumnDiag.setPrefWidth(220);
        tColumnDiag.setMaxWidth(5000);
        tColumnDiag.setCellValueFactory(new PropertyValueFactory<PatientVo, String>("diagnosis"));

        tblElements.setItems(PatientBo.getInstance().getElements());

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni,tColumnName,tColumnAge,tColumnDateB,tColumnSex,tColumnDiag));
    }

    private void fillTableResponsibles(TableView<ResponsibleVo> tblElements, PatientVo patientVo){

        TableColumn<ResponsibleVo,String> tColumnDni = new TableColumn<>("DNI");
        tColumnDni.setMinWidth(10);
        tColumnDni.setPrefWidth(80);
        tColumnDni.setMaxWidth(5000);
        tColumnDni.setCellValueFactory(data -> data.getValue().getPersonProperty().get().getDniProperty());

        TableColumn<ResponsibleVo,String> tColumnName = new TableColumn<>("Nombre");
        tColumnName.setMinWidth(10);
        tColumnName.setPrefWidth(179);
        tColumnName.setMaxWidth(5000);
        tColumnName.setCellValueFactory(data -> data.getValue().getPerson().getNameProperty());

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

        tblElements.setItems(PatientResponsibleBo.getInstance().responsiblesOfPatient(patientVo));

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni,tColumnName,tColumnCel,tColumnAddress));
    }

    private void loadElementInForm(){
        tblElements.getSelectionModel().selectedItemProperty().addListener(e->{
            PatientVo patientVo = tblElements.getSelectionModel().getSelectedItem();
            
            tfDni.setText(patientVo.getPerson().getDni());
            tfName.setText(patientVo.getPerson().getName());
            tfAge.setText(patientVo.getAge());
            if(patientVo.getSex().equals("M")){
                rbMan.setSelected(true);
            }else{
                rbWoman.setSelected(true);
            }
            dpDate.setValue(patientVo.getDateBirth().toLocalDate());
            tfDiag.setText(patientVo.getDiagnosis());
            fillTableResponsibles(tblElements2,patientVo);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dpDate.setEditable(false);
        ToggleGroup group = new ToggleGroup();
        rbMan.setToggleGroup(group);
        rbWoman.setToggleGroup(group);
        fillTablePatients(tblElements);
        loadElementInForm();

        btnAdd2.setOnAction((actionEvent)->{
            addResponsible(actionEvent);
        });
        
    }


    
}
