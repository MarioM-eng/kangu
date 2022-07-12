package Helpers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ViewsPath {
    
    private static final ViewsPath singleton = new ViewsPath();
    private final URL SCENE = getClass().getResource("/Views/window.fxml");
    private final URL  PATIENT = getClass().getResource("/Views/patient.fxml");
    private final URL  LOGIN = getClass().getResource("/Views/login.fxml");
    private final URL  ACUDIENTE = getClass().getResource("/Views/responsible.fxml");
    private final URL  AGENDA = getClass().getResource("/Views/appointment2.fxml");
    private final URL  SCHEDULE = getClass().getResource("/Views/schedule.fxml");
    private final URL  PROFESSIONAL = getClass().getResource("/Views/professional.fxml");
    private final URL  INTERVAL = getClass().getResource("/Views/interval.fxml");
    private final URL  USER = getClass().getResource("/Views/user.fxml");
    private final URL  HOME = getClass().getResource("/Views/home.fxml");
    private final URL  RESPONSABILITY = getClass().getResource("/Views/responsability.fxml");
    private final URL  PROFILE = getClass().getResource("/Views/profile.fxml");
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
        VIEWS.put("Ventana", SCENE);
        VIEWS.put("Paciente", PATIENT);
        VIEWS.put("login", LOGIN);
        VIEWS.put("Acudiente", ACUDIENTE);
        VIEWS.put("Agenda", AGENDA);
        VIEWS.put("Horario", SCHEDULE);
        VIEWS.put("Profesional", PROFESSIONAL);
        VIEWS.put("Intervalo", INTERVAL);
        VIEWS.put("Principal", HOME);
        VIEWS.put("Usuario", USER);
        VIEWS.put("Responsabilidad", RESPONSABILITY);
        VIEWS.put("Perfiles", PROFILE);
    }

    public Map<String,URL> getViewsPath(){
        return VIEWS;
    }

}
