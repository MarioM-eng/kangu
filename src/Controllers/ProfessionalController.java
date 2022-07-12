package Controllers;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import Controllers.Schedule.AppointmentController;
import Models.ProfileBo;
import Models.ProfileVo;
import Models.UserBo;
import Models.UserVo;
import Models.Relationships.ManyToManyBo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Region;

public class ProfessionalController extends Controller implements Initializable {

    @FXML
    private Button btnAdd;
    @FXML
    private Button btnRemove;
    @FXML
    private ComboBox<ProfileVo> cbSpecialty;
    @FXML
    private ComboBox<UserVo> cbProfession;
    @FXML
    private TableView<UserVo> tbElements;

    ManyToManyBo<UserVo, ProfileVo, UserBo, ProfileBo> manyToManyBo;
    private ObservableList<UserVo> professionals;
    private AppointmentController appointmentController;

    public ProfessionalController(AppointmentController appointmentController){
        manyToManyBo = new ManyToManyBo<>(
            new UserVo(), 
            new ProfileVo(), 
            UserBo.getInstance(), 
            ProfileBo.getInstance());
        professionals = FXCollections.observableArrayList();
        this.appointmentController = appointmentController;
    }
    
    private void add(ActionEvent actionEvent) {

        UserVo userVo = cbProfession.getSelectionModel().getSelectedItem();
        professionals.add(userVo);

    }

    private void remove(ActionEvent actionEvent) {

        UserVo userVo = cbProfession.getSelectionModel().getSelectedItem();
        professionals.remove(userVo);

    }

    private void tableComumn(TableView<UserVo> tblElements) {

        TableColumn<UserVo, String> tColumnDni = new TableColumn<>("DNI");
        tColumnDni.setMaxWidth(5000);
        tColumnDni.setCellValueFactory(data -> data.getValue().getDniProperty());

        TableColumn<UserVo, String> tColumnName = new TableColumn<>("Nombre");
        tColumnName.setMaxWidth(5000);
        tColumnName.setCellValueFactory(data -> data.getValue().getNameProperty());

        tblElements.getColumns().addAll(Arrays.asList(tColumnDni, tColumnName));
    }

    private void filltable(TableView<UserVo> tblElements) {
        tableComumn(tblElements);
        tblElements.setItems(professionals);
    }

    private void fillComboBox(ComboBox combo, ObservableList list) {
        combo.setItems(list);
    }

    private void events() {
        // Evento para que el comboBox de profesionales se llene dependiendo la
        // especialidad
        cbSpecialty.getSelectionModel().selectedItemProperty().addListener(event -> {

            ProfileVo profileVo = cbSpecialty.getSelectionModel().getSelectedItem();

            fillComboBox(cbProfession, manyToManyBo.makeAObject(UserVo.class, profileVo));

        });

        // Evento para que al seleccionar un elemento de la tabla, abre la ventana de agenda
        tbElements.getSelectionModel().selectedItemProperty().addListener(
                event -> {
                    tbElements.setOnMouseClicked(e->{
                        if(e.getClickCount() >= 2){
                            if(e.getPickResult().getIntersectedNode().getClass().equals(Region.class)){
                                if(tbElements.getSelectionModel().getSelectedItem() != null){
                                    tbElements.getSelectionModel().clearSelection();
                                }
                            }else{
                                if(tbElements.getSelectionModel().getSelectedItem() != null){
                                    UserVo userVo = tbElements.getSelectionModel().getSelectedItem();
                                    this.appointmentController.getParams().put("professional", userVo);
                                    this.appointmentController.charge();
                                }
                            }
                        }else{
                            if(e.getPickResult().getIntersectedNode().getClass().equals(Region.class)){
                                if(tbElements.getSelectionModel().getSelectedItem() != null){
                                    tbElements.getSelectionModel().clearSelection();
                                }
                            }
                        }
                    });
                    
                });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        events();
        fillComboBox(cbSpecialty, ProfileBo.getInstance().getElements().filtered(
                el -> el.getId() != 1 && el.getId() != 2));


        filltable(tbElements);

        btnAdd.setOnAction(actionEvent -> {
            add(actionEvent);
        });
        btnRemove.setOnAction(actionEvent -> {
            remove(actionEvent);
        });
        
    }
    
}
