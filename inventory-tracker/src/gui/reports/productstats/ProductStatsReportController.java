package gui.reports.productstats;

import gui.common.FileFormat;
import gui.common.IView;
import gui.reports.AbstractReportController;
import gui.reports.Report;

/**
 * Controller class for the product statistics report view.
 */
public class ProductStatsReportController extends AbstractReportController implements
        IProductStatsReportController {

    /**
     * Constructor.
     *
     * @param view Reference to the item statistics report view
     */
    public ProductStatsReportController(IView view) {
        super(view);
        construct();
    }

    //
    // Controller overrides
    //

    /**
     * Returns a reference to the view for this controller.
     * <p/>
     * {@pre None}
     * <p/>
     * {@post Returns a reference to the view for this controller.}
     */
    @Override
    protected IProductStatsReportView getView() {
        return (IProductStatsReportView) super.getView();
    }

    /**
     * Loads data into the controller's view.
     * <p/>
     * {@pre None}
     * <p/>
     * {@post The controller has loaded data into its view}
     */
    @Override
    protected void loadValues() {
        this.getView().setFormat(FileFormat.PDF);
        this.getView().setMonths("3");
    }

    //
    // IProductStatsReportController overrides
    //

    /**
     * This method is called when any of the fields in the product statistics report view is changed by
     * the user.
     */
    @Override
    public void valuesChanged() {
        if (!isValidInput(this.getView().getMonths())) {
            this.getView().enableOK(false);
            return;
        }
        this.getView().enableOK(true);
    }

    @Override
    protected FileFormat getReportFormat() {
        return this.getView().getFormat();
    }

    @Override
    protected Report getReport() {
        return new ProductStatisticsReport(Integer.parseInt(this.getView().getMonths()));
    }

    private boolean isValidInput(String months) {
        // Check to make sure the month value isn't blank
        if (null == months || months.isEmpty()) {
            return false;
        }

        // check to make sure the month value is an integer and in the
        // determined range.
        Float count = Float.parseFloat(this.getView().getMonths());
        if (count != Math.round(count) || (count > 100) || (count <= 0)) {
            return false;
        }

        return true;
    }
}
