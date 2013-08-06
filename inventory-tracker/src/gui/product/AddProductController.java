package gui.product;

import core.model.BarCode;
import core.model.Product;
import core.model.Quantity;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import gui.common.Controller;
import gui.common.IView;
import gui.common.SizeUnits;
import gui.common.UnitsConverter;

import javax.swing.*;
import java.util.regex.Pattern;

import static core.model.InventoryManager.Factory.getInventoryManager;
import static core.model.Product.Factory.newProduct;
import static gui.product.ProductDetector.Factory.getProductDetector;

/**
 * Controller class for the add item view.
 */
public class AddProductController extends Controller implements
        IAddProductController {

    private final String barcode;
    private static final Pattern POSITIVE_INTEGER_PATTERN =
            Pattern.compile("-?\\d+(\\.\\d+)?");
    private String sizeValue = "0";
    private String shelfLife = "0";
    private String monthSupply = "0";

    /**
     * Constructor.
     *
     * @param view    Reference to the add product view
     * @param barcode Barcode for the product being added
     */
    public AddProductController(IView view, String barcode) {
        super(view);
        this.barcode = barcode;
        construct();
    }

    //
    // Controller overrides
    //

    /**
     * Returns a reference to the view for this controller.
     * <p/>
     * {
     *
     * @pre None}
     * <p/>
     * {
     * @post Returns a reference to the view for this controller.}
     */
    @Override
    protected IAddProductView getView() {
        return (IAddProductView) super.getView();
    }

    /**
     * Sets the enable/disable state of all components in the controller's view. A component should
     * be enabled only if the user is currently allowed to interact with that component.
     * <p/>
     * {
     *
     * @pre None}
     * <p/>
     * {
     * @post The enable/disable state of all components in the controller's view have been set
     * appropriately.}
     */
    @Override
    protected void enableComponents() {
        this.getView().enableBarcode(false);
        this.getView().enableSizeValue(false);
        this.getView().enableOK(false);
    }

    /**
     * Loads data into the controller's view.
     * <p/>
     * {
     *
     * @pre None}
     * <p/>
     * {
     * @post The controller has loaded data into its view}
     */
    @Override
    protected void loadValues() {
        this.getView().setDescription("Identifying Product -- Please Wait...");
        this.getView().enableDescription(false);

        SwingWorker<ProductDescriptor, Void> worker = new SwingWorker<ProductDescriptor, Void>() {
            @Override
            protected ProductDescriptor doInBackground() throws Exception {
                AddProductController _this = AddProductController.this;

                return getProductDetector().getProductDescription(_this.barcode);
            }

            @Override
            protected void done() {
                AddProductController _this = AddProductController.this;

                try {
                    ProductDescriptor descriptor = this.get();
                    _this.getView().setBarcode(descriptor.getBarcode());
                    _this.getView().setDescription(descriptor.getDescription());
                } catch (Exception e) {
                    ExceptionHandler.TO_LOG.reportException(e, "Unable to get product description");
                }

                _this.getView().enableDescription(true);
                _this.valuesChanged();
            }
        };
        worker.execute();

        this.getView().setBarcode(barcode);
        this.getView().setSizeValue("1");
        this.getView().setSizeUnit(SizeUnits.Count);
        this.getView().setShelfLife("0");
        this.getView().setSupply("0");
    }

    //
    // IAddProductController overrides
    //

    /**
     * This method is called when any of the fields in the add product view is changed by the user.
     */
    @Override
    public void valuesChanged() {

        // If size units is Count, disable the size values text field and set it as one
        SizeUnits su = this.getView().getSizeUnit();
        if (su == SizeUnits.Count) {
            this.getView().enableSizeValue(false);
            this.getView().setSizeValue("1");
        } else // Enable Size value textbox and set it to the correct number
        {
            this.getView().enableSizeValue(true);
        }

        //Makes sure that the product has a description
        String description = this.getView().getDescription();
        // Makes sure the size value is a number and not blank
        sizeValue = this.getView().getSizeValue();
        // Makes sure the shelf life is a number and not blank
        shelfLife = this.getView().getShelfLife();
        // Makes sure the 3 month supply is a number and not blank
        monthSupply = this.getView().getSupply();

        if (descriptionIsValid(description) == false
                || productSizeIsValid(sizeValue) == false
                || shelfLifeIsValid(shelfLife) == false
                || monthSupplyIsValid(monthSupply) == false) {
            this.getView().enableOK(false);
            return;
        }

        // If it passes all conditions, enable ok button for adding product
        this.getView().enableOK(true);
    }

    /**
     * This method is called when the user clicks the "OK" button in the add product view.
     */
    @Override
    public void addProduct() {
        try {
            // Make new product
            final Product product =
                    newProduct(BarCode.getBarCodeFor(this.getView().getBarcode()),
                            this.getView().getDescription());

            product.setShelfLifeInMonths(Integer.parseInt(this.getView().getShelfLife()));
            product.set3MonthSupplyQuota(Integer.parseInt(this.getView().getSupply()));
            product.setSize(new Quantity(Float.parseFloat(this.getView().getSizeValue()),
                    UnitsConverter.sizeUnitsToUnits(this.getView().getSizeUnit())));

            // add the product to the inventory manager
            getInventoryManager().addProduct(product);

        } catch (HITException ex) {
            ExceptionHandler.TO_USER.reportException(ex,
                    "Unable To Create Product");
        }
    }

    private boolean productSizeIsValid(String sizeValue) {
        if (null == sizeValue || sizeValue.isEmpty()) {
            return false;
        }

        return POSITIVE_INTEGER_PATTERN.matcher(sizeValue).matches();
    }

    private boolean descriptionIsValid(String description) {
        if (null == description || description.isEmpty()) {
            return false;
        }

        return true;
    }

    private boolean shelfLifeIsValid(String shelfLife) {
        if (null == shelfLife || shelfLife.isEmpty()) {
            return false;
        }

        return POSITIVE_INTEGER_PATTERN.matcher(shelfLife).matches();
    }

    private boolean monthSupplyIsValid(String monthSupply) {
        if (null == monthSupply || monthSupply.isEmpty()) {
            return false;
        }

        return POSITIVE_INTEGER_PATTERN.matcher(monthSupply).matches();
    }

}
