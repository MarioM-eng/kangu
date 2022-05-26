package Helpers;

import java.io.IOException;
import java.net.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

public class TabLoader{

    private static TabLoader singleton = new TabLoader();

    private TabLoader(){};

    public static TabLoader getInstance(){
        return singleton;
    }

    private boolean isEmpty(Tab tab){
        //Si el Tab ya tiene contenido, terminar el método
        if(tab.getContent() == null){
            return false;
        }
        return true;
    }

    private Parent getRoot(URL ruta){
        FXMLLoader loaderMonitor = new FXMLLoader();
        Parent root;
        loaderMonitor.setLocation(ruta);
        try {
            root = loaderMonitor.load();
            return root;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Carga un Tab
     * @param ruta
     * @param tab
     * @return
     */
    public void loadTab(URL ruta, Tab tab){
        if(!isEmpty(tab)){
            Parent root = getRoot(ruta);
            tab.setContent(root);
        }
    }
    
    /**
     * Verifica que la ruta del tab exista y lo carga
     * @param tab
     */
    public void tabLoader(Tab tab){

        if(ViewsPath.getInstance().getViewsPath().containsKey(tab.getText())){
            loadTab(ViewsPath.getInstance().getViewsPath().get(tab.getText()), tab);
        }else{
            System.err.print("Ruta de tab no encontrada");
        }

    }

}
