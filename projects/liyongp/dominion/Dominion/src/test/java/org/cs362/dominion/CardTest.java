import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class CardTest {
	private List<Card> cards;
    private GameState state, testState, controlState;
    private Player player1;
    private Player player2;
    private Player control;
    private Player player;
    
// // initialize a game state and player cards
	@Before
	public void reset(){
		cards = new ArrayList<Card>(Card.createCards());
		state = new GameState(cards);
		
		controlState = new GameState(cards);
		
		player1 = new Player(state, "player-1");
		state.addPlayer(player1);
		player2 = new Player(state, "player-2");
		state.addPlayer(player2);
		
		control = new Player(controlState, "Controled player 1");
		controlState.addPlayer(control);
	         
	    //Initialize the game!
		state.initializeGame();
	}
		
		//--------------------  TESTING TREASURE CARDS  --------------------
		//--------------------  TESTING TREASURE CARDS  --------------------
		
	
		//TEST ____ COPPER ____
		//TEST ____ COPPER ____
	@Test
	public void copperTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Copper).getCost(), 0); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Copper).getTreasureValue(), 1);
		assertEquals(Card.getCard(cards, Card.CardName.Copper).getType(), Card.Type.TREASURE);
		assertEquals(Card.getCard(cards, Card.CardName.Copper).score(), 0);
		
		player=(Player) control.clone();								//Clone control player
		player.hand.add(Card.getCard(cards, Card.CardName.Copper));		//ADD Copper to hand
		player.playTtreasureCard();										//Player use Treasure Cards(1 Copper)
		assertEquals(player.coins, 1);									//Assertions
		assertEquals(player.playedCards.size(), 1);
		assertEquals(player.hand.size(), 0);
	}
		
		//TEST ____ SILVER ____
		//TEST ____ SILVER ____
	@Test
	public void silverTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Silver).getCost(), 3); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Silver).getTreasureValue(), 2);
		assertEquals(Card.getCard(cards, Card.CardName.Silver).getType(), Card.Type.TREASURE);
		assertEquals(Card.getCard(cards, Card.CardName.Silver).score(), 0);
		
		player=(Player) control.clone();								//Clone control player
		player.hand.add(Card.getCard(cards, Card.CardName.Silver));		//ADD Silver to hand
		player.playTtreasureCard();										//Player use Treasure Cards(1 Silver)
		assertEquals(player.coins, 2);									//Assertions
		assertEquals(player.playedCards.size(), 1);
		assertEquals(player.hand.size(), 0);
	}
		
		//TEST ____ GOLD ____
		//TEST ____ GOLD ____
	@Test
	public void goldTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Gold).getCost(), 6); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Gold).getTreasureValue(), 3);
		assertEquals(Card.getCard(cards, Card.CardName.Gold).getType(), Card.Type.TREASURE);
		assertEquals(Card.getCard(cards, Card.CardName.Gold).score(), 0);
		
		player=(Player) control.clone();								//Clone control player
		player.hand.add(Card.getCard(cards, Card.CardName.Gold));		//ADD Gold to hand
		player.playTtreasureCard();										//Player use Treasure Cards(1 Gold)
		assertEquals(player.coins, 3);									//Assertions
		assertEquals(player.playedCards.size(), 1);
		assertEquals(player.hand.size(), 0);
	}
		
		//--------------------  TESTING VICTORY CARDS  --------------------
		//--------------------  TESTING VICTORY CARDS  --------------------
		
		
		//TEST ____ CURSE ____
		//TEST ____ CURSE ____
	@Test
	public void curseTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Curse).getCost(), 0); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Curse).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Curse).getType(), Card.Type.VICTORY);
		assertEquals(Card.getCard(cards, Card.CardName.Curse).score(), -1);
		
		player=(Player) control.clone();										//Clone control player
		player.hand.add(Card.getCard(cards, Card.CardName.Curse));				//ADD Curse to hand
		assertEquals(player.scoreFor(), -1);									//Assertions
		assertEquals(player.hand.size(), 1);
	}
	
	
		//TEST ____ ESTATE ____
		//TEST ____ ESTATE ____
	@Test
	public void estateTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Estate).getCost(), 2); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Estate).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Estate).getType(), Card.Type.VICTORY);
		assertEquals(Card.getCard(cards, Card.CardName.Estate).score(), 1);
		
		player=(Player) control.clone();									//Clone control player
		player.hand.add(Card.getCard(cards, Card.CardName.Estate));			//ADD Estate to hand
		assertEquals(player.scoreFor(), 1);									//Assertions
		assertEquals(player.hand.size(), 1);
	}
		
		//TEST ____ DUCHY ____
		//TEST ____ DUCHY ____
	@Test
	public void duckyTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Duchy).getCost(), 5); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Duchy).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Duchy).getType(), Card.Type.VICTORY);
		assertEquals(Card.getCard(cards, Card.CardName.Duchy).score(), 3);
		
		player=(Player) control.clone();									//Clone control player
		player.hand.add(Card.getCard(cards, Card.CardName.Duchy));			//ADD Duchy to hand
		assertEquals(player.scoreFor(), 3);									//Assertions
		assertEquals(player.hand.size(), 1);
	}
		
		//TEST ____ PROVINCE ____
		//TEST ____ PROVINCE ____
	@Test
	public void provinceTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Province).getCost(), 8); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Province).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Province).getType(), Card.Type.VICTORY);
		assertEquals(Card.getCard(cards, Card.CardName.Province).score(), 6);
		
		player=(Player) control.clone();									//Clone control player
		player.hand.add(Card.getCard(cards, Card.CardName.Province));		//ADD Province to hand
		assertEquals(player.scoreFor(), 6);									//Assertions
		assertEquals(player.hand.size(), 1);
	}
		
		//--------------------  TESTING ACTION CARDS  --------------------
		//--------------------  TESTING ACTION CARDS  --------------------
		
		
		//TEST ____ ADVENTURER ____
		//TEST ____ ADCENTURER ____
	@Test
	public void adventurerTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getCost(), 6); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Adventurer).score(), 0);
		
		testState=(GameState) state.clone();													//Clone State, need initialized board
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Adventurer));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size()+2, testState.players.get(0).hand.size()); //Adventurer is added +1 then move to played card -1 then we gain 2 Treasure cards +2
		assertEquals(testState.players.get(0).playedCards.size(), 1); 							//PlayedCards +1
	}
	
		//TEST ____ AMBASSADOR ____
		//TEST ____ AMBASSADOR ____
	@Test
	public void ambassadorTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getCost(), 3); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Ambassador).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Ambassador).score(), 0);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Ambassador));		//ADD Ambassador to hand
		testState.players.get(0).playKingdomCard();												//Play Ambassador
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size(), testState.players.get(0).hand.size()+2); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).playedCards.size(), 1); 							//To supply so only played 1 card
	}
	
		//TEST ____ BARON ____
		//TEST ____ BARON ____
	@Test
	public void baronTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Baron).getCost(), 4); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Baron).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Baron).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Baron).score(), 0);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.remove(0);											//remove a card add estate to ensure estate is in hand
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Estate));
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Baron));		//ADD Baron to hand
		//System.out.println(testState.players.get(0).discard.size());
		testState.players.get(0).playKingdomCard();											//Play card	
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		//System.out.println(testState.players.get(0).discard.size());
		assertEquals(state.players.get(0).hand.size() , testState.players.get(0).hand.size()+1); 
		assertEquals(testState.players.get(0).playedCards.size(), 1); 
		assertEquals(testState.players.get(0).discard.size(), state.players.get(0).discard.size()+1); 
		assertEquals(testState.players.get(0).coins, 4); 
	}
		
		//TEST ____ COUNCIL_ROOM ____
		//TEST ____ COUNCIL_ROOM ____
	@Test
	public void council_roomTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getCost(), 5); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Council_Room).score(), 0);
		//Play Adventure
		System.out.println("State: " + state.players.get(0).hand.size());		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Council_Room));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size()+2 , testState.players.get(0).hand.size()); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).playedCards.size(), 1); 
		assertEquals(testState.players.get(0).numBuys, 2); 
	}
		
		//TEST ____ CUTPURSE ____
		//TEST ____ CUTPURSE ____
	@Test
	public void cutpurseTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getCost(), 4); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getTreasureValue(), 2);
		assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Cutpurse).score(), 0);
		
		
		testState=(GameState) state.clone();//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Cutpurse));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());	
		assertTrue(state.players.get(0).hand.size() < testState.players.get(0).hand.size()); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).coins, 2); 
		assertEquals(testState.players.get(0).playedCards.size(), 1); 
	}
	
	
		//TEST ____ EMBARGO ____
		//TEST ____ EMBARGO ____
	@Test
	public void embargoTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Embargo).getCost(), 2); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Embargo).getTreasureValue(), 2);
		assertEquals(Card.getCard(cards, Card.CardName.Embargo).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Embargo).score(), 0);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Embargo));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size(), testState.players.get(0).hand.size()); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).playedCards.size(), 0); 
	}
	
		
		//TEST ____ FEAST ____
		//TEST ____ FEAST ____
	@Test
	public void feastTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Feast).getCost(), 4); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Feast).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Feast).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Feast).score(), 0);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Feast));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size(), testState.players.get(0).hand.size()); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).playedCards.size(), 0); 
		assertEquals(testState.players.get(0).coins, 5); 
	}
	
		
		//TEST ____ FESTIVAL ____
		//TEST ____ FESTIVAL ____
	@Test
	public void festivalTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Festival).getCost(), 5); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Festival).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Festival).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Festival).score(), 0);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Festival));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size(), testState.players.get(0).hand.size()); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).playedCards.size(), 1); 
		assertEquals(testState.players.get(0).numActions, 2); // action +2 but this card used the 1 action we started with
		assertEquals(testState.players.get(0).numBuys, 2); 
		assertEquals(testState.players.get(0).coins, 2); 
	}
	
		//TEST ____ GARDENS ____
		//TEST ____ GARDENS ____
	@Test
	public void gardensTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Gardens).getCost(), 4); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Gardens).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Gardens).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Gardens).score(), 0);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Gardens));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size() , testState.players.get(0).hand.size()); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).scoreFor(), 4); //+1 points for 10 cards
	}
		
		//TEST ____ GREAT_HALL ____
		//TEST ____ GREAT_HALL ____
	@Test
	public void great_hallTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getCost(), 3); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Great_Hall).score(), 1);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Great_Hall));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size()+1, testState.players.get(0).hand.size()); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).playedCards.size(), 1); 
		assertEquals(testState.players.get(0).numActions, 1);
		assertEquals(testState.players.get(0).scoreFor(), state.players.get(0).scoreFor() + 1);
	}
	
		
		//TEST ____ MINE ____
		//TEST ____ MINE ____
	@Test
	public void mineTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Mine).getCost(), 5); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Mine).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Mine).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Mine).score(), 0);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Mine));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		System.out.println("SSAS" +state.players.get(0).hand.size());									
		System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size(), testState.players.get(0).hand.size()); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).playedCards.size(), 1);
		testState.players.get(0).playTtreasureCard();
		state.players.get(0).playTtreasureCard();
		assertEquals(testState.players.get(0).coins, state.players.get(0).coins +1); //lose copper add silver = -1 +2 = 1 ;; lose silver add gold = -2 +3 = 1.
		
	}
	
		//TEST ____ SMITHY ____
		//TEST ____ SMITHY ____
	@Test
	public void smithyTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Smithy).getCost(), 4); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Smithy).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Smithy).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Smithy).score(), 0);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Smithy));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size() +3 , testState.players.get(0).hand.size()); 	//Draw 3 cards, smithy is in playedcard.
		assertEquals(testState.players.get(0).playedCards.size(), 1); 
	}
	
	
		//TEST ____ VILLAGE ____
		//TEST ____ VILLAGE ____
	@Test
	public void villageTest() throws Throwable {
		assertEquals(Card.getCard(cards, Card.CardName.Village).getCost(), 3); //Assert basic stats of card
		assertEquals(Card.getCard(cards, Card.CardName.Village).getTreasureValue(), 0);
		assertEquals(Card.getCard(cards, Card.CardName.Village).getType(), Card.Type.ACTION);
		assertEquals(Card.getCard(cards, Card.CardName.Village).score(), 0);
		
		testState=(GameState) state.clone();													//Clone control State, assert involves two players
		testState.players.get(0).hand.add(Card.getCard(cards, Card.CardName.Village));		//ADD Adventurer to hand
		testState.players.get(0).playKingdomCard();												//Play Adventure
		//System.out.println(state.players.get(0).hand.size());									
		//System.out.println(testState.players.get(0).hand.size());
		assertEquals(state.players.get(0).hand.size() +1, testState.players.get(0).hand.size()); 	//Ambassador move 1 or 2 cards from hand to supply
		assertEquals(testState.players.get(0).playedCards.size(), 1); 
		assertEquals(testState.players.get(0).numActions, 2); 
	
	}

}
