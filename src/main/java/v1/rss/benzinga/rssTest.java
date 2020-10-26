package v1.rss.benzinga;

import v1.rss.benzinga.gui.newsDashboard;
import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.util.JniLoader;

import java.io.IOException;
import java.net.URISyntaxException;

public class rssTest {
	public static void main(String[] args) throws IOException {
		rssFeedParser parser = new rssFeedParser("https://www.benzinga.com/analyst-ratings/feed");
		feed feed = parser.readFeed();

		rssFeedParser parser2 = new rssFeedParser("https://www.benzinga.com/markets/options/feed");
		feed feed2 = parser2.readFeed();

		JniLoader.load();
		try(JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();
				newsDashboard.show(imGui, feed);
				newsDashboard.show(imGui, feed2);
				imGui.render();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
