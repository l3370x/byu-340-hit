package core.model;

/**
 * The {@code ModelNotification} class is used to encapsulate information about 
 * a change to the model.  The notification consists of two (2) parts:
 * <ol>
 * <li>The type of the change</li>
 * <li>The payload of the change</li>
 * </ol>
 * <p>
 * For instance, if content was added to a container, the change type will be 
 * {@link ChangeType#CONTENT_ADDED} and the payload will be the content object
 * that was added.
 * 
 * @author kmcqueen
 */
public class ModelNotification {
    private final ChangeType changeType;
    private final Object payload;
    
    public ModelNotification(ChangeType changeType, Object payload) {
        this.changeType = changeType;
        this.payload = payload;
    }
    
    public ChangeType getChangeType() {
        return this.changeType;
    }
    
    public Object getPayload() {
        return this.payload;
    }
    
    /**
     * The {@code ChangeType} enumeration identifies the various types of 
     * changes that can occur in the model.
     */
    public static enum ChangeType {
        /**
         * Indicates that content was added.  The payload will be the content
         * that was added.
         */
        CONTENT_ADDED,
        
        /**
         * Indicates that content was removed.  The payload will be the content
         * that was removed.
         */
        CONTENT_REMOVED,
        
        /**
         * Indicates that one of the content objects was updated.  The payload
         * will be the content object that was updated.
         */
        //CONTENT_UPDATED,
        
        /**
         * Indicates that a {@link Product} was added.  The payload will be the
         * added product.
         */
        PRODUCT_ADDED,
        
        /**
         * Indicates that a {@link Product} was removed.  The payload will be
         * the removed product.
         */
        PRODUCT_REMOVED,
        
        /**
         * Indicates that a {@link Product} was updated.  The payload will be
         * the updated product.
         */
        //PRODUCT_UPDATED,

        /**
         * Indicates that an {@link Item} was added.  The payload will be the
         * added item.
         */
        ITEM_ADDED,
        
        /**
         * Indicates that an {@link Item} was removed.  The payload will be
         * the removed item.
         */
        ITEM_REMOVED,
        
        /**
         * Indicates that an {@link Item} was updated.  The payload will be
         * the updated item.
         */
        //ITEM_UPDATED;
    }
}
