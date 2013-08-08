/**
 *
 */
package persistence;

/**
 * @author rfun11
 */
public class ProductPCDAO extends AbstractDAO {

    public static final String TABLE_NAME = "PRODUCT_PRODUCT_CONTAINER";
    public static final String COL_PRODUCT_ID = "PRODUCT_ID";
    public static final String COL_PRODUCT_CONTAINER_ID = "PRODUCT_CONTAINER_ID";

    private static final String[] COLUMN_NAMES = new String[] { 
           COL_PRODUCT_ID, 
           COL_PRODUCT_CONTAINER_ID };

    @Override
    public String[] getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    public String[] getKeyColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }

}
