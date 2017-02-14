package cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class CardTest {
	
	//tests the play functionality of cards

	//   public  static void main(String args[]) throws CloneNotSupportedException{
	  @Test
	  public void test0()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state,testState;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Smithy));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize + 4;
		      System.out.println("hand count = " + handSize);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      
		      Card c = Card.getCard(cards, Card.CardName.Smithy);
		      c.play(state.players.get(0), state);
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
		      assertEquals(state.players.get(0).hand.size(), after);
		      
		      

		      
		      

	   }
	  
	  @Test
	  public void test1()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize + 2;
		      System.out.println("hand count = " + handSize);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      Card c = Card.getCard(cards, Card.CardName.Adventurer);
		      c.play(state.players.get(0), state);
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      assertEquals(state.players.get(0).hand.size(), after);
	   }
	  
	  
	  @Test
	  public void test2()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Village));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize + 1;
		      System.out.println("hand count = " + handSize);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      Card c = Card.getCard(cards, Card.CardName.Village);
		      c.play(state.players.get(0), state);
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions + ", expected = " + 3);
		      System.out.println("hand buys = " + state.players.get(0).buys);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      assertEquals(state.players.get(0).hand.size(), after);
		      assertEquals(state.players.get(0).actions, 3);
	   }
	  
	  
	  @Test
	  public void test3()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Baron));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize -1;
		      System.out.println("hand count = " + handSize);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      Card c = Card.getCard(cards, Card.CardName.Baron);
		      c.play(state.players.get(0), state);
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins + ", expected = " + 4);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys + ", expected= " + 2);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      assertEquals(state.players.get(0).hand.size(), after);
		      assertEquals(state.players.get(0).coins, 4);
		      assertEquals(state.players.get(0).buys, 2);
	   }
	  
	  
	  @Test
	  public void test4()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Cutpurse));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(1).hand.size();
		      System.out.println(" player2 hand count = " + handSize);
		      int after = handSize - 1;
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      Card c = Card.getCard(cards, Card.CardName.Cutpurse);
		      c.play(state.players.get(0), state);
		      
		      System.out.println("player 2 hand count = " + state.players.get(1).hand.size() + ", expected = " + after);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins + ", expected = " + 2);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      assertEquals(state.players.get(1).hand.size(), after);
		      assertEquals(state.players.get(0).coins, 2);
	   }
	  
	  
	  @Test
	  public void test5()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.GreatHall));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize + 1;
		      System.out.println("hand count = " + handSize);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      Card c = Card.getCard(cards, Card.CardName.GreatHall);
		      c.play(state.players.get(0), state);
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions + ", expected = " + 2);
		      System.out.println("hand buys = " + state.players.get(0).buys);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      assertEquals(state.players.get(0).hand.size(), after);
		      assertEquals(state.players.get(0).actions, 2);
	   }
	  
	  
	  @Test
	  public void test6()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.CouncilRoom));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after1 = handSize + 4;
		      System.out.println("hand count = " + handSize);
		      System.out.println("player 2 hand count = " + state.players.get(1).hand.size());
		      int after2 = state.players.get(1).hand.size() + 1;
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      Card c = Card.getCard(cards, Card.CardName.CouncilRoom);
		      c.play(state.players.get(0), state);
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after1);
		      System.out.println("player 2 hand count = " + state.players.get(1).hand.size() + ", expected = " + after2);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys + ", expected = " + 2);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      assertEquals(state.players.get(0).hand.size(), after1);
		      assertEquals(state.players.get(1).hand.size(), after2);
		      assertEquals(state.players.get(0).buys, 2);
	   }
	  
	  
	  @Test
	  public void test7()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Feast));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize - 1;
		      System.out.println("hand count = " + handSize);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
//		      Card c = Card.getCard(cards, Card.CardName.Feast);
//		      c.play(state.players.get(0), state);
		      
		      state.players.get(0).playKingdomCard();
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
		      System.out.println("hand discard = " + state.players.get(0).discard.size() + ", expected = " + 1);
		      assertEquals(state.players.get(0).hand.size(), after);
		      assertEquals(state.players.get(0).discard.size(), 1);
		      
		      List<Card> Feasts = Card.filterName(state.players.get(0).hand, Card.CardName.Feast);
		      assertEquals(Feasts.size(), 0);
	   }
	  
	  
	  @Test
	  public void test8()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Ambassador));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize - 3;
		      System.out.println("hand count = " + handSize);
		      System.out.println("player 2 hand discard = " + state.players.get(1).discard.size());
		      int after2 = state.players.get(1).discard.size() + 1;
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      state.players.get(0).playKingdomCard();
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
//		      System.out.println("player 2 hand count = " + state.players.get(1).hand.size());
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      System.out.println("hand discard = " + state.players.get(1).discard.size() + ", expected = " + after2);
		      assertEquals(state.players.get(0).hand.size(), after);
		      assertEquals(state.players.get(1).discard.size(), after2);
	   }
	  
	  
	  @Test
	  public void test10()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Minion));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize;
		      System.out.println("hand count = " + handSize);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      state.players.get(0).playKingdomCard();
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins + ", expected = " + 1);
		      System.out.println("hand actions = " + state.players.get(0).actions + ", expected = " + 2);
		      System.out.println("hand buys = " + state.players.get(0).buys + ", expected = " + 2);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      assertEquals(state.players.get(0).hand.size(), after);
		      assertEquals(state.players.get(0).coins, 1);
		      assertEquals(state.players.get(0).actions, 1);
		      assertEquals(state.players.get(0).buys, 2);
	   }
	  
	  
	  @Test
	  public void test9()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Embargo));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize - 1;
		      System.out.println("hand count = " + handSize);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      state.players.get(0).playKingdomCard();
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins + ", expected = " + 2);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      assertEquals(state.players.get(0).hand.size(), after);
		      assertEquals(state.players.get(0).coins, 2);
		      assertEquals(state.players.get(0).playedCards.size(), 0);
	   }
	  
	  
	  @Test
	  public void test11()  throws Throwable  {	   
		    List<Card> cards;
		    GameState state;
			Randomness.reset(10);	   

		    int newCards = 3;  
//		 // initialize a game state and player cards
			cards = new ArrayList<Card>(Card.createCards());
			 state = new GameState(cards);  
//			
		      Player player = new Player(state, "player-1");
		         player.hand.add(Card.getCard(cards,Card.CardName.Mine));

		         state.addPlayer(player);
		       player = new Player(state, "player-2");
		         state.addPlayer(player);
		         
		      //Initialize the game!
		      state.initializeGame();
		      
		      int handSize = state.players.get(0).hand.size();
		      int after = handSize - 1;
		      System.out.println("hand count = " + handSize);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
	      
		      System.out.println("hand hand = " + state.players.get(0).hand.size());
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      
		      state.players.get(0).playKingdomCard();
		      
		      System.out.println("hand count = " + state.players.get(0).hand.size() + ", expected = " + after);
		      System.out.println("hand deck = " + state.players.get(0).deck.size());
		      System.out.println("hand coins = " + state.players.get(0).coins);
		      System.out.println("hand actions = " + state.players.get(0).actions);
		      System.out.println("hand buys = " + state.players.get(0).buys);
		      System.out.println("hand discard = " + state.players.get(0).discard.size());
		      assertEquals(state.players.get(0).hand.size(), after);
	   }
	
	
}