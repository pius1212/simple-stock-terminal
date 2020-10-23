package v1.stock.handler;

import v1.stock.alpaca.alpacaPositions;
import v1.stock.polygon.polygonListener;
import v1.stock.test.bar.barHandler;

import java.io.IOException;

import static v1.stock.global.apiCredentials.*;

public class changeTicker {
	public static void changeTicker(String ticker) throws IOException, InterruptedException {
		barHandler.updateBars(ticker, APIkey, secret);
		alpacaPositions.updatePositionsAndOrders(pAPIkey, psecret);
	}

	public static void updatePolygonListener(String ticker, polygonListener pl){
		pl.replaceTicker(ticker);
	}
}
