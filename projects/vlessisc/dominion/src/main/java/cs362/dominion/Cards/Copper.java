package cs362.dominion.Cards;

public class Copper extends Card {

	public Copper(){
		
	}
	
	public String getDefinition() {
		return "A copper resource, worth $1";
	}
	
	public int getInitialSupply(int numPlayers){
		return 60;
	}
	
	public cardTypes getType(){
		return Card.cardTypes.TREASURE;
	}
	
	
	public int getCoins(){
		return 1;
	}
	
	public int getCost(){
		return 0;
	}
	
	
}
