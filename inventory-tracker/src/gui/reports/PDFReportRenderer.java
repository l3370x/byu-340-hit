package gui.reports;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.IOException;

/**
 * The {@code PDFReportRenderer} writes report data to a file in PDF format.
 */
public class PDFReportRenderer extends AbstractReportRenderer {
    private static final String PDF_FILE_EXT = "pdf";
    private static final Font FONT_TABLE_HEADER = FontFactory.getFont("Helvetica", 10, Font.BOLD);
    private static final Font FONT_TABLE_DATA = FontFactory.getFont("Helvetica", 8, Font.NORMAL);
    private static final Font FONT_HEADING = FontFactory.getFont("Helvetica", 12, Font.BOLD);
    private static final Font FONT_TITLE = FontFactory.getFont("Helvetica", 14, Font.BOLD);

    private static final BaseColor EVEN_ROW = new BaseColor(235, 235, 255);

    private Document document;
    private PdfPTable table;
    private BaseColor rowBackground = null;

    @Override
    protected String getFileExtension() {
        return PDF_FILE_EXT;
    }

    @Override
    public void beginDocument(String filename, String title, ReportOrientation orientation)
            throws IOException {
        // create the document
        this.document = new Document(orientation == ReportOrientation.LANDSCAPE ?
                PageSize.LETTER_LANDSCAPE.rotate() : PageSize.LETTER);

        // attach the document to the output stream
        try {
            PdfWriter.getInstance(this.document, this.openOutputStream(filename));
        } catch (DocumentException e) {
            throw new IOException(e);
        }

        // open the document
        this.document.open();

        // create and add the title to the document
        try {
            Paragraph titleParagraph = new Paragraph(title, FONT_TITLE);
            titleParagraph.setAlignment(Element.ALIGN_CENTER);
            titleParagraph.setSpacingAfter(18);
            this.document.add(titleParagraph);
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void endDocument() throws IOException {
        this.document.close();
    }

    @Override
    public void addHeading(String headingText) throws IOException {
        try {
            this.document.add(new Paragraph(headingText, FONT_HEADING));
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void addText(String text) throws IOException {
        try {
            this.document.add(new Paragraph(text));
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }

    @Override
    public void beginTable(String... headers) throws IOException {
        this.table = new PdfPTable(headers.length);
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell(new Phrase(header, FONT_TABLE_HEADER));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            headerCell.setVerticalAlignment(Element.ALIGN_CENTER);
            headerCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            this.table.addCell(headerCell);
        }
        this.table.setHeaderRows(1);
    }

    @Override
    public void addTableRow(String... values) throws IOException {
        for (String value : values) {
            PdfPCell cell = new PdfPCell(new Phrase(value, FONT_TABLE_DATA));
            cell.setBackgroundColor(this.rowBackground);
            this.table.addCell(cell);
        }

        this.rowBackground = null == this.rowBackground ? EVEN_ROW : null;
    }

    @Override
    public void endTable() throws IOException {
        try {
            this.document.add(this.table);

            this.table = null;
        } catch (DocumentException e) {
            throw new IOException(e);
        }
    }
}
