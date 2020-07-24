package stock.bar;

public class liveBar {
    private double high;
    private double low;
    private double open;
    private double now;
    private boolean active;

    public liveBar(double high, double low, double open, double now, boolean active){
        this.high = high;
        this.low = low;
        this.open = open;
        this.now = now;
        this.active = active;
    }

    public double getHigh() {
        return high;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public double getLow() {
        return low;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public double getOpen() {
        return open;
    }

    public void setOpen(double open) {
        this.open = open;
    }

    public double getNow() {
        return now;
    }

    public void setNow(double now) {
        this.now = now;
    }

    public boolean isActive(){
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void update(double data){
        setNow(data);

        if(!active){
            active = true;

            setHigh(data);
            setLow(data);
            setOpen(data);
        }

        if(data > high){
            setHigh(data);
        }

        if(data < low){
            setLow(data);
        }
    }
}
