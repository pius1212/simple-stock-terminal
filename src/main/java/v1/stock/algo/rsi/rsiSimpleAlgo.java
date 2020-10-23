package v1.stock.algo.rsi;

import v1.stock.alpaca.alpacaOrder;
import v1.stock.test.bar.barTest;

import java.io.IOException;

public abstract class rsiSimpleAlgo extends barTest implements Runnable{
    public static void rsiAlgo(String ticker, int buy, int sell){
        Thread t = new Thread(() -> {
            while (true) {
                try {
                    if(rsi.size() != 0){
                        if(rsi.size() != 0 && rsi.get(rsi.size() - 1).getPoint() <= buy){
                            alpacaOrder.openLongMarket(pAPIkey, psecret, ticker, 100, "day");
                            alpacaOrder.openLongMarket(pAPIkey, psecret, ticker, 100, "day");
                            System.out.println("bought 100 shares of " + ticker);
                        } else if (rsi.size() != 0 && rsi.get(rsi.size() - 1).getPoint() >= sell){
                            alpacaOrder.openShortMarket(pAPIkey, psecret, ticker, 100, "day");
                            alpacaOrder.openShortMarket(pAPIkey, psecret, ticker, 100, "day");
                            System.out.println("sold 100 shares of " + ticker);
                        }
                    }
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException ignored){
                }
            }
        });
        t.start();
    }
}
