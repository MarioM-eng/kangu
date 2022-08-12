package Helpers;

public class ClassHandler {

    public static String classNameToStringLower(Object c){
        String name = c.getClass().getSimpleName();
        name = name.substring(0, name.length() - 2);
        name = name.toLowerCase();
        if(name.contains(".")){
            name = name.substring(name.indexOf(".")+1);
        }
        return name;
    }

    public static String classNameToStringLower(Object c,String concat){
        String name = c.getClass().getSimpleName();
        name = name.substring(0, name.length() - 2);
        name = name.toLowerCase();
        if(name.contains(".")){
            name = name.substring(name.indexOf(".")+1);
        }
        name = name.concat(concat);
        return name;
    }

    public static String classNameToString(Object c){
        String name = c.getClass().getSimpleName();
        name = name.substring(0, name.length() - 2);
        return name;
    }

    public static String classNameToString(Object c,String concat){
        String name = c.getClass().getSimpleName();
        name = name.substring(0, name.length() - 2);
        name = name.concat(concat);
        return name;
    }

    public static String classNameForRelationsToStringLower(Object c1,Object c2,String separator){
        String name = null;
        String name1 = classNameToStringLower(c1);
        String name2 = classNameToStringLower(c2);
        if(name1.compareToIgnoreCase(name2) < 0){
            name = name1.concat(separator).concat(name2);
        }else{
            name = name2.concat(separator).concat(name1);
        }
        return name;
    }

    public static String elementNameToString(Class c){
        String name = c.getClass().getName();
        name = name.substring(0, name.length()-2);
        return name;
    }

    public static Class classNameToClass(Class c,String concat){

        try {
            return Class.forName(classNameToString(c,concat));
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            System.out.println("No se encuentra clase " + e.getMessage());
        }
        return null;
    }
    
}
