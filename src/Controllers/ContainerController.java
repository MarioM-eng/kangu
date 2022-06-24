package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Helpers.ViewsPath;
import Helpers.ViewCreator.ScenePieceBuild;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.HBox;

public class ContainerController implements Initializable {

    @FXML
    private HBox container;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ScenePieceBuild.getInstance().withPane(container)
        .withPath(ViewsPath.getInstance().getViewsPath().get("Principal"))
        .withTitle("Principal")
        .build();
        
    }
    
}
