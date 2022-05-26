package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import Helpers.HelperENCRYPT;
import Helpers.ViewsPath;
import Helpers.ViewCreator.WindowBuild;
//import Models.UserBo;
//import Models.UserVo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Auth implements Initializable{

    //public static UserVo usuarioCurrent;
    public static boolean login = false;
    
    @FXML
    private TextField tUsuario;

    @FXML
    private PasswordField tClave;

    @FXML
    private Button bIngresar;

    @FXML
    void bIngresarClick(ActionEvent event){
        login();
    }

    /**Retorna el usuario con la sesión activa */
    /*public static UserVo getUsuario(){
        return Auth.usuarioCurrent;
    }*/

    /**Da valor al usuario con la sesión activa */
    /*private void setUsuario(UserVo usuario){
        Auth.usuarioCurrent = usuario;
    }*/

    public boolean login(){

        boolean dni = false;

        /*for (UserVo userVo : UserBo.getInstance().getUsers()) {
            if(userVo.getDni().equals(tUsuario.getText())){
                dni = true;
                String clave = HelperENCRYPT.Encriptar(tClave.getText());//Se encripta la contraseña
                if(userVo.getClave().equals(clave)){
                    //System.out.println("Entraste");
                    setUsuario(userVo);//
                    //Código para abrir una ventana y cerrar la anterior en JavaFX
                    String title = "Inicio";
                    URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
                    WindowBuild.getInstance().withTitle(title).withUrl(ruta).build();

                    return login = true;
                }
            }
        }*/

        if(dni){
            
            System.out.println("Contraseña incorrecta");
            login = false;
        }else{
            System.out.println("Usuario y contraseña incorrecta ");
            login = false;
        }  
        return login;
    }

    public static boolean logout(){
        String title = "Login";
        URL ruta = ViewsPath.getInstance().getViewsPath().get(title);
        WindowBuild.getInstance().withTitle(title).withUrl(ruta).build();
        login = false;
        return login;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // TODO Auto-generated method stub
        tUsuario.setText("Mario_kangu");
        tClave.setText("1234");
    }

    
    
}
