public class Smithy extends Card {
	public Smithy() {
		name = "Smithy";
		description = "+3 Cards";
		cardType = Type.ACTION;
		cost = 4;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 3;
		buys = 0;
		playOrder = 6;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		System.out.println("Playing " + name);																		//Draw three cards
		for(int i = 0; i < 3; i++) {
			if(domGame.players[playerID].draw.getSize() == 0) { domGame.players[playerID].discardToDraw(domGame.rand); }
			domGame.players[playerID].hand.addCard(domGame.players[playerID].draw.drawTop());
		}
		
		System.out.println("Discarding: " + domGame.players[playerID].hand.cards.get(cardIndex).name);
		domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(cardIndex));				//Add card to discard pile
		domGame.players[playerID].hand.removeCard(cardIndex);														//Remove card from hand
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return (player.getAverageValue() * 3) - 3 * (player.getActionCount()/player.getCardCount());
	}
}
