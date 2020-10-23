package v1.stock;

import v1.stock.alpaca.alpacaIndicatorData;
import v1.stock.polygon.polygonListener;
import v1.stock.test.bar.bar;
import v1.stock.test.bar.liveBar;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class stock {
	private String apiKey = "";
	private String apiSecret = "";
	private String pApiKey = "";
	private String pSecret = "";

	private String ticker = "";

	private static List<bar> bars = new CopyOnWriteArrayList< bar >();
	private static liveBar liveBar = new liveBar(0, 0, 0, 0, false);

	private static List <alpacaIndicatorData> sma10 = new CopyOnWriteArrayList<alpacaIndicatorData>();
	private static List <alpacaIndicatorData> sma20 = new CopyOnWriteArrayList<alpacaIndicatorData>();
	private static List <alpacaIndicatorData> rsi = new CopyOnWriteArrayList<alpacaIndicatorData>();
	private static List <alpacaIndicatorData> rsiScotch = new CopyOnWriteArrayList<alpacaIndicatorData>();
	private static List <alpacaIndicatorData> wld = new CopyOnWriteArrayList<alpacaIndicatorData>();


	public stock(String apiKey, String apiSecret, String pApiKey, String pSecret, String ticker){
		this.apiKey = apiKey;
		this.apiSecret = apiSecret;
		this.pApiKey = pApiKey;
		this.pSecret = pSecret;
		this.ticker = ticker;
	}

	public void socket(polygonListener pl){
		pl.send("{\"action\":\"subscribe\",\"params\":\"T." + ticker + "\"}");
		pl.send("{\"action\":\"subscribe\",\"params\":\"Q." + ticker + "\"}");
	}

	public void render(){

	}

	public void run(){

	}
}
