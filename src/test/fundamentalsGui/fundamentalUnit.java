package test.fundamentalsGui;

import org.json.JSONObject;

public class fundamentalUnit {
	private String rawData;
	private String eps;
	private String dilutedEPS;
	private String revenue;
	private String grossProfit;
	private String assets;
	private String liabilities;
	private String longTermLiabilities;
	private String shortTermLiabilities;
	

	public fundamentalUnit(JSONObject report){
		this.rawData = report.toString();
	}

}
