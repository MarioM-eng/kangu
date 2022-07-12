package Models.Relationships;

import javafx.collections.ObservableList;

public interface BelongsTo<T,U>{

    /**
     * Obtiene los elemento que contienen al objeto
     * @param element objeto que se busca en la lista de elementos
     * @return los elemento que contienen al objeto
     */
    public ObservableList<T> getElementsOf(U element);
    
}
