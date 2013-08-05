package persistence;

public class RemovedItemsDAO extends AbstractDAO {

    public static final String TABLE_NAME = "REMOVED_ITEMS_REPORT";
    public static final String COL_ID = "ID";
    public static final String COL_LAST_REPORT_RUN = "LAST_REPORT_RUN";

    private static final String[] COLUMN_NAMES = new String[]{
            COL_ID,
            COL_LAST_REPORT_RUN
    };

    @Override
    public String[] getColumnNames() {
        return COLUMN_NAMES;
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
