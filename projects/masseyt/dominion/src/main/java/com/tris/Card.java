package com.tris;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Card {
    public enum Type {
        TREASURE, ACTION, VICTORY;
    }

    public enum Name {
        COPPER, SILVER, GOLD, ESTATE, DUTCHY, PROVINCE, CURSE,
        ADVENTURER, AMBASSADOR, BARON, COUNCIL_ROOM, CUTPURSE, EMBARGO, FEAST,
        GARDENS, GREAT_HALL, MINE, REMODEL, SMITHY, VILLAGE;
    }

    Type type;
    Name name;
    int cost, points, value;
    String text;

    Card(Name name, Type type, int cost, int points, int value, String text) {
        this.name = name;
        this.type = type;
        this.cost = cost;
        this.points = points;
        this.value = value;
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public Name getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int points(Player p) {
        return points;
    }

    static List<Card> generate() {
        List<Card> cardList = new ArrayList<Card>();
        Card card = new Card(Name.COPPER, Type.TREASURE, 0, 0, 1, "No info");
        cardList.add(card);
        card = new Card(Name.SILVER, Type.TREASURE, 3, 0, 2, "No info");
        cardList.add(card);
        card = new Card(Name.GOLD, Type.TREASURE, 6, 0, 3, "No info");
        cardList.add(card);

        card = new Card(Name.ESTATE, Type.VICTORY, 2, 1, 0, "No info");
        cardList.add(card);
        card = new Card(Name.DUTCHY, Type.VICTORY, 5, 3, 0, "No info");
        cardList.add(card);
        card = new Card(Name.PROVINCE, Type.VICTORY, 8, 6, 0, "No info");
        cardList.add(card);
        card = new Card(Name.CURSE, Type.VICTORY, 0, -1, 0, "No info");
        cardList.add(card);

        card = new Card(Name.ADVENTURER, Type.ACTION, 6, 0, 0, "Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.");
        cardList.add(card);
        card = new Card(Name.AMBASSADOR, Type.ACTION, 3, 0, 0, "Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.");
        cardList.add(card);
        card = new Card(Name.BARON, Type.ACTION, 4, 0, 0, "+1 Buy, You may discard an Estate card. If you do, +4 Coins. Otherwise, gain an Estate card.");
        cardList.add(card);
        card = new Card(Name.COUNCIL_ROOM, Type.ACTION, 5, 0, 0, "+4 Cards, +1 Buy, Each other player draws a card.");
        cardList.add(card);
        card = new Card(Name.CUTPURSE, Type.ACTION, 4, 0, 0, "+2 Coins, Each other player discards a Copper card (or reveals a hand with no Copper).");
        cardList.add(card);
        card = new Card(Name.EMBARGO, Type.ACTION, 2, 0, 0, "+2 Coins, Trash this card. Put an Embargo token on top of a Supply pile. - When a player buys a card, he gains a Curse card per Embargo token on that pile.");
        cardList.add(card);
        card = new Card(Name.FEAST, Type.ACTION, 4, 0, 0, "Trash this card. Gain a card costing up to 5 Coins.");
        cardList.add(card);
        card = new Card(Name.GARDENS, Type.VICTORY, 4, 0, 0, "Variable, Worth 1 Victory for every 10 cards in your deck (rounded down).");
        cardList.add(card);
        card = new Card(Name.GREAT_HALL, Type.ACTION, 3, 0, 0, "1 Victory, +1 Card, +1 Action.");
        cardList.add(card);
        card = new Card(Name.MINE, Type.ACTION, 5, 0, 0, "Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 Coins more; put it into your hand.");
        cardList.add(card);
        card = new Card(Name.REMODEL, Type.ACTION, 4, 0, 0, "Trash a card from your hand. Gain a card costing up to 2 Coins more than the trashed card.");
        cardList.add(card);
        card = new Card(Name.SMITHY, Type.ACTION, 4, 0, 0, "+3 Cards.");
        cardList.add(card);
        card = new Card(Name.VILLAGE, Type.ACTION, 3, 0, 0, "+1 Card. +2 Actions.");
        cardList.add(card);
        return cardList;
    }

    public void play(Player player, Game game) {

        switch (this.name) {
            case ADVENTURER:
                System.out.println("You play an Adventurer: Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.");
                int two = 0;
                Card c;
                do {
                    player.draw();
                    c = player.hand.get(player.hand.size()-1);
                    if (c.getType().equals(Type.TREASURE))
                        two++;
                    else
                        player.discard(c);
                } while (two < 2);
                return;
            case AMBASSADOR:
                System.out.println("You play an Ambassador: Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.");
                player.listCards(player.hand);
                System.out.println("Choose a card to return to the supply:");
                int sel = Input.getInt(1, player.hand.size()) - 1;
                c = player.hand.remove(sel);
                for (int i = 0; i < player.hand.size(); i++) {
                    if (c.getName().equals(player.hand.get(i).getName())) {
                        player.hand.remove(i);
                        break;
                    }
                }
                for (Player p : game.players)
                    if (!p.name.equals(player.name))
                        p.gain(c);
                return;
            case BARON:
                System.out.println("You play a Baron: +1 Buy, You may discard an Estate card. If you do, +4 Coins. Otherwise, gain an Estate card.");
                player.buys++;
                boolean extra = true;
                for (int i = 0; i < player.hand.size(); i++) {
                    if (player.hand.get(i).getName().equals(Name.ESTATE)) {
                        player.discard(player.hand.get(i));
                        player.coins += 4;
                        extra = false;
                        break;
                    }
                }
                if (extra)
                    player.gain(getCardBoard(game.board, Name.ESTATE));
                return;
            case COUNCIL_ROOM:
                System.out.println("You play a Council Room: +4 Cards, +1 Buy, Each other player draws a card.");
                player.draw();
                player.draw();
                player.draw();
                player.buys++;
                for (Player p : game.players)
                    p.draw();

                return;
            case CUTPURSE:
                System.out.println("You play a Cutpurse: +2 Coins, Each other player discards a Copper card (or reveals a hand with no Copper).");
                player.coins += 2;
                for (Player p : game.players) {
                    if (!p.name.equals(player.name)) {
                        for (int i = 0; i < p.hand.size(); i++) {
                            if (p.hand.get(i).getName().equals(Name.COPPER)) {
                                p.discard(p.hand.get(i));
                                break;
                            }
                        }
                    }
                }
                return;
            case EMBARGO:
                System.out.println("You play an Embargo: +2 Coins, Trash this card. Put an Embargo token on top of a Supply pile. - When a player buys a card, he gains a Curse card per Embargo token on that pile.");
                player.coins += 2;
                List<Card> set = new ArrayList<Card>();
                set = game.listEmbargo();
                System.out.println("Pick a supply to place an Embargo Token on:");
                sel = Input.getInt(1, set.size()) - 1;
                c = set.get(sel);
                game.embargo.put(c, (game.embargo.get(c) + 1));
            case FEAST:
                System.out.println("You play a Feast: Trash this card. Gain a card costing up to 5 Coins.");
                set = game.listBoard();
                boolean done = false;
                do {
                    System.out.println("Which card would you like to gain? (enter number of card, or 0 for none)");
                    int selection = Input.getInt(0, 17);
                    if (selection == 0) {
                        System.out.println("Skipping gain...");
                        done = true;
                    } else {
                        c = set.get(selection - 1);
                        if (game.getRemaining(c) <= 0)
                            System.out.println("There are no " + c.getName() + "S remaining.");
                        else {
                            if (c.getCost() > 5) {
                                System.out.println(c.getName() + " costs more than 5 coins.");
                            } else {
                                game.board.put(c, game.board.get(c) - 1);
                                player.gain(c);
                                player.played.remove(player.played.size()-1);
                                done = true;
                            }
                        }
                    }
                } while (done == false);
                return;
            case GARDENS:
                System.out.println("Cannot play a Garden: Worth 1 Victory for every 10 cards in your deck (rounded down).");
                return;
            case GREAT_HALL:
                System.out.println("You play a Great Hall: 1 Victory, +1 Card, +1 Action.");
                player.draw();
                player.actions++;
                return;
            case MINE:
                System.out.println("You play a Mine: Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 Coins more; put it into your hand.");
                System.out.println("Choose a treasure card to trash:");
                Card card = player.hand.get(0);
                do {
                    sel = Input.getInt(0, player.hand.size());
                    if (sel == 0) {
                        System.out.println("Skipping treasure upgrade.");
                        break;
                    }
                    sel--;
                    card = player.hand.get(sel);
                    System.out.println("SELECTING TREASURE TO TRASH");
                } while (!(card.getType().equals(Type.TREASURE)));
                player.hand.remove(sel);
                set = game.listBoard();
                Card silver = set.get(0);
                Card gold = set.get(0);
                for (int i = 0; i < set.size(); i++) {
                    if (set.get(i).getName().equals(Name.SILVER))
                        silver = set.get(i);
                    if (set.get(i).getName().equals(Name.GOLD))
                        gold = set.get(i);
                }
                if (card.getName().equals(Name.COPPER)) {
                    player.gain(silver);
                }
                if (card.getName().equals(Name.SILVER)) {
                    player.gain(gold);
                }
                return;
            case REMODEL:
                System.out.println("You play a Remodel: Trash a card from your hand. Gain a card costing up to 2 Coins more than the trashed card.");
                player.listCards(player.hand);
                System.out.println("Choose a card to trash:");
                int idx = Input.getInt(1, player.hand.size()) - 1;
                card = player.hand.get(idx);
                player.hand.remove(idx);
                System.out.println("Gain a card costing up to " + (card.getCost() + 2) + " coins:");
                done = false;
                set = game.listBoard();
                do {
                    int selection = Input.getInt(1, 17);
                    c = set.get(selection - 1);
                    if (game.getRemaining(c) <= 0)
                        System.out.println("There are no " + c.getName() + "S remaining.");
                    else {
                        if (c.getCost() > (card.getCost()+2)) {
                            System.out.println(c.getName() + " costs more than " + (card.getCost()+2) + " coins.");
                        } else {
                            game.board.put(c, game.board.get(c) - 1);
                            player.gain(c);
                            done = true;
                        }
                    }
                } while (done == false);
                return;
            case SMITHY:
                System.out.println("You play a Smithy: +3 Cards.");
                player.draw();
                player.draw();
                player.draw();
                return;
            case VILLAGE:
                System.out.println("You play a Village: +1 Card, +2 Actions.");
                player.draw();
                player.actions += 2;
//            case CELLAR:
//                System.out.println("You play a Cellar");
//                return;
//            case MARKET:
//                System.out.println("You play a Market: +1 Card. +1 Action. +1 Buy. +1 Coin.");
//                player.draw();
//                player.actions++;
//                player.buys++;
//                player.coins++;
//                return;
//            case MILITIA:
//                System.out.println("You play a Militia");
//                return;
//            case MINE:
//                System.out.println("You play a Mine");
//                return;
//            case MOAT:
//                System.out.println("You play a Moat: +2 Cards.");
//                player.draw();
//                player.draw();
//                return;
//            case WITCH:
//                System.out.println("You play a Witch");
//                return;
//            case WOODCUTTER:
//                System.out.println("You play a Woodcutter: ????");
//                return;
//            case WORKSHOP:
//                System.out.println("You play a Workshop: Gain a card costing up to 4 Coins.");
//                //game.listBoard();
//                List<Card> set = new ArrayList<Card>();
//                set = game.listBoard();
//                boolean done = false;
//                do {
//                    System.out.println("Which card would you like to gain? (enter number of card, or 0 for none)");
//                    Input input = new Input();
//                    int selection = input.getInt(0, 17);
//                    if (selection == 0) {
//                        System.out.println("Skipping gain...");
//                        done = true;
//                    } else {
//                        Card c = set.get(selection - 1);
//                        if (game.getRemaining(c) <= 0)
//                            System.out.println("There are no " + c.getName() + "S remaining.");
//                        else {
//                            if (c.getCost() > 4) {
//                                System.out.println(c.getName() + " costs more than 4 coins.");
//                            } else {
//                                game.board.put(c, game.board.get(c) - 1);
//                                player.gain(c);
//                                done = true;
//                            }
//                        }
//                    }
//                } while (done == false);
        }
    }

    public static Card getCard(List<Card> cards, Name name) {
        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).name.equals(name)) {
                return cards.get(i);
            }
        }
        return null;
    }

    public static Card getCardBoard(HashMap<Card, Integer> map, Name name) {
        List<Card> set = new ArrayList<Card>();
        for (Map.Entry<Card, Integer> list : map.entrySet()){
            Card c = list.getKey();
            set.add(c);
        }
        Card c = getCard(set, name);
        return c;
    }

    public static List<Card> filter(Iterable<Card> cards, Type target) {
        List<Card> out = new ArrayList<Card>();
        for (Card c : cards) {
            if (c.getType() == target) {
                out.add(c);
            }
        }
        return out;
    }

    public static void info(Card c) {
        switch (c.type) {
            case TREASURE:
                System.out.print(c.name + " - " + c.type + ": costs " + c.cost + ", worth " + c.value + ".");
                return;
            case VICTORY:
                System.out.print(c.name + " - " + c.type + ":  costs " + c.cost + ", gives " + c.points + " victory points");
                return;
            case ACTION:
                System.out.print(c.name + " - " + c.type + ": costs " + c.cost + " - " + c.text);
        }
    }
}

//        1 REMODEL - ACTION: costs 4 - Trash a card from your hand. Gain a card gosting up to 2 Coins more than the trashed card. (10 remaining)
//        2 MARKET - ACTION: costs 5 - +1 Card. +1 Action. +1 Buy. +1 Coin. (10 remaining)
//        3 WOODCUTTER - ACTION: costs 3 - +1 Buy. +2 Coins. (10 remaining)
//        4 COPPER - TREASURE: costs 0, worth 1. (46 remaining)
//        5 WORKSHOP - ACTION: costs 3 - Gain a card costing up to 4 Coins. (10 remaining)
//        6 ESTATE - VICTORY:  costs 2, gives 1 victory points (8 remaining)
//        7 PROVINCE - VICTORY:  costs 8, gives 6 victory points (8 remaining)
//        8 VILLAGE - ACTION: costs 3 - +1 Card. +2 Actions. (10 remaining)
//        9 GOLD - TREASURE: costs 6, worth 3. (30 remaining)
//        10 MILITIA - ACTION: costs 4 - +2 Coins. Each other player discards down to 3 cards in his hand. (10 remaining)
//        11 SILVER - TREASURE: costs 3, worth 2. (40 remaining)
//        12 MOAT - ACTION: costs 2 - When another player plays an Attack card, you may reveal this from your hand. If you do, you are unaffected by that attack. (10 remaining)
//        13 MINE - ACTION: costs 5 - Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 Coins more; put it into your hand. (10 remaining)
//        14 SMITHY - ACTION: costs 4 - +3 Cards. (10 remaining)
//        15 DUTCHY - VICTORY:  costs 5, gives 3 victory points (8 remaining)
//        16 CURSE - VICTORY:  costs 0, gives -1 victory points (10 remaining)
//        17 CELLAR - ACTION: costs 2 - +1 Action. Discard any number of cards. +1 Card per card discarded. (10 remaining)