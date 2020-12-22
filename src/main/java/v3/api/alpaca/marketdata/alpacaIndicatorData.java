package v3.api.alpaca.marketdata;

public class alpacaIndicatorData {
    private double point;
    public alpacaIndicatorData(double point){
        this.point = point;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }
}
