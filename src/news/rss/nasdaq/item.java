package news.rss.nasdaq;

public class item {
	String title;
	String link;
	String description;
	String pubDate;
	String guid;
	String creator;
	String category;

	public item(){
		//this was intetional
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public String getDescription() {
		return description;
	} //this is all it is,

	public String getTitle() {
		return title;
	}

	public String getCategory() {
		return category;
	}

	public String getCreator() {
		return creator;
	}

	public String getGuid() {
		return guid;
	}

	public String getPubDate() {
		return pubDate;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public void setPubDate(String pubDate) {
		this.pubDate = pubDate;
	}
}
