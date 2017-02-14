package org.cs362.dominion;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	testGameStart.class,
	testAdventurer.class,
	testAmbassador.class,
	testBaron.class,
	testCoins.class,
	testCouncilRoom.class,
	testFeast.class,
	testFestival.class,
	testGardens.class,
	testGreatHall.class,
	testLaboratory.class,
	testMine.class,
	testSalvager.class,
	testSmithy.class,
	testVillage.class,
	testTurn.class })
public class AllTests {}
