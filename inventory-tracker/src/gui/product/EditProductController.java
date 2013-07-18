package gui.product;

import static core.model.InventoryManager.Factory.getInventoryManager;
import static core.model.Product.Factory.newProduct;

import java.util.regex.Pattern;

import core.model.BarCode;
import core.model.Product;
import core.model.Quantity;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import gui.common.*;

/**
 * Controller class for the edit product view.
 */
public class EditProductController extends Controller
        implements IEditProductController {

    private ProductData target;
    private String sizeValue;
    private String shelfLife;
    private String monthSupply;
    private static final Pattern POSITIVE_INTEGER_PATTERN = 
	Pattern.compile("-?\\d+(\\.\\d+)?");

    /**
     * Constructor.
     *
     * @param view Reference to the edit product view
     * @param target Product being edited
     */
    public EditProductController(IView view, ProductData target) {
        super(view);
        this.target = target;
        sizeValue = target.getSize().replaceAll("[\\D]", "");
        shelfLife = target.getShelfLife().replaceAll("[\\D]", "");
        monthSupply = target.getSupply().replaceAll("[\\D]", "");
        construct();
    }

    //
    // Controller overrides
    //
    /**
     * Returns a reference to the view for this controller.
     *
     * {
     *
     * @pre None}
     *
     * {
     * @post Returns a reference to the view for this controller.}
     */
    @Override
    protected IEditProductView getView() {
        return (IEditProductView) super.getView();
    }

    /**
     * Sets the enable/disable state of all components in the controller's view. A component should
     * be enabled only if the user is currently allowed to interact with that component.
     *
     * {
     *
     * @pre None}
     *
     * {
     * @post The enable/disable state of all components in the controller's view have been set
     * appropriately.}
     */
    @Override
    protected void enableComponents() {
        this.getView().enableBarcode(false);

        if (target.getSize().contains("count")) {
            this.getView().enableSizeValue(false);
        } else {
            this.getView().enableSizeValue(true);
        }

        this.getView().enableOK(false);
    }

    /**
     * Loads data into the controller's view.
     *
     * {
     *
     * @pre None}
     *
     * {
     * @post The controller has loaded data into its view}
     */
    @Override
    protected void loadValues() {
        this.getView().setDescription(target.getDescription());
        this.getView().setBarcode(target.getBarcode());
        this.getView().setSizeValue(sizeValue);
        try {
			this.getView().setSizeUnit(UnitsConverter.stringToUnits(target.getCount()));
		} catch (HITException ex) {
			 ExceptionHandler.TO_USER.reportException(ex,
	                    "Unable To Load Product");
		}
        this.getView().setShelfLife(shelfLife);
        this.getView().setSupply(monthSupply);
    }

    //
    // IEditProductController overrides
    //
    /**
     * This method is called when any of the fields in the edit product view is changed by the user.
     */
    @Override
    public void valuesChanged() {
    	  // If size units is Count, disable the size values text field and set it as one
        SizeUnits su = this.getView().getSizeUnit();
        if (su == SizeUnits.Count) {
            this.getView().enableSizeValue(false);
            this.getView().setSizeValue("1");
        } else // Enable Size value textbox and set it to the correct number
        {
            this.getView().enableSizeValue(true);
        }

        //Makes sure that the product has a description
        String description = this.getView().getDescription();
        // Makes sure the size value is a number and not blank
        sizeValue = this.getView().getSizeValue();
        // Makes sure the shelf life is a number and not blank
        shelfLife = this.getView().getShelfLife();
        // Makes sure the 3 month supply is a number and not blank
        monthSupply = this.getView().getSupply();
            
        if(descriptionIsValid(description) == false 
           || productSizeIsValid(sizeValue) == false 
           || shelfLifeIsValid(shelfLife) == false 
           || monthSupplyIsValid(monthSupply) == false)
        {
        	this.getView().enableOK(false);
        	return;
        }

        // If it passes all conditions, enable ok button for adding product
        this.getView().enableOK(true);

    }

    /**
     * This method is called when the user clicks the "OK" button in the edit product view.
     */
    @Override
    public void editProduct() {
        try {
            // Make new product
            final Product product = newProduct(BarCode.getBarCodeFor(this.getView().getBarcode()),
                    this.getView().getDescription());

            product.setShelfLifeInMonths(Integer.parseInt(this.getView().getShelfLife()));
            product.set3MonthSupplyQuota(Integer.parseInt(this.getView().getSupply()));
            product.setSize(new Quantity(Float.parseFloat(this.getView().getSizeValue()),
                    UnitsConverter.sizeUnitsToUnits(this.getView().getSizeUnit())));


            // add the product to the inventory manager
            getInventoryManager().addProduct(product);

        } catch (HITException ex) {
            ExceptionHandler.TO_USER.reportException(ex,
                    "Unable To Edit Product");
        }

    }
    
    private boolean productSizeIsValid(String sizeValue) {
        if (null == sizeValue || sizeValue.isEmpty()) {
            return false;
        }

        return POSITIVE_INTEGER_PATTERN.matcher(sizeValue).matches();
    }
    
    private boolean descriptionIsValid(String description) {
        if (null == description || description.isEmpty()) {
            return false;
        }

        return true;
    }
    
    private boolean shelfLifeIsValid(String shelfLife) {
        if (null == shelfLife || shelfLife.isEmpty()) {
            return false;
        }

        return POSITIVE_INTEGER_PATTERN.matcher(shelfLife).matches();
    }
    
    private boolean monthSupplyIsValid(String monthSupply) {
        if (null == monthSupply || monthSupply.isEmpty()) {
            return false;
        }

        return POSITIVE_INTEGER_PATTERN.matcher(monthSupply).matches();
    }
}
