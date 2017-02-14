
public class Embargo extends Card {
	public Embargo() {
		name = "Embargo";
		description = "+2 Coins. Trash this card. Put an Embargo token on top of a Supply Pile. When a player buys a card, he gains a Curse card per Embargo token on that pile.";
		cardType = Type.ACTION;
		cost = 2;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 0;
		playOrder = 9;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		int supplyPile = domGame.rand.nextInt(17);											//The Supply piles index
		boolean basic = false;
		if(supplyPile >= 10) { supplyPile %= 10; basic = true;}
		System.out.println("Playing " + name);
		domGame.players[playerID].bonusCoins += 2;											//Gain 2 coins
		domGame.players[playerID].hand.removeCard(cardIndex);								//Trash the card
		
		if(basic) {
			System.out.println("Player has embargoed: " + domGame.basicCards.get(supplyPile).cards.get(0).name);
			domGame.basicCards.get(supplyPile).embargoes += 1;								//Embargo that Supply Pile
		}
		else {
			System.out.println("Player has embargoed: " + domGame.kingdomCards.get(supplyPile).cards.get(0).name);
			domGame.kingdomCards.get(supplyPile).embargoes += 1;							//Embargo that supply pile
		}
		
		System.out.println("Discarding: " + name);											//Discard the Card
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {
			if(domGame.players[playerID].hand.cards.get(i).name == name) {
				domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(i));
				domGame.players[playerID].hand.removeCard(i);
				break;
			}
		}
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return 0.5f;
	}
}

