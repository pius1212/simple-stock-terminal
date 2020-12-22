package v3.api.alpaca.marketdata;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImVec4;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class alpacaListener extends WebSocketClient {
    static boolean isRed = false;

    private String ticker;
    private String key;
    private String secret;
    private static List < Double > printPrice = new CopyOnWriteArrayList < Double > ();
    private static List < Integer > printVolume = new CopyOnWriteArrayList < Integer > ();
    private static List < String > printTime = new CopyOnWriteArrayList < String > ();
    static double bidPrice;
    static int bidSize;
    static double askPrice;
    static int askSize;
    public static double price;

    public alpacaListener(URI serverUri, String ticker, String key, String secret) {
        super(serverUri);
        this.secret = secret;
        this.key = key;
        this.ticker = ticker;
    }

    public alpacaListener(URI serverUri, Draft draft, String key, String secret) {
        super(serverUri, draft);
        this.secret = secret;
        this.key = key;
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        send("{\"action\":\"authenticate\",\"data\":{\"key_id\":\"" + key + "\",\"secret_key\":\"" + secret + "\"}}");
        System.out.println(key);
        System.out.println(secret);
        System.out.println("new connection opened");
    }

    @Override
    public void onMessage(String s) {

    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

//    @Override
//    public void onMessage(String message) {
//        //System.out.println("received message: " + message);
//        if (message.equals("{\"stream\":\"authorization\",\"data\":{\"action\":\"authenticate\",\"status\":\"authorized\"}}")) {
//            send("{" + "\"action\": \"listen\"," + "\"data\": {" + " \"streams\": [\"T." + ticker + "\", \"Q." + ticker + "\", \"AM." + ticker + "\"]" + "}" + "}");
//        } else {
//            JSONObject json = new JSONObject(message);
//            JSONObject json1 = json.getJSONObject("data");
//
//            if (json1.get("ev").equals("Q")) {
//                bidPrice = json1.getDouble("p");
//                bidSize = json1.getInt("s");
//                askPrice = json1.getDouble("P");
//                askSize = json1.getInt("s");
//            } else if (json1.get("ev").equals("T")) {
//                long unixSeconds = json1.getLong("t");
//                Date date = new java.util.Date(Long.valueOf(unixSeconds / 1000000));
//                SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
//                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
//                String formattedDate = sdf.format(date);
//
//                printTime.add(formattedDate);
//                printVolume.add(json1.getInt("s"));
//                printPrice.add(json1.getDouble("p"));
//                price = json1.getDouble("p");
//                barTest.priceNow = price;
//                barTest.liveBar.update(price);
//
//            } else {
//                int volume = json1.getInt("v");
//                double open = json1.getDouble("o");
//                double close = json1.getDouble("c");
//                double high = json1.getDouble("h");
//                double low = json1.getDouble("l");
//                double vwap = json1.getDouble("vw");
//            }
//        }
//    }

//    @Override
//    public void onMessage(ByteBuffer message) {
//        boolean exist = false;
//        if(exist){
//            System.out.println("received ByteBuffer");
//        }
//    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

    public void addLine(double price, int volume, long time){
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);

        printPrice.add(price);
        printVolume.add(volume);
        printTime.add(formattedDate);
    }

    public void addLine(double price, int volume, long time, double bp, int bs, int as, double ap){
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
        String formattedDate = sdf.format(date);

        printPrice.add(price);
        printVolume.add(volume);
        printTime.add(formattedDate);

        bidPrice = bp;
        bidSize = bs;
        askPrice = ap;
        askSize = as;
    }

    public void renderListener(String ticker, JImGui imGui) throws URISyntaxException {
        //t = trade
        //q = quote
        //am = minute bars
        //T.(ticker here), Q.(ticker here), AM.(ticker here)

        if (printPrice.size() >= 200) {
            for(int i = 0; i <= printPrice.size() - 100; i++){
                printPrice.remove(i);
                printTime.remove(i);
                printVolume.remove(i);
            }
        }

            imGui.setNextWindowPos(1510, 220);
            imGui.setWindowSize("Print/Lvl1 Data", 410, 510);
            imGui.begin(ticker + " Print/Lvl1 Data ");

            imGui.columns(3, "printAndBA", true);

            imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), "Bid: " + bidPrice + " x " + bidSize*100);

            imGui.nextColumn();

            DecimalFormat df = new DecimalFormat("#.####");

            imGui.text("Spread: " + df.format((askPrice - bidPrice)));

            imGui.nextColumn();
            imGui.textColored(JImVec4.fromHSV(7.0f, 0.8f, 0.8f), "Ask: " + askPrice + " x " + askSize*100);

            imGui.nextColumn();
            imGui.separator();

            for (int i = printPrice.size() - 1; i >= 0; i--) {
                if(bidPrice == printPrice.get(i)){
                    imGui.textColored(JImVec4.fromHSV(7.0f, 0.8f, 0.8f), String.valueOf(printPrice.get(i)));
                    imGui.nextColumn();
                    imGui.textColored(JImVec4.fromHSV(7.0f, 0.8f, 0.8f), String.valueOf(printVolume.get(i)));
                    imGui.nextColumn();
                    imGui.text(printTime.get(i));
                    imGui.nextColumn();

                } else if(askPrice == printPrice.get(i)){
                    imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), String.valueOf(printPrice.get(i)));
                    imGui.nextColumn();
                    imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), String.valueOf(printVolume.get(i)));
                    imGui.nextColumn();
                    imGui.text(printTime.get(i));
                    imGui.nextColumn();

                } else {
                    imGui.text(String.valueOf(printPrice.get(i)));
                    imGui.nextColumn();
                    if(printVolume.get(i) % 100 == 0){
                        imGui.textColored(JImVec4.fromHSV(2 / 7.0f, 0.8f, 0.8f), String.valueOf(printVolume.get(i)));
                    } else {
                        imGui.text(String.valueOf(printVolume.get(i)));
                    }
                    imGui.nextColumn();
                    imGui.text(printTime.get(i));
                    imGui.nextColumn();

                }
            }
        imGui.end();
    }

    public static double getPrice() {
        return price;
    }

    public static boolean isRed() {
        return isRed;
    }
}