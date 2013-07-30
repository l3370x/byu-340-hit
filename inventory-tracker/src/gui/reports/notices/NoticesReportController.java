package gui.reports.notices;

import common.VisitOrder;
import gui.common.FileFormat;
import gui.common.IView;
import gui.reports.*;

import java.util.List;

import core.model.Item;
import core.model.exception.ExceptionHandler;
import core.model.exception.HITException;
import core.model.exception.HITException.Severity;

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

    private List<Item> createData() {
		// TODO Auto-generated method stub
		return null;
	}

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
	public void addWarning(ReportController report, String storageUnit,
			String category, String threeMonthSupply,
			List<String> problems) {
		report.appendText(String
				.format("Product group %s::%s has a 3-month supply of (%s) that is inconsistent with the following products:",
						storageUnit, category, threeMonthSupply));
		//for (ArrayList<String> row : problems) {
			for (int i = 0; i < 3; i++)
				report.appendText(String.format("- %s::%s (%s)", problems.get(i)));
		//}
	}
	
//	public class BadStuff{
//		public BadStuff(Category c, Item i){
//			this.c = c;
//			this.i = i;
//		}
//		
//		Category c;
//		Item i;
//	}

	/**
	 * This method is called when the user clicks the "OK" button in the notices
	 * report view.
	 */
	@Override
	public void display() {
		ReportController report = null;

		FileFormat f = this.getView().getFormat();
		String title = String.format("Notices Report");
		String name = "NoticesReport";

		if (f.equals(FileFormat.PDF))
			report = new PDFNoticesReport(name, title);
		else if (f.equals(FileFormat.HTML))
			report = new HTMLNoticesReport(name, title);
		else {
			ExceptionHandler.TO_USER.reportException(new HITException(
					Severity.INFO, "Couldn't make report from given input."),
					String.format("Bad Format Entered - %s.", f.toString()));
			return;
		}

		report.initialize();
//		List<BadStuff> badItems = new ArrayList<BadStuff>();
//		for (Item i : InventoryManager.Factory.getInventoryManager().getItems()) {
//			Containable pointer = (Containable) i.getContainer();
//			while (pointer instanceof Category) {
//				UnitType itemType = null;
//				UnitType catType = null;
//				try {
//					itemType = UnitsConverter.unitsToUnitType(i.getProduct().getSize()
//							.getUnits());
//					catType = UnitsConverter
//							.unitsToUnitType(((Category) pointer)
//									.get3MonthSupplyQuantity().getUnits());
//				} catch (HITException e) {
//					break;
//				}
//				if (itemType != catType) {
//					badItems.add(new BadStuff((Category) pointer, i));
//				}
//				pointer = (Containable) pointer.getContainer();
//			}
//		}
//
//		
//		 for(BadStuff bs : badItems) {
//		 List<String> problems = new ArrayList<String>();
//		 problems.add(bs.i.getContainer().getName());
//		 problems.add(bs.i.getProduct().getDescription());
//		 problems.add(bs.i.getProduct().getSize().toString());
//		
//		 addWarning(report, bs.c.getContainer().getName(), bs.c.getName(), bs.c.get3MonthSupplyQuantity().toString(),
//		 problems);
//		 }

		report.finalize();

	}

    @Override
    protected FileFormat getReportFormat() {
        return this.getView().getFormat();
    }

    @Override
    protected Report getReport() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected VisitOrder getModelVisitOrder() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    private boolean isValidInput() {
		if (this.getView().getFormat() == null)
			return false;
		return true;
	}

}
