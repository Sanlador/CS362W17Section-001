package org.cs362.dominion;

import static org.junit.Assert.*;
import java.util.*;

import org.junit.Test;
import org.junit.Before;

public class junit {
	  private GameState state;
      private List<Card> cards;
      private Player player1;
      private Player player2;
      @Before
      public void initializeGame() {
          cards = new ArrayList<Card>(Card.createCards());
          state = new GameState(cards);
          player1 = new Player(state, "PLAYER 1");
          state.addPlayer(player1);
          player2 = new Player(state, "PLAYER 2");
          state.addPlayer(player2);
          state.initializeGame();
      }
      @Test
      public void testCopper() {
          Card Copper = Card.getCard(cards, Card.CardName.Copper);
          assertEquals(Copper.getCost(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Copper).score(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Copper).getTreasureValue(), 1);
          assertEquals(Card.getCard(cards, Card.CardName.Copper).getCardName(),Card.CardName.Copper);
          assertEquals(Card.getCard(cards, Card.CardName.Copper).getType(), Card.Type.TREASURE);
      }
      @Test
      public void testSilver() {
          Card Silver = Card.getCard(cards, Card.CardName.Silver);
          assertEquals(Silver.getCost(), 3);
          assertEquals(Card.getCard(cards, Card.CardName.Silver).score(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Silver).getTreasureValue(), 2);
          assertEquals(Card.getCard(cards, Card.CardName.Silver).getCardName(),Card.CardName.Silver);
          assertEquals(Card.getCard(cards, Card.CardName.Silver).getType(), Card.Type.TREASURE);
      }
      @Test
      public void testGold() {
          Card Gold = Card.getCard(cards, Card.CardName.Gold);
          assertEquals(Gold.getCost(), 6);
          assertEquals(Card.getCard(cards, Card.CardName.Gold).score(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Gold).getTreasureValue(), 3);
          assertEquals(Card.getCard(cards, Card.CardName.Gold).getCardName(),Card.CardName.Gold);
          assertEquals(Card.getCard(cards, Card.CardName.Gold).getType(), Card.Type.TREASURE);
      }
      @Test
      public void testProvince() {
          Card Province = Card.getCard(cards, Card.CardName.Province);
          assertEquals(Province.getCost(), 8);
          assertEquals(Card.getCard(cards, Card.CardName.Province).score(), 6);
          assertEquals(Card.getCard(cards, Card.CardName.Province).getTreasureValue(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Province).getCardName(),Card.CardName.Province);
          assertEquals(Card.getCard(cards, Card.CardName.Province).getType(), Card.Type.VICTORY);
      }
      @Test
      public void testDuchy() {
          Card Duchy = Card.getCard(cards, Card.CardName.Duchy);
          assertEquals(Duchy.getCost(), 5);
          assertEquals(Card.getCard(cards, Card.CardName.Duchy).score(), 3);
          assertEquals(Card.getCard(cards, Card.CardName.Duchy).getTreasureValue(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Duchy).getCardName(),Card.CardName.Duchy);
          assertEquals(Card.getCard(cards, Card.CardName.Duchy).getType(), Card.Type.VICTORY);
      }   
      @Test
      public void testEstate() {
          Card Estate = Card.getCard(cards, Card.CardName.Estate);
          assertEquals(Estate.getCost(), 2);
          assertEquals(Card.getCard(cards, Card.CardName.Estate).score(), 1);
          assertEquals(Card.getCard(cards, Card.CardName.Estate).getTreasureValue(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Estate).getCardName(),Card.CardName.Estate);
          assertEquals(Card.getCard(cards, Card.CardName.Estate).getType(), Card.Type.VICTORY);
      }   
      @Test
      public void testCurse() {
          Card Curse = Card.getCard(cards, Card.CardName.Curse);
          assertEquals(Curse.getCost(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Curse).score(), -1);
          assertEquals(Card.getCard(cards, Card.CardName.Curse).getTreasureValue(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Curse).getCardName(),Card.CardName.Curse);
          assertEquals(Card.getCard(cards, Card.CardName.Curse).getType(), Card.Type.VICTORY);
      }   
      @Test
      public void testGardens() {
          Card Gardens = Card.getCard(cards, Card.CardName.Gardens);
          assertEquals(Gardens.getCost(), 4);
          assertEquals(Card.getCard(cards, Card.CardName.Gardens).score(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Gardens).getTreasureValue(), 0);
          assertEquals(Card.getCard(cards, Card.CardName.Gardens).getCardName(),Card.CardName.Gardens);
          assertEquals(Card.getCard(cards, Card.CardName.Gardens).getType(), Card.Type.ACTION);
      }   
}
