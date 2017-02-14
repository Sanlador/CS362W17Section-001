package org.cs362.dominion;

public class TreasureCard extends Card {	
	public enum TreasureType {
		COPPER,
		SILVER,
		GOLD
	}
	
	public final TreasureType type;
	
	public TreasureCard(TreasureType type) {
		this.type = type;
		
		switch(type) {
			case GOLD:
				super.name = "Gold";
				super.cost = 6;
				break;
		
			case SILVER:
				super.name = "Silver";
				super.cost = 3;
				break;
			
			default:
				super.name = "Copper";
				super.cost = 0;
				break;
		}
	}
	
	@Override
	public TreasureCard clone() {
		final TreasureCard clone = (TreasureCard)super.clone();
		return clone;
	}
	
	public int GetValue() {
		switch(type) {
			case GOLD:
				return 3;
			
			case SILVER:
				return 2;
				
			default:
				return 1;
		}
	}
}
