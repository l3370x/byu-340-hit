package gui.product;

import core.model.BarCode;
import core.model.Category;
import core.model.Container;
import core.model.Product;
import core.model.Quantity;
import core.model.Quantity.Units;
import static core.model.InventoryManager.Factory.getInventoryManager;
import static core.model.Product.Factory.newProduct;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import gui.common.*;

/**
 * Controller class for the add item view.
 */
public class AddProductController extends Controller implements
		IAddProductController {
	private final String barcode;
	private String sizeValue = "0";
	private String shelfLife = "0";
	private String monthSupply = "0";
	/**
	 * Constructor.
	 * 
	 * @param view Reference to the add product view
	 * @param barcode Barcode for the product being added
	 */
	public AddProductController(IView view, String barcode) {
		super(view);
		this.barcode = barcode;
		construct();
	}

	//
	// Controller overrides
	//
	
	/**
	 * Returns a reference to the view for this controller.
	 * 
	 * {@pre None}
	 * 
	 * {@post Returns a reference to the view for this controller.}
	 */
	@Override
	protected IAddProductView getView() {
		return (IAddProductView)super.getView();
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently
	 * allowed to interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's view
	 * have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
		this.getView().enableBarcode(false);
		this.getView().enableSizeValue(false);
		this.getView().enableOK(false);
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 *  {@pre None}
	 *  
	 *  {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
		this.getView().setDescription("");
		this.getView().setBarcode(barcode);
		this.getView().setSizeValue("1");
		this.getView().setSizeUnit(SizeUnits.Count);
		this.getView().setShelfLife("0");
		this.getView().setSupply("0");
	}

	//
	// IAddProductController overrides
	//
	
	/**
	 * This method is called when any of the fields in the
	 * add product view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		
        // If size units is Count, disable the size values text field and set it as one
        SizeUnits su = this.getView().getSizeUnit();
        if (su == SizeUnits.Count)
        {
        	this.getView().enableSizeValue(false);
        	this.getView().setSizeValue("1");
        	sizeValue = "0";
        }
        else // Enable Size value textbox and set it to the correct number
        {
        	this.getView().enableSizeValue(true);
        	this.getView().setSizeValue(sizeValue);
        }
        
        //Makes sure that the product has a description
        String description = this.getView().getDescription();
        if (null == description || description.isEmpty()) {
            this.getView().enableOK(false);
            return;
        }
        
        // Makes sure the size value is a number and not blank
        sizeValue = this.getView().getSizeValue();
        if (false == sizeValue.matches("-?\\d+(\\.\\d+)?") || 
        		null == sizeValue || sizeValue.isEmpty()) {
        	
        	this.getView().enableOK(false);
        	return;
        }
        
        // Makes sure the shelf life is a number and not blank
        shelfLife = this.getView().getShelfLife();
        if (false == shelfLife.matches("-?\\d+(\\.\\d+)?") || 
        		null == shelfLife|| shelfLife.isEmpty()) {
        	
        	this.getView().enableOK(false);
        	return;
        }
        
        // Makes sure the 3 month supply is a number and not blank
        monthSupply = this.getView().getSupply();
        if (false == monthSupply.matches("-?\\d+(\\.\\d+)?") || 
        		null == monthSupply|| monthSupply.isEmpty()) {
        	
        	this.getView().enableOK(false);
        	return;
        }
        
        // If it passes all conditions, enable ok button for adding product
        this.getView().enableOK(true);
            
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add product view.
	 */
	@Override
	public void addProduct() {
		try {
			// Make new product
			final Product product = newProduct(BarCode.getBarCodeFor(this.getView().getBarcode()),
											   this.getView().getDescription());
			
			product.setShelfLifeInMonths(Integer.parseInt(this.getView().getShelfLife()));
			product.set3MonthSupplyQuota(Integer.parseInt(this.getView().getSupply()));
            product.setSize(new Quantity(Float.parseFloat(this.getView().getSizeValue()), 
            			    UnitController.sizeUnitsToUnits(this.getView().getSizeUnit())));
            
            // add the product to the inventory manager
            getInventoryManager().addProduct(product);
            
        } catch (HITException ex) {
            ExceptionHandler.TO_USER.reportException(ex, 
                    "Unable To Create Product");
        }
	}

}

