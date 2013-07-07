package core.model;

import core.model.exception.HITException;
import core.model.exception.Severity;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * The {@code ContainerTests} class contains unit tests for the {@link Container}
 * interface.  The {@link TestContainer} class is used as a simple implementation
 * of the container.  The {@link TestContainable} class is used as a simple 
 * implementation of the container's content.
 * 
 * @author kmcqueen
 */
public class ContainerTests extends ContainmentTests {

    @Test
    public void testAddWithAddableContainable() throws HITException {
        TestContainer container = new TestContainer();
        TestContainable containable = new TestContainable(true);

        this.addContainableToContainer(containable, container);
    }
    
    @Test
    public void testAddWithAlreadyAddedContent() {
        TestContainer container = new TestContainer();
        TestContainable content = new TestContainable(true);
        
        try {
            this.addContainableToContainer(content, container);
            this.addContainableToContainer(content, container);
        } catch (HITException e) {
            assertEquals(Severity.INFO, e.getSeverity());
        }
    }

    @Test(expected = HITException.class)
    public void testAddWithNonAddableContainable() throws HITException {
        TestContainer container = new TestContainer();
        TestContainable containable = new TestContainable(false);

        try {
            this.addContainableToContainer(containable, container);
        } catch (HITException e) {
            // we are expecting an exception with severity error
            if (Severity.ERROR == e.getSeverity()) {
                throw e;
            } else {
                fail();
            }
        }
    }
    
    @Test
    public void testRemoveContainable() throws HITException {
        TestContainer container = new TestContainer();
        TestContainable content = new TestContainable(true);
        
        // we have to add the content first
        this.addContainableToContainer(content, container);
        
        // now remove the content from the container
        this.removeContainableFromContainer(content, container);
    }
    
    @Test (expected = HITException.class)
    public void testRemoveWithUncontainedContainable() throws HITException {
        TestContainer container = new TestContainer();
        TestContainable content = new TestContainable(true);
        
        // remove the content without having added it first
        this.removeContainableFromContainer(content, container);
    }

    @Override
    protected void doAddContentToContainer(TestContainable content, 
        TestContainer container) throws HITException {
        container.add(content);
    }

    @Override
    protected void doRemoveContentFromContainer(TestContainable content, 
        TestContainer container) throws HITException {
        container.remove(content);
    }
}