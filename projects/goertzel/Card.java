package goertzel;




import goertzel.Player;

import static goertzel.Card.CardType.*;
import static goertzel.Card.CardName.*;

public class Card {
    public enum CardType {
        VICTORY, TREASURE, ACTION
    }
    public enum CardName {
        PROVINCE, DUCHY, ESTATE, CURSE,
        GOLD, SILVER, COPPER,
        ADVENTURER, AMBASSADOR, BARON, COUNCIL_ROOM,
        CUTPURSE, EMBARGO, FEAST, GARDENS, GREAT_HALL,
        MINE, SMITHY, SALVAGER, VILLAGE
    }

    final CardType type;
    final CardName name;
    final int cost;
    final int treasureVal;
    final int victoryPoints;
    final int actInc;
    final int cardInc;
    final int goldInc;
    final int buyInc;

    private Card(CardName name, CardType type, int[] stats) {
        this.type = type;
        this.name = name;
        this.cost = stats[0];
        this.treasureVal = stats[1];
        this.victoryPoints = stats[2];
        this.actInc = stats[3];
        this.cardInc = stats[4];
        this.goldInc = stats[5];
        this.buyInc = stats[6];
    }

    Card(CardName name) {
        this(name, matchType(name), stats(name));
    }



    void play(Player player) {
        if (this.type != TREASURE)
            System.out.println("\t\t" + player.getPlayerName() + " played " + this.name);
        if (this.type == ACTION)
            player.decActions();
        switch (name) {
            // Victory Cards
            case PROVINCE: case DUCHY: case ESTATE: case CURSE: player.discard(this) ;break;// Doesn't get played

            // Treasure Cards - increase player's gold amount
            case GOLD: case SILVER: case COPPER:
                player.incGold(goldInc);
                player.discard(this);
                break;

            case ADVENTURER:
                // Reveal cards from your deck until you reveal 2 Treasure cards.
                // Put those Treasure cards into your hand and discard the other revealed cards.
                int revealedCount = 0;
                while (revealedCount < 2){
                    Card temp = player.revealCardFromDeck();
                    if (temp.type == TREASURE) {
                        player.putInHand(temp);
                        revealedCount++;
                    }
                    else player.discard(temp);
                }
                player.discard(this);
                break;

            case AMBASSADOR:
                // Reveal a card from your hand.
                // Return up to 2 copies of it from your hand to the Supply.
                // Then each other player gains a copy of it.
                Card revealed = player.randCardFromHand();
                System.out.println("\t\t" + player.getPlayerName() + " revealed " + revealed.name);

                player.returnCard(revealed.name);
                player.returnCard(revealed.name);

                for (Player p : player.boardReference.players)
                    if (p != player) p.gain(revealed.name, 10);
                player.discard(this);
                break;

            case BARON:
                //+1 Buy
                // You may discard an Estate for +4 Gold. If you don't, gain an Estate.
                player.incBuys(buyInc);
                Card maybeEstate = player.handHasCard(ESTATE);
                if (maybeEstate != null) {
                    System.out.println("\t\t" + player.getPlayerName() + " discarded ESTATE");
                    player.discard(maybeEstate);
                    player.incGold(4);
                }
                else player.gain(ESTATE, 99);
                player.discard(this);
                break;

            case COUNCIL_ROOM:
                // +4 Cards
                // +1 Buy
                // Each other player draws a card.
                player.drawCards(4);
                player.incBuys(buyInc);

                for (Player p : player.boardReference.players)
                    if (p != player) p.drawCards(1);
                player.discard(this);
                break;

            case CUTPURSE:
                // +2 Gold
                // Each other player discards a Copper card (or reveals a hand with no Copper).
                player.incGold(goldInc);

                for (Player p : player.boardReference.players) {
                    if (p != player) {
                        Card hasCopper = p.handHasCard(COPPER);
                        if (hasCopper != null) p.discard(hasCopper);
                        else p.revealHand();
                    }
                }
                player.discard(this);
                break;

            case EMBARGO:
                // +2 Gold
                // Trash this card. Put an Embargo token on top of a Supply pile.
                // When a player buys a card, he gains a Curse card per Embargo token on that pile.
                player.incGold(goldInc);
                player.trashCard(this);

                Supply_Stack toEmbargo = player.getRandomSupply();
                int loopPrevention = 0;
                while ((toEmbargo.isEmbargoed()) && (loopPrevention < 10)){
                    toEmbargo = player.getRandomSupply();
                    loopPrevention++;
                }
                if (loopPrevention >= 30){
                    System.out.println("\t\t" + player.getPlayerName() + " could not embargo any supply");
                }
                else {
                    System.out.println("\t\t" + player.getPlayerName() + " embargoed " + toEmbargo.getName());
                    toEmbargo.embargo();
                }
                break;

            case FEAST:
                // Trash this card.
                // Gain a card costing up to 5 Gold.
                player.trashCard(this);
                player.gainRandom(5);
                break;

            case GARDENS:
                // Worth 1 VP per 10 cards you have (round down).
                break;

            case GREAT_HALL:
                // +1 Card, +1 Action. Worth 1 VP
                player.drawCards(1);
                player.incActions(1);
                player.discard(this);
                break;

            case MINE:
                // You may trash a Treasure from your hand.
                // Gain a Treasure to your hand costing up to 3 Gold more than it.
                Card trashed = player.trashTreasure();
                if (trashed != null){
                    player.mineGain(trashed.cost+3);
                }
                player.discard(this);
                break;

            case SMITHY:
                // +3 Cards
                player.drawCards(3);
                player.discard(this);
                break;
            case SALVAGER:
                // +1 Buy
                // Trash a card from your hand. +Gold equal to its cost.
                player.incBuys(1);
                Card temp = player.randCardFromHand();
                player.trashCard(temp);
                player.incGold(temp.cost);
                player.discard(this);
                break;

            case VILLAGE:
                // +1 Card, +2 Actions
                player.drawCards(1);
                player.incActions(actInc);
                player.discard(this);
                break;
        }
    }

    static CardType matchType(CardName name) {
        switch (name) {
            case PROVINCE:
            case DUCHY:
            case ESTATE:
            case CURSE:
            case GARDENS:       return VICTORY;
            case GOLD:
            case SILVER:
            case COPPER:        return TREASURE;
            case ADVENTURER:
            case AMBASSADOR:
            case BARON:
            case COUNCIL_ROOM:
            case CUTPURSE:
            case EMBARGO:
            case FEAST:
            case GREAT_HALL:
            case MINE:
            case SMITHY:
            case SALVAGER:
            case VILLAGE:       return ACTION;
            default: return null;
        }
    }

    static int[] stats(CardName name) {
        switch (name) {         //Cost, Treasure, VP, ActInc, CardInc, GoldInc, BuyInc
            case PROVINCE:      return new int[] {8, 0, 6, 0, 0, 0, 0};
            case DUCHY:         return new int[] {5, 0, 3, 0, 0, 0, 0};
            case ESTATE:        return new int[] {3, 0, 1, 0, 0, 0, 0};
            case CURSE:         return new int[] {0, 0, -1,0, 0, 0, 0};
            case GOLD:          return new int[] {6, 3, 0, 0, 0, 3, 0};
            case SILVER:        return new int[] {3, 2, 0, 0, 0, 2, 0};
            case COPPER:        return new int[] {0, 1, 0, 0, 0, 1, 0};
            case ADVENTURER:    return new int[] {6, 0, 0, 0, 0, 0, 0};
            case AMBASSADOR:    return new int[] {3, 0, 0, 0, 0, 0, 0};
            case BARON:         return new int[] {4, 0, 0, 0, 0, 0, 1};
            case COUNCIL_ROOM:  return new int[] {5, 0, 0, 0, 4, 0, 1};
            case CUTPURSE:      return new int[] {4, 0, 0, 0, 0, 2, 0};
            case EMBARGO:       return new int[] {2, 0, 0, 0, 0, 2, 0};
            case FEAST:         return new int[] {4, 0, 0, 0, 0, 0, 0};
            case GARDENS:       return new int[] {4, 0, 0, 0, 0, 0, 0};
            case GREAT_HALL:    return new int[] {3, 0, 0, 1, 1, 0, 0};
            case MINE:          return new int[] {5, 0, 0, 0, 0, 0, 0};
            case SMITHY:        return new int[] {4, 0, 0, 0, 3, 0, 0};
            case SALVAGER:      return new int[] {4, 0, 0, 0, 0, 0, 1};
            case VILLAGE:       return new int[] {3, 0, 0, 2, 1, 0, 0};
            default:            return new int[] {0, 0, 0, 0, 0, 0, 0};
        }
    }


    public String toString() {
        return String.format("%s\t\t%s\t\tCost: %d \tVP: %d\tTr_Val: %d", name, type, cost, victoryPoints, treasureVal);
    }

}
