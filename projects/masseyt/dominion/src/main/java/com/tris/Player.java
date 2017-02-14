package com.tris;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import com.tris.Card.Type;

/**
 * Created by Tristan Massey on 2/9/2017.
 */
public class Player {
    LinkedList<Card> deck = new LinkedList<Card>();
    List<Card> hand = new ArrayList<Card>();
    List<Card> discard = new ArrayList<Card>();
    List<Card> played = new ArrayList<Card>();

    int actions, buys, coins;
    Input input = new Input();

    Game game;
    String name;

    Player(Game game, String name) {
        this.game = game;
        this.name = name;
    }

    Card draw() {
        if (deck.isEmpty()) {
            System.out.println("Deck is empty. Reshuffling.");
            while (discard.size() > 0) {
                int index = (int) Randomness.nextRandomInt(discard.size());
                deck.add(discard.remove(index));
            }
        }
        Card drawn = deck.poll();
        hand.add(drawn);
        System.out.println("Drew " + drawn.getName());
        //Collections.sort(hand);
        return drawn;
    }

    void initPlayer() {
        actions = 1;
        buys = 1;
        coins = 0;
        for (int i = 0; i < 5; i++)
            draw();
    }

    void gain(Card card) {
        System.out.println("Gained " + card.getName());
        game.board.put(card, game.board.get(card)-1);
        discard.add(card);
    }

    void discard(Card card) {
        System.out.println("Discarded " + card.getName());
        hand.remove(card);
        discard.add(card);
    }

    int score() {
        int score = 0;
        int cards = 0;
        int gardens = 0;
        for (Card c : hand) {
            score += c.points(this);
            cards++;
            if (c.getName().equals(Card.Name.GARDENS))
                gardens++;
            if (c.getName().equals(Card.Name.GREAT_HALL))
                score++;
        }
        for (Card c : played) {
            score += c.points(this);
            cards++;
            if (c.getName().equals(Card.Name.GARDENS))
                gardens++;
            if (c.getName().equals(Card.Name.GREAT_HALL))
                score++;
        }
        for (Card c : discard) {
            score += c.points(this);
            cards++;
            if (c.getName().equals(Card.Name.GARDENS))
                gardens++;
            if (c.getName().equals(Card.Name.GREAT_HALL))
                score++;
        }
        for (Card c : deck) {
            score += c.points(this);
            cards++;
            if (c.getName().equals(Card.Name.GARDENS))
                gardens++;
            if (c.getName().equals(Card.Name.GREAT_HALL))
                score++;
        }
        int multiplier = cards/10;
        score += gardens * multiplier;
        return score;
    }

    void playAction() {
        System.out.println("CURRENT SCORE: " + score());
        System.out.println("*****Action Phase*****");
        while (actions > 0) {
            System.out.println("Your current hand: ");
            listCards(hand);
            List<Card> actionCards = Card.filter(hand, Type.ACTION);
            if (actionCards.isEmpty()) {
                System.out.println("No action cards...");
                return;
            }
            Card c = (Card) actionCards.get(0);
            if (c == null) {
                return;
            }
            //System.out.println("Which card would you like to play? (enter number of card, or 0 for none)");
            //int index = input.getInt(1, hand.size()) - 1;
            int index = input.getInt(0, hand.size());
            if (hand.get(index).getType().equals(Type.ACTION)) {
                played.add(hand.get(index));
                c = hand.remove(index);
                actions--;
                c.play(this, game);
            }
        }
        System.out.println();
    }

    void buy() {
        System.out.println("*****Buy Phase*****");
        //System.out.println("Available to buy:");
        List<Card> set = new ArrayList<Card>();
        set = game.listBoard();
        List<Card> treasureCards = Card.filter(hand, Type.TREASURE);
        Card c;
        for (int i = 0; i < treasureCards.size(); i++) {
            c = (Card) treasureCards.get(i);
            coins += c.value;
        }
        while (buys > 0) {
            //System.out.println("You have " + treasureCards.size() + " treasure cards...");
            //System.out.println("You have " + coins + " coins to spend...");
            //System.out.println("Which card would you like to buy? (enter number of card, or 0 for none)");
            int selection = input.getInt(0, 17);
            if (selection == 0) {
                System.out.println("Skipping Buy Phase...");
                return;
            }
            c = set.get(selection - 1);
            if (game.getRemaining(c) <= 0)
                System.out.println("There are no " + c.getName() + "S remaining.");
            else {
                if (c.getCost() > coins) {
                    //System.out.println("You can't afford a " + c.getName());
                } else {
                    if (selection == 16)
                        break;
                    System.out.println("You bought a " + c.getName() + " (-" + c.getCost() + " coins)");
                    coins -= c.getCost();
                    game.board.put(c, game.board.get(c) - 1);
                    discard.add(c);
                    if (c.getType().equals(Type.ACTION)) {
                        Card curse = c;
                        for (Card temp : set)
                            if (temp.getName().equals(Card.Name.CURSE))
                                curse = temp;
                        for (int i = 0; i < game.embargo.get(c); i++)
                            if (game.board.get(curse) != 0)
                                gain(curse);
                    }
                    buys--;
                }
            }
        }
    }

    public static List<Card> listCards(List<Card> c) {
        for (int i = 0; i < c.size(); i++) {
            System.out.print((i+1) + " ");
            Card.info(c.get(i));
            System.out.println();
        }
        return c;
    }

    void endTurn() {
        actions = 1;
        buys = 1;
        coins = 0;
        Card c;
        System.out.println("Discarding...");
        while (hand.size() > 0) {
            c = hand.remove(0);
            discard.add(c);
        }
        while (played.size() > 0) {
            c = played.remove(0);
            discard.add(c);
        }
        while (hand.size() < 5)
            draw();
        System.out.println("Turn finished... pass to next player");
        System.out.println();
    }
}
