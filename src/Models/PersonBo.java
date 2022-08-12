package Models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class PersonBo<T> extends ModelBo<T> {

    public PersonBo() {
        super();
        //TODO Auto-generated constructor stub
    }

    /**
     * Busca a través de la lista de elementos con un caracter o cadena dada
     * @param dato 
     * @return ObservableList<Person>
     */
    public ObservableList<T> searchThroughList(String dato)
    {
        ObservableList<T> resultados = FXCollections.observableArrayList();
        String ex = ".*"+ dato +".*";
        Pattern pat = Pattern.compile(ex.toLowerCase());
        Matcher matName = null;
        Matcher matDni = null;
        for (T t : getElements()) {
            Person person = ((Person) t);
            matName = pat.matcher(person.getName().toLowerCase());
            matDni = pat.matcher(person.getDni().toLowerCase());
            if(matName.matches() && matDni.matches()){
                resultados.add(t);
            }else if(matName.matches() || matDni.matches()){
                resultados.add(t);
            }
        }
        
        return resultados;
    }

    protected boolean checkPerson(Person person){
        for (T t : getElements()) {
            if(((Person) t).getDni().equals(person.getDni())){
                return true;
            }
        }
        return false;
    }
    
}
