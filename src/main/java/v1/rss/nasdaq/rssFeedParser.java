package v1.rss.nasdaq;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class rssFeedParser {
	private URL url;

	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String LANGUAGE = "language";
	static final String LINK = "link";
	static final String CREATOR = "dc:creator";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";

	public rssFeedParser(String url){
		try {
			this.url = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public feed readFeed() {

		feed feed = null;
		try {
			boolean isFeedHeader = true;
			String description = "";
			String title = "";
			String link = "";
			String language = "";
			String creator = "";
			String pubdate = "";
			String guid = "";

			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			InputStream io = url.openStream();
			XMLEventReader eventReader = inputFactory.createXMLEventReader(io);

			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName().getLocalPart();
					switch (localPart) {
						case ITEM:
							if (isFeedHeader) {
								isFeedHeader = false;
								feed = new feed(title, link, description, language);
							}
							event = eventReader.nextEvent();
							break;
						case TITLE:
							title = getCharacterData(event, eventReader);
							break;
						case DESCRIPTION:
							description = getCharacterData(event, eventReader);
							break;
						case LINK:
							link = getCharacterData(event, eventReader);
							break;
						case GUID:
							guid = getCharacterData(event, eventReader);
							break;
						case LANGUAGE:
							language = getCharacterData(event, eventReader);
							break;
						case CREATOR:
							creator = getCharacterData(event, eventReader);
							break;
						case PUB_DATE:
							pubdate = getCharacterData(event, eventReader);
							break;

					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart().equals(ITEM)) {
						item message = new item();
						message.setCreator(creator);
						message.setDescription(description);
						message.setGuid(guid);
						message.setLink(link);
						message.setTitle(title);
						message.setPubDate(pubdate);  //java didnt like it being not static lol, so i had to do this
						feed.getEntries().add(message);
						event = eventReader.nextEvent();
						continue;
					}
				}
			}
		} catch (XMLStreamException | IOException e) {
			throw new RuntimeException(e);
		}
		return feed;
	}

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader) throws XMLStreamException { //nothing
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result; //basically rss parser, yea
	}
}
