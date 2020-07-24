package stock.etf;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImVec4;
import org.ice1000.jimgui.flag.JImCondition;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import stock.getStockData;

import java.io.IOException;

public class etf {

    private String ticker;
    private String price;
    private JImVec4 ud;
    private String pctg;
    private String token;
    private float[] prices;
    private String resolution;
    private String dayHigh;
    private String dayLow;
    private String news;
    private String open;

    public etf(String ticker, String price, String pctg, JImVec4 ud, String token, float[] prices, String dayHigh, String dayLow, String news, String resolution, String open) {
        super();
        this.ticker = ticker;
        this.price = price;
        this.ud = ud;
        this.pctg = pctg;
        this.token = token;
        this.prices = prices;
        this.dayHigh = dayHigh;
        this.dayLow = dayLow;
        this.news = news;
        this.resolution = resolution;
        this.open = open;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getUd() {
        String x = new String();
        if (ud.equals(JImVec4.fromHSV(7.0f, 0.8f, 0.8f))) {
            x = "neg";
        } else if (ud.equals(JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f))) {
            x = "pos";
        }
        return x;
    }

    public void setUd(JImVec4 ud) {
        this.ud = ud;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPctg() {
        return pctg;
    }

    public void setPctg(String pctg) {
        this.pctg = pctg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public float[] getPrices() {
        return prices;
    }

    public void setPrices(float[] prices) {
        this.prices = prices;
    }

    public void setDayHigh(String dayHigh) {
        this.dayHigh = dayHigh;
    }

    public String getDayHigh() {
        return dayHigh;
    }

    public void setDayLow(String dayLow) {
        this.dayLow = dayLow;
    }

    public String getDayLow() {
        return dayLow;
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public String getResolution(){
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }

    public void update() throws IOException {
        String x = getStockData.getQuote(getTicker(), getToken());
        String[] y = x.split(",", -1);

        String a = getStockData.dayHighLow(ticker, token);
        //        String b = getStockData.yearHighLow(ticker, token);
        String[] c = a.split(",", -1);
        //        String[] d = b.split("," , -1);

        setPrice(y[0]);
        setPctg(y[1]);

        setDayHigh(c[0]);
        setDayLow(c[1]);
        //        setYearHigh(d[0]);
        //        setYearLow(d[1]);

        JImVec4 color = JImVec4.fromHSV(7.0f, 0.8f, 0.8f);
        assert false;
        if (y[2].equals("true"))
            color = JImVec4.fromHSV(3 / 7.0f, 0.8f, 0.8f);

        setUd(color);
    }

    public void renderPrice(@NotNull JImGui imGui) {
        imGui.columns(3, "columns", false);

        imGui.setWindowFontScale(2.0f);
        imGui.textColored(ud, ticker + ":");
        imGui.nextColumn();
        imGui.textColored(ud, price);






        
        imGui.nextColumn();
        imGui.textColored(ud, pctg + "%");
        imGui.nextColumn();

    }

    public void updateChart() throws IOException {

        String jsonData = getStockData.getCandle(ticker, token, 1400, "close", resolution);
        System.out.println(jsonData);
        JSONArray obj = new JSONArray(jsonData);
        System.out.println(obj.getFloat(0));

        for (int i = 0; i < obj.length(); i++) {
            prices[i] = obj.getFloat(i);
        }
    }

    public void renderDetailed(JImGui imGui) throws IOException {
        imGui.pushTextWrapPos();

        imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);
        imGui.begin(ticker);

        imGui.text("Graph for " + ticker);
        imGui.plotLines("", prices, 0, prices.length, ticker, 670f, 420f);

        imGui.columns(2, "columns", false);
        imGui.separator();

        imGui.textColored(ud, ticker + " Price: ");
        imGui.textColored(ud, "Day High: ");
        imGui.textColored(ud, "Day Low: ");

        imGui.nextColumn();

        imGui.textColored(ud, price + "          " + pctg + "%");
        imGui.textColored(ud, dayHigh);
        imGui.textColored(ud, dayLow);

        imGui.nextColumn();

        imGui.separator();

        imGui.end();
    }
}