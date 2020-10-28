package v2.api.iex.markets;

import org.json.JSONObject;

import v2.global.json.jsonReader;
import v2.api.iex.reference.symbols;

import java.io.File;
import java.io.IOException;

public class book {
	public static JSONObject deep(String ticker) throws IOException {
		JSONObject data = new JSONObject();
		if (symbols.checkSymbol(ticker)){
			data = jsonReader.readJsonFromUrl("https://api.iextrading.com/1.0/deep?symbols=" + ticker);
		} else if(data.getInt("lastUpdated") == 0){
			data = new JSONObject(new File("iexTickerError.json"));
		}

		return data;
	}
}
