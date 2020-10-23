package news.rss.nasdaq;

import java.util.ArrayList;
import java.util.List;

public class feed {
	String title;
	String link;
	String description;
	String language;
	rssType rssType;

	List<item> entries = new ArrayList<item>();
	public feed(String title, String link, String description, String language){
		this.description = description;
		this.language = language;
		this.link = link;
		this.rssType = rssType;
		this.title = title;
	}

	public List<item> getEntries(){
		return entries;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public news.rss.nasdaq.rssType getRssType() {
		return rssType;
	}

	public String getLanguage() {
		return language;
	}

	public String getLink() {
		return link;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setRssType(news.rss.nasdaq.rssType rssType) {
		this.rssType = rssType;
	}

	public void setTitle(String title) {
		this.title = title;
	}


}
