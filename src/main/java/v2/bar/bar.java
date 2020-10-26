package v2.bar;

public class bar {
    private double low;
    private double high;
    private double close;
    private double open;
    private long time;

    public bar(double low, double high, double close, double open, long time){
        this.low = low;
        this.high = high;
        this.close = close;
        this.open = open;
        this.time = time;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getOpen() {
        return open;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getLow() {
        return low;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setClose(double close) {
        this.close = close;
    }

    public double getClose() {
        return close;
    }

    public double getHigh() {
        return high;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
