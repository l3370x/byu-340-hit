package core.model.exception;

/**
 * The {@code HITException} is thrown whenever an operation on the HIT model
 * could not be completed for some reason.  The exception allows for
 * by severity (meaning that some exceptions may be worse than others).  For 
 * example, an exception will be thrown if an Item could not be added to some
 * ProductContainer, but it may be that the Item is already in the 
 * ProductContainer so the exception is not necessarily a show-stopper.
 * 
 * @author kemcqueen
 */
public class HITException extends Exception {
    private final Severity severity;
    
    /**
     * Create a new HITException instance with the given cause.
     * 
     * @param cause the original reason for throwing the exception
     */
    public HITException(Throwable cause) {
        super(cause);
        
        this.severity = Severity.ERROR;
    }
    
    /**
     * Create a new HITException instance with the given severity and message.
     * 
     * @param sev the level of severity of the exception
     * @param message a user-readable message indicating why the exception 
     * occurred
     */
    public HITException(Severity sev, String message) {
        super(message);
        
        this.severity = sev;
    }
    
    /**
     * Get the severity of the exception as it was set at construction time.
     * 
     * @return the severity of th exception
     */
    public Severity getSeverity() {
        return this.severity;
    }
}
