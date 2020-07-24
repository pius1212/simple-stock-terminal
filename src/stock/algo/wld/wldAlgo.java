package stock.algo.wld;

import org.junit.experimental.theories.Theories;
import stock.alpaca.alpacaOrder;
import stock.test.bar.barTest;
import stock.test.indicator.rsiHandler;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DecimalFormat;

public abstract class wldAlgo extends barTest implements Runnable{
	private static boolean longOpen = true;
	private static boolean shortOpen = true;
	private static boolean tradable = true;

	public static void wldAlgo(String ticker){
		Thread t = new Thread(() -> {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			while (true) {
				//if(tradable){
					System.out.println("Long: " + longOpen + " | Short: " + shortOpen + " | Latest Wld: " + latestWld + " | Latest bar close: " + latestBar + " | live price: " + liveBar.getNow());

					if(isLongPos && shortOpen){
						longOpen = true;
						shortOpen = false;
						try {
							alpacaOrder.close(ticker, pAPIkey, psecret);
							alpacaOrder.openLongMarket(pAPIkey, psecret, ticker, 1500, "day");
							System.out.println("bought 1500 shares of " + ticker);
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
					}

					if(!isLongPos && longOpen){
						longOpen = false;
						shortOpen = true;
						try {
							alpacaOrder.close(ticker, pAPIkey, psecret);
							alpacaOrder.openShortMarket(pAPIkey, psecret, ticker, 1500, "day");
							System.out.println("sold 1500 shares of " + ticker);
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
					}
				//}
			}
		});

		t.start();
	}

	private static void reverse(String ticker) throws IOException, InterruptedException {
		alpacaOrder.close(ticker, pAPIkey, psecret);
	}

}
