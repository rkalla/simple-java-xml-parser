package com.thebuzzmedia.sjxp;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.thebuzzmedia.sjxp.rule.DefaultRuleTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({ DefaultRuleTest.class, AttributeOnlyTest.class,
		CharacterOnlyTest.class, ComplexTest.class, EmptyTest.class,
		NamespaceTest.class, SimpleTest.class, StopTest.class })
public class AllTests {
	// no op
}
