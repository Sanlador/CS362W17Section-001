package com.tris;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Tristan Massey on 2/9/2017.
 */
public class Game {

    List<Player> players = new ArrayList<Player>();
    List<Card> cards;
    HashMap<Card, Integer> board = new HashMap<Card, Integer>();
    HashMap<Card, Integer> embargo = new HashMap<Card, Integer>();
    HashMap<Card, Integer> trash = new HashMap<Card, Integer>();

    Game(List<Card> cards){
        this.cards = cards;
    }
    void addPlayer(Player p){
        players.add(p);
    }
    void initGame(){
//        int selectedKingdom = 0;
//        int kingdomsTotal = 11;
//        while (selectedKingdom < kingdomsTotal){
//            int random = (int) Randomness.random.nextInt(cards.size());
//            Card c = cards.get(random);
//            if (c.getType() != Card.Type.ACTION)
//                continue;
//            if (board.containsKey(c))
//                continue;
//            board.put(c, 10);
//            selectedKingdom++;
//        }
        board.put(Card.getCard(cards, Card.Name.CURSE), 10);
        board.put(Card.getCard(cards, Card.Name.COPPER), 46);
        board.put(Card.getCard(cards, Card.Name.SILVER), 40);
        board.put(Card.getCard(cards, Card.Name.GOLD), 30);
        board.put(Card.getCard(cards, Card.Name.ESTATE), 8);
        board.put(Card.getCard(cards, Card.Name.DUTCHY), 8);
        board.put(Card.getCard(cards, Card.Name.PROVINCE), 8);

        board.put(Card.getCard(cards, Card.Name.ADVENTURER), 10);
        board.put(Card.getCard(cards, Card.Name.AMBASSADOR), 10);
        board.put(Card.getCard(cards, Card.Name.BARON), 10);
        board.put(Card.getCard(cards, Card.Name.COUNCIL_ROOM), 10);
        board.put(Card.getCard(cards, Card.Name.CUTPURSE), 10);
        board.put(Card.getCard(cards, Card.Name.EMBARGO), 10);
        board.put(Card.getCard(cards, Card.Name.FEAST), 10);
        board.put(Card.getCard(cards, Card.Name.GARDENS), 10);
        board.put(Card.getCard(cards, Card.Name.GREAT_HALL), 10);
        board.put(Card.getCard(cards, Card.Name.MINE), 10);
//        board.put(Card.getCard(cards, Card.Name.REMODEL), 10);
//        board.put(Card.getCard(cards, Card.Name.SMITHY), 10);
//        board.put(Card.getCard(cards, Card.Name.VILLAGE), 10);

        embargo.put(Card.getCard(cards, Card.Name.ADVENTURER), 0);
        embargo.put(Card.getCard(cards, Card.Name.AMBASSADOR), 0);
        embargo.put(Card.getCard(cards, Card.Name.BARON), 0);
        embargo.put(Card.getCard(cards, Card.Name.COUNCIL_ROOM), 0);
        embargo.put(Card.getCard(cards, Card.Name.CUTPURSE), 0);
        embargo.put(Card.getCard(cards, Card.Name.EMBARGO), 0);
        embargo.put(Card.getCard(cards, Card.Name.FEAST), 0);
        embargo.put(Card.getCard(cards, Card.Name.GARDENS), 0);
        embargo.put(Card.getCard(cards, Card.Name.GREAT_HALL), 0);
        embargo.put(Card.getCard(cards, Card.Name.MINE), 0);
//        embargo.put(Card.getCard(cards, Card.Name.REMODEL), 0);
//        embargo.put(Card.getCard(cards, Card.Name.SMITHY), 0);
//        embargo.put(Card.getCard(cards, Card.Name.VILLAGE), 0);

        for (Player p : players){
            for (int i = 0; i < 7; i++)
                p.gain(Card.getCard(cards, Card.Name.COPPER));
            for (int i = 0; i < 3; i++)
                p.gain(Card.getCard(cards, Card.Name.ESTATE));
            p.initPlayer();
        }
    }

    public List<Card> listBoard(){
        int i = 1;
        List<Card> set = new ArrayList<Card>();
        for (Map.Entry<Card, Integer> list : board.entrySet()){
            //System.out.print(i + " ");
            Card c = list.getKey();
            int val = list.getValue();
            //Card.info(c);
            set.add(c);
            //System.out.println(" (" + val + " remaining)");
            i++;
        }
        return set;
    }

    public List<Card> listEmbargo(){
        int i = 1;
        List<Card> set = new ArrayList<Card>();
        for (Map.Entry<Card, Integer> list : embargo.entrySet()){
            System.out.print(i + " ");
            Card c = list.getKey();
            int val = list.getValue();
            Card.info(c);
            set.add(c);
            System.out.println(" (" + val + " tokens currently)");
            i++;
        }
        return set;
    }

    public int getRemaining(Card c){
        int remaining = board.get(c);
        return remaining;
    }

//    public void listActions(){
//        List<Card> actions = new ArrayList<Card>();
//        for (Map.Entry<Card, Integer> list : board.entrySet()){
//            if (list.getKey().getType() == Card.Type.ACTION)
//                actions.add(list.getKey());
//            for (int i = 0; i < actions.size(); i++){
//                Card c = actions.get(i);
//                Card.info(c);
//            }
//        }
//    }

    public HashMap<Player, Integer> play() {
        int turn = 0;
        while (!isGameOver()){
            turn++;
            for (Player p : players){
                System.out.println(p.name + "'s turn.");
                p.playAction();
                p.buy();
                System.out.println();
                System.out.println("*****Clean-up Phase*****");
                p.endTurn();
            }
//            if (turn == 3)
//                break;
        }
        return this.getWinners();
    }

    public boolean isGameOver(){
        System.out.println("CHECKING FOR GAME OVER: PROVINCES LEFT - " + board.get(Card.getCard(cards, Card.Name.PROVINCE)));
        System.out.println("DUTCHYS LEFT - " + board.get(Card.getCard(cards, Card.Name.DUTCHY)));
        System.out.println("ESTATES LEFT - " + board.get(Card.getCard(cards, Card.Name.ESTATE)));
        System.out.println("CURSES LEFT - " + board.get(Card.getCard(cards, Card.Name.CURSE)));
        if ((board.get(Card.getCard(cards, Card.Name.PROVINCE)) == null) || (board.get(Card.getCard(cards, Card.Name.PROVINCE)) == 0)){
            System.out.println("Game over!");
            return true;
        }
        System.out.print("CHECKING FOR GAME OVER: EMPTY PILES - ");
        int empty = 0;
        for (int i : board.values()){
            if (i == 0)
                empty++;
            if (empty >= 3){
                System.out.println("Game over!");
                return true;
            }
        }
        System.out.println(empty);
        return false;
    }

    public HashMap<Player, Integer> getWinners(){
        HashMap<Player, Integer> playerScore;
        playerScore = new HashMap<Player, Integer>();
        for (Player p : players){
            int score = p.score();
            playerScore.put(p, score);
        }
        return playerScore;
    }
}
