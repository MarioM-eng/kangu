package Controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class ToolbarController implements Initializable{
    @FXML
    private AnchorPane toolbar;
    @FXML
    private ImageView imgMenu;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Label LName;
    @FXML
    private Label LRole;

    @FXML
    public void MIsalirClick(ActionEvent action){
        Auth.logout();
    }

    @FXML
    public void MIMisDatosClick(ActionEvent action){
        //
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO Auto-generated method stub
        // LName.setText(Auth.getUsuario().toString());
        imgLogo.setImage(new Image("/Images/logo.jpeg"));
        // String roles = "";
        // boolean admin = false;
        // for (RolVo rolVo : Auth.getUsuario().getRoles()) {
        //     switch(rolVo.getId()){
        //         case 1:
        //             admin = true;
        //             roles = rolVo.getName() + "\n";
        //             break;
        //         case 2: 
        //             admin = true;
        //             roles = roles.concat("|" + rolVo.getName() + " ");
        //             break;
        //         case 3:
        //             roles = "|" + roles.concat("|" + rolVo.getName() + " ");
        //             break;
        //     }
        // }
        // if(admin){
        //     imgMenu.setImage(new Image("/Images/default/002-admin.png"));
        // }else{
        //     imgMenu.setImage(new Image("/src/Images/default/009-user.png"));
        // }
        // LRole.setText(roles);
    }
    
}
