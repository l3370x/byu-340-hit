package gui.reports;

import common.VisitOrder;
import static core.model.InventoryManager.Factory.getInventoryManager;
import static gui.reports.ReportRenderer.Factory.getRendererFor;
import core.model.ProductContainerVisitor;
import gui.common.Controller;
import gui.common.FileFormat;
import gui.common.IView;

/**
 * Created with IntelliJ IDEA.
 * User: kmcqueen
 * Date: 7/29/13
 * Time: 9:52 PM
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractReportController extends Controller implements IReportController {
    /**
     * Constructor.
     *
     * @param view The view for this controller
     *             <p/>
     *             {@pre view != null}
     *             <p/>
     *             {@post getView() == view}
     */
    protected AbstractReportController(IView view) {
        super(view);
    }


    /**
     * Sets the enable/disable state of all components in the controller's view.
     * A component should be enabled only if the user is currently allowed to
     * interact with that component.
     *
     * {@pre None}
     *
     * {@post The enable/disable state of all components in the controller's
     * view have been set appropriately.}
     */
    @Override
    protected void enableComponents() {
    }

    /**
     * Loads data into the controller's view.
     *
     * {@pre None}
     *
     * {@post The controller has loaded data into its view}
     */
    @Override
    protected void loadValues() {
    }

    /**
     * This method is called when any of the fields in the notices report view
     * is changed by the user.
     */
    @Override
    public void valuesChanged() {
    }

    @Override
    public void display() {
        // create the report instance
        Report report = this.getReport();

        // get the visit order
        VisitOrder order = this.getModelVisitOrder();

        // create the model visitor
        ProductContainerVisitor visitor = new ProductContainerVisitor(report, order);

        // visit the model
        visitor.visit(getInventoryManager());

        // get the renderer for the report and the desired output format
        ReportRenderer renderer = getRendererFor(this.getReportFormat());

        // render the report
        report.render(renderer);

        // display the rendered report
        renderer.displayReport();
    }

    /**
     * Get the {@link VisitOrder} to be used when visiting the inventory model.  The default
     * implementation returns {@link VisitOrder#PRE_ORDER}.
     *
     * @return the visit order to be used when visiting the inventory model (default is
     * {@link VisitOrder#PRE_ORDER})
     */
    protected VisitOrder getModelVisitOrder() {
        return VisitOrder.PRE_ORDER;
    }

    /**
     * Get the file format for the generated report.
     *
     * @return the file format for the generated report
     */
    protected abstract FileFormat getReportFormat();

    /**
     * Get a {@link Report} instance for use in this report controller
     *
     * @return a report instance
     */
    protected abstract Report getReport();
}

