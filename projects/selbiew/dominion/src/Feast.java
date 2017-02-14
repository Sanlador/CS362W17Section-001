
public class Feast extends Card {
	public Feast() {
		name = "Feast";
		description = "Trash this card. Gain a card costing up to 5 Coins.";
		cardType = Type.ACTION;
		cost = 4;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 0;
		playOrder = 8;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		System.out.println("Playing " + name);
		int randInt;
		boolean basic = false;
		Boolean cardGained = false;		
		System.out.println("Trashing: " + domGame.players[playerID].hand.cards.get(cardIndex).name);
		domGame.players[playerID].hand.removeCard(cardIndex);						//Trash the card
		
		while(!cardGained) {
			randInt = domGame.rand.nextInt(17);
			if(randInt >= 10) { randInt %= 10; basic = true;}
			if(basic && domGame.basicCards.get(randInt).size != 0) { 				//No price constraint on feast - Bug
				domGame.players[playerID].discard.addCard(domGame.basicCards.get(randInt).drawTop()); 
				cardGained = true;
				}
			else if(domGame.kingdomCards.get(randInt).size != 0) { 					//No price constraint on feast - Bug
				domGame.players[playerID].discard.addCard(domGame.kingdomCards.get(randInt).drawTop());
				cardGained = true;
			}
		}
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return 4.0f - 3 * (player.getActionCount()/player.getCardCount());
	}
}
