package test.newsGui;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.flag.JImCondition;
import org.ice1000.jimgui.util.JniLoader;
import test.newsGui.clock.rssUpdateClock;
import test.newsGui.rss.feed;
import test.newsGui.rss.feedMessage;
import test.newsGui.rss.rssFeedParser;

import java.io.IOException;

public class main {
	public static void news(JImGui imGui, feed feed) throws IOException {
		imGui.setNextWindowPos(100, 200, JImCondition.FirstUseEver);
		imGui.begin("News");

		for (feedMessage message : feed.getMessages()) {
			message.render(imGui);
		}

		imGui.end();
	}

	public static void main(String[] args) throws IOException {
		//rssUpdateClock rssUpdateClock = new rssUpdateClock(1, "https://www.benzinga.com/analyst-ratings/feed");

		rssFeedParser parser = new rssFeedParser("https://www.benzinga.com/analyst-ratings/feed");

		feed feed = parser.readFeed();

		for (feedMessage message : feed.getMessages()) {
			System.out.println(message);
		}
		JniLoader.load();
		try(JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();

			while (!imGui.windowShouldClose()) {
				//feed feed = rssUpdateClock.getFeed();
				imGui.initNewFrame();

				news(imGui, feed);

				imGui.render();
			}
		}
	}
}
