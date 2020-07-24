package stock;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Collections;

public class getStockData {
    public static String getQuote(String ticker, String token) throws IOException {
        org.json.JSONObject json = JsonReader.readJsonFromUrl("https://finnhub.io/api/v1/quote?symbol=" + ticker + "&token=" + token);
        System.out.println(json.toString());
        System.out.println(ticker + " price is: " + json.get("c"));

        double x;
        double y;

        x = json.getDouble("c");
        y = json.getDouble("o");

        String a = "false";

        Double z = 100*((x - y)/y);
        DecimalFormat df = new DecimalFormat("#.##");

        String pctg = df.format(z);

        if(x - y >= 0)
            a = "true";

       // System.out.println("-----------------------");
        //System.out.println(x.toString() + "," + pctg + "," + a);

        return Double.toString(x) + "," + pctg + "," + a + "," + y;
    }

    public static String getCandle(String ticker, String token, int Count, String returnType, String resolution) throws IOException {
        System.out.println("https://finnhub.io/api/v1/stock/candle?symbol=" + ticker + "&resolution=" + resolution + "&count="+ Count + "&token=" + token);
        String returnToUser = null;
        JSONObject json = JsonReader.readJsonFromUrl("https://finnhub.io/api/v1/stock/candle?symbol=" + ticker + "&resolution=" + resolution + "&count="+ Count + "&token=" + token);

        switch (returnType) {
            case "close":
                returnToUser = json.getJSONArray("c").toString();
                break;
            case "open":
                returnToUser = json.getJSONArray("o").toString();
                break;
            case "all":
                returnToUser = json.toString();
                break;
        }

        return returnToUser;
    }

    public static String dayHighLow(String ticker, String token) throws IOException {
        System.out.println(JsonReader.readJsonFromUrl("https://finnhub.io/api/v1/quote?symbol=" + ticker + "&token=" + token));
        org.json.JSONObject json = JsonReader.readJsonFromUrl("https://finnhub.io/api/v1/quote?symbol=" + ticker + "&token=" + token);
        return json.getDouble("h") + "," + json.getDouble("l");
    }

    public static String getCompanyProfile(String ticker, String token) throws IOException {
        JSONObject json = new JSONObject(IOUtils.toString(new URL("https://finnhub.io/api/v1/stock/profile2?symbol="+ticker+"&token=" + token), Charset.forName("UTF-8")));
        System.out.println(json.toString());
        return json.toString();
    }

    public static String getFinancialData(String ticker, String token) throws IOException{
        JSONObject json = new JSONObject(IOUtils.toString(new URL("https://finnhub.io/api/v1/stock/financials-reported?freq=quarterly&symbol=" + ticker + "&token="+token), Charset.forName("UTF-8")));
        System.out.println(json.toString());
        return json.toString();
    }

    public static String getRatingsData(String ticker, String token) throws IOException{
        //https://finnhub.io/api/v1/stock/recommendation?symbol=AAPL&token=bqk6knnrh5r9t8htfof0
        JSONArray json = new JSONArray(IOUtils.toString(new URL("https://finnhub.io/api/v1/stock/recommendation?symbol=" + ticker +"&token=" + token), Charset.forName("UTF-8")));
        System.out.println(json.toString());
        return json.toString();
    }
//    public static String yearHighLow(String ticker, String token) throws IOException {
//        JSONObject json = JsonReader.readJsonFromUrl("https://finnhub.io/api/v1/stock/candle?symbol=" + ticker + "&resolution=D&count=365&token=" + token);
//        JSONArray temp = json.getJSONArray("l");
//        JSONArray temp1 = json.getJSONArray("h");
//
//        int[] x = new int[364];
//        int[] y = new int[364];
//
//        Arrays.sort(y);
//
//
//
//        for(int z = 0; z < 365; z++){
//            x[z] = temp.getInt(z);
//        }
//        for(int a = 0; a < 365; a++){
//            y[a] = temp1.getInt(a);
//        }
//
//        int low = Collections.min(Arrays.asList(x[0], x[1], x[2], x[3], x[4], x[5], x[6], x[7], x[8], x[9], x[10], x[11]));
//        int high = y[y.length-1];
//
//        return high + "," + low;
//    }
}
