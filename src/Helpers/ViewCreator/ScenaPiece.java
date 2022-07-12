package Helpers.ViewCreator;

import java.net.URL;

import javafx.scene.layout.Pane;

public class ScenaPiece {

    private Pane pane;
    private URL path;
    private String title;
    private Object controller;

    public Pane getPane() {
        return pane;
    }
    public void setPane(Pane pane) {
        this.pane = pane;
    }
    public URL getPath() {
        return path;
    }
    public void setPath(URL path) {
        this.path = path;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public Object getController() {
        return controller;
    }
    public void setController(Object controller) {
        this.controller = controller;
    }
    
    
}
