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
package com.thebuzzmedia.sjxp.rule;

import org.junit.Test;

import com.thebuzzmedia.sjxp.rule.IRule.Type;

import static junit.framework.Assert.*;

public class DefaultRuleTest {
	public static final String PATH_EMPTY = "";
	public static final String PATH_SIMPLE = "/library/book";
	public static final String PATH_TRAILING_SLASH = "/sports/baseball/";
	public static final String PATH_COMPLEX = "/[http://www.w3.org/1999/02/22-rdf-syntax-ns#]RDF/[http://purl.org/rss/1.0/]channel";

	public static final String ATTR_EMPTY = "";
	public static final String ATTR_SIMPLE = "a";
	public static final String ATTR_COMPLEX = "[http://www.w3.org/1999/02/22-rdf-syntax-ns#]about";

	@Test
	public void testNullType() {
		try {
			new DefaultRule(null, PATH_SIMPLE);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testNullPath1() {
		try {
			new DefaultRule(Type.ATTRIBUTE, null);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testNullPath2() {
		try {
			new DefaultRule(Type.CHARACTER, null);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testEmptyPath() {
		try {
			new DefaultRule(Type.CHARACTER, PATH_EMPTY);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testSimplePath() {
		assertNotNull(new DefaultRule(Type.CHARACTER, PATH_SIMPLE));
	}

	@Test
	public void testTrailingSlash() {
		try {
			new DefaultRule(Type.CHARACTER, PATH_TRAILING_SLASH);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testComplexPath() {
		assertNotNull(new DefaultRule(Type.CHARACTER, PATH_COMPLEX));
	}

	@Test
	public void testNullAttribute() {
		try {
			new DefaultRule(Type.ATTRIBUTE, PATH_SIMPLE);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testEmptyAttribute() {
		assertNotNull(new DefaultRule(Type.ATTRIBUTE, PATH_SIMPLE, ATTR_EMPTY));
	}

	@Test
	public void testSimpleAttribute() {
		assertNotNull(new DefaultRule(Type.ATTRIBUTE, PATH_SIMPLE, ATTR_SIMPLE));
	}

	@Test
	public void testComplexAttribute() {
		assertNotNull(new DefaultRule(Type.ATTRIBUTE, PATH_COMPLEX,
				ATTR_COMPLEX));
	}

	@Test
	public void testCharacterAttribute() {
		try {
			new DefaultRule(Type.CHARACTER, PATH_COMPLEX, ATTR_SIMPLE);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}
}