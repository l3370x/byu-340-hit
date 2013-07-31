package gui.productgroup;

import static core.model.ModelNotification.ChangeType.CONTENT_UPDATED;

import java.util.Observable;

import core.model.Category;
import core.model.ModelNotification;
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
public class EditProductGroupController extends Controller implements
		IEditProductGroupController {

	private final ProductContainerData target;

	/**
	 * Constructor.
	 * 
	 * @param view
	 *            Reference to edit product group view
	 * @param target
	 *            Product group being edited
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
	 *      {
	 * @post Returns a reference to the view for this controller.}
	 */
	@Override
	protected IEditProductGroupView getView() {
		return (IEditProductGroupView) super.getView();
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
	 *      {
	 * @post The enable/disable state of all components in the controller's view
	 *       have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
		this.getView().enableOK(true);
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 * {
	 * 
	 * @pre None}
	 * 
	 *      {
	 * @post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
		final IEditProductGroupView view = this.getView();
		final String name = this.target.getName();
		final Category category = (Category) this.target.getTag();
		final String supply = String.valueOf(category.get3MonthSupplyQuantity()
				.getValue());
		SizeUnits size;
		try {
			size = UnitsConverter.unitsToSizeUnits(category
					.get3MonthSupplyQuantity().getUnits());
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
	
	private boolean isValidName(String name) {
        if (null == name || name.isEmpty())
            return false;
        return true;
    }

    private boolean isValidCount(String count) {
        if (false == count.matches("-?\\d+(\\.\\d+)?") || null == count
                || Float.parseFloat(count) < 0)
            return false;
        return true;
    }
	
	/**
	 * This method is called when any of the fields in the edit product group
	 * view is changed by the user.
	 */
	@Override
	public void valuesChanged() {
		// if the name is null or the name is empty then we
		// can't create a Product Group
		String name = this.getView().getProductGroupName();
		if (!isValidName(name)) {
			this.getView().enableOK(false);
			return;
		}

		// if the count is not a number or is blank, or is negative, don't allow
		// OK
		String count = this.getView().getSupplyValue();
		if (!isValidCount(count)) {
			this.getView().enableOK(false);
			return;
		}

		// if the name matches an existing container, then we can't
		// create a container
		for (Category c : ((Category) target.getTag()).getContainer()
				.getContents()) {
			if (c.getName().equals(this.getView().getProductGroupName())
					&& !c.getName().equals(target.getName())) {
				this.getView().enableOK(false);
				return;
			}
		}

		// if the unit is count, check to make sure the value is an int.
		if (this.getView().getSupplyUnit().equals(SizeUnits.Count)
				&& (Float.parseFloat(count) != Math.round(Float
						.parseFloat(count)))) {
			this.getView().enableOK(false);
			return;
		}

		// we can create a storage unit
		this.getView().enableOK(true);
	}

	/**
	 * This method is called when the user clicks the "OK" button in the edit
	 * product group view.
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

			// set the category's new info
			category.setName(newName);

			// set the new quantity
			Quantity newQuantity = new Quantity(Float.parseFloat(newSupply),
					UnitsConverter.sizeUnitsToUnits(newSize));
			category.set3MonthSupplyQuantity(newQuantity);

			// check for valid quantity
			if (newSupply.compareTo("0") == 0) {
				category.set3MonthSupplyQuantity(new Quantity(0, Units.COUNT));
			}

			// the unit should notify its observers of the change
			if (category instanceof Observable) {
				((Observable) category).notifyObservers(new ModelNotification(
						CONTENT_UPDATED, category.getContainer(), category));
			}

		} catch (HITException ex) {
			ExceptionHandler.TO_USER.reportException(ex,
					"Unable To Edit Product Group");
		}
	}
}
