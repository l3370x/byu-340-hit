package gui.productgroup;

import static core.model.Category.Factory.newCategory;
import core.model.Container;
import core.model.Category;
import core.model.Quantity;
import gui.common.UnitsConverter;
import core.model.Quantity.Units;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the add product group view.
 */
public class AddProductGroupController extends Controller implements
		IAddProductGroupController {
	private final ProductContainerData container;
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to add product group view
	 * @param container Product container to which the new product group is being added
	 */
	public AddProductGroupController(IView view, ProductContainerData container) {
		super(view);
		this.container = container;
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
	protected IAddProductGroupView getView() {
		return (IAddProductGroupView)super.getView();
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
		this.getView().setSupplyValue("0");
		this.getView().setSupplyUnit(SizeUnits.Count);
	}

	//
	// IAddProductGroupController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * add product group view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		// if the name is null or the name is empty, then we can't create a 
        // storage unit
        String name = this.getView().getProductGroupName();
        if (null == name || name.isEmpty()) {
            this.getView().enableOK(false);
            return;
        }
        
        // if the count is not a number or is blank, don't allow OK
        String count = this.getView().getSupplyValue();
        if (false == count.matches("-?\\d+(\\.\\d+)?") || 
        		null == count || name.isEmpty()) {
        	this.getView().enableOK(false);
        	return;
        }

        // if the name matches an existing storage unit, then we can't
        // create a storage unit
        
        for (int i = 0 ; i < this.container.getChildCount() ; i++) {
            if (this.container.getChild(i).getName().equals(
            		this.getView().getProductGroupName())) {
                this.getView().enableOK(false);
                return;
            }
        }
        
        // we can create a storage unit
        this.getView().enableOK(true);
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add product group view.
	 */
	@Override
	public void addProductGroup() {
		try {
            // create the storage unit
            final Category category = 
                    newCategory(this.getView().getProductGroupName());
            String  newSupply = this.getView().getSupplyValue();
    		SizeUnits newSize = this.getView().getSupplyUnit();
            Quantity newQuantity = new Quantity(Float.parseFloat(newSupply),
                    UnitsConverter.sizeUnitsToUnits(newSize));
            category.set3MonthSupplyQuantity(newQuantity);
            // add the category to the selected container
            Container selectedContainer = (Container) this.container.getTag();
            selectedContainer.add(category);
            
        } catch (HITException ex) {
            ExceptionHandler.TO_USER.reportException(ex, 
                    "Unable To Create Product Group");
        }
	}

}

