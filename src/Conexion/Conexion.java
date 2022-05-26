/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Conexion;

import java.io.FileInputStream;
import java.io.IOException;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.sql.Connection;          //Aqui obtenemos el metodo conectar
import java.sql.DriverManager;       //Aqui obtenemos el manejo del driver de java a mysql
import java.sql.PreparedStatement;   //Aqui obtenemos una sintaxis facil de crear sentencias sql
import java.sql.SQLException;        //Aqui obtenemos los metodo para manejo de excepciones
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import Helpers.HelperENCRYPT;

/**
 *
 * @author Jimmy Vasquez
 */
//Dentro del constructor vamos a crear la conexion usando un try-catch
public class Conexion {

    private Connection conection = null;
    private String servidor = "";
    private String database = "";
    private String usuario = "";
    private String password = "";
    private String url = "";
    private static final Conexion singleton = new Conexion();

    // public Conexion(String servidor, String database, String usuario, String password) {
    //     this.servidor = servidor;
    //     this.database = database;
    //     this.usuario = usuario;
    //     this.password = password;
    //     CrearConexion();
    // }

    private Conexion() {
        Properties propiedades = new Properties();
        try {
            propiedades.load(new FileInputStream("src/Properties/DatosConexion.properties"));
            this.servidor = HelperENCRYPT.Desencriptar(propiedades.getProperty("server"));
            this.database = HelperENCRYPT.Desencriptar(propiedades.getProperty("db"));
            this.usuario = HelperENCRYPT.Desencriptar(propiedades.getProperty("user"));
            this.password = HelperENCRYPT.Desencriptar(propiedades.getProperty("password"));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        CrearConexion();
    }

    public static Conexion getInstance(){
        return singleton;
    }

    public void CrearConexion() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
            url = "jdbc:mysql://" + servidor + "/" + database + "?useSSL=false";//Grego linea para advertencia "Establishing SSL connection without server's identity verification is not recommended. "
            try {
                conection = DriverManager.getConnection(url, usuario, password);
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Conexion a Base de Datos " + url + " . . . . .Ok");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConexion() {
        if (conection == null) {
            CrearConexion();
        }
        return conection;
    }

    public Connection cerrarConexion() {
        try {
            conection.close();
            System.out.println("Cerrando conexion a " + url + " . . . . . Ok");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        conection = null;
        return conection;
    }
}
