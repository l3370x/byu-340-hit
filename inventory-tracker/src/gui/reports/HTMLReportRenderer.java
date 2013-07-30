package gui.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.MessageFormat;

/**
 * The {@code HTMLReportRenderer} writes report data to a file in HTML format.
 */
public class HTMLReportRenderer extends AbstractReportRenderer {
    private static final String HTML_FILE_EXT = "html";
    private static final String HTML_HEADER =
            "<html>\n<head><title>{0}</title</head>\n<body>\n<h1>{0}</h1><br/>";
    private static final String HTML_END = "</body></html>";
    private static final String HTML_TABLE_BEGIN = "\n<table border=\"1\">";
    private static final String HTML_TABLE_ROW_BEGIN = "\n<tr>\n";
    private static final String HTML_TABLE_HEADER_CELL = "\n<th>{0}</th>";
    private static final String HTML_TABLE_DATA_CELL = "\n<td>{0}</td";
    private static final String HTML_TABLE_ROW_END = "\n</tr>";
    private static final String HTML_TABLE_END = "\n</table>";
    private static final String HTML_HEADING = "\n<h3>{0}</h3>";
    private static final String HTML_PARAGRAPH = "\n<p>{0}</p>";

    private PrintWriter writer;

    @Override
    public void beginDocument(String filename, String title) throws IOException {
        // open the output stream
        this.writer = new PrintWriter(this.openOutputStream(filename));

        // write the headers
        this.writer.write(MessageFormat.format(HTML_HEADER, title));
    }

    @Override
    public void endDocument() {
        this.writer.write(HTML_END);
        this.writer.flush();
        this.writer.close();
    }

    @Override
    public void addHeading(String headingText) {
        this.writer.write(MessageFormat.format(HTML_HEADING, headingText));
    }

    @Override
    public void addText(String text) throws IOException {
        this.writer.write(MessageFormat.format(HTML_PARAGRAPH, text));
    }

    @Override
    public void beginTable(String... headers) {
        // open the html table
        this.writer.write(HTML_TABLE_BEGIN);
        this.writeTableRow(HTML_TABLE_HEADER_CELL, headers);
    }

    private void writeTableRow(String cellFormat, String... headers) {
        this.writer.write(HTML_TABLE_ROW_BEGIN);

        // write the headers
        for (String header : headers) {
            this.writer.write(MessageFormat.format(cellFormat, header));
        }

        // close the header row
        this.writer.write(HTML_TABLE_ROW_END);
    }

    @Override
    public void addTableRow(String... values) {
        this.writeTableRow(HTML_TABLE_DATA_CELL, values);
    }

    @Override
    public void endTable() {
        this.writer.write(HTML_TABLE_END);
    }

    @Override
    protected String getFileExtension() {
        return HTML_FILE_EXT;
    }
}
