package v3.api.iex.reference;

import java.io.IOException;
import java.util.LinkedList;

import org.json.JSONArray;
import v2.global.json.jsonReader;

public class symbols {
	private static LinkedList<String> symbols = new LinkedList<String>();

	private static void fetchSymbols() throws IOException {
		JSONArray data = jsonReader.readJsonFromUrlArray("https://api.iextrading.com/1.0/ref-data/symbols");
		for(int i = 0; i < data.length(); i++){
			symbols.add(data.getJSONObject(i).getString("symbol"));
		}
	}

	public static LinkedList<String> getSymbols() throws IOException {
		fetchSymbols();
		return symbols;
	}

	public static boolean checkSymbol(String ticker) throws IOException {
		fetchSymbols();

		boolean isInDB = false;
		for (String s : symbols) {
			if (ticker.equals(s)){
				isInDB = true;
				break;
			}
		}

		return isInDB;
	}
}
