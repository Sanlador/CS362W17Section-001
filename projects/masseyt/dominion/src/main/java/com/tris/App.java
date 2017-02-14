package com.tris;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        List<Card> cards;
        Game game;
        Randomness.reset(10);

        cards = new ArrayList<Card>(Card.generate());
        game = new Game(cards);

        int numPlayers = 2;
        System.out.println(numPlayers + " players!");

        Player player;
        player = new Player(game, "player1");
        game.addPlayer(player);
        player = new Player(game, "player2");
        game.addPlayer(player);

        game.initGame();

        HashMap<Player, Integer> winners= null;
        winners = game.play();
        System.out.println ("Finished game.\n");

        for(Player p: winners.keySet()){
            System.out.println ("Player name: "+ p.name + " , Score: "+ winners.get(p) );
        }

        System.exit(0);
    }
}
