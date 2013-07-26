package core.model;

import static org.junit.Assert.*;

/**
 *
 * @author kmcqueen
 */
public enum ContainmentAssertion {
    ASSERT_CONTAINER_CONTAINS_CONTENT_TRUE {
        @Override
        public void _assert(Container container, Containable content, int oldSize) {
            assertTrue(container.contains(content));
        }
    },
    
    ASSERT_CONTAINER_CONTAINS_CONTENT_FALSE {
        @Override
        public void _assert(Container container, Containable content, int oldSize) {
            assertFalse(container.contains(content));
        }
    },
    
    ASSERT_CONTAINER_EQUALS_CONTENT_CONTAINER_TRUE {
        @Override
        public void _assert(Container container, Containable content, int oldSize) {
            assertEquals(container, content.getContainer());
        }
    },
    
    ASSERT_CONTAINER_EQUALS_CONTENT_CONTAINER_FALSE {
        @Override
        public void _assert(Container container, Containable content, int oldSize) {
            assertNotSame(container, content.getContainer());
        }
    },
    
    ASSERT_CONTAINER_SIZE_INCREMENTED {
        @Override
        public void _assert(Container container, Containable content, int oldSize) {
            assertEquals(oldSize + 1, container.size());
        }
    },
    
    ASSERT_CONTAINER_SIZE_DECREMENTED {
        @Override
        public void _assert(Container container, Containable content, int oldSize) {
            assertEquals(oldSize - 1, container.size());
        }
    },
    
    ASSERT_CONTAINER_CONTENTS_INCLUDES_CONTENT_TRUE {
        @Override
        public void _assert(Container container, Containable content, int oldSize) {
            AbstractContainmentTests.checkContents(container, content, true);
        }
    },
    
    ASSERT_CONTAINER_CONTENTS_INCLUDES_CONTENT_FALSE {
        @Override
        public void _assert(Container container, Containable content, int oldSize) {
            AbstractContainmentTests.checkContents(container, content, false);
        }
    };
    
    public void _assert(Container container, Containable content, int oldSize) {
        fail();
    }
}
