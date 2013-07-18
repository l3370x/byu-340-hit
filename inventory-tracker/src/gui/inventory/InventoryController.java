package gui.inventory;

import core.model.Category;
import core.model.Containable;
import core.model.Container;
import core.model.InventoryManager;
import gui.common.*;
import gui.item.*;
import gui.product.*;

import static core.model.InventoryManager.Factory.getInventoryManager;
import core.model.Item;
import core.model.ModelNotification;
import core.model.Product;
import core.model.ProductContainer;
import core.model.Quantity;
import core.model.StorageUnit;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;

import java.util.*;

/**
 * Controller class for inventory view.
 */
public class InventoryController extends Controller
        implements IInventoryController, Observer {

    private final Map<ProductContainer, ProductContainerData> dataByContainer = 
            new HashMap<>();
    
    /**
     * Constructor.
     *
     * @param view Reference to the inventory view
     */
    public InventoryController(IInventoryView view) {
        super(view);

        construct();
    }

    /**
     * Returns a reference to the view for this controller.
     */
    @Override
    protected IInventoryView getView() {
        return (IInventoryView) super.getView();
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
        /*
        ProductContainerData root = new ProductContainerData();

        ProductContainerData basementCloset = new ProductContainerData("Basement Closet");

        ProductContainerData toothpaste = new ProductContainerData("Toothpaste");
        toothpaste.addChild(new ProductContainerData("Kids"));
        toothpaste.addChild(new ProductContainerData("Parents"));
        basementCloset.addChild(toothpaste);

        root.addChild(basementCloset);

        ProductContainerData foodStorage = new ProductContainerData("Food Storage Room");

        ProductContainerData soup = new ProductContainerData("Soup");
        soup.addChild(new ProductContainerData("Chicken Noodle"));
        soup.addChild(new ProductContainerData("Split Pea"));
        soup.addChild(new ProductContainerData("Tomato"));
        foodStorage.addChild(soup);

        root.addChild(foodStorage);

        getView().setProductContainers(root);
        */
        
        getInventoryManager().deleteObserver(this);
        
        this.getView().setProductContainers(
                this.createProductContainerData(getInventoryManager()));
        
        getInventoryManager().addObserver(this);
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
     * {
     * @post The enable/disable state of all components in the controller's view
     * have been set appropriately.}
     */
    @Override
    protected void enableComponents() {
    }

    //
    // IInventoryController overrides
    //
    /**
     * Returns true if and only if the "Add Storage Unit" menu item should be
     * enabled.
     */
    @Override
    public boolean canAddStorageUnit() {
        return true;
    }

    /**
     * Returns true if and only if the "Add Items" menu item should be enabled.
     */
    @Override
    public boolean canAddItems() {
        return true;
    }

    /**
     * Returns true if and only if the "Transfer Items" menu item should be
     * enabled.
     */
    @Override
    public boolean canTransferItems() {
        return true;
    }

    /**
     * Returns true if and only if the "Remove Items" menu item should be
     * enabled.
     */
    @Override
    public boolean canRemoveItems() {
        return true;
    }

    /**
     * Returns true if and only if the "Delete Storage Unit" menu item should be
     * enabled.
     */
    @Override
    public boolean canDeleteStorageUnit() {
        ProductContainerData selectedProductContainer = 
                this.getView().getSelectedProductContainer();
        
        return selectedProductContainer.getChildCount() <= 0;
    }

    /**
     * This method is called when the user selects the "Delete Storage Unit"
     * menu item.
     */
    @Override
    public void deleteStorageUnit() {
        ProductContainerData selected = 
                this.getView().getSelectedProductContainer();
        StorageUnit unit = 
                ((ProductContainer) selected.getTag()).getStorageUnit();
        try {
            unit.getContainer().remove(unit);
        } catch (HITException ex) {
            ExceptionHandler.TO_USER.reportException(ex, 
                    "Unable To Delete Storage Unit");
        }
    }

    /**
     * Returns true if and only if the "Edit Storage Unit" menu item should be
     * enabled.
     */
    @Override
    public boolean canEditStorageUnit() {
        return true;
    }

    /**
     * Returns true if and only if the "Add Product Group" menu item should be
     * enabled.
     */
    @Override
    public boolean canAddProductGroup() {
        return true;
    }

    /**
     * Returns true if and only if the "Delete Product Group" menu item should
     * be enabled.
     */
    @Override
    public boolean canDeleteProductGroup() {
        return this.canDeleteStorageUnit();
    }

    /**
     * Returns true if and only if the "Edit Product Group" menu item should be
     * enabled.
     */
    @Override
    public boolean canEditProductGroup() {
        return true;
    }

    /**
     * This method is called when the user selects the "Delete Product Group"
     * menu item.
     */
    @Override
    public void deleteProductGroup() {
        ProductContainerData selected = 
                this.getView().getSelectedProductContainer();
        
        Object tag = selected.getTag();
        if (false == tag instanceof Category) {
            return;
        }
        
        Category category = (Category) tag;
        
        try {
            category.getContainer().remove(category);
        } catch (HITException ex) {
            ExceptionHandler.TO_USER.reportException(ex, 
                    "Unable To Delete Product Group");
        }
    }
    
    /**
     * This method is called when the selected item container changes.
     */
    @Override
    public void productContainerSelectionChanged() {
        updateContextPane(this.getView());
        updateProductsPane(this.getView());
    }

    /**
     * This method is called when the selected item changes.
     */
    @Override
    public void productSelectionChanged() {
        updateItemsPane(this.getView());
    }

    /**
     * This method is called when the selected item changes.
     */
    @Override
    public void itemSelectionChanged() {
    }

    /**
     * Returns true if and only if the "Delete Product" menu item should be
     * enabled.
     */
    @Override
    public boolean canDeleteProduct() {
        return true;
    }

    /**
     * This method is called when the user selects the "Delete Product" menu
     * item.
     */
    @Override
    public void deleteProduct() {
    }

    /**
     * Returns true if and only if the "Edit Item" menu item should be enabled.
     */
    @Override
    public boolean canEditItem() {
        return true;
    }

    /**
     * This method is called when the user selects the "Edit Item" menu item.
     */
    @Override
    public void editItem() {
        getView().displayEditItemView();
    }

    /**
     * Returns true if and only if the "Remove Item" menu item should be
     * enabled.
     */
    @Override
    public boolean canRemoveItem() {
        return true;
    }

    /**
     * This method is called when the user selects the "Remove Item" menu item.
     */
    @Override
    public void removeItem() {
    }

    /**
     * Returns true if and only if the "Edit Product" menu item should be
     * enabled.
     */
    @Override
    public boolean canEditProduct() {
        return true;
    }

    /**
     * This method is called when the user selects the "Add Product Group" menu
     * item.
     */
    @Override
    public void addProductGroup() {
        getView().displayAddProductGroupView();
    }

    /**
     * This method is called when the user selects the "Add Items" menu item.
     */
    @Override
    public void addItems() {
        getView().displayAddItemBatchView();
    }

    /**
     * This method is called when the user selects the "Transfer Items" menu
     * item.
     */
    @Override
    public void transferItems() {
        getView().displayTransferItemBatchView();
    }

    /**
     * This method is called when the user selects the "Remove Items" menu item.
     */
    @Override
    public void removeItems() {
        getView().displayRemoveItemBatchView();
    }

    /**
     * This method is called when the user selects the "Add Storage Unit" menu
     * item.
     */
    @Override
    public void addStorageUnit() {
        getView().displayAddStorageUnitView();
    }

    /**
     * This method is called when the user selects the "Edit Product Group" menu
     * item.
     */
    @Override
    public void editProductGroup() {
        getView().displayEditProductGroupView();
    }

    /**
     * This method is called when the user selects the "Edit Storage Unit" menu
     * item.
     */
    @Override
    public void editStorageUnit() {
        getView().displayEditStorageUnitView();
    }

    /**
     * This method is called when the user selects the "Edit Product" menu item.
     */
    @Override
    public void editProduct() {
        getView().displayEditProductView();
    }

    /**
     * This method is called when the user drags a product into a product
     * container.
     *
     * @param productData Product dragged into the target product container
     * @param containerData Target product container
     */
    @Override
    public void addProductToContainer(ProductData productData,
            ProductContainerData containerData) {
    }

    /**
     * This method is called when the user drags an item into a product
     * container.
     *
     * @param itemData Item dragged into the target product container
     * @param containerData Target product container
     */
    @Override
    public void moveItemToContainer(ItemData itemData,
            ProductContainerData containerData) {
    }

    private ProductContainerData createProductContainerData(ProductContainer container) {
        assert null != container;
        
        // create the product container data (associating it with the container)
        ProductContainerData data = new ProductContainerData(container.getName());
        data.setTag(container);
        this.dataByContainer.put(container, data);
        
        for (Containable content : (Iterable<Containable>) container.getContents()) {
            if (content instanceof ProductContainer) {
                data.addChild(this.createProductContainerData((ProductContainer) content));
            }
        }
        
        return data;
    }

    @Override
    public void update(Observable o, Object arg) {
        if (false == arg instanceof ModelNotification) {
            return;
        }
        
        ModelNotification notification = (ModelNotification) arg;
        
        final Container container = notification.getContainer();
        if (false == container instanceof ProductContainer) {
            return;
        }
        
        final Containable content = notification.getContent();
        if (false == content instanceof ProductContainer) {
            return;
        }
        
        switch (notification.getChangeType()) {
            case CONTENT_REMOVED:
                this.contentWasRemoved((ProductContainer) container, 
                        (ProductContainer) content);
                break;
                
            case CONTENT_ADDED:
                this.contentWasAdded((ProductContainer) container, 
                        (ProductContainer) content);
                break;
                
            case CONTENT_UPDATED:
                this.contentWasUpdated((ProductContainer) container, 
                        (ProductContainer) content);
                break;
        }
    }
    
    private void contentWasAdded(ProductContainer parent, ProductContainer child) {
        assert child instanceof Containable;
        
        ProductContainerData parentData = this.dataByContainer.get(parent);
        assert null != parentData;
        
        // figure out where it should be inserted
        int index = parent.indexOf((Containable) child);
        if (index < 0) {
            return;
        }
        
        // create a ProductContaineData for the added content
        ProductContainerData childData = this.createProductContainerData(child);
        
        // add it to the view
        this.getView().insertProductContainer(parentData, childData, index);
        
        // select it in the view
        this.getView().selectProductContainer(childData);
        this.productContainerSelectionChanged();
    }
    
    private void contentWasRemoved(ProductContainer parent, ProductContainer child) {
        // find the ProductContaineData associated with the removed content
        ProductContainerData childData = this.dataByContainer.remove(child);
        if (null == childData) {
            return;
        }
        
        // remove the ProductContaineData from the view
        this.getView().deleteProductContainer(childData);
    }
    
    private void contentWasUpdated(ProductContainer parent, ProductContainer child) {
        assert child instanceof Containable;
        
        // find the ProductContainerData associated with the updated child
        ProductContainerData childData = this.dataByContainer.get(child);
        
        // if it is the name that's different, then do a rename on the 
        // ProductContainerData, otherwise we can ignore the change
        if (false == childData.getName().equals(child.getName())) {
            this.getView().renameProductContainer(childData, child.getName(), 
                    parent.indexOf((Containable) child));
            this.getView().selectProductContainer(childData);
            this.productContainerSelectionChanged();
        }
    }
    
    private static void updateContextPane(IInventoryView view) {
        ProductContainer container = getProductContainer(view);
        ViewUpdater.getUpdaterFor(container).updateContextPane(view, container);
    }
    
    private static void updateProductsPane(IInventoryView view) {
        ProductContainer container = getProductContainer(view);
        if (null == container) {
            return;
        }
        
        List<ProductData> productList = new ArrayList<>();
        
        for (Product product : (Iterable<Product>) container.getProducts()) {
            productList.add(createProductData(product));
        }

        view.setProducts(productList.toArray(new ProductData[productList.size()]));
    }

    private static void updateItemsPane(IInventoryView view) {
        ProductContainer container = getProductContainer(view);
        if (null == container) {
            return;
        }
        
        Product product = getProduct(view);
        if (null == product) {
            return;
        }
        
        List<ItemData> itemList = new ArrayList<>();
        for (Item item : (Iterable<Item>) ((ProductContainer) container).getItems(product)) {
            itemList.add(createItemData(item));
        }

        view.setItems(itemList.toArray(new ItemData[itemList.size()]));
    }
    
    private static ProductContainer getProductContainer(IInventoryView view) {
        if (null == view) {
            return null;
        }
        
        ProductContainerData containerData = view.getSelectedProductContainer();
        if (null == containerData) {
            return null;
        }
        
        Object tag = containerData.getTag();
        if (false == tag instanceof ProductContainer) {
            return null;
        }
        
        return (ProductContainer) tag;
    }
    
    private static Product getProduct(IInventoryView view) {
        if (null == view) {
            return null;
        }
        
        ProductData productData = view.getSelectedProduct();
        if (null == productData) {
            return null;
        }
        
        Object tag = productData.getTag();
        if (false == tag instanceof Product) {
            return null;
        }
        
        return (Product) tag;
    }

    private static ProductData createProductData(Product product) {
        return new ProductData(product);
    }
    
    private static ItemData createItemData(Item item) {
        return new ItemData(item);
    }
    
    private static enum ViewUpdater {
        INVENTORY_MANAGER(InventoryManager.class) {
            @Override
            public void updateContextPane(IInventoryView view, ProductContainer container) {
                super.updateContextPane(view, container);
                view.setContextUnit("ALL");
            }
        },
        STORAGE_UNIT(StorageUnit.class) {
            @Override
            public void updateContextPane(IInventoryView view, ProductContainer container) {
                super.updateContextPane(view, container);
                view.setContextUnit(((ProductContainer) container).getName());
            }
        },
        CATEGORY(Category.class) {
            @Override
            public void updateContextPane(IInventoryView view, ProductContainer container) {
                super.updateContextPane(view, container);
                view.setContextUnit(((ProductContainer) container).getStorageUnit().getName());
                view.setContextGroup(((ProductContainer) container).getName());
                
                final Quantity supply = ((Category) container).get3MonthSupplyQuantity();
                if (null != supply) {
                    view.setContextSupply(supply.toString());
                }
            }
        },
        NULL(Object.class);
        
        private final Class<?> _class;
        
        private ViewUpdater(Class<?> _class) {
            this._class = _class;
        }
        
        public void updateContextPane(IInventoryView view, ProductContainer container) {
            view.setContextUnit("");
            view.setContextGroup("");
            view.setContextSupply("");
        }
        
        public static ViewUpdater getUpdaterFor(ProductContainer container) {
            if (null == container) {
                return ViewUpdater.NULL;
            }
            
            for (ViewUpdater updater : ViewUpdater.values()) {
                if (updater._class.isInstance(container)) {
                    return updater;
                }
            }
            
            return ViewUpdater.NULL;
        }
    }
}
