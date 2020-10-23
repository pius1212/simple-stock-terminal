package news.rss.nasdaq;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum rssType {
	HEADLINES("NDAQ Original Content"),
	COMMODOTIES("Commodities"),
	CRYPTO("Cryptocurrencies"),
	DIVIDENDS("Dividends"),
	EARNINGS("Earnings"),
	ETF("ETFs"),
	IPO("IPOs"),
	MARKETS("Markets"),
	OPTIONS("Options"),
	STOCKS("Stocks");

	private static final Map<String, rssType> stringTonetworksMap;

	private final String networkValue;

	static {
		stringTonetworksMap = new HashMap<String, rssType>();
		for (rssType rssType : EnumSet.allOf(rssType.class)) {
			stringTonetworksMap.put(rssType.getRssTypeValue(), rssType);
		}
	}

	rssType(String value) {
		this.networkValue = value;
	}

	public String getRssTypeValue() {
		return networkValue;
	}
}
