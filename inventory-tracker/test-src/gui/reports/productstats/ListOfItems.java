package gui.reports.productstats;

import core.model.*;
import core.model.exception.HITException;

import java.util.Calendar;
import java.util.Date;
import java.util.Observer;

/**
 * @author Cameron Jones
 * @author Keith McQueen
 */
public class ListOfItems {
    ItemCollection currentItems = new ItemCollection(null);
    ItemCollection removedItems = new ItemCollection(null);

    public ListOfItems() {
        try {
            Calendar addDate = Calendar.getInstance();

            // item added today
            currentItems.add(new MockItem("100000000001", new Date(), null));

            // item one day ago
            addDate.setTime(new Date());
            addDate.add(Calendar.DATE, -1);
            currentItems.add(new MockItem("100000000002", addDate.getTime(), null));

            // added one week ago
            addDate.setTime(new Date());
            addDate.add(Calendar.WEEK_OF_YEAR, -1);
            currentItems.add(new MockItem("100000000003", addDate.getTime(), null));

            // added two weeks ago
            addDate.setTime(new Date());
            addDate.add(Calendar.WEEK_OF_YEAR, -2);
            currentItems.add(new MockItem("100000000004", addDate.getTime(), null));

            // added one month ago
            addDate.setTime(new Date());
            addDate.add(Calendar.MONTH, -1);
            currentItems.add(new MockItem("100000000005", addDate.getTime(), null));

            // added one year ago
            addDate.setTime(new Date());
            addDate.add(Calendar.YEAR, -1);
            currentItems.add(new MockItem("100000000006", addDate.getTime(), null));

            Calendar removeDate = Calendar.getInstance();

            // added today, removed today
            removedItems.add(new MockItem("100000000007", new Date(), new Date()));

            // 8: Added 3 weeks ago, removed 1 week ago
            addDate.setTime(new Date());
            addDate.add(Calendar.WEEK_OF_YEAR, -3);
            removeDate.setTime(new Date());
            removeDate.add(Calendar.WEEK_OF_YEAR, -1);
            removedItems.add(new MockItem("100000000008", addDate.getTime(), removeDate.getTime()));

            // 9: added one year ago. Removed yesterday
            addDate.setTime(new Date());
            addDate.add(Calendar.YEAR, -1);
            removeDate.setTime(new Date());
            removeDate.add(Calendar.DATE, -1);
            removedItems.add(new MockItem("100000000009", addDate.getTime(), removeDate.getTime()));

            // 10: added six months ago, removed 3 months ago
            addDate.setTime(new Date());
            addDate.add(Calendar.MONTH, -6);
            removeDate.setTime(new Date());
            removeDate.add(Calendar.MONTH, -3);
            removedItems.add(new MockItem("100000000010", addDate.getTime(), removeDate.getTime()));

            // 11: Added and removed 6 weeks ago
            addDate.setTime(new Date());
            addDate.add(Calendar.WEEK_OF_YEAR, -6);
            removeDate.setTime(new Date());
            removeDate.add(Calendar.WEEK_OF_YEAR, -6);
            removedItems.add(new MockItem("100000000011", addDate.getTime(), removeDate.getTime()));

            // 12: added yesterday, removed today
            addDate.setTime(new Date());
            addDate.add(Calendar.DATE, -1);
            removeDate.setTime(new Date());
            removedItems.add(new MockItem("100000000012", addDate.getTime(), removeDate.getTime()));
        } catch (HITException e) {
            e.printStackTrace();
        }
    }

    public Iterable<Item> returnCurrent() {
        return currentItems.getContents();
    }

    public Iterable<Item> returnRemoved() {
        return removedItems.getContents();
    }

    private static class MockItem implements Item {
        private final BarCode barCode;
        private final Date entryDate;
        private final Date exitDate;

        public MockItem(String barcode, Date entryDate, Date exitDate) {
            this.barCode = BarCode.getBarCodeFor(barcode);
            this.entryDate = entryDate;
            this.exitDate = exitDate;
        }

        @Override
        public Product getProduct() {
            return null;
        }

        @Override
        public BarCode getBarCode() {
            return this.barCode;
        }

        @Override
        public Date getEntryDate() {
            return this.entryDate;
        }

        @Override
        public Date getExitDate() {
            return this.exitDate;
        }

        @Override
        public void setExitDate(Date d) {
        }

        @Override
        public Date getExpirationDate() {
            return null;
        }

        @Override
        public void setEntryDate(Date date) {
        }

        @Override
        public void addObs(Observer o) {
        }

        @Override
        public void wasAddedTo(ProductContainer container) throws HITException {
        }

        @Override
        public void wasRemovedFrom(ProductContainer container) throws HITException {
        }

        @Override
        public void transfer(ProductContainer from, ProductContainer to) throws HITException {
        }

        @Override
        public ProductContainer getContainer() {
            return null;
        }
    }
}
