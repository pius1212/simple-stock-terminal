package v2.objects.futures;

import org.json.JSONObject;

public class wikiContFutTick {
	private String datasetCode;
	private String databaseCode;
	private String name;
	private String description;
	private String refreshedAt;
	private String newestDate;
	private String columnNames;
	private String frequency;
	private String type;

	private String date;
	private float open;
	private float high;
	private float low;
	private float last;
	private float change;
	private float settle;
	private float volume;
	private float prevDayOI;

	//https://www.quandl.com/data/CHRIS/CME_ES1-E-mini-S-P-500-Futures-Continuous-Contract-1-ES1-Front-Month

	public wikiContFutTick(JSONObject jo){
		JSONObject data = jo.getJSONObject("dataset");
		datasetCode = data.getString("dataset_code");
		databaseCode = data.getString("dataset_code");



	}
}
