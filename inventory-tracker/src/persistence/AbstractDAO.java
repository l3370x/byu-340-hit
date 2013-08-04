package persistence;

import core.model.exception.HITException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static common.util.DbUtils.safeClose;
import static core.model.exception.HITException.Severity.ERROR;
import static java.text.MessageFormat.format;
import static persistence.TransactionManager.Factory.getTransactionManager;

/**
 * The {@code AbstractDAO} class is a base class for data access objects in the system.  It defines
 * the contract for the data access objects as well as providing the default behavior.
 *
 * @author Keith McQueen
 */
public abstract class AbstractDAO {
    private static final String SELECT_SQL = "select {0} from {1}";
    private static final String SELECT_SQL_WITH_CONDITION = "select {0} from {1} where {2}";

    private static final String INSERT_SQL = "insert into {1} ({0}) values ({2})";


    /**
     * Get all rows associated with this DAO.
     *
     * @return a collection of data transfer objects representing all the data associated with this
     *         DAO
     * @throws HITException if the data could not be retrieved for any reason
     */
    public Iterable<DataTransferObject> getAll() throws HITException {
        // prepare the query string
        String query = this.prepareSelectSQL();

        // begin a transaction (get a connection)
        Connection connection = getTransactionManager().beginTransaction();

        PreparedStatement statement = null;
        ResultSet results = null;
        try {
            // prepare the statement
            statement = connection.prepareStatement(query);

            // execute the statement
            results = statement.executeQuery();

            // prepare the list of data to be returned
            List<DataTransferObject> data = new ArrayList<>();
            while (results.next()) {
                // create the DTO
                DataTransferObject row = new DataTransferObject();

                // populate the DTO from the results
                for (String colName : this.getColumnNames()) {
                    row.setValue(colName, results.getObject(colName));
                }

                // add the DTO to the result list
                data.add(row);
            }

            // return the data
            return data;
        } catch (SQLException e) {
            throw new HITException(ERROR, e);
        } finally {
            // end the transaction (no need to commit)
            getTransactionManager().endTransaction(connection, false);

            // close the statement
            safeClose(statement);

            // close the result set
            safeClose(results);
        }
    }

    /**
     * Insert a new row for this DAO with the given data.
     *
     * @param dto the data to be inserted
     * @throws HITException if the data could not be inserted for any reason
     */
    public void insert(DataTransferObject dto) throws HITException {
        // prepare the insert query
        String query = this.prepareInsertSQL();

        // begin a transaction (get a connection)
        Connection connection = getTransactionManager().beginTransaction();

        PreparedStatement statement = null;
        try {
            // prepare the statement
            statement = connection.prepareStatement(query);

            // bind the statement variables
            String[] columnNames = this.getColumnNames();
            for (int i = 0; i < columnNames.length; i++) {
                statement.setObject(i + 1, dto.getValue(columnNames[i]));
            }

            if (statement.executeUpdate() != 1) {
                throw new HITException(ERROR, "Unable to insert data into " + this.getTableName());
            }
        } catch (SQLException e) {
            throw new HITException(ERROR, "Unable to insert data into " + this.getTableName());
        } finally {
            // end the transaction
            getTransactionManager().endTransaction(connection, true);

            // close the statement
            safeClose(statement);
        }
    }

    /**
     * Delete the given data associated with this DAO.
     *
     * @param dto represents the data to be deleted
     * @throws HITException if the data could not be deleted for any reason
     */
    public void delete(DataTransferObject dto) throws HITException {

    }

    /**
     * Update the data associated with this DAO using the given data.
     *
     * @param dto the data to be updated
     * @throws HITException if the data could not be updated for any reason
     */
    public void update(DataTransferObject dto) throws HITException {

    }

    /**
     * Get the column names (or key names) associated with this DAO.  It is critical that the column
     * (or key) names be returned in the same order every time this method is invoked.
     *
     * @return an array of the column (or key) names associated with this DAO
     */
    public abstract String[] getColumnNames();

    /**
     * Get the table name associated with this DAO.
     *
     * @return the table name associated with this DAO
     */
    public abstract String getTableName();

    private String prepareSelectSQL() {
        // prepare the column list
        String columnList = Arrays.toString(this.getColumnNames());
        columnList = columnList.replace("[", "");
        columnList = columnList.replace("]", "");

        return format(SELECT_SQL, columnList, this.getTableName());
    }

    private String prepareInsertSQL() {
        // prepare the column list
        String[] columnNames = this.getColumnNames();
        String columnList = Arrays.toString(columnNames);
        columnList = columnList.replace("[", "");
        columnList = columnList.replace("]", "");

        // prepare the placeholders string
        String[] placholders = new String[columnNames.length];
        Arrays.fill(placholders, "?");
        String placeholdersText = Arrays.toString(placholders);
        placeholdersText = placeholdersText.replace("[", "");
        placeholdersText = placeholdersText.replace("]", "");

        return format(INSERT_SQL, columnList, this.getTableName(), placeholdersText);
    }
}
