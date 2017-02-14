package org.cs362.dominion;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;


public class ShopTest {

    private List<Card> cards;// = new ArrayList<Card>(Card.createCards());
    private GameState state;// = new GameState(cards);
    private Player player;//=new Player(state, "Easy-Com");
    private Player bot;


    @Before
    public void initalizeBoard(){
        cards = new ArrayList<Card>(Card.createCards());
        state = new GameState(cards);
        player=new Player(state, "New-Com");
        bot=new Player(state, "Just a bot");
        state.addPlayer(player);
        state.addPlayer(bot);
        int selectedKindom=0;
        int Kingdom_Cards_Selected=10;
        while (selectedKindom < Kingdom_Cards_Selected) {
            int random = (int)  Randomness.random.nextInt(cards.size());//
            Card tmp = cards.get(random);
            if(tmp.getType()!=Card.Type.ACTION) continue;
            if(state.gameBoard.containsKey(tmp)) continue;
            state.gameBoard.put(tmp, 10);
            selectedKindom++;
        }
        //set number of Curse cards the default number of players is 2
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Curse), 10);

        //set number of Victory cards
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Province), 8);
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Duchy), 8);
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Estate), 8);
        //set number of Treasure cards
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
        state.gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 46);
    }


    @Test
    public void noActions() throws Exception {
        //System.out.println(state.gameBoard);
        System.out.println(" --- ----------New Test--------- --- ");
        player.coins=10;
        player.numBuys=0;
        player.buyCard(state);
        assertEquals(player.coins, 10);
        System.out.println("Player has " + player.coins +" coins but without actions, cant buy card!");
        System.out.println(" --- ----------End Test--------- --- \n");

    }

    @Test
    public void buyMultiCards(){
        System.out.println(" --- ----------New Test--------- --- ");
        player.coins=10;
        player.numBuys=3;
        player.buyCard(state);

        assertEquals(player.numBuys, 0);
        //assertEquals(player.coins, 0);
        System.out.println(" --- ----------End Test--------- --- \n");
    }

    @Test
    public void RandomSelectGoods(){
        System.out.println(" --- ----------New Test--------- --- ");
        for(int i = 0; i < 10; i++) {
            bot.coins = 4;
            bot.numBuys = 1;
            bot.buyCard(state);
        }

        //assertEquals(player.coins, 4);
        System.out.println(" --- ----------End Test--------- --- \n");



        }

        @Test
        public void ult_test_all(){
        Random ran = new Random();
        //Random test 1000 time with random size of coins, buyings.
            for(int i = 0; i < 1000; i++){
                System.out.println("---No."+i+"----");
                int ranCoin = ran.nextInt(10);
                int ranBuy = ran.nextInt(4);
                bot.coins = ranCoin;
                bot.numBuys = ranBuy;
                bot.buyCard(state);
            }
            System.out.println("\n---complete----");
            System.out.println("\n1000 times testing end. it looks fine to me");

    }
    }

