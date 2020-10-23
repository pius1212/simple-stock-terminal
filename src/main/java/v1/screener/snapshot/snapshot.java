package v1.screener.snapshot;

import org.json.JSONArray;
import org.json.JSONObject;

public class snapshot {
	snapshotObject[] snapshotObjects;

	public snapshot(JSONObject jsonObject){
		JSONArray ja = jsonObject.getJSONArray("tickers");

		snapshotObjects = new snapshotObject[ja.length()];
		for (int i = 0; i <= ja.length() - 1; i++){
			snapshotObjects[i] = new snapshotObject(ja.getJSONObject(i));
		}
	}

	public snapshotObject[] getSnapshotObjects() {
		return snapshotObjects;
	}
}
