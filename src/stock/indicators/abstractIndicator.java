package stock.indicators;

import stock.alpaca.alpacaIndicatorData;
import stock.bar.bar;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class abstractIndicator {
	private List<alpacaIndicatorData> indicatorData = new CopyOnWriteArrayList<alpacaIndicatorData>();
	private List<bar> barData = new CopyOnWriteArrayList<bar>();

	public abstractIndicator(CopyOnWriteArrayList<bar> bars) {
		this.barData = bars;
	}

	public void updateBarData(CopyOnWriteArrayList<bar> bars) {
		this.barData = bars;
	}

	public List<alpacaIndicatorData> getIndicatorData(){
		return indicatorData;
	}
}