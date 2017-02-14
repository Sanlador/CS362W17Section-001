package org.cs362.dominion;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite(AllTests.class.getName());
		//$JUnit-BEGIN$
		suite.addTestSuite(AdventurerTest.class);
		suite.addTestSuite(BaronTest.class);
		suite.addTestSuite(CouncilRoomTest.class);
		suite.addTestSuite(CutPurseTest.class);
		suite.addTestSuite(DeckTest.class);
		suite.addTestSuite(EmbargoTest.class);
		suite.addTestSuite(FeastTest.class);
		suite.addTestSuite(GameTest.class);
		suite.addTestSuite(GardensTest.class);
		suite.addTestSuite(GreatHallTest.class);
		suite.addTestSuite(MineTest.class);
		suite.addTestSuite(MinionTest.class);
		suite.addTestSuite(PlayerTest.class);
		suite.addTestSuite(RemodelTest.class);
		suite.addTestSuite(SalavagerTest.class);
		suite.addTestSuite(SeaHagTest.class);
		suite.addTestSuite(SmithyTest.class);
		suite.addTestSuite(StewardTest.class);
		suite.addTestSuite(TreasureMapTest.class);
		suite.addTestSuite(VillageTest.class);
		//$JUnit-END$
		return suite;
	}

}
