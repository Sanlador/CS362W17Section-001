package cs362.dominion.Cards;



public class Bazaar extends Card {

	public Bazaar(){
		
	}

	public String getDefinition() {
		return "+1 Card; +2 Actions, +$1.";
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
		return 5;
	}
	
	public int getCoins(){
		return 1;
	}
	
	public int getActions(){
		return 2;
	}
	
	public int getCards(){
		return 1;
	}
	
}
