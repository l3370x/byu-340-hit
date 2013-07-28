package common;

/**
 * The {@code Command} interface defines the contract for a class that can execute code.
 */
public interface Command {
    /**
     * Perform the code associated with this command.
     */
    void execute();

    /**
     * Undo the operation performed by this command.
     */
    void undo();
}
