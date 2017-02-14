package cs362.dominion.Cards;


public class Gold extends Card {

	public Gold(){
		
	}
	
	public String getDefinition() {
		return "A gold resource, worth $3";
	}
	
	public int getInitialSupply(int NumPlayers){
		return 40;
	}
	
	
	
	public int getCoins(){
		return 3;
	}
	
	public int getCost(){
		return 6;
	}
	
	
	public cardTypes getType(){
		return cardTypes.TREASURE;
	}
	
	
}
