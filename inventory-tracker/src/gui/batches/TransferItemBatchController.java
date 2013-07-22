package gui.batches;

import static core.model.InventoryManager.Factory.getInventoryManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;

import core.model.BarCode;
import core.model.Item;
import core.model.Product;
import core.model.ProductContainer;
import core.model.StorageUnit;
import core.model.exception.HITException;
import core.model.exception.HITException.Severity;
import gui.common.*;
import gui.inventory.*;
import gui.item.ItemData;
import gui.product.*;

/**
 * Controller class for the transfer item batch view.
 */
public class TransferItemBatchController extends Controller implements
        ITransferItemBatchController {

    private ProductContainerData source;
    private static final int TIMER_DELAY = 1000;
    private Timer timer;
    private final CopyOnWriteArrayList<Product> transferedProducts = new CopyOnWriteArrayList<>();
    private final Map<Product, List<Item>> transferedItemsByProduct = new HashMap<>();

    /**
     * Constructor.
     *
     * @param view Reference to the transfer item batch view.
     * @param target Reference to the storage unit to which items are being transferred.
     */
    public TransferItemBatchController(IView view, ProductContainerData target) {
        super(view);
        source = target;
        construct();
    }

    /**
     * Returns a reference to the view for this controller.
     */
    @Override
    protected ITransferItemBatchView getView() {
        return (ITransferItemBatchView) super.getView();
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

        // give focus to the barcode field
        this.getView().giveBarcodeFocus();

        // if using a scanner, clear the barcode field
        if (this.getView().getUseScanner()) {
            this.getView().setBarcode("");
        }

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
     * This method is called when the "Item Barcode" field in the transfer item batch view is
     * changed by the user.
     */
    @Override
    public void barcodeChanged() {
        if (false == this.getView().getUseScanner()) {
            this.ensureItemExists();
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

    private boolean ensureItemExists() {
        final BarCode barcode = BarCode.getBarCodeFor(this.getView().getBarcode());
        if (barcode.toString().length() != 12) {
            enableComponents();
            return false;

        }

        // see if the Item exists in the InventoryManager
        Item item = getInventoryManager().getItem(barcode);

        if (item == null) {
            enableComponents();
            return false;
        } else {
            this.getView().enableItemAction(true);
            return true;
        }
    }

    /**
     * This method is called when the "Use Barcode Scanner" setting in the transfer item batch view
     * is changed by the user.
     */
    @Override
    public void useScannerChanged() {
        if (this.getView().getUseScanner()) {
            this.getView().enableItemAction(false);
            this.initTimer();
        } else {
            if (null != this.timer && this.timer.isRunning()) {
                this.timer.stop();
            }
        }
    }

    private void initTimer() {
        if (null == this.timer) {
            this.timer = new Timer(TIMER_DELAY, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    transferItem();
                }
            });
            this.timer.setRepeats(false);
        }
    }

    /**
     * This method is called when the selected product changes in the transfer item batch view.
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

        List<Item> removedItems = this.transferedItemsByProduct.get((Product) tag);
        if (null == removedItems) {
            return;
        }

        List<ItemData> itemList = new ArrayList<>();
        for (Item item : removedItems) {
            itemList.add(new ItemData(item));
        }
        this.getView().setItems(itemList.toArray(new ItemData[itemList.size()]));

    }

    /**
     * This method is called when the user clicks the "Transfer Item" button in the transfer item
     * batch view.
     */
    @Override
    public void transferItem() {
        if (this.ensureItemExists() == false) {
            return;
        }

        final BarCode barcode = BarCode.getBarCodeFor(this.getView().getBarcode());
        Item item = getInventoryManager().getItem(barcode);

        try {

            Object tag = this.source.getTag();
            if (false == tag instanceof StorageUnit) {
                throw new HITException(Severity.ERROR,
                        "Items cannot be transfered without a target Storage Unit");
            }
            ProductContainer targetUnit = (StorageUnit) tag;

            item.transfer(item.getContainer(), targetUnit);


            //Update Views
            this.updateProductsPane(item);
            this.loadValues();
            this.enableComponents();

        } catch (HITException e) {
            this.getView().displayErrorMessage("Could Not Remove Item: " + e.getMessage());
        }
    }

    private void updateProductsPane(Item item) {
        Product product = item.getProduct();
        // add the product to the list if it hasn't been already
        this.transferedProducts.addIfAbsent(product);
        if (this.transferedItemsByProduct.containsKey(product)) {
            List<Item> list = this.transferedItemsByProduct.get(product);
            list.add(item);
            this.transferedItemsByProduct.put(product, list);
        } else {
            List<Item> list = new ArrayList<>();
            list.add(item);
            this.transferedItemsByProduct.put(product, list);
        }

        // create the product data instances
        ProductData selected = null;
        List<ProductData> productList = new ArrayList<>();
        for (Product p : this.transferedProducts) {
            ProductData data = new ProductData(p);
            data.setCount(Integer.toString(this.transferedItemsByProduct.get(p).size()));
            productList.add(data);
            if (p == item.getProduct()) {
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

    /**
     * This method is called when the user clicks the "Redo" button in the transfer item batch view.
     */
    @Override
    public void redo() {
    }

    /**
     * This method is called when the user clicks the "Undo" button in the transfer item batch view.
     */
    @Override
    public void undo() {
    }

    /**
     * This method is called when the user clicks the "Done" button in the transfer item batch view.
     */
    @Override
    public void done() {
        getView().close();
    }
}
