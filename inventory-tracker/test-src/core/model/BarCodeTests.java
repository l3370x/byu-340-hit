package core.model;

import core.model.exception.HITException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The {@code BarCodeTests} class provides unit tests for the {@link BarCode}
 * class.
 * 
 * @author kmcqueen
 */
public class BarCodeTests {
    @Test
    public void testGenerateBarCode() throws HITException {
        for (int i = 0; i < 100; i++) {
            BarCode.generateItemBarCode();
        }
    }
}