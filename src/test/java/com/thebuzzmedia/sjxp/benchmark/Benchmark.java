package com.thebuzzmedia.sjxp.benchmark;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.thebuzzmedia.sjxp.XMLParser;
import com.thebuzzmedia.sjxp.rule.DefaultRule;
import com.thebuzzmedia.sjxp.rule.IRule;
import com.thebuzzmedia.sjxp.rule.IRule.Type;

public class Benchmark {
	public static final IRule[] HACKERNEWS_RULES = new IRule[] {
			new DefaultRule(Type.CHARACTER, "/rss/channel/item/title") {
				@Override
				public void handleParsedCharacters(XMLParser parser, String text) {
					count++;
				}
			}, new DefaultRule(Type.CHARACTER, "/rss/channel/item/link") {
				@Override
				public void handleParsedCharacters(XMLParser parser, String text) {
					count++;
				}
			} };

	public static final IRule[] BUGZILLA_RULES = new IRule[] {
			new DefaultRule(Type.ATTRIBUTE, "/bugzilla/bug/long_desc/who",
					"name") {
				@Override
				public void handleParsedAttribute(XMLParser parser, int index, String value) {
					count++;
				}
			},
			new DefaultRule(Type.CHARACTER, "/bugzilla/bug/long_desc/thetext") {
				@Override
				public void handleParsedCharacters(XMLParser parser, String text) {
					count++;
				}
			} };

	public static final IRule[] CRAIGSLIST_RULES = new IRule[] {
			new DefaultRule(
					Type.ATTRIBUTE,
					"/[http://www.w3.org/1999/02/22-rdf-syntax-ns#]RDF/[http://purl.org/rss/1.0/]item",
					"[http://www.w3.org/1999/02/22-rdf-syntax-ns#]about") {
				@Override
				public void handleParsedAttribute(XMLParser parser, int index, String value) {
					count++;
				}
			},
			new DefaultRule(
					Type.CHARACTER,
					"/[http://www.w3.org/1999/02/22-rdf-syntax-ns#]RDF/[http://purl.org/rss/1.0/]item/[http://purl.org/rss/1.0/]description") {
				@Override
				public void handleParsedCharacters(XMLParser parser, String text) {
					count++;
				}
			} };

	public static final IRule[] TECHCRUNCH_RULES = new IRule[] {
			new DefaultRule(Type.CHARACTER, "/rss/channel/item/title") {
				@Override
				public void handleParsedCharacters(XMLParser parser, String text) {
					count++;
				}
			}, new DefaultRule(Type.CHARACTER, "/rss/channel/item/link") {
				@Override
				public void handleParsedCharacters(XMLParser parser, String text) {
					count++;
				}
			} };

	public static final IRule[] SAMSUNG_RULES = new IRule[] {
			new DefaultRule(Type.CHARACTER, "/rss/channel/item/title") {
				@Override
				public void handleParsedCharacters(XMLParser parser, String text) {
					count++;
				}
			}, new DefaultRule(Type.CHARACTER, "/rss/channel/item/link") {
				@Override
				public void handleParsedCharacters(XMLParser parser, String text) {
					count++;
				}
			},
			new DefaultRule(Type.CHARACTER, "/rss/channel/item/description") {
				@Override
				public void handleParsedCharacters(XMLParser parser, String text) {
					count++;
				}
			} };

	public static final IRule[] ECLIPSE_XML_STRESS_RULES = new IRule[] { new DefaultRule(
			Type.CHARACTER,
			"/motorcarrierfreightdetails/motorcarrierfreightdetail/additionallineitems/additionallineitem/quantityandweight") {
		@Override
		public void handleParsedCharacters(XMLParser parser, String text) {
			count++;
		}
	} };

	public static final IRule[] DICTIONARY_RULES = new IRule[] { new DefaultRule(
			Type.CHARACTER, "/dictionary/e/ss/s/qp/q/w") {
		@Override
		public void handleParsedCharacters(XMLParser parser, String text) {
			count++;
		}
	} };

	private static int count = 0;

	public static void main(String[] args) throws IOException {
		// Hacker News
		benchmark(15000, "rss-news.ycombinator.com.xml", HACKERNEWS_RULES);

		// Bugzilla
		benchmark(135000, "bugzilla-bug-feed.xml", BUGZILLA_RULES);

		// Craiglist
		benchmark(300000, "rdf-newyork.craigslist.org.xml", CRAIGSLIST_RULES);

		// TechCrunch
		benchmark(305000, "rss-techcrunch.com.xml", TECHCRUNCH_RULES);

		// Samsung
		benchmark(750000, "rss-news.samsung.com.xml", SAMSUNG_RULES);

		// Eclipse XML Stress
		benchmark(1650000, "eclipse-xml-stress-test.xml",
				ECLIPSE_XML_STRESS_RULES);

		// Dictionary
		benchmark(10650000, "dictionary.xml", DICTIONARY_RULES);
	}

	private static void benchmark(int estFileSize, String filename,
			IRule... rules) throws IOException {
		InputStream in = loadFile(estFileSize, filename);
		XMLParser parser = new XMLParser(rules);

		count = 0;
		int size = in.available();
		long startTime = System.currentTimeMillis();

		parser.parse(in);

		System.out.println("Processed " + size + " bytes, parsed " + count
				+ " XML elements in "
				+ (System.currentTimeMillis() - startTime) + "ms");
	}

	/**
	 * Used to load the file completely off-disk and into memory to avoid
	 * introducing unpredictable (and unequal) latency into the parse timing.
	 */
	private static InputStream loadFile(int estFileSize, String filename)
			throws IOException {
		int bytesRead = 0;
		int totalBytesRead = 0;
		byte[] buffer = new byte[8192];
		byte[] result = new byte[estFileSize];

		BufferedInputStream in = new BufferedInputStream(
				Benchmark.class.getResourceAsStream(filename));

		while ((bytesRead = in.read(buffer)) > 0) {
			System.arraycopy(buffer, 0, result, totalBytesRead, bytesRead);
			totalBytesRead += bytesRead;
		}

		return new ByteArrayInputStream(result, 0, totalBytesRead);
	}
}