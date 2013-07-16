package gui.storageunit;

import core.model.InventoryManager;
import static core.model.InventoryManager.Factory.getInventoryManager;
import static core.model.StorageUnit.Factory.newStorageUnit;
import core.model.StorageUnit;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import gui.common.*;

/**
 * Controller class for the add storage unit view.
 */
public class AddStorageUnitController extends Controller implements
		IAddStorageUnitController {
	
	/**
	 * Constructor.
	 * 
	 * @param view Reference to add storage unit view
	 */
	public AddStorageUnitController(IView view) {
		super(view);
		
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
	protected IAddStorageUnitView getView() {
		return (IAddStorageUnitView)super.getView();
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
	}

	//
	// IAddStorageUnitController overrides
	//

	/**
	 * This method is called when any of the fields in the
	 * add storage unit view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
            // if the name is null or the name is empty, then we can't create a 
            // storage unit
            String name = this.getView().getStorageUnitName();
            if (null == name || name.isEmpty()) {
                this.getView().enableOK(false);
                return;
            }

            // if the name matches an existing storage unit, then we can't
            // create a storage unit
            InventoryManager manager = getInventoryManager();
            for (StorageUnit unit : manager.getContents()) {
                if (name.equals(unit.getName())) {
                    this.getView().enableOK(false);
                    return;
                }
            }
            
            // we can create a storage unit
            this.getView().enableOK(true);
	}
	
	/**
	 * This method is called when the user clicks the "OK"
	 * button in the add storage unit view.
	 */
	@Override
	public void addStorageUnit() {
            try {
                // create the storage unit
                final StorageUnit storageUnit = 
                        newStorageUnit(this.getView().getStorageUnitName());
                
                // add the storage unit to the inventory manager
                getInventoryManager().add(storageUnit);
            } catch (HITException ex) {
                ExceptionHandler.TO_USER.reportException(ex, 
                        "Unable To Create Storage Unit");
            }
	}

}

