package core.model.exception;

import javax.swing.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.JOptionPane.showMessageDialog;

/**
 * @author kemcqueen
 */
public enum ExceptionHandler {

    /**
     * Writes errors/exceptions to a log.
     */
    TO_LOG {
        @Override
        public void reportException(Throwable error, String additionalMessage) {
            Level level;

            if (error instanceof HITException) {
                switch (((HITException) error).getSeverity()) {
                    case ERROR:
                        level = Level.SEVERE;
                        break;

                    case WARNING:
                        level = Level.WARNING;
                        break;

                    case INFO:
                    default:
                        level = Level.INFO;
                        break;
                }

                Logger.getAnonymousLogger().log(level, additionalMessage, error);

                System.err.println(additionalMessage);
                error.printStackTrace(System.err);
            }
        }
    },

    /**
     * Reports errors/exceptions to the user/screen using a message dialog.
     */
    TO_USER {
        @Override
        public void reportException(Throwable error, String additionalMessage) {
            int msgType = JOptionPane.PLAIN_MESSAGE;

            // if the error was a HITException, use the Severity to determine
            // which alert to display to the user
            if (error instanceof HITException) {
                switch (((HITException) error).getSeverity()) {
                    case ERROR:
                        msgType = JOptionPane.ERROR_MESSAGE;
                        break;

                    case WARNING:
                        msgType = JOptionPane.WARNING_MESSAGE;
                        break;

                    case INFO:
                        msgType = JOptionPane.INFORMATION_MESSAGE;
                        break;

                    default:
                        msgType = JOptionPane.PLAIN_MESSAGE;
                        break;
                }
            }

            showMessageDialog(null, error.getMessage(), additionalMessage, msgType);
        }
    };

    /**
     * Report the given error.
     *
     * @param error             the error to be reported
     * @param additionalMessage an additional message that will be reported along with the error
     *                          (should not be null)
     */
    public void reportException(Throwable error, String additionalMessage) {
    }
}
