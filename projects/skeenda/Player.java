package dominion_v2;

import java.util.ArrayList;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import dominion_v2.Card.Type;

//Most of this is from the code given to us in class Ali Aburas

public class Player{
	List<Card> hand = new ArrayList<Card>();
	LinkedList<Card> deck = new LinkedList<Card>();
	List<Card> discard = new ArrayList<Card>();
	List<Card> playedCards = new ArrayList<Card>();
	
	String playerName;
	int numActions;
	int numBuys;
	int coins;
	
	final GameState gameState;
	
	public Player(GameState gameState, String playerName)
	{
		this.playerName = playerName;
		this.gameState = gameState;
	}
	
	final Card drawCard()
	{
		if (deck.isEmpty())
		{
			System.out.println("reshuffling deck of " + playerName + " to draw 5 cards" );
			while(discard.size() > 0)
			{
				int ndx = (int)Randomness.nextRandomInt(discard.size());
				deck.add(discard.remove(ndx));
			}
		}
		Card toDraw = deck.poll();
		hand.add(toDraw);
		System.out.println("draw " + toDraw);
		//Collections.sort(hand);
		return toDraw;
		
	}
	
	
	final boolean gain(Card card)
	{
		discard.add(card);
		System.out.println("Player: " + this.playerName + " gains" +card);
		return true;
	}
	
	public void discard(Card card)
	{
		hand.remove(card);
		discard.add(card);
		System.out.println("Player: " + playerName + " discards "+ card);
	}
	
	
	final int scoreFor()
	{
		int score = 0;
		for(Card c : hand)
			score += c.playerScore(this);
		for(Card c : discard)
			score += c.playerScore(this);
		for(Card c : deck)
			score += c.playerScore(this);
		
		return score;
	}	
	
	
	public void playKingdomCard()
	{
		while( numActions > 0)
		{
			List<Card> actionCards = Card.filter(hand, Type.Action);
			
			if(actionCards.size() == 0)
			{
				return;
			}
			Card c = (Card)actionCards.get(0);
			if(c == null)
			{
				return;
			}
			System.out.println("Player actionPhase Card: " + c.toString());
			
			playedCards.add(c);
			numActions -= 1;
			
			c.play(this, gameState);
			discard(c);
		}
	}

	
	public void playTreasureCard()
	{
		List<Card> treasureCards = Card.filter(hand, Type.Treasure);
		if(treasureCards.size() == 0)
		{
			return;
		}
		for(int i = 0; i < treasureCards.size(); i++)
		{
			Card c = (Card)treasureCards.get(i);
			if(c == null)
			{
				return;
			}
		
			playedCards.add(c);
			c.play(this, gameState);
			discard(c);
			//System.out.println("CARDS: " + c);
			//System.out.println("Coins:" + coins);
		}
	}
	
	public void buyCard()
	{
		int value;
		//Implement if value of them equal null then do something else
		while( numBuys > 0 )
		{
			if( coins <= 1){
				//buy copper
				//Card.getCard(gain(Card.CardName.Copper));
				gain(Card.getCard(GameState.cards, Card.CardName.Copper));
				value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Copper));
				GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Copper), value - 1);
				numBuys--;
				//remove one copper from field
			}
			else if( coins == 2)
			{
				Random rand = new Random();
				int randomNum = rand.nextInt((3-1)+1)+1;
				System.out.println("RandomNum: " + randomNum);
				//System.out.println("I am here");
				boolean test = true;
				while(test){
				//Switch statement
					switch(randomNum){
						case 1:
						
							gain(Card.getCard(GameState.cards, Card.CardName.Estate));
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Estate));
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Estate), value - 1);
							test = false;
						
						case 2:
						
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Cellar));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Cellar), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Cellar));
							test = false;
						
						case 3:
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Chapel));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Chapel), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Chapel));
							test = false;
						
					}
				}
				numBuys--;
				coins = coins - 2;
				//rng, buy estate, cellar, chapel
				//remove from field
			}
			else if( coins == 3)
			{
				Random rand = new Random();
				int randomNum = rand.nextInt((3-1)+1)+1;
				System.out.println("RandomNum: " + randomNum);
				boolean test = true;
				while(test){
				//switch statement
					switch(randomNum){
						case 1:

							System.out.println("Drawing Silver");
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Silver));
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Silver), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Silver));
							test = false;
						
						case 2:
						
						
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Chancellor));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Chancellor), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Chancellor));
							test = false;
						
						case 3:
						
						
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Woodcutter));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Woodcutter), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Woodcutter));
							test = false;
						
					}
				}
					
				numBuys--;
				coins = coins - 3;
				//buy silver, chancellor, woodcutter
				//remove from field
			}
			else if( coins == 4)
			{
				Random rand = new Random();
				int randomNum = rand.nextInt((3-1)+1)+1;
				System.out.println("RandomNum: " + randomNum);
				boolean test = true;
				//Switch Statement
				while(test){
					switch(randomNum){
						case 1 :
					
							System.out.println("Bought Bureaucrat");
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Bureaucrat));
							if(value == 0)
							{
								value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Silver));
								GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Silver), value - 1);
								gain(Card.getCard(GameState.cards, Card.CardName.Silver));	
								test = false;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Bureaucrat), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Bureaucrat));
							test = false;
								
						case 2:
						
						
							System.out.println("Bought Smithy");
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Smithy));
							if(value == 0)
							{
								value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Silver));
								GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Silver), value - 1);
								gain(Card.getCard(GameState.cards, Card.CardName.Silver));	
								test = false;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Smithy), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Smithy));
							test = false;
						
						case 3:
						
							System.out.println("Bought Feast");
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Feast));
							if(value == 0)
							{
								value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Silver));
								GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Silver), value - 1);
								gain(Card.getCard(GameState.cards, Card.CardName.Silver));	
								test = false;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Feast), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Feast));
							test = false;
					}					
				}
				
				numBuys--;
				coins = coins - 4;
				//rng buy Bureaucrat, smithy, feast
				//remove from field
			}
			else if( coins == 5)
			{
				Random rand = new Random();
				int randomNum = rand.nextInt((6-1)+1)+1;
				System.out.println("RandomNum: " + randomNum);
				boolean test = true;
				while(test){
				//Switch Statement
					switch(randomNum){
						case 1:
						
							gain(Card.getCard(GameState.cards, Card.CardName.Dutchy));
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Dutchy));
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Dutchy), value - 1);
							test = false;
						
						case 2:
						
						
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.CouncilRoom));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.CouncilRoom), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.CouncilRoom));
							test = false;
						
						case 3:
						
					
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Festival));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Festival), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Festival));
							test = false;
						
						case 4:
						
					
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Laboratory));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Laboratory), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Laboratory));
							test = false;
						
						case 5:
						
					
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Library));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Library), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Library));
							test = false;
						
						case 6:
						
						
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Market));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Market), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Market));
							test = false;
								
					}
				}
				
				numBuys--;
				coins = coins - 5;
				//rng buy duchy, council room, Festival, lab, library, market

			}
			
			else if(coins == 6)
			{
				Random rand = new Random();
				int randomNum = rand.nextInt((2-1)+1)+1;
				System.out.println("RandomNum: " + randomNum);
				boolean test = true;
				while(test){
				//switch statement
					switch(randomNum){
						case 1:
						
							gain(Card.getCard(GameState.cards, Card.CardName.Gold));
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Gold));
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Gold), value - 1);
							test = false;
						
						case 2:
						
					
							value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Adventurer));
							if(value == 0)
							{
								randomNum = 1;
								break;
							}
							GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Adventurer), value - 1);
							gain(Card.getCard(GameState.cards, Card.CardName.Adventurer));
							test = false;
						
					}
				}
				numBuys--;
				coins = coins - 6;
				//rng buy Gold, Adventurer
			}
			else if(coins == 8)
			{
				value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Province));
				GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Province), value - 1);	
				gain(Card.getCard(GameState.cards, Card.CardName.Province));
				numBuys--;
				coins = coins - 8;
			//buy province
			}
			else
			{
				gain(Card.getCard(GameState.cards, Card.CardName.Gold));
				value = GameState.gameBoard.get(Card.getCard(GameState.cards, Card.CardName.Gold));
				GameState.gameBoard.put(Card.getCard(GameState.cards, Card.CardName.Gold), value - 1);
				numBuys--;
				coins = coins - 6;
			}	
		}
		System.out.println("You are out of Purchases");
	}


	
	public void endTurn()
	{
		for(int i = 0; i < hand.size(); i++)
		{
			Card c = hand.get(i);
			discard(c);
		}
		for(int i = 0; i < 5; i++)
		{
			drawCard();
		}
	}

}