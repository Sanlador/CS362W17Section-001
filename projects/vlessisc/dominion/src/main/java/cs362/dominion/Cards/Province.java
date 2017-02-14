package cs362.dominion.Cards;

public class Province extends Card {
	
	
	public String getDefinition(){
		return "Victory card, worth 6 victory points";
	}
	
	public int getInitialSupply(int numPlayers){
		return 8;
	}
	

	public int getVictoryPoints(){
		return 6;
	}
	
	public int getCost(){
		return 8;
	}
	
	
	public cardTypes getType(){
		return Card.cardTypes.VICTORY;
	}
	
}
