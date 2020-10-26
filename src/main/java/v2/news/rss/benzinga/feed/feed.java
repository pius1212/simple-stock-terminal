package v2.news.rss.benzinga.feed;

import java.util.ArrayList;
import java.util.List;

public class feed {
	final String title;
	final String link;
	final String description;
	final String language;
	final String copyright;
	final String pubDate;

	final List<feedMessage> entries = new ArrayList<feedMessage>();

	public feed(String title, String link, String description, String language,
				String copyright, String pubDate) {
		this.title = title;
		this.link = link;
		this.description = description;
		this.language = language;
		this.copyright = copyright;
		this.pubDate = pubDate;
	}

	public List<feedMessage> getMessages() {
		return entries;
	}

	public void addMessage(feedMessage message) {
		entries.add(message);
	}

	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	}

	public String getLanguage() {
		return language;
	}

	public String getCopyright() {
		return copyright;
	}

	public String getPubDate() {
		return pubDate;
	}

	@Override
	public String toString() {
		return "feed [copyright=" + copyright + ", description=" + description
				+ ", language=" + language + ", link=" + link + ", pubDate="
				+ pubDate + ", title=" + title + "]";
	}
}
