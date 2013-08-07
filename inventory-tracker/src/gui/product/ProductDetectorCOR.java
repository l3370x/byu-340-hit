package gui.product;

import core.model.exception.ExceptionHandler;

/**
 * The {@code ProductDetectorCOR} class is used to wrap a {@link ProductDetector} instance for use
 * in a chain of responsibility pattern.
 */
class ProductDetectorCOR implements ProductDetector {
    private final String detectorClassName;
    private final ProductDetectorCOR next;
    private ProductDetector detector;

    public ProductDetectorCOR(String detectorClassName, ProductDetectorCOR next) {
        this.detectorClassName = detectorClassName;
        this.next = next;
    }

    @Override
    public ProductDescriptor getProductDescription(String barcode) {
        if (null == detector) {
            this.initDetector();
        }

        System.out.println("Trying detector: " + this.detectorClassName);

        ProductDescriptor descriptor = this.detector.getProductDescription(barcode);
        if (null != descriptor.getDescription() &&
                false == descriptor.getDescription().isEmpty()) {
            return descriptor;
        }

        return this.next.getProductDescription(barcode);
    }

    private void initDetector() {
        try {
            Class<?> detectorClass = Class.forName(this.detectorClassName);
            this.detector = (ProductDetector) detectorClass.newInstance();
        } catch (Exception e) {
            ExceptionHandler.TO_LOG.reportException(e,
                    "Unable to create ProductDetector instance");
            this.detector = new NullProductDetector();
        }
    }
}
