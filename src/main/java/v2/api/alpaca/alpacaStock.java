package v2.api.alpaca;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImVec4;
import org.ice1000.jimgui.flag.JImCondition;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;
import v1.stock.getStockData;

import java.io.IOException;

public class alpacaStock {

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

    private String companyProfile;
    private String financialData;
    private String ratingsData;

    //    private String yearHigh;
    //    private String yearLow;

    public alpacaStock(String ticker, String price, String pctg, JImVec4 ud, String token, float[] prices, String dayHigh, String dayLow /*, String yearHigh, String yearLow*/ , String news, String resolution, String companyProfile, String financialData, String ratingsData) {
        super();
        this.ticker = ticker;
        this.price = price;
        this.ud = ud;
        this.pctg = pctg;
        this.token = token;
        this.prices = prices;
        this.dayHigh = dayHigh;
        this.dayLow = dayLow;
        //        this.yearHigh = yearHigh;
        //        this.yearLow = yearLow;
        this.news = news;
        this.resolution = resolution;
        this.companyProfile = companyProfile;
        this.financialData = financialData;
        this.ratingsData = ratingsData;
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

    //    public void setYearHigh(String yearHigh) {
    //        this.yearHigh = yearHigh;
    //    }
    //
    //    public String getYearHigh() {
    //        return yearHigh;
    //    }
    //
    //    public void setYearLow(String yearLow) {
    //        this.yearLow = yearLow;
    //    }
    //
    //    public String getYearLow() {
    //        return yearLow;
    //    }

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

    public String getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(String companyProfile) {
        this.companyProfile = companyProfile;
    }

    public String getFinancialData(){
        return financialData;
    }

    public void setFinancialData(String financialData) {
        this.financialData = financialData;
    }

    public String getRatingsData() {
        return ratingsData;
    }

    public void setRatingsData(String ratingsData) {
        this.ratingsData = ratingsData;
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
        imGui.columns(4, "columns", false);

        imGui.textColored(ud, ticker + ":");
        imGui.nextColumn();
        imGui.textColored(ud, price );
        imGui.nextColumn();
        imGui.textColored(ud, pctg + "%");
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

        JSONObject json = new JSONObject(getCompanyProfile());
        JSONObject json1 = new JSONObject(getFinancialData());
        JSONArray json2 = json1.getJSONArray("data");
        JSONObject json3 = json2.getJSONObject(0);
        JSONObject report = json3.getJSONObject("report");
        JSONObject bs = report.getJSONObject("bs");
        JSONObject cf = report.getJSONObject("cf");
        JSONObject ic = report.getJSONObject("ic");

        JSONArray json4 = new JSONArray(getRatingsData());
        JSONObject latestRatings = json4.getJSONObject(0);

        imGui.text("Company Profile");
        imGui.text("Country: " + json.get("country"));
        imGui.text("Currency: " + json.get("currency"));
        imGui.text("Exchange: " + json.get("exchange"));
        imGui.text("IPO Date: " + json.get("ipo"));
        imGui.text("Market Cap: " + json.get("marketCapitalization"));
        imGui.text("Name: " + json.get("name"));
        imGui.text("Phone: " + json.get("phone"));
        imGui.text("Shares Outstanding: " + json.get("shareOutstanding"));
        imGui.text("Ticker: " + json.get("ticker"));
        imGui.text("URL: " + json.get("weburl"));
        imGui.text("Industry: " + json.get("finnhubIndustry"));

        imGui.nextColumn();

        imGui.text("Financials");
        imGui.text("Form: " + json3.get("form"));
        imGui.text("Start Date: " + json3.get("startDate"));
        imGui.text("End Date: " + json3.get("endDate"));
        imGui.text("Assets: " + bs.get("Assets"));
        imGui.text("Liabilities: " + bs.get("Liabilities"));
        imGui.text("Long Term Liabilities: " + bs.get("LongTermDebtCurrent"));
        imGui.text("Short Term Liabilities: " + bs.get("OtherShortTermBorrowings"));
        imGui.text("Net Inventory: " + bs.get("InventoryNet"));
        imGui.text("Net Income Loss: " + cf.get("NetIncomeLoss"));
        imGui.text("Net Profit: " + ic.get("GrossProfit"));
        imGui.text("Operating Expenses: " + ic.get("OperatingExpenses"));

        imGui.separator();
        imGui.nextColumn();

        imGui.text("Ratings");
        imGui.text("Strong Buy: " + latestRatings.get("strongBuy"));
        imGui.text("Buy: " + latestRatings.get("buy"));
        imGui.text("Hold: " + latestRatings.get("hold"));
        imGui.text("Sell: " + latestRatings.get("sell"));
        imGui.text("Strong Sell: " + latestRatings.get("strongSell"));
        imGui.text("Ratings as of: " + latestRatings.get("period"));

        imGui.end();
    }
}