package org.cs362.dominion;
import java.util.*;

import static org.junit.Assert.*;
import org.junit.Test;

//@Test goes over functions
//assertEquals("THIS IS TEXT", testTheThing, what it should be);

//CARDS:
//1  silver
//2  copper
//3  province
//4  dutchy
//5  estate
//6  village
//7  smithy
//8  adventurer
//9  witch
//10 cellar
//11 market
//12 curse
//13 ambassador
//14 baron
//15 council room
//16 cutpurse
//17 embargo
//18 feast
//19 gardens
//20 great hall
//21 mine
//22 salvager
//23 gold


public class GameTest {
    @Test
    public void basicSetup() {
        System.out.println("\n\n############ BASIC SETUP ############\n");
        Game dominion = new Game("Connor");

        int playerNumber = 0;
        for (Player player: dominion.players) {
            String name = player.getName();
            String cardToPlay;

            System.out.printf("\nSetting start points for player %s\n", name);
            player.starterPoints();

            System.out.printf("\n%s decks\n", name);
            player.printAllDecks();
            System.out.printf("\n%s drawing 50 cards\n", name);
            player.draw(50);
            System.out.printf("\n%s decks\n", name);
            player.printAllDecks();
            player.discardAll();
        }
    }

    @Test
    public void villageTest() {
        System.out.println("\n\n###### VILLAGE TEST ######\n");
        Game dominion = new Game("Connor");

        int playerNumber = 0;
        for (Player player: dominion.players) {

            setups(dominion, player, playerNumber, "village");


            //TESTING FOR CARD RESULTS
            System.out.println("There should be 10 cards in the players hand and one village in the discard pile:\n");
            player.printAllDecks();
            assertEquals("The player should have 0 moves after playing village", player.getActions(), 2);

            playerNumber++;
        }

        dominion = new Game("Connor");
        for (Player player: dominion.players) {
            for (int x = 0; x < 20; x++) {
                dominion.buy(player, "village");
            }
            player.recycle();
            player.draw(10);
            player.starterPoints();
            String cardToPlay;
            while (player.hasActions()) {
                cardToPlay = dominion.randomCard();
                dominion.playCard(0, cardToPlay);
            }
        }
    }

    @Test
    public void smithyTest() {
        System.out.println("\n\n###### SMITHY TEST ######\n");
        Game dominion = new Game("Connor");

        int playerNumber = 0;
        for (Player player: dominion.players) {

            setups(dominion, player, playerNumber, "smithy");

            //TESTING FOR CARD RESULTS
            assertTrue("There is no smithy in the discard file", player.discard.hasCard("smithy"));
            assertTrue("There are not 3 cards in the hand", player.hand.numCards() == 3);
            assertEquals("The player should have 0 moves after playing smithy", player.getActions(), 0);

            playerNumber++;
        }
    }

    @Test
    public void adventurerTest() {
        System.out.println("\n\n###### ADVENTURE TEST ######\n");
        Game dominion = new Game("Connor");

        int playerNumber = 0;
        for (Player player: dominion.players) {

            setups(dominion, player, playerNumber, "adventurer");

            //TESTING FOR CARD RESULTS
            System.out.printf("There should be 2 cards in the players hand and one adventurerer in the discard pile:\n");
            player.printAllDecks();
            assertEquals("The player should have 0 moves after playing adventurer", player.getActions(), 0);

            playerNumber++;
        }
    }

    @Test
    public void baronTest() {
        System.out.println("\n\n###### BARON TEST ######\n");
        Game dominion = new Game("Connor");

        int playerNumber = 0;
        for (Player player: dominion.players) {

            setups(dominion, player, playerNumber, "baron");

            //TESTING FOR CARD RESULTS
            System.out.printf("The baron card could have chosen to discard it for 4 coins, or draw an estate card with it.\n");
            player.printAllDecks();
            assertEquals("The player should have 0 moves after playing baron", player.getActions(), 0);

            playerNumber++;
        }
    }

    @Test
    public void witchTest() {
        System.out.println("\n\n###### WITCH TEST ######\n");
        Game dominion = new Game("Connor", "Billy", "Lily");

        int playerNumber = 0;
        //select the first player
        setups(dominion, dominion.getPlayer(0), 0, "witch");

        for (Player player: dominion.players) {

            //TESTING FOR CARD RESULTS
            if (playerNumber != 0) {
                assertTrue("A player is missing a curse card", player.discard.hasCard("curse"));
            }
            assertEquals("The player should have 0 moves after playing witch", player.getActions(), 0);

            playerNumber++;
        }

        for (int x = 0; x < 20; x++)
            dominion.getDeck("curse").drawCard();
        assertEquals("The witch deck was not drawn to being empty", dominion.getDeck("curse").numCards(), 0);

        dominion.playCard(0, "witch");
        for (Player player: dominion.players) {
            player.printAllDecks();
        }
    }

    @Test
    public void cellarTest() {
        System.out.println("\n\n############ CELLAR TEST ############\n");
        Game dominion = new Game("Connor");

        int playerNumber = 0;
        //select the first player

        for (Player player: dominion.players) {
            System.out.println("Filling the hand with random cards\n");
            for (int x = 0; x < 10; x++) {
                player.hand.addCard(new Card(dominion.randomCard()));
            }
            System.out.println("\n");
            setups(dominion, player, playerNumber, "cellar");

            //TESTING FOR CARD RESULTS
            System.out.printf("The cellar card should have discarded a random number of cards and the hand should still be the same size\n");
            player.printAllDecks();

            assertEquals("The player did not maintain the 1 move after playing cellar", player.getActions(), 1);

            playerNumber++;
        }
    }

    @Test
    public void councilRoomTest() {
        System.out.println("\n\n############ COUNCIL ROOM TEST ############\n");
        Game dominion = new Game("Connor", "Billy", "Lily");

        int playerNumber = 0;
        //select the first player

        setups(dominion, dominion.getPlayer(0), 0, "council room");
        for (Player player: dominion.players) {

            //TESTING FOR CARD RESULTS
            System.out.printf("The council room card should have made all other players draw a card\n");
            player.printAllDecks();

            assertEquals("The player did not move to 0 moves after playing council room", player.getActions(), 0);

            playerNumber++;
        }
    }

    @Test
    public void cutPurseTest() {
        System.out.println("\n\n############ CUTPURSE TEST ############\n");
        Game dominion = new Game("Connor", "Billy", "Lily");

        int playerNumber = 0;
        //select the first player

        for (Player player: dominion.players) {
            player.hand.addCard(new Card("copper"));
        }
        setups(dominion, dominion.getPlayer(0), 0, "cutpurse");
        for (Player player: dominion.players) {

            //TESTING FOR CARD RESULTS
            if (playerNumber != 0) {
                assertTrue("The player does not have a discarded copper", player.discard.hasCard("copper"));
            }
            assertEquals("The player did not move to 0 moves after playing council room", player.getActions(), 0);

            playerNumber++;
        }
    }

    @Test
    public void feastTest() {
        System.out.println("\n\n############ FEAST TEST ############\n");
        Game dominion = new Game("Connor");
        for (int x = 0; x < 100; x++) {
            setups(dominion, dominion.getPlayer(0), 0, "feast");

            //TESTING FOR CARD RESULTS
            assertTrue("The purchased card with feast is less than a value of 5", dominion.getPlayer(0).discard.cardInfo(0).getCost() <= 5);
            assertEquals("The player did not move to 0 moves after playing council room", dominion.getPlayer(0).getActions(), 0);
            dominion = new Game("Connor");
        }
    }

    @Test
    public void mineTest() {
        System.out.println("\n\n############ MINE TEST ############\n");
        Game dominion = new Game("Connor");
        dominion.getPlayer(0).hand.addCard(new Card("copper"));
        setups(dominion, dominion.getPlayer(0), 0, "mine");

        //TESTING FOR CARD RESULTS
        assertTrue("The mine did not traid a copper for a silver", dominion.getPlayer(0).hand.hasCard("silver"));
        assertEquals("The player did not move to 0 moves after playing mine", dominion.getPlayer(0).getActions(), 0);

        dominion = new Game("Connor");
        dominion.getPlayer(0).hand.addCard(new Card("silver"));
        setups(dominion, dominion.getPlayer(0), 0, "mine");
        assertTrue("The mine did not traid a silver for a gold", dominion.getPlayer(0).hand.hasCard("gold"));

        dominion = new Game("Connor");
        dominion.getPlayer(0).hand.addCard(new Card("gold"));
        setups(dominion, dominion.getPlayer(0), 0, "mine");
    }

    @Test
    public void victoryPointsTest() {
        System.out.println("\n\n############ VICTORY POINTS TEST ############\n");
        Player player = new Player("Connor");

        player.drawDeck.addCard(new Card("gardens"));
        player.drawDeck.addCard(new Card("gardens"));

        int gardensCount = 0;
        for (int y = 0; y < player.drawDeck.numCards(); y++) {
            if (player.drawDeck.cardInfo(y).getName().equals("gardens")) {
                gardensCount++;
            }
        }
        assertEquals("The counter says there are not two gardens", gardensCount, 2);
        player.drawDeck.addNumberOfCardType(10, new Card("copper"));

        int numCards = player.drawDeck.numCards();
        System.out.printf("Gardens: %d", gardensCount);
        System.out.printf("\tCount: %d", numCards);
    }


    public void setups(Game dominion, Player player, int playerNumber, String cardTest) {
        player.starterPoints();
        player.hand.addCard(new Card(cardTest));
        dominion.playCard(playerNumber, cardTest);
    }

    @Test
    public void testMain() {
        System.out.println("\n\n############################# MAIN GAME #############################\n\n");
        Game.main(null);
    }
}
