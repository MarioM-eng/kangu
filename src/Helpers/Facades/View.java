package Helpers.Facades;

import java.net.URL;

import Controllers.Controller;
import Helpers.ViewsPath;
import Helpers.ViewCreator.SceneBuilder;
import Helpers.ViewCreator.ScenePieceBuild;
import Helpers.ViewCreator.WindowBuild;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class View {

    private WindowBuild windowBuild;
    private static View singleton = new View();

    private View(){}

    public static View getInstance(){
        return singleton;
    }

    public void createScene(Controller controller, String nombre, Pane pane){
        ScenePieceBuild scene = ScenePieceBuild.getNewIntance();
        scene.withPath(ViewsPath.getInstance().getViewsPath().get(nombre))
        .withTitle(nombre)
        .withPane(pane)
        .withController(controller);
        scene.build();
    }
    
    public void createModal(Controller controller, String nombre){
        URL ruta = ViewsPath.getInstance().getViewsPath().get(nombre);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).withController(controller);
        Scene scene = sb.build();
        windowBuild = WindowBuild.getNewInstance();
        String logo = "Images/logo.jpeg";
        windowBuild.withLogo(logo).modal();
        windowBuild.withTitle(nombre).withScene(scene).build();
        windowBuild.show();
    }

}
