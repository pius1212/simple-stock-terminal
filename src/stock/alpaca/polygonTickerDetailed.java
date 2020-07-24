package stock.alpaca;

import org.json.JSONObject;
import stock.JsonReader;
import stock.test.bar.barTest;

import java.io.IOException;

public class polygonTickerDetailed extends barTest {
	public static void getDetailed(String ticker, String APIKey) throws IOException {
		String endpoint = "https://api.polygon.io/v1/meta/symbols/" + ticker + "/company?apiKey=" + APIKey;

		detailed = JsonReader.readJsonFromUrl(endpoint);
	}
}
