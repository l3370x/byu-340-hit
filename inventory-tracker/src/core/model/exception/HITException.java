package core.model.exception;

/**
 *
 * @author kemcqueen
 */
public class HITException extends Exception {
    private final Severity severity;
    
    public HITException(Throwable cause) {
        super(cause);
        
        this.severity = Severity.ERROR;
    }
    
    public HITException(Severity sev, String message) {
        super(message);
        
        this.severity = sev;
    }
}
