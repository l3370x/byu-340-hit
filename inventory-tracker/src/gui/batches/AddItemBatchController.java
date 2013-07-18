package gui.batches;


import java.util.ArrayList;
import java.util.Calendar;

import core.model.*;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import static core.model.Item.Factory.newItem;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
<<<<<<< HEAD
	IAddItemBatchController {

    private ProductContainerData source;
    private StorageUnit targetUnit;
    private ArrayList<ProductData> ProductsView = new ArrayList<ProductData>();
    private ArrayList<ItemData> ItemsView = new ArrayList<ItemData>();

    /**
     * Constructor.
     * 
     * @param view
     *            Reference to the add item batch view.
     * @param target
     *            Reference to the storage unit to which items are being added.
     */
    public AddItemBatchController(IView view, ProductContainerData target) {
	super(view);
	source = target;
	construct();
=======
        IAddItemBatchController {

    private ProductContainerData source;
    private StorageUnit targetUnit;
    private ArrayList<ProductData> ProductsView = new ArrayList<>();
    private ArrayList<ItemData> ItemsView = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param view Reference to the add item batch view.
     * @param target Reference to the storage unit to which items are being added.
     */
    public AddItemBatchController(IView view, ProductContainerData target) {
        super(view);
        source = target;
        construct();
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
    }

    /**
     * Returns a reference to the view for this controller.
     */
    @Override
    protected IAddItemBatchView getView() {
<<<<<<< HEAD
	return (IAddItemBatchView) super.getView();
=======
        return (IAddItemBatchView) super.getView();
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
    }

    /**
     * Loads data into the controller's view.
<<<<<<< HEAD
     * 
     * {@pre None}
     * 
     * {@post The controller has loaded data into its view}
     */
    @Override
    protected void loadValues() {
	this.getView().setCount("1");

	// Just to test
	// this.getView().displayInformationMessage(source.getName());
=======
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
        this.getView().setCount("1");

        //Just to test
        // this.getView().displayInformationMessage(source.getName());


>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380

    }

    /**
<<<<<<< HEAD
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
	this.getView().enableItemAction(false);
	this.getView().enableUndo(false);
	this.getView().enableRedo(false);
    }

    /**
     * This method is called when the "Entry Date" field in the add item batch
     * view is changed by the user.
=======
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
     * This method is called when the "Entry Date" field in the add item batch view is changed by
     * the user.
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
     */
    @Override
    public void entryDateChanged() {
    }

    /**
<<<<<<< HEAD
     * This method is called when the "Count" field in the add item batch view
     * is changed by the user.
=======
     * This method is called when the "Count" field in the add item batch view is changed by the
     * user.
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
     */
    @Override
    public void countChanged() {
    }

    /**
<<<<<<< HEAD
     * This method is called when the "Product Barcode" field in the add item
     * batch view is changed by the user.
=======
     * This method is called when the "Product Barcode" field in the add item batch view is changed
     * by the user.
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
     */
    @Override
    public void barcodeChanged() {

<<<<<<< HEAD
	if (!this.getView().getUseScanner()) {
	    if (this.getView().getBarcode() != "") {
		this.getView().enableItemAction(true);
	    }
	}
    }

    /**
     * This method is called when the "Use Barcode Scanner" setting in the add
     * item batch view is changed by the user.
=======
        if (!this.getView().getUseScanner()) {
            if (this.getView().getBarcode() != null && 
                    !this.getView().getBarcode().isEmpty()) {
                this.getView().enableItemAction(true);

            }

        }

    }

    /**
     * This method is called when the "Use Barcode Scanner" setting in the add item batch view is
     * changed by the user.
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
     */
    @Override
    public void useScannerChanged() {
    }

    /**
<<<<<<< HEAD
     * This method is called when the selected product changes in the add item
     * batch view.
     */
    @Override
    public void selectedProductChanged() {
	// Display the items
    }

    /**
     * This method is called when the user clicks the "Add Item" button in the
     * add item batch view.
=======
     * This method is called when the selected product changes in the add item batch view.
     */
    @Override
    public void selectedProductChanged() {
        //Display the items
    }

    /**
     * This method is called when the user clicks the "Add Item" button in the add item batch view.
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
     */
    @Override
    public void addItem() {

<<<<<<< HEAD
	// First check if barcode exists.

	// Get the Storage unit Name from the view
	ProductContainer tag = (ProductContainer) source.getTag();
	StorageUnit unit = tag.getStorageUnit();
	Product prod = unit
		.getProduct(new BarCode(this.getView().getBarcode()));

	if (prod == null) {
	    this.getView().displayAddProductView();
	    StorageUnit unit1 = tag.getStorageUnit();

	    if (unit1.getProduct(new BarCode(this.getView().getBarcode())) != null) {
		this.getView().displayInformationMessage("FOund product");

		prod = unit1
			.getProduct(new BarCode(this.getView().getBarcode()));
	    } else {
		this.getView().enableItemAction(false);
		return;
	    }
	}

	Calendar expiryDate = Calendar.getInstance();
	expiryDate.setTime(this.getView().getEntryDate());
	expiryDate.add(Calendar.MONTH, prod.getShelfLifeInMonths());

	try {
	    Item itemtoadd = new Item.Factory().newItem(prod, this.getView()
		    .getEntryDate(), expiryDate.getTime());
	    unit.addItem(itemtoadd);

	} catch (HITException e) {
	    // TODO Auto-generated catch block
	    this.getView().displayErrorMessage(e.getMessage());
	}

	ProductsView.add(new ProductData(prod));
	this.getView().setProducts((ProductData[]) ProductsView.toArray());

	// Update the Main View
	// Also show the product in the list of Products as well as add the item
	// in the list of items
	// for this session, so that once the user clicks on the product, he can
	// also see the list of items added
    }

    /**
     * This method is called when the user clicks the "Redo" button in the add
     * item batch view.
=======
        //First check if barcode exists. 

        //Get the Storage unit Name from the view
        ProductContainer tag = (ProductContainer) source.getTag();
        StorageUnit unit = tag.getStorageUnit();
        final BarCode barCodeFor;
        try {
            barCodeFor = BarCode.getBarCodeFor(this.getView().getBarcode());
        } catch (HITException ex) {
            ExceptionHandler.TO_USER.reportException(ex, "Can not create bar code");
            return;
        }
        
        Product prod = unit.getProduct(barCodeFor);

        if (prod == null) {
            this.getView().displayAddProductView();
            StorageUnit unit1 = tag.getStorageUnit();

            if (unit1.getProduct(barCodeFor) != null) {
                this.getView().displayInformationMessage("FOund product");


                prod = unit1.getProduct(barCodeFor);
            } else {
                this.getView().enableItemAction(false);
                return;
            }
        }


        Calendar expiryDate = Calendar.getInstance();
        expiryDate.setTime(this.getView().getEntryDate());
        expiryDate.add(Calendar.MONTH, prod.getShelfLifeInMonths());

        try {
            Item itemtoadd = newItem(prod, this.getView().getEntryDate(), expiryDate.getTime());
            unit.addItem(itemtoadd);

        } catch (HITException e) {
            // TODO Auto-generated catch block
            this.getView().displayErrorMessage(e.getMessage());
        }

        ProductsView.add(new ProductData(prod));
        this.getView().setProducts((ProductData[]) ProductsView.toArray());




        //Update the Main View
        //Also show the product in the list of Products as well as add the item in the list of items
        //for this session, so that once the user clicks on the product, he can also see the list of items added
    }

    /**
     * This method is called when the user clicks the "Redo" button in the add item batch view.
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
     */
    @Override
    public void redo() {
    }

    /**
<<<<<<< HEAD
     * This method is called when the user clicks the "Undo" button in the add
     * item batch view.
=======
     * This method is called when the user clicks the "Undo" button in the add item batch view.
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
     */
    @Override
    public void undo() {
    }

    /**
<<<<<<< HEAD
     * This method is called when the user clicks the "Done" button in the add
     * item batch view.
     */
    @Override
    public void done() {
	getView().close();
    }

=======
     * This method is called when the user clicks the "Done" button in the add item batch view.
     */
    @Override
    public void done() {
        getView().close();
    }
>>>>>>> 7544444f8bbd91e2a38a71dae1adbff6eab42380
}
