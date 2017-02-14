package org.cs362.dominion;

import java.util.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;

public class Card{
	public static enum Type{
		ACTION, TREASURE, VICTORY;
	}
	public static enum CardName{
		Gold, Silver, Cooper, Province, Duchy, Estate, Curse, Adventurer, Bureaucrat, Cellar, Chancellor, Chapel, Council_Room, Feast, Garden, Laboratory, Library, Market, Smithy, Village;
	}
	private Type CardType;
	private CardName cardName;
	private int cost, score, treasureValue;
	
	private Card(CardName cardName, Type type, int cost, int score, int treasureValue){
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.CardType = type;
		this.cardName = cardName;
	}
	public Type getType(){
		return CardType;
	}
	public CardName getCardName(){
		return cardName;
	}
	public int getCost(){
		return cost;
	}
	public int getTreasureValue(){
		return treasureValue;
	}
	public int getScore(){
		return score;
	}
	public static List<Card> addAllCards(){
		List<Card> temp = new LinkedList<Card>();
		Card o = new Card(CardName.Province, Type.VICTORY, 8, 6, 0);
		temp.add(o);
		o = new Card(CardName.Duchy, Type.VICTORY, 5, 3, 0);
		temp.add(o);
		o = new Card(CardName.Estate, Type.VICTORY, 2, 1, 0);
		temp.add(o);
		o = new Card(CardName.Curse, Type.VICTORY, 0, -1, 0);
		temp.add(o);
		o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		temp.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		temp.add(o);
		o = new Card(CardName.Cooper, Type.TREASURE, 0, 0, 1);
		temp.add(o);
		o = new Card(CardName.Adventurer, Type.ACTION, 6, 0, 0);
		temp.add(o);
		o = new Card(CardName.Bureaucrat, Type.ACTION, 4, 0, 0);
		temp.add(o);
		o = new Card(CardName.Cellar, Type.ACTION, 2, 0, 0);
		temp.add(o);
		o = new Card(CardName.Chancellor, Type.ACTION, 3, 0, 0);
		temp.add(o);
		o = new Card(CardName.Chapel, Type.ACTION, 2, 0, 0);
		temp.add(o);
		o = new Card(CardName.Council_Room, Type.ACTION, 5, 0, 0);
		temp.add(o);
		o = new Card(CardName.Feast, Type.ACTION, 4, 0, 0);
		temp.add(o);
		o = new Card(CardName.Garden, Type.VICTORY, 4, 0, 0);
		temp.add(o);
		o = new Card(CardName.Laboratory, Type.ACTION, 5, 0, 0);
		temp.add(o);
		o = new Card(CardName.Library, Type.ACTION, 5, 0, 0);
		temp.add(o);
		o = new Card(CardName.Market, Type.ACTION, 5, 0, 0);
		temp.add(o);
		o = new Card(CardName.Smithy, Type.ACTION, 4, 0, 0);
		temp.add(o);
		o = new Card(CardName.Village, Type.ACTION, 3, 0, 0);
		temp.add(o);
		return temp;
	}
	
	public static List<Card> createCards(){
		List<Card> ret = new LinkedList<Card>();
		Card o;
		Random rand = new Random();
		int randomInt, victoryCounter=0, mainCounter = 0, treasureCounter = 0;
		
		while(true){
			randomInt = rand.nextInt(10);
			if(randomInt % 2 == 0 && victoryCounter != 3){
				o = new Card(CardName.Estate, Type.VICTORY, 2, 1 , 0);
				ret.add(o);
				victoryCounter++;
				mainCounter++;
			}
			else if(treasureCounter != 7){
				o = new Card(CardName.Cooper, Type.TREASURE, 0, 0, 1);
				ret.add(o);
				treasureCounter++;
				mainCounter++;
			}
			if(mainCounter == 10)
				break;
		}
		return ret;
	}
	public String toString(int i){
		StringBuilder sb = new StringBuilder();
		
		sb.append(" \n"+ i+".\t" + this.getCardName());
		sb.append("-" + this.getType() + " ");
		sb.append("\tCost: " + this.cost + " ");
		sb.append("\tScore: " + this.score + " ");
		sb.append("\tTreasure Value: " + this.treasureValue);
		
		return sb.toString();
	}
	public String toStringShort(int i){
		StringBuilder sb = new StringBuilder();
		sb.append("\n"+i+". "+this.getCardName()+"| Cost: "+this.getCost()+"| Treasure Val: "+this.getScore()+"| Type: "+this.getType());
		return sb.toString();
	}
	public int checkTypeAction(){
		if(this.CardType == Type.ACTION)
			return 1;
		else
			return 0;
	}
	public int checkTypeVictory(){
		if(this.CardType == Type.VICTORY)
			return 1;
		else
			return 0;
	}
	public int calcTreasure(){
		if(this.CardType == Type.TREASURE)
			return this.getTreasureValue();
		else
			return 0;
	}
	public int calcVictory(){
		if(this.CardType == Type.VICTORY)
			return this.getScore();
		else
			return 0;
	}
	public static List<Card> filter(Iterable<Card> cards, Type target) {
		List<Card> out = new ArrayList<Card>();
		for (Card c : cards)
			if (c.getType() == target)
				out.add(c);
		return out;
	}
	public static List<Card> cmp(List<Card> all, int num){
		List<Card> out = new ArrayList<Card>();
		for(Card c: all)
			if(c.getCost() <= num)
				out.add(c);
		return out;
	}
	public static Card getCard(List<Card> cards, CardName n){
		for(Card c:cards){
			if(c.getCardName() ==  n)
				return c;
		}
		return null;
	}
}
