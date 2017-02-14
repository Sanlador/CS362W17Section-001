package org.cs362.dominion;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ testAdventurer.class, testAmbassador.class, testBaron.class, testCoins.class, testCouncilRoom.class,
		testFeast.class, testFestival.class, testGreatHall.class, testLaboratory.class, testMine.class,
		testSalvager.class, testSmithy.class, testVillage.class })
public class AllTests {

}
