import java.util.Random;

public class Player {
	public int ID;											//The player's ID
	public int VP;											//The player's running tally of Victory Points
	int buys = 1;											//The player's buys
	int actions = 1;										//The player's actions
	int bonusCoins = 0;										//Coins from playing action cards
	public Deck hand, draw, discard;						//The player's hand, draw pile, and discard piles
	
	public Player(int playerNum, Random rand) {							
		ID = playerNum;
		VP = 0;
		hand = new Deck();
		draw = new Deck();
		discard = new Deck();
		
		for(int i = 0; i < 7; i++) {						//Starting deck is 7 Copper, 3 Estates
			draw.addCard(new Copper());
			if(i > 3) { draw.addCard(new Estate()); }
		}
		draw.shuffle(rand);
	}
	
	public void takeTurn(DomGame domGame) {					//The player takes their turn. Action, Buy, etc
		buys = 1;
		actions = 1;
		bonusCoins = 0;
		if(hand.size == 0) {								//Fill the hand if it's empty
			fillHand(domGame.rand);
		}
		hand.print();										//Print the hands contents
		
		actionAI(domGame);									//Play any action cards
		buyAI(domGame);										//Buy cards
		totalPoints();										//Calculate total victory points
		while(hand.size != 0) {
			discard.addCard(hand.drawTop());
		}	
		fillHand(domGame.rand);
		
	}
	
	public void discardToDraw(Random rand) {							//Shuffle when discard into draw pile is empty
		while(discard.size != 0) {
			draw.addCard(discard.drawTop());
		}
		draw.shuffle(rand);
	}
	
	public void fillHand(Random rand) {
		for(int i = 0; i < 5; i++) {						//Draw 5 cards at beginning of turn if no cards
			if(draw.size == 0) { discardToDraw(rand); }
			hand.addCard(draw.drawTop());
		}
	}
	
	public int buyCard(DomGame domGame, int coins, String cardName) {
		for(int i = 0; i < 7; i++) {
			if(domGame.basicCards.get(i).cards.size() != 0) {
				if(cardName == domGame.basicCards.get(i).cards.get(0).name && coins >= domGame.basicCards.get(i).cards.get(0).cost) {
					int cardCost = domGame.basicCards.get(i).cards.get(0).cost;
					discard.addCard(domGame.basicCards.get(i).drawTop());
					coins -= cardCost;
					for(int j = 0; j < domGame.basicCards.get(i).embargoes; j++) {
						if(domGame.basicCards.get(3).size != 0) { 
						discard.addCard(domGame.basicCards.get(3).drawTop()); 
						System.out.println("Gained a Curse due to Embargo");
						}
					}
					System.out.println("Buying " + cardName + " for " + cardCost + " coins.");
				}
			}
		}
		
		for(int i = 0; i < 10; i++) {
			if(domGame.kingdomCards.get(i).cards.size() != 0) {
				if(cardName == domGame.kingdomCards.get(i).cards.get(0).name && coins >= domGame.kingdomCards.get(i).cards.get(0).cost) {
					int cardCost = domGame.kingdomCards.get(i).cards.get(0).cost;
					discard.addCard(domGame.kingdomCards.get(i).drawTop());
					coins -= cardCost;
					for(int j = 0; j < domGame.kingdomCards.get(i).embargoes; j++) {
						if(domGame.basicCards.get(3).size != 0) { discard.addCard(
							domGame.basicCards.get(3).drawTop()); 
							System.out.println("Gained a Curse due to Embargo");
						}
					}
					System.out.println("Buying " + cardName + " for " + cardCost + " coins.");
				}
			}
		}
		return coins;
		
	}
	public void buyAI(DomGame domGame) {
		hand.update();
		int coins = hand.value + bonusCoins;
		while(buys > 0 && coins != 0) {
			System.out.println("Buys: " + buys + " Coins: " + coins);
			if(coins >= 8) {
				coins = buyCard(domGame, coins, "Province");
				System.out.println(domGame.basicCards.get(6).size + " Provinces left.");
			}
			else if(coins >= 6) {
				int goldChance = domGame.rand.nextInt(9);
				if(goldChance > 7) { buyCard(domGame, coins, "Adventurer"); }
				else { coins = buyCard(domGame, coins, "Gold"); }
			}
			
			else {
				int silverChance = domGame.rand.nextInt(5);
				int oldCoins = coins;
				if(silverChance >= 3) {
					int randomKingdom = domGame.rand.nextInt(10);
					coins = buyCard(domGame, coins, domGame.kingdomCards.get(randomKingdom).cards.get(0).name);
					if(oldCoins == coins) { coins = buyCard(domGame, coins, "Silver"); }  				//Buys silver if it couldn't afford the random card it picked
				}
				else { coins = buyCard(domGame, coins, "Silver"); }
			}
			buys -= 1;
		}
		System.out.println("Buys: " + buys + " Coins: " + coins);
		//System.out.println("Card Count: " + getCardCount() + " Total Value: " + getTotalValue() + " Average Value: " + getAverageValue());
	}
	
	public void actionAI(DomGame domGame) {
		hand.printCards();
		while(actions > 0) 	{
			System.out.println("Actions: " + actions + " Buys: " + buys + " Cards: " + hand.getSize());
			int playIndex = 0;
			for(int i = 0; i < hand.getSize(); i++) {
				if(hand.cards.get(i).playOrder < hand.cards.get(playIndex).playOrder) { 
					System.out.println(hand.cards.get(i).name + " has sooner play order than " + hand.cards.get(playIndex).name);
					playIndex = i; 
				}
			}
			if(hand.cards.get(playIndex).cardType == Card.Type.ACTION || hand.cards.get(playIndex).cardType == Card.Type.ATTACK) { hand.cards.get(playIndex).play(domGame, ID, playIndex); }
			else { System.out.println("Not playing a card"); }
			actions--;
			hand.print();
			hand.printCards();
		}	
	}
	
	public int getCardCount() {
		return hand.getSize() + draw.getSize() + discard.getSize();
	}
	
	public int getTotalValue() {
		return hand.getValue() + draw.getValue() + discard.getValue();
	}
	
	public float getAverageValue() {
		return ((float)getTotalValue() / (float)getCardCount());
	}
	
	public int getActionCount() {
		return hand.getAction() + draw.getAction() + discard.getAction();
	}
	
	public int getTreasureCount() {
		return hand.getTreasure() + draw.getTreasure() + discard.getTreasure();
	}
	
	public void totalPoints() {																	//Update the total number of Victory Points in the Player's Deck (hand, discard, and draw)
		draw.update();
		hand.update();
		discard.update();
		VP = draw.points + hand.points + discard.points;
	}
	
}

