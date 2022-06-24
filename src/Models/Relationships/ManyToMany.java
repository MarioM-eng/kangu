package Models.Relationships;

public class ManyToMany<T,U> {

    private T element_1;
    private U element_2;

    public ManyToMany(T element_1, U element_2) {
        this.element_1 = element_1;
        this.element_2 = element_2;
    }

    public ManyToMany() {}

    public T getElement_1() {
        return element_1;
    }
    public void setElement_1(T element_1) {
        this.element_1 = element_1;
    }
    public U getElement_2() {
        return element_2;
    }
    public void setElement_2(U element_2) {
        this.element_2 = element_2;
    }

    public Object makeString(Object object){
        return object;
    }
    
}
