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
		System.out.println(feed);

		JniLoader.load();
		try(JImGui imGui = new JImGui()) {
			imGui.initBeforeMainLoop();
			while (!imGui.windowShouldClose()) {
				imGui.initNewFrame();
				newsDashboard.show(imGui, feed);
				imGui.render();
			}
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}
}
