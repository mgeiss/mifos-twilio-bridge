package org.mifos.module.sms.provider;



import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Map.Entry;

import org.json.*;

public class AfricasTalkingGateway {
	private String _username;
    private String _apiKey;
    private int responseCode;
    
    private static final String SMSURLString      = "https://api.africastalking.com/version1/messaging";
    private static final String VOICEURLString    = "https://voice.africastalking.com";
	private static final String SUBSCRIPTION_URL  = "https://api.africastalking.com/version1/subscription";
	private static final String USERDATAURLString = "https://api.africastalking.com/version1/user";
	private static final String AIRTIMEURLString  = "https://api.africastalking.com/version1/airtime";
	private static final int HTTP_CODE_OK         = 200;
	private static final int HTTP_CODE_CREATED    = 201;
	
	//Change debug flag to true to view raw server response
	private static final boolean DEBUG = false;
	
	public AfricasTalkingGateway(String username_, String apiKey_)
    {
		_username = username_;
		_apiKey   = apiKey_;
    }
    
	
	//Bulk messages methods
    public JSONArray sendMessage(String to_, String message_) throws Exception
    {
    
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("to", to_);
    	data.put("message", message_);
	
    	return sendMessageImpro(to_, message_, data);
    }
    
   
    public JSONArray sendMessage(String to_, String message_, String from_, int bulkSMSMode_) throws Exception
    {	
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("to", to_);
    	data.put("message", message_);
	
    	if ( from_.length() > 0 ) data.put("from", from_);
	
    	data.put("bulkSMSMode", Integer.toString(bulkSMSMode_));
	
    	return sendMessageImpro(to_, message_, data);
    }
    

    public JSONArray sendMessage(String to_, String message_, String from_, int bulkSMSMode_, HashMap<String, String> options_) throws Exception
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("to", to_);
    	data.put("message", message_);
	
    	if ( from_.length() > 0 ) data.put("from", from_);
	
    	data.put("bulkSMSMode", Integer.toString(bulkSMSMode_));
	

    	if (options_.containsKey("enqueue")) data.put("enqueue", options_.get("enqueue"));
    	if (options_.containsKey("keyword")) data.put("keyword", options_.get("keyword"));
    	if (options_.containsKey("linkId"))  data.put("linkId", options_.get("linkId"));
    	if (options_.containsKey("retryDurationInHours"))  data.put("retryDurationInHours", options_.get("retryDurationInHours"));
	
    	return sendMessageImpro(to_, message_, data);
    }
    

    public JSONArray fetchMessages(int lastReceivedId_) throws Exception
    {
    	String requestUrl = SMSURLString + "?" +
    			URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(_username, "UTF-8") +
    			"&" + URLEncoder.encode("lastReceivedId", "UTF-8") + "=" + URLEncoder.encode(Integer.toString(lastReceivedId_), "UTF-8");
    	
    	String response = sendGETRequest(requestUrl);
    	if(responseCode == HTTP_CODE_OK) {
    		JSONObject jsObject = new JSONObject(response);
    		return jsObject.getJSONObject("SMSMessageData").getJSONArray("Messages");
    	}
    	
    	throw new Exception(response.toString());
    }

    
    //Subcscription methods
    public JSONObject createSubscription(String phoneNumber_, String shortCode_, String keyword_) throws Exception
    {
    	if(phoneNumber_.length() == 0 || shortCode_.length() == 0 || keyword_.length() == 0)
    		throw new Exception("Please supply phoneNumber, shortCode and keyword");
    	
		HashMap <String, String> data_ = new HashMap<String, String>();
		data_.put("username", _username);
		data_.put("phoneNumber", phoneNumber_);
		data_.put("shortCode", shortCode_);
		data_.put("keyword", keyword_);
		String requestUrl = SUBSCRIPTION_URL + "/create";
		
		String response = sendPOSTRequest(data_, requestUrl);
		
		if(responseCode != HTTP_CODE_CREATED)
			throw new Exception(response.toString());
		
		JSONObject jsObject = new JSONObject(response);
		return jsObject;
    }

  
    public JSONObject deleteSubscription(String phoneNumber_,String shortCode_, String keyword_) throws Exception
    {
    	if(phoneNumber_.length() == 0 || shortCode_.length() == 0 || keyword_.length() == 0)
		 throw new Exception("Please supply phone number, short code and keyword");
    	
		HashMap <String, String> data_ = new HashMap<String, String>();
		data_.put("username", _username);
		data_.put("phoneNumber", phoneNumber_);
		data_.put("shortCode", shortCode_);
		data_.put("keyword", keyword_);
		String requestUrl = SUBSCRIPTION_URL + "/delete";
		
		String response = sendPOSTRequest(data_, requestUrl);
		
		if(responseCode != HTTP_CODE_CREATED)
			throw new Exception(response.toString());
		
		JSONObject jsObject = new JSONObject(response);
		return jsObject;
    }

    
    public JSONArray fetchPremiumSubscriptions (String shortCode_, String keyword_, int lastReceivedId_) throws Exception
    {
    	if(shortCode_.length() == 0 || keyword_.length() == 0)
    		throw new Exception("Please supply short code and keyword");
    	
    	lastReceivedId_ = lastReceivedId_ > 0? lastReceivedId_ : 0;
    	String requestUrl = SUBSCRIPTION_URL + "?username="+_username+"&shortCode="+shortCode_+"&keyword="+keyword_+"&lastReceivedId="+lastReceivedId_;
    	
    	String response = sendGETRequest(requestUrl);
    	if(responseCode == HTTP_CODE_OK) {
    		JSONObject jsObject = new JSONObject(response);
    		return jsObject.getJSONArray("responses");
    	}
    	
    	throw new Exception(response.toString());
    }
    
    
    public void call(String from_, String to_) throws Exception
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("from", from_);
    	data.put("to", to_);
    	String urlString = VOICEURLString + "/call";
    	
    	String response   = sendPOSTRequest(data, urlString);
    	
    	JSONObject jsObject = new JSONObject(response);
    	
    	if(!jsObject.getString("Status").equals("Success"))
    		throw new Exception(jsObject.getString("ErrorMessage"));
    }
 
    //Call methods
    public int getNumQueuedCalls(String phoneNumber, String queueName) throws Exception
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("phoneNumber", phoneNumber);
    	data.put("queueName", queueName);
    	
    	return queuedCalls(data);
    }
 
    
	public int getNumQueuedCalls(String phoneNumber) throws Exception 
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("phoneNumbers", phoneNumber);
    	
    	return queuedCalls(data);
    }
 
    
   public void uploadMediaFile(String url_) throws Exception
    {
    	HashMap<String, String> data = new HashMap<String, String>();
    	data.put("username", _username);
    	data.put("url", url_);
    	String requestUrl = VOICEURLString + "/mediaUpload";
    	
    	String response = sendPOSTRequest(data, requestUrl);
    	
    	JSONObject jsObject = new JSONObject(response);
    	
    	if(!jsObject.getString("Status").equals("Success"))
    		throw new Exception(jsObject.getString("ErrorMessage"));
    	
    }
 
    
   //Airtime methods
    public JSONArray sendAirtime(String recipients_) throws Exception 
    {
    	HashMap<String, String> data_ = new HashMap<String, String>();
    	data_.put("username", _username);
    	data_.put("recipients", recipients_);
    	String urlString = AIRTIMEURLString + "/send";
    	
    	String response = sendPOSTRequest(data_, urlString);
    	
    	if(responseCode == HTTP_CODE_CREATED) {
    		JSONObject jsObject = new JSONObject(response);
    		JSONArray results = jsObject.getJSONArray("responses");
    		if(results.length() > 0)
    			return results;
    		throw new Exception(jsObject.getString("errorMessage"));
    	}
    	
    	throw new Exception(response);
    }
    
    
    //User data method
    public JSONObject getUserData() throws Exception
    {
    	String requestUrl = USERDATAURLString + "?username="+_username;
    	
    	String response   = sendGETRequest(requestUrl);
    	if(responseCode == HTTP_CODE_OK) {
    		JSONObject jsObject = new JSONObject(response);
    		return jsObject.getJSONObject("UserData");
    	}
    	
    	throw new Exception(response);
    }

   
    private JSONArray sendMessageImpro(String to_, String message_, HashMap<String, String> data_) throws Exception{
    	String response = sendPOSTRequest(data_, SMSURLString);
    	if (responseCode == HTTP_CODE_CREATED) {
    		JSONObject jsObject = new JSONObject(response);
    		JSONArray  recipients = jsObject.getJSONObject("SMSMessageData").getJSONArray("Recipients");
    		return recipients;
    	}
    	
    	throw new Exception(response);
    }

    
    //Private accessor methods
    private int queuedCalls(HashMap<String, String> data_) throws Exception {
    	String requestUrl = VOICEURLString + "/queueStatus";
    	String response = sendPOSTRequest(data_, requestUrl);
    	JSONObject jsObject = new JSONObject(response);
    	if(jsObject.getString("Status").equals("Success"))
    		return jsObject.getInt("NumQueued");
    	throw new Exception(jsObject.getString("ErrorMessage"));
    }
    
    
    private String sendPOSTRequest(HashMap<String, String> dataMap_, String urlString_) throws Exception {
    	try {
    		String data = new String();
    		Iterator<Entry<String, String>> it = dataMap_.entrySet().iterator();
    		while (it.hasNext()) {
    			Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
    			data += URLEncoder.encode(pairs.getKey().toString(), "UTF-8");
    			data += "=" + URLEncoder.encode(pairs.getValue().toString(), "UTF-8");
    			if ( it.hasNext() ) data += "&";
    		}
    		URL url = new URL(urlString_);
    		URLConnection conn = url.openConnection();
    		conn.setRequestProperty("Accept", "application/json");
    		conn.setRequestProperty("apikey", _apiKey);
	    	conn.setDoOutput(true);
	    	OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
	    	writer.write(data);
	    	writer.flush();
	    
	    	HttpURLConnection http_conn = (HttpURLConnection)conn;
	    	responseCode = http_conn.getResponseCode();
			
	    	BufferedReader reader;
    		if(responseCode == HTTP_CODE_OK || responseCode == HTTP_CODE_CREATED)
    			reader = new BufferedReader(new InputStreamReader(http_conn.getInputStream()));
    		else
    			reader = new BufferedReader(new InputStreamReader(http_conn.getErrorStream()));
    		String response = reader.readLine();
    		
    		if(DEBUG)
    			System.out.println(response);
    		
    		reader.close();
    		return response;
	    
    	} catch (Exception e){
    		throw e;
 		}
    }

    private String sendGETRequest(String urlString) throws Exception
    {
    	try {
    		URL url= new URL(urlString);
    		URLConnection connection = (URLConnection)url.openConnection();
    		connection.setRequestProperty("Accept","application/json");
    		connection.setRequestProperty("apikey", _apiKey);
    		
    		HttpURLConnection http_conn = (HttpURLConnection)connection;
    		responseCode = http_conn.getResponseCode();
    		
    		BufferedReader reader;
    		if(responseCode == HTTP_CODE_OK || responseCode == HTTP_CODE_CREATED)
    			reader = new BufferedReader(new InputStreamReader(http_conn.getInputStream()));
    		else
    			reader = new BufferedReader(new InputStreamReader(http_conn.getErrorStream()));
    		String response = reader.readLine();
    		
    		if(DEBUG)
    			System.out.println(response);
    		
    		reader.close();
    		return response;
    	}
    	catch (Exception e) {throw e;}
    }

}
