package common;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

/**
 * The {@code UndoSupport} class provides support for executing commands with the feature of being
 * able to undo previously-executed commands, or redoing previously undone commands.
 */
public class UndoSupport {
    private final Deque<Command> redoStack;
    private final Deque<Command> undoStack;

    public UndoSupport() {
        this.redoStack = new ArrayDeque<>();
        this.undoStack = new ArrayDeque<>();
    }

    /**
     * Execute the given command and make it ready to be undone.
     *
     * @param command the command to be executed
     */
    public void execute(Command command) {
        command.execute();

        this.undoStack.push(command);
    }

    /**
     * Undo the command that was last executed and make it ready to be redone.
     */
    public void undo() {
        if (false == this.canUndo()) {
            return;
        }

        Command undoable = this.undoStack.pop();
        undoable.undo();

        this.redoStack.push(undoable);
    }

    /**
     * Redo the command that was last undone and make it ready to be undone again.
     */
    public void redo() {
        if (false == this.canRedo()) {
            return;
        }

        Command redoable = this.redoStack.pop();

        this.execute(redoable);
    }

    /**
     * Indicates whether this undo support has a command that can be undone.
     *
     * @return {@code true} if there an undoable command is available, {@code false} otherwise.
     */
    public boolean canUndo() {
        return false == this.undoStack.isEmpty();
    }


    /**
     * Indicates whether this undo support has a command that can be redone.
     *
     * @return {@code true} if there is a redoable command available, {@code false} otherwise
     */
    public boolean canRedo() {
        return false == this.redoStack.isEmpty();
    }
}
