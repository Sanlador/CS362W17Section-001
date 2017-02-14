package org.cs362.dominion;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestSuite {


    @Test
    public void testVillageCard() throws Throwable{
        // initialize game
        List<Card> cards;
        GameState state;
        Randomness.reset(10);
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);

        Player player = new Player(state, "player-1");
        state.addPlayer(player);
        player = new Player(state, "player-2");
        state.addPlayer(player);

        //Initialize the game!
        state.initializeGame();

        // put Village in players hand to guarantee the action play
        state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Village));

        // compare the number of actions to what is expected after Kingdom Card Play
        int action_counter = 2;
        state.players.get(0).playKingdomCard();
        System.out.println("Player should have:  " +action_counter+" Actions. Player actually has: " +state.players.get(0).numActions);
        assertEquals(action_counter, state.players.get(0).numActions);
        // print condition
        System.out.println("Made it to end of Village Test Successfully!");
    }



    @Test
    public void testGameOver()  throws Throwable  {


        // run through a basic game of Dominion to check for proper ending value.
        PlayDominion Dominion = new PlayDominion();;
        Dominion.main(new String[] {"arg1", "arg2", "arg3"});
        assertEquals(Dominion.gameOver, 1);
        System.out.println("Game exited Properly");

        // Create gameboard from scratch for next gameover check
        List<Card> cards;
        GameState state;
        Randomness.reset(10);

//		 // initialize a game state and player cards
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
//
        // set up players
        Player player = new Player(state, "player-1");

        state.addPlayer(player);
        player = new Player(state, "player-2");
        state.addPlayer(player);

        //Initialize the game!
        state.initializeGame();

        // Set Province Count to 0 (This should force end the game because of the Rules for an empty Province Deck)
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Province), 0);
        System.out.println("Game should be over on check: ");
        assertTrue(state.isGameOver());



    }


    @Test
    public void testMoney()  throws Throwable  {
        // startup initial game
        List<Card> cards;
        GameState state;
        Randomness.reset(10);

//		 // initialize a game state and player cards
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
//
        Player player = new Player(state, "player-1");

        state.addPlayer(player);
        player = new Player(state, "player-2");
        state.addPlayer(player);

        //Initialize the game!
        state.initializeGame();

        // give the player Silver and Gold (To check if both give the proper Treasure Value)
        state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Silver));
        state.players.get(0).hand.add(Card.getCard(cards,Card.CardName.Gold));
        int value = 0;
        // loop through what is expected
        for(Card c: state.players.get(0).hand){
            value+=c.getTreasureValue();
        }
        state.players.get(0).playTtreasureCard();

        // compare what is expecetd to what actually was given to treasure values.
        System.out.println("Value in hand = " + value + "Treasure value played = " + state.players.get(0).coins);

        // true assertion to end
        assertEquals(value, state.players.get(0).coins);

    }

    @Test
    public void testSmithy()  throws Throwable {
        // This is the very first test that I completed, was a base point for many of the other tests
        // in this program.
        List<Card> cards;
        GameState state;
        Randomness.reset(10);

//		 // initialize a game state and player cards
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
//
        Player player = new Player(state, "player-1");
        state.addPlayer(player);

        player = new Player(state, "player-2");
        state.addPlayer(player);

        //Initialize the game!
        state.initializeGame();


        // Remove All the Cards from the users hand.
        int inhand = player.hand.size();
        for(int i = 0; i < inhand; i++){
            player.discard(player.hand.get(0));
        }

        int inplay = player.playedCards.size();

        for(int j = 0; j < inplay; j++){
            Card c = player.playedCards.get(0);
            player.playedCards.remove(c);
            player.discard.add(c);
        }
        // Now we only give the user a Smithy and make them play it.
        int newCards = 3;
        // check if (when if No Kingdom Cards will return properly)
        player.playKingdomCard();
        player.hand.add(Card.getCard(cards,Card.CardName.Smithy));
        player.playKingdomCard();

        // After Smithy Play check if the player actually drew 3 new cards.
        inhand = player.hand.size();
        assertEquals(inhand, newCards);

    }

    @Test
    public void TestRandom() throws Throwable{
        Randomness randCheck = new Randomness();
        // step through main Random function.
        // This test is used primarily for Code Coverage
        randCheck.main(new String[] {"arg1", "arg2", "arg3"});

    }

    @Test
    public void TestTooManyPlayers() throws Throwable{
        // Tests if too many players will cause a game end.

        List<Card> cards;
        GameState state;
        Randomness.reset(10);

//		 // initialize a game state and player cards
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
//
        Player player = new Player(state, "player-1");
        state.addPlayer(player);

        player = new Player(state, "player-2");
        state.addPlayer(player);

        player = new Player(state, "player-3");
        state.addPlayer(player);

        //Initialize the game!
        state.initializeGame();

        // compare hand size to out of bounds
        assertNotEquals(state.players.size(),2);
        System.out.println("Number of players is out of Bounds!");
    }



    @Test

    public void TestEmptyKingdomPile() throws Throwable

    {
        // Create gameboard from scratch for next gameover check
        List<Card> cards;
        GameState state;
        Randomness.reset(10);

		 // initialize a game state and player cards
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);

        Player player = new Player(state, "player-1");

        state.addPlayer(player);
        player = new Player(state, "player-2");
        state.addPlayer(player);

        //Initialize the game!
        state.initializeGame();

        // set Piles all to 0.
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 0);
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 0);
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 0);
        System.out.println("Game should be over on check: ");
        // This is because by the rules if 3 piles are empty the game should end.
        assertTrue(state.isGameOver());
    }

    @Test
    public void TestKingdomCards() throws Throwable{
        List<Card> cards;
        GameState state;
        Randomness.reset(10);

//		 // initialize a game state and player cards
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
//
        Player player = new Player(state, "player-1");
        state.addPlayer(player);

        player = new Player(state, "player-2");
        state.addPlayer(player);

        //Initialize the game!
        state.initializeGame();


        // Run Through Each Card checking for proper runthrough.
        player.hand.add(Card.getCard(cards,Card.CardName.Adventurer));
        player.playKingdomCard();

        // baron checks if the number of buys has incrimented to 2.
        player.numActions=1;
        player.hand.add(Card.getCard(cards,Card.CardName.Baron));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Baron)));
        player.playKingdomCard();
        assertFalse(player.hand.contains(Card.getCard(cards,Card.CardName.Baron)));
       assert(player.numBuys == 2);

       // Ambassador checks if the card was successfully removed from the hand after.
        player.numActions=1;
        player.hand.add(Card.getCard(cards,Card.CardName.Ambassador));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Ambassador)));
        player.playKingdomCard();
        assertFalse(player.hand.contains(Card.getCard(cards,Card.CardName.Ambassador)));

        // Council Room Checks for +1 Buy and +4 Cards.
        player.numActions=1;
        player.numBuys=1;
        player.hand.add(Card.getCard(cards,Card.CardName.CouncilRoom));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.CouncilRoom)));
        player.playKingdomCard();
        assertFalse(player.hand.contains(Card.getCard(cards,Card.CardName.CouncilRoom)));
        assert(player.numBuys == 2);
        assert(player.hand.size() > 0);

        // Cutpurse Guarantees +2 Coins for the treasure buy.
        player.numActions=1;
        player.coins = 0;
        player.hand.add(Card.getCard(cards,Card.CardName.Cutpurse));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Cutpurse)));
        player.playKingdomCard();
        assertFalse(player.hand.contains(Card.getCard(cards,Card.CardName.Cutpurse)));
        assert(player.coins == 2);

        // Embargo also adds +2 Coins.
        player.coins = 0;
        player.numActions=1;
        player.hand.add(Card.getCard(cards,Card.CardName.Embargo));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Embargo)));
        player.playKingdomCard();
        assertFalse(player.hand.contains(Card.getCard(cards,Card.CardName.Embargo)));
        assert(player.coins == 2);

        // Feast Guarantees that the Feast card is Removed from the gamme after Action.
        player.numActions=1;
        player.hand.add(Card.getCard(cards,Card.CardName.Feast));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Feast)));
        player.playKingdomCard();
        assertFalse(player.discard.contains(Card.getCard(cards,Card.CardName.Feast)));

        // Great Hall Adds 1 action and Cards to hand.
        player.numActions=1;
        player.hand.add(Card.getCard(cards,Card.CardName.GreatHall));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.GreatHall)));
        player.playKingdomCard();
        assertFalse(player.hand.contains(Card.getCard(cards,Card.CardName.GreatHall)));
        assert(player.numActions ==1);
        assert(player.hand.size() > 0);

        // Gardens are not played since it's not an action.
        player.numActions=1;
        player.hand.add(Card.getCard(cards,Card.CardName.Gardens));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Gardens)));
        player.playKingdomCard();
        // This check is false because Gardens is not an action card.
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Gardens)));
        assert(player.numActions==1);

        // market ads 1 Card, 1 action, 1 buy, and 1 gold
        player.numBuys=1;
        player.coins=0;
        player.numActions=1;
        player.hand.add(Card.getCard(cards,Card.CardName.Market));
        player.deck.add(Card.getCard(cards,Card.CardName.Gold));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Market)));
        player.playKingdomCard();
        assertFalse(player.hand.contains(Card.getCard(cards,Card.CardName.Market)));
        assert(player.numBuys==2);
        assert(player.coins == 1);
        assert(player.numActions==1);

        // First check for the case that the hand does not contain any Treasure Cards to upgrade.
        player.numBuys=1;
        player.hand.add(Card.getCard(cards,Card.CardName.Mine));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Mine)));
        player.coins=0;
        player.playKingdomCard();
        assertFalse(player.hand.contains(Card.getCard(cards,Card.CardName.Mine)));

        // Now we add a Silver Card and check for the upgrade
        player.hand.add(Card.getCard(cards,Card.CardName.Silver));
        player.numActions=1;
        player.hand.add(Card.getCard(cards,Card.CardName.Mine));
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Mine)));
        player.playKingdomCard();
        assertFalse(player.hand.contains(Card.getCard(cards,Card.CardName.Mine)));
        // Since the Mine Forces the player to buy a Treasure card with an increased value The Silver can becomee a Copper/Silver Or Gold
        assertTrue(player.hand.contains(Card.getCard(cards,Card.CardName.Gold)) || player.hand.contains(Card.getCard(cards,Card.CardName.Copper)) || player.hand.contains(Card.getCard(cards,Card.CardName.Silver)));


    }



}
