
package dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

public class myTest {
	
	@Test
	public void testCouncilRoom(){
			
	    List<Card> cards;
	    int [] piles = new int [17];
		
	    GameState state,testState;
		Randomness.reset(10);	   

	    int newCards = 3;  
//	 // initialize a game state and player cards
		 cards = new ArrayList<Card>(Card.createCards());
		 state = new GameState(cards, piles) ;  
		 testState = new GameState(cards, piles);
	
	      Player player = new Player(state, "player-1");
	      state.addPlayer(player);

	         
	      player = new Player(state, "player-2");
	      state.addPlayer(player);


	      testState= state;   
	      //Initialize the game!
	      state.initializeGame();
	      System.out.println("player1 hand is: " + state.players.get(0).hand);
	      
	      assertEquals(state.players.get(0).discard.size(), 10);
	      assertEquals(state.players.get(1).discard.size(), 10);
	      
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Smithy));
	      
	      assertEquals(state.players.get(0).hand.size(), 1);

	      state.players.get(0).hand.remove(Card.getCard(cards,Card.CardName.Smithy));
	      
	      assertEquals(state.players.get(0).hand.size(), 0);
	      
	      //TEST COUNCILROOM
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Councilroom));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).hand.size(), 4);
	      assertEquals(state.players.get(0).numBuys, 0);
	      
	}
	@Test
    public void testAdventurer(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();
	      
	      //TEST ADVENTURER
	  state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Adventurer));

    }
	
	@Test
    public void testAmbassador(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
	      
	
      	  //TEST AMBASSADOR
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Ambassador));
	      int oldv = state.piles[8];
	      int newv = state.piles[8]+1;


	      
	      assertEquals(oldv+1, newv);
			
	      
	      
	    

	}
	@Test
    public void testBaron(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);

      testState= state;   

      state.initializeGame();	
	      
	
	      //TEST BARON
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Baron));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).numBuys, 2);
			
	      
	      
	    

	}
	@Test
    public void testCutpurse(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
	      
	
	      //TEST CUTPURSE
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Cutpurse));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).deck.size(), 0);
	      assertEquals(state.players.get(0).hand.size(), 0);	
	      
	      
	    

	}
	@Test
    public void testEmbargo(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
	      
	
	      //TEST EMBARGO
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Embargo));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).hand.size(), 0);
			
	      
	      
	    

	}
	@Test
    public void testFeast(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
	      
	
	      //TEST FEAST
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Feast));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).hand.size(), 1);
			
	      
	      
	    

	}
	@Test
    public void testGardens(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   
      
      state.initializeGame();	
	      
	
	      //TEST GARDENS
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Gardens));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).hand.size(), 0);
			
	      
	      
	    

	}
	@Test
    public void testGreatHall(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
	      
	
	      //TEST GREAT_HALL
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Great_hall));
	      int holder= state.players.get(0).hand.size();
	      state.players.get(0).playKingdomCard();
	      assertEquals(holder, state.players.get(0).hand.size());
			
	      
	      
	    

	}
	@Test
    public void testMine(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);
	
      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
	      
	
	      //TEST MINE
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Mine));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).hand.size(), 0);
			
	      
	      
	    

	}
	@Test
    public void testSmithy(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
	      
	
	      //TEST SMITHY
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Smithy));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).hand.size(), 0);
			
	      
	      
	    

	}
	@Test
    public void testVillage(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
	      
	
	      //TEST VILLAGE
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Village));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).hand.size(), 1);
	      assertEquals(state.players.get(0).numActions, 1);	      
	      
	    

	}
	@Test
    public void testSpy(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
	      
	
	      //TEST SPY
	      state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Spy));
	      state.players.get(0).playKingdomCard();
	      assertEquals(state.players.get(0).hand.size(), 1);
	      assertEquals(state.players.get(0).numActions, 1);
			
	      
	      
	    

	}
	@Test
    public void testWholeGame(){
	List<Card> cards;
    int [] piles = new int [17];
	
    GameState state,testState;
	Randomness.reset(10);	   

    int newCards = 3;  

	 cards = new ArrayList<Card>(Card.createCards());
	 state = new GameState(cards, piles) ;  
	 testState = new GameState(cards, piles);

      Player player = new Player(state, "player-1");
      state.addPlayer(player);

         
      player = new Player(state, "player-2");
      state.addPlayer(player);


      testState= state;   

      state.initializeGame();	
      System.out.println("/******************PLAYING GAME**********************/");
      System.out.println("Initialization DominionBoard:\n " + state.toString());
      
      HashMap<Player, Integer> winners=state.play(state);
      System.out.println ("/******************FINISHED GAME**********************/");
    
      for(Player p: winners.keySet()){
    	  System.out.println ("Player name: "+winners.get(p) + " , Score: "+ winners.get(p) );
      }
      
      
	      
	
    } 

	
}
