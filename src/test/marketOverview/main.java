package test.marketOverview;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;
import org.json.JSONObject;

import java.io.IOException;

public class main {
	public static String APIkey = "AKKD80XYTBL1XEVJ7ZQI";
	public static String secret = "m1cQC82sSM7dTowwQB1fILwo075KOzEDoEEXptcF";
	public static String pAPIkey = "PK1NVCKPN2WN1OKUROU1";
	public static String psecret = "oGT88PQDZPxjlayLyxgWDoti2hLpHo3W7s9gFmSD";

	public static void main(String[] args) throws IOException {
		JSONObject jo = test.polygon.rest.stocksEquities.snapshot.getSnapshotAllTickers(APIkey);

		JniLoader.load();
		try(JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();


				imGui.render();
			}
		}
	}
}
