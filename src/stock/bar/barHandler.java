//    public static String APIkey = "AKKD80XYTBL1XEVJ7ZQI";
//    public static String secret = "m1cQC82sSM7dTowwQB1fILwo075KOzEDoEEXptcF";
//    public static String pAPIkey = "PKYV3DLTL8TVMV8OXQGO";
//    public static String psecret = "tciYUFo7EaycKLfBRODmnrhp5Z32ym8xDO0/wX16";

package stock.bar;

import org.json.JSONArray;
import org.json.JSONObject;
import stock.alpaca.alpacaIndicatorData;

import java.io.IOException;

import static stock.algo.custom.shortcuts.wld;

public class barHandler extends barTest {
    public static void updateBars(String ticker, String APIkey, String secret) throws IOException, InterruptedException {

        String data = stock.alpaca.alpacaBar.getCandle(APIkey, secret, ticker, 100, "1Min");
        System.out.println(data);
        JSONObject j = new JSONObject(data);
        JSONArray ja = j.getJSONArray(ticker);

        bars.clear();

        for(int i = 0; i <=99; i++){
            JSONObject jo = ja.getJSONObject(i);
            bar b = new bar(jo.getDouble("l"), jo.getDouble("h"), jo.getDouble("c"), jo.getDouble("o"), jo.getLong("t"));

            bars.add(b);
        }
        latestBar = bars.get(bars.size() - 1).getClose();

        double[] x = wld(globalWldLen);

        isWorking = true;
        wld.clear();
        for(int i = 1; i < x.length; i++){
            alpacaIndicatorData aid = new alpacaIndicatorData(x[i]);
            wld.add(aid);
        }
        latestWld = wld.get(wld.size() - 1).getPoint();
        isWorking = false;
        if(!wld.isEmpty())
            wldb = wld;
    }


}
