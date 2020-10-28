package v2.gui.data.book;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;
import org.json.JSONArray;
import org.json.JSONObject;
import v1.stock.alpaca.alpacaOrder;
import v2.api.iex.markets.book;

import java.io.IOException;

public class deep {
	private static JSONObject jo;
	private static JSONArray bid;
	private static JSONArray ask;

	static {
		try {
			jo = book.deep("GE");
			bid = jo.getJSONArray("bids");
			ask = jo.getJSONArray("asks");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {

		Thread t = new Thread(() -> {
			while (true) {
				try {
					Thread.sleep(300);
				} catch (InterruptedException ignored) {

				}

				try {
					jo = book.deep("GE");
					bid = jo.getJSONArray("bids");
					ask = jo.getJSONArray("asks");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		t.start();

		JniLoader.load();
		try(JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();
				imGui.columns(2, "Lvl2", true);

				float[] bidPrices = new float[bid.length()];
				float[] askPrices = new float[ask.length()];
				float[] bidSize = new float[bid.length()];
				float[] askSize = new float[ask.length()];


				for (int i = 0; i < bid.length(); i++){
					bidPrices[i] = bid.getJSONObject(i).getFloat("price");
					bidSize[i] = bid.getJSONObject(i).getFloat("size");
				}

				for (int i = 0; i < ask.length(); i++){
					askPrices[i] = ask.getJSONObject(i).getFloat("price");
					askSize[i] = ask.getJSONObject(i).getFloat("size");
				}

				imGui.render();
			}
		}
	}
}
