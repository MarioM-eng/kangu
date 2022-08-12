package Controllers;

import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.ResourceBundle;

import Controllers.Schedules.SchedulesController;
import Helpers.ViewsPath;
import Helpers.Alert.AlertImplement;
import Helpers.Facades.IAlert;
import Helpers.Facades.View;
import Helpers.Validate.Charact;
import Helpers.Validate.FieldValidation;
import Helpers.Validate.Range;
import Helpers.ViewCreator.SceneBuilder;
import Helpers.ViewCreator.WindowBuild;
import Models.PatientBo;
import Models.PatientVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientController implements Initializable {

    @FXML private TextField tfDni;
    @FXML private Label lbNoticeDni;
    @FXML private TextField tfName;
    @FXML private Label lbNoticeName;
    @FXML private TextField tfAge;
    @FXML private Label lbNoticeAge;
    @FXML private TextField tfSearch;

    @FXML private DatePicker dpDate;
    @FXML private Label lbNoticeDate;

    @FXML private TextArea tfDiag;
    @FXML private Label lbNoticeDiagnosis;

    @FXML private RadioButton rbMan;
    @FXML private RadioButton rbWoman;
    @FXML private Label lbNoticeSex;

    @FXML private Button btnAdd;
    @FXML private Button btnUpdate;
    @FXML private Button btnResponsibles;
    @FXML private Button btnSearch;
    @FXML private Button btnAll;
    @FXML private Button btnDelete;
    @FXML private Button btnRecovery;
    @FXML private Button btnPaperBin;
    @FXML private Button btnCleanUp;
    @FXML private Button btnSched;

    @FXML private Node lSubmenu;

    private ToggleGroup group;

    @FXML private TableView<PatientVo> tblElements;

    private WindowBuild windowBuild;
    private AlertImplement alert;
    private PatientVo patient;

    private void addResponsible(ActionEvent actionEvent){
        String title = "Responsabilidad";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        ResponsabilityController controller = new ResponsabilityController(getPatient());
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).withController(controller).build();
        Scene scene = sb.getScene();
        if(windowBuild == null){
            windowBuild = WindowBuild.getNewInstance();
            String logo = "Images/logo.jpeg";
            windowBuild.withLogo(logo).modal();
        }
        windowBuild.withTitle(title).withScene(scene).build();
        windowBuild.show();
    }

    private void schedule(ActionEvent actionEvent){
        SchedulesController schedules = new SchedulesController();
        schedules.getParams().put("patient", getPatient());
        View.getInstance().createModal(schedules, "Horarios");
    }

    private void cleanUpField(){

        tfDni.setText("");
        tfName.setText("");
        tfAge.setText("");
        tfSearch.setText("");
        dpDate.setValue(null);
        tfDiag.setText("");
        rbMan.setSelected(false);
        rbWoman.setSelected(false);
        lSubmenu.setDisable(true);
        tblElements.getSelectionModel().clearSelection();

    }

    private String rbResult(){
        if (rbMan.isSelected()) {
            return "M";
        } else{
            return "F";
        }
    }

    private void add(ActionEvent actionEvent){
        
        if(validateField()){
            PatientVo patientVo = new PatientVo();
            patientVo.setDni(tfDni.getText());
            patientVo.setName(tfName.getText());
            patientVo.setAge(tfAge.getText());
            patientVo.setDateBirth(Date.valueOf(dpDate.getValue()));
            patientVo.setDiagnosis(tfDiag.getText());
            patientVo.setSex(rbResult());
            patientVo = PatientBo.getInstance().create(patientVo);
        }else{
            alert.alert("Verifique los campos", IAlert.ERROR);
        }

    }

    private boolean update(ActionEvent actionEvent){
        if(!validateField()){return false;}
        PatientVo patientVo;
        try {
            patientVo = (PatientVo) getPatient().clone();
            if(getPatient().getDni().equals(tfDni.getText())
                && getPatient().getName().equals(tfName.getText()) 
                && getPatient().getAge().equals(tfAge.getText())
                && getPatient().getDateBirth().toString().equals(dpDate.getEditor().getText())
                && getPatient().getSex().equals(rbResult())
                && getPatient().getDiagnosis().equals(tfDiag.getText())){
                    alert.alert("Para actualizar debe cambiar al menos un campo", IAlert.SIMPLE);
            }else{
                patientVo.setDni(tfDni.getText());
                patientVo.setName(tfName.getText());
                patientVo.setAge(tfAge.getText());
                patientVo.setDateBirth(Date.valueOf(dpDate.getValue()));
                patientVo.setName(tfName.getText());
                patientVo.setDiagnosis(tfDiag.getText());
                patientVo.setSex(rbResult());
                if(PatientBo.getInstance().update(patientVo)){
                    tblElements.refresh();
                    alert.alert("Paciente actualizado con éxito", IAlert.SIMPLE);
                }else{
                    alert.alert("No se pudo actualizar paciente", IAlert.ERROR);
                }
            }
        } catch (CloneNotSupportedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return true;
    }

    private void delete(ActionEvent actionEvent){
        if(PatientBo.getInstance().softDelete(getPatient())){
            alert.alert("Se eliminó el paciente " + getPatient().getName(), IAlert.SIMPLE);
            fillTable();
            cleanUpField();
        }else{
            alert.alert("Error al tratar de eliminar paciente", IAlert.ERROR);
        }
    }

    private void paperBin(ActionEvent actionEvent){
        tblElements.setItems(PatientBo.getInstance().getElements().filtered(
            element->element.getDeletedAt()!=null));
    }

    private void all(ActionEvent actionEvent){
        fillTable();
    }

    private void recovery(ActionEvent actionEvent){
        if(PatientBo.getInstance().recovery(getPatient())){
            alert.alert("Se recuperó a paciente " + getPatient().getName(), IAlert.SIMPLE);
            fillTable();
            cleanUpField();
        }else{
            alert.alert("Error al tratar de recuperar paciente", IAlert.ERROR);
        }

    }

    private void search(){
        tblElements.setItems(PatientBo.getInstance().searchThroughList(tfSearch.getText()));
    }
    
    private void tableColumn(){

        TableColumn<PatientVo,String> tColumnDni = new TableColumn<>("DNI");
        tColumnDni.setMinWidth(10);
        tColumnDni.setPrefWidth(80);
        tColumnDni.setMaxWidth(5000);
        tColumnDni.setCellValueFactory(data -> data.getValue().getDniProperty());

        TableColumn<PatientVo,String> tColumnName = new TableColumn<>("Nombre");
        tColumnName.setMinWidth(10);
        tColumnName.setPrefWidth(179);
        tColumnName.setMaxWidth(5000);
        tColumnName.setCellValueFactory(data -> data.getValue().getNameProperty());

        TableColumn<PatientVo,String> tColumnAge = new TableColumn<>("Edad");
        tColumnAge.setMinWidth(10);
        tColumnAge.setPrefWidth(80);
        tColumnAge.setMaxWidth(5000);
        tColumnAge.setCellValueFactory(new PropertyValueFactory<PatientVo, String>("age"));

        TableColumn<PatientVo,Date> tColumnDateB = new TableColumn<>("Fecha de nacimiento");
        tColumnDateB.setMinWidth(10);
        tColumnDateB.setPrefWidth(150);
        tColumnDateB.setMaxWidth(5000);
        tColumnDateB.setCellValueFactory(data -> data.getValue().getDateBirthProperty());

        TableColumn<PatientVo,String> tColumnSex = new TableColumn<>("Sexo");
        tColumnSex.setMinWidth(10);
        tColumnSex.setPrefWidth(80);
        tColumnSex.setMaxWidth(5000);
        tColumnSex.setCellValueFactory(new PropertyValueFactory<PatientVo, String>("sex"));

        TableColumn<PatientVo,String> tColumnDiag = new TableColumn<>("Diagnostico");
        tColumnDiag.setMinWidth(10);
        tColumnDiag.setPrefWidth(220);
        tColumnDiag.setMaxWidth(5000);
        tColumnDiag.setCellValueFactory(new PropertyValueFactory<PatientVo, String>("diagnosis"));

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni,tColumnName,tColumnAge,tColumnDateB,tColumnSex,tColumnDiag));
    }

    private void fillTable(){
        tblElements.setItems(PatientBo.getInstance().getElements().filtered(
            element->element.getDeletedAt()==null));
    }

    private void loadElementInForm() {
        if (getPatient() != null) {
            tfDni.setText(getPatient().getDni());
            tfName.setText(getPatient().getName());
            tfAge.setText(getPatient().getAge());
            if (getPatient().getSex() != null) {
                if (getPatient().getSex().equals("M")) {
                    rbMan.setSelected(true);
                } else {
                    rbWoman.setSelected(true);
                }
            } else {
                rbMan.setSelected(false);
                rbWoman.setSelected(false);
            }
            dpDate.setValue(getPatient().getDateBirth().toLocalDate());
            tfDiag.setText(getPatient().getDiagnosis());
            lSubmenu.setDisable(false);
        }
    }

    private void eventSearch(){
        tfSearch.setOnKeyTyped(actionEvent->{
            search();
        });
        tfSearch.setOnMouseClicked(actionEvent->{
            search();
        });
    }

    private void events(){
        tblElements.getSelectionModel().selectedItemProperty().addListener(
            listener->{
                this.patient = tblElements.getSelectionModel().getSelectedItem();
                loadElementInForm();
                if(this.patient != null){
                    btnUpdate.setDisable(false);
                    btnCleanUp.setDisable(false);
                }else{
                    btnUpdate.setDisable(true);
                    btnCleanUp.setDisable(true);
                }
            }
        );
    }

    private boolean validateField(){
        boolean result = true;
        if(Charact.isEmpty(new FieldValidation(tfDni,lbNoticeDni))){result = false;}
        if(!Range.min(new FieldValidation(tfDni,lbNoticeDni),10)){result = false;}
        if(Charact.isEmpty(new FieldValidation(tfName,lbNoticeName))){result = false;}
        if(!Range.min(new FieldValidation(tfName,lbNoticeName),2)){result = false;}
        if(Charact.isEmpty(new FieldValidation(tfAge,lbNoticeAge))){result = false;}
        if(!Range.min(new FieldValidation(tfAge,lbNoticeAge),1)){result = false;}
        if(Charact.isEmpty(new FieldValidation(dpDate.getEditor(),lbNoticeDate))){result = false;}
        if(Charact.isEmpty(new FieldValidation(tfDiag,lbNoticeDiagnosis))){result = false;}
        if (group.getSelectedToggle() == null) {
            lbNoticeSex.setText("Seleccione una opción");
            group.selectedToggleProperty().addListener(listener->lbNoticeSex.setText(""));
            result = false;
        }
        return result;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alert = new AlertImplement();
        dpDate.setEditable(false);
        group = new ToggleGroup();
        rbMan.setToggleGroup(group);
        rbWoman.setToggleGroup(group);
        tableColumn();
        fillTable();
        loadElementInForm();

        btnAdd.setOnAction((actionEvent)->add(actionEvent));
        btnUpdate.setOnAction((actionEvent)->update(actionEvent));
        btnResponsibles.setOnAction((actionEvent)->addResponsible(actionEvent));
        btnCleanUp.setOnAction((actionEvent)->cleanUpField());
        btnDelete.setOnAction((actionEvent)->delete(actionEvent));
        btnPaperBin.setOnAction((actionEvent)->paperBin(actionEvent));
        btnAll.setOnAction((actionEvent)->all(actionEvent));
        btnRecovery.setOnAction((actionEvent)->recovery(actionEvent));
        btnSched.setOnAction((actionEvent)->schedule(actionEvent));

        eventSearch();
        events();

        Charact.numeros(new FieldValidation().withTf(tfDni));
        Range.max(tfDni, 10);
        
        Charact.letterWithSpace(new FieldValidation().withTf(tfName));
        Range.max(tfName, 50);

        Charact.numeros(new FieldValidation().withTf(tfAge));
        Range.max(tfAge, 30);

        Charact.letterWithSpace(new FieldValidation().withTf(tfDiag));
        Range.max(tfDiag, 200);
        
        Range.max(tfSearch, 50);
    }

    public PatientVo getPatient() {
        return patient;
    }
    
}
