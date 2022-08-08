package Helpers.Facades;

public interface IAlert {

    public final static int SIMPLE = 0;
    public final static int WARNING = 1;
    public final static int ERROR = 2;
    public final static int CONFIRMATION = 3;
    public final static int TEXT_INPUT = 4;

    public void alert(String text, int type);
    
}
