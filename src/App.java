import java.net.URL;

import Helpers.ViewsPath;
import Helpers.Facades.View;
import Helpers.ViewCreator.SceneBuilder;
import Helpers.ViewCreator.WindowBuild;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        //System.out.println(HelperENCRYPT.Encriptar("1234"));
        //Conexion.getInstance().getConexion();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //Para el buen funcionamiento de la ventana principal, la agregamos al creador de ventanas
        WindowBuild.getInstance().withStage(primaryStage);
        //login(primaryStage);
        
        //View.getInstance().createModal(controller, "Agendas");
        View.getInstance().create("Kangu");
        
        /* String logo = "Images/logo.jpeg";
        String title = "Profesional";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        WindowBuild windowBuild = WindowBuild.getInstance();
        
        Scene scene = new SceneBuilder().withPath(ruta).withController(controller).build();
        windowBuild.withStage(primaryStage).withTitle(title).withLogo(logo).withScene(scene).build();
        windowBuild.show(); */
    }

    public void login(Stage primaryStage){
        URL ruta = ViewsPath.getInstance().getViewsPath().get("Login");
        String logo = "Images/logo.jpeg";
        String title = "Login";
        WindowBuild windowBuild = WindowBuild.getInstance();
        SceneBuilder sb = new SceneBuilder().withPath(ruta);
        sb.build();
        Scene scene = sb.getScene();
        windowBuild.withStage(primaryStage).withTitle(title).withLogo(logo).withScene(scene).build();
        windowBuild.show();
    }
        

}
