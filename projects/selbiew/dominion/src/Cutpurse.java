public class Cutpurse extends Card {
	public Cutpurse() {
		name = "Cutpurse";
		description = "+2 Coins. Each other player discards a Copper card (or reveals a hand with no Copper).";
		cardType = Type.ATTACK;
		cost = 4;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 0;
		playOrder = 10;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		int otherPlayerID = 0;
		if(playerID == 0) { otherPlayerID = 1; }																	//Gets the other players ID
		System.out.println("Playing " + name);
		domGame.players[playerID].bonusCoins += 2;																	//Gain 2 coins
		
		for(int i = 0; i < domGame.players[otherPlayerID].hand.size; i++) {
			if(domGame.players[otherPlayerID].hand.cards.get(i).name == "Copper") {									//Goes through other player's hand to discard a copper
				System.out.println("Opponent discards: " + domGame.players[otherPlayerID].hand.cards.get(i).name);	
				domGame.players[otherPlayerID].discard.addCard(domGame.players[otherPlayerID].hand.cards.get(i));
				domGame.players[otherPlayerID].hand.removeCard(i);
				break;
			}
			else {
				System.out.println("Reveals: " + domGame.players[otherPlayerID].hand.cards.get(i).name);			//Reveal non Copper Cards
			}
		}
		
		System.out.println("Discarding: " + name);																	//Discard cutpurse
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {
			if(domGame.players[playerID].hand.cards.get(i).name == name) {
				domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(i));
				domGame.players[playerID].hand.removeCard(i);
				break;
			}
		}
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return 2.0f - 3 * (player.getActionCount()/player.getCardCount());
	}
}
