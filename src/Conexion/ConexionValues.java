package Conexion;

import java.sql.Connection;

public class ConexionValues {

    private Connection conexion;
    private String servidor;
    private String database;
    private String usuario;
    private String password;
    private String url;

    public ConexionValues(Connection conexion, String servidor, String database, String usuario, String password,
            String url) {
        this.conexion = conexion;
        this.servidor = servidor;
        this.database = database;
        this.usuario = usuario;
        this.password = password;
        this.url = url;
    }

    public ConexionValues(String servidor, String database, String usuario, String password) {
        this.conexion = conexion;
        this.servidor = servidor;
        this.database = database;
        this.usuario = usuario;
        this.password = password;
        this.url = url = "jdbc:mysql://" + servidor + "/" + database + "?useSSL=false";//Agrego linea para advertencia "Establishing SSL connection without server's identity verification is not recommended.
    }

    public ConexionValues() {
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public String getServidor() {
        return servidor;
    }

    public void setServidor(String servidor) {
        this.servidor = servidor;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    

    
    
}
