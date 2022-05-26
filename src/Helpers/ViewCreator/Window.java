package Helpers.ViewCreator;

import java.net.URL;

import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Window {
    
    private Stage stage;
    private URL path;
    private Image logo;
    private String title;

    public Stage getStage() {
        return stage;
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public URL getPath() {
        return path;
    }
    public void setPath(URL path) {
        this.path = path;
    }
    public Image getRutaLogo() {
        return logo;
    }
    public void setRutaLogo(Image logo) {
        this.logo = logo;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    

}
