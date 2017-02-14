package org.cs362.dominion;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class TestCards {
	@Test
	public void testActionCards() {
		int count;
		
		DominionBoard board = new DominionBoard();
		GameState state = new GameState(board);
		
		Card testCard = new ActionCard(ActionCard.ActionType.ADVENTURER);
		assertEquals("Card name incorrect", "Adventurer", testCard.GetName());
		assertEquals("Cost incorrect", 6, testCard.GetValue());
		count = state.currentPlayer.deck.GetHand().size();
		((ActionCard)testCard).PlayAction(state);
		assertEquals("not enough cards drawn", count+2, state.currentPlayer.deck.GetHand().size());
		
		testCard = new ActionCard(ActionCard.ActionType.CUTPURSE);
		state.otherPlayer.deck.DrawCards(10);
		count = state.otherPlayer.deck.GetHand().size();
		((ActionCard)testCard).PlayAction(state);
		assertEquals("Copper was not discarded", count-1, state.otherPlayer.deck.GetHand().size());
		
		testCard = new ActionCard(ActionCard.ActionType.EMBARGO);
		state.currentPlayer.deck.AddCard(testCard);
		count = state.currentPlayer.deck.GetCardCount();	
		((ActionCard)testCard).PlayAction(state);
		assertEquals("Player did not trash card after playing", count-1, state.currentPlayer.deck.GetCardCount());
		
		testCard = new ActionCard(ActionCard.ActionType.FEAST);
		state.currentPlayer.deck.AddCard(testCard);
		count = state.currentPlayer.deck.GetCardCount();	
		((ActionCard)testCard).PlayAction(state);
		assertEquals("Player gained or lost a card while playing a Feast", count, state.currentPlayer.deck.GetCardCount());
		
		testCard = new ActionCard(ActionCard.ActionType.MINE);
		count = state.currentPlayer.deck.GetCardCount();
		((ActionCard)testCard).PlayAction(state);
		assertEquals("Player gained or lost a card while playing a Mine", count, state.currentPlayer.deck.GetCardCount());
		
		testCard = new ActionCard(ActionCard.ActionType.REMODEL);
		count = state.currentPlayer.deck.GetCardCount();
		((ActionCard)testCard).PlayAction(state);
		assertEquals("Player gained or lost a card while playing a Remodel", count, state.currentPlayer.deck.GetCardCount());
	}
}
