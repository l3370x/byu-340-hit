/**
 * 
 */
package gui.product;

/**
 * @author Rohit
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import core.model.exception.ExceptionHandler;

public class CheckUPC implements ProductDetector {
	
	
	//http://www.checkupc.com/search.php?keyword=asd

	private String baseURL = "http://www.checkupc.com/search.php?keyword=";
	

	@Override
	public ProductDescriptor getProductDescription(String barcode) {
		
		String description = "";
		if (barcode.matches("[0-9]+") && barcode.length() > 6) {

			try {
				URL url = new URL(baseURL + URLEncoder.encode(barcode, "UTF-8"));
				BufferedReader rdr = new BufferedReader(new InputStreamReader(url.openStream()));
				String output = rdr.toString();
				System.out.println(output);
				
				//<h1 style="font-size:17px;font-family:arial;lin
				//e-height:22px;"><strong>Acer AL1916W Asd - Flat panel display -
				//TFT - 19" - widescreen - 1440 x 900 - 300 cd/m2 - 700:1 - 5 ms - 0.294 
				//mm - DVI-D, VGA - silver</strong></h1><br />

				/*
			
				URLConnection con = url.openConnection();
				Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
				Matcher m = p.matcher(con.getContentType());
				/* If Content-Type doesn't match this pre-conception, choose default and 
				 * hope for the best. */
				/*
				String charset = m.matches() ? m.group(1) : "ISO-8859-1";
				Reader r = new InputStreamReader(con.getInputStream(), charset);
				StringBuilder buf = new StringBuilder();
				while (true) {
				  int ch = r.read();
				  if (ch < 0)
				    break;
				  buf.append((char) ch);
				}
				String str = buf.toString();8
				*/
				

			//	while ((line = rdr.readLine()) != null) {
					// yes, this might be the lazy way
		//			if (line.contains("\"description\": ")) {
		//				description = line.substring(20, line.length() - 3);
		//			}
		//		}
		//		rdr.close();

			} catch (MalformedURLException e) {
				ExceptionHandler.TO_LOG.reportException(e, "Error passing url to www.google.com");
			} catch (IOException e) {
				ExceptionHandler.TO_LOG.reportException(e,
						"Error reading input stream from www.google.com");
			}
		}
		return new ProductDescriptor(barcode, description);
	}
}
