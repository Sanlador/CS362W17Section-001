//package org.cs362.dominion;

import java.util.*;
import java.util.Random;

public class Player {
	long seed = System.nanoTime();
	List<Card> hand = new ArrayList<Card>();
	LinkedList<Card> deck = new LinkedList<Card>();
	List<Card> discard = new ArrayList<Card>();
	List<Card> playedCards = new ArrayList<Card>();
	
	String player_name;
	int Actions;
	int Buys;
	int Coins;
	
	public Player(String name, int a, int b, int c){
		this.player_name = name;
		this.Actions = a;
		this.Buys = b;
		this.Coins = c;
	}
	public int getActions(){
		return this.Actions;
	}
	public int getBuys(){
		return this.Buys;
	}
	public int getCoins(){
		return this.Coins;
	}
	public void setActions(int num){
		this.Actions = this.Actions + num;
	}
	public void setBuys(int num){
		this.Buys = this.Buys + num;
	}
	public void setCoins(int num){
		this.Coins = this.Coins + num;
	}
	public void buildDeck(){
		this.deck = new LinkedList<Card>(Card.createCards());
	}
	// Testing
	/*
	public void printDeck(){
		System.out.print("\nGoing to print the Deck now");
		for (Card card:this.deck)
			System.out.print(card.toString());
	}*/
	// Testing
	public void printHand(){
		int i = 1;
		System.out.print("\nHand: ");
		for (Card card:this.hand){
			System.out.print(card.toString(i));
			i++;
		}
	}
	public void drawCard(){
		if(this.deck.isEmpty() && this.discard.isEmpty()){
			System.out.print("\nYou have no more cards to draw from your deck!");
			return;
		}
		else if(this.deck.isEmpty()){
			Collections.shuffle(this.discard, new Random(this.seed));
			for (Card card:this.discard){
				this.deck.add(card);
			}
			this.discard.clear();
		}
		Card drawn = this.deck.removeFirst();
		this.hand.add(drawn);
	}
	public void printInfo(){
		System.out.print("\n Actions: " + this.Actions);
		System.out.print("\n Buys: " + this.Buys);
		//System.out.print("\n Coins: " + this.Coins);
	}
	public int checkTypeAction(int num){
		int answer;
		Card temp = this.hand.get(num-1);
		answer = temp.checkTypeAction();
		if(answer == 1){
			System.out.print("\nYou played " + temp.getCardName());
		}
		return answer;
	}
	public int checkTreasure(){
		int total = 0;
		for (Card card:this.hand){
			total = total + card.calcTreasure();
		}
		return total;
	}
	public void removeHand(){
		for(Card card:this.hand){
			this.discard.add(card);
		}
		this.hand.clear();
	}
	public void resetStats(){
		this.Actions = 1;
		this.Buys = 1;
		this.Coins = 0;
	}
	public void activateCard(Card c, DominionBoard board, Player player2){
		Random rand = new Random();
		Card temp = c;
		Card act;
		int counter=0, tempNum, i = 1, tempNum2;
		this.hand.remove(temp);
		if(temp.getCardName() == Card.CardName.Laboratory){
			if(this.deck.isEmpty() && this.discard.isEmpty()){
				System.out.print("\nYou have no more cards to draw from your deck!");
				this.setActions(1);
				return;
			}
			for(int j = 0; j < 2; j++)
				this.drawCard();
			this.setActions(1);
			this.discard.add(temp);
		}
		else if(temp.getCardName() == Card.CardName.Adventurer){
			this.discard.add(temp);
			//Check if deck & discard is empty
			while(true){
				if(this.deck.isEmpty()){
					Collections.shuffle(this.discard, new Random(this.seed));
					for (Card card:this.discard){
						this.deck.add(card);
					}
					this.discard.clear();
				}
				act = this.deck.getFirst();
				System.out.print("\nYou have drawn the " + act.getCardName() + " card");
				if(act.getType() == Card.Type.TREASURE){
					System.out.print("\nAdding "+ act.getCardName()+" to hand");
					act = this.deck.removeFirst();
					this.hand.add(act);
					counter++;
				}
				else{
					System.out.print("\nDiscarded "+ act.getCardName());
					this.discard.add(act);
					this.deck.removeFirst();
				}
				if(counter == 2)
					break;
			}
		}
		else if(temp.getCardName() == Card.CardName.Bureaucrat){
			tempNum = board.buyingCard(6);
			if(tempNum == 0)
				System.out.print("\nNo more silver to add to deck");
			else{
				System.out.print("\nAdding silver to top of deck");
				act = board.buyCards.get(5);
				this.deck.addFirst(act);
			}
			for(Card card:player2.hand){
				if(card.checkTypeVictory() == 1){
					System.out.print("\nRemoved "+ card.getCardName()+" from other player's hand");
					player2.discard.add(card);
					player2.hand.remove(counter);
					return;
				}
				else
					counter++;
			}
			System.out.print("\nNo victory cards in other's hand, showing hand");
			for(Card card:player2.hand){
				System.out.print(card.toString(i));
				i++;
			}
		}
		else if(temp.getCardName() == Card.CardName.Cellar){
			tempNum = rand.nextInt(this.hand.size());
			System.out.println("\nDiscarding " + tempNum + " cards");
			this.printHand();
			tempNum++;
			for(int j = 0; j < tempNum; j++){
				tempNum2 = rand.nextInt(this.hand.size());
				act = this.hand.get(tempNum2);
				System.out.println("\nDiscarding: "+ act.getCardName());
				this.discard.add(act);
				this.hand.remove(tempNum2);
				this.printHand();
			}
			System.out.print("\nDrawing "+ tempNum + " new cards");
			for(int j = 0; j < tempNum; j++){
				this.drawCard();
			}
			this.discard.add(temp);
			this.setActions(1);
		}
		else if(temp.getCardName() == Card.CardName.Chancellor){
			this.setCoins(2);
			for(Card card:this.deck){
				this.discard.add(card);
			}
			this.deck.clear();
			this.discard.add(temp);
			System.out.print("\nAdded 2 Coins and discarded entire deck to discard pile");
		}
		else if(temp.getCardName() == Card.CardName.Chapel){
			tempNum2 = rand.nextInt(5);
			for(int j = 0; j < tempNum2; j++){
				this.printHand();
				tempNum = rand.nextInt(this.hand.size());
				if(tempNum == 0){
					this.discard.add(temp);
					return;
				}
				act = this.hand.remove(tempNum);
				System.out.println("Trashing "+ act.getCardName());
				}
			this.discard.add(temp);
		}
		else if(temp.getCardName() == Card.CardName.Council_Room){
			System.out.print("\n+1 Buy, +4 Cards, other players draw");
			this.setBuys(1);
			for(int j = 0; j < 4; j++){
				this.drawCard();
			}
			act = player2.deck.remove(0);
			player2.hand.add(act);
			this.discard.add(temp);
		}
		else if(temp.getCardName() == Card.CardName.Feast){
			this.setCoins(5);
			while(true) {
				tempNum = rand.nextInt(19);
				if (board.buyingCard(tempNum + 1) == 0)
					continue;
				if (board.buyCards.get(tempNum).getCost() > this.getCoins())
					continue;
				temp = board.buyCards.get(tempNum);
				System.out.print("\nYou just bought " + temp.getCardName() + "!");
				this.discard.add(temp);
				this.setCoins((-1) * temp.getCost());
				break;
			}
			this.setCoins((-1)*this.getCoins());
		}
		else if(temp.getCardName() == Card.CardName.Library){
			tempNum = this.hand.size();
			while(true){
				this.drawCard();
				act = this.hand.get(tempNum);
				if(act.getType() == Card.Type.ACTION){
					this.discard.add(act);
					this.hand.remove(tempNum);
				}
				else
					tempNum++;
				if(tempNum == 7)
					break;
			}
			this.discard.add(temp);
		}
		else if(temp.getCardName() == Card.CardName.Smithy){
			System.out.println("\nDrawing 3 cards!");
			for(int j = 0; j < 3; j++)
				this.drawCard();
			this.discard.add(temp);
		}
		else if(temp.getCardName() == Card.CardName.Market){
			System.out.println("\n+1 Card, +1 Action, +1 Buy, +1 Coin");
			this.drawCard();
			this.setActions(1);
			this.setBuys(1);
			this.setCoins(1);
			this.discard.add(temp);
		}
		else if(temp.getCardName() == Card.CardName.Village){
			System.out.println("\n+2 Actions, +1 Card");
			this.setActions(2);
			this.drawCard();
		}
	}
	public int checkVictory(){
		int total = 0;
		for(Card card:this.deck){
			total = total + card.calcVictory();
		}
		return total;
	}
	public void cleanUp(){
		for (Card card:this.hand){
			this.deck.add(card);
		}
		for (Card card:this.discard){
			this.deck.add(card);
		}
		this.discard.clear();
		this.hand.clear();
	}
	
}
