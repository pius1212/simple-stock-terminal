package v1.stock.alpaca;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class alpacaBar {
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
