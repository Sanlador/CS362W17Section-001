import java.util.ArrayList;
import java.util.List;

public class Ambassador extends Card {
	public Ambassador() {
		name = "Ambassador";
		description = "Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.";
		cardType = Type.ATTACK;
		cost = 3;
		value = 0;
		points = 0;
		actions = 0;
		cardDraw = 0;
		buys = 0;
		playOrder = 12;
	}
	
	public void play(DomGame domGame, int playerID, int cardIndex) {
		int revCard = cardIndex;
		boolean oppGained = false;
		//while(cardIndex == revCard) { revCard = domGame.rand.nextInt(domGame.players[playerID].hand.getSize()); }		//Ambassador can return itself - Bug
		revCard = domGame.rand.nextInt(domGame.players[playerID].hand.getSize());
		String revCardName = domGame.players[playerID].hand.cards.get(revCard).name;						//Remember it's name
		int revCardCount = 0;
		int otherPlayerID = 0;
		List<Integer> cardLoc = new ArrayList<Integer>();
		if(playerID == 0) { otherPlayerID = 1; }
		
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {									//Check how many cards like it in hand
			if(domGame.players[playerID].hand.cards.get(i).name == domGame.players[playerID].hand.cards.get(revCard).name) { revCardCount++; }
		}
		
		int retCount = domGame.rand.nextInt(Math.min(3, revCardCount+1));												//Return at most 2
		System.out.println("Choosing to return " + retCount + " " + revCardName + "s");
		int retCards = 0;																					//Number of Cards that have been trashed
		for(int i = 0; i < 7; i++) {																		//Add the amount you wish to return to the appropriate pile
			if(revCardName == domGame.basicCards.get(i).cards.get(0).name) {
				for(int j = 0; j < retCount; j++) {
					domGame.basicCards.get(i).addCard(domGame.basicCards.get(i).cards.get(0));
					if(!oppGained) {domGame.players[otherPlayerID].discard.addCard(domGame.basicCards.get(i).drawTop()); oppGained = true; }	//Give the opponent the card
				}
			}
		}
		
		for(int i = 0; i < 10; i++) {																		//Add the amount you wish to return to the appropriate pile
			if(revCardName == domGame.kingdomCards.get(i).cards.get(0).name) {
				for(int j = 0; j < retCount; j++) {
					domGame.kingdomCards.get(i).addCard(domGame.kingdomCards.get(i).cards.get(0));
					if(!oppGained) {domGame.players[otherPlayerID].discard.addCard(domGame.kingdomCards.get(i).drawTop()); oppGained = true; }	//Give the opponent the card
				}
			}
		}
		
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {									//Get the indexes of the first instances of the card		
			if(domGame.players[playerID].hand.cards.get(i).name == domGame.players[playerID].hand.cards.get(revCard).name) { 
				if(retCards < retCount) {
					//System.out.println("Removing " + domGame.players[playerID].hand.cards.get(i).name + " at " + i + " from hand.");
					cardLoc.add(i);
					retCards++;
				}
			}
		}
		
		for(int i = cardLoc.size()-1; i >= 0; i--) {														//Remove the cards
			System.out.println("Removing " + domGame.players[playerID].hand.cards.get(cardLoc.get(i)).name + " at " + cardLoc.get(i) + " from hand.");
			domGame.players[playerID].hand.removeCard(cardLoc.get(i));
		}
	
		
		System.out.println("Discarding: " + name);															//Discard Ambassador
		for(int i = 0; i < domGame.players[playerID].hand.getSize(); i++) {
			if(domGame.players[playerID].hand.cards.get(i).name == name) {
				domGame.players[playerID].discard.addCard(domGame.players[playerID].hand.cards.get(i));
				domGame.players[playerID].hand.removeCard(i);
				break;
			}
		}
	}
	
	public float dynamicValue(DomGame domGame, Player player) {
		return  0.5f;
	}
}
