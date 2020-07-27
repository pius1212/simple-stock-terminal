package stock.alpaca;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class alpacaRESTConnection {
	public static String openLongMarket(String token, String secret, String ticker, int qty, String tof) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		String json = new StringBuilder()
				.append("{")
				.append("\"symbol\":\"" + ticker + "\",")
				.append("\"qty\":\"" + qty + "\",")
				.append("\"side\":\"buy\",")
				.append("\"type\":\"market\",")
				.append("\"time_in_force\":\"" + tof + "\"")
				.append("}").toString();

		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create("https://paper-api.alpaca.markets/v2/orders"))
				.header("APCA-API-KEY-ID", token)
				.header("APCA-API-SECRET-KEY", secret)
				.header("Content-Type", "application/json")
				.build();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}

	public static String openShortMarket(String token, String secret, String ticker, int qty, String tos) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		String json = new StringBuilder()
				.append("{")
				.append("\"symbol\":\"" + ticker + "\",")
				.append("\"qty\":\"" + qty + "\",")
				.append("\"side\":\"sell\",")
				.append("\"type\":\"market\",")
				.append("\"time_in_force\":\"" + tos + "\"")
				.append("}").toString();

		HttpRequest request = HttpRequest.newBuilder()
				.POST(HttpRequest.BodyPublishers.ofString(json))
				.uri(URI.create("https://paper-api.alpaca.markets/v2/orders"))
				.header("APCA-API-KEY-ID", token)
				.header("APCA-API-SECRET-KEY", secret)
				.header("Content-Type", "application/json")
				.build();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}

	public static void close(String ticker, String token, String secret) throws IOException, InterruptedException {
		//TODO: m8 you did a get request
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.DELETE()
				.uri(URI.create("https://paper-api.alpaca.markets/v2/positions/" + ticker))
				.header("APCA-API-KEY-ID", token)
				.header("APCA-API-SECRET-KEY", secret)
				.header("Content-Type", "application/json")
				.build();

		client.send(request, HttpResponse.BodyHandlers.ofString());
	}

	public static String getOrders(String token, String secret) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://paper-api.alpaca.markets/v2/orders"))
				.header("APCA-API-KEY-ID", token)
				.header("APCA-API-SECRET-KEY", secret)
				.build();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}

	public static String getPositions(String token, String secret) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://paper-api.alpaca.markets/v2/positions"))
				.header("APCA-API-KEY-ID", token)
				.header("APCA-API-SECRET-KEY", secret)
				.build();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}

	public static String getCandle(String token, String secret, String ticker, int limit, String timeframe) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create("https://data.alpaca.markets/v1/bars/" + timeframe + "?symbols=" + ticker + "&limit=" + limit))
				.header("APCA-API-KEY-ID", token)
				.header("APCA-API-SECRET-KEY", secret)
				.build();

		var response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}
}
