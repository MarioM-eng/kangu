package Controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import Helpers.Alert.AlertImplement;
import Helpers.Facades.IAlert;
import Models.ProfileBo;
import Models.ProfileVo;
import Models.UserBo;
import Models.UserVo;
import Models.Relationships.ManyToMany;
import Models.Relationships.ManyToManyBo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProfileUserController implements Initializable{

    private Object param;
    private ManyToManyBo<UserVo,ProfileVo,UserBo,ProfileBo> manyToManyBo;
    private AlertImplement alert;

    @FXML private ComboBox<ProfileVo> cbProfiles;
    @FXML private Label lbNoticeProfile;

    @FXML private Button btnAdd;
    @FXML private Button btnRemove;

    @FXML private TableView<ManyToMany<UserVo,ProfileVo>> tblElements;

    public ProfileUserController(){
        manyToManyBo = new ManyToManyBo<>(new UserVo(), new ProfileVo(), UserBo.getInstance(),ProfileBo.getInstance());
    }

    public ProfileUserController(Object param){
        this.param = param;
        manyToManyBo = new ManyToManyBo<>(new UserVo(), new ProfileVo(), UserBo.getInstance(),ProfileBo.getInstance());
    }

    private void add(ActionEvent actionEvent){

        ProfileVo profileVo = cbProfiles.getSelectionModel().getSelectedItem();

        if(profileVo != null){
            UserVo userVo = (UserVo) param;
            ManyToMany<UserVo, ProfileVo> profileUser = new ManyToMany<UserVo, ProfileVo>(userVo,profileVo);
            if(!tblElements.getItems().contains(profileUser))
                if(manyToManyBo.create(profileUser)){
                    alert.alert(profileVo +" fue agregado a "+ userVo, IAlert.SIMPLE);
                }else{
                    alert.alert("No fue posible registar " + profileVo + " para " + userVo + " a la base de datos",IAlert.ERROR);
                }
            else 
                alert.alert("El perfil " + profileVo + " para el usuario " + userVo + " ya existe",IAlert.ERROR);
        }else{
            lbNoticeProfile.setText("Seleccione un perfil");
            cbProfiles.getSelectionModel().selectedItemProperty().addListener(
                listener->lbNoticeProfile.setText("")
            );
        }

    }

    private void remove(ActionEvent actionEvent){
        if(manyToManyBo.delete(tblElements.getSelectionModel().getSelectedItem())){
            alert.alert("Elemento eliminado", IAlert.SIMPLE);
        }else{
            alert.alert("No fue posible eliminar elemento de la base de datos", IAlert.ERROR);
        }
    }

    private void fillTable(TableView<ManyToMany<UserVo,ProfileVo>> tblElements){
        TableColumn<ManyToMany<UserVo,ProfileVo>,String> tColumnName = new TableColumn<>("Nombre");
        tColumnName.setMinWidth(10);
        tColumnName.setPrefWidth(179);
        tColumnName.setMaxWidth(5000);
        tColumnName.setCellValueFactory(data -> data.getValue().getElement_2().getNameProperty());

        tblElements.setItems(manyToManyBo.getElements().filtered(
                element-> element.getElement_1().equals(param)
            ));

        tblElements.getColumns().addAll(Arrays.asList(tColumnName));
    }

    private void fillCombo(ComboBox<ProfileVo> combo){
        combo.setItems(ProfileBo.getInstance().getElements());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        alert = new AlertImplement();
        fillTable(tblElements);
        fillCombo(cbProfiles);

        btnAdd.setOnAction(actionEvent->{
            add(actionEvent);
        });
        btnRemove.setOnAction(actionEvent->{
            remove(actionEvent);
        });
        
    }
    
}
