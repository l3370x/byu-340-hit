package persistence;

import core.model.exception.HITException;
import junit.framework.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created with IntelliJ IDEA. User: kmcqueen Date: 8/7/13 Time: 1:33 PM To change this template use
 * File | Settings | File Templates.
 */
public class ItemDAOTests {
    @Test
    public void testDAO() {
        DataTransferObject dto = new DataTransferObject();
        dto.setValue(ItemDAO.COL_BARCODE, "123456789111");
        dto.setValue(ItemDAO.COL_PROD_BARCODE, "111987654321");
        Date now = new Date();
        dto.setValue(ItemDAO.COL_ENTRY_DATE, now);

        try {
            ItemDAO dao = new ItemDAO();

            dao.deleteAll();

            dao.insert(dto);

            for (DataTransferObject d : dao.get(dto)) {
                Date entryDate = new Date((Long) d.getValue(ItemDAO.COL_ENTRY_DATE));
                System.out.println(d.getValue(ItemDAO.COL_ENTRY_DATE));
                System.out.println(entryDate);

                Assert.assertEquals(now.getTime(), entryDate.getTime());
            }
        } catch (HITException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
