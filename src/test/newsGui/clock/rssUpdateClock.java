package test.newsGui.clock;

import test.newsGui.rss.rssFeedParser;
import test.newsGui.rss.feed;

public class rssUpdateClock {

	private feed feed;
	private String url;

	public rssUpdateClock(int intervalMin, String url){
		clock(intervalMin);
		this.url = url;
	}
	private rssFeedParser update(){
		return new rssFeedParser(url);
	}

	private void clock(int intervalMin){
		Thread t = new Thread(() -> {
			while (true) {
				rssFeedParser parser = update();
				feed = parser.readFeed();

				try {
					Thread.sleep(intervalMin*60000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		t.start();
	}

	public test.newsGui.rss.feed getFeed() {
		return feed;
	}
}