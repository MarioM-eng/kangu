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
     * @param producto
     * @return ObservableList<ProductVo>
     */
    protected void addElement(ModelWithName element) {
        if(!getElements().isEmpty()){
            if(!getElements().contains(element)){
                getElements().add((T) element);
            }
        }
    }

    /**
     * Actualiza un elemento de la lista
     * @param detail
     * @return
     */
    protected boolean updateElement(Model element) {
        if(!getElements().isEmpty()){
            Iterator<T> iterator = getElements().iterator();
            while(iterator.hasNext()){
                Model elementVo = Model.class.cast(iterator.next());
                if(elementVo.getId() == element.getId()){
                    getElements().remove(elementVo);
                    getElements().add((T) element);
                }
            }
        }
        return false;
    }

    /**
     * Elimina un elemento
     * @param element
     */
    protected void deleteElement(Model element) {
        if(!getElements().isEmpty()){
            if(getElements().contains(element)){
                getElements().remove(element);
            }
        }
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
        List list = all();
        if(!getElements().isEmpty()){
            if(getElements().containsAll(list)){
                return false;
            }else{
                getElements().removeAll();
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
            Model model = (Model) t;
            if(model.getId() == id){
                return (T) model;
            }
        }
        System.out.println(ElementNameToString() + " no encontrado");
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

    private String ClassNameToString(Class c){
        String name = c.getName();
        name = name.toLowerCase();
        name = name.concat("s");
        return name;
    }

    private String ElementNameToString(){
        T t = null;
        String name = t.getClass().getName();
        name = name.substring(0, name.length()-2);
        return name;
    }

    /**
     * Trae todos los elementos de la DB
     * @return Una lista de elementos
     */
    protected abstract List<T> all();

}
