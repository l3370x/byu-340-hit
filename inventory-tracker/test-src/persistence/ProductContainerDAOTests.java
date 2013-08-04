package persistence;

import core.model.exception.HITException;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static persistence.ProductContainerDAO.COL_IS_STORAGE_UNIT;
import static persistence.ProductContainerDAO.COL_NAME;

/**
 * Created with IntelliJ IDEA. User: kmcqueen Date: 8/3/13 Time: 11:06 PM To change this template
 * use File | Settings | File Templates.
 */
public class ProductContainerDAOTests {

    @Test
    public void testInsertProductContainer() throws HITException {
        ProductContainerDAO dao = new ProductContainerDAO();

        DataTransferObject dtoIn = new DataTransferObject();
        dtoIn.setValue(COL_NAME, "My Storage Unit");
        dtoIn.setValue(COL_IS_STORAGE_UNIT, Boolean.TRUE);

        dao.insert(dtoIn);

        Iterable<DataTransferObject> data = dao.getAll();
        for (DataTransferObject dtoOut : data) {
            assertEquals(dtoIn.getValue(COL_NAME), dtoOut.getValue(COL_NAME));
            assertEquals(dtoIn.getValue(COL_IS_STORAGE_UNIT), dtoOut.getValue(COL_IS_STORAGE_UNIT));
        }
    }
}
