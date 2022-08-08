package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Helpers.Facades.MainViews;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class ContainerController implements Initializable {

    @FXML private HBox container;
    @FXML private Button btnAppointments;
    @FXML private Button btnUsers;
    @FXML private Button btnPatients;
    @FXML private Pane pButtons;
    
    private void mark(Button btn){
        pButtons.getChildren().forEach(
            button->{
                if(!button.getClass().equals(Separator.class)){
                    Button bt = (Button)button;
                    if(bt.equals(btn)){
                        bt.getStyleClass().forEach(
                            style->{
                                if(style.equals("btn-blue")){
                                    bt.getStyleClass().remove(style);
                                    style = "btn-gray";
                                    bt.getStyleClass().add(style);
                                }
                            }
                        );
                    }else{
                        bt.getStyleClass().forEach(
                            style->{
                                if(style.equals("btn-gray")){
                                    bt.getStyleClass().remove(style);
                                    style = "btn-blue";
                                    bt.getStyleClass().add(style);
                                }
                            }
                        );
                    }
                }
            }
        );
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //View.getInstance().setPaneMain(container);
        MainViews mainViews = new MainViews(container);
        mainViews.add("Agendas");
        mainViews.add("Usuario");
        mainViews.add("Paciente");
        btnAppointments.setOnAction(
            action->{
                mark(btnAppointments);
                mainViews.charge("Agendas");
            }
        );
        btnUsers.setOnAction(
            action->{
                mark(btnUsers);
                mainViews.charge("Usuario");
            }
        );
        btnPatients.setOnAction(
            action->{
                mark(btnPatients);
                mainViews.charge("Paciente");
            }
        );

        mark(btnAppointments);
        mainViews.charge("Agendas");
        
    }
    
}
