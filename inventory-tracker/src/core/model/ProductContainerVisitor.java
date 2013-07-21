package core.model;

import common.Operator;
import common.VisitOrder;
import common.Visitor;

/**
 *
 * @author kmcqueen
 */
public class ProductContainerVisitor implements Visitor<ProductContainer> {
    private final Operator<ProductContainer, Boolean> operator;
    private final VisitOrder order;
    
    public ProductContainerVisitor(Operator<ProductContainer, Boolean> operator, VisitOrder order) {
        this.operator = operator;
        this.order = order;
    }
    
    @Override
    public boolean visit(ProductContainer container) {
        return this.order.visit(container, this.operator);
    }
    
}
