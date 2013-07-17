package core.model;

import core.model.exception.HITException;
import static core.model.ContainmentAssertion.*;
import core.model.ModelNotification.ChangeType;
import static core.model.ModelNotification.ChangeType.*;
import core.model.exception.HITException.Severity;
import java.util.Observable;
import java.util.Observer;
import static org.junit.Assert.*;
import org.junit.Test;

/**
 * The {@code AbstractContainmentTests} class is used to provide default testing
 * of both {@link Container} and {@link Containable} instances.  It uses 
 * generics along with abstract methods to allow for subclasses to test specific 
 * implementations of these interfaces.
 * 
 * @author kmcqueen
 */
public abstract class AbstractContainmentTests<A extends Container, B extends Containable> {
    
    protected void addContainableToContainer(final A container, final B content) throws HITException {
        // get the size of the container before doing the add
        int oldSize = container.size();
        
        // create an observer
        final TestObserver<A, B> observer = 
                new TestObserver<>(container, content, CONTENT_ADDED);
        
        // add the observer
        container.addObserver(observer);

        // now add the content to the container
        this.doAddContentToContainer(container, content);
        
        // delete the observer
        container.deleteObserver(observer);
        
        // make sure the observer was notified
        assertTrue(observer.wasNotified());
        
        // make sure the content is in the container
        this.assertContentInContainer(container, content, oldSize);
    }
    
    protected void removeContainableFromContainer(A container, B content) throws HITException {
        // get the size of the container before doing the remove
        int oldSize = container.size();
        
        // create an observer
        final TestObserver<A, B> observer = 
                new TestObserver<>(container, content, CONTENT_REMOVED);
        
        // add the observer
        container.addObserver(observer);

        // now remove the content from the container
        this.doRemoveContentFromContainer(container, content);
        
        // delete the observer
        container.deleteObserver(observer);
        
        // make sure the observer was notified
        assertTrue(observer.wasNotified());
        
        // make sure the content is NOT in the container
        this.assertContentNotInContainer(container, content, oldSize);
    }
    
    protected final void doAddContentToContainer(A container, 
            B content) throws HITException {
        container.add(content);
    }
    
    protected final void doRemoveContentFromContainer(A container, 
            B content) throws HITException {
        container.remove(content);
    }
    
    protected abstract A createContainer(Object arg);
    
    protected abstract B createContent(Object arg);
    
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

    protected void assertContentInContainer(A container, B content, int oldSize) {
        this._assert(container, content, oldSize, 
                ASSERT_CONTAINER_SIZE_INCREMENTED,
                ASSERT_CONTAINER_CONTAINS_CONTENT_TRUE,
                ASSERT_CONTAINER_EQUALS_CONTENT_CONTAINER_TRUE,
                ASSERT_CONTAINER_CONTENTS_INCLUDES_CONTENT_TRUE);
    }

    protected void assertContentNotInContainer(A container, B content, int oldSize) {
        this._assert(container, content, oldSize, 
                ASSERT_CONTAINER_SIZE_DECREMENTED,
                ASSERT_CONTAINER_CONTAINS_CONTENT_FALSE,
                ASSERT_CONTAINER_EQUALS_CONTENT_CONTAINER_FALSE,
                ASSERT_CONTAINER_CONTENTS_INCLUDES_CONTENT_FALSE);
    }
    
    protected void _assert(A container, B content, 
            int oldSize, ContainmentAssertion ... assertions) {
        for (ContainmentAssertion assertion : assertions) {
            assertion._assert(container, content, oldSize);
        }
    }

    @Test
    public void testAddWithAddableContent() throws HITException {
        A container = this.createContainer("Container");
        B content = this.createContent("Content");
        this.addContainableToContainer(container, content);
    }

    @Test
    public void testAddWithAlreadyAddedContent() {
        A container = this.createContainer("Container");
        B content = this.createContent("Content");
        try {
            this.addContainableToContainer(container, content);
            this.addContainableToContainer(container, content);
        } catch (HITException e) {
            assertEquals(Severity.INFO, e.getSeverity());
        }
    }

    @Test (expected = HITException.class)
    public void testAddWithNonAddableContent() throws HITException {
        A container = this.createContainer("TestContainer");
        B containable = this.createContent(null);
        try {
            this.addContainableToContainer(container, containable);
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
        A container = this.createContainer("Container");
        B content = this.createContent("Content");
        // we have to add the content first
        this.addContainableToContainer(container, content);
        // now remove the content from the container
        this.removeContainableFromContainer(container, content);
    }

    @Test(expected = HITException.class)
    public void testRemoveWithUncontainedContent() throws HITException {
        A container = this.createContainer("Container");
        B content = this.createContent("Content");
        // remove the content without having added it first
        this.removeContainableFromContainer(container, content);
    }
    
    private static class TestObserver<A extends Container, B extends Containable> implements Observer {
        
        private final ChangeType changeType;
        private final A container;
        private final B content;
        private boolean wasNotified = false;
        
        public TestObserver(A container, B content, ChangeType changeType) {
            this.container = container;
            this.content = content;
            this.changeType = changeType;
        }
        
        @Override
        public void update(Observable o, Object arg) {
            this.wasNotified = true;
            
            assertEquals(container, o);
            assertNotNull(arg);
            assertTrue(arg instanceof ModelNotification);

            ModelNotification notification = (ModelNotification) arg;
            assertEquals(this.changeType, notification.getChangeType());
            assertEquals(content, notification.getContent());
       }
  
        public boolean wasNotified() {
            return this.wasNotified;
        }
    }
}