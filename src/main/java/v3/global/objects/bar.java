package v3.global.objects;

public class bar {
	private float open;
	private float high;
	private float low;
	private float close;

	//order in standardized OHLC
	public bar(float open, float high, float low, float close){
		this.open = open;
		this.high = high;
		this.low = low;
		this.close = close;
	}

	public float getClose() {
		return close;
	}

	public float getHigh() {
		return high;
	}

	public float getLow() {
		return low;
	}

	public float getOpen() {
		return open;
	}

	public void setClose(float close) {
		this.close = close;
	}

	public void setHigh(float high) {
		this.high = high;
	}

	public void setLow(float low) {
		this.low = low;
	}

	public void setOpen(float open) {
		this.open = open;
	}
}
