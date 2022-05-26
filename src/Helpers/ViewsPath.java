package Helpers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ViewsPath {
    
    private static final ViewsPath singleton = new ViewsPath();
    private final URL PRODUCTS = getClass().getResource("/Views/product.fxml");
    private final URL  CATEGORIES = getClass().getResource("/Views/category.fxml");
    private final URL SIZES = getClass().getResource("/Views/size.fxml");
    private final URL INVENTORY = getClass().getResource("/Views/inventory.fxml");
    private final URL LOGIN = getClass().getResource("/Views/login.fxml");
    private final URL HOME = getClass().getResource("/Views/principal.fxml");
    //private final URL TOOLBAR = getClass().getResource("/Views/toolbar.fxml");
    private final URL USERS = getClass().getResource("/Views/usuarios.fxml");
    private final URL SALES = getClass().getResource("/Views/ventas.fxml");
    private Map<String,URL> VIEWS;

    private ViewsPath(){
        if(VIEWS == null){
            VIEWS = new HashMap<String,URL>();
        }
        fillCollection();
    }

    public static ViewsPath getInstance(){
        return singleton;
    }

    private void fillCollection(){
        VIEWS.put("Productos", PRODUCTS);
        VIEWS.put("Categorias", CATEGORIES);
        VIEWS.put("Tamaños", SIZES);
        VIEWS.put("Inventario", INVENTORY);
        VIEWS.put("Ventas", SALES);
        VIEWS.put("Usuarios", USERS);
        VIEWS.put("Inicio", HOME);
        VIEWS.put("Login", LOGIN);
    }

    public Map<String,URL> getViewsPath(){
        return VIEWS;
    }

}
