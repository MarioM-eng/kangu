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
    private final URL  AGENDAS = getClass().getResource("/Views/appointments.fxml");
    private final URL  SCHEDULE = getClass().getResource("/Views/schedule_module/schedule.fxml");
    private final URL  SCHEDULES = getClass().getResource("/Views/schedule_module/display_schedule.fxml");
    private final URL  DATING_NOTIFICATION = getClass().getResource("/Views/dating_notifications.fxml");
    private final URL  USER = getClass().getResource("/Views/user.fxml");
    private final URL  RESPONSABILITY = getClass().getResource("/Views/responsability.fxml");
    private final URL  PROFILEUSER = getClass().getResource("/Views/profile_user.fxml");
    private final URL  PRUEBA = getClass().getResource("/Views/prueba.fxml");
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
        VIEWS.put("Kangu", SCENE);
        VIEWS.put("Paciente", PATIENT);
        VIEWS.put("login", LOGIN);
        VIEWS.put("Acudiente", ACUDIENTE);
        VIEWS.put("Agendas", AGENDAS);
        VIEWS.put("Horario", SCHEDULE);
        VIEWS.put("Horarios", SCHEDULES);
        VIEWS.put("notificacion_cita", DATING_NOTIFICATION);
        VIEWS.put("Usuario", USER);
        VIEWS.put("Responsabilidad", RESPONSABILITY);
        VIEWS.put("Perfiles", PROFILEUSER);
        VIEWS.put("prueba", PRUEBA);
    }

    public Map<String,URL> getViewsPath(){
        return VIEWS;
    }

}
