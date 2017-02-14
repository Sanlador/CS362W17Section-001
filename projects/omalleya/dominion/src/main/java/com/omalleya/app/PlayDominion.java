package com.omalleya.app;

import java.util.HashMap;
import java.util.List;


public class PlayDominion {
    public static void main( String[] args )
    {
        List<Card> cards = Card.createCards();
        GameState gs = new GameState(cards);
        
        Player p1 = new Player(gs, "player 1");
        Player p2 = new Player(gs, "player 2");

        gs.addPlayer(p1);
        gs.addPlayer(p2);

        gs.initializeGame();

        //testing to see what the players' decks looks like

        HashMap<Player, Integer> winners=gs.play();
        System.out.println ("Finished game.\n");
    
        for(Player p: winners.keySet()){
            System.out.println ("Player name: "+p.player_name + " , Score: "+ winners.get(p) );
        }
    }
}