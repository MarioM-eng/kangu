package Controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import Helpers.ViewsPath;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class UserController implements Initializable {

    @FXML
    private TextField tfDni;
    @FXML
    private TextField tfName;
    @FXML
    private TextField tfUserName;
    @FXML
    private TextField tfPassword;
    @FXML
    private TextField tfSearch;


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
    private Node lSubmenu;

    @FXML
    private TableView<UserVo> tblElements;

    private WindowBuild windowBuild;

    private void profiles(ActionEvent actionEvent){
        UserVo UserVo = tblElements.getSelectionModel().getSelectedItem();
        String title = "Perfiles";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        ProfileUserController controller = new ProfileUserController(UserVo);
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
            element->element.getDeleted_at()!=null));
    }

    private void all(ActionEvent actionEvent){
        tblElements.setItems(UserBo.getInstance().getElements().filtered(
            element->element.getDeleted_at()==null));
    }

    private void update(ActionEvent actionEvent){

        UserVo userVo = tblElements.getSelectionModel().getSelectedItem();
        userVo.setDni(tfDni.getText());
        userVo.setName(tfName.getText());
        userVo.setUsername(tfUserName.getText());
        userVo.setPassword(tfPassword.getText());

        UserBo.getInstance().update(userVo);
        tblElements.refresh();

    }

    private void delete(ActionEvent actionEvent){

        UserVo userVo = tblElements.getSelectionModel().getSelectedItem();
        UserBo.getInstance().softDelete(userVo);
    }

    private void recovery(ActionEvent actionEvent){

        UserVo userVo = tblElements.getSelectionModel().getSelectedItem();
        UserBo.getInstance().recovery(userVo);

    }

    private void add(ActionEvent actionEvent){
        
        UserVo userVo = new UserVo();
        userVo.setDni(tfDni.getText());
        userVo.setName(tfName.getText());
        userVo.setUsername(tfUserName.getText());
        userVo.setPassword(tfDni.getText());
        
        if(!UserBo.getInstance().checkUserName(tfUserName.getText())){
            userVo = UserBo.getInstance().create(userVo);

            if(userVo == null){
                System.out.println("El DNI " + tfDni.getText() + " ya existe");
            }
        }else{
            System.out.println("El nombre de usuario ya existe");
        }

    }

    private void fillTable(TableView<UserVo> tblElements){

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
            element->element.getDeleted_at()==null));

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni,tColumnName,tColumnUserName));
    }

    private void loadElementInForm(){
        
        tblElements.getSelectionModel().selectedItemProperty().addListener(e->{
            UserVo userVo = tblElements.getSelectionModel().getSelectedItem();
            if(userVo != null){
                tfDni.setText(userVo.getDni());
                tfName.setText(userVo.getName());
                tfUserName.setText(userVo.getUsername());
                tfPassword.setText(userVo.getPassword());
                lSubmenu.setDisable(false);
            }         
        });
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
            tfUserName.setText(tfName.getText() + "_kangu");
        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        tfPassword.setEditable(false);
        fillTable(tblElements);
        loadElementInForm();
        btnAdd.setOnAction(actionEvent->{
            add(actionEvent);
        });
        btnCleanUp.setOnAction(actionEvent->{
            cleanUpField(actionEvent);
        });
        btnProfiles.setOnAction(actionEvent->{
            profiles(actionEvent);
        });
        btnUpdate.setOnAction(actionEvent->{
            update(actionEvent);
        });
        btnDelete.setOnAction(actionEvent->{
            delete(actionEvent);
        });
        btnRecovery.setOnAction(actionEvent->{
            recovery(actionEvent);
        });
        btnPaperBin.setOnAction(actionEvent->{
            paperBin(actionEvent);
        });
        btnAll.setOnAction(actionEvent->{
            all(actionEvent);
        });

        eventSearch();
        events();
        
    }
    
}
