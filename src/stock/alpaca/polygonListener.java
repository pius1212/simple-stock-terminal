package stock.alpaca;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONObject;
import stock.JsonReader;
import stock.test.bar.barTest;

import java.net.URI;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class polygonListener extends WebSocketClient {
	private String ticker;
	private String key;
	private List<Double> printPrice = new CopyOnWriteArrayList<Double>();
	private List<Integer> printVolume = new CopyOnWriteArrayList<Integer>();
	private List<Long> printTime = new CopyOnWriteArrayList<Long>();
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
		//System.out.println("received message: " + message);

		if (message.equals("[{\"ev\":\"status\",\"status\":\"auth_success\",\"message\":\"authenticated\"}]")) {
			send("{\"action\":\"subscribe\",\"params\":\"T." + ticker + "\"}");
			send("{\"action\":\"subscribe\",\"params\":\"Q." + ticker + "\"}");
		} else {
			JSONArray j = new JSONArray(message);
			JSONObject json = j.getJSONObject(0);
			if (json.get("ev").equals("Q")) {
				bidPrice = json.getDouble("bp");
				bidSize = json.getInt("bs");
				askPrice = json.getDouble("ap");
				askSize = json.getInt("as");
			} else if (json.get("ev").equals("T")) {
				printTime.add(json.getLong("t"));
				printVolume.add(json.getInt("s"));
				printPrice.add(json.getDouble("p"));
				price = json.getDouble("p");
				barTest.priceNow = price;
				barTest.liveBar.update(price);
			}
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
		try {
			for(int i = 0; i <= printPrice.size() - 1; i++){
				al.addLine(printPrice.get(i), printVolume.get(i), printTime.get(i), bidPrice, bidSize, askSize, askPrice);
			}

			printPrice.clear();
			printVolume.clear();
			printTime.clear();
		} catch (ArrayIndexOutOfBoundsException ignored){

		}

	}
}
