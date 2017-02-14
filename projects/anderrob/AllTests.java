package dominion;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ BureaucratTestTest.class, Cellar.class, ChancellorTest.class, Chapel.class, ChapelTest.class,
		CouncilRoomTest.class, FeastTest.class, FestivalTest.class, LaboratoryTest.class, LibraryTest.class,
		SmithyTest.class, VillageTest.class, TurnTest.class })
public class AllTests {

}
