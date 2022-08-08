package Helpers.Validate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public abstract class Charact{

    private static final Pattern PASSWORDREGEX = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");
    private static final Pattern NUMBERREGEX = Pattern.compile("^(?=.*[0-9]).{1}$");
    private static final Pattern LETTERREGEX = Pattern.compile("^(?=.*[A-Za-z]).{1}$");
    private static final Pattern LETTERWITHSPACEREGEX = Pattern.compile("^(?=.*[A-Za-z\\s]).{1}$");

    /**
     * Solo acepta números en un TextField
     */
    public static void numeros(FieldValidation field){
        EventHandler<KeyEvent> manejadorTeclado = (KeyEvent event)->{
            Matcher mat = NUMBERREGEX.matcher(event.getCharacter());
            if(!mat.matches()){
                event.consume();
            }
        };
        field.getTF().addEventHandler(KeyEvent.KEY_TYPED, manejadorTeclado);
    }

    /**
     * Solo acepta letras en un TextField
     * @param tf TextField a escuchar
     * @param lb Muestra mensaje de advertencia
     */
    public static void letterWithSpace(FieldValidation field){
        EventHandler<KeyEvent> manejadorTeclado = (KeyEvent event)->{
            Matcher mat = LETTERWITHSPACEREGEX.matcher(event.getCharacter());
            if(!mat.matches()){
                event.consume();
            }
        };
        field.getTF().addEventHandler(KeyEvent.KEY_TYPED, manejadorTeclado);
    }

    /**
     * Solo acepta letras en un TextField
     * @param tf TextField a escuchar
     * @param lb Muestra mensaje de advertencia
     */
    public static void letter(FieldValidation field){
        EventHandler<KeyEvent> manejadorTeclado = (KeyEvent event)->{
            Matcher mat = LETTERREGEX.matcher(event.getCharacter());
            if(!mat.matches()){
                event.consume();
            }
        };
        field.getTF().addEventHandler(KeyEvent.KEY_TYPED, manejadorTeclado);
    }

    public static boolean isEmpty(FieldValidation field){
        if(field.getTF().getText().isEmpty()){
            field.getLb().setText("No debe estar vacio");
            field.getTF().setOnKeyPressed(
                event->{
                    field.getLb().setText("");
                }
            );
            return true;
        }
        return false;
    }

    public static boolean password(FieldValidation field){
        Matcher mat = PASSWORDREGEX.matcher(field.getTF().getText());
        String s = "Debe contener al menos un dígito\n"
        + "Debe contener al menos una letra mayúscula\n"
        + "No se permiten espacios en blanco en toda la cadena\n"
        + "Debe contener mínimo 8 caracteres";
            if(!mat.matches()){
                field.getLb().setText(s);
                field.getTF().setOnKeyTyped(
                    event->{
                        if(field.getTF().getText().length() >= 8){
                            field.getLb().setText("");
                        }
                    }
                );
                return false;
            }
        return true;
    }
    
}
