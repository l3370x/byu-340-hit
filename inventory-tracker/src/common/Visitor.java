package common;

/**
 * 
 * @author kmcqueen
 * @param <T> the type of visitable object that can be visited by this visitor
 */
public interface Visitor<T extends Visitable> {
    boolean visit(T visitable);
}
