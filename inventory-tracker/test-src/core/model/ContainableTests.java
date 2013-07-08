package core.model;

import core.model.exception.HITException;
import org.junit.Test;

/**
 * The {@code ContainableTests} class contains unit tests for the 
 * {@link Containable} interface.  The {@link TestContainer} class is used as a 
 * simple implementation of the container.  The {@link TestContainable} class is 
 * used as a simple implementation of the container's content.
 * 
 * @author kmcqueen
 */
public class ContainableTests extends AbstractContainmentTests<TestContainer, TestContainable> {
    @Test
    public void testTransferFromContainedToNotContained() throws HITException {
        TestContainer fromA = this.createContainer("From");
        TestContainer toB = this.createContainer("To");
        TestContainable content = this.createContent("Content");
        
        // put the content in A
        this.addContainableToContainer(fromA, content);
        
        int oldASize = fromA.size();
        int oldBSize = toB.size();
        
        // transfer the content from A to B
        content.transfer(fromA, toB);
        
        // make sure the content is NOT in A
        this.assertContentNotInContainer(fromA, content, oldASize);
        
        // make sure the content IS in B
        this.assertContentInContainer(toB, content, oldBSize);
    }
    
    @Test
    public void testTransferFromContainedToContained() throws HITException {
        TestContainer fromA = this.createContainer("From");
        TestContainer toB = fromA;
        TestContainable content = this.createContent("Content");
        
        // put the content in A
        this.addContainableToContainer(fromA, content);
        
        int oldASize = fromA.size();
        int oldBSize = 0;
        
        // transfer the content from A to B
        content.transfer(fromA, toB);
        
        // make sure the content IS in B
        this.assertContentInContainer(toB, content, oldBSize);
    }
    
    @Test (expected = HITException.class)
    public void testTransferFromNotContainedToContained() throws HITException {
        TestContainer fromA = this.createContainer("From");
        TestContainer toB = this.createContainer("To");
        TestContainable content = this.createContent("Content");
        
        // put the content in B
        this.addContainableToContainer(toB, content);
        
        content.transfer(fromA, toB);
    }
    
    @Test (expected = HITException.class)
    public void testTransferFromNotContainedtoNotContained() throws HITException {
        TestContainer fromA = this.createContainer("From");
        TestContainer toB = this.createContainer("To");
        TestContainable content = this.createContent("Content");
        
        content.transfer(fromA, toB);
    }
    
    @Test (expected = HITException.class)
    public void testAddContentToNullContainer() throws HITException {
        TestContainable content = this.createContent("Content");
        
        this.doAddContentToContainer(null, content);
    }
    
    @Test (expected = HITException.class)
    public void testRemoveContentFromNullContainer() throws HITException {
        TestContainable content = this.createContent("Content");
        
        this.doRemoveContentFromContainer(null, content);
    }
    
    @Test (expected = HITException.class)
    public void testTransferFromNullToNonNull() throws HITException {
        TestContainer fromA = null;
        TestContainer toB = this.createContainer("To");
        TestContainable content = this.createContent("Content");
        
        content.transfer(fromA, toB);
    }

    @Test (expected = HITException.class)
    public void testTransferFromNullToNull() throws HITException {
        TestContainer fromA = null;
        TestContainer toB = null;
        TestContainable content = this.createContent("Content");
        
        content.transfer(fromA, toB);
    }

    @Test (expected = HITException.class)
    public void testTransferFromNonNullToNonNull() throws HITException {
        TestContainer fromA = this.createContainer("From");
        TestContainer toB = null;
        TestContainable content = this.createContent("Content");
        
        content.transfer(fromA, toB);
    }

    @Override
    protected void doAddContentToContainer(TestContainer container, TestContainable content) throws HITException {
        content.putIn(container);
    }

    @Override
    protected void doRemoveContentFromContainer(TestContainer container, TestContainable content) throws HITException {
        content.removeFrom(container);
    }

    @Override
    protected TestContainer createContainer(Object arg) {
        return new TestContainer();
    }

    @Override
    protected TestContainable createContent(Object arg) {
        return new TestContainable(arg != null);
    }
    
}