package Controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import Controllers.Schedules.SchedulesController;
import Helpers.HelperENCRYPT;
import Helpers.ViewsPath;
import Helpers.Alert.AlertImplement;
import Helpers.Facades.IAlert;
import Helpers.Facades.View;
import Helpers.Validate.Charact;
import Helpers.Validate.FieldValidation;
import Helpers.Validate.Range;
import Helpers.ViewCreator.SceneBuilder;
import Helpers.ViewCreator.WindowBuild;
import Models.UserBo;
import Models.UserVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class UserController implements Initializable {

    @FXML private TextField tfDni;
    @FXML private Label lbNoticeDni;
    @FXML private TextField tfName;
    @FXML private Label lbNoticeName;
    @FXML private TextField tfUserName;
    @FXML private Label lbNoticeUserName;
    @FXML private TextField tfPassword;
    @FXML private Label lbNoticePassword;
    @FXML private TextField tfSearch;


    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnProfiles;
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
    private Button btnResetPassword;

    @FXML
    private Node lSubmenu;

    @FXML
    private TableView<UserVo> tblElements;

    private WindowBuild windowBuild;
    private UserVo user;
    private AlertImplement alert;

    private void profiles(ActionEvent actionEvent){
        String title = "Perfiles";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        ProfileUserController controller = new ProfileUserController(getUser());
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

    private void appointments(ActionEvent actionEvent){
        SchedulesController schedules = new SchedulesController();
        schedules.getParams().put("proffessional", getUser());
        View.getInstance().createModal(schedules, "Horarios");
    }

    private void cleanUpField(){

        tfDni.setText("");
        tfName.setText("");
        tfUserName.setText("");
        tfPassword.setText("");
        lSubmenu.setDisable(true);
        tblElements.getSelectionModel().clearSelection();

    }

    private void search(){
        tblElements.setItems(UserBo.getInstance().searchThroughList(tfSearch.getText()));
    }

    private void paperBin(ActionEvent actionEvent){
        tblElements.setItems(UserBo.getInstance().getElements().filtered(
            element->element.getDeletedAt()!=null));
    }

    private void all(ActionEvent actionEvent){
        tblElements.setItems(UserBo.getInstance().getElements().filtered(
            element->element.getDeletedAt()==null));
    }
    
    private void resetPassword(ActionEvent actionEvent){
        alert.alert("¿Está seguro que desea reestablecer la contraseña del usuario " + getUser().getName(), IAlert.CONFIRMATION);
        if(alert.getResponse()){
            UserVo userVo = null;
            try {
                userVo = (UserVo)getUser().clone();
                String clave = HelperENCRYPT.Encriptar(userVo.getDni());
                userVo.setPassword(clave);
                if(UserBo.getInstance().update(userVo)){
                    tfPassword.setText(userVo.getDni());
                    tblElements.refresh();
                    alert.alert("Se reestableció la contraseña con éxito", IAlert.SIMPLE);
                }else{
                    alert.alert("Error al tratar de reestablecer la contraseña", IAlert.ERROR);
                }
            } catch (CloneNotSupportedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    private boolean update(ActionEvent actionEvent){
        if(!validateField()){return false;}
        UserVo userVo = null;
        try {
            userVo = (UserVo)getUser().clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        if(getUser().getDni().equals(tfDni.getText())
            && getUser().getName().equals(tfName.getText()) 
            && getUser().getUsername().equals(tfUserName.getText())){
                alert.alert("Para actualizar debe cambiar al menos un campo", IAlert.SIMPLE);
        }else{
            userVo.setDni(tfDni.getText());
            userVo.setName(tfName.getText());
            userVo.setUsername(tfUserName.getText());
            userVo.setPassword(tfPassword.getText());
            if(UserBo.getInstance().update(userVo)){
                tblElements.refresh();
                alert.alert("Usurio actualizado con éxito", IAlert.SIMPLE);
            }else{
                alert.alert("No se pudo actualizar usuario", IAlert.ERROR);
            }
        }
        return true;
    }

    private void delete(ActionEvent actionEvent){
        if(UserBo.getInstance().softDelete(getUser())){
            alert.alert("Se eliminó el usuario " + getUser().getName(), IAlert.SIMPLE);
            fillTable();
            cleanUpField();
        }else{
            alert.alert("Error al tratar de eliminar usuario", IAlert.ERROR);
        }
    }

    private void recovery(ActionEvent actionEvent){
        if(UserBo.getInstance().recovery(getUser())){
            alert.alert("Se recuperó a usuario " + getUser().getName(), IAlert.SIMPLE);
            fillTable();
            cleanUpField();
        }else{
            alert.alert("Error al tratar de recuperar usuario", IAlert.ERROR);
        }
    }

    private void add(ActionEvent actionEvent){
        if(validateField()){
            UserVo userVo = new UserVo();
            userVo.setDni(tfDni.getText());
            userVo.setName(tfName.getText());
            userVo.setUsername(tfUserName.getText());
            userVo.setPassword(tfDni.getText());
            if(!UserBo.getInstance().checkUserName(tfUserName.getText())){
                userVo = UserBo.getInstance().create(userVo);

                if(userVo == null){
                    alert.alert("El DNI " + tfDni.getText() + " ya existe", IAlert.ERROR);
                }else{
                    alert.alert("Usuario agregado con éxito", IAlert.SIMPLE);
                }
            }else{
                alert.alert("El nombre de usuario ya existe", IAlert.ERROR);
            }
        } else{
            alert.alert("Verifique los campos", IAlert.ERROR);
        }

    }

    private void tableColumn(){

        TableColumn<UserVo,String> tColumnDni = new TableColumn<>("DNI");
        tColumnDni.setMaxWidth(5000);
        tColumnDni.setCellValueFactory(data -> data.getValue().getDniProperty());

        TableColumn<UserVo,String> tColumnName = new TableColumn<>("Nombre");
        tColumnName.setMaxWidth(5000);
        tColumnName.setCellValueFactory(data -> data.getValue().getNameProperty());

        TableColumn<UserVo,String> tColumnUserName = new TableColumn<>("Usuario");
        tColumnUserName.setMaxWidth(5000);
        tColumnUserName.setCellValueFactory(new PropertyValueFactory<UserVo, String>("username"));

        tblElements.setItems(UserBo.getInstance().getElements().filtered(
            element->element.getDeletedAt()==null));

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni,tColumnName,tColumnUserName));
    }

    private void fillTable(){
        tblElements.setItems(UserBo.getInstance().getElements().filtered(
            element->element.getDeletedAt()==null));
    }

    private void loadElementInForm() {
        if (getUser() != null) {
            tfDni.setText(getUser().getDni());
            tfName.setText(getUser().getName());
            tfUserName.setText(getUser().getUsername());
            tfPassword.setText(getUser().getPassword());
            lSubmenu.setDisable(false);
        }
    }

    private void eventSearch(){
        tfSearch.setOnKeyTyped(event->{
            search();
        });
        tfSearch.setOnMouseClicked(event->{
            search();
        });
    }

    private void events(){
        tfName.setOnKeyTyped(event->{
            String name = null;
            if(tfName.getText().contains(" ")){
                name = tfName.getText().substring(0, tfName.getText().indexOf(" "));
            }else{
                name = tfName.getText();
            }
            tfUserName.setText(name + "_kangu");
        });

        tblElements.getSelectionModel().selectedItemProperty().addListener(
            listener->{
                this.user = tblElements.getSelectionModel().getSelectedItem();
                loadElementInForm();
                if(this.user != null){
                    btnResetPassword.setDisable(false);
                    btnUpdate.setDisable(false);
                    btnCleanUp.setDisable(false);
                }else{
                    btnResetPassword.setDisable(true);
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
        if(Charact.isEmpty(new FieldValidation(tfUserName,lbNoticeUserName))){result = false;}
        if(!Range.min(new FieldValidation(tfUserName,lbNoticeUserName),7)){result = false;}
        if(Charact.isEmpty(new FieldValidation(tfPassword,lbNoticePassword))){result = false;}
        return result;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alert = new AlertImplement();
        tfPassword.setEditable(false);
        tableColumn();
        fillTable();
        btnAdd.setOnAction(actionEvent->add(actionEvent));
        btnCleanUp.setOnAction(actionEvent->cleanUpField());
        btnProfiles.setOnAction(actionEvent->profiles(actionEvent));
        btnUpdate.setOnAction(actionEvent->update(actionEvent));
        btnDelete.setOnAction(actionEvent->delete(actionEvent));
        btnRecovery.setOnAction(actionEvent->recovery(actionEvent));
        btnPaperBin.setOnAction(actionEvent->paperBin(actionEvent));
        btnAll.setOnAction(actionEvent->all(actionEvent));
        btnResetPassword.setOnAction(actionEvent->resetPassword(actionEvent));

        eventSearch();
        events();

        Charact.numeros(new FieldValidation().withTf(tfDni));
        Range.max(tfDni, 10);
        
        Charact.letterWithSpace(new FieldValidation().withTf(tfName));
        Range.max(tfName, 50);

        Charact.letter(new FieldValidation().withTf(tfUserName));
        Range.max(tfUserName, 30);
        
        Range.max(tfSearch, 50);
    }

    public UserVo getUser() {
        return user;
    }
    
}
