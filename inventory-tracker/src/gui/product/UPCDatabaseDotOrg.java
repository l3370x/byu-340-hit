package gui.product;

import core.model.exception.ExceptionHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.ext.DefaultHandler2;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.MessageFormat;

/**
 * The {@code UPCDatabaseDotOrg} class is a {@link ProductDetector} implementation that issues a
 * request to www.upcdatabase.org to find the product details for a particular barcode.
 *
 * @author Keith McQueen
 */
public class UPCDatabaseDotOrg extends DefaultHandler2 implements ProductDetector {
    private static final String URL_FORMAT = "http://api.upcdatabase.org/xml/{0}/{1}";
    private static final String UPC_DB_DOT_ORG_KEY = "07a1f2e4a6d47b72564476ebe54502e9";
    private static final String DESCRIPTION_ELEMENT = "itemname";
    private static final String BARCODE_ELEMENT = "number";

    private boolean shouldSaveDescription;
    private boolean shouldSaveBarcode;

    private String barcode;
    private String description = NOT_FOUND;

    @Override
    public ProductDescriptor getProductDescription(String barcode) {
        InputStream inputStream = null;

        try {
            // create the URL
            URL url = new URL(MessageFormat.format(URL_FORMAT, UPC_DB_DOT_ORG_KEY, barcode));

            // open a connection to the URL
            inputStream = url.openStream();

            // create a parser
            SAXParser parser = SAXParserFactory.newInstance().newSAXParser();

            // parse the XML
            parser.parse(inputStream, this);
        } catch (Exception e) {
            ExceptionHandler.TO_LOG.reportException(e,
                    "Unable to get product description from www.upcdatabase.org");
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    ExceptionHandler.TO_LOG.reportException(e,
                            "Error closing input stream to www.upcdatabase.org");
                }
            }
        }

        // use the barcode that was entered rather than the found barcode
        return new ProductDescriptor(barcode, this.description);
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        this.shouldSaveDescription = DESCRIPTION_ELEMENT.equals(qName);
        this.shouldSaveBarcode = BARCODE_ELEMENT.equals(qName);
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (this.shouldSaveDescription) {
            this.description = new String(ch, start, length);
            this.shouldSaveDescription = false;
        }

        if (this.shouldSaveBarcode) {
            this.barcode = new String(ch, start, length);
            this.shouldSaveBarcode = false;
        }
    }
}
