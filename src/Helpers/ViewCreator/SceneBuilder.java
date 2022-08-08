package Helpers.ViewCreator;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class SceneBuilder implements IBuilder {

    private FXMLLoader fxmlLoader;
    private Scene scene;

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

    public void build(){
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error al asignar el root node" + e.getMessage());
        }
        setScene(new Scene(root));
    }

    public FXMLLoader getFxmlLoader() {
        return fxmlLoader;
    }

    public void setFxmlLoader(FXMLLoader fxmlLoader) {
        this.fxmlLoader = fxmlLoader;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    
    
}
