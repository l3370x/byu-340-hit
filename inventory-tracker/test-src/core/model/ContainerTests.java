package core.model;

import core.model.exception.HITException;
import org.junit.Test;

/**
 * The {@code ContainerTests} class contains unit tests for the 
 * {@link Container} interface.  The {@link TestContainer} class is used as a 
 * simple implementation of the container.  The {@link TestContainable} class is 
 * used as a simple implementation of the container's content.
 * 
 * @author kmcqueen
 */
public class ContainerTests extends AbstractContainmentTests<TestContainer, TestContainable> {
    @Test (expected = HITException.class)
    public void testAddNullContent() throws HITException {
        TestContainer container = this.createContainer("Container");
        
        this.addContainableToContainer(container, null);
    }
    
    @Test (expected = HITException.class)
    public void testRemoveNullContent() throws HITException {
        TestContainer container = this.createContainer("Container");
        
        this.removeContainableFromContainer(container, null);
    }

    @Override
    protected void doAddContentToContainer(
            TestContainer container, TestContainable content) throws HITException {
        container.add(content);
    }

    @Override
    protected void doRemoveContentFromContainer(
            TestContainer container, TestContainable content) throws HITException {
        container.remove(content);
    }

    @Override
    protected TestContainer createContainer(Object arg) {
        return new TestContainer();
    }

    @Override
    protected TestContainable createContent(Object arg) {
        return new TestContainable(null != arg);
    }
}