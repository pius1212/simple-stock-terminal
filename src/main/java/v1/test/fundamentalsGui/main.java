//package v1.test.fundamentalsGui;
//
//import org.ice1000.jimgui.*;
//import org.ice1000.jimgui.flag.JImCondition;
//import org.ice1000.jimgui.util.JniLoader;
//import org.json.JSONArray;
//import v1.stock.stockListener;
//import v1.stock.getStockData;
//
//import java.io.IOException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.util.*;
//
//import java.time.LocalDateTime; // Import the LocalDateTime class
//import java.time.format.DateTimeFormatter; // Import the DateTimeFormatter class
//
//public class v2.main {
//	public static List<etf> etfList = new ArrayList<>();
//	static String newsData;
//	static boolean weekend = false;
//
//	public static void v2.main(String... args) throws IOException, InterruptedException, URISyntaxException {
//		System.out.println("______________________________________________________");
//
//		String token = "bqk6knnrh5r9t8htfof0";
//		String token2 = "brg62afrh5rfc7bjejg0";
//
//		JniLoader.load();
//
//		stockListener client = new stockListener(new URI("wss://ws.finnhub.io?token=" + token), "SPY");
//		client.connect();
//		stockListener client1 = new stockListener(new URI("wss://ws.finnhub.io?token=" + token2), "GNUS");
//		client1.connect();
//
//		try (JImGui imGui = new JImGui()) {
//			Timer timer = new Timer();
//			if (!weekend) {
//				imGui.setBackground(JImVec4.fromHSV(0f, 0f, 0f));
//				newsETF.updateNews(token);
//
//				addETF("SPY", token, "30");
//				addETF("GNUS", token, "30");
//
//				for (etf s : etfList) {
//					try {
//						s.update();
//						Thread.sleep(1500);
//					} catch (IOException | InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//				Thread.sleep(1000);
//
//				for (etf s : etfList) {
//					try {
//						s.updateChart();
//						Thread.sleep(1500);
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
//				Thread.sleep(1000);
//
//
//				timer.schedule(new TimerTask() {
//					public void run() {
//						for (etf s : etfList) {
//							try {
//								s.updateChart();
//								Thread.sleep(1000);
//							} catch (IOException | InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}, 0, etfList.size() * 300000);
//				Thread.sleep(1000);
//
//				timer.schedule(new TimerTask() {
//					public void run() {
//						for (etf s: etfList) {
//							try {
//								s.setNews(newsETF.getStockNews(s.getTicker(), token));
//								Thread.sleep(1000);
//							} catch (IOException | InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//					}
//				}, 0, 3600000);
//				Thread.sleep(1000);
//
//				timer.schedule(new TimerTask() {
//					public void run() {
//						try {
//							newsETF.updateNews(token);
//						} catch (IOException e) {
//							e.printStackTrace();
//						}
//					}
//				}, 0, 43200000);
//				Thread.sleep(1000);
//
//				timer.schedule(new TimerTask() {
//					public void run() {
//						for (etf s : etfList) {
//							try {
//								s.update();
//								Thread.sleep(2000);
//							} catch (IOException | InterruptedException e) {
//								e.printStackTrace();
//							}
//						}
//
//					}
//				}, 0, etfList.size() * 300000);
//				Thread.sleep(1000);
//
//				//______________________RUN MAIN GUI_________________________________________________________________________
//				imGui.initBeforeMainLoop();
//				while (!imGui.windowShouldClose()) {
//					LocalDateTime myDateObj = LocalDateTime.now();
//					DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("HH:mm:ss");
//					String formattedDate = myDateObj.format(myFormatObj);
//
//					if (formattedDate.equals("16:00:00")) {
//						SoundJLayerX soundToPlay = new SoundJLayerX("res/nyse-opening-bell.mp3");
//
//						soundToPlay.play();
//					}
//					imGui.initNewFrame();
//					imGui.text("MGM 23c 6/11");
//
//
//					if (!weekend) {
//						assert false;
//						for (etf s : etfList) {
//							s.renderPrice(imGui);
//						}
//
//						for (etf s : etfList) {
//							s.renderDetailed(imGui);
//						}
//					}
//
//					newsETF.renderNews(imGui);
//
//					imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);
//					imGui.begin("Time");
//					imGui.setWindowFontScale(2.0f);
//					imGui.textColored(JImVec4.fromHSV(4 / 7.0f, 0.8f, 0.8f), formattedDate);
//					imGui.end();
//
//					imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);
//					imGui.begin("SPY Print");
//					client.renderPrintSheet(imGui);
//					imGui.end();
//
//					imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);
//					imGui.begin("GNUS Print");
//					client1.renderPrintSheet(imGui);
//					imGui.end();
//
//					imGui.render();
//				}
//			}
//		}
//	}
//
//	private static void addETF(String ticker, String token, String resolution) throws IOException, InterruptedException {
//		String jsonData = "";
//
//		String x = "";
//		String a = "";
//
//		String[] y = null;
//		String[] c = null;
//
//
//		Thread.sleep(1000);
//
//		jsonData = getStockData.getCandle(ticker, token, 1400, "close", resolution);
//
//		x = getStockData.getQuote(ticker, token);
//		y = x.split(",", -1);
//
//		Thread.sleep(1000);
//
//		a = getStockData.dayHighLow(ticker, token);
//		c = a.split(",", -1);
//
//
//		Thread.sleep(1000);
//		JSONArray obj = new JSONArray(jsonData);
//		float[] z = new float[obj.length()];
//
//		for (int i = 0; i < obj.length(); i++) {
//			z[i] = obj.getFloat(i);
//		}
//
//
//		JImVec4 color;
//
//		color = JImVec4.fromHSV(7.0f, 0.8f, 0.8f);
//
//		assert false;
//		if (y[2].equals("true"))
//			color = JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f);
//
//		etf etf = new etf(ticker, y[0], y[1], color, token, z, c[0], c[1], newsETF.getStockNews(ticker, token), resolution, y[3]);
//
//		etfList.add(etf);
//	}
//}