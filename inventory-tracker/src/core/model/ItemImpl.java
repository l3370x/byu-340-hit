package core.model;

import core.model.exception.HITException;

import java.util.Calendar;
import java.util.Date;

import common.util.DateUtils;
import java.util.Observer;

/**
 * The {@code ItemImpl} class is the default implementation of the {@link Item} interface. The
 * constructor(s(s) are (mostly) hidden. To get an Item instance you must use the
 * {@link Item.Factory}.
 *
 * @invariant ?
 *
 * @author kemcqueen
 */
class ItemImpl extends AbstractContainable<ProductContainer> implements Item {

    private Date entryDate;
    private BarCode barcode;
    private Date exitDate;
    private Product product;

    ItemImpl(Date entryDate, Product product,
            BarCode barcode) {
        this.product = product;
        this.entryDate = entryDate;
        this.barcode = barcode;
        computeExpirationDate();
    }

    @Override
    public Product getProduct() {
        return this.product;
    }

    @Override
    public Date getExpirationDate() {
        return this.computeExpirationDate();
    }

    @Override
    public void transfer(ProductContainer from, ProductContainer to) throws HITException {
        from.removeItem(this);
        to.addItem(this);
    }

    @Override
    public String toString() {
        return this.getBarCode().toString();
    }

    @Override
    protected void verifyContainedIn(ProductContainer container) throws HITException {
        if (null == container) {
            throw new HITException(HITException.Severity.WARNING,
                    "Container must not be null");
        }

        if (false == container.containsItem(this)) {
            throw new HITException(HITException.Severity.WARNING,
                    "Container (" + container + ") does not contain this item");
        }
    }

    @Override
    protected void verifyNotContainedIn(ProductContainer container) throws HITException {
        if (null == container) {
            throw new HITException(HITException.Severity.WARNING,
                    "Container must not be null");
        }

        if (this.getContainer() != container) {
            throw new HITException(HITException.Severity.WARNING,
                    "Container (" + container + ") is not the current container");
        }

        if (container.containsItem(this)) {
            throw new HITException(HITException.Severity.WARNING,
                    "Container (" + container + ") still contains this item");
        }
    }

    @Override
    public BarCode getBarCode() {
        return this.barcode;
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
        this.exitDate = d;
    }

    @Override
    public void setEntryDate(Date date) {
        // make checks to date
        if (date.after(DateUtils.earliestDate())
                && date.before(DateUtils.currentDate())) {
            // Yes. Continue
            this.entryDate = date;
            this.notifyObservers(new ModelNotification(
                    ModelNotification.ChangeType.CONTENT_UPDATED, this
                    .getContainer(), this));
        }
    }

    private Date computeExpirationDate() {
        int shelfLife = this.getProduct().getShelfLifeInMonths();
        if (shelfLife <= 0) {
            return null;
        }
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(this.getEntryDate());
        cal.add(Calendar.MONTH, shelfLife);
        
        return cal.getTime();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
    }
}
