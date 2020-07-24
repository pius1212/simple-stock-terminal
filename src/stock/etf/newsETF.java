package stock.etf;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.JImVec4;
import org.ice1000.jimgui.flag.JImCondition;
import org.json.JSONArray;
import org.json.JSONObject;
import stock.JsonReader;
import stock.newsObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class newsETF extends SPY {
    public static void updateNews(String token)  throws IOException {
        JSONArray json = JsonReader.readJsonFromUrlArray("https://finnhub.io/api/v1/news?category=general&token=" + token);
        String[] format = json.toString().split("\n", -1);
        StringBuffer sb = new StringBuffer();
        for (String s : format) {
            sb.append(s);
        }
        SPY.newsData = sb.toString();
    }

    public static String getStockNews(String ticker, String token) throws IOException {
        JSONArray json = JsonReader.readJsonFromUrlArray("https://finnhub.io/api/v1/news/?"+ ticker +"&token=" + token);

        return json.toString();
    }

    public static void renderNews(JImGui imGui){
        JSONArray json = new JSONArray(SPY.newsData);
        List<newsObject> newsList = new ArrayList<>();

        for(int i = 0; i<json.length(); i++) {
            boolean duplicate = false;
            JSONObject x = json.getJSONObject(i);
            newsObject no = new newsObject(x.get("category").toString(), x.getLong("datetime"), x.get("headline").toString(), x.getInt("id"), x.get("image").toString(), x.get("related").toString(), x.get("source").toString(), x.get("summary").toString(), x.get("url").toString());
            for(newsObject n : newsList){
                if (n.getHeadline().equals(no.getHeadline())){
                    duplicate = true;
                    break;
                }
            }
            if (!duplicate){
                newsList.add(no);
            }
        }

        imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);
        imGui.begin("Daily News");
        imGui.pushTextWrapPos();
        for(newsObject no : newsList){
            if(imGui.collapsingHeader(no.getHeadline())){
                long unixSeconds = no.getDatetime();
                Date date = new Date(unixSeconds*1000L);
                SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
                sdf.setTimeZone(java.util.TimeZone.getTimeZone("GMT-4"));
                String formattedDate = sdf.format(date);

                imGui.text(formattedDate);
                imGui.textColored(JImVec4.fromHSV(0.0f, 0.0f, 0.5f), no.getSource());
                if(!no.getRelated().isEmpty()){
                    imGui.text("Referenced Symbols:" + no.getRelated());
                }
                imGui.text(no.getSummary());
                imGui.textColored(JImVec4.fromHSV(4 / 7.0f, 0.8f, 0.8f), no.getUrl());
            }
        }
        imGui.end();
    }
}
