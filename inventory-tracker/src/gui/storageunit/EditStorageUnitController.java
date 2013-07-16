package gui.storageunit;

import core.model.InventoryManager;
import static core.model.InventoryManager.Factory.getInventoryManager;
import core.model.StorageUnit;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import gui.common.*;
import gui.inventory.*;

/**
 * Controller class for the edit storage unit view.
 */
public class EditStorageUnitController extends Controller
        implements IEditStorageUnitController {

    private final ProductContainerData target;

    /**
     * Constructor.
     *
     * @param view Reference to edit storage unit view
     * @param target The storage unit being edited
     */
    public EditStorageUnitController(IView view, ProductContainerData target) {
        super(view);

        this.target = target;

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
    protected IEditStorageUnitView getView() {
        return (IEditStorageUnitView) super.getView();
    }

    /**
     * Sets the enable/disable state of all components in the controller's view.
     * A component should be enabled only if the user is currently allowed to
     * interact with that component.
     *
     * {
     *
     * @pre None}
     *
     * {
     * @post The enable/disable state of all components in the controller's view
     * have been set appropriately.}
     */
    @Override
    protected void enableComponents() {
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
        final IEditStorageUnitView view = this.getView();
        final String name = this.target.getName();
        view.setStorageUnitName(name);
    }

    //
    // IEditStorageUnitController overrides
    //
    /**
     * This method is called when any of the fields in the edit storage unit
     * view is changed by the user.
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
     * This method is called when the user clicks the "OK" button in the edit
     * storage unit view.
     */
    @Override
    public void editStorageUnit() {
        String newName = this.getView().getStorageUnitName();
        try {
            // get the StorageUnit "tag" from the data
            final StorageUnit unit = (StorageUnit) this.target.getTag();
            
            // get the storage unit's container
            InventoryManager container = unit.getContainer();
            
            // remove the storage unit from its container
            container.remove(unit);
            
            // set the unit's new name
            unit.setName(newName);
            
            // put the storage unit back into the container
            container.add(unit);
            
        } catch (HITException ex) {
            ExceptionHandler.TO_USER.reportException(ex, 
                    "Unable To Edit Storage Unit");
        }
    }
}
