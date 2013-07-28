package gui.batches;

import common.Command;
import common.UndoSupport;
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
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import gui.common.*;
import gui.item.ItemData;
import gui.product.*;
import java.util.Date;

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
        IRemoveItemBatchController {

    private static final int TIMER_DELAY = 500;
    private Timer timer;
    private final CopyOnWriteArrayList<Product> removedProducts = new CopyOnWriteArrayList<>();
    private final Map<Product, List<Item>> removedItemsByProduct = new HashMap<>();
    
    private final UndoSupport undoSupport = new UndoSupport();

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
        this.getView().setBarcode("");
        this.getView().setUseScanner(false);
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
        this.getView().enableUndo(this.undoSupport.canUndo());
        this.getView().enableRedo(this.undoSupport.canRedo());
    }

    /**
     * This method is called when the "Item Barcode" field is changed in the remove item batch view
     * by the user.
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
     * This method is called when the "Use Barcode Scanner" setting is changed in the remove item
     * batch view by the user.
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
                    removeItem();
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

        ProductData productData = this.getView().getSelectedProduct();
        if (null == productData) {
            this.getView().setItems(new ItemData[0]);
            return;
        }

        Object tag = productData.getTag();
        if (false == tag instanceof Product) {
            this.getView().setItems(new ItemData[0]);
            return;
        }

        List<Item> removedItems = this.removedItemsByProduct.get((Product) tag);
        if (null == removedItems) {
            this.getView().setItems(new ItemData[0]);
            return;
        }

        List<ItemData> itemList = new ArrayList<>();
        for (Item item : removedItems) {
            itemList.add(new ItemData(item));
        }
        this.getView().setItems(itemList.toArray(new ItemData[itemList.size()]));

    }

    /**
     * This method is called when the user clicks the "Remove Item" button in the remove item batch
     * view.
     */
    @Override
    public void removeItem() {
        if (this.ensureItemExists() == false) {
            return;
        }

        final BarCode barcode = BarCode.getBarCodeFor(this.getView().getBarcode());
        Item item = getInventoryManager().getItem(barcode);

        Command command = new RemoveItemCommand(item);
        this.undoSupport.execute(command);

        //Update Views
        this.loadValues();
        this.enableComponents();
    }

    private void updateProductsPane(Item item) {
        // create the product data instances
        ProductData selected = null;
        List<ProductData> productList = new ArrayList<>();
        for (Product p : this.removedProducts) {
            ProductData data = new ProductData(p);
            data.setCount(Integer.toString(this.removedItemsByProduct.get(p).size()));
            productList.add(data);
            if (null != item && p == item.getProduct()) {
                selected = data;
            }
        }

        // display the products in the view
        this.getView().setProducts(productList.toArray(new ProductData[productList.size()]));

        // select the just-added product
        if (null != selected) {
            this.getView().selectProduct(selected);
        }
        this.selectedProductChanged();
    }

    /**
     * This method is called when the user clicks the "Redo" button in the remove item batch view.
     */
    @Override
    public void redo() {
        this.undoSupport.redo();
        this.enableComponents();
    }

    /**
     * This method is called when the user clicks the "Undo" button in the remove item batch view.
     */
    @Override
    public void undo() {
        this.undoSupport.undo();
        this.enableComponents();
    }

    /**
     * This method is called when the user clicks the "Done" button in the remove item batch view.
     */
    @Override
    public void done() {
        getView().close();
    }
    
    private class RemoveItemCommand implements Command {
        private final ProductContainer container;
        private final Product product;
        private final Item item;
        
        public RemoveItemCommand(Item item) {
            this.container = item.getContainer();
            this.product = item.getProduct();
            this.item = item;
        }
        
        @Override
        public void execute() {
            try {
                RemoveItemBatchController _this = RemoveItemBatchController.this;
                
                // add the item's product to the list of removed products (if necessary)
                _this.removedProducts.addIfAbsent(this.product);
                
                // get (or create) the list of removed items
                List<Item> removedItems = _this.removedItemsByProduct.get(this.product);
                if (null == removedItems) {
                    removedItems = new ArrayList<>();
                    _this.removedItemsByProduct.put(this.product, removedItems);
                }
                
                // add the item to the list of removed items
                removedItems.add(this.item);
                
                // remove the item from its container
                this.container.removeItem(this.item);

                // save the removed item for tracking purposes
                getInventoryManager().saveRemovedItem(this.item);

                // update the view
                _this.updateProductsPane(item);
            } catch (HITException ex) {
                ExceptionHandler.TO_USER.reportException(ex, "Unable To Remove Item");
            }
            
        }

        @Override
        public void undo() {
            try {
                RemoveItemBatchController _this = RemoveItemBatchController.this;
                
                List<Item> removedItems = _this.removedItemsByProduct.get(this.product);
                removedItems.remove(this.item);
                if (removedItems.isEmpty()) {
                    _this.removedItemsByProduct.remove(this.product);
                    _this.removedProducts.remove(this.product);
                }
                
                // put the item back into the container
                this.container.addItem(this.item);
                
                // remove the item from the "removed" items
                getInventoryManager().deleteRemovedItem(this.item);

                // update the view
                _this.updateProductsPane(this.item);
            } catch (HITException ex) {
                ExceptionHandler.TO_USER.reportException(ex, "Unable To Undo Remove Item");
            }
        }
    }
}
