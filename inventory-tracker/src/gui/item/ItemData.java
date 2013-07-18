package gui.item;

import core.model.Category;
import core.model.Item;
import core.model.ProductContainer;
import java.util.*;

import gui.common.Tagable;

/**
 * Display data class for items.
 */
public class ItemData extends Tagable {

    /**
     * EntryDate attribute.
     */
    private Date _entryDate;
    /**
     * ExpirationDate attribute.
     */
    private Date _expirationDate;
    /**
     * Barcode attribute.
     */
    private String _barcode;
    /**
     * StorageUnit attribute.
     */
    private String _storageUnit;
    /**
     * ProductGroup attribute.
     */
    private String _productGroup;

    /**
     * Constructor.
     *
     * {
     *
     * @pre None}
     *
     * {
     * @post getEntryDate() == current date/time}
	 * {
     * @post getExpirationDate() == current date/time}
	 * {
     * @post getBarcode() == ""}
	 * {
     * @post getStorageUnit() == ""}
	 * {
     * @post getProductGroup() == ""}
     */
    public ItemData(Item item) {
        _entryDate = item.getEntryDate();
        _expirationDate = item.getExpirationDate();
        _barcode = item.getBarCode().getValue();
        
        final ProductContainer container = item.getContainer();
        if (null != container) {
            _storageUnit = container.getStorageUnit().getName();
        }
        if (container instanceof Category) {
            _productGroup = ((Category) container).getName();
        }
        
        this.setTag(item);
    }

    /**
     * Returns the value of the Barcode attribute.
     */
    public String getBarcode() {
        return _barcode;
    }

    /**
     * Sets the value of the Barcode attribute.
     *
     * @param barcode New Barcode value
     *
     * {
     * @pre barcode != null}
     *
     * {
     * @post getBarcode() == barcode}
     */
    public void setBarcode(String barcode) {
        this._barcode = barcode;
    }

    /**
     * Returns the value of the EntryDate attribute.
     */
    public Date getEntryDate() {
        return _entryDate;
    }

    /**
     * Sets the value of the EntryDate attribute.
     *
     * @param entryDate New EntryDate value
     *
     * {
     * @pre entryDate != null}
     *
     * {
     * @post getEntryDate() == entryDate}
     */
    public void setEntryDate(Date entryDate) {
        this._entryDate = entryDate;
    }

    /**
     * Returns the value of the ExpirationDate attribute.
     */
    public Date getExpirationDate() {
        return _expirationDate;
    }

    /**
     * Sets the value of the ExpirationDate attribute.
     *
     * @param expirationDate New ExpirationDate value
     *
     * {
     * @pre None}
     *
     * {
     * @post getExpirationDate() == expirationDate}
     */
    public void setExpirationDate(Date expirationDate) {
        this._expirationDate = expirationDate;
    }

    /**
     * Returns the value of the StorageUnit attribute.
     */
    public String getStorageUnit() {
        return _storageUnit;
    }

    /**
     * Sets the value of the StorageUnit attribute.
     *
     * @param storageUnit New StorageUnit value
     *
     * {
     * @pre storageUnit != null}
     *
     * {
     * @post getStorageUnit() == storageUnit}
     */
    public void setStorageUnit(String storageUnit) {
        this._storageUnit = storageUnit;
    }

    /**
     * Returns the value of the ProductGroup attribute.
     */
    public String getProductGroup() {
        return _productGroup;
    }

    /**
     * Sets the value of the ProductGroup attribute.
     *
     * @param productGroup New ProductGroup value
     *
     * {
     * @pre productGroup != null}
     *
     * {
     * @post getProductGroup() == productGroup}
     */
    public void setProductGroup(String productGroup) {
        this._productGroup = productGroup;
    }
}
