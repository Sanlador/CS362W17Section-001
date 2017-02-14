package dominion_v2;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import dominion_v2.Card.CardName;
import dominion_v2.Card.Type;

public class CardTest {
	List<Card> cards = new ArrayList<Card>(Card.createCards());
	GameState state = new GameState(cards);
	Random rand = new Random();
		
	@Test
	public void testGetType() {		
		for(int i = 0; i<25; i++){
			int random = (int)Randomness.random.nextInt(cards.size());
			Card tmp = cards.get(random);
			if(tmp.getType() == Type.Action){
				continue;
			}
			else if(tmp.getType() == Type.Treasure){
				continue;
			}
			else if(tmp.getType() == Type.Victory)
			{
				continue;
			}
			else
			{
				fail("Not of correct type");
			}
		}
	}

	@Test
	public void testGetCardName() {
		int random = rand.nextInt(20)+1;
		Card tmp = cards.get(random);
		
		for(int i = 0; i < 500; i++)
		{
			if(tmp.getCardName() == CardName.Adventurer || tmp.getCardName() == CardName.Bureaucrat || tmp.getCardName() == CardName.Cellar ||
					tmp.getCardName() == CardName.Chancellor || tmp.getCardName() == CardName.Chapel || tmp.getCardName() == CardName.Copper ||
					tmp.getCardName() == CardName.CouncilRoom || tmp.getCardName() == CardName.Curse || tmp.getCardName() == CardName.Dutchy ||
					tmp.getCardName() == CardName.Estate || tmp.getCardName() == CardName.Feast || tmp.getCardName() == CardName.Festival ||
					tmp.getCardName() == CardName.Gold || tmp.getCardName() == CardName.Laboratory || tmp.getCardName() == CardName.Library ||
					tmp.getCardName() == CardName.Market || tmp.getCardName() == CardName.Province || tmp.getCardName() == CardName.Silver ||
					tmp.getCardName() == CardName.Smithy || tmp.getCardName() == CardName.Woodcutter)
			{
				continue;
			}
			else
			{
				fail("Not of correct Name");
			}
		}
	}

	@Test
	public void testGetTreasure() {
		int random = (int)Randomness.random.nextInt(cards.size());
		Card tmp = cards.get(random);
		
		if(tmp.getTreasure() == 0 || tmp.getTreasure() == 1 || tmp.getTreasure() == 2 || tmp.getTreasure() == 3)
		{
			System.out.println("Treasure pass");
		}
		else
		{
			fail("Treasure of wrong value");
		}
	}
	
	@Test
	public void testPlay(){
		Player player1 = new Player(state, "player-1");
		Player player2 = new Player(state, "player-2");
		state.addPlayer(player1);
		state.addPlayer(player2);
		state.initializeGame(10);
		Card tmp = 	Card.getCard(cards, Card.CardName.Gold);
		
		tmp.play(player1, state);
		if(player1.coins == 3 && player2.coins == 0)
		{
			System.out.println("Gold works");
			player1.coins = 0;
			player2.coins = 0;
		}
		
		tmp = Card.getCard(cards, Card.CardName.Silver);
		tmp.play(player1, state);
		if(player1.coins == 2 && player2.coins == 0)
		{
			System.out.println("Silver works");
			player1.coins = 0;
			player2.coins = 0;			
		}
		
		tmp = Card.getCard(cards, Card.CardName.Copper);
		tmp.play(player1, state);
		if(player1.coins == 1 && player2.coins == 0)
		{
			System.out.println("Copper works");
			player1.coins = 0;
			player2.coins = 0;			
		}
		
		tmp = Card.getCard(cards, Card.CardName.Cellar);
		tmp.play(player1, state);
		if(player1.numActions == 2 && player2.numActions == 1)
		{
			System.out.println("Cellar works");
			player1.numActions = 0;
			player2.numActions = 0;			
		}
		
		tmp = Card.getCard(cards, Card.CardName.Chancellor);
		tmp.play(player1, state);
		if(player1.coins == 2 && player2.coins == 0)
		{
			System.out.println("Cellar works");
			player1.coins = 0;
			player2.coins = 0;			
		}
		
		tmp = Card.getCard(cards, Card.CardName.CouncilRoom);
		tmp.play(player1, state);
		if(player1.hand.size() == 9 && player2.hand.size() == 5)
		{
			System.out.println("Cellar works");
			for(int i = 0; i < player1.hand.size(); i++)
			{
				Card c = (Card)player1.hand.get(i);
				if(c == null){
					break;
				}
				player1.discard(c);
			}
			for(int i = 0; i < player2.hand.size(); i++)
			{
				Card c = (Card)player2.hand.get(i);
				if(c == null){
					break;
				}
				player2.discard(c);
			}
			//System.out.println(player2.hand.size());
		}
		
		tmp = Card.getCard(cards, Card.CardName.Feast);
		tmp.play(player1, state);
		if(player1.coins == 5 && player2.coins == 0)
		{
			System.out.println("Feast works");
			player1.coins = 0;
			player2.coins = 0;			
		}
		
		player1.numActions = 0;
		player2.numActions = 0;
		player1.numBuys = 0;
		player2.numBuys = 0;
		player1.coins = 0;
		player2.coins = 0;
		
		tmp = Card.getCard(cards, Card.CardName.Festival);
		tmp.play(player1, state);
		if(player1.numActions == 2 && player2.numActions == 0 && player1.numBuys == 1
				&& player2.numBuys == 0 && player1.coins == 2 && player2.coins == 0)
		{
			System.out.println("Festival works");
			player1.numActions = 0;
			player2.numActions = 0;
			player1.numBuys = 0;
			player2.numBuys = 0;
			player1.coins = 0;
			player2.coins = 0;			
		}
		
		tmp = Card.getCard(cards, Card.CardName.Laboratory);
		tmp.play(player1, state);
		if(player1.hand.size() == 6 && player1.numActions == 1 && player2.numActions == 0)
		{
			System.out.println("Laboratory works");
			player1.numActions = 0;
			player2.numActions = 0;
			player1.numBuys = 0;
			player2.numBuys = 0;
			player1.coins = 0;
			player2.coins = 0;			
		}
		
		tmp = Card.getCard(cards, Card.CardName.Library);
		tmp.play(player1, state);
		if(player1.hand.size() >= 7)
		{
			System.out.println("Library works");
			player1.numActions = 0;
			player2.numActions = 0;
			player1.numBuys = 0;
			player2.numBuys = 0;
			player1.coins = 0;
			player2.coins = 0;			
		}
		
		tmp = Card.getCard(cards, Card.CardName.Market);
		tmp.play(player1, state);
		if(player1.hand.size() >= 5 && player1.numActions == 1 && player2.numActions == 0
				&& player1.numBuys == 1 && player2.numBuys == 0 && player1.coins == 1 &&
				player2.coins == 0)
		{
			System.out.println("Market works");
			player1.numActions = 0;
			player2.numActions = 0;
			player1.numBuys = 0;
			player2.numBuys = 0;
			player1.coins = 0;
			player2.coins = 0;			
		}
		
		tmp = Card.getCard(cards, Card.CardName.Smithy);
		tmp.play(player1, state);
		if(player1.hand.size() >= 5)
		{
			System.out.println("Smithy works");
			player1.numActions = 0;
			player2.numActions = 0;
			player1.numBuys = 0;
			player2.numBuys = 0;
			player1.coins = 0;
			player2.coins = 0;			
		}
		
		tmp = Card.getCard(cards, Card.CardName.Woodcutter);
		tmp.play(player1, state);
		if(player1.numBuys == 1 && player2.numBuys == 0 && player1.coins == 2 &&
				player2.coins == 0)
		{
			System.out.println("Woodcutter works");
			player1.numActions = 0;
			player2.numActions = 0;
			player1.numBuys = 0;
			player2.numBuys = 0;
			player1.coins = 0;
			player2.coins = 0;			
		}
	}

}
