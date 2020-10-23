package v1.rss.benzinga.gui;

import v1.rss.benzinga.feed;
import v1.rss.benzinga.feedMessage;
import org.ice1000.jimgui.JImGui;

import java.io.IOException;
import java.net.URISyntaxException;

public class newsDashboard {
	public static void show(JImGui imGui, feed feed) throws IOException, URISyntaxException {
		imGui.begin("News");
		for (feedMessage message : feed.getMessages()) {
			imGui.pushTextWrapPos();
			message.show(imGui);
		}
		imGui.end();
	}
}
