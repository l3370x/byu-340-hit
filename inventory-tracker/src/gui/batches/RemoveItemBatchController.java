package gui.batches;

import static core.model.InventoryManager.Factory.getInventoryManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.swing.Timer;

import core.model.BarCode;
import core.model.Item;
import core.model.Product;
import core.model.ProductContainer;
import core.model.exception.HITException;
import gui.common.*;
import gui.inventory.ProductContainerData;
import gui.item.ItemData;
import gui.product.*;

/**
 * Controller class for the remove item batch view.
 */
public class RemoveItemBatchController extends Controller implements
        IRemoveItemBatchController {
	
	  private static final int TIMER_DELAY = 1000;
	  private Timer timer;
	  private final CopyOnWriteArrayList<Product> removedProducts = new CopyOnWriteArrayList<>();
	  private final Map<Product, List<Item>> removedItemsByProduct = new HashMap<>();



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
    	
        ProductData productData = this.getView().getSelectedProduct();
        if (null == productData) {
            return;
        }
        
        Object tag = productData.getTag();
        if (false == tag instanceof Product) {
            return;
        }
        
        List<Item> removedItems = this.removedItemsByProduct.get((Product) tag);
        if (null == removedItems) {
            return;
        }
        
        List<ItemData> itemList = new ArrayList<ItemData>();
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
		final BarCode barcode = BarCode.getBarCodeFor(this.getView().getBarcode());
    	Item item = getInventoryManager().itemByBarcode(barcode);

    	try {
//    		ProductContainer container = (ProductContainer) this.source.getTag();
            
    		getInventoryManager().removeItem(item);
			//Update Views
		       this.updateProductsPane(item);
		       this.loadValues();
		       this.enableComponents();
			
		} catch (HITException e) {
			this.getView().displayErrorMessage("Could Not Remove Item: "+e.getMessage());
		}
    }

    private void updateProductsPane(Item item) {
    	Product product=item.getProduct();
        // add the product to the list if it hasn't been already
        this.removedProducts.addIfAbsent(product);
        if(this.removedItemsByProduct.containsKey(product))
        {
          List<Item> list = this.removedItemsByProduct.get(product);
          list.add(item);
      	this.removedItemsByProduct.put(product, list);     
        }
        else
        {
      	  List<Item> list = new ArrayList<Item>();
      	 list.add(item);
     	this.removedItemsByProduct.put(product, list);     
        }
        
       
        
  //      ProductContainer container = (ProductContainer) this.source.getTag();
        
        // create the product data instances
        ProductData selected = null;
        List<ProductData> productList = new ArrayList<>();
        for (Product p : this.removedProducts) {
            ProductData data = new ProductData(p);
            data.setCount(Integer.toString(this.removedItemsByProduct.get(p).size()));
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
