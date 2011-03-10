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

@SuppressWarnings({ "rawtypes", "unchecked" })
public class AttributeOnlyTest extends AbstractTest {
	public static final int[] CAGES = new int[] { 1, 2 };
	public static final String[] TYPES = new String[] { "tiger", "walrus" };
	public static final boolean[] LOLZ = new boolean[] { true, false };

	private int count = 0;

	private boolean[] OK_CAGES = new boolean[CAGES.length];
	private boolean[] OK_TYPES = new boolean[TYPES.length];
	private boolean[] OK_LOLZ = new boolean[LOLZ.length];

	@Test
	public void test() {
		XMLParser parser = new XMLParser(new CageRule(), new AnimalRule());
		parser.parse(this.getClass().getResourceAsStream(
				"resources/attribute-only.xml"));

		assertTrue(isAllOK(OK_CAGES, OK_TYPES, OK_LOLZ));
	}

	class CageRule extends DefaultRule {
		public CageRule() {
			super(Type.ATTRIBUTE, "/zoo/cage", "id");
		}

		@Override
		public void handleParsedAttribute(XMLParser parser, int index,
				String value, Object userObject) {
			assertEquals(CAGES[count], Integer.parseInt(value));
			OK_CAGES[count] = true;
		}
	}

	class AnimalRule extends DefaultRule {
		public AnimalRule() {
			super(Type.ATTRIBUTE, "/zoo/cage/animal", "type",
					"canHasCheezBurger");
		}

		@Override
		public void handleParsedAttribute(XMLParser parser, int index,
				String value, Object userObject) {
			switch (index) {
			case 0:
				assertEquals(TYPES[count], value);
				OK_TYPES[count] = true;
				break;

			case 1:
				assertEquals(LOLZ[count], Boolean.parseBoolean(value));
				OK_LOLZ[count++] = true;
				break;

			default:
				assertTrue(false); // shouldn't happen
				break;
			}
		}
	}
}