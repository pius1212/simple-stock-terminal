package stock.alpaca;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImVec4;
import org.ice1000.jimgui.flag.JImCondition;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import stock.test.bar.barHandler;
import stock.test.bar.barTest;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class alpacaPositions extends barTest {
    public static void updatePositionsAndOrders(String token, String secret) throws IOException, InterruptedException {
//        orders = getOrders(token, secret);
        positions = getPositions(token, secret);
        System.out.println(positions);
    }

    public static void renderPositionsAndOrders(JImGui imGui, double price, int buy, int sell, String time) throws IOException, InterruptedException {
//        JSONArray ja1 = new JSONArray(orders);
        try{
            JSONArray ja2 = new JSONArray(positions);

            imGui.setNextWindowPos(0, 760);
            imGui.setWindowSize("Bar Chart Test", 1000, 200);
            imGui.begin("Orders & Positions");

            imGui.text(time);

            imGui.columns(6, "ordersAndPos", true);

            imGui.text("Order/Pos");
            imGui.nextColumn();
            imGui.text("Symbol");
            imGui.nextColumn();
            imGui.text("Side");
            imGui.nextColumn();
            imGui.text("Avg Price");
            imGui.nextColumn();
            imGui.text("Qty");
            imGui.nextColumn();
            imGui.text("Unrealized P/L");
            imGui.nextColumn();
            imGui.separator();

            if(alpacaOrder.hasPositions()){
//            JSONObject jo1 = ja1.getJSONObject(0);
                for(int i = 0; i < ja2.length(); i++){
                    JSONObject jo2 = ja2.getJSONObject(i);

                    imGui.text("Position");
                    imGui.nextColumn();
                    imGui.text(jo2.getString("symbol"));
                    imGui.nextColumn();
                    JImVec4 color = JImVec4.fromHSV(7.0f, 0.8f, 0.8f);
                    if(jo2.getString("side").equals("sell")){
                        color = JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f);
                    }
                    imGui.textColored(color, jo2.getString("side"));
                    imGui.nextColumn();
                    imGui.text(jo2.getString("avg_entry_price"));
                    imGui.nextColumn();
                    imGui.text(jo2.getString("qty"));
                    imGui.nextColumn();
                    JImVec4 color1 = JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f);
                    if(Double.parseDouble(jo2.getString("unrealized_pl")) <= 0){
                        color1 = JImVec4.fromHSV(7.0f, 0.8f, 0.8f);
                    }
                    imGui.textColored(color1, jo2.getString("unrealized_pl"));
                    imGui.nextColumn();
                }
            }
            imGui.end();
        } catch (JSONException ignored){

        }

    }

    private static String getOrders(String token, String secret) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://paper-api.alpaca.markets/v2/orders"))
                .header("APCA-API-KEY-ID", token)
                .header("APCA-API-SECRET-KEY", secret)
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    private static String getPositions(String token, String secret) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://paper-api.alpaca.markets/v2/positions"))
                .header("APCA-API-KEY-ID", token)
                .header("APCA-API-SECRET-KEY", secret)
                .build();

        var response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }
}
