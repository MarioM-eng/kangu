package Helpers.ViewCreator;

import java.io.IOException;

import Helpers.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WindowBuild implements IBuilder{

    private final static WindowBuild singleton = new WindowBuild();
    private Window window;

    private WindowBuild(){
        this.window = new Window();
    }

    public Window getWindow(){
        return this.window;
    }

    public static WindowBuild getInstance(){
        return singleton;
    }

    public static WindowBuild getNewInstance(){
        WindowBuild windowBuild = new WindowBuild();
        Stage stage = new Stage();
        windowBuild.withStage(stage);
        return windowBuild;
    }

    public WindowBuild withStage(Stage stage){
        this.window.setStage(stage);
        return this;
    }
    public WindowBuild withTitle(String title){
        this.window.setTitle(title);
        return this;
    }
    public WindowBuild withLogo(String path){ 
        this.window.setLogo(new Image(path));
        return this;
    }
    public WindowBuild withObject(Object object){ 
        this.window.setObject(object);
        return this;
    }

    public WindowBuild withScene(Scene scene){
        this.window.setScene(scene);
        return this;
    }

    private boolean validateStage(){
        if(this.window.getStage() == null){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Window build() {
        if(validateStage()){
            this.window.getStage().setScene(this.window.getScene());
            //this.window.getStage().getScene().setUserData(this.window.getObject());
            this.window.getStage().getIcons().add(this.window.getLogo());
            this.window.getStage().setTitle(this.window.getTitle());
            this.window.getStage().centerOnScreen();
        }else{
            System.out.println("Sin Stage");
        }
        
        return window;
        // TODO Auto-generated method stub
        
    }

    @Override
    public void show() {
        this.window.getStage().show();        
    }

    @Override
    public void showAndWait() {
        this.window.getStage().showAndWait();
        
    }

    @Override
    public void modal() {
        
        this.window.getStage().initModality(Modality.APPLICATION_MODAL);
        
    }

    @Override
    public void setDataUser(Object object) {
        this.window.getStage().setUserData(object);
    }

    @Override
    public Object getDataUser() {
        return this.window.getStage().getUserData();
    }

    @Override
    public void close() {
        this.window.getStage().close();
        // TODO Auto-generated method stub
        
    }
    
    /**
     * Crea una nueva vista dentro de la scene principal
     * @param nameView es el nombre de la vista 
     */
    public void newView(String nameView){
        HBox hBox = (HBox) this.window.getStage().getScene().getRoot().getChildrenUnmodifiable().get(1);
        FXMLLoader fxmlLoader  = new FXMLLoader();
        fxmlLoader.setLocation(ViewsPath.getInstance().getViewsPath().get(nameView));
        Parent root;
        try {
            root = fxmlLoader.load();
            hBox.getChildren().setAll(root);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
}
