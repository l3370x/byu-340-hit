package gui.product;

import java.io.IOException;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.semantics3.api.Products;

public class UPCDatabaseSemantics implements ProductDetector {

	private static final String API_KEY = "SEM36A0567808F6C76F836D9F2ABEE198283";
	private static final String API_SECRET = "NzVkNGU1NDk3YTMxZTg4OTdlZjg5M2Q0MjBkN2EzMWU";

	@Override
	public ProductDescriptor getProductDescription(String barcode) {
		Products products = new Products(API_KEY, API_SECRET);

		products.productsField("upc", barcode);

		try {
			JSONObject object = products.getProducts();
			JSONArray jsonMainArr = object.getJSONArray("results");
			String name = null;
			JSONObject childJSONObject = jsonMainArr.getJSONObject(0);
			name = childJSONObject.getString("name");
			ProductDescriptor results = new ProductDescriptor(barcode, name);
			return results;
		} catch (OAuthMessageSignerException | OAuthExpectationFailedException
				| OAuthCommunicationException | IOException | JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

}
