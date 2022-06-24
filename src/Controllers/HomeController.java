package Controllers;

import Helpers.ViewsPath;
import Helpers.ViewCreator.ScenePieceBuild;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class HomeController {

    @FXML
    private AnchorPane anchorLayout;


    @FXML
    public void usersBtnClick(ActionEvent aEvent){
        ScenePieceBuild.getInstance().withPath(ViewsPath.getInstance().getViewsPath().get("Usuario"));
        ScenePieceBuild.getInstance().withTitle("Usuario");
        ScenePieceBuild.getInstance().build();
    }

    @FXML
    public void patientsBtnClick(ActionEvent aEvent){
        ScenePieceBuild.getInstance().withPath(ViewsPath.getInstance().getViewsPath().get("Paciente"));
        ScenePieceBuild.getInstance().withTitle("Paciente");
        ScenePieceBuild.getInstance().build();
    }
    
}
