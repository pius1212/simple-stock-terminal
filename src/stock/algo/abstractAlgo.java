package stock.algo;

import stock.alpaca.alpacaOrder;
import stock.test.bar.barTest;

import java.io.IOException;

public abstract class abstractAlgo extends barTest implements Runnable{
	private static void openLong(int shares, String ticker) throws IOException, InterruptedException {
		alpacaOrder.openLongMarket(pAPIkey, psecret, ticker, shares, "day");
	}

	private static void openShort(int shares, String ticker) throws IOException, InterruptedException {
		alpacaOrder.openShortMarket(pAPIkey, psecret, ticker, shares, "day");
	}

	private static void reverse(int shares, String ticker){

	}
}
