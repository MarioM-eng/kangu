package Controllers;

import java.net.URL;
import java.sql.Date;
import java.util.Arrays;
import java.util.ResourceBundle;

import Helpers.ViewsPath;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;

public class PatientController implements Initializable {

    @FXML
    private TextField tfDni;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfAge;
    @FXML
    private TextField tfSearch;

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
    private Button btnResponsibles;
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
    private Button btnCleanUp;
    @FXML
    private Button btnSched;

    @FXML
    private Node lSubmenu;

    @FXML
    private TableView<PatientVo> tblElements;

    private WindowBuild windowBuild;

    private void addResponsible(ActionEvent actionEvent){
        PatientVo patientVo = tblElements.getSelectionModel().getSelectedItem();
        String title = "Responsabilidad";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        ResponsabilityController controller = new ResponsabilityController(patientVo);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).withController(controller);
        Scene scene = sb.build();
        if(windowBuild == null){
            windowBuild = WindowBuild.getNewInstance();
            String logo = "Images/logo.jpeg";
            windowBuild.withLogo(logo).modal();
        }
        windowBuild.withTitle(title).withScene(scene).build();
        windowBuild.show();
    }

    private void schedule(ActionEvent actionEvent){
        PatientVo patientVo = tblElements.getSelectionModel().getSelectedItem();
        String title = "Agenda";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        ScheduleController controller = new ScheduleController(patientVo);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).withController(controller);
        Scene scene = sb.build();
        if(windowBuild == null){
            windowBuild = WindowBuild.getNewInstance();
            String logo = "Images/logo.jpeg";
            windowBuild.withLogo(logo).modal();
        }
        windowBuild.withTitle(title).withScene(scene).build();
        windowBuild.show();
    }

    private void cleanUpField(ActionEvent actionEvent){

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

    private void add(ActionEvent actionEvent){

        PatientVo patientVo = new PatientVo();
        patientVo.setDni(tfDni.getText());
        patientVo.setName(tfName.getText());
        patientVo.setAge(tfAge.getText());
        patientVo.setDateBirth(Date.valueOf(dpDate.getValue()));
        patientVo.setDiagnosis(tfDiag.getText());
        if (rbMan.isSelected()) {
            patientVo.setSex("M");
        }else if(rbWoman.isSelected()){
            patientVo.setSex("F");
        }

        patientVo = PatientBo.getInstance().create(patientVo);

    }

    private void update(ActionEvent actionEvent){

        PatientVo patientVo = tblElements.getSelectionModel().getSelectedItem();
        patientVo.setDni(tfDni.getText());
        patientVo.setName(tfName.getText());
        patientVo.setAge(tfAge.getText());
        patientVo.setDateBirth(Date.valueOf(dpDate.getValue()));
        patientVo.setName(tfName.getText());
        patientVo.setDiagnosis(tfDiag.getText());
        if (rbMan.isSelected()) {
            patientVo.setSex("M");
        }else if(rbWoman.isSelected()){
            patientVo.setSex("F");
        }
        PatientBo.getInstance().update(patientVo);
        tblElements.refresh();

    }

    private void delete(ActionEvent actionEvent){

        PatientVo patientVo = tblElements.getSelectionModel().getSelectedItem();
        PatientBo.getInstance().softDelete(patientVo);
    }

    private void paperBin(ActionEvent actionEvent){
        tblElements.setItems(PatientBo.getInstance().getElements().filtered(
            element->element.getDeleted_at()!=null));
    }

    private void all(ActionEvent actionEvent){
        tblElements.setItems(PatientBo.getInstance().getElements().filtered(
            element->element.getDeleted_at()==null));
    }

    private void recovery(ActionEvent actionEvent){

        PatientVo patientVo = tblElements.getSelectionModel().getSelectedItem();
        PatientBo.getInstance().recovery(patientVo);

    }

    private void search(){
        tblElements.setItems(PatientBo.getInstance().searchThroughList(tfSearch.getText()));
    }
    
    private void fillTablePatients(TableView<PatientVo> tblElements){

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

        tblElements.setItems(PatientBo.getInstance().getElements().filtered(
            element->element.getDeleted_at()==null));

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni,tColumnName,tColumnAge,tColumnDateB,tColumnSex,tColumnDiag));
    }

    private void loadElementInForm(){
        
        tblElements.getSelectionModel().selectedItemProperty().addListener(e->{
            PatientVo patientVo = tblElements.getSelectionModel().getSelectedItem();
            if(patientVo != null){
                tfDni.setText(patientVo.getDni());
                tfName.setText(patientVo.getName());
                tfAge.setText(patientVo.getAge());
                if(patientVo.getSex() != null){
                    if(patientVo.getSex().equals("M")){
                        rbMan.setSelected(true);
                    }else{
                        rbWoman.setSelected(true);
                    }
                }else{
                    rbMan.setSelected(false);
                    rbWoman.setSelected(false);
                }
                    
                dpDate.setValue(patientVo.getDateBirth().toLocalDate());
                tfDiag.setText(patientVo.getDiagnosis());
                lSubmenu.setDisable(false);
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
        dpDate.setEditable(false);
        ToggleGroup group = new ToggleGroup();
        rbMan.setToggleGroup(group);
        rbWoman.setToggleGroup(group);
        fillTablePatients(tblElements);
        loadElementInForm();

        btnAdd.setOnAction((actionEvent)->{
            add(actionEvent);
        });

        btnUpdate.setOnAction((actionEvent)->{
            update(actionEvent);
        });

        btnResponsibles.setOnAction((actionEvent)->{
            addResponsible(actionEvent);
        });

        btnCleanUp.setOnAction((actionEvent)->{
            cleanUpField(actionEvent);
        });

        btnDelete.setOnAction((actionEvent)->{
            delete(actionEvent);
        });

        btnPaperBin.setOnAction((actionEvent)->{
            paperBin(actionEvent);
        });

        btnAll.setOnAction((actionEvent)->{
            all(actionEvent);
        });

        btnRecovery.setOnAction((actionEvent)->{
            recovery(actionEvent);
        });
        btnSched.setOnAction((actionEvent)->{
            schedule(actionEvent);
        });
        eventSearch();
    }


    
}
