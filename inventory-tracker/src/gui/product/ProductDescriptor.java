package gui.product;

/**
 */
public class ProductDescriptor {
    private final String description;
    private final String barcode;

    public ProductDescriptor(String barcode, String description) {
        this.barcode = barcode;
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public String getBarcode() {
        return this.barcode;
    }
}
