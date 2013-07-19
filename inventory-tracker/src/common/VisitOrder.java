package common;

/**
 * The {@code VisitOrder} enumerated type defines and implements the visiting order strategy.
 * 
 * @author kmcqueen
 */
public enum VisitOrder {
    /**
     * Do a pre-order traversal of the visitable object(s).  This means that the current node will
     * be processed before its child nodes.
     */
    PRE_ORDER {
        @Override
        public <V extends Visitable> boolean visit(V target, Operator<V, Boolean> operator) {
            // visit the target first
            if (false == operator.operate(target)) {
                return false;
            }

            // now visit the next generation
            for (Visitable child : target.getNextToBeVisited()) {
                if (false == this.visit((V) child, operator)) {
                    return false;
                }
            }

            return true;
        }

    },
    /**
     * Do a post-order traversal of the visitable object(s).  This means that the current node will
     * be visited after its child nodes.
     */
    POST_ORDER {
        @Override
        public <V extends Visitable> boolean visit(V target, Operator<V, Boolean> operator) {
            // now visit the next generation first
            for (Visitable child : target.getNextToBeVisited()) {
                if (false == this.visit((V) child, operator)) {
                    return false;
                }
            }

            // now visit the target
            if (false == operator.operate(target)) {
                return false;
            }

            return true;
        }
    };
    
    public abstract <V extends Visitable> boolean visit(V target, Operator<V, Boolean> visitor);
}
