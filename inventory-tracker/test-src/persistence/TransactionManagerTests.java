package persistence;

import core.model.exception.HITException;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import static junit.framework.Assert.assertNotNull;
import static persistence.TransactionManager.Factory.getTransactionManager;

/**
 * The {@code TransactionManagerTests} class contains unit tests for the {@link
 * TransactionManager}.
 */
public class TransactionManagerTests {

    @Test
    public void testGetTransactionManager() throws HITException, SQLException {
        TransactionManager transactionManager = getTransactionManager();
        assertNotNull(transactionManager);

        Connection connection = transactionManager.beginTransaction();
        assertNotNull(connection);

        DatabaseMetaData metaData = connection.getMetaData();
        assertNotNull(metaData);

        ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
        assertNotNull(tables);

        while (tables.next()) {
            String table_name = tables.getString("TABLE_NAME");
            System.out.println(table_name);
        }
    }

}
