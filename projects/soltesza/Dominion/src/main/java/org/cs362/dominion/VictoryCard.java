package org.cs362.dominion;

public class VictoryCard extends Card implements Cloneable {
	public enum VictoryType {
		CURSE,
		ESTATE,
		DUCHY,
		PROVINCE,
		GARDENS
	}
	
	public final VictoryType type;
	
	public VictoryCard(VictoryType type) {
		this.type = type;
		
		switch(type) {
			case CURSE:
				super.name = "Curse";
				super.cost = 0;
				break;
			case ESTATE:
				super.name = "Estate";
				super.cost = 1;
				break;
			case DUCHY:
				super.name = "Duchy";
				super.cost = 5;
				break;
			case PROVINCE:
				super.name = "Province";
				super.cost = 8;
				break;
			default:
				super.name = "Gardens";
				super.cost = 4;
		}
	}
	
	@Override
	public VictoryCard clone() {
		final VictoryCard clone = (VictoryCard)super.clone();
		return clone;
	}
	
	public int GetVPs() {
		switch(type) {
			case CURSE:
				return -1;
			
			case ESTATE:
				return 1;
			
			case DUCHY:
				return 5;
				
			case PROVINCE:
				return 8;
			default:
				return 1;
		}
	}
}
