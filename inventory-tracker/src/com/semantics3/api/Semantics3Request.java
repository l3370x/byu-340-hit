package com.semantics3.api;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.semantics3.errors.Semantics3Exception;

import oauth.signpost.OAuthConsumer;
import oauth.signpost.basic.DefaultOAuthConsumer;
import oauth.signpost.exception.OAuthCommunicationException;
import oauth.signpost.exception.OAuthExpectationFailedException;
import oauth.signpost.exception.OAuthMessageSignerException;

public class Semantics3Request{
	final static private String API_DOMAIN = "api.semantics3.com";
	final static private String API_BASE   = "https://" + API_DOMAIN + "/v1/";
	
	private String apiKey;
	private String apiSecret;
	private String endpoint;
	private String endpointURL;
	private OAuthConsumer consumer;
	private HashMap<String,JSONObject> query = new HashMap<String, JSONObject>();
	private JSONObject queryResult;
	private StringBuffer urlBuilder;
	
	public Semantics3Request(String apiKey, String apiSecret, String endpoint) {
		if (apiKey == null) { 
			throw new Semantics3Exception(
					"API Credentials Missing",
					"You did not supply an apiKey. Please sign up at https://semantics3.com/ to obtain your api_key."
				);
		}
		if (apiSecret == null) { 
			throw new Semantics3Exception(
					"API Credentials Missing",
					"You did not supply an apiSecret. Please sign up at https://semantics3.com/ to obtain your api_key."
				);
		}

		this.apiKey    = apiKey;
		this.apiSecret = apiSecret;
		this.endpoint  = endpoint;
		this.consumer = new DefaultOAuthConsumer(apiKey, apiSecret);
		consumer.setTokenWithSecret("", "");
	}
	
	protected JSONObject fetch(String endpoint, String params) throws
			OAuthMessageSignerException,
			OAuthExpectationFailedException,
			OAuthCommunicationException,
			IOException {
		String req = new StringBuffer()
					.append(API_BASE)
					.append(endpoint)
					.append("?q=")
					.append(URLEncoder.encode(params, "UTF-8"))
					.toString();
		URL url = new URL(req);

		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.setRequestProperty("User-Agent", "Semantics3 Java Library");
		consumer.sign(request);
		request.connect();
		JSONObject json = null;
		try {
			json = new JSONObject(new JSONTokener(request.getInputStream()));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return json;
	}
	
	public Semantics3Request add(String endpoint, Object... fields) {
		JSONObject endpointQuery;
		if ((endpointQuery = query.get(endpoint)) == null) {
			endpointQuery = new JSONObject();
			query.put(endpoint,endpointQuery);
		}
		JSONObject sq = endpointQuery;
		for (int i=0;i<fields.length-2;i++) {
			JSONObject tmp = sq;
			if(sq.has((String)fields[i])){
				try {
					sq = sq.getJSONObject((String)fields[i]);
				} catch(Exception e) {
					throw new Semantics3Exception(
							"Invalid constraint",
							"Cannot add this constraint, '" + fields[i] +"' is already a value."
						);
				}
			} else {
				sq = new JSONObject();
				try {
					tmp.put((String)fields[i], sq);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		try {
			sq.put((String)fields[fields.length-2], fields[fields.length-1]);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return this;
	}
	
	public void remove(String endpoint, String... fields) {
		_remove(this.query.get(endpoint),0,fields);
	}
	
	private void _remove(JSONObject subquery,int i, String[] fields) {
		if (i == fields.length-1) {
			subquery.remove(fields[i]);
		} else {
			JSONObject child = null;
			try {
				child = (JSONObject) subquery.get(fields[i]);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			_remove(child,i+1,fields);
			if (child.length() == 0) {
				subquery.remove(fields[i]);
			}
		}
	}

	public Semantics3Request field(Object...fields) {
		return add(this.endpoint,fields);
	}
	
	protected void runQuery() throws
		OAuthMessageSignerException,
		OAuthExpectationFailedException,
		OAuthCommunicationException,
		IOException {
		runQuery(this.endpoint);
	}
	
	protected void runQuery(String endpoint) throws
		OAuthMessageSignerException,
		OAuthExpectationFailedException,
		OAuthCommunicationException,
		IOException {
		JSONObject q = this.query.get(endpoint);
		if (q==null) {
			throw new Semantics3Exception(
					"No query built", 
					"You need to first create a query using the add() method."
				);
				
		}
		this.queryResult = fetch(endpoint,q.toString());
		try {
			if (!this.queryResult.getString("code").equals("OK")) {
				throw new Semantics3Exception(
						this.queryResult.getString("code"),
						this.queryResult.getString("message")
					);
				
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public JSONObject get() throws OAuthMessageSignerException, OAuthExpectationFailedException, OAuthCommunicationException, IOException {
		return get(this.endpoint);
	}
	
	public JSONObject get(String endpoint) throws 
		OAuthMessageSignerException,
		OAuthExpectationFailedException,
		OAuthCommunicationException,
		IOException {
		this.runQuery(endpoint);
		return this.queryResult;
	}
}
