
public class Remodel extends Card {
	public Remodel() {
		name = "Remodel";
		description = "Trash a card from your hand. Gain a card costing up to 2 Coins more than it.";
		cardType = Type.ACTION;
		cost = 4;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 0;
		playOrder = 7;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		System.out.println("Playing " + name);
		
		int randInt = cardIndex;
		boolean basic;
		int buyingPower = 0;
		Boolean cardGained = false;
		
		//(while(randInt == cardIndex) {																						//Can trash itself - Bug
		randInt = domGame.rand.nextInt(domGame.players[playerID].hand.getSize());
		//}
		
		buyingPower = domGame.players[playerID].hand.cards.get(randInt).cost + 2; 												//Adds 2 + trashed card's value to it's buying power
		System.out.println("Trashing " + domGame.players[playerID].hand.cards.get(randInt).name + " for " + buyingPower + " coins");
		domGame.players[playerID].hand.removeCard(randInt); 																	//Trash that card
			
		while(!cardGained) {
			basic = false;
			randInt = domGame.rand.nextInt(17);																					//Buy one of the 17 available cards
			if(randInt >= 10) { randInt %= 10; basic = true;}																	//0-9 is Kingdom, 10-16 -> 0-6 and Basic
			if(basic && domGame.basicCards.get(randInt).getSize() != 0 && domGame.basicCards.get(randInt).cards.get(0).cost <= buyingPower) {
				System.out.println("Gaining " + domGame.basicCards.get(randInt).cards.get(0).name);
				domGame.players[playerID].discard.addCard(domGame.basicCards.get(randInt).drawTop());
				cardGained = true; 
				}
			else if(domGame.kingdomCards.get(randInt).getSize() != 0 && domGame.kingdomCards.get(randInt).cards.get(0).cost <= buyingPower) { 
				System.out.println("Gaining " + domGame.kingdomCards.get(randInt).cards.get(0).name);
				domGame.players[playerID].discard.addCard(domGame.kingdomCards.get(randInt).drawTop());
				cardGained = true;
				}
		}
		
		System.out.println("Discarding: " + name);																				//Discard Remodel
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
