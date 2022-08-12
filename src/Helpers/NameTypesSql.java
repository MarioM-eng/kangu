package Helpers;

public class NameTypesSql {

    public static final String GET = "get";
    public static final String SET = "set";

    public static String typeToMethodNameString(String type, String getterOrSetter){
        String string = getterOrSetter;
        if(type.equals("VARCHAR") || type.equals("CHAR")){
            string = string.concat("String");
        } else if(type.equals("INTEGER")){
            string = string.concat("Int");
        }else{
            string = string.concat(type.substring(0,1));
            string = string.concat(type.substring(1).toLowerCase());
            
        }
        System.out.println(string);
        return string;
    }
    
}
