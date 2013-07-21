package common;

/**
 *
 * @author kmcqueen
 */
public interface Visitable {
    <V extends Visitable> Iterable<V> getNextToBeVisited();
}
