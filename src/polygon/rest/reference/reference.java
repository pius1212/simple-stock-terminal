package polygon.rest.reference;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class reference {
	private static String baseURL 		= "https://api.polygon.io/v2/reference/";
	private static String apiKeyPeram 	= "?apiKey="; //IMPORTANT, ALWAYS FIRST

	//TODO: continue when finding out how to code :o
	//shh ik this is a very insecure way to code lol
	public static JSONObject getTickers(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "tickers" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	//ticker types
	public static JSONObject getTickerTypes(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "types" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	//get market types
	public static JSONObject getMarkets(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "markets" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	//get locales(basically regions)
	public static JSONObject getLocales(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "locales" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	//get stock splits
	public static JSONObject getStockSplits(String ticker, String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "splits/" + ticker + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	//get stock dividends
	public static JSONObject getStockDividends(String ticker, String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "dividends/" + ticker + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	//get stock financials
	//TODO: finish when you learn more
	public static JSONObject getStockFinancials(String ticker, String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "financials/" + ticker + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	public static JSONObject getStockFinancials(String ticker, String apiKey, int limit) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "financials/" + ticker + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	public static JSONObject getStockFinancials(String ticker, String apiKey, String type) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "financials/" + ticker + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}


}
