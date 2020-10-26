package v2.api.polygon.websocket;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImVec4;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import v1.stock.test.bar.barTest;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class polygonListener extends WebSocketClient {
	static boolean isRed = false;

	private String ticker;
	private String key;
	private List<Double> printPrice = new CopyOnWriteArrayList<Double>();
	private List<Integer> printVolume = new CopyOnWriteArrayList<Integer>();
	private List<String> printTime2 = new CopyOnWriteArrayList<String>();
	static double bidPrice;
	static int bidSize;
	static double askPrice;
	static int askSize;
	public static double price;



	public polygonListener(URI serverUri, String ticker, String key) {
		super(serverUri);
		this.ticker = ticker;
		this.key = key;
	}

	@Override
	public void onOpen(ServerHandshake handshakedata) {
		send("{\"action\":\"auth\",\"params\":\"" + key +"\"}");

		System.out.println("new connection opened");
	}

	@Override
	public void onClose(int code, String reason, boolean remote) {
		System.out.println("closed with exit code " + code + " additional info: " + reason);
	}

	@Override
	public void onMessage(String message) {
		System.out.println("received message: " + message);

		if (message.equals("[{\"ev\":\"status\",\"status\":\"auth_success\",\"message\":\"authenticated\"}]")) {
			send("{\"action\":\"subscribe\",\"params\":\"T." + ticker + ", " + "Q." + ticker + "\"}");
		} else {
			JSONArray j = new JSONArray(message);
			JSONObject json = j.getJSONObject(0);
			if (json.get("ev").equals("Q")) {
				bidPrice = json.getDouble("bp");
				bidSize = json.getInt("bs");
				askPrice = json.getDouble("ap");
				askSize = json.getInt("as");
			} else if (json.get("ev").equals("T")) {

				Date date = new Date(json.getLong("t"));
				SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
				sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
				String formattedDate = sdf.format(date);

				printTime2.add(formattedDate);
				printVolume.add(json.getInt("s"));
				printPrice.add(json.getDouble("p"));
				price = json.getDouble("p");
				barTest.priceNow = price;
				barTest.bidNow = bidPrice;
				barTest.askNow = askPrice;
				barTest.liveBar.update(price);
			}
		}
	}

	public void replaceTicker(String ticker){
		if(ticker.equals(this.ticker)){
			System.out.println("ur dumb");
		} else {
			send("{\"action\":\"unsubscribe\",\"params\":\"T." + this.ticker + ", " + "Q." + this.ticker + "\"}");

			this.ticker = ticker;
			printPrice.clear();
			printVolume.clear();
			printTime2.clear();
			bidPrice = 0;
			bidSize = 0;
			askPrice = 0;
			askSize = 0;
			price = 0;
			send("{\"action\":\"subscribe\",\"params\":\"T." + ticker + ", " + "Q." + ticker + "\"}");
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


	public void renderListener(String ticker, JImGui imGui) throws URISyntaxException {
		//t = trade
		//q = quote
		//am = minute bars
		//T.(ticker here), Q.(ticker here), AM.(ticker here)

		if (printPrice.size() >= 200) {
			for(int i = 0; i <= printPrice.size() - 100; i++){
				printPrice.remove(i);
				printTime2.remove(i);
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
				imGui.text(printTime2.get(i));
				imGui.nextColumn();

			} else if(askPrice == printPrice.get(i)){
				imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), String.valueOf(printPrice.get(i)));
				imGui.nextColumn();
				imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), String.valueOf(printVolume.get(i)));
				imGui.nextColumn();
				imGui.text(printTime2.get(i));
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
				imGui.text(printTime2.get(i));
				imGui.nextColumn();

			}
		}
		imGui.end();
	}

	public static double getPrice() {
		return price;
	}

	public void setTicker(String ticker){
		this.ticker = ticker;
	}

	public static boolean isRed() {
		return isRed;
	}
}
