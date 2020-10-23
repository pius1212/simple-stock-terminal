package stock.test.rss;

import news.rss.nasdaq.feed;
import news.rss.nasdaq.item;
import news.rss.nasdaq.rssFeedParser;

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
