package org.cs362.dominion;

import java.util.Vector;

public class ActionCard extends Card {
	public enum ActionType {
		ADVENTURER,
		AMBASSADOR,
		BARON,
		COUNCIL_ROOM,
		CUTPURSE,
		EMBARGO,
		FEAST,
		GREAT_HALL,
		MINE,
		REMODEL,
		SMITHY,
		VILLAGE
	}
	
	private ActionType type;
	
	public ActionCard(ActionType type) {
		this.type = type;
		
		switch(type) {
			case ADVENTURER:
				super.name = "Adventurer";
				super.cost = 6;
				break;
			case AMBASSADOR:
				super.name = "Ambassador";
				super.cost = 3;
				break;
			case BARON:
				super.name = "Baron";
				super.cost = 4;
				break;
			case COUNCIL_ROOM:
				super.name = "Council Room";
				super.cost = 5;
				break;
			case CUTPURSE	:
				super.name = "Cutpurse";
				super.cost = 4;
				break;
			case EMBARGO:
				super.name = "Embargo";
				super.cost = 2;
				break;
			case FEAST:
				super.name = "Feast";
				super.cost = 4;
				break;
			case GREAT_HALL:
				super.name = "Great Hall";
				super.cost = 3;
				break;
			case MINE:
				super.name = "Mine";
				super.cost = 5;
				break;
			case SMITHY:
				super.name = "Smithy";
				super.cost = 4;
				break;
			case REMODEL:
				super.name = "Remodel";
				super.cost = 4;
				break;
			default:
				super.name = "Village";
				super.cost = 3;
				break;
		}
	}
	
	@Override
	public ActionCard clone() {
		final ActionCard clone = (ActionCard)super.clone();
		return clone;
	}
	
	public void PlayAction(GameState state) {
		switch(type) {
			case ADVENTURER:
				int cardCount = state.currentPlayer.deck.GetCardCount();
				int numTreasures = 0;
				for(int i=0; i<cardCount; i++) {
					Vector<Card> newCard = state.currentPlayer.deck.DrawCards(1);
					if(!(newCard.firstElement() instanceof TreasureCard)) {
						state.currentPlayer.deck.DiscardFromHand(newCard.firstElement());
					}
					else {
						numTreasures++;
					}
					if(numTreasures <= 2) { //Error 1: should be numTreasures >= 2
						break;
					}
				}
				
				break;
			case AMBASSADOR:
			
				break;
			case BARON:
				state.currentPlayer.AddBuys(1);
				
				
				break;
			case CUTPURSE:
				state.currentPlayer.AddCoins(2);
				Vector<Card> hand = state.otherPlayer.deck.GetHand();
				for(int i=0; i<hand.size(); i++) {
					Card card = hand.get(i);
					if(card.GetName() == "Copper") {
						state.otherPlayer.deck.DiscardFromHand(card);
						break;
					}
				}
				break;
			case EMBARGO:                            
				state.currentPlayer.AddCoins(2);
				state.currentPlayer.EmbargoPile();
				state.currentPlayer.deck.TrashCard(this);
				break;
			case COUNCIL_ROOM:
				state.DrawCards(state.currentPlayer, 4);
				state.currentPlayer.AddBuys(1);
				state.DrawCards(state.otherPlayer, 1);
				break;
			case FEAST:
				state.currentPlayer.Buy(1, 5);
				state.currentPlayer.deck.TrashCard(this);
				break;
			case GREAT_HALL:
				state.DrawCards(state.currentPlayer, 1);
				state.currentPlayer.AddActions(1);
				break;
			case MINE:
				Card card = state.currentPlayer.TrashFromHand(); 
				if(card != null) {
					state.currentPlayer.Buy(1, card.GetValue()+3);
				}
				break;
			case SMITHY:
				state.DrawCards(state.currentPlayer, 3);
				break;
			case REMODEL:
				Card card1 = state.currentPlayer.TrashFromHand(); 
				if(card1 != null) {
					state.currentPlayer.Buy(1, card1.GetValue()+2);
				}
				break;
			default:
				state.DrawCards(state.currentPlayer, 1);
				state.currentPlayer.AddActions(2);
				break;
		}
	}
}
