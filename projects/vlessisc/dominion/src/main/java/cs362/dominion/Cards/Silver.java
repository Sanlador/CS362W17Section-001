package cs362.dominion.Cards;

public class Silver extends Card {

	
	public Silver(){
		
	}
	
	public String getDefinition() {
		return "A silver resource, worth $2";
	}
	
	public int getInitialSupply(int numPlayers){
		return 40;
	}
	
	public cardTypes getType(){
		return Card.cardTypes.TREASURE;
	}
	
	public int getCost(){
		return 3;
	}
	
	
	public int getCoins(){
		return 2;
	}
	
}
