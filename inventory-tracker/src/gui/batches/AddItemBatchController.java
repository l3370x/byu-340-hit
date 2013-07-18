package gui.batches;

import java.util.Calendar;

import core.model.*;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import static core.model.InventoryManager.Factory.getInventoryManager;
import static core.model.Item.Factory.newItem;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
        IAddItemBatchController {

    private ProductContainerData source;
    private final Set<ProductData> addedProducts = new HashSet<>();
    private final Map<Product, Set<Item>> addedItemsByProduct = new HashMap<>();

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
    }

    /**
     * Returns a reference to the view for this controller.
     */
    @Override
    protected IAddItemBatchView getView() {
        return (IAddItemBatchView) super.getView();
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
        this.getView().setCount("1");

        //Just to test
        // this.getView().displayInformationMessage(source.getName());



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
     * This method is called when the "Entry Date" field in the add item batch view is changed by
     * the user.
     */
    @Override
    public void entryDateChanged() {
    }

    /**
     * This method is called when the "Count" field in the add item batch view is changed by the
     * user.
     */
    @Override
    public void countChanged() {
    }
    
    private Timer timer = new Timer();

    /**
     * This method is called when the "Product Barcode" field in the add item batch view is changed
     * by the user.
     */
    @Override
    public void barcodeChanged() {
        if (!this.getView().getUseScanner()) {
            if (this.getView().getBarcode() != null
                    && !this.getView().getBarcode().isEmpty()) {
                this.getView().enableItemAction(true);
            }
        }

        /*
        if (this.getView().getUseScanner()) {
            this.ensureProductExists();
        } else {
            timer.cancel();
            timer.schedule(new TimerTask(){

                @Override
                public void run() {
                    ensureProductExists();
                }
            }, 11000);
        }
        */
    }
    
    /*
    private void ensureProductExists() {
            final BarCode barcode = BarCode.getBarCodeFor(this.getView().getBarcode());

            // see if the product exists in the InventoryManager
            Product product = getInventoryManager().getProduct(barcode);

            if (product == null) {
                // prompt the user to create/add the product
                this.getView().displayAddProductView();

                // see if the product was added
                product = getInventoryManager().getProduct(barcode);

                // if the product still doesn't exist, there's nothing more we can do
                if (null == product) {
                    this.getView().enableItemAction(false);
                    return;
                }
            }
    }
    */

    /**
     * This method is called when the "Use Barcode Scanner" setting in the add item batch view is
     * changed by the user.
     */
    @Override
    public void useScannerChanged() {
    }

    /**
     * This method is called when the selected product changes in the add item batch view.
     */
    @Override
    public void selectedProductChanged() {
        ProductData productData = this.getView().getSelectedProduct();
        if (null == productData) {
            return;
        }
        
        Object tag = productData.getTag();
        if (false == tag instanceof Product) {
            return;
        }
        
        Set<Item> addedItems = this.addedItemsByProduct.get((Product) tag);
        if (null == addedItems) {
            return;
        }
        
        List<ItemData> itemList = new ArrayList<>();
        for (Item item : addedItems) {
            itemList.add(new ItemData(item));
        }
        
        this.getView().setItems(itemList.toArray(new ItemData[0]));
    }

    /**
     * This method is called when the user clicks the "Add Item" button in the add item batch view.
     */
    @Override
    public void addItem() {
        try {
            // TODO this code actually belongs in the barcodeChanged() method
            final BarCode barcode = BarCode.getBarCodeFor(this.getView().getBarcode());

            // see if the product exists in the InventoryManager
            Product product = getInventoryManager().getProduct(barcode);

            if (product == null) {
                // prompt the user to create/add the product
                this.getView().displayAddProductView();

                // see if the product was added
                product = getInventoryManager().getProduct(barcode);

                // if the product still doesn't exist, there's nothing more we can do
                if (null == product) {
                    this.getView().enableItemAction(false);
                    return;
                }
            }
            
            Set<Item> addedItems = this.addedItemsByProduct.get(product);
            if (null == addedItems) {
                addedItems = new HashSet<>();
                this.addedItemsByProduct.put(product, addedItems);
            }

            int count = Integer.valueOf(this.getView().getCount());
            for (int i = 0; i < count; i++) {
                // generate data for the Item
                Calendar expiryDate = Calendar.getInstance();
                expiryDate.setTime(this.getView().getEntryDate());
                expiryDate.add(Calendar.MONTH, product.getShelfLifeInMonths());

                // create the item
                Item itemtoadd = newItem(product, this.getView().getEntryDate(), expiryDate.getTime());

                // add the item
                ProductContainer tag = (ProductContainer) source.getTag();
                tag.getStorageUnit().addItem(itemtoadd);
                addedItems.add(itemtoadd);
            }

            // I'm not sure this is right
            final ProductData productData = new ProductData(product);
            this.addedProducts.add(productData);
            this.getView().setProducts(this.addedProducts.toArray(new ProductData[0]));
        } catch (HITException e) {
            ExceptionHandler.TO_USER.reportException(e, "Unable To Add Item(s)");
        }
    }

    /**
     * This method is called when the user clicks the "Redo" button in the add item batch view.
     */
    @Override
    public void redo() {
    }

    /**
     * This method is called when the user clicks the "Undo" button in the add item batch view.
     */
    @Override
    public void undo() {
    }

    /**
     * This method is called when the user clicks the "Done" button in the add item batch view.
     */
    @Override
    public void done() {
        getView().close();
    }
}
