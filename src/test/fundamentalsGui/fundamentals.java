package test.fundamentalsGui;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class fundamentals {
	private String ticker;
	private String token;

	public fundamentals(String ticker, String token) throws IOException {
		String companyProfile = getCompanyProfile(ticker, token);
	}

	public static String getCompanyProfile(String ticker, String token) throws IOException {
		JSONObject json = new JSONObject(IOUtils.toString(new URL("https://finnhub.io/api/v1/stock/profile2?symbol="+ticker+"&token=" + token), StandardCharsets.UTF_8));
		System.out.println(json.toString());
		return json.toString();
	}
}
