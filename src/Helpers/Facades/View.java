package Helpers.Facades;

import java.net.URL;

import Controllers.Controller;
import Helpers.ViewsPath;
import Helpers.ViewCreator.SceneBuilder;
import Helpers.ViewCreator.WindowBuild;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class View {

    private WindowBuild windowBuild;
    private Pane paneMain;
    private static View singleton = new View();

    private View(){}

    public static View getInstance(){
        return singleton;
    }

    public Pane getPaneMain() {
        return paneMain;
    }

    public void setPaneMain(Pane paneMain) {
        this.paneMain = paneMain;
    }

    public void createEmbed(Controller controller, String path, Pane pane){
        URL ruta = ViewsPath.getInstance().getViewsPath().get(path);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).withController(controller).build();
        pane.getChildren().add(sb.getScene().getRoot());

    }

    /**
     * Crea una vista(una escena dentro de una venta) 
     * @param controller El controlador de la Scene
     * @param path La ruta de la Scene
     * @param stage la ventana que mostrará a la Scene
     */
    public void create(Controller controller, String path, Stage stage){
        URL ruta = ViewsPath.getInstance().getViewsPath().get(path);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).withController(controller).build();
        Scene scene = sb.getScene();
        windowBuild = WindowBuild.getNewInstance();
        windowBuild.withStage(stage);
        String logo = "Images/logo.jpeg";
        windowBuild.withLogo(logo);
        windowBuild.withTitle(path).withScene(scene).build();
        if(!windowBuild.getWindow().getStage().isShowing()){
            windowBuild.show();
        }
    }

    /**
     * Crea una vista(una escena dentro de una venta)
     * @param path La ruta de la Scene
     * @param stage la ventana que mostrará a la Scene
     */
    public void create(String path, Stage stage){
        URL ruta = ViewsPath.getInstance().getViewsPath().get(path);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).build();
        Scene scene = sb.getScene();
        windowBuild = WindowBuild.getNewInstance();
        windowBuild.withStage(stage);
        String logo = "Images/logo.jpeg";
        windowBuild.withLogo(logo);
        windowBuild.withTitle(path).withScene(scene).build();
        if(!windowBuild.getWindow().getStage().isShowing()){
            windowBuild.show();
        }
    }

    /**
     * Crea una vista(una escena dentro de una venta) en el Stage actual
     * @param controller El controlador de la Scene
     * @param path La ruta de la Scene
     */
    public void create(Controller controller, String path){
        URL ruta = ViewsPath.getInstance().getViewsPath().get(path);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).withController(controller).build();
        Scene scene = sb.getScene();
        windowBuild = WindowBuild.getInstance();
        String logo = "Images/logo.jpeg";
        windowBuild.withLogo(logo);
        windowBuild.withTitle(path).withScene(scene).build();
        if(!windowBuild.getWindow().getStage().isShowing()){
            windowBuild.show();
        }
    }

    /**
     * Crea una vista(una escena dentro de una venta) en el Stage actual
     * @param controller El controlador de la Scene
     * @param path El nombre de la ruta de la Scene
     */
    public void create(String path){
        URL ruta = ViewsPath.getInstance().getViewsPath().get(path);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).build();
        Scene scene = sb.getScene();
        windowBuild = WindowBuild.getInstance();
        String logo = "Images/logo.jpeg";
        windowBuild.withLogo(logo)
        .withTitle(path).withScene(scene).build();
        if(!windowBuild.getWindow().getStage().isShowing()){
            windowBuild.show();
        }
    }

    /**Métodos para crear vistas independientes */
    
    /**
     * Crea una ventana Modal
     * @param controller Controlador de la Scene
     * @param nombre Nombre de la ruta de la Scene
     */
    public void createModal(Controller controller, String nombre){
        URL ruta = ViewsPath.getInstance().getViewsPath().get(nombre);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).withController(controller).build();
        Scene scene = sb.getScene();
        windowBuild = WindowBuild.getNewInstance();
        String logo = "Images/logo.jpeg";
        windowBuild.withLogo(logo).modal();
        windowBuild.withTitle(nombre).withScene(scene).build();
        windowBuild.show();
    }

    /**
     * Crea una ventana Modal que al abrirse, pausa la ventana principal hasta que 
     * el modal se cierre
     * @param controller Controlador de la Scene
     * @param nombre Nombre de la ruta de la Scene
     */
    public void createModalWithWait(Controller controller, String nombre){
        URL ruta = ViewsPath.getInstance().getViewsPath().get(nombre);
        SceneBuilder sb = new SceneBuilder();
        sb.withPath(ruta).withController(controller).build();
        Scene scene = sb.getScene();
        windowBuild = WindowBuild.getNewInstance();
        String logo = "Images/logo.jpeg";
        windowBuild.withLogo(logo).modal();
        windowBuild.withTitle(nombre).withScene(scene).build();
        windowBuild.showAndWait();
    }

    public void close(){
        this.windowBuild.close();
    }

}
