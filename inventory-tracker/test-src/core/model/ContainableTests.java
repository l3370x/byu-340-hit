package core.model;

import core.model.exception.HITException;
import static core.model.ContainmentAssertion.*;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kmcqueen
 */
public class ContainableTests extends ContainerTests {
    @Test
    public void testTransferFromContainedToNotContained() throws HITException {
        TestContainer fromA = new TestContainer();
        TestContainer toB = new TestContainer();
        TestContainable content = new TestContainable(true);
        
        // put the content in A
        this.addContainableToContainer(content, fromA);
        
        int oldASize = fromA.size();
        int oldBSize = toB.size();
        
        // transfer the content from A to B
        content.transfer(fromA, toB);
        
        // make sure the content is NOT in A
        this.assertContentNotInContainer(content, fromA, oldASize);
        
        // make sure the content IS in B
        this.assertContentInContainer(content, toB, oldBSize);
    }
    
    @Test
    public void testTransferFromContainedToContained() throws HITException {
        TestContainer fromA = new TestContainer();
        TestContainer toB = fromA;
        TestContainable content = new TestContainable(true);
        
        // put the content in A
        this.addContainableToContainer(content, fromA);
        
        int oldASize = fromA.size();
        int oldBSize = 0;
        
        // transfer the content from A to B
        content.transfer(fromA, toB);
        
        // make sure the content IS in B
        this.assertContentInContainer(content, toB, oldBSize);
    }
    
    @Test (expected = HITException.class)
    public void testTransferFromNotContainedToContained() throws HITException {
        TestContainer fromA = new TestContainer();
        TestContainer toB = new TestContainer();
        TestContainable content = new TestContainable(true);
        
        // put the content in B
        this.addContainableToContainer(content, toB);
        
        content.transfer(fromA, toB);
    }
    
    @Test (expected = HITException.class)
    public void testTransferFromNotContainedtoNotContained() throws HITException {
        TestContainer fromA = new TestContainer();
        TestContainer toB = new TestContainer();
        TestContainable content = new TestContainable(true);
        
        content.transfer(fromA, toB);
    }

    @Override
    protected void doAddContentToContainer(TestContainable content, 
        TestContainer container) throws HITException {
        content.putIn(container);
    }

    @Override
    protected void doRemoveContentFromContainer(TestContainable content, 
        TestContainer container) throws HITException {
        content.removeFrom(container);
    }
    
}