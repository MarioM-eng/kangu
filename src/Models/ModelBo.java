package Models;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public abstract class ModelBo<T> {
    
    private ObservableList<T> elements;

    public ModelBo(){
        if(this.elements == null){
            this.elements = FXCollections.observableArrayList();
        }
    }
    /**
     * Obtiene todos los elementos
     * @return
     */
    public ObservableList<T> getElements(){
        return this.elements;
    }

    /**
     * Agrega un elemento que no esté ya en la lista
     * @param element elemento que se quiere agregar
     * @return true si se agregó y false si no
     */
    protected boolean addElement(T element) {
        if(!getElements().isEmpty()){
            if(!getElements().contains(element)){
                getElements().add(element);
                return true;
            }
        }else{
            getElements().add(element);
            return true;
        }
        return false;
    }

    /**
     * Actualiza un elemento de la lista
     * @param element
     * @return <code>true</code> si fue actualizda la lista y <code>false</code> si no
     */
    protected boolean updateElement(T element) {
        if(!getElements().isEmpty()){
            Iterator<T> iterator = getElements().iterator();
            while(iterator.hasNext()){
                T elementVo = (T) iterator.next();
                if(((Model) elementVo).getId() == ((Model) element).getId()){
                    deleteElement(elementVo);
                    addElement(element);
                    break;
                }
            }
        }
        return false;
    }

    /**
     * Elimina un elemento
     * @param element
     */
    protected boolean deleteElement(T element) {
        if(isFound(element)){
            getElements().remove(element);
            return true;
        }
        return false;
    }

    /**
     * Setea los elementos
     * @param elements
     */
    protected void setElemnt(ObservableList<T> elements) {
        this.elements.removeAll(getElements());
        this.elements.addAll(elements);
    }

    /***
     * Actualiza la lista almacenada en memoria directamente de la bd
     */
    protected boolean updateList(){
        List<T> list = all();
        if(!getElements().isEmpty()){
            if(getElements().containsAll(list)){
                return false;
            }else{
                getElements().removeAll(getElements());
                getElements().addAll(list);
                return true;
            }
        }else{
            getElements().addAll(list);
            return true;
        }
    }

    /**
     * Encuentra un elemento en la lista de elementos almacenada y actualizada en memoria
     * @param id
     * @return el elemento
     */
    public T findThroughList(int id){
        for (T t : getElements()) {
            if(((Model) t).getId() == id){
                return t;
            }
        }
        System.out.println("Elemento no encontrado");
        return null;
    }

    /**
     * Encuentra un elemento en la lista de elementos almacenada y actualizada en memoria
     * @param dni
     * @return
     */
    public T findThroughListFor(Model element){
        return (getElements().contains(element)) ? getElements().get(getElements().indexOf(element)): null;
        
    }


    /**
     * Elimina un elemento
     * @param element
     */
    protected boolean isFound(T element) {
        if(!getElements().isEmpty()){
            if(getElements().contains(element)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * Trae todos los elementos de la DB
     * @return Una lista de elementos
     */
    protected abstract List<T> all();

}
