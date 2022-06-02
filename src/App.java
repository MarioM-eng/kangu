import java.net.URL;

import Conexion.Conexion;
import Helpers.HelperENCRYPT;
import Helpers.ViewsPath;
import Helpers.ViewCreator.WindowBuild;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application{
    public static void main(String[] args) throws Exception {
        //System.out.println(HelperENCRYPT.Encriptar("1234"));
        //Conexion.getInstance().getConexion();
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //login(primaryStage);
        URL ruta = ViewsPath.getInstance().getViewsPath().get("mPacientes");
        String logo = "Images/logo.jpeg";
        String title = "Pacientes";
        WindowBuild windowBuild = WindowBuild.getInstance();
        windowBuild.withStage(primaryStage).withUrl(ruta).withTitle(title).withLogo(logo).build();
        windowBuild.show();
    }

    public void login(Stage primaryStage){
        URL ruta = ViewsPath.getInstance().getViewsPath().get("Login");
        String logo = "Images/logo.jpeg";
        String title = "Login";
        WindowBuild windowBuild = WindowBuild.getInstance();
        windowBuild.withStage(primaryStage).withUrl(ruta).withTitle(title).withLogo(logo).build();
        windowBuild.show();
    }
        

}
