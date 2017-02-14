/*
 * This class will implement a Super Card class for the cards in a game of
 * Dominion. The cards will hold all of the information that every card in
 * the game has. There are functions that will implement the specific
 * features of each card. If a card does not have one of those features,
 * the function should not be changed in the inherited class.
 * 	Example:
 *   - Dutchy has no action, it will not change the action function
 *   - A copper is worth no victory points, it will have 0 victory points
 *   - Etc
 *   
 *   Robert Rosenberger
 *   Last modified: 2/8/2017
 */
package org.cs362.dominion;
import java.util.ArrayList;

public class Card {
	
	private String name;
	private String description;
	private int cost;
	private int victoryPoints;
	private int treasure;
	private ArrayList<CardType> types;
	
	//constructor
	public Card(){
		name = new String();
		types = new ArrayList<CardType>();
	}
	public Card(String name, int cost, int victoryPoints, int treasure, CardType[] types){
		this.description = "";
		this.name = new String(name);
		this.cost = cost;
		this.victoryPoints = victoryPoints;
		this.treasure = treasure;
		this.types = new ArrayList<CardType>();
		for(int j=0; j<types.length; j++)
			this.types.add(types[j]);
	}
	public Card(Card other){
		this.description = "";
		name = new String(other.name);
		cost = other.cost;
		victoryPoints = other.victoryPoints;
		treasure = other.treasure;
		this.types = new ArrayList<CardType>();
		for(int j=0; j<other.types.size(); j++)
			this.types.add(other.types.get(j));
	}
	
	//getters
	public String getName(){
		return name;
	}
	public int getCost(){
		return cost;
	}
	public int getVictoryPoints(){
		return victoryPoints;
	}
	public int getTreasure(){
		return treasure;
	}
	
	//checks to see if the card if of the passed in type
	// - returns true if it is, false otherwise
	public boolean isCardType(CardType type){
		for(int j=0; j<types.size(); j++)
			if(type == types.get(j))
				return true;
		return false;
	}
	
	//performs the cards action
	public void Action(ArrayList<Player> players,
			Player currentPlayer, Board board){};
	
	//performs the cards attack
	public void Attack(ArrayList<Player> players,
			Player currentPlayer, Board board){};
	
	//performs the cards reaction
	public void Reaction(){};
	
	//prints the card
	public void print(){
		System.out.println(getName()+"\n"+this.description);
	}
}































