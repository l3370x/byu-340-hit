package gui.item;

import java.util.Calendar;
import java.util.Date;
import java.util.Observable;

import common.util.DateUtils;
import core.model.InventoryManager;
import core.model.Item.Factory;
import core.model.ModelNotification;
import core.model.Product;
import static core.model.InventoryManager.Factory.getInventoryManager;
import core.model.Item;
import core.model.exception.HITException;
import gui.common.*;

/**
 * Controller class for the edit item view.
 */
public class EditItemController extends Controller implements
		IEditItemController {

	ItemData item;

	/**
	 * Constructor.
	 * 
	 * @param view
	 *            Reference to edit item view
	 * @param target
	 *            Item that is being edited
	 */
	public EditItemController(IView view, ItemData target) {
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
	protected IEditItemView getView() {
		return (IEditItemView) super.getView();
	}

	/**
	 * Sets the enable/disable state of all components in the controller's view.
	 * A component should be enabled only if the user is currently allowed to
	 * interact with that component.
	 * 
	 * {@pre None}
	 * 
	 * {@post The enable/disable state of all components in the controller's
	 * view have been set appropriately.}
	 */
	@Override
	protected void enableComponents() {
		IEditItemView view = getView();
		view.enableDescription(false);
		view.enableBarcode(false);
		view.enableEntryDate(true);
		view.enableOK(true);
	}

	/**
	 * Loads data into the controller's view.
	 * 
	 * {@pre None}
	 * 
	 * {@post The controller has loaded data into its view}
	 */
	@Override
	protected void loadValues() {
		IEditItemView view = getView();
		Item ourItem = (Item) item.getTag();
		view.setDescription(ourItem.getProduct().getDescription());
		view.setBarcode(ourItem.getBarCode().getValue());
		view.setEntryDate(ourItem.getEntryDate());
	}

	//
	// IEditItemController overrides
	//

	/**
	 * This method is called when any of the fields in the edit item view is
	 * changed by the user.
	 */
	@Override
	public void valuesChanged() {
		IEditItemView view = getView();
		Date viewDate = view.getEntryDate();
		if (viewDate.before(DateUtils.earliestDate())
				|| viewDate.after(DateUtils.currentDate())) {
			view.enableOK(false);
		} else
			view.enableOK(true);
	}

	/**
	 * This method is called when the user clicks the "OK" button in the edit
	 * item view.
	 */
	@Override
	public void editItem() {
		Item item = (Item) this.item.getTag();
		item.setEntryDate(this.getView().getEntryDate());
		if (item instanceof Observable) {
			((Observable) item).notifyObservers(new ModelNotification(
					ModelNotification.ChangeType.ITEM_UPDATED, item
							.getContainer(), item));
		}
	}

}
