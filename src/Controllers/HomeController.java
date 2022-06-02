package Controllers;

import java.net.URL;

import Helpers.ViewsPath;
import Helpers.ViewCreator.WindowBuild;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class HomeController {

    @FXML
    private AnchorPane anchorLayout;

    @FXML
    public void productosBtnClick(ActionEvent aEvent){
        String title = "Inventario";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        WindowBuild.getInstance().withTitle(title).withUrl(ruta).build();
        //StageCreator.getInstance().closeScene(anchorLayout);
    }

    @FXML
    public void ventasBtnClick(ActionEvent aEvent){
        String title = "Ventas";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        WindowBuild.getInstance().withTitle(title).withUrl(ruta).build();
    }

    @FXML
    public void usersBtnClick(ActionEvent aEvent){
        String title = "Usuarios";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        WindowBuild.getInstance().withTitle(title).withUrl(ruta).build();
    }

    // @FXML
    // public void recibosBtnClick(ActionEvent aEvent){
    //     StageCreator.getInstance().createScene("/Views/products.fxml", "Productos");
    //     StageCreator.getInstance().closeScene(anchorLayout);
    // }
    
}
