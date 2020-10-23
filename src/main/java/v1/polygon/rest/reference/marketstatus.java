package v1.polygon.rest.reference;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class marketstatus {
	private String baseURL = "https://api.polygon.io/v1/marketstatus/";
	private static String apiKeyPeram = "?apiKey="; //IMPORTANT, ALWAYS FIRST

	public JSONObject getMarketStatus(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "now" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	public JSONObject getUpcomingHoliday(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "upcoming" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}
}