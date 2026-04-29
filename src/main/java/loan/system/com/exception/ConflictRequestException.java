package loan.system.com.exception;

public class ConflictRequestException extends RuntimeException{
    public ConflictRequestException (String msg){
        super(msg);
    }
}
