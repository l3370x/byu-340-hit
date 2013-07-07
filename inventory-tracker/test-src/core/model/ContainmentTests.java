package core.model;

import core.model.exception.HITException;
import static core.model.ContainmentAssertion.*;
import static org.junit.Assert.*;

/**
 * 
 * @author kmcqueen
 */
public abstract class ContainmentTests {
    
    protected void addContainableToContainer(TestContainable content, 
            TestContainer container) throws HITException {
        // get the size of the container before doing the add
        int oldSize = container.size();

        // now add the content to the container
        this.doAddContentToContainer(content, container);
        
        // make sure the content is in the container
        this.assertContentInContainer(content, container, oldSize);
    }
    
    protected void removeContainableFromContainer(TestContainable content, 
            TestContainer container) throws HITException {
        // get the size of the container before doing the remove
        int oldSize = container.size();
        
        // now remove the content from the container
        this.doRemoveContentFromContainer(content, container);
        
        // make sure the content is NOT in the container
        this.assertContentNotInContainer(content, container, oldSize);
    }
    
    protected abstract void doAddContentToContainer(TestContainable content, 
            TestContainer container) throws HITException;
    
    protected abstract void doRemoveContentFromContainer(TestContainable content,
            TestContainer container) throws HITException;
    
    static void checkContents(Container container, 
            Containable content, boolean shouldBeInContents) {
        for (Object containable : container.getContents()) {
            if (containable == content) {
                if (shouldBeInContents) {
                    return;
                }
                
                fail();
            }
        }
        
        if (shouldBeInContents) {
            fail();
        }
    }

    protected void assertContentInContainer(TestContainable content, 
            TestContainer container, int oldSize) {
        this._assert(container, content, oldSize, 
                ASSERT_CONTAINER_SIZE_INCREMENTED,
                ASSERT_CONTAINER_CONTAINS_CONTENT_TRUE,
                ASSERT_CONTAINER_EQUALS_CONTENT_CONTAINER_TRUE,
                ASSERT_CONTENT_IS_CONTAINED_IN_CONTAINER_TRUE,
                ASSERT_CONTAINER_CONTENTS_INCLUDES_CONTENT_TRUE);
    }

    protected void assertContentNotInContainer(TestContainable content, 
            TestContainer container, int oldSize) {
        this._assert(container, content, oldSize, 
                ASSERT_CONTAINER_SIZE_DECREMENTED,
                ASSERT_CONTAINER_CONTAINS_CONTENT_FALSE,
                ASSERT_CONTAINER_EQUALS_CONTENT_CONTAINER_FALSE,
                ASSERT_CONTENT_IS_CONTAINED_IN_CONTAINER_FALSE,
                ASSERT_CONTAINER_CONTENTS_INCLUDES_CONTENT_FALSE);
    }
    
    protected void _assert(TestContainer container, TestContainable content, 
            int oldSize, ContainmentAssertion ... assertions) {
        for (ContainmentAssertion assertion : assertions) {
            assertion._assert(container, content, oldSize);
        }
    }
}