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

public class CharacterOnlyTest extends AbstractTest {
	public static final String[] TITLES = new String[] { "I Like Ham",
			"That's What She Said", "I Crack Myself Up" };
	public static final String[] AUTHORS = new String[] {
			"Vincent Von Hamshoes", "Hardly Long", "Hilarious McFunnynuts" };

	private int count = 0;
	
	private boolean[] OK_TITLES = new boolean[TITLES.length];
	private boolean[] OK_AUTHORS = new boolean[AUTHORS.length];

	@Test
	public void test() {
		XMLParser parser = new XMLParser(new TitleRule(), new AuthorRule());
		parser.parse(this.getClass().getResourceAsStream(
				"resources/character-only.xml"));
		
		assertTrue(isAllOK(OK_TITLES, OK_AUTHORS));
	}

	class TitleRule extends DefaultRule {
		public TitleRule() {
			super(Type.CHARACTER, "/library/book/title");
		}

		@Override
		public void handleParsedCharacters(XMLParser parser, String text) {
			assertEquals(TITLES[count], text);
			OK_TITLES[count] = true;
		}
	}

	class AuthorRule extends DefaultRule {
		public AuthorRule() {
			super(Type.CHARACTER, "/library/book/author");
		}

		@Override
		public void handleParsedCharacters(XMLParser parser, String text) {
			assertEquals(AUTHORS[count], text);
			OK_AUTHORS[count++] = true;
		}
	}
}