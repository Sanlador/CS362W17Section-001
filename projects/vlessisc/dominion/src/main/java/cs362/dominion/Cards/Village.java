package cs362.dominion.Cards;

public class Village extends Card {

	public Village(){
		
	}
	
	public String getDefinition() {
		return "+1 Card; +2 Actions.";
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
		return 3;
	}
	
	public int getCards(){
		return 1;
	}
	
	public int getActions(){
		return 2;
	}
	
	
}
