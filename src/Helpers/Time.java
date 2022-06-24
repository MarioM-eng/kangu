package Helpers;

import java.time.DayOfWeek;

public class Time {

    public static String[] SEMANA = {"Lunes","Martes","Miércoles","Jueves","Viernes","Una vez","Siempre"};
    
    public static String[] INTERVALOS = {"Minutos", "Horas"};

    public static int[] HORAS = horas();
    public static int[] MINUTOS = minutos();
    public static int DURACION_MINIMA = 40;
    public static int DURACION_MAXIMA = 60;
    public static int[] DURACION = {DURACION_MINIMA, DURACION_MAXIMA};

    public static int dayNumberOfWeek(String nombre){
        int resultado = 0;
        if(nombre.equals("Lunes")){
            resultado = DayOfWeek.MONDAY.getValue();
        }
        if(nombre.equals("Martes")){
            resultado = DayOfWeek.TUESDAY.getValue();
        }
        if(nombre.equals("Miércoles")){
            resultado = DayOfWeek.WEDNESDAY.getValue();
        }
        if(nombre.equals("Jueves")){
            resultado = DayOfWeek.THURSDAY.getValue();
        }
        if(nombre.equals("Viernes")){
            resultado = DayOfWeek.FRIDAY.getValue();
        }
        if(nombre.equals("Una vez")){
            resultado = 8;
        }
        if(nombre.equals("Siempre")){
            resultado = 9;
        }

        return resultado;
    }

    private static int[] minutos(){
        int[] resultado = new int[6];
        int contador = 0;
        for (int i = 0; i < resultado.length; i ++) {
            resultado[i] = contador;
            contador += 10;
        }
        return resultado;
    }

    private static int[] horas(){
        int[] resultado = new int[24];
        for (int i = 0; i < resultado.length; i++) {
            resultado[i] = i;
        }
        return resultado;
    }
}
