public class Adventurer extends Card {
	public Adventurer() {
		name = "Adventurer";
		description = "Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.";
		cardType = Type.ACTION;
		cost = 6;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 0;
		playOrder = 3;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		int revTreasure = 0;																								//The number of the Treasures to reveal
		System.out.println("Playing " + name);
		if(domGame.players[playerID].draw.getSize() == 0) { domGame.players[playerID].discardToDraw(domGame.rand); }		//Shuffle discard into draw if draw is empty
		
		while(revTreasure < 2) {																							//Reveal cards until two treasures are revealed
			if(domGame.players[playerID].draw.getSize() == 0) { domGame.players[playerID].discardToDraw(domGame.rand); }	//Shuffle discard into draw if draw is empty
			if(domGame.players[playerID].draw.cards.get(0).cardType == Card.Type.TREASURE) {
				System.out.println("Adding " + domGame.players[playerID].draw.cards.get(0).name + " to hand.");
				domGame.players[playerID].hand.addCard(domGame.players[playerID].draw.drawTop());							//Add Treasures to hand
				revTreasure++;
			}
			else {
				System.out.println("Revealing " + domGame.players[playerID].draw.cards.get(0).name);						//Reveal and discard other cards
				domGame.players[playerID].discard.addCard(domGame.players[playerID].draw.drawTop());
			}
		}
		
		
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
		return (player.getTotalValue() / player.getTreasureCount()) * 2 - 3 * (player.getActionCount()/player.getCardCount());
	}
}
