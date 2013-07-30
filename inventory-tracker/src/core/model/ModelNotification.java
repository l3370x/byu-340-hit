package core.model;

/**
 * The {@code ModelNotification} class is used to encapsulate information about a change to the
 * model.  The notification consists of two (3) parts: <ol> <li>The type of the change</li> <li>The
 * container where the change occurred</li> <li>The content that was added/removed/updated</li>
 * </ol>
 * <p/>
 * For instance, if content was added to a container, the change type will be {@link
 * ChangeType#CONTENT_ADDED} and the content will be the content object that was added and the
 * container will be where the content was added.
 *
 * @author kmcqueen
 */
public class ModelNotification {
    private final ChangeType changeType;
    private final Container container;
    private final Containable content;

    public ModelNotification(ChangeType changeType, Container container, Containable content) {
        this.changeType = changeType;
        this.container = container;
        this.content = content;
    }

    public ChangeType getChangeType() {
        return this.changeType;
    }

    public Container getContainer() {
        return this.container;
    }

    public Containable getContent() {
        return this.content;
    }

    /**
     * The {@code ChangeType} enumeration identifies the various types of changes that can occur in
     * the model.
     */
    public static enum ChangeType {
        /**
         * Indicates that content was added.  The content will be the content that was added.
         */
        CONTENT_ADDED,

        /**
         * Indicates that content was removed.  The content will be the content that was removed.
         */
        CONTENT_REMOVED,

        /**
         * Indicates that one of the content objects was updated.  The content will be the content
         * object that was updated.
         */
        CONTENT_UPDATED,

        /**
         * Indicates that a {@link Product} was added.  The content will be the added product.
         */
        PRODUCT_ADDED,

        /**
         * Indicates that a {@link Product} was removed.  The content will be the removed product.
         */
        PRODUCT_REMOVED,

        /**
         * Indicates that a {@link Product} was updated.  The content will be the updated product.
         */
        PRODUCT_UPDATED,

        /**
         * Indicates that an {@link Item} was added.  The content will be the added item.
         */
        ITEM_ADDED,

        /**
         * Indicates that an {@link Item} was removed.  The content will be the removed item.
         */
        ITEM_REMOVED,

        /**
         * Indicates that an {@link Item} was updated.  The content will be the updated item.
         */
        ITEM_UPDATED;
    }
}
