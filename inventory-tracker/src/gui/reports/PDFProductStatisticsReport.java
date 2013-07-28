/**
 * 
 */
package gui.reports;

/**
 * @author aaron
 * 
 */
public class PDFProductStatisticsReport extends PDFReport {

	public PDFProductStatisticsReport(String filename, String title) {
		super(filename, title);
		super.setLandscape(true);
	}
}
