package gui.product;

/**
 * The {@code NullProductDetector} class implements the {@link ProductDetector} interface, but only
 * returns a {@code null} value for the product description.
 *
 * @author Keith McQueen
*/
class NullProductDetector extends ProductDetectorCOR {
    public NullProductDetector() {
        super(null, null);
    }

    @Override
    public ProductDescriptor getProductDescription(String barcode) {
        return new ProductDescriptor(barcode, NOT_FOUND);
    }
}
