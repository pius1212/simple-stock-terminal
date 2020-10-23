package v1.stock.algo.wld;

import v1.stock.alpaca.alpacaOrder;
import v1.stock.test.bar.barTest;

import java.io.IOException;

public abstract class wldAlgo extends barTest implements Runnable{
	private static boolean longOpen = false;
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
					//System.out.println("Long: " + longOpen + " | Short: " + shortOpen + " | Latest Wld: " + latestWld + " | Latest bar close: " + latestBar + " | live price: " + liveBar.getNow());

					if(isLongPos && shortOpen){
						longOpen = true;
						shortOpen = false;
						try {
							alpacaOrder.close(ticker, pAPIkey, psecret);
							alpacaOrder.openLongMarket(pAPIkey, psecret, ticker, 200, "day");
							System.out.println("bought 200 shares of " + ticker);
						} catch (IOException | InterruptedException e) {
							e.printStackTrace();
						}
					} else if(!isLongPos && longOpen){
						longOpen = false;
						shortOpen = true;
						try {
							alpacaOrder.close(ticker, pAPIkey, psecret);
							alpacaOrder.openShortMarket(pAPIkey, psecret, ticker, 200, "day");
							System.out.println("sold 200 shares of " + ticker);
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
