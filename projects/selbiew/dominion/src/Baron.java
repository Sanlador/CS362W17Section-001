public class Baron extends Card {
	public Baron() {
		name = "Baron";
		description = "+1 Buy. You may discard an Estate card. If you do, +4 Coins. Otherwise, gain and Estate Card.";
		cardType = Type.ACTION;
		cost = 4;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 1;
		playOrder = 11;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		boolean discardedEstate = false;															//Boolean that is true when they discard an Estate
		int estateIndex = -1;
		System.out.println("Playing " + name);
		domGame.players[playerID].buys += 1;														//Gain a buy
		
		for(int i = 0; i < domGame.players[playerID].hand.size; i++) {								//Discard an Estate if they have one (why wouldn't you?)
			if(domGame.players[playerID].hand.cards.get(i).name == "Estate") {	
				estateIndex = i;
			}
		}
		
		if(estateIndex != -1) {																		//Discard an Estate if they have one
			domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(estateIndex));
			domGame.players[playerID].hand.removeCard(estateIndex);
			System.out.println("Discarded Estate!");
			discardedEstate = true;
			domGame.players[playerID].bonusCoins += 4;												//Gain four coins
		}
			
		if(!discardedEstate) {
			domGame.players[playerID].discard.addCard(domGame.basicCards.get(4).drawTop());			//Gain an estate if one was not discarded
			System.out.println("Gained an Estate!");
		}
		
		System.out.println("Discarding: " + name);													//Discard Baron
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {
			if(domGame.players[playerID].hand.cards.get(i).name == name) {
				domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(i));
				domGame.players[playerID].hand.removeCard(i);
				break;
			}
		}
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return (3 / player.getCardCount()) * 4 + 0.5f - 3 * (player.getActionCount()/player.getCardCount());
	}
}
