package Models.Relationships;

import javafx.collections.ObservableList;

public interface HasMany<T>{

    /**
     * Retorna una lista de elementos que contienen el mismo objeto
     * @param object Objeto que se quiere buscar
     * @param c Clase del objeto que se quiere buscar
     * @return ObservableList<T>
     */
    public ObservableList<T> getElementsOf(Object object);
    
}
