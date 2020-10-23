package v1.stock.test.rss;

import v1.rss.nasdaq.feed;
import v1.rss.nasdaq.item;
import v1.rss.nasdaq.rssFeedParser;

public class test {
	public static void main(String[] args) {
		rssFeedParser parser = new rssFeedParser("https://www.nasdaq.com/feed/rssoutbound?category=Options");
		feed feed = parser.readFeed();
		System.out.println(feed);

		for(item item : feed.getEntries()){
			System.out.println(item.getDescription()); //no, its being put into objects
		}
	}
}
