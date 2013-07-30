package gui.reports;

import gui.common.FileFormat;

/**
 * Created with IntelliJ IDEA.
 * User: kmcqueen
 * Date: 7/29/13
 * Time: 9:59 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ReportRenderer {
    public static class Factory {
        public static ReportRenderer getRendererFor(FileFormat format) {
            switch (format) {
                case HTML:
                    // return new HTMLReportRenderer;
                    return null;

                case PDF:
                    // return new PDFReportRenderer
                    return null;
            }

            return null;
        }
    }
}
