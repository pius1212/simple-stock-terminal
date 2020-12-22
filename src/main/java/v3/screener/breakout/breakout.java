package v3.screener.breakout;

import org.json.JSONObject;
import v3.global.api.apiCredentials;
import v3.api.polygon.rest.stocksEquities.*;
import v3.global.parse.csvItem;
import v3.global.parse.csv;

import java.io.IOException;
import java.util.LinkedList;

public class breakout {
	private LinkedList<breakoutItem> items = new LinkedList<breakoutItem>();
	private apiCredentials credentials = new apiCredentials();
	private String[] tickers;

	public breakout(/*LinkedList<breakoutItem> items,*/ apiCredentials credentials) throws IOException {
		//this.items = items;
		this.credentials = credentials;

		//parse csv
		csvItem[] tickersBasicInfo = csv.parse("src/main/resources/watch.csv");
		String[] tickers = new String[tickersBasicInfo.length];

		int i = 0;
		for(csvItem item : tickersBasicInfo){
			tickers[i] = item.getItems()[0];
			i++;
		}

		this.tickers = tickers;
	}

	public void update() throws IOException {


		JSONObject jo = snapshot.getSnapshotAllTickers(credentials.getPolygonKey(), tickers);
		System.out.println(jo.toString());
	}
}
