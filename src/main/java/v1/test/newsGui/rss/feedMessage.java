package v1.test.newsGui.rss;

import org.ice1000.jimgui.JImGui;

import java.io.IOException;

public class feedMessage {
	String title;
	String description;
	String link;
	String author;
	String guid;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	@Override
	public String toString() {
		return "feedMessage [title=" + title + ", description=" + description
				+ ", link=" + link + ", author=" + author + ", guid=" + guid
				+ "]";
	}

	public void render(JImGui imGui) throws IOException {
		//Pattern p = Pattern.compile("\$");

		imGui.separator();
		imGui.textWrapped(title);
		//imGui.image(JImTextureID.fromFile(new File("res/v1.test.png")));
		imGui.text("");
		imGui.textWrapped(description);
		imGui.text("");
		//imGui.textWrapped(description.);
	}
}
