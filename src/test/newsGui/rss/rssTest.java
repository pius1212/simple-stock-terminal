package test.newsGui.rss;

import com.sun.syndication.io.FeedException;

import java.io.IOException;

public class rssTest {
	public static void main(String[] args) throws IOException, FeedException {
//		URL url = new URL("https://www.benzinga.com/analyst-ratings/feed");
//
//		SyndFeedInput input = new SyndFeedInput();
//		SyndFeed feed = input.build(new XmlReader(url));
//
//		System.out.println(feed);
		rssFeedParser parser = new rssFeedParser("https://www.benzinga.com/analyst-ratings/feed");

		feed feed = parser.readFeed();
		System.out.println(feed);
		for (feedMessage message : feed.getMessages()) {
			System.out.println(message);
		}
	}
}
