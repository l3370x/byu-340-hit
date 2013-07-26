package core.model;

import core.model.exception.HITException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The {@code CategoryAsContainableTests} class tests the operations of the
 * {@link Category} class as a "containable" (contained by another Category
 * instance).
 * 
 * @author kmcqueen
 */
public class CategoryAsContainableTests extends CategoryAsContainerTests {

    @Test (expected = HITException.class)
    public void testCategoryNotTransferable() throws HITException {
        Category fromA = this.createContainer("Soup");
        Category toB = this.createContainer("Vegetables");
        Category content = this.createContent("Vegetable Soup");
        
        try {
            this.addContainableToContainer(fromA, content);
        } catch (HITException ex) {
            fail(ex.getMessage());
        }
        
        content.transfer(fromA, toB);
    }
    
    @Override
    protected Category createContainer(Object arg) {
        return CategoryAsContainerTests.constructCategory(arg);
    }

    @Override
    protected Category createContent(Object arg) {
        return this.createContainer(arg);
    }
    
}