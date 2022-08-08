package Helpers.ViewCreator;

public interface IWindowBuilder {

    public void show();
    /**
     * Este método muestra la ventana, y hasta que no se cierre esta ventana secundaría, 
     * no habrá cambios en la principal
     */
    public void showAndWait();
    /**
     * Este método hace que el stage o ventana no deje colocar la ventana princpal 
     * hasta que esta se cierre
     */
    public void modal();
    /**
     * Método para setear el objeto que se mantendrá al crear el stage
     */
    public void setDataUser(Object object);
    /**
     * Método para obtener el objeto que se mantiene al crear el stage
     */
    public Object getDataUser();
    public void close();
    
}
