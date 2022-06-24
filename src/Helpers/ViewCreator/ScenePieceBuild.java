package Helpers.ViewCreator;

import java.net.URL;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class ScenePieceBuild implements ISceneBuilder {

    private final static ScenePieceBuild singleton = new ScenePieceBuild();
    private ScenaPiece scenaPiece;
    

    private ScenePieceBuild(){
        scenaPiece = new ScenaPiece();
    }

    public static ScenePieceBuild getInstance(){
        return singleton;
    }

    public ScenePieceBuild withTitle(String title){
        scenaPiece.setTitle(title);
        return this;
    }
    public ScenePieceBuild withPath(URL path){
        scenaPiece.setPath(path);
        return this;
    }
    public ScenePieceBuild withPane(Pane pane){
        scenaPiece.setPane(pane);
        return this;
    }

    @Override
    public Scene build() {
        Scene scene = new SceneBuilder().withPath(scenaPiece.getPath()).build();
        scenaPiece.getPane().getChildren().clear();
        scenaPiece.getPane().getChildren().add(scene.getRoot());
        WindowBuild.getInstance().withTitle(scenaPiece.getTitle());
        
        return scene;
    }

    
    
}
