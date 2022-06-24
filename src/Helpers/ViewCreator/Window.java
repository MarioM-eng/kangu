package Helpers.ViewCreator;

import java.net.URL;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Window {
    
    private Stage stage;
    private Image logo;
    private String title;
    private Object object;
    private Scene scene;

    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public Image getLogo() {
        return logo;
    }
    public void setLogo(Image logo) {
        this.logo = logo;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Object getObject() {
        return object;
    }
    public void setObject(Object object) {
        this.object = object;
    }
    public Scene getScene() {
        return scene;
    }
    public void setScene(Scene scene) {
        this.scene = scene;
    }

}
