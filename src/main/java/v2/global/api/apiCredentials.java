package v2.global.api;

import org.json.JSONObject;

import java.io.File;

import v2.global.file.fileIO;

public class apiCredentials {
	private JSONObject data = new JSONObject(new File("data.json"));
	private JSONObject credentials = data.getJSONObject("credentials");

	private String alpacaKey = credentials.getString("alpacaKey");
	private String alpacaSecret = credentials.getString("alpacaSecret");
	private String polygonKey = credentials.getString("polygonKey");
	private String paperAlpacaKey = credentials.getString("paperAlpacaKey");
	private String paperAlpacaSecret = credentials.getString("paperAlpacaSecret");

	public String getAlpacaKey() {
		return alpacaKey;
	}

	public String getAlpacaSecret() {
		return alpacaSecret;
	}

	public String getPaperAlpacaKey() {
		return paperAlpacaKey;
	}

	public String getPaperAlpacaSecret() {
		return paperAlpacaSecret;
	}

	public String getPolygonKey() {
		return polygonKey;
	}

	public void setAlpacaKey(String alpacaKey) {
		credentials.put("alpacaKey", alpacaKey);
		fileHandler();
		this.alpacaKey = alpacaKey;
	}

	public void setAlpacaSecret(String alpacaSecret) {
		credentials.put("alpacaSecret", alpacaSecret);
		fileHandler();
		this.alpacaSecret = alpacaSecret;
	}

	public void setPaperAlpacaKey(String paperAlpacaKey) {
		credentials.put("polygonKey", polygonKey);
		fileHandler();
		this.paperAlpacaKey = paperAlpacaKey;
	}

	public void setPaperAlpacaSecret(String paperAlpacaSecret) {
		credentials.put("paperAlpacaKey", paperAlpacaKey);
		fileHandler();
		this.paperAlpacaSecret = paperAlpacaSecret;
	}

	public void setPolygonKey(String polygonKey) {
		credentials.put("paperAlpacaSecret", paperAlpacaSecret);
		fileHandler();
		this.polygonKey = polygonKey;
	}

	private void fileHandler(){
		data.put("credentials", new JSONObject(credentials.toString()));
		String jsonData = data.toString();

		fileIO.overwriteFile("data.json", jsonData);
	}
}
