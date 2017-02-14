package org.cs362.dominion;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class CardTest{
    private Player player1;
    private Player player2;
    private List<Card> cards;
    private DominionBoard board;

    @Before
    public void initializeTests(){
        board = new DominionBoard();
        board.initialize();
        cards = new ArrayList<Card>(Card.addAllCards());
        player1 = new Player("PLAYER 1", 1, 1, 0);
        player2 = new Player("PLAYER 2", 1, 1, 0);
    }

    @Test
    public void TestProvince(){
        assertEquals(Card.getCard(cards, Card.CardName.Province).getCost(), 8);
        assertEquals(Card.getCard(cards, Card.CardName.Province).getScore(), 6);
        assertEquals(Card.getCard(cards, Card.CardName.Province).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Province).getTreasureValue(), 0);
    }
    @Test
    public void TestDuchy(){
        assertEquals(Card.getCard(cards, Card.CardName.Duchy).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Duchy).getScore(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Duchy).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Duchy).getTreasureValue(), 0);
    }
    @Test
    public void TestEstate(){
        assertEquals(Card.getCard(cards, Card.CardName.Estate).getCost(), 2);
        assertEquals(Card.getCard(cards, Card.CardName.Estate).getScore(), 1);
        assertEquals(Card.getCard(cards, Card.CardName.Estate).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Estate).getTreasureValue(), 0);
    }
    @Test
    public void TestGold(){
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getCost(), 6);
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getScore(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getType(), Card.Type.TREASURE);
        assertEquals(Card.getCard(cards, Card.CardName.Gold).getTreasureValue(), 3);
    }
    @Test
    public void PlayGold(){
        assertEquals(player1.getCoins(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Gold));
        player1.setCoins(player1.checkTreasure());
        assertEquals(player1.getCoins(), 3);
    }
    @Test
    public void TestSilver(){
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getScore(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getType(), Card.Type.TREASURE);
        assertEquals(Card.getCard(cards, Card.CardName.Silver).getTreasureValue(), 2);
    }
    @Test
    public void PlaySilver(){
        assertEquals(player1.getCoins(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Silver));
        player1.setCoins(player1.checkTreasure());
        assertEquals(player1.getCoins(), 2);
    }
    @Test
    public void TestCooper(){
        assertEquals(Card.getCard(cards, Card.CardName.Cooper).getCost(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Cooper).getScore(), 0);
        assertEquals(Card.getCard(cards, Card.CardName.Cooper).getType(), Card.Type.TREASURE);
        assertEquals(Card.getCard(cards, Card.CardName.Cooper).getTreasureValue(), 1);
    }
    @Test
    public void PlayCooper(){
        assertEquals(player1.getCoins(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Cooper));
        player1.setCoins(player1.checkTreasure());
        assertEquals(player1.getCoins(), 1);
    }
    @Test
    public void TestAdventurer(){
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getCost(), 6);
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Adventurer).getScore(), 0);
        player1.buildDeck();
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Adventurer));
        assertEquals(player1.hand.size(), 1);
        player1.activateCard(Card.getCard(cards, Card.CardName.Adventurer), board, player2);
        assertEquals(player1.hand.size(), 2);
        player1.setCoins(player1.checkTreasure());
        assertEquals(player1.getCoins(), 2);
    }
    @Test
    public void TestBureaucrat(){
        assertEquals(Card.getCard(cards, Card.CardName.Bureaucrat).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Bureaucrat).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Bureaucrat).getScore(), 0);
        assertEquals(player1.getCoins(), 0);
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Bureaucrat));
        player2.hand.add(Card.getCard(cards, Card.CardName.Cooper));
        player1.activateCard(Card.getCard(cards, Card.CardName.Bureaucrat), board, player2);
        assertEquals(player1.deck.getFirst().getCardName(), Card.CardName.Silver);
    }
    @Test
    public void TestCellar(){
        assertEquals(Card.getCard(cards, Card.CardName.Cellar).getCost(), 2);
        assertEquals(Card.getCard(cards, Card.CardName.Cellar).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Cellar).getScore(), 0);
        assertEquals(player1.getActions(), 1);
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Cellar));
        player1.hand.add(Card.getCard(cards, Card.CardName.Cooper));
        player1.activateCard(Card.getCard(cards, Card.CardName.Cellar), board, player2);
        assertEquals(player1.getActions(), 2);
        assertEquals(player1.hand.size(), 1);
    }
    @Test
    public void TestChancellor(){
        assertEquals(Card.getCard(cards, Card.CardName.Chancellor).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Chancellor).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Chancellor).getScore(), 0);
        assertEquals(player1.deck.size(), 0);
        player1.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.hand.size(), 0);
        assertEquals(player1.getCoins(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Chancellor));
        player1.activateCard(Card.getCard(cards, Card.CardName.Chancellor), board, player2);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player1.discard.size(), 11);
        assertEquals(player1.getCoins(), 2);

    }
    @Test
    public void TestChapel(){
        assertEquals(Card.getCard(cards, Card.CardName.Chapel).getCost(), 2);
        assertEquals(Card.getCard(cards, Card.CardName.Chapel).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Chapel).getScore(), 0);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.hand.size(), 0);
        for(int i = 0; i < 4; i++)
            player1.hand.add(Card.getCard(cards, Card.CardName.Cooper));
        player1.hand.add(Card.getCard(cards, Card.CardName.Chapel));
        assertEquals(player1.hand.size(), 5);
        player1.activateCard(Card.getCard(cards, Card.CardName.Chapel), board, player2);
        assertTrue(player1.hand.size() <= 4);
        assertEquals(player1.discard.size(), 1);
    }
    @Test
    public void TestCouncil_Room(){
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Council_Room).getScore(), 0);
        assertEquals(player1.deck.size(), 0);
        assertEquals(player2.deck.size(), 0);
        assertEquals(player1.getBuys(), 1);
        player1.buildDeck();
        player2.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player2.deck.size(), 10);
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Council_Room));
        assertEquals(player1.hand.size(), 1);
        player1.activateCard(Card.getCard(cards, Card.CardName.Council_Room), board, player2);
        assertEquals(player1.hand.size(), 4);
        assertEquals(player2.hand.size(), 1);
        assertEquals(player1.getBuys(), 2);
        assertEquals(player1.deck.size(), 6);
        assertEquals(player2.deck.size(), 9);
    }
    @Test
    public void TestFeast(){
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Feast).getScore(), 0);
        assertEquals(player1.getBuys(), 1);
        assertEquals(player1.discard.size(), 0);
        assertEquals(player1.getCoins(), 0);
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Feast));
        assertEquals(player1.hand.size(), 1);
        player1.activateCard(Card.getCard(cards, Card.CardName.Feast), board, player2);
        assertEquals(player1.hand.size(), 0);
        assertEquals(player1.getCoins(), 0);
        assertEquals(player1.discard.size(), 1);
        assertEquals(player1.getBuys(), 1);
    }
    @Test
    public void TestGarden(){
        assertEquals(Card.getCard(cards, Card.CardName.Garden).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Garden).getType(), Card.Type.VICTORY);
        assertEquals(Card.getCard(cards, Card.CardName.Garden).getScore(), 0);
    }
    @Test
    public void TestLaboratory(){
        assertEquals(Card.getCard(cards, Card.CardName.Laboratory).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Laboratory).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Laboratory).getScore(), 0);
        assertEquals(player1.deck.size(), 0);
        player1.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player1.getActions(), 1);
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Laboratory));
        assertEquals(player1.hand.size(), 1);
        player1.activateCard(Card.getCard(cards, Card.CardName.Laboratory), board, player2);
        assertEquals(player1.getActions(), 2);
        assertEquals(player1.hand.size(), 2);
    }
    @Test
    public void TestLibrary(){
        assertEquals(Card.getCard(cards, Card.CardName.Library).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Library).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Library).getScore(), 0);
        assertEquals(player1.deck.size(), 0);
        player1.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Library));
        assertEquals(player1.hand.size(), 1);
        player1.activateCard(Card.getCard(cards, Card.CardName.Library), board, player2);
        assertEquals(player1.deck.size(), 3);
        assertEquals(player1.hand.size(), 7);
    }
    @Test
    public void TestMarket(){
        assertEquals(Card.getCard(cards, Card.CardName.Market).getCost(), 5);
        assertEquals(Card.getCard(cards, Card.CardName.Market).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Market).getScore(), 0);
        assertEquals(player1.getActions(), 1);
        assertEquals(player1.getBuys(), 1);
        assertEquals(player1.getCoins(), 0);
        assertEquals(player1.deck.size(), 0);
        player1.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Market));
        assertEquals(player1.hand.size(), 1);
        player1.activateCard(Card.getCard(cards, Card.CardName.Market), board, player2);
        assertEquals(player1.getActions(), 2);
        assertEquals(player1.getBuys(), 2);
        assertEquals(player1.getCoins(), 1);
        assertEquals(player1.deck.size(), 9);
        assertEquals(player1.hand.size(), 1);
    }
    @Test
    public void TestSmithy(){
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).getCost(), 4);
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Smithy).getScore(), 0);
        assertEquals(player1.deck.size(), 0);
        player1.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Smithy));
        assertEquals(player1.hand.size(), 1);
        player1.activateCard(Card.getCard(cards, Card.CardName.Smithy), board, player2);
        assertEquals(player1.hand.size(), 3);
        assertEquals(player1.deck.size(), 7);
    }
    @Test
    public void TestVillage(){
        assertEquals(Card.getCard(cards, Card.CardName.Village).getCost(), 3);
        assertEquals(Card.getCard(cards, Card.CardName.Village).getType(), Card.Type.ACTION);
        assertEquals(Card.getCard(cards, Card.CardName.Village).getScore(), 0);
        assertEquals(player1.getActions(), 1);
        assertEquals(player1.deck.size(), 0);
        player1.buildDeck();
        assertEquals(player1.deck.size(), 10);
        assertEquals(player1.hand.size(), 0);
        player1.hand.add(Card.getCard(cards, Card.CardName.Village));
        assertEquals(player1.hand.size(), 1);
        player1.activateCard(Card.getCard(cards, Card.CardName.Village), board, player2);
        assertEquals(player1.hand.size(), 1);
        assertEquals(player1.getActions(), 3);
    }
}