package stock;

import java.net.URI;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImVec4;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import stock.alpaca.alpacaListener;
import stock.etf.etf;

import stock.test.bar.barTest;

public class stockListener extends WebSocketClient{

    private String ticker;
    public boolean isRed = false;
    private List<Double> printPrice = new CopyOnWriteArrayList<Double>();
    private List<Integer> printVolume = new CopyOnWriteArrayList<Integer>();
    private List<Long> printTime = new CopyOnWriteArrayList<Long>();

    public stockListener(URI serverUri, String ticker) {
        super(serverUri);
        this.ticker = ticker;
    }

    public stockListener(URI serverUri, Draft draft, String ticker) {
        super(serverUri, draft);
        this.ticker = ticker;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("{\"type\":\"subscribe\",\"symbol\":\"" + ticker +"\"}");

        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
       // System.out.println("received message: " + message);

        if(!message.equals("{\"type\":\"ping\"}")){
                JSONObject json = new JSONObject(message);
                JSONArray json1 = json.getJSONArray("data");
                JSONObject json2 = json1.getJSONObject(0);
                Double x = (Double) json2.getDouble("p");
                Integer y = json2.getInt("v");
                Long z = json2.getLong("t");

                 printPrice.add(x);
                 printVolume.add(y);
                 printTime.add(z);
        }
    }

    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

    public void forwardData(alpacaListener al){
        for(int i = printPrice.size() - 1; i >= 0; i--){
            al.addLine(printPrice.get(i), printVolume.get(i), printTime.get(i));
        }

        printPrice.clear();
        printVolume.clear();
        printTime.clear();
    }

    public void renderPrintSheet(JImGui imGui){
        imGui.columns(3, "x", true);
        double temp = 0;

        for (Double d : printPrice) {
            if (temp > d){
                imGui.textColored(JImVec4.fromHSV(7.0f, 0.8f, 0.8f), String.valueOf(d));
                temp = d;
                isRed = true;
            } else if(temp < d){
                imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), String.valueOf(d));
                temp = d;
                isRed = false;
            } else {
                if(isRed){
                    imGui.textColored(JImVec4.fromHSV(7.0f, 0.8f, 0.8f), String.valueOf(d));
                } else {
                    imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), String.valueOf(d));
                }
                temp = d;
            }
        }
        imGui.nextColumn();
        for (Integer i : printVolume){
            if (temp > i){
                imGui.textColored(JImVec4.fromHSV(7.0f, 0.8f, 0.8f), String.valueOf(i));
                temp = i;
            } else if(temp < i){
                imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), String.valueOf(i));
                temp = i;
            } else {
                if(isRed){
                    imGui.textColored(JImVec4.fromHSV(7.0f, 0.8f, 0.8f), String.valueOf(i));
                } else {
                    imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), String.valueOf(i));
                }
                temp = i;
            }
        }
        imGui.nextColumn();
        for (long l : printTime) {
            long unixSeconds = l;
            Date date = new java.util.Date(unixSeconds);
            SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
            sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
            String formattedDate = sdf.format(date);

            imGui.text(formattedDate);
        }

        if(printPrice.size() >= 400){
            for(int i = 0; i <= 199; i++){
                printPrice.remove(i);
                printVolume.remove(i);
                printTime.remove(i);
            }
        }
        imGui.nextColumn();
        imGui.setScrollHereY(1.0f);
    }
}