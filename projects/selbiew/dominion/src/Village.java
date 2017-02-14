public class Village extends Card {
	public Village() {
		name = "Village";
		description = "+1 Cards. +2 Actions.";
		cardType = Type.ACTION;
		cost = 3;
		value = 0;
		points = 0;
		actions = 2;
		cardDraw = 1;
		buys = 0;
		playOrder = 1;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		System.out.println("Playing " + name);
		if(domGame.players[playerID].draw.getSize() == 0) { domGame.players[playerID].discardToDraw(domGame.rand); }	//Shuffle discard into draw if draw is empty
		domGame.players[playerID].hand.addCard(domGame.players[playerID].draw.drawTop());								//Draw a card
		
		domGame.players[playerID].actions += 2;																			//Add two actions
		System.out.println("Discarding: " + name);
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {
			if(domGame.players[playerID].hand.cards.get(i).name == name) {
				domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(i));
				domGame.players[playerID].hand.removeCard(i);
				break;
			}
		}
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return (player.getAverageValue() + 0.5f);
	}
	
}
