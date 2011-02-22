/**   
 * Copyright 2011 The Buzz Media, LLC
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.thebuzzmedia.sjxp;

import org.junit.Test;

import com.thebuzzmedia.sjxp.rule.DefaultRule;

import static junit.framework.Assert.*;

public class ComplexTest extends AbstractTest {
	public static final String[] TITLES = new String[] {
			"Wisconsin’s Ties to Labor Fray as Its Economy Shifts",
			"American Held in Pakistan Shootings Worked With C.I.A.",
			"Danger Pent Up Behind Aging Dams" };
	public static final String[] ORIG_LINKS = new String[] {
			"http://www.nytimes.com/2011/02/22/us/22union.html?partner=rss&amp;emc=rss",
			"http://www.nytimes.com/2011/02/22/world/asia/22pakistan.html?partner=rss&amp;emc=rss",
			"http://www.nytimes.com/2011/02/22/science/22dam.html?partner=rss&amp;emc=rss" };
	public static final String[] CREATORS = new String[] {
			"By A. G. SULZBERGER and MONICA DAVEY",
			"By MARK MAZZETTI, ASHLEY PARKER, JANE PERLEZ and ERIC SCHMITT",
			"By HENRY FOUNTAIN" };
	public static final String[] PUB_DATES = new String[] {
			"Mon, 21 Feb 2011 23:40:19 GMT", "Mon, 21 Feb 2011 23:18:52 GMT",
			"Mon, 21 Feb 2011 22:20:20 GMT" };

	private int count = 0;

	private boolean[] OK_TITLES = new boolean[TITLES.length];
	private boolean[] OK_ORIG_LINKS = new boolean[ORIG_LINKS.length];
	private boolean[] OK_CREATORS = new boolean[CREATORS.length];
	private boolean[] OK_PUB_DATES = new boolean[PUB_DATES.length];

	@Test
	public void test() {
		setDebug(true);
		XMLParser parser = new XMLParser(new TitleRule(), new OrigLinkRule(),
				new CreatorRule(), new PubDateRule());
		parser.parse(this.getClass().getResourceAsStream(
				"resources/namespace.xml"));

		assertTrue(isAllOK(OK_TITLES, OK_ORIG_LINKS, OK_CREATORS, OK_PUB_DATES));
	}

	class TitleRule extends DefaultRule {
		public TitleRule() {
			super(Type.CHARACTER, "/rss/channel/item/title");
		}

		@Override
		public void handleParsedCharacters(String text) {
			assertEquals(TITLES[count], text);
			OK_TITLES[count] = true;
		}
	}

	class OrigLinkRule extends DefaultRule {
		public OrigLinkRule() {
			super(Type.CHARACTER,
					"/rss/channel/item/[http://www.pheedo.com/namespace/pheedo]origLink");
		}

		@Override
		public void handleParsedCharacters(String text) {
			assertEquals(ORIG_LINKS[count], text);
			OK_ORIG_LINKS[count] = true;
		}
	}

	class CreatorRule extends DefaultRule {
		public CreatorRule() {
			super(Type.CHARACTER,
					"/rss/channel/item/[http://purl.org/dc/elements/1.1/]creator");
		}

		@Override
		public void handleParsedCharacters(String text) {
			assertEquals(CREATORS[count], text);
			OK_CREATORS[count] = true;
		}
	}

	class PubDateRule extends DefaultRule {
		public PubDateRule() {
			super(Type.CHARACTER, "/rss/channel/item/pubDate");
		}

		@Override
		public void handleParsedCharacters(String text) {
			assertEquals(PUB_DATES[count], text);
			OK_PUB_DATES[count++] = true;
		}
	}
}