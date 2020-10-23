package screener.snapshot;

import screener.main;

import java.io.IOException;

public class timer extends main{
	public static void timer(long delay, String apiKey){
		Thread t = new Thread(() -> {
			while(true){
				try {
					Thread.sleep(delay);
				} catch (InterruptedException ignored) { }

				try {
					snapshot = new snapshot(polygon.rest.stocksEquities.snapshot.getSnapshotAllTickers(apiKey));
				} catch (IOException ignored) { }
			}
		});
		t.start();
	}
}
