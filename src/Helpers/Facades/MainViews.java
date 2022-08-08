package Helpers.Facades;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import Controllers.Controller;
import Helpers.ViewsPath;
import Helpers.ViewCreator.SceneBuilder;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class MainViews {

    private Map<String,Scene> mainViewsPath;
    private Map<String,URL> views;
    private Pane container;

    public MainViews(Pane container){
        this.container = container;
        mainViewsPath = new HashMap<>();
        views = ViewsPath.getInstance().getViewsPath();
    }

    public Map<String,Scene> getMainViewsPath() {
        return mainViewsPath;
    }

    public void setMainViewsPath(Map<String,Scene> mainViewsPath) {
        this.mainViewsPath = mainViewsPath;
    }

    public Map<String, URL> getViews() {
        return views;
    }

    private Scene buildScene(SceneBuilder scene){
        scene.build();
        return scene.getScene();
    }
    
    /**
     * Agrega una scene principal
     * @param namePath nombre de la ruta de la scene
     * @return true si la ruta existe o false si no
     */
    public boolean add(String namePath){
        if(views.containsKey(namePath)){
            SceneBuilder scene = new SceneBuilder();
            scene.withPath(views.get(namePath));
            getMainViewsPath().put(namePath,buildScene(scene));
            return true;
        }
        System.out.println("No existe la ruta con el nombre " + namePath);
        return false;
    }

    /**
     * Agrega una scene principal
     * @param namePath nombre de la ruta de la scene
     * @param controller
     * @return true si la ruta existe o false si no
     */
    public boolean add(String namePath, Controller controller){
        if(views.containsKey(namePath)){
            SceneBuilder scene = new SceneBuilder();
            scene.withPath(views.get(namePath)).withController(controller);
            getMainViewsPath().put(namePath,buildScene(scene));
            return true;
        }
        System.out.println("No existe la ruta con el nombre " + namePath);
        return false;
    }

    /**
     * Carga una Scene en el Pane
     * @param namePath nombre de la ruta
     * @return true si la Scene existe, false si no
     */
    public boolean charge(String namePath){
        if(getMainViewsPath().containsKey(namePath)){
            this.container.getChildren().removeAll(this.container.getChildren());
            this.container.getChildren().add(getMainViewsPath().get(namePath).getRoot());
            return true;
        }
        System.out.println("No existe vista con el nombre " + namePath);
        return false;        
    }


    
}
