package gui.product;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.MessageFormat;

public class UPCDatabaseSemantics implements ProductDetector {
	
	private static final String API_KEY = "SEM36A0567808F6C76F836D9F2ABEE198283";
	private static final String API_SECRET = "NzVkNGU1NDk3YTMxZTg4OTdlZjg5M2Q0MjBkN2EzMWU";
	private static final String URL_FORMAT = "https://api.semantics3.com/v1/products?q={0}";
	private static final String BARCODE_ELEMENT = "\"upc\":";

	@Override
	public ProductDescriptor getProductDescription(String barcode) {
		try {
			URL url = new URL(MessageFormat.format(URL_FORMAT, BARCODE_ELEMENT + barcode));
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
