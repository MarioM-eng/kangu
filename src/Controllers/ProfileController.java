package Controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class ProfileController implements Initializable{

    private Object param;
    ManyToManyBo<UserVo,ProfileVo,UserBo,ProfileBo> manyToManyBo;

    @FXML
    private ComboBox<ProfileVo> cbProfiles;

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;

    @FXML
    private TableView<ManyToMany<UserVo,ProfileVo>> tblElements;

    public ProfileController(){
        manyToManyBo = new ManyToManyBo<>(new UserVo(), new ProfileVo(), UserBo.getInstance(),ProfileBo.getInstance());
    }

    public ProfileController(Object param){
        this.param = param;
        manyToManyBo = new ManyToManyBo<>(new UserVo(), new ProfileVo(), UserBo.getInstance(),ProfileBo.getInstance());
    }

    private void add(ActionEvent actionEvent){

        ProfileVo profileVo = cbProfiles.getSelectionModel().getSelectedItem();

        if(profileVo != null){
            UserVo userVo = (UserVo) param;
            manyToManyBo.create(new ManyToMany<UserVo, ProfileVo>(userVo,profileVo));
        }else{
            System.out.println("Debe seleccionar un perfil");
        }

    }

    private void remove(ActionEvent actionEvent){

        manyToManyBo.delete(tblElements.getSelectionModel().getSelectedItem());
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
    private void loadElementInForm(){
        tblElements.getSelectionModel().selectedItemProperty().addListener(e->{
            ProfileVo profileVo = tblElements.getSelectionModel().getSelectedItem().getElement_2();
            if(profileVo != null){
                cbProfiles.getSelectionModel().select(profileVo);
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        fillTable(tblElements);
        fillCombo(cbProfiles);
        loadElementInForm();

        btnAdd.setOnAction(actionEvent->{
            add(actionEvent);
        });
        btnRemove.setOnAction(actionEvent->{
            remove(actionEvent);
        });
        
    }
    
}
