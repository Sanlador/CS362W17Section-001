package ORG.CS362.DOMINION;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BuycardTest.class, CardTest.class, DiscardTest.class, DrawTest.class, PlayernumTest.class,
		ShuffleTest.class })
public class AllTests {

}
