package v1.stock.algo.custom;

import v1.stock.test.bar.barTest;

import static v1.stock.algo.custom.shortcuts.atr;

//bars at 0-99
//assume all legnth at 14
public abstract class customAlgo extends barTest implements Runnable {
    private static double trendUp = 0;
    private static double trendDown= 0;

    public static void customAlgo(String ticker, int pd, int factor){
        Thread t = new Thread(() -> {
            while (true) {
                double[] atr = atr(pd);
                double up = shortcuts.hl2(bars.get(bars.size() - 1).getHigh(), bars.get(bars.size() - 1).getLow()) - (factor * atr[atr.length - 1]);
                double down = shortcuts.hl2(bars.get(bars.size() - 1).getHigh(), bars.get(bars.size() - 1).getLow()) + (factor * atr[atr.length - 1]);

//                if(bars.get(bars.size() - 2).getClose() > )

            }
        });
        t.start();
    }

}

