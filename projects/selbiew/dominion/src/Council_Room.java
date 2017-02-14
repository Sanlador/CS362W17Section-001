public class Council_Room extends Card {
	public Council_Room() {
		name = "Council Room";
		description = "+4 Cards. +1 Buy. Each other player draws a card.";
		cardType = Type.ACTION;
		cost = 5;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 4;
		buys = 1;
		playOrder = 4;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		System.out.println("Playing " + name);
		int otherPlayerID = 0;
		if(playerID == 0) { otherPlayerID = 1; }
		
		for(int i = 0; i < 4; i++) {																							//Player draws 4 cards
			if(domGame.players[playerID].draw.getSize() == 0) { domGame.players[playerID].discardToDraw(domGame.rand); }		//Shuffle discard in draw if necessary
			domGame.players[playerID].hand.addCard(domGame.players[playerID].draw.drawTop());
		}
		
		if(domGame.players[otherPlayerID].draw.getSize() == 0) { domGame.players[otherPlayerID].discardToDraw(domGame.rand); }	//Opponent draws 1 card, shuffling if needed
		domGame.players[otherPlayerID].hand.addCard(domGame.players[otherPlayerID].draw.drawTop());
		
		domGame.players[playerID].buys += 1;																				//Player gains a buy
		
		System.out.println("Discarding: " + name);																			//Discard the card
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {
			if(domGame.players[playerID].hand.cards.get(i).name == name) {
				domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(i));
				domGame.players[playerID].hand.removeCard(i);
				break;
			}
		}
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return (player.getAverageValue() * 4 + 0.5f - 3 * (player.getActionCount()/player.getCardCount()));
	}
}
