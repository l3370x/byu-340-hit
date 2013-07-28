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

public class PDFReport extends ReportController {

	private boolean landscape;
	private Document document;

	public PDFReport(String filename, String title) {
		super(filename, title);
		this.landscape = false;
	}

	@Override
	public void initialize() {
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
		writeTitle();
	}
	
	private void writeTitle(){
		PdfPTable table = new PdfPTable(1);
		PdfPCell titleCell = new PdfPCell(new Phrase(title));
		titleCell.setBorder(0);
		titleCell.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(titleCell);
		table.setHorizontalAlignment(Element.ALIGN_CENTER);
		addTable(table);
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

	@Override
	public String getFileExtention() {
		return ".pdf";
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
		table.addCell(titleCell);
		addTable(table);
	}

	@Override
	public void finalize() {
		document.close();

		finalizeReport();
	}
}
