package gui.product;

import core.model.exception.ExceptionHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * The {@code ProductDetector} interface defines the contract for an object that can return a
 * product description given a product barcode.
 *
 * @author Keith McQueen
 */
public interface ProductDetector {
    /**
     * The default value of the description if the product description could not be found.
     */
    String NOT_FOUND = "";

    /**
     * Get the product description for the given barcode.
     *
     *
     * @param barcode the barcode representing the product whose description should be returned
     *
     * @return the description of the product represented by the given barcode, if found, otherwise
     * an empty string
     */
    ProductDescriptor getProductDescription(String barcode);

    public static class Factory {
        private static ProductDetector detector;

        public static ProductDetector getProductDetector() {
            if (null == detector) {
                initDetector();
            }

            return detector;
        }

        private static void initDetector() {
            try {
                // create the properties object
                Properties properties = new Properties();

                // load the properties from the .product-detectors file
                File pluginsFile = new File(System.getProperty("user.dir"), ".product-detectors");
                properties.load(new FileInputStream(pluginsFile));

                // for each key in the file, create a new ProductDetectorCOR and add it to the chain
                ProductDetectorCOR head = new NullProductDetector();
                for (String detectorName : properties.stringPropertyNames()) {
                    head = new ProductDetectorCOR(detectorName, head);
                }

                detector = head;
            } catch (IOException e) {
                ExceptionHandler.TO_LOG.reportException(e,
                        "Unable to load ProductDetector plug-ins");

                detector = new NullProductDetector();
            }
        }
    }
}
