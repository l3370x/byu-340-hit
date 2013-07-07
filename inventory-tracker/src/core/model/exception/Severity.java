package core.model.exception;

/**
 * The {@code Severity} enumeration represents the possible values of severity
 * for a {@link HITException}.
 * 
 * @author kemcqueen
 */
public enum Severity {
    /**
     * ERROR is the highest severity and indicates that the system is in an 
     * unstable and maybe even unusable state.  In no case should this
     * be ignored.  It should be brought to the user's attention as soon as 
     * possible.
     */
    ERROR, 
    
    /**
     * Indicates that something unexpected has happened and that the system
     * might be in an unstable condition.  This should not be ignored and may 
     * need to be brought to the user's attention.
     */
    WARNING, 
    
    /**
     * Indicates that something unexpected has happened but that the system is 
     * still in a stable state.  This may not need to be brought to the user's
     * attention.
     */
    INFO, 
    
    /**
     * Indicates that something unexpected has happened but that there is really
     * nothing wrong with the system.  This should not be brought to the user's
     * attention.
     */
    OK;
}
