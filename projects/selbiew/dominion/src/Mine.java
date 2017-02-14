public class Mine extends Card {
	public Mine() {
		name = "Mine";
		description = "You may trash a Treasure from your hand. Gain a Treasure to your hand costing up to 3 Coins more than it.";
		cardType = Type.ACTION;
		cost = 5;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 0;
		playOrder = 5;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		System.out.println("Playing " + name);
		
		for(int i = 0; i < domGame.players[playerID].hand.size; i++) {							//Turn Copper into Silver
			if(domGame.players[playerID].hand.cards.get(i).name == "Copper") {
				domGame.players[playerID].hand.addCard(domGame.basicCards.get(1).drawTop());
				domGame.players[playerID].hand.removeCard(i);
				System.out.println("Turned Copper into Silver");
				break;
			}
			else if(domGame.players[playerID].hand.cards.get(i).name == "Silver") {				//Turn Silver into Gold
				domGame.players[playerID].hand.addCard(domGame.basicCards.get(2).drawTop());
				domGame.players[playerID].hand.removeCard(i);
				System.out.println("Turned Silver into Gold");
				break;
			}
		}
		
		System.out.println("Discarding: " + name);												//Discard Mine
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {
			if(domGame.players[playerID].hand.cards.get(i).name == name) {
				domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(i));
				domGame.players[playerID].hand.removeCard(i);
				break;
			}
		}
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return 4.0f - 3 * (player.getActionCount()/player.getCardCount());
	}
}
