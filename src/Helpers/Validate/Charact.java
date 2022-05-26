package Helpers.Validate;

import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Charact implements IValidate {

    public Charact() {}

    /**
     * Solo acepta números en un TextField
     */
    public void numeros(FieldValidation field){
        
        EventHandler<KeyEvent> manejadorTeclado = (KeyEvent event)->{
            char key = event.getCharacter().charAt(0);
            if (!Character.isDigit(key)){
                if(field.getLb() != null){
                    field.getLb().setText("Solo se admiten números");
                }
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
    public void letras(FieldValidation field){
        EventHandler<KeyEvent> manejadorTeclado = (KeyEvent event)->{
            char key = event.getCharacter().charAt(0);
            if (!Character.isLetter(key)){
                if(!event.getCharacter().equals(" ")){
                    if(field.getLb() != null){
                        field.getLb().setText("Solo se admiten letras");
                    }
                    event.consume();
                }
            }
        };
        field.getTF().addEventHandler(KeyEvent.KEY_TYPED, manejadorTeclado);
    }

    @Override
    public boolean isEmpty(FieldValidation field) {
        return field.getTF() == null;
    }
    
}
