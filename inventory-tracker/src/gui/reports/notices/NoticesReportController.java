package gui.reports.notices;

import gui.common.FileFormat;
import gui.common.IView;
import gui.reports.AbstractReportController;
import gui.reports.Report;

/**
 * Controller class for the notices report view.
 */
public class NoticesReportController extends AbstractReportController implements
		INoticesReportController {

	/**
	 * Constructor.
	 * 
	 * @param view
	 *            Reference to the notices report view
	 */
	public NoticesReportController(IView view) {
		super(view);

		construct();
	}

	//
	// Controller overrides
	//

	/**
	 * Returns a reference to the view for this controller.
	 * 
	 * {@pre None}
	 * 
	 * {@post Returns a reference to the view for this controller.}
	 */
	@Override
	protected INoticesReportView getView() {
		return (INoticesReportView) super.getView();
	}

    //
	// IExpiredReportController overrides
	//

	/**
	 * 
	 * @param storageUnit
	 *            the name of a storage unti
	 * @param category
	 *            the name of a category
	 * @param threeMonthSupply
	 *            a string of the value and unit for 3-month supply for a
	 *            product
	 * @param problems
	 *            an N numbered list of 3 string lists that include the
	 *            following: a product description, an item description, and the
	 *            quantity of an item that conflicts witht the 3-month param
	 */

	/**
	 * This method is called when the user clicks the "OK" button in the notices
	 * report view.
	 */
    

    @Override
    protected FileFormat getReportFormat() {
        return this.getView().getFormat();
    }

    @Override
    protected Report getReport() {
        NoticesReport report = new NoticesReport();
        return report;
    }


}
