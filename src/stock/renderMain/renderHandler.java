package stock.renderMain;

import org.ice1000.jimgui.JImDrawList;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImGuiGen;
import org.ice1000.jimgui.NativeBool;
import org.json.JSONException;
import org.json.JSONObject;
import stock.alpaca.alpacaIndicatorData;
import stock.global.barInstance;
import stock.global.colors;
import stock.test.bar.bar;
import stock.test.bar.barHandler;
import stock.test.bar.liveBar;

import java.awt.*;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.CopyOnWriteArrayList;

import static stock.algo.custom.shortcuts.wld;

public class renderHandler{

	public static void renderBars(String ticker,
								  String APIkey, String secret,
								  JImGui imGui,
								  liveBar liveBar,
								  NativeBool RSI, NativeBool WLD,
								  CopyOnWriteArrayList<bar> bars,
								  barInstance barInstance
	) throws IOException, InterruptedException {
		//rgb as int below very important
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ss");
		String formattedDate = myDateObj.format(myFormatObj);

		if (formattedDate.equals("01") && barInstance.isRequestBar()) {
			barHandler.updateBars(ticker, APIkey, secret);
			liveBar = new liveBar(0, 0, bars.get(bars.size() - 1).getClose(), 0, false);
			barInstance.setRequestBar(false);
		}

		if(formattedDate.equals("03")){
			barInstance.setRequestBar(true);
		}


		imGui.setNextWindowPos(0, 20);
		imGui.setWindowSize("Bar Chart Test", 1510, 740);
		imGui.begin("Bar Chart Test");
		float cursorPosX = JImGui.getWindowPosX();
		float cursorPosY = JImGui.getWindowPosY();

		JImDrawList dl = imGui.findWindowDrawList();

		double pl = bars.get(0).getLow();
		double ph = bars.get(0).getHigh();
		alpacaIndicatorData[] x = wld.toArray(new alpacaIndicatorData[0]);
		double[] adjUse = new double[100];

		for (int i = 1; i <= 99; i++) {
			if (bars.get(i).getLow() < pl) {
				pl = bars.get(i).getLow();
			}

			if (bars.get(i).getHigh() > ph) {
				ph = bars.get(i).getHigh();
			}
		}
		if(liveBar.isActive()){
			if(liveBar.getLow() < pl){
				pl = liveBar.getLow();
			}
			if(liveBar.getHigh() > ph){
				ph = liveBar.getHigh();
			}
		}
		if(WLD.accessValue()){
			for (int i = 1; i < x.length; i++){
				if(x[i].getPoint() < pl){
					pl = x[i].getPoint();
				}
				if(x[i].getPoint() > ph){
					ph = x[i].getPoint();
				}
			}
		}

		assert dl != null;
		dl.addText(13, cursorPosX + 1440, cursorPosY + 21 , Color.WHITE.getRGB(), String.valueOf(ph));
		dl.addText(13, cursorPosX + 1440, cursorPosY + 705 , Color.WHITE.getRGB(), String.valueOf(pl));

		//TODO: get a fucking life
		double ratio = ph - pl;
		double ratioDiv = ratio / 7;
		DecimalFormat df = new DecimalFormat("#.###");

		int[] spotDate = new int[] {36, 44, 51, 58, 65, 72, 79, 87, 94};
		Date[] formattedDateS = new Date[9];
		String[] formattedTime = new String[9];
		DateFormat format = new SimpleDateFormat("MM/dd | HH:mm:ss");
		format.setTimeZone(TimeZone.getTimeZone("US/Eastern"));

		for(int i = 0; i < 9; i++){
			formattedDateS[i] = new Date(bars.get(spotDate[i]).getTime() * 1000);
		}

		for(int i = 1; i < 10; i++){
			formattedTime[i] = format.format(formattedDateS[i - 1]);
			dl.addText(13, cursorPosX + (142.5f*i) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime[i]);
		}

		for(int i = 1; i < 6; i++){
			dl.addText(13, cursorPosX + 1440, cursorPosY + 18 + (100*i) , Color.WHITE.getRGB(), df.format(ph - Double.parseDouble(df.format(ratioDiv*i))));
		}


		dl.addRect(cursorPosX + 10, cursorPosY + 25, cursorPosX + 1435, cursorPosY + 715, colors.darkGrey); //1425, 695
		for(int i = 1; i <= 9; i++){
			dl.addLine(cursorPosX + 10 + ((float)i*142.5f) , cursorPosY + 25, cursorPosX + 10 + ((float)i*142.5f), cursorPosY + 715, colors.darkGrey);
			//139
		}
		for(int i = 1; i <= 6; i++){
			dl.addLine(cursorPosX + 10, cursorPosY + 25 + ((float) i*99.2857143f), cursorPosX + 1435, cursorPosY + 25 + ((float) i*99.2857143f), colors.darkGrey);
		}

		int counter = (x.length - 1) - 69; //set(30) - len - 1

		for (int i = 30; i <= 99; i++) {
			double wldf;
			double wldn = 0;

			int color = 0;

			if(WLD.accessValue()){
				//TODO: change to list because java doesnt like this
				adjUse[i - 1] = x[counter - 1].getPoint();
				adjUse[i] = x[counter].getPoint();
				wldf = ((adjUse[i - 1] - pl) / ratio) * 670;
				wldn = ((adjUse[i] - pl) / ratio) * 670;

				if(wldf > wldn){
					color = colors.yellow;
				} else if(wldf < wldn){
					color = colors.cyan;
				} else {
					color = colors.grey;
				}

				dl.addLine(cursorPosX + 41 + ((i - 31) * 20), cursorPosY + 670 - (float) wldf + 30, cursorPosX + 41 + ((i - 30) * 20), cursorPosY + 670 - (float) wldn + 30, color, 2);
				counter++;
			}

			if(i == 99 && WLD.accessValue()){
				dl.addLine(cursorPosX + 11, cursorPosY + 670 - (float) wldn + 30, cursorPosX + 57 + ((i - 30) * 20), cursorPosY + 670 - (float) wldn + 30, color, 1);
				//testing text sample
				dl.addRectFilled(cursorPosX + 55 + ((i - 30) * 20), cursorPosY + 670 - (float) wldn + 22, cursorPosX + 120 + ((i - 30) * 20), cursorPosY + 670 - (float) wldn + 39f, color); //22, 38
				dl.addText(13, cursorPosX + 58 + ((i - 30) * 20), cursorPosY + 670 - (float) wldn + 24 , Color.BLACK.getRGB(), String.valueOf(df.format(adjUse[99])));
				if(color == colors.yellow){
					barInstance.setLongPos(false);
				} else if(color == colors.cyan) {
					barInstance.setLongPos(true);
				}
			}

			double h = ((bars.get(i).getHigh() - pl) / ratio) * 670;
			double l = ((bars.get(i).getLow() - pl) / ratio) * 670;
			double o = ((bars.get(i).getOpen() - pl) / ratio) * 670;
			double c = ((bars.get(i).getClose() - pl) / ratio) * 670;

			int temp = Color.BLUE.getRGB();
			if (c > o) {
				temp = Color.GREEN.getRGB();
			} else if(c == o){
				if(bars.get(i - 1).getClose() <= c){
					temp = Color.GREEN.getRGB();
				}
			}
			//fancy math for lame barchart
			dl.addLine(cursorPosX + 41 + ((i - 31) * 20), cursorPosY + 670 - (float) l + 30, cursorPosX + 41 + ((i - 31) * 20), cursorPosY + 670 - (float) h + 30, colors.grey);
			dl.addRectFilled(cursorPosX + 47 + ((i - 31) * 20), cursorPosY + 670 - (float) o + 30, cursorPosX + 36 + ((i - 31) * 20), cursorPosY + 670 - (float) c + 30, temp);

			if(o == c){
				dl.addLine(cursorPosX + 47 + ((i - 31) * 20), cursorPosY + 670 - (float) o + 30, cursorPosX + 36 + ((i - 31) * 20), cursorPosY + 670 - (float) c + 30, temp);
			}
		}

		//START LIVE BARCHART
		int adjust = 99;

		double h;
		double l;
		double o;
		double c;

		if(liveBar.isActive()){
			h = ((liveBar.getHigh() - pl) / ratio) * 670;
			l = ((liveBar.getLow() - pl) / ratio) * 670;
			o = ((liveBar.getOpen() - pl) / ratio) * 670;
			c = ((liveBar.getNow() - pl) / ratio) * 670;
		} else {
			h = ((bars.get(99).getClose() - pl) / ratio) * 670;
			l = ((bars.get(99).getClose() - pl) / ratio) * 670;
			o = ((bars.get(99).getClose() - pl) / ratio) * 670;
			c = ((bars.get(99).getClose() - pl) / ratio) * 670;
		}


		int temp = colors.red;
		if (c > o) {
			temp = colors.green;
		} else if(c == o){
			if(liveBar.getNow() <= c){
				temp = colors.green;
			}
		}

		//TODO: make a method/object for this
		barInstance.setBarColor(temp);

		dl.addLine(cursorPosX + 41 + ((adjust - 30) * 20), cursorPosY + 670 - (float) l + 30, cursorPosX + 41 + ((adjust - 30) * 20), cursorPosY + 670 - (float) h + 30, colors.grey);
		dl.addRectFilled(cursorPosX + 47 + ((adjust - 30) * 20), cursorPosY + 670 - (float) o + 30, cursorPosX + 36 + ((adjust - 30) * 20), cursorPosY + 670 - (float) c + 30, temp);

		if(o == c){
			dl.addLine(cursorPosX + 47 + ((adjust - 30) * 20), cursorPosY + 670 - (float) o + 30, cursorPosX + 36 + ((adjust - 30) * 20), cursorPosY + 670 - (float) c + 30, temp);
		}

		//TODO: the line for the price because you have dementia || somehow make it dotted because this gives me aids
		dl.addLine(cursorPosX + 11, cursorPosY + 670 - (float) c + 30, cursorPosX + 57 + ((adjust - 30) * 20), cursorPosY + 670 - (float) c + 30, temp, 1);
		//testing text sample
		dl.addRectFilled(cursorPosX + 55 + ((adjust - 30) * 20), cursorPosY + 670 - (float) c + 22, cursorPosX + 120 + ((adjust - 30) * 20), cursorPosY + 670 - (float) c + 39f, temp); //22, 38

		if(liveBar.isActive())
			dl.addText(13, cursorPosX + 58 + ((adjust - 30) * 20), cursorPosY + 670 - (float) c + 24 , Color.BLACK.getRGB(), String.valueOf(liveBar.getNow()));
		else
			dl.addText(13, cursorPosX + 58 + ((adjust - 30) * 20), cursorPosY + 670 - (float) c + 24 , Color.BLACK.getRGB(), String.valueOf(bars.get(99).getClose()));

	}

	public static void stockDescription(JImGui imGui,
										CopyOnWriteArrayList<bar> bars,
										double priceNow,
										JSONObject detailed,
										barInstance barInstance
										){
		imGui.setNextWindowPos(1510, 20);
		imGui.setWindowSize("Details", 410, 200);
		imGui.begin("Details");

		float cursorPosX = JImGui.getWindowPosX();
		float cursorPosY = JImGui.getWindowPosY();

		JImDrawList dl = imGui.findWindowDrawList();

		String sector = "";
		String mktcap = "";
		String dsc = "";
		try {
			sector = detailed.getString("sector");
			dsc = detailed.getString("description");
			mktcap = detailed.getString("marketcap");
		} catch (JSONException ignored){

		}

		imGui.text(detailed.getString("name"));
		if(!sector.isEmpty())
			imGui.text("Sector: " + sector);
		if(!mktcap.isEmpty())
			imGui.text("Market Cap: " + mktcap);
		if(!dsc.isEmpty())
			imGui.textWrapped("Description: " + dsc);
		assert dl != null;

		if(priceNow == 0) {
			dl.addText(22, 300 + cursorPosX, 23 + cursorPosY, barInstance.getBarColor(), String.valueOf(bars.get(99).getClose()));
		}
		else
			dl.addText(22, 300 + cursorPosX, 23 + cursorPosY, barInstance.getBarColor(), String.valueOf(priceNow));
		JImGuiGen.end();
	}
}
