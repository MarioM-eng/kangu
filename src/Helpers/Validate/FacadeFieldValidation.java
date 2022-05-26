package Helpers.Validate;

public class FacadeFieldValidation {
    
    private FieldValidation fieldV;

    public FacadeFieldValidation(FieldValidation fieldV){
        this.fieldV = fieldV;
    }

    public void validateNum(int max, int min){
        Charact charact = new Charact();
        Range range = new Range();
        charact.numeros(fieldV);
        range.range(fieldV, max, min);
    }

    public void validateLet(int max, int min){
        Charact charact = new Charact();
        Range range = new Range();
        charact.letras(fieldV);
        range.range(fieldV, max, min);
    }





}
