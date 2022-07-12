package Helpers.ViewCreator;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ScenePieceBuild implements ISceneBuilder {

    private final static ScenePieceBuild singleton = new ScenePieceBuild();
    private ScenaPiece scenaPiece;
    FXMLLoader fxmlLoader;
    

    private ScenePieceBuild(){
        scenaPiece = new ScenaPiece();
        fxmlLoader = new FXMLLoader();
    }

    public static ScenePieceBuild getNewIntance(){
        return new ScenePieceBuild();
    }

    public static ScenePieceBuild getInstance(){
        return singleton;
    }

    public ScenePieceBuild withTitle(String title){
        scenaPiece.setTitle(title);
        return this;
    }
    public ScenePieceBuild withPath(URL path){
        fxmlLoader.setLocation(path);
        return this;
    }
    public ScenePieceBuild withPane(Pane pane){
        scenaPiece.setPane(pane);
        return this;
    }
    public ScenePieceBuild withController(Object controller){
        fxmlLoader.setController(controller);
        return this;
    }

    @Override
    public Scene build() {
        Scene scene = null;
        Parent root = null;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            System.out.println("Error al asignar el root node" + e.getMessage());
        }
        scene = new Scene(root);
        scenaPiece.getPane().getChildren().clear();
        scenaPiece.getPane().getChildren().add(scene.getRoot());
        //WindowBuild.getInstance().withTitle(scenaPiece.getTitle());
        
        return scene;
    }

    
    
}
