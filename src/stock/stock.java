package stock;

import stock.alpaca.alpacaIndicatorData;
import stock.test.bar.bar;
import stock.test.bar.liveBar;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class stock {
	private static String apiKey = "AKKD80XYTBL1XEVJ7ZQI";
	private static String apiSecret = "m1cQC82sSM7dTowwQB1fILwo075KOzEDoEEXptcF";
	private static String pApiKey = "PKWZ29UVJ5EE2IGSHN6C";
	private static String pSecret = "4dxBWhILHEsxaQf/62LYkDi30pM4UCpqC/1dTBCz";

	private static List<bar> bars = new CopyOnWriteArrayList< bar >();
	//private static stock.test.bar.liveBar liveBar = new liveBar(0, 0, 0, 0, false);

	private static List <alpacaIndicatorData> sma10 = new CopyOnWriteArrayList<alpacaIndicatorData>();
	private static List <alpacaIndicatorData> sma20 = new CopyOnWriteArrayList<alpacaIndicatorData>();
	private static List <alpacaIndicatorData> rsi = new CopyOnWriteArrayList<alpacaIndicatorData>();
	private static List <alpacaIndicatorData> rsiScotch = new CopyOnWriteArrayList<alpacaIndicatorData>();
	private static List <alpacaIndicatorData> wld = new CopyOnWriteArrayList<alpacaIndicatorData>();


	public stock(String apiKey, String apiSecret, String pApiKey, String pSecret){

	}
}
