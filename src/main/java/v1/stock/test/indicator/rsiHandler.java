package v1.stock.test.indicator;

import v1.stock.alpaca.alpacaIndicatorData;
import v1.stock.test.bar.barTest;

public class rsiHandler extends barTest {
    //29 is start of graph
    public static void getRSI(){
        rsi.clear();
        for(int i = 15; i <= 85; i++){
            double[] k = new double[14]; //avg high
            double[] l = new double[14]; //avg low

            for(int j = i; j <= i + 13; j++){
                if(bars.get(j).getClose() - bars.get(j).getOpen() >= 0){
                    k[j-i] = bars.get(j).getClose() - bars.get(j).getOpen();
                    l[j-i] = 0;
                } else {
                    l[j-i] = Math.abs(bars.get(j).getClose() - bars.get(j).getOpen());
                    k[j-i] = 0;
                }
            }
            double m = (k[0] + k[1] + k[2] + k[3] + k[4] + k[5] + k[6] + k[7] + k[8] + k[9] + k[10] + k[11] + k[12] + k[13])/14;
            double n = (l[0] + l[1] + l[2] + l[3] + l[4] + l[5] + l[6] + l[7] + l[8] + l[9] + l[10] + l[11] + l[12] + l[13])/14;

            double rsiA = 100 - (100/(1 + m / n));
            alpacaIndicatorData aid = new alpacaIndicatorData(rsiA);
            rsi.add(aid);
        }
    }
}
