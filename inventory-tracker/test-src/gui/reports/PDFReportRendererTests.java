package gui.reports;

import org.junit.Test;

import java.io.IOException;

public class PDFReportRendererTests {
    @Test
    public void testRenderAReport() throws IOException {
        PDFReportRenderer renderer = new PDFReportRenderer();
        renderer.beginDocument("testing", "Testing", ReportRenderer.ReportOrientation.PORTRAIT);
        renderer.beginTable("Column A", "Column B", "Column C", "Column D", "Column E");

        for (int i = 0; i < 40; i++) {
            renderer.addTableRow("A", "B", "C", "D", "E");
        }

        renderer.endTable();
        renderer.endDocument();
        renderer.displayReport();
    }
}
