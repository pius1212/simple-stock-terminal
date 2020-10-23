package stock.handler;

import stock.algo.wld.wldAlgo;
import stock.alpaca.alpacaPositions;
import stock.polygon.polygonListener;
import stock.polygon.polygonTickerDetailed;
import stock.test.bar.barHandler;

import java.io.IOException;
import java.net.URI;

import static stock.global.apiCredentials.*;

public class changeTicker {
	public static void changeTicker(String ticker) throws IOException, InterruptedException {
		barHandler.updateBars(ticker, APIkey, secret);
		alpacaPositions.updatePositionsAndOrders(pAPIkey, psecret);
	}

	public static void updatePolygonListener(String ticker, polygonListener pl){
		pl.replaceTicker(ticker);
	}
}
