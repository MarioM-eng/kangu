package Models.Relationships;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import Conexion.Conexion;
import Helpers.ClassHandler;
import Models.Model;
import Models.ModelBo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ManyToManyBo<T,U,V,W> extends ModelBo<ManyToMany<T,U>> {

    private T t;//Este es el modelo values object T
    private U u;//Este es el modelo values object U
    private V v;//Este es el modelo bussines object V de T
    private W w;//Este es el modelo bussines object W de U

    

    public ManyToManyBo(T t, U u, V v, W w) {
        super();
        this.t = t;
        this.u = u;
        this.v = v;
        this.w = w;
        updateList();
    }

    @Override
    protected List<ManyToMany<T, U>> all() {
        
        List<ManyToMany<T, U>> lista = new ArrayList<>();
        
        String query = "CALL all_" + ClassHandler.classNameForRelationsToStringLower(t, u, "_") + "()";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            ResultSet resultados = callable.executeQuery(query);
            ManyToMany<T,U> relation;
            while(resultados.next()){
                relation = new ManyToMany<>();
                relation.setElement_1(((ModelBo<T>) v).findThroughList(resultados.getInt(ClassHandler.classNameToStringLower(t, "_id"))));
                relation.setElement_2(((ModelBo<U>) w).findThroughList(resultados.getInt(ClassHandler.classNameToStringLower(u, "_id"))));
                lista.add(relation);
            }
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al traer elemento de la base de datos: " + e.getMessage());
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al traer elemento de la base de datos: " + e.getMessage());
        } finally{
            if(callable != null){
                try {
                    callable.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return lista;
        
    }
    
    public boolean create(ManyToMany<T, U> relation)
    {
        String query = "CALL add_" + ClassHandler.classNameForRelationsToStringLower(t, u, "_") + "(?,?)";
        CallableStatement callable = null;

        try (Connection db = Conexion.getNewInstance().getConexion()){
            callable = db.prepareCall(query);
            callable.setInt(1, ((Model) relation.getElement_1()).getId());
            callable.setInt(2, ((Model) relation.getElement_2()).getId());
            
            if(callable.executeUpdate() != -1){
                if(addElement(relation)){
                    System.out.println(relation.getElement_2() +" fue agregado a "+ relation.getElement_1());
                }else{
                    System.out.println("No fue posible agregar el elemento a la lista");
                }
                return true;
            }else{
                System.out.println("No fue posible registar " + relation.getElement_2() + " para " + relation.getElement_1() + " a la base de datos");
                return false;
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al intentar agregar elemento a la base de datos: " + e.getMessage());
            return false;
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al intentar agregar elemento a la base de datos: " + e.getMessage());
        } finally{
            if(callable != null){
                try {
                    callable.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public boolean delete(ManyToMany<T, U> relation){
        //Se define la consulta que vamos a realizar
        String query = "CALL delete_" + ClassHandler.classNameForRelationsToStringLower(t, u, "_") + "(?,?)";
        //La interfaz CallableStatement permite la utilización de sentencias SQL para llamar a procedimientos almacenados
        CallableStatement callable = null;
        //Dentro de un try-catch creamos la conexión
        try (Connection db = Conexion.getNewInstance().getConexion()){
            //Creamos el objeto tipo CallableStatement para llamar al procedimiento almacenado
            callable = db.prepareCall(query);
            //Setea los parametros designados en los ?
            callable.setInt(1, ((Model) relation.getElement_1()).getId());
            callable.setInt(2, ((Model) relation.getElement_2()).getId());

            if(callable.executeUpdate() != -1){
                if(deleteElement(relation)){
                    System.out.println(relation.getElement_2() +" eliminado de "+ relation.getElement_1());
                }else{
                    System.out.println("No fue posible eliminar el elemento a la lista");
                }
                return true;
            }else{
                System.out.println("No fue posible eliminar elemento de la base de datos");
            }
            
        } catch (SQLException e) {
            //TODO: handle exception
            System.err.println("Error al eliminar " + relation.getElement_2() +" de "+ relation.getElement_1() + " " + e.getMessage());
            return false;
        }catch (Exception e) {
            //TODO: handle exception
            System.err.println("Error al eliminar " + relation.getElement_2() +" de "+ relation.getElement_1() + " " + e.getMessage());
            return false;
        } finally{
            if(callable != null){
                try {
                    callable.close();
                } catch (SQLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return false;
    }
    /**
     * Crea una lista con los elementos relacionados con el objeto
     * @param object Objeto del cual se quiere buscar sus relaciones
     * @return ObservableList
     */
    public ObservableList makeAObject(Object object){
        
        ListIterator<ManyToMany<T,U>> listIterator = null;
        if(object.getClass().equals(t.getClass())){
            ObservableList<U> observableList = FXCollections.observableArrayList();
            listIterator = getElements().filtered(el->el.getElement_1().equals(object)).listIterator();
            while (listIterator.hasNext()) {
                observableList.add(listIterator.next().getElement_2());
            }
            return observableList;
        }else if(object.getClass().equals(u.getClass())){
            ObservableList<T> observableList = FXCollections.observableArrayList();
            listIterator = getElements().filtered(el->el.getElement_2().equals(object)).listIterator();
            while (listIterator.hasNext()) {
                observableList.add(listIterator.next().getElement_1());
            }
            return observableList;
        }
        return null;
    }
}
