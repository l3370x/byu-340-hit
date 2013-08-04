package persistence;

import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;

import java.sql.Connection;

/**
 * The {@code TransactionManager} interface defines the contract for an object that can manage
 * database resources including transactions, connections, etc.
 *
 * @author Keith McQueen
 */
public interface TransactionManager {
    /**
     * Get a connection to the database
     * @return
     */
    //Connection getConnection();

    /**
     * Begin a transaction with the database.  The returned connection can be used to access the
     * underlying database.
     *
     * @return an open connection to the database
     * @throws HITException if the transaction could not be started for any reason
     */
    Connection beginTransaction() throws HITException;

    /**
     * End the transaction with the database.  If {@code commit} is {@code true}, changes made using
     * the connection will be committed to the database, otherwise the changes will be rolled back.
     * Calling this method will close the given connection.  Any attempts to use the connection
     * after calling this method will result in unpredictable behavior (likely an exception will be
     * thrown).
     *
     * @param connection the connection for which to end the transaction
     * @param commit     {@code true} to commit changes made using the given connection, {@code
     *                   false} to roll back the changes
     * @throws HITException if the transaction could not be closed for any reason
     */
    void endTransaction(Connection connection, boolean commit) throws HITException;

    public static final class Factory {
        //private static final String DB_PATH = ""
        private static final String DB_FILE = "inventory-tracker/db/hit.sqlite";

        private static TransactionManager transactionMgr;

        static {
            try {
                transactionMgr = new TransactionManagerImpl(DB_FILE);
            } catch (HITException e) {
                transactionMgr = null;
                ExceptionHandler.TO_LOG.reportException(e,
                        "Unable to initialize TransactionManager");
            }
        }

        public static TransactionManager getTransactionManager() {
            return transactionMgr;
        }
    }
}
