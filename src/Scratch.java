import stock.test.bar.barTest;

import java.io.IOException;

import static stock.test.bar.barTest.bars;
import static stock.test.bar.barTest.getBarCount;

class Scratch {
    public static void main(String[] args) throws IOException, InterruptedException {
        sma(14);
    }
    public static double[] sma(int length){
        int barcountR = getBarCount - length;
        double[] sma = new double[barcountR];
        int temp = 0;

        for(int i = getBarCount - barcountR; i <= getBarCount; i++){
            double[] k = new double[length];
            double m = 0;

            for(int j = i; j <= i + length - 1; j++){
                k[j-i] = bars.get(j).getClose();
            }
            for(int j = 0; j <= length - 1; j++){
                m += k[j];
            }

            double l = m/length;

            sma[temp] = l;
            temp++;
        }

        return sma;
    }
}

