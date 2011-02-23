package com.thebuzzmedia.sjxp;

import org.junit.Test;

import com.thebuzzmedia.sjxp.rule.DefaultRule;
import com.thebuzzmedia.sjxp.rule.IRule.Type;

import static junit.framework.Assert.*;

/**
 * NOTE: This test must be run on it's own from a cold start so the system
 * properties set when the XMLParser class is instantiated. That is why it is
 * not part of {@link AllTests}.
 */
public class EnvVarTest {
	@Test
	public void testEnvVars() {
		// Toggle all the properties to the opposite of their defaults
		System.setProperty("sjxp.debug", "true");
		System.setProperty("sjxp.namespaces", "false");
		System.setProperty("sjxp.validation", "false");

		// Cause the class to load
		new XMLParser(new DefaultRule(Type.CHARACTER, "/test"));

		assertTrue(XMLParser.DEBUG);
		assertFalse(XMLParser.ENABLE_NAMESPACES);
		assertFalse(XMLParser.ENABLE_VALIDATION);
	}
}