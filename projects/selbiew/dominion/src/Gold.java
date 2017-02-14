public class Gold extends Card {
	
	public Gold() { 
		name = "Gold";
		description = "A Gold, worth three Coins.";
		cardType = Type.TREASURE;
		cost = 6;
		value = 3;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 0;
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return 5.0f;
	}
}