package v1.stock.polygon;

import v1.stock.JsonReader;
import v1.stock.test.bar.barTest;

import java.io.IOException;

public class polygonTickerDetailed extends barTest {
	public static void getDetailed(String ticker, String APIKey) throws IOException {
		String endpoint = "https://api.polygon.io/v1/meta/symbols/" + ticker + "/company?apiKey=" + APIKey;
		System.out.println(endpoint);
		detailed = JsonReader.readJsonFromUrl(endpoint);
	}
}
