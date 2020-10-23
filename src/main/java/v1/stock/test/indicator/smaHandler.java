package v1.stock.test.indicator;

import v1.stock.alpaca.alpacaIndicatorData;
import v1.stock.test.bar.barTest;

public class smaHandler extends barTest {
    public static void getSMA10(){
        for(int i = 19; i <= 89; i++){
            //note to future self, keep at 89 because there is another for loop that parses for 99
            double[] k = new double[10];
            for(int j = i; j <= i + 9; j++){
                k[j-i] = bars.get(j).getClose();
            }
            double l = (k[0] + k[1] + k[2] + k[3] + k[4] + k[5] + k[6] + k[7] + k[8] + k[9])/10;
            alpacaIndicatorData aid = new alpacaIndicatorData(l);
            sma10.add(aid);
        }
    }
}
