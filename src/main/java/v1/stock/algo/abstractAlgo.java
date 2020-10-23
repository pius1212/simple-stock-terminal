package v1.stock.algo;

import v1.stock.alpaca.alpacaOrder;
import v1.stock.test.bar.barTest;

import java.io.IOException;

public abstract class abstractAlgo extends barTest implements Runnable{
	private static boolean longOpen = true;
	private static boolean shortOpen = true;
	private static boolean tradable = true;

	private static void openLong(int shares, String ticker) throws IOException, InterruptedException {
		alpacaOrder.openLongMarket(pAPIkey, psecret, ticker, shares, "day");
	}

	private static void openShort(int shares, String ticker) throws IOException, InterruptedException {
		alpacaOrder.openShortMarket(pAPIkey, psecret, ticker, shares, "day");
	}

	private static void reverse(int shares, String ticker, boolean isLong) throws IOException, InterruptedException {
		alpacaOrder.close(ticker, pAPIkey, secret);
		if(isLong)
			alpacaOrder.openShortMarket(pAPIkey, psecret, ticker, shares, "day");
		else
			alpacaOrder.openLongMarket(pAPIkey, psecret, ticker, shares, "day");
	}
}
