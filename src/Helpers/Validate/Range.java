package Helpers.Validate;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class Range implements IStyle {

    /**
     * Valida dependiendo del número de caracteres a recibir el vampo de texto
     * @param tf    la instancia del TextField
     * @param lb    la instancia de Label
     * @param max Entero de número de caracteres máximo
     * @param min Entero de número de caracteres mínimo
     */
    public void range(FieldValidation field, int max, int min){
        /*IValidate iValidate = (f)->{
            return f.getTF() == null;
        };*/
        EventHandler<KeyEvent> manejadorTeclado = (KeyEvent event)->{
            //if(!iValidate.isEmpty(field)){
                ObservableList<String> styleClass = field.getTF().getStyleClass();
                ObservableList<String> lbStyleClass = field.getLb().getStyleClass();
                int characterNumber = field.getTF().getText().length();
                if(characterNumber > max || characterNumber < min){
                    field.getLb().setText("Solo se aceptan de " + 
                                            min + " a " + max +" carácteres");
                    error(styleClass, lbStyleClass);

                }else{
                    good(styleClass, lbStyleClass);
                    field.getLb().setText("Muy bien");
                }
            //}
        };
        
        field.getTF().addEventHandler(KeyEvent.KEY_RELEASED, manejadorTeclado);
    }

    private void errorLb(ObservableList<String> styleClass)
    {
        if(!styleClass.contains(LABEL_RED)) {
            if(styleClass.contains(LABEL_GREEN)){
                styleClass.remove(LABEL_GREEN);
                styleClass.add(LABEL_RED);
            }else{
                styleClass.add(LABEL_RED);
            }
        }
    }

    private void exLb(ObservableList<String> styleClass)
    {
        if(!styleClass.contains(LABEL_GREEN)) {
            if(styleClass.contains(LABEL_RED)){
                styleClass.remove(LABEL_RED);
                styleClass.add(LABEL_GREEN);
            }else{
                styleClass.add(LABEL_GREEN);
            }
        }
    }
    
    private void errorTF(ObservableList<String> styleClass)
    {
        //Si no contiene el borde de error
        if(!styleClass.contains(BORDER_ERROR)) {
            //si contiene el borde bien
            if(styleClass.contains(BORDER_GOOD)){
                //quitar el borde bien
                styleClass.remove(BORDER_GOOD);
                //y agregar el borde de error
                styleClass.add(BORDER_ERROR);
            }else{//si no
                //simplemente agregar el borde de error
                styleClass.add(BORDER_ERROR);
            }
        }
    }

    private void exTF(ObservableList<String> styleClass)
    {
        //Si no contiene el borde de error
        if(!styleClass.contains(BORDER_ERROR)) {
            //si contiene el borde bien
            if(styleClass.contains(BORDER_GOOD)){
                //quitar el borde bien
                styleClass.remove(BORDER_GOOD);
                //y agregar el borde de error
                styleClass.add(BORDER_ERROR);
            }else{//si no
                //simplemente agregar el borde de error
                styleClass.add(BORDER_ERROR);
            }
        }
    }

    @Override
    public void error(ObservableList<String> textFieldStyleCLass, ObservableList<String> labelStyleCLass) {
        errorTF(textFieldStyleCLass);
        errorLb(labelStyleCLass);
        
    }

    @Override
    public void good(ObservableList<String> textFieldStyleCLass, ObservableList<String> labelStyleCLass) {
        exTF(textFieldStyleCLass);
        exLb(labelStyleCLass);
        
    }
    
}
