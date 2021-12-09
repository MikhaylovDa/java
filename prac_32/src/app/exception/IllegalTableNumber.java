package app.exception;

public class IllegalTableNumber extends RuntimeException{
    public IllegalTableNumber(String textError) {
        super(textError);
    }
}
