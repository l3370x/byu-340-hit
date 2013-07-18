package gui.batches;

import static core.model.InventoryManager.Factory.getInventoryManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import core.model.BarCode;
import core.model.Item;
import core.model.Product;
import gui.common.*;
import gui.product.*;

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
        IRemoveItemBatchController {
	
	  private static final int TIMER_DELAY = 1000;
	  private Timer timer;


    /**
     * Constructor.
     *
     * @param view Reference to the remove item batch view.
     */
    public RemoveItemBatchController(IView view) {
        super(view);

        construct();
    }

    /**
     * Returns a reference to the view for this controller.
     */
    @Override
    protected IRemoveItemBatchView getView() {
        return (IRemoveItemBatchView) super.getView();
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
        this.getView().setUseScanner(false);
    	 this.useScannerChanged();
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
    	 this.getView().enableItemAction(false);
         this.getView().enableUndo(false);
         this.getView().enableRedo(false);
    }

    /**
     * This method is called when the "Item Barcode" field is changed in the remove item batch view
     * by the user.
     */
    @Override
    public void barcodeChanged() {
    	if (this.getView().getUseScanner()) {
            this.ensureItemExists();
        } else {
            if (this.timer.isRunning()) {
                this.timer.restart();
            } else {
                this.timer.start();
            }
        }
    }
    

	private void ensureItemExists() {
		final BarCode barcode = BarCode.getBarCodeFor(this.getView().getBarcode());
	
	    // see if the Item exists in the InventoryManager
		Item item = getInventoryManager().itemByBarcode(barcode);

		if (item == null) {
			// prompt the user to create/add the product
			this.getView().displayErrorMessage("Item Does not exist.");
			
			this.getView().setBarcode("");
			enableComponents();
			}
		else
		{
			this.getView().enableItemAction(true);

		}
			}


    /**
     * This method is called when the "Use Barcode Scanner" setting is changed in the remove item
     * batch view by the user.
     */
    @Override
    public void useScannerChanged() {
        if (this.getView().getUseScanner()) {
            this.timer.stop();
            this.getView().enableItemAction(false);
        } else {
            this.initTimer();
        }
    }
    
    private void initTimer() {
        if (null == this.timer) {
            this.timer = new Timer(TIMER_DELAY, new ActionListener(){
                @Override
                public void actionPerformed(ActionEvent e) {
                    ensureItemExists();
                }
            });
            this.timer.setRepeats(false);
        }
    }

    /**
     * This method is called when the selected product changes in the remove item batch view.
     */
    @Override
    public void selectedProductChanged() {
    }

    /**
     * This method is called when the user clicks the "Remove Item" button in the remove item batch
     * view.
     */
    @Override
    public void removeItem() {
    }

    /**
     * This method is called when the user clicks the "Redo" button in the remove item batch view.
     */
    @Override
    public void redo() {
    }

    /**
     * This method is called when the user clicks the "Undo" button in the remove item batch view.
     */
    @Override
    public void undo() {
    }

    /**
     * This method is called when the user clicks the "Done" button in the remove item batch view.
     */
    @Override
    public void done() {
        getView().close();
    }
}
