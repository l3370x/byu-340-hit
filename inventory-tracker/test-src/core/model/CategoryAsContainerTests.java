package core.model;

import core.model.exception.HITException;
import static core.model.Category.Factory.newCategory;
import core.model.exception.Severity;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The {@code CategoryAsContainerTests} class tests the operations of the 
 * {@link Category} class where it acts as a container of other Category 
 * instances.
 * 
 * @author kmcqueen
 */
public class CategoryAsContainerTests extends AbstractContainmentTests<Category, Category> {
    @Test
    public void testFactoryMethodWithValidName() throws HITException {
        final String cannedGoodsName = "Canned Goods";
        Category cat = newCategory(cannedGoodsName);
        
        assertNotNull(cat);
        assertEquals(cannedGoodsName, cat.getName());
    }
    
    @Test (expected = HITException.class)
    public void testFactoryMethodWithNullName() throws HITException {
        newCategory(null);
    }
    
    @Test (expected = HITException.class)
    public void testFactoryMethodWithEmptyName() throws HITException {
        newCategory("");
    }
    
    @Test(expected = HITException.class)
    @Override
    public void testAddWithNonAddableContent() throws HITException {
        Category container = this.createContainer("Canned Goods");
        Category content = this.createContent("Soup");
        Category content2 = this.createContent("Soup");
        
        try {
            this.addContainableToContainer(container, content);
            this.addContainableToContainer(container, content2);
        } catch (HITException e) {
            // we are expecting an exception with severity error
            if (Severity.ERROR == e.getSeverity()) {
                throw e;
            } else {
                fail();
            }
        }
    }    

    @Override
    protected void doAddContentToContainer(Category container, Category content) throws HITException {
        container.add(content);
    }

    @Override
    protected void doRemoveContentFromContainer(Category container, Category content) throws HITException {
        container.remove(content);
    }

    @Override
    protected Category createContainer(Object arg) {
        return constructCategory(arg);
    }

    @Override
    protected Category createContent(Object arg) {
        return this.createContainer(arg);
    }

    static Category constructCategory(Object arg) {
        try {
            return Category.Factory.newCategory(String.valueOf(arg));
        } catch (HITException ex) {
            fail(ex.getMessage());
            return null;
        }
    }
}