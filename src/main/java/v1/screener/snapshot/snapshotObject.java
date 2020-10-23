package v1.screener.snapshot;

import org.json.JSONObject;

public class snapshotObject {
	//metadata
	private String ticker;
	private float dayChange;
	private float dayChangePerc;
	private long timestamp;

	//day data
	private float dayOpen;
	private float dayClose;
	private float dayLow;
	private float dayHigh;
	private int dayVolume;

	//last quote
	private float lastBid;
	private float bidSize;
	private float lastAsk;
	private float askSize;

	//last price
	private float lastPrice;
	private float lastTradeSize;
	private long lastTradeTime;

	//minute bar
	private float minClose;
	private float minHigh;
	private float minLow;
	private float minOpen;
	private float minVolume;

	//prevDay
	private float prevClose;
	private float prevHigh;
	private float prevLow;
	private float prevOpen;
	private float prevVolume;

	public snapshotObject(JSONObject jo){
		JSONObject day = jo.getJSONObject("day");
		JSONObject trade = jo.getJSONObject("lastTrade");
		JSONObject quote = jo.getJSONObject("lastQuote");
		JSONObject min = jo.getJSONObject("min");
		JSONObject prevDay = jo.getJSONObject("prevDay");

		ticker = jo.getString("ticker");
		dayChange = jo.getFloat("todaysChange");
		dayChangePerc = jo.getFloat("todaysChangePerc");
		timestamp = jo.getLong("updated");

		dayOpen = day.getFloat("o");
		dayClose = day.getFloat("c");
		dayLow = day.getFloat("l");
		dayHigh = day.getFloat("h");
		dayVolume = day.getInt("v");

		lastPrice = trade.getFloat("p");
		lastTradeSize = trade.getFloat("s");
		lastTradeTime = trade.getLong("t");

		lastBid = quote.getFloat("p");
		bidSize = quote.getFloat("s");
		lastAsk = quote.getFloat("P");
		askSize = quote.getFloat("S");

		minOpen = min.getFloat("o");
		minClose = min.getFloat("c");
		minLow = min.getFloat("l");
		minHigh = min.getFloat("h");
		minVolume = min.getInt("v");

		prevOpen = prevDay.getFloat("o");
		prevClose = prevDay.getFloat("c");
		prevLow = prevDay.getFloat("l");
		prevHigh = prevDay.getFloat("h");
		prevVolume = prevDay.getInt("v");
	}

	public float getAskSize() {
		return askSize;
	}

	public float getBidSize() {
		return bidSize;
	}

	public float getDayChange() {
		return dayChange;
	}

	public float getDayChangePerc() {
		return dayChangePerc;
	}

	public float getDayClose() {
		return dayClose;
	}

	public float getDayHigh() {
		return dayHigh;
	}

	public float getDayLow() {
		return dayLow;
	}

	public float getDayOpen() {
		return dayOpen;
	}

	public float getLastAsk() {
		return lastAsk;
	}

	public float getLastBid() {
		return lastBid;
	}

	public float getLastPrice() {
		return lastPrice;
	}

	public float getLastTradeSize() {
		return lastTradeSize;
	}

	public float getMinClose() {
		return minClose;
	}

	public float getMinHigh() {
		return minHigh;
	}

	public float getMinLow() {
		return minLow;
	}

	public float getMinOpen() {
		return minOpen;
	}

	public float getMinVolume() {
		return minVolume;
	}

	public float getPrevClose() {
		return prevClose;
	}

	public float getPrevHigh() {
		return prevHigh;
	}

	public float getPrevLow() {
		return prevLow;
	}

	public float getPrevOpen() {
		return prevOpen;
	}

	public float getPrevVolume() {
		return prevVolume;
	}

	public int getDayVolume() {
		return dayVolume;
	}

	public long getLastTradeTime() {
		return lastTradeTime;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public String getTicker() {
		return ticker;
	}

	public void setAskSize(float askSize) {
		this.askSize = askSize;
	}

	public void setBidSize(float bidSize) {
		this.bidSize = bidSize;
	}

	public void setDayChange(float dayChange) {
		this.dayChange = dayChange;
	}

	public void setDayChangePerc(float dayChangePerc) {
		this.dayChangePerc = dayChangePerc;
	}

	public void setDayClose(float dayClose) {
		this.dayClose = dayClose;
	}

	public void setDayHigh(float dayHigh) {
		this.dayHigh = dayHigh;
	}

	public void setDayLow(float dayLow) {
		this.dayLow = dayLow;
	}

	public void setDayOpen(float dayOpen) {
		this.dayOpen = dayOpen;
	}

	public void setDayVolume(int dayVolume) {
		this.dayVolume = dayVolume;
	}

	public void setLastAsk(float lastAsk) {
		this.lastAsk = lastAsk;
	}

	public void setLastBid(float lastBid) {
		this.lastBid = lastBid;
	}

	public void setLastPrice(float lastPrice) {
		this.lastPrice = lastPrice;
	}

	public void setLastTradeSize(float lastTradeSize) {
		this.lastTradeSize = lastTradeSize;
	}

	public void setLastTradeTime(long lastTradeTime) {
		this.lastTradeTime = lastTradeTime;
	}

	public void setMinClose(float minClose) {
		this.minClose = minClose;
	}

	public void setMinHigh(float minHigh) {
		this.minHigh = minHigh;
	}

	public void setMinLow(float minLow) {
		this.minLow = minLow;
	}

	public void setMinOpen(float minOpen) {
		this.minOpen = minOpen;
	}

	public void setMinVolume(float minVolume) {
		this.minVolume = minVolume;
	}

	public void setPrevClose(float prevClose) {
		this.prevClose = prevClose;
	}

	public void setPrevHigh(float prevHigh) {
		this.prevHigh = prevHigh;
	}

	public void setPrevLow(float prevLow) {
		this.prevLow = prevLow;
	}

	public void setPrevOpen(float prevOpen) {
		this.prevOpen = prevOpen;
	}

	public void setPrevVolume(float prevVolume) {
		this.prevVolume = prevVolume;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
