public class Silver extends Card {
	
	public Silver() { 
		name = "Silver";
		description = "A Silver, worth two Coins.";
		cardType = Type.TREASURE;
		cost = 3;
		value = 2;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 0;
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return 2.5f;
	}
}