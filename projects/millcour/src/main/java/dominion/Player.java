package dominion;

import java.util.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class Player{
		private int numActions;
		private int numBuys;
		private int coins;
		private int victory;	
		private Deck hand;
	       	private Deck discard;
		private Deck draw;	

		public Player(){
			numActions = 1;
			numBuys = 1;
			coins = 0;
			victory = 0;
			hand = new Deck(Card.CardName.Hand);
			discard = new Deck(Card.CardName.Discard);
			draw = new Deck(Card.CardName.Draw);
		}

		public void setNumActions(int numActions){
			this.numActions = numActions;
		}

		public void setNumBuys(int numBuys){
			this.numBuys = numBuys;
		}

		public void setVictory(int victory){
			this.victory = victory;
		}

		public void setCoins(int coins){
			this.coins = coins;
		}

		public int getVictory(){
			return victory;
		}

		public int getNumActions(){
			return numActions;
		}

		public int getNumBuys(){
			return numBuys;
		}

		public int getCoins(){
			return coins;
		}

		public Deck getHand(){
			return hand;
		}

		public Deck getDiscard(){
			return discard;
		}

		public Deck getDraw(){
			return draw;
		}

		public void totalCoins(){
			for(int i=0; i<hand.getPile().size(); i++){
				//System.out.println("IN TOTAL: " + hand.getPile().get(i).getTreasureValue());
				coins += hand.getPile().get(i).getTreasureValue();	
			}
		}

		public void takeTurn(){
			draw.shuffleDeck();
			drawHand();
			printHand();
			while(numActions > 0){
				System.out.println("Taking the Action\n");
				numActions--;
			}while(numBuys > 0){
				System.out.println("Taking the Buy\n");
				totalCoins();
				System.out.println("TV: " + coins);
				numBuys--;
			}
		}

		public void drawHand(){
			Card temp;
			if(draw.getPile().size() < 5){
				discard.shuffleDeck();
				discardToDraw();
			}
			for(int i=0; i<5; i++){
				hand.getPile().add(draw.drawCard());
			}
		}

		public void discardHand(){
			Card temp;
			for(int i=hand.getPile().size()-1; i>=0; i--){
				temp = hand.getPile().get(i);
				hand.getPile().remove(i);
				discard.getPile().add(temp);
			}
		}

		public void discardToDraw(){
			Card temp;
			for(int i=discard.getPile().size()-1; i>=0; i--){
				temp = discard.getPile().get(i);
				discard.getPile().remove(i);
				draw.getPile().add(temp);
			}
		}

		public void printHand(){
			System.out.println("CARDS IN HAND:");
			for(int i=0; i<hand.getPile().size(); i++){
				System.out.println("| " + hand.getPile().get(i).getCardName() + " |");
			}
			System.out.println("----------\n");
		}

		public void resetPlayer(){
			numBuys = 1;
			numActions = 1;
			coins = 0;
			discardHand();
			drawHand();
		}

}

