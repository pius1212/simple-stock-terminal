package v2.news.rss.benzinga.feed;

import org.apache.commons.io.IOUtils;
import v2.news.rss.benzinga.feed.feed;
import v2.news.rss.benzinga.feed.feedMessage;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class rssFeedParser {
	static final String TITLE = "title";
	static final String DESCRIPTION = "description";
	static final String LANGUAGE = "language";
	static final String COPYRIGHT = "copyright";
	static final String LINK = "link";
	static final String AUTHOR = "author";
	static final String ITEM = "item";
	static final String PUB_DATE = "pubDate";
	static final String GUID = "guid";

	final URL url;

	public rssFeedParser(String feedUrl) {
		try {
			this.url = new URL(feedUrl);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}

	public feed readFeed() {
		feed feed = null;
		try {
			boolean isFeedHeader = true;
			// Set header values intial to the empty string
			String description = "";
			String title = "";
			String link = "";
			String language = "";
			String copyright = "";
			String author = "";
			String pubdate = "";
			String guid = "";

			// First create a new XMLInputFactory
			XMLInputFactory inputFactory = XMLInputFactory.newInstance();
			// Setup a new eventReader

			InputStream in = read();

			String stylingFilter = IOUtils.toString(in, StandardCharsets.UTF_8).replace("&lt;link type=&quot;text/css&quot; rel=&quot;stylesheet&quot; href=&quot;https://www.globenewswire.com/styles/gnw_nitf.css&quot; /&gt;", "").replace("&lt;p align=&quot;justify&quot;&gt;", "").replace("rel=&quot;nofollow&quot; target=&quot;_blank&quot; rel=&quot;nofollow&quot;", "").replace("&lt;p align=&quot;left&quot;", "").replace("&lt;p align=&quot;right&quot;", "");
			String htmlTagFilters = stylingFilter.replace("&lt;u&gt;", "").replace("&lt;/u&gt;", "").replace("&lt;a", "").replace("&lt;/a&gt;", "").replace("&lt;br /&gt;", "").replace("&lt;em&gt;", "").replace("&lt;/em&gt;", "").replace("&lt;/strong&gt;", "").replace("&lt;strong&gt;", "").replace("&lt;/p&gt;", "").replace("&lt;p&gt;", "").replace(" p ", " ");
			String symbolFilters = htmlTagFilters.replace("#34;", "\"").replace("#39;", "'").replace("&amp;#039;", "'").replace("&amp;", "").replace("amp;", "").replace("&#amp;", "'").replace("&quot;", "\"").replace("&#039;", "").replace("&nbsp;", "\n").replace("&rsquo;", "'").replace("&rdquo;", "\"").replace("&ldquo;", "\"");
			String linkFilters = symbolFilters.replaceAll("href=\"http.*?\\s", "").replaceAll("href=\"https.*?\\s", "");
			String tagSymbolFilters = linkFilters.replace("&lt;", "").replace("&gt;", "");
			InputStream stream = new ByteArrayInputStream(tagSymbolFilters.getBytes(StandardCharsets.UTF_8));

			XMLEventReader eventReader = inputFactory.createXMLEventReader(stream);
			// read the XML document
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					String localPart = event.asStartElement().getName()
							.getLocalPart();
					switch (localPart) {
						case ITEM:
							if (isFeedHeader) {
								isFeedHeader = false;
								feed = new feed(title, link, description, language,
										copyright, pubdate);
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
						case AUTHOR:
							author = getCharacterData(event, eventReader);
							break;
						case PUB_DATE:
							pubdate = getCharacterData(event, eventReader);
							break;
						case COPYRIGHT:
							copyright = getCharacterData(event, eventReader);
							break;
					}
				} else if (event.isEndElement()) {
					if (event.asEndElement().getName().getLocalPart().equals(ITEM)) {
						feedMessage message = new feedMessage();
						message.setAuthor(author);
						message.setDescription(description);
						message.setGuid(guid);
						message.setLink(link);
						message.setTitle(title);
						assert feed != null;
						feed.addMessage(message);
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

	private String getCharacterData(XMLEvent event, XMLEventReader eventReader)
			throws XMLStreamException {
		String result = "";
		event = eventReader.nextEvent();
		if (event instanceof Characters) {
			result = event.asCharacters().getData();
		}
		return result;
	}

	private InputStream read() {
		try {
			return url.openStream();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private String removeUrl(String commentstr) {
		String urlPattern = "((https?|ftp|gopher|telnet|file|Unsure|http):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)";
		Pattern p = Pattern.compile(urlPattern,Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(commentstr);
		int i = 0;
		while (m.find()) {
			commentstr = commentstr.replaceAll(m.group(i),"").trim();
			i++;
		}
		return commentstr;
	}
}
