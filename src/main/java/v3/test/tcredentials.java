package v3.test;

import org.json.JSONObject;
import v3.global.json.jsonReader;

import java.net.URI;
import java.net.URISyntaxException;

public class tcredentials {
	public static void main(String[] args) throws URISyntaxException {
//		apiCredentials api = new apiCredentials();
//
//		System.out.println(api.getPolygonKey());


		JSONObject data = jsonReader.readJSONObjectFromFile("/data.json");
		System.out.println(data.toString());
	}
}
