package cs362.dominion.Cards;


public class Smithy extends Card {

	public Smithy(){
		
	}
	
	public String getDefinition() {
		return "+3 Cards";
	}
	
	public cardTypes getType(){
		return Card.cardTypes.KINGDOM;
	}
	
	/**
	 * There are 10 of each kingdom card
	 */
	public int getInitialSupply(int numPlayers){
		return 10;
	}
	
	
	public int getCost(){
		return 4;
	}
	
	public int getCards(){
		return 3;
	}
	

	
	
}
