public class Gardens extends Card {
	public Gardens() {
		name = "Gardens";
		description = "Worth 1 Victory Point per 10 cards you have (round down).";
		cardType = Type.VICTORY;
		cost = 4;
		value = 0;											//Start with 10 Cards
		points = 1;
		actions = 0;
		cardDraw = 0;
		buys = 0;
	}
	
	public void play() {
		System.out.println("Playing " + name);
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return (player.getCardCount() / 4) * 0.5f;
	}
	
	public void updateVP(int size) {						//Dynamically update Gardens value, not called - Bug
		points = size / 4;
	}
}
