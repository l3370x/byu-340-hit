package gui.productgroup;


import core.model.Category;
import core.model.Quantity;
import core.model.Quantity.Units;
import gui.common.*;
import gui.inventory.*;
import core.model.Container;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;

/**
 * Controller class for the edit product group view.
 */
public class EditProductGroupController extends Controller
        implements IEditProductGroupController {

    private final ProductContainerData target;

    /**
     * Constructor.
     *
     * @param view Reference to edit product group view
     * @param target Product group being edited
     */
    public EditProductGroupController(IView view, ProductContainerData target) {
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
    protected IEditProductGroupView getView() {
        return (IEditProductGroupView) super.getView();
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
        final IEditProductGroupView view = this.getView();
        final String name = this.target.getName();
        final Category category = (Category) this.target.getTag();
        final String supply = String.valueOf(category.get3MonthSupplyQuantity().getValue());
        SizeUnits size;
        try {
            size = UnitController.unitsToSizeUnits(category.get3MonthSupplyQuantity().getUnits());
        } catch (HITException e) {
            size = SizeUnits.Count;
        }
        view.setProductGroupName(name);
        view.setSupplyUnit(size);
        view.setSupplyValue(supply);
    }

    //
    // IEditProductGroupController overrides
    //
    /**
     * This method is called when any of the fields in the edit product group view is changed by the
     * user.
     */
    @Override
    public void valuesChanged() {
        // if the name is null or the name is empty, then we can't create a 
        // Product Group
        String name = this.getView().getProductGroupName();
        if (null == name || name.isEmpty()) {
            this.getView().enableOK(false);
            return;
        }

        // if the name matches an existing container, then we can't
        // create a container
        for (Category c : ((Category) target.getTag()).getContainer().getContents()) {
            if (c.getName().equals(this.getView().getProductGroupName())) {
                this.getView().enableOK(false);
                return;
            }
        }

        // we can create a storage unit
        this.getView().enableOK(true);
    }

    /**
     * This method is called when the user clicks the "OK" button in the edit product group view.
     */
    @Override
    public void editProductGroup() {
        String newName = this.getView().getProductGroupName();
        String newSupply = this.getView().getSupplyValue();
        SizeUnits newSize = this.getView().getSupplyUnit();
        try {
            // get the StorageUnit "tag" from the data
            final Category category = (Category) this.target.getTag();

            // get the category's container
            Container container = category.getContainer();

            // remove the storage unit from its container
            container.remove(category);

            // set the category's new info
            category.setName(newName);

            Quantity newQuantity = new Quantity(Integer.getInteger(newSupply), 
                    Units.valueOf(newSize.toString()));
            // TODO check this
            category.set3MonthSupplyQuantity(newQuantity);

            // put the storage unit back into the container
            container.add(category);

        } catch (HITException ex) {
            ExceptionHandler.TO_USER.reportException(ex,
                    "Unable To Edit Product Group");
        }
    }
}
