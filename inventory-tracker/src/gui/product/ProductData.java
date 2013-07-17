package gui.product;

import core.model.Product;
import gui.common.Tagable;

/**
 * Display data class for products.
 */
public class ProductData extends Tagable {

	/**
	 * Description attribute.
	 */
	private String _description;
	
	/**
	 * Size attribute.
	 */
	private String _size;
	
	/**
	 * Count attribute.
	 */
	private String _count;
	
	/**
	 * Shelf Life attribute
	 */
	private String _shelfLife;
	
	/**
	 * Supply attribute.
	 */
	private String _supply;
	
	/**
	 * Barcode attribute.
	 */
	private String _barcode;

	/**
	 * Constructor.
	 * 
	 * {@pre None}
	 * 
	 * {@post getDescription() == ""}
	 * {@post getSize() == ""}
	 * {@post getCount() == ""}
	 * {@post getShelfLife() == ""}
	 * {@post getSupply() == ""}
	 * {@post getBarcode() == ""}
	 */
	public ProductData(Product prod) {
		setTag(prod);
		_description = prod.getDescription();
		_size = Float.toString(prod.getSize().getValue()) + prod.getSize().getUnits().toString();
		_count = Integer.toString(prod.getContainer().getItemsCount(prod));
		_shelfLife = Integer.toString(prod.getShelfLifeInMonths());
		_supply = Integer.toString(prod.get3MonthSupplyQuota());;
		_barcode = prod.getBarCode().toString();
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
	 * {@pre barcode != null}
	 * 
	 * {@post getBarcode() == barcode}
	 */
	public void setBarcode(String barcode) {
		this._barcode = barcode;
	}

	/**
	 * Returns the value of the Description value.
	 */
	public String getDescription() {
		return _description;
	}

	/**
	 * Sets the value of the Description value.
	 * 
	 * @param description New Description value
	 * 
	 * {@pre description != null}
	 * 
	 * {@post getDescription() == description}
	 */
	public void setDescription(String description) {
		this._description = description;
	}

	/**
	 * Returns the value of the Size attribute.
	 */
	public String getSize() {
		return _size;
	}

	/**
	 * Sets the value of the Size attribute.
	 * 
	 * @param size New Size value
	 * 
	 * {@pre size != null}
	 * 
	 * {@post getSize() == size}
	 */
	public void setSize(String size) {
		this._size = size;
	}

	/**
	 * Returns the value of the Count attribute.
	 */
	public String getCount() {
		return _count;
	}

	/**
	 * Sets the value of the Count attribute.
	 * 
	 * @param count New Count value
	 * 
	 * {@pre count != null}
	 * 
	 * {@post getCount() == count}
	 */
	public void setCount(String count) {
		this._count = count;
	}

	/**
	 * Returns the value of the Shelf Life attribute.
	 */
	public String getShelfLife() {
		return _shelfLife;
	}

	/**
	 * Sets the value of the Shelf Life attribute.
	 * 
	 * @param shelfLife New Shelf Life value
	 * 
	 * {@pre shelfLife != null}
	 * 
	 * {@post getShelfLife() == shelfLife}
	 */
	public void setShelfLife(String shelfLife) {
		this._shelfLife = shelfLife;
	}

	/**
	 * Returns the value of the Supply attribute.
	 */
	public String getSupply() {
		return _supply;
	}

	/**
	 * Sets the value of the Supply attribute.
	 * 
	 * @param supply New Supply value
	 * 
	 * {@pre supply != null}
	 * 
	 * {@post getSupply() == supply}
	 */
	public void setSupply(String supply) {
		this._supply = supply;
	}

}

