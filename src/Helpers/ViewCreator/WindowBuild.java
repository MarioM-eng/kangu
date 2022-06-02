package Helpers.ViewCreator;

import java.io.IOException;
import java.net.URL;

import Helpers.ViewsPath;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
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
        return new WindowBuild();
    }

    public WindowBuild withStage(Stage stage){
        this.window.setStage(stage);
        return this;
    }
    public WindowBuild withUrl(URL path){
        this.window.setPath(path);
        return this;
    }
    public WindowBuild withTitle(String title){
        this.window.setTitle(title);
        return this;
    }
    public WindowBuild withLogo(String path){ 
        this.window.setRutaLogo(new Image(path));
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
            if(this.window.getPath() != null){
                FXMLLoader fxmlLoader  = new FXMLLoader();
                fxmlLoader.setLocation(this.window.getPath());
                Parent root;
                try {
                    root = (AnchorPane) fxmlLoader.load();
                    Scene scene = new Scene(root);
                    this.window.getStage().setScene(scene);
                    this.window.getStage().getIcons().add(this.window.getRutaLogo());
                    this.window.getStage().setTitle(this.window.getTitle());
                    this.window.getStage().centerOnScreen();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    System.out.println("Error al asignar el root node" + e.getMessage());
                }
            }else{
                System.out.println("Sin ruta de vista");
            }
        }else{
            System.out.println("Sin Stage");
        }
        
        return window;
        // TODO Auto-generated method stub
        
    }

    @Override
    public void show() {
        if(validateStage()){
            this.window.getStage().show();
        }else{
            System.out.println("Sin Stage");
        }
        // TODO Auto-generated method stub
        
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
