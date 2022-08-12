package Helpers;

public class MethodHandler {

    public static final String GET = "get";
    public static final String SET = "set";

    public static String methodNameToString(String attributeName, String getterOrSetter){
        if(attributeName.contains("_")){
            String[] arr = attributeName.split("_");
            attributeName = "";
            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr[i].replace(arr[i].substring(0,1), arr[i].substring(0,1).toUpperCase());
                
                attributeName = attributeName.concat(arr[i]);
            }
        }else{
            attributeName = attributeName.replace(attributeName.substring(0,1), attributeName.substring(0,1).toUpperCase());
        }
        attributeName = getterOrSetter.concat(attributeName);
        return attributeName;
    }

    public static String attributeNameToString(String methodName){
        methodName = methodName.substring(3);
        String name = "";
        for (int i = 0; i < methodName.toCharArray().length; i++) {
            if(i == 0){
                name = methodName.substring(0,i+1).toLowerCase();
            }else{
                if(Character.isUpperCase(methodName.charAt(i))){
                    name = "_".concat(methodName.substring(0,i+1));
                }else{
                    name = methodName.substring(0,i+1);
                }
            }
        }
        return name;
    }
    
}
