package v2.api.polygon.rest.reference;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class meta {
	private static String baseURL 		= "https://api.polygon.io/v1/meta/";
	private static String symbols 		= "symbols/";
	private static String apiKeyPeram 	= "?apiKey="; //IMPORTANT, ALWAYS FIRST

	//REFERENCE
	public static JSONObject getTickerDetails(String ticker, String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + symbols + ticker + "/company" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	public static JSONObject getTickerNews(String ticker, String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + symbols + ticker + "/news" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	public static JSONObject getTickerNews(String ticker, int perPage, String apiKey) throws IOException {
		String perPagePeram = "?perpage=";

		return new JSONObject(IOUtils.toString(new URL(baseURL + symbols + ticker + "/news" + apiKeyPeram + apiKey + perPagePeram + perPage), StandardCharsets.UTF_8));
	}

	public static JSONObject getTickerNews(String ticker, int perPage, int page, String apiKey) throws IOException {
		String perPagePeram = "&perpage=";
		String pagePeram = "&page=";

		return new JSONObject(IOUtils.toString(new URL(baseURL + symbols + ticker + "/news" + apiKeyPeram + apiKey + perPagePeram + perPage + pagePeram + page), StandardCharsets.UTF_8));
	}
}
