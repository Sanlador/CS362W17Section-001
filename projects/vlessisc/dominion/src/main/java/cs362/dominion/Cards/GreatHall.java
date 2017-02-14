package cs362.dominion.Cards;


public class GreatHall extends Card {
	
	public String getDefinition() {
		return "1 Victory Point +1 Card; +1 Action.";
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
	
	public int getCards(){
		return 1;
	}
	
	public int getActions(){
		return 1;
	}
	
	public int getVictoryPoints(){
		return 1;
	}
	
	public int getCost(){
		return 3;
	}
}
