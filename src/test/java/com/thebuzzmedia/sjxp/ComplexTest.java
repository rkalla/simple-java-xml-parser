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
	public static final String[] ABOUTS = new String[] {
			"http://news.slashdot.org/story/11/02/21/2210212/Anonymous-Denies-Targeting-Westboro-Baptist-Church?from=rss",
			"http://ask.slashdot.org/story/11/02/21/215258/Ask-Slashdot-Is-There-a-War-Against-Small-Mail-Servers?from=rss",
			"http://news.slashdot.org/story/11/02/21/2058243/Physicists-Build-Bigger-Bottles-For-Antimatter?from=rss" };
	public static final String[] TITLES = new String[] {
			"Anonymous Denies Targeting Westboro Baptist Church",
			"Ask Slashdot: Is There a War Against Small Mail Servers?",
			"Physicists Build Bigger 'Bottles' For Antimatter" };
	public static final String[] SUBJECTS = new String[] { "internet",
			"communications", "news" };
	public static final String[] DEPTS = new String[] {
			"listen-to-admiral-ackbar", "lazy-spam-prevention",
			"go-big-or-go-home" };

	private int count = 0;

	private boolean[] OK_ABOUTS = new boolean[ABOUTS.length];
	private boolean[] OK_TITLES = new boolean[TITLES.length];
	private boolean[] OK_SUBJECTS = new boolean[SUBJECTS.length];
	private boolean[] OK_DEPTS = new boolean[DEPTS.length];

	@Test
	public void test() {
		setDebug(true);
		XMLParser parser = new XMLParser(new AboutRule(), new TitleRule(),
				new SubjectRule(), new DeptRule());
		parser.parse(this.getClass().getResourceAsStream(
				"resources/complex.xml"));

		assertTrue(isAllOK(OK_ABOUTS, OK_TITLES, OK_SUBJECTS, OK_DEPTS));
	}

	class AboutRule extends DefaultRule {
		public AboutRule() {
			super(
					Type.ATTRIBUTE,
					"/[http://www.w3.org/1999/02/22-rdf-syntax-ns#]RDF/[http://purl.org/rss/1.0/]item",
					"[http://www.w3.org/1999/02/22-rdf-syntax-ns#]about");
		}

		@Override
		public void handleParsedAttribute(int index, String value) {
			assertEquals(ABOUTS[count], value);
			OK_ABOUTS[count] = true;
		}
	}

	class TitleRule extends DefaultRule {
		public TitleRule() {
			super(
					Type.CHARACTER,
					"/[http://www.w3.org/1999/02/22-rdf-syntax-ns#]RDF/[http://purl.org/rss/1.0/]item/[http://purl.org/rss/1.0/]title");
		}

		@Override
		public void handleParsedCharacters(String text) {
			assertEquals(TITLES[count], text);
			OK_TITLES[count] = true;
		}
	}

	class SubjectRule extends DefaultRule {
		public SubjectRule() {
			super(
					Type.CHARACTER,
					"/[http://www.w3.org/1999/02/22-rdf-syntax-ns#]RDF/[http://purl.org/rss/1.0/]item/[http://purl.org/dc/elements/1.1/]subject");
		}

		@Override
		public void handleParsedCharacters(String text) {
			assertEquals(SUBJECTS[count], text);
			OK_SUBJECTS[count] = true;
		}
	}

	class DeptRule extends DefaultRule {
		public DeptRule() {
			super(
					Type.CHARACTER,
					"/[http://www.w3.org/1999/02/22-rdf-syntax-ns#]RDF/[http://purl.org/rss/1.0/]item/[http://purl.org/rss/1.0/modules/slash/]department");
		}

		@Override
		public void handleParsedCharacters(String text) {
			assertEquals(DEPTS[count], text);
			OK_DEPTS[count++] = true;
		}
	}
}