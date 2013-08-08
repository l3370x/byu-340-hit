package persistence;

import core.model.exception.HITException;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.MessageFormat;

import static common.util.DbUtils.safeClose;
import static core.model.exception.HITException.Severity.ERROR;
import static java.sql.DriverManager.getConnection;

/**
 * The {@code TransactionManagerImpl} is the default implementation of the {@link
 * TransactionManager} interface which controls access to the database.
 *
 * @author Keith McQueen
 */
class TransactionManagerImpl implements TransactionManager {
    private static final String DRIVER_NAME = "org.sqlite.JDBC";
    private static final String CONNECTION_URL = "jdbc:sqlite:{0}";

    private final String dbPath;

    public TransactionManagerImpl(String dbPath) throws HITException {
        this.dbPath = dbPath;

        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            throw new HITException(ERROR, e);
        }
    }

    @Override
    public Connection beginTransaction() throws HITException {
        try {
            String connectionURL = MessageFormat.format(CONNECTION_URL, this.dbPath);
            Connection dbConnection = getConnection(connectionURL);
            dbConnection.createStatement().execute("PRAGMA foreign_keys = ON;");
            dbConnection.setAutoCommit(false);

            return dbConnection;
        } catch (SQLException e) {
            throw new HITException(ERROR, e);
        }
    }

    @Override
    public void endTransaction(Connection connection, boolean commit) throws HITException {
        try {
            if (commit) {
                connection.commit();
            } else {
                connection.rollback();
            }
        } catch (SQLException e) {
            throw new HITException(ERROR, e);
        } finally {
            safeClose(connection);
        }
    }
}
