package common;

/**
 * 
 * @author kmcqueen
 * @param <I> the input type to the operator, what it operates on
 * @param <O> the output type from the operator, what it returns as a result of operation
 */
public interface Operator<I, O> {
    O operate(I obj);
}
