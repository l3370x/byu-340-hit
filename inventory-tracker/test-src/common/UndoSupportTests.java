package common;

import org.junit.Test;

import static junit.framework.Assert.*;

public class UndoSupportTests {

    @Test
    public void testUndo() {
        UndoSupport support = new UndoSupport();

        final boolean[] value = new boolean[] { false };

        support.execute(new Command() {
            @Override
            public void execute() {
                value[0] = true;
            }

            @Override
            public void undo() {
                value[0] = false;
            }
        });

        assertEquals(true, value[0]);
        assertTrue(support.canUndo());
        assertFalse(support.canRedo());

        support.undo();

        assertEquals(false, value[0]);
        assertFalse(support.canUndo());
        assertTrue(support.canRedo());

        support.redo();

        assertEquals(true, value[0]);
        assertTrue(support.canUndo());
        assertFalse(support.canRedo());
    }
}
