package cs362.dominion.Cards;


public class Gardens extends Card {

	
	public cardTypes getType(){
		return Card.cardTypes.KINGDOM;
	}
	
	public String getDefinition() {
		return "Worth 1 Victory for every 10 cards in your deck (rounded down).";
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
}
