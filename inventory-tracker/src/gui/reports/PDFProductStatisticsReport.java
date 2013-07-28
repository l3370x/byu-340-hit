/**
 * 
 */
package gui.reports;

import java.util.ArrayList;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author aaron
 * 
 */
public class PDFProductStatisticsReport extends ReportController implements
		IReport {

	private String title;
	private boolean landscape;
	private Document document;

	public PDFProductStatisticsReport(String filename, String title) {
		super(filename, title);
		this.title = title;
		this.landscape = true;
		initializeReport();
	}

	@Override
	public String getFileExtention() {
		return ".pdf";
	}

	/**
	 * Set the report to print in landscape view. Default is Portrait.
	 * 
	 * @param value
	 *            set true for landscape, false for portrait
	 */
	public void setLandscape(boolean value) {
		this.landscape = value;
	}

	/**
	 * Set the title for the top heading of the PDF
	 * 
	 * @param title
	 *            the new title
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Initialize everything needed to make report.
	 * 
	 * @param title
	 *            the heading above the table on the first page
	 * @param filename
	 *            the name of the file to be written to disk
	 * @param landscape
	 *            print in landscape?
	 */
	private void initializeReport() {
		initOutputFile();
		appendText(title);
	}

	/**
	 * creates the file to be written to
	 * 
	 */
	private void initOutputFile() {
		if (landscape)
			document = new Document(PageSize.LETTER_LANDSCAPE);
		else
			document = new Document(PageSize.LETTER_LANDSCAPE);
		try {
			PdfWriter.getInstance(document, output);
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		document.open();
	}

	private void addTable(PdfPTable table) {
		try {
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void appendTable(List<ArrayList<String>> rows) {
		int maxColumns = maxSize(rows);
		for (ArrayList<String> row : rows) {
			PdfPTable table = new PdfPTable(maxColumns);
			table = appendTableRow(row, table);
			addTable(table);
		}

	}

	private int maxSize(List<ArrayList<String>> lists) {
		int max = 0;
		for (ArrayList<String> list : lists) {
			if (max < list.size())
				max = list.size();
		}
		return max;
	}

	private PdfPTable appendTableRow(ArrayList<String> row, PdfPTable table) {
		for (String s : row) {
			table.addCell(s);
		}
		return table;
	}

	@Override
	public void appendText(String s) {
		PdfPTable table = new PdfPTable(1);
		PdfPCell titleCell = new PdfPCell(new Phrase(s));
		titleCell.setBorder(0);
		titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(titleCell);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		addTable(table);
	}

	@Override
	public void finalize() {
		document.close();

		finalizeReport();
	}

}
