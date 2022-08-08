package Helpers.Alert;

import java.util.Optional;

import Helpers.Facades.IAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.stage.StageStyle;

public class AlertImplement implements IAlert{

    private Alert alert;
    private boolean response;

    public AlertImplement(){
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.initStyle(StageStyle.UTILITY);
        response = false;
    }

    @Override
    public void alert(String text, int type) {
        
        switch (type) {
            case IAlert.SIMPLE:
                alert.setAlertType(Alert.AlertType.INFORMATION);
                alert.setTitle("Aviso");
                alert.setContentText(text);
                alert.showAndWait();
                break;
            
            case IAlert.WARNING:
                alert.setAlertType(Alert.AlertType.WARNING);
                alert.setTitle("Advertencia");
                alert.setContentText(text);
                java.awt.Toolkit.getDefaultToolkit().beep();
                alert.showAndWait();
                break;

            case IAlert.ERROR:
                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText(text);
                java.awt.Toolkit.getDefaultToolkit().beep();
                alert.showAndWait();
                break;

            case IAlert.CONFIRMATION:
                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmación");
                alert.setContentText(text);
                System.out.println(Thread.currentThread().getName());
                Optional<ButtonType> result = alert.showAndWait();
                if(result.get()==ButtonType.OK){
                    response = true;
                }else{
                    response = false;
                }
                break;
        
            case IAlert.TEXT_INPUT:
                alert.setAlertType(Alert.AlertType.INFORMATION);
                TextInputDialog textI = new TextInputDialog();
                alert.setTitle("Ingresar");
                alert.setContentText(text);
                Optional<String> answer = textI.showAndWait();
                answer.ifPresent((t)->System.out.println(t.toUpperCase()));
                break;
        }
        
    }

    public boolean getResponse() {
        return response;
    }

    /* private void setResponse(boolean response) {
        this.response = response;
    } */

    
    
}
