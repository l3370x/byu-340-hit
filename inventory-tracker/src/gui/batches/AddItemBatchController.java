package gui.batches;

import java.util.Calendar;

import core.model.*;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import static core.model.InventoryManager.Factory.getInventoryManager;
import static core.model.Item.Factory.newItem;
import static core.model.BarCode.*;
import core.model.exception.HITException.Severity;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;
import javax.swing.Timer;

/**
 * Controller class for the add item batch view.
 */
public class AddItemBatchController extends Controller implements
        IAddItemBatchController {

    private static final int TIMER_DELAY = 500;
    private static final Pattern POSITIVE_INTEGER_PATTERN = Pattern.compile("[1-9]+0*");
    private Timer timer;
    private ProductContainerData source;
    private final CopyOnWriteArrayList<Product> addedProducts = new CopyOnWriteArrayList<>();
    private final Map<Product, List<Item>> addedItemsByProduct = new HashMap<>();

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
        // default to use the scanner
        this.getView().setUseScanner(true);
        this.useScannerChanged();

        // prepare the form for entry
        this.prepareForEntry();
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
        this.getView().setUseScanner(true);
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
        if (this.getView().getUseScanner()) {
            return;
        }
        
        this.getView().enableItemAction(this.countIsValid(this.getView().getCount()));
    }

    /**
     * This method is called when the "Product Barcode" field in the add item batch view is changed
     * by the user.
     */
    @Override
    public void barcodeChanged() {
        if (false == this.getView().getUseScanner()) {
            return;
        }
        
        String barcode = this.getView().getBarcode();
        if (null == barcode || barcode.isEmpty()) {
            return;
        }

        if (this.timer.isRunning()) {
            this.timer.restart();
        } else {
            this.timer.start();
        }
    }

    /**
     * This method is called when the "Use Barcode Scanner" setting in the add item batch view is
     * changed by the user.
     */
    @Override
    public void useScannerChanged() {
        if (this.getView().getUseScanner()) {
            // when using the scanner, items are added immediately and automatically
            this.getView().enableItemAction(false);

            // start the timer
            this.initTimer();
        } else {
            // when not using the scanner, the user will have to click the "Add Item" button
            this.getView().enableItemAction(true);

            // stop the timer
            this.timer.stop();
        }
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

        List<Item> addedItems = this.addedItemsByProduct.get((Product) tag);
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
            // get the bar code
            final BarCode barcode = getBarCodeFor(this.getView().getBarcode());

            // get the product
            Product product = getInventoryManager().getProduct(barcode);

            // if the product doesn't exist, then...
            if (product == null) {
                // prompt the user to create/add the product
                this.getView().displayAddProductView();

                // see if the product was added
                product = getInventoryManager().getProduct(barcode);

                // if the product *still* doesn't exist, then there's nothing more we can do here
                if (null == product) {
                    return;
                }
            }

            List<Item> addedItems = this.addedItemsByProduct.get(product);
            if (null == addedItems) {
                addedItems = new ArrayList<>();
                this.addedItemsByProduct.put(product, addedItems);
            }
            
            String countVal = this.getView().getCount();
            if (false == this.countIsValid(countVal)) {
                throw new HITException(Severity.ERROR, "Invalid value for count (" + countVal + 
                        "): must be a numeric value greater than 0.");
            }
            
            Object tag = this.source.getTag();
            if (false == tag instanceof StorageUnit) {
                throw new HITException(Severity.ERROR, 
                        "Items may only be added when a storage unit is selected");
            }
            
            // get the target container for the items (the target is the actual container where the 
            // product is located within the storage unit
            ProductContainer target = product.getProductContainer((StorageUnit) tag);
            if (null == target) {
                target = (StorageUnit) tag;
            }

            // create "count" items
            int count = Integer.valueOf(countVal);
            for (int i = 0; i < count; i++) {
                // generate data for the Item
                Calendar expiryDate = Calendar.getInstance();
                expiryDate.setTime(this.getView().getEntryDate());
                expiryDate.add(Calendar.MONTH, product.getShelfLifeInMonths());

                // create the item
                Item itemtoadd = newItem(product,
                        this.getView().getEntryDate(), expiryDate.getTime());

                // add the item
                target.addItem(itemtoadd);
                addedItems.add(itemtoadd);
            }

            // update the products pane
            this.updateProductsPane(product);
       
        } catch (HITException e) {
            ExceptionHandler.TO_USER.reportException(e, "Unable To Add Item(s)");
        } finally {
            // re-initialize entry view
            this.prepareForEntry();
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

        List<Item> allAddedItems = new ArrayList<>();
        for (List<Item> addedItems : this.addedItemsByProduct.values()) {
            allAddedItems.addAll(addedItems);
        }

        ItemLabelController.createDocument(allAddedItems.toArray(new Item[allAddedItems.size()]));
    }

    private void initTimer() {
        if (null == this.timer) {
            this.timer = new Timer(TIMER_DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addItem();
                }
            });
            this.timer.setRepeats(false);
        }
    }

    private void updateProductsPane(Product product) {
        // add the product to the list if it hasn't been already
        this.addedProducts.addIfAbsent(product);

        ProductContainer container = (ProductContainer) this.source.getTag();

        // create the product data instances
        ProductData selected = null;
        List<ProductData> productList = new ArrayList<>();
        for (Product p : this.addedProducts) {
            ProductData data = new ProductData(p);
            data.setCount(Integer.toString(container.getItemCount(p)));
            productList.add(data);
            if (p == product) {
                selected = data;
            }
        }

        // display the products in the view
        this.getView().setProducts(productList.toArray(new ProductData[productList.size()]));

        // select the just-added product
        if (null != selected) {
            this.getView().selectProduct(selected);
            this.selectedProductChanged();
        }
    }

    private void prepareForEntry() {
        // the count should default to 1
        this.getView().setCount("1");

        // the entry date should default to "now"
        this.getView().setEntryDate(new Date());

        // give focus to the barcode field
        this.getView().giveBarcodeFocus();

        // if using a scanner, clear the barcode field
        if (this.getView().getUseScanner()) {
            this.getView().setBarcode("");
        }
    }

    private boolean countIsValid(String count) {
        return POSITIVE_INTEGER_PATTERN.matcher(
                count).matches();
    }
}
