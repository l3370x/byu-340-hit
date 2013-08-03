package gui.product;

import junit.framework.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 */
public class UPCDatabaseDotOrgTests {
    @Test
    public void testGetProductInfo() throws IOException {
        //ProductDetector detector = ProductDetector.Factory.getProductDetector();

        UPCDatabaseDotOrg detector = new UPCDatabaseDotOrg();
        ProductDescriptor descriptor = detector.getProductDescription("0111222333446");

        Assert.assertNotNull(descriptor);
        Assert.assertEquals("UPC Database Testing Code", descriptor.getDescription());
        Assert.assertEquals("0111222333446", descriptor.getBarcode());
    }
}
