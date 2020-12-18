package v3.api.alpaca;

import v1.stock.test.bar.barTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class alpacaOrder extends barTest {
    public static void main(String[] args) throws IOException, InterruptedException {
        String APIkey = "PKZYUI1VIOYF8T3CGHGA";
        String secret = "9QUsGwnAIq8FMLGkCARMoGkFzfATJB/A6fW6fETz";
        String ticker = "SPY";
    }
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
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create("https://paper-api.alpaca.markets/v2/positions/" + ticker))
                .header("APCA-API-KEY-ID", token)
                .header("APCA-API-SECRET-KEY", secret)
                .header("Content-Type", "application/json")
                .build();

       client.send(request, HttpResponse.BodyHandlers.ofString());
    }

    public static boolean hasOrders(String token, String secret) throws IOException, InterruptedException {
        boolean hasOrders = true;

        if (orders.equals("[]")){
            hasOrders = false;
        }
        return hasOrders;
    }

    public static boolean hasPositions() throws IOException, InterruptedException {
        boolean hasPositions = true;
        if (positions.equals("[]")){
            hasPositions = false;
        }
        return hasPositions;
    }
}
