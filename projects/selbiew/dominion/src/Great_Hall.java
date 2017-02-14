public class Great_Hall extends Card {
	public Great_Hall() {
		name = "Great_Hall";
		description = "+1 Card. +1 Action. 1 Victory Point.";
		cardType = Type.ACTION;
		cost = 3;
		value = 0;
		points = 1;
		actions = 1;
		cardDraw = 1;
		buys = 0;
		playOrder = 2;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		System.out.println("Playing " + name);
		if(domGame.players[playerID].draw.getSize() == 0) { domGame.players[playerID].discardToDraw(domGame.rand); }	//Shuffle discard into draw if draw is empty
		domGame.players[playerID].hand.addCard(domGame.players[playerID].draw.drawTop());								//Draw a card
		
		domGame.players[playerID].actions += 1;																			//Add an action
		System.out.println("Discarding: " + name);
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {												//Discard Cutpurse
			if(domGame.players[playerID].hand.cards.get(i).name == name) {
				domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(i));
				domGame.players[playerID].hand.removeCard(i);
				break;
			}
		}
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return 3.0f  - 3 * (player.getActionCount()/player.getCardCount());
	}
}
