package com.thebuzzmedia.sjxp.rule;

import org.junit.Test;

import com.thebuzzmedia.sjxp.rule.IRule.Type;

import static junit.framework.Assert.*;

public class DefaultRuleTest {
	public static final String PATH_EMPTY = "";
	public static final String PATH_ROOT = "/";
	public static final String PATH_SIMPLE = "/library/book";
	public static final String PATH_COMPLEX = "/[http://www.w3.org/1999/02/22-rdf-syntax-ns#]RDF/[http://purl.org/rss/1.0/]channel";

	public static final String ATTR_EMPTY = "";
	public static final String ATTR_SIMPLE = "a";
	public static final String ATTR_COMPLEX = "[http://www.w3.org/1999/02/22-rdf-syntax-ns#]about";

	@Test
	public void testNullType() {
		try {
			new DefaultRule(null, PATH_ROOT);
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
	public void testRootPath() {
		assertNotNull(new DefaultRule(Type.CHARACTER, PATH_ROOT));
	}

	@Test
	public void testSimplePath() {
		assertNotNull(new DefaultRule(Type.CHARACTER, PATH_SIMPLE));
	}

	@Test
	public void testComplexPath() {
		assertNotNull(new DefaultRule(Type.CHARACTER, PATH_COMPLEX));
	}

	@Test
	public void testNullAttribute() {
		try {
			new DefaultRule(Type.ATTRIBUTE, PATH_ROOT);
			assertTrue(false);
		} catch (IllegalArgumentException e) {
			assertTrue(true);
		}
	}

	@Test
	public void testEmptyAttribute() {
		assertNotNull(new DefaultRule(Type.ATTRIBUTE, PATH_ROOT, ATTR_EMPTY));
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