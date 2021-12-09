package app.exception;

public class OrderAlreadyAddedException  extends Exception{
    public OrderAlreadyAddedException(String textError){
        super(textError);
    }
}
