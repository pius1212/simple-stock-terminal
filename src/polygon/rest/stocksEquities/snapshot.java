package polygon.rest.stocksEquities;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class snapshot {
	private static String baseURL 		= "https://api.polygon.io/v2/snapshot/";
	private static String symbols 		= "symbols/";
	private static String apiKeyPeram 	= "?apiKey="; //IMPORTANT, ALWAYS FIRST

	public static JSONObject getSnapshotAllTickers(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "locale/us/markets/stocks/tickers" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	public static JSONObject getSnapshotSingleTicker(String ticker, String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "locale/us/markets/stocks/tickers/" + ticker + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	public static JSONObject getSnapshotGainers(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "locale/us/markets/stocks/gainers" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}

	public static JSONObject getSnapshotLosers(String apiKey) throws IOException {
		return new JSONObject(IOUtils.toString(new URL(baseURL + "locale/us/markets/stocks/losers" + apiKeyPeram + apiKey), StandardCharsets.UTF_8));
	}
}
