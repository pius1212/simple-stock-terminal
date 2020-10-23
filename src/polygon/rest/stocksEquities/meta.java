package polygon.rest.stocksEquities;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class meta {
	private static String baseURL 		= "https://api.polygon.io/v1/meta/";
	private static String symbols 		= "symbols/";
	private static String apiKeyPeram 	= "?apiKey="; //IMPORTANT, ALWAYS FIRST

	//STOCKS AND EQUITIES
	public static JSONObject getStockExchanges(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "exchanges" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}
}
