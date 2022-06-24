import java.net.URL;
import java.sql.Date;
import java.time.DayOfWeek;
import java.time.LocalDate;

import Conexion.Conexion;
import Controllers.ScheduleController;
import Helpers.HelperENCRYPT;
import Helpers.ViewsPath;
import Helpers.ViewCreator.SceneBuilder;
import Helpers.ViewCreator.WindowBuild;
import Models.PatientBo;
import Models.PatientVo;
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
        //login(primaryStage);
        
        String logo = "Images/logo.jpeg";
        String title = "Agenda";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        WindowBuild windowBuild = WindowBuild.getInstance();
        ScheduleController controller = new ScheduleController(PatientBo.getInstance().findThroughList(8));
        Scene scene = new SceneBuilder().withPath(ruta).withController(controller).build();
        windowBuild.withStage(primaryStage).withTitle(title).withLogo(logo).withScene(scene).build();
        windowBuild.show();
    }

    public void login(Stage primaryStage){
        URL ruta = ViewsPath.getInstance().getViewsPath().get("Login");
        String logo = "Images/logo.jpeg";
        String title = "Login";
        WindowBuild windowBuild = WindowBuild.getInstance();
        Scene scene = new SceneBuilder().withPath(ruta).build();
        windowBuild.withStage(primaryStage).withTitle(title).withLogo(logo).withScene(scene).build();
        windowBuild.show();
    }
        

}
