package Helpers.ViewCreator;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneBuilder implements ISceneBuilder {

    private static SceneBuilder singleton = new SceneBuilder();
    FXMLLoader fxmlLoader;

    public SceneBuilder(){
        fxmlLoader  = new FXMLLoader();
    }

    public SceneBuilder withController(Object controller){
        fxmlLoader.setController(controller);
        return this;
    }

    public SceneBuilder withPath(URL path){
        fxmlLoader.setLocation(path);
        return this;
    }

    public Scene build(){
        Scene scene = null;
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error al asignar el root node" + e.getMessage());
        }
        scene = new Scene(root);
        return scene;
    }
    
}
