package v3.screener.breakout;

import v3.global.objects.bar;

public class breakoutItem {
	private String ticker;
	private bar fivePrevDay;
	private bar currentSessionBar;

	//stored as ticker
	//stored as 5 day bar of previous day
	//stored as current day bar
	public breakoutItem(String ticker, bar fivePrevDay, bar currentSessionBar){
		this.ticker = ticker;
		this.fivePrevDay = fivePrevDay;
		this.currentSessionBar = currentSessionBar;
	}

	public String getTicker() {
		return ticker;
	}

	public bar getBar() {
		return fivePrevDay;
	}

	public bar getCurrentSessionBar() {
		return currentSessionBar;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setBar(v3.global.objects.bar fivePrevDay) {
		this.fivePrevDay = fivePrevDay;
	}

	public void setCurrentSessionBar(bar currentSessionBar) {
		this.currentSessionBar = currentSessionBar;
	}
}
