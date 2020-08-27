package test.polygon.rest.stocksEquities;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class aggs {
	private static String baseURL 		= "https://api.polygon.io/v2/aggs/";
	private static String grouped		= "grouped/";
	private static String apiKeyPeram 	= "?apiKey="; //IMPORTANT, ALWAYS FIRST

	public static JSONObject getPrevClose(String ticker, String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "ticker/" + ticker + "/prev" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	public static JSONObject getPrevClose(String ticker, boolean unadjusted, String apiKey) throws IOException {
		String unadjustedPeram = "&unadjusted=";
		return new JSONObject(IOUtils.toString(new URL(baseURL + "ticker/" + ticker + "/prev" + apiKeyPeram + apiKey + unadjustedPeram + unadjusted), StandardCharsets.UTF_8));
	}

//	public static JSONObject geBars(){
//
//	}
}
