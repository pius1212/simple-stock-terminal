package v2.news.rss.benzinga.feed;

import org.ice1000.jimgui.JImGui;
import org.ice1000.jimgui.NativeBool;
import org.ice1000.jimgui.flag.JImWindowFlags;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class feedMessage {
	String title;
	String description;
	String link;
	String author;
	String guid;

	boolean isDetailed;

	public feedMessage(){

	}

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

	public boolean isDetailed() {
		return isDetailed;
	}

	public void setDetailed(boolean detailed) {
		isDetailed = detailed;
	}

	public void show(JImGui imGui) throws IOException, URISyntaxException {
		imGui.text(getTitle());
		if (imGui.button("Detailed") || isDetailed()){
			setDetailed(true);
			showDetailed(imGui, getTitle(), getDescription(), getLink());
		}
		imGui.separator();
	}

	public void showDetailed(JImGui imGui, String title, String description, String link) throws IOException, URISyntaxException {
		NativeBool openPtr = new NativeBool();
		openPtr.modifyValue(true);
		imGui.begin(title, openPtr, JImWindowFlags.NoTitleBar);

		imGui.pushTextWrapPos();

		imGui.text(title);

		imGui.separator();
		imGui.text(description);

		if(imGui.button("Link")){
			Desktop.getDesktop().browse(new URL(link).toURI());
		}
		imGui.sameLine();
		if(imGui.button("Close")){
			setDetailed(false);
		}

		imGui.end();
	}

	@Override
	public String toString() {
		return "feedMessage [title=" + title + ", description=" + description
				+ ", link=" + link + ", author=" + author + ", guid=" + guid
				+ "]";
	}
}
