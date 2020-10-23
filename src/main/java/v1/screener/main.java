package v1.screener;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImVec4;
import org.ice1000.jimgui.util.JniLoader;
import v1.screener.snapshot.snapshot;
import v1.screener.snapshot.snapshotObject;
import v1.screener.snapshot.timer;

import java.io.IOException;

public class main {
	public static String APIkey = "AKKD80XYTBL1XEVJ7ZQI";
	public static String secret = "m1cQC82sSM7dTowwQB1fILwo075KOzEDoEEXptcF";
	public static String pAPIkey = "PK1NVCKPN2WN1OKUROU1";
	public static String psecret = "oGT88PQDZPxjlayLyxgWDoti2hLpHo3W7s9gFmSD";

	private static long delay = 3000;

	public static snapshot snapshot;

	static {
		try {
			snapshot = new snapshot(v1.polygon.rest.stocksEquities.snapshot.getSnapshotAllTickers(APIkey));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		int i = 0;
		timer.timer(delay, APIkey);
		JniLoader.load();
		try(JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();
				i++;

				imGui.text(String.valueOf(i));
				imGui.columns(3, "printAndBA", true);
				for (snapshotObject s: snapshot.getSnapshotObjects()) {
					imGui.text(s.getTicker());
					imGui.nextColumn();
					imGui.text(String.valueOf(s.getLastPrice()));
					imGui.nextColumn();
					if(s.getDayChangePerc() < 0){
						imGui.textColored(JImVec4.fromHSV(7.0f, 0.8f, 0.8f), s.getDayChangePerc() + "%");
					} else if(s.getDayChangePerc() > 0){
						imGui.textColored(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f), s.getDayChangePerc() + "%");
					} else {
						imGui.text(s.getDayChangePerc() + "%");
					}
					imGui.nextColumn();
					imGui.separator();
				}
				imGui.render();
			}
		}
	}
}

