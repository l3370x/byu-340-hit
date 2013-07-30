package gui.reports;

import common.Operator;
import core.model.ProductContainer;

/**
 * Created with IntelliJ IDEA.
 * User: kmcqueen
 * Date: 7/29/13
 * Time: 9:58 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Report extends Operator<ProductContainer, Boolean> {
    void render(ReportRenderer renderer);
}
