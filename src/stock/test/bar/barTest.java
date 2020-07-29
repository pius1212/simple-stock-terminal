package stock.test.bar;

import org.ice1000.jimgui.*;
import org.ice1000.jimgui.flag.JImCondition;
import org.ice1000.jimgui.util.JniLoader;
import org.json.JSONException;
import org.json.JSONObject;
import stock.algo.wld.wldAlgo;
import stock.alpaca.*;
import stock.stockListener;
import stock.global.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static stock.algo.custom.shortcuts.*;

public class barTest {
    public static List < bar > bars = new CopyOnWriteArrayList < bar > ();
    public static liveBar liveBar = new liveBar(0, 0, 0, 0, false);

    public static List <alpacaIndicatorData> sma10 = new CopyOnWriteArrayList<alpacaIndicatorData>();
    public static List <alpacaIndicatorData> sma20 = new CopyOnWriteArrayList<alpacaIndicatorData>();
    public static List <alpacaIndicatorData> rsi = new CopyOnWriteArrayList<alpacaIndicatorData>();
    public static List <alpacaIndicatorData> rsiScotch = new CopyOnWriteArrayList<alpacaIndicatorData>();
    public static List <alpacaIndicatorData> wld = new CopyOnWriteArrayList<alpacaIndicatorData>();
    public static List <alpacaIndicatorData> wldb = new CopyOnWriteArrayList<alpacaIndicatorData>();

    public static double latestWld = 0;
    public static double latestBar = 0;

    public static int globalWldLen = 7;

    public static String orders = "[]";
    public static String positions = "[]";

    static boolean requestBar = true;
    static int barColor = Color.GREEN.getRGB();

    public static String APIkey = "AKKD80XYTBL1XEVJ7ZQI";
    public static String secret = "m1cQC82sSM7dTowwQB1fILwo075KOzEDoEEXptcF";
    public static String pAPIkey = "PKWZ29UVJ5EE2IGSHN6C";
    public static String psecret = "4dxBWhILHEsxaQf/62LYkDi30pM4UCpqC/1dTBCz";

    public static int rsibuy = 28;
    public static int rsisell = 72;
    public static int getBarCount = 100;
    public static double priceNow = 0.00;

    public static String time = "";

    public static JSONObject detailed = new JSONObject();

    public static boolean isWorking = false;
    public static boolean isLongPos = false;

    public static void renderBars(String ticker, String APIkey, String secret, JImGui imGui, NativeBool RSI, NativeBool WLD) throws IOException, InterruptedException {
        //rgb as int below very important
        LocalDateTime myDateObj = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("ss");
        String formattedDate = myDateObj.format(myFormatObj);

        if (formattedDate.equals("01") && requestBar) {
            barHandler.updateBars(ticker, APIkey, secret);
            liveBar = new liveBar(0, 0, bars.get(bars.size() - 1).getClose(), 0, false);
            requestBar = false;
        }

        if(formattedDate.equals("03")){
            requestBar = true;
        }


        imGui.setNextWindowPos(0, 20);
        imGui.setWindowSize("Bar Chart Test", 1510, 740);
        imGui.begin("Bar Chart Test");
        float cursorPosX = imGui.getWindowPosX();
        float cursorPosY = imGui.getWindowPosY();

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

        dl.addText(13, cursorPosX + 1440, cursorPosY + 21 , Color.WHITE.getRGB(), String.valueOf(ph));
        dl.addText(13, cursorPosX + 1440, cursorPosY + 705 , Color.WHITE.getRGB(), String.valueOf(pl));


        //TODO: make into an array because you are fucking retarded

        //TODO: dear random person reading this,
        // i was so lazy that i didnt make it into an array,
        // it was 4am, piss off

        //TODO: get a fucking life
        Date date = new Date(bars.get(36).getTime() * 1000);
        Date date1 = new Date(bars.get(44).getTime() * 1000);
        Date date2 = new Date(bars.get(51).getTime() * 1000);
        Date date3 = new Date(bars.get(58).getTime() * 1000);
        Date date4 = new Date(bars.get(65).getTime() * 1000);
        Date date5 = new Date(bars.get(72).getTime() * 1000);
        Date date6 = new Date(bars.get(79).getTime() * 1000);
        Date date7 = new Date(bars.get(87).getTime() * 1000);
        Date date8 = new Date(bars.get(94).getTime() * 1000);

        DateFormat format = new SimpleDateFormat("MM/dd | HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("US/Eastern"));

        String formattedTime1 = format.format(date);
        String formattedTime2 = format.format(date1);
        String formattedTime3 = format.format(date2);
        String formattedTime4 = format.format(date3);
        String formattedTime5 = format.format(date4);
        String formattedTime6 = format.format(date5);
        String formattedTime7 = format.format(date6);
        String formattedTime8 = format.format(date7);
        String formattedTime9 = format.format(date8);

        //TODO: dear random person reading this,
        // i was so lazy that i didnt make it into an array,
        // it was 4am, piss off

        dl.addText(13, cursorPosX + (142.5f*1) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime1);
        dl.addText(13, cursorPosX + (142.5f*2) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime2);
        dl.addText(13, cursorPosX + (142.5f*3) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime3);
        dl.addText(13, cursorPosX + (142.5f*4) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime4);
        dl.addText(13, cursorPosX + (142.5f*5) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime5);
        dl.addText(13, cursorPosX + (142.5f*6) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime6);
        dl.addText(13, cursorPosX + (142.5f*7) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime7);
        dl.addText(13, cursorPosX + (142.5f*8) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime8);
        dl.addText(13, cursorPosX + (142.5f*9) - 32, cursorPosY + 715, Color.WHITE.getRGB(), formattedTime9);

        //TODO: dear random person reading this,
        // i was so lazy that i didnt make it into an array,
        // it was 4am, piss off

        double ratio = ph - pl;
        double ratioDiv = ratio / 7;
        DecimalFormat df = new DecimalFormat("#.###");

        dl.addText(13, cursorPosX + 1440, cursorPosY + 18 + (100*1) , Color.WHITE.getRGB(), df.format(ph - Double.parseDouble(df.format(ratioDiv*1))));
        dl.addText(13, cursorPosX + 1440, cursorPosY + 18 + (100*2) , Color.WHITE.getRGB(), df.format(ph - Double.parseDouble(df.format(ratioDiv*2))));
        dl.addText(13, cursorPosX + 1440, cursorPosY + 18 + (100*3) , Color.WHITE.getRGB(), df.format(ph - Double.parseDouble(df.format(ratioDiv*3))));
        dl.addText(13, cursorPosX + 1440, cursorPosY + 18 + (100*4) , Color.WHITE.getRGB(), df.format(ph - Double.parseDouble(df.format(ratioDiv*4))));
        dl.addText(13, cursorPosX + 1440, cursorPosY + 18 + (100*5) , Color.WHITE.getRGB(), df.format(ph - Double.parseDouble(df.format(ratioDiv*5))));
        dl.addText(13, cursorPosX + 1440, cursorPosY + 18 + (100*6) , Color.WHITE.getRGB(), df.format(ph - Double.parseDouble(df.format(ratioDiv*6))));

        //TODO: dear random person reading this,
        // i was so lazy that i didnt make it into an array,
        // it was 4am, piss off

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
                    color = Color.CYAN.getRGB();
                } else if(wldf < wldn){
                    color = Color.YELLOW.getRGB();
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
                if(color == Color.CYAN.getRGB()){
                    isLongPos = false;
                } else if(color == Color.YELLOW.getRGB()) {
                    isLongPos = true;
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


        int temp = Color.BLUE.getRGB();
        if (c > o) {
            temp = Color.GREEN.getRGB();
        } else if(c == o){
            if(liveBar.getNow() <= c){
                temp = Color.GREEN.getRGB();
            }
        }

        barColor = temp;

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

    public static void stockDescription(JImGui imGui, alpacaListener al){
        imGui.setNextWindowPos(1510, 20);
        imGui.setWindowSize("Details", 410, 200);
        imGui.begin("Details");

        float cursorPosX = imGui.getWindowPosX();
        float cursorPosY = imGui.getWindowPosY();

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

        if(priceNow == 0)
            dl.addText(22, 300 + cursorPosX, 23 + cursorPosY, barColor, String.valueOf(bars.get(99).getClose()));
        else
            dl.addText(22, 300 + cursorPosX, 23 + cursorPosY, barColor, String.valueOf(priceNow));
        imGui.end();
    }


    public static void wldTest(JImGui imGui){
        imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);

        imGui.begin("Test");
        double[] x = wld(globalWldLen);
        imGui.plotLines("WLD test", dtf(x), 0, x.length, "WLD test",380, 180);
        imGui.text(String.valueOf(x[x.length - 1]));

        imGui.end();
    }

    public static void MenuBar(JImGui imGui, NativeBool WLD, NativeBool WLDA){
        imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);
        if (imGui.beginMainMenuBar()) {
            if (imGui.beginMenu("Indicators", true)) {
                imGui.menuItem("WLD", null, WLD);
                imGui.endMenu();
            }
            if(imGui.beginMenu("Algos", true)){
                imGui.menuItem("WLD Algo", null, WLDA);
                imGui.endMenu();
            }
            imGui.endMenuBar();
        }
        imGui.end();
    }

    public static void main(String[] args) throws IOException, InterruptedException, URISyntaxException {
        String endpoint = "wss://data.alpaca.markets/stream";
        String ticker = "SPY";

        barHandler.updateBars(ticker, APIkey, secret);
        alpacaPositions.updatePositionsAndOrders(pAPIkey, psecret);
        polygonTickerDetailed.getDetailed(ticker, APIkey);

        System.out.println(rsi.size());

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    alpacaPositions.updatePositionsAndOrders(pAPIkey, psecret);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, 0, 1000);
        Thread.sleep(1000);

        JniLoader.load();

        NativeBool RSI = new NativeBool();
        NativeBool WLD = new NativeBool();
        NativeBool WLDA = new NativeBool();

        alpacaListener al = new alpacaListener(new URI(endpoint), ticker, APIkey, secret);
        al.connect();

        stockListener client = new stockListener(new URI("wss://ws.finnhub.io?token=bqk6knnrh5r9t8htfof0"), ticker );
        client.connect();

        polygonListener pl = new polygonListener(new URI("wss://socket.polygon.io/stocks"), ticker, APIkey);
        pl.connect();
        //wldAlgo.wldAlgo(ticker);

        System.out.println(rsi.size());

        try (JImGui imGui = new JImGui()) {
            imGui.setBackground(JImVec4.fromHSV(0, 0, 0));
            imGui.initBeforeMainLoop();
            while (!imGui.windowShouldClose()) {
                imGui.initNewFrame();

                LocalDateTime myDateObj = LocalDateTime.now();
                DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/YYYY || HH:mm:ss");
                DateTimeFormatter myFormatObj2 = DateTimeFormatter.ofPattern("HH:mm:ss");
                String formattedDate = myDateObj.format(myFormatObj);
                time = myDateObj.format(myFormatObj2);

                client.forwardData(al);
                pl.forwardData(al);

                stockDescription(imGui, al);
                renderBars(ticker, APIkey, secret, imGui, RSI, WLD);
                al.renderListener(ticker, imGui);
                MenuBar(imGui, WLD, WLDA);
                wldTest(imGui);

                alpacaPositions.renderPositionsAndOrders(imGui, al.getPrice(), rsibuy, rsisell, formattedDate);
                imGui.render();
            }
        }
    }

    public enum barDataRead {
        CLOSE,
        OPEN,
        HL,
        HLC,
        HIGH,
        LOW;
    }

    public enum barLength {
        MIN,
        FIVE_MIN,
        FIFTEEN_MIN,
        DAY;
    }
}