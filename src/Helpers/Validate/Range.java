package Helpers.Validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.EventHandler;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyEvent;

public class Range {

    /**
     * Valida dependiendo del número de caracteres a recibir el vampo de texto
     * @param tf    la instancia del TextField
     * @param lb    la instancia de Label
     * @param max Entero de número de caracteres máximo
     * @param min Entero de número de caracteres mínimo
     */
    public static void max(TextInputControl field, int max){
        EventHandler<KeyEvent> manejadorTeclado = (KeyEvent event)->{
                int characterNumber = field.getText().length();
                if(characterNumber > max-1){
                    event.consume();
                }
        };
        
        field.addEventHandler(KeyEvent.KEY_TYPED, manejadorTeclado);
    }

    public static boolean min(FieldValidation field, int min){
        Pattern mini = Pattern.compile("^().{" + min + ",}$");
        Matcher mat = mini.matcher(field.getTF().getText());
            if(!mat.matches()){
                field.getLb().setText("Mínimo " + min + " caracteres");
                field.getTF().setOnKeyTyped(
                    event->{
                        if(field.getTF().getText().length() >= min){
                            field.getLb().setText("");
                        }
                    }
                );
                return false;
            }
        return true;
    }
    
}
