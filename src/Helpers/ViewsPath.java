package Helpers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ViewsPath {
    
    private static final ViewsPath singleton = new ViewsPath();
    private final URL PATIENTMENU = getClass().getResource("/Views/patient_menu.fxml");
    private final URL  PATIENT = getClass().getResource("/Views/patient.fxml");
    private final URL  LOGIN = getClass().getResource("/Views/login.fxml");
    private final URL  ACUDIENTE = getClass().getResource("/Views/responsible.fxml");
    //private final URL TOOLBAR = getClass().getResource("/Views/toolbar.fxml");
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
        VIEWS.put("mPacientes", PATIENTMENU);
        VIEWS.put("Paciente", PATIENT);
        VIEWS.put("login", LOGIN);
        VIEWS.put("Acudiente", ACUDIENTE);
    }

    public Map<String,URL> getViewsPath(){
        return VIEWS;
    }

}
