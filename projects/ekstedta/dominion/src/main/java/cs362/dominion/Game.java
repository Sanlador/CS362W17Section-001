package cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Game {
    private Random rng = new Random();
    private Integer numPlayers;
    private List<Card> kingdomCards; // set of cards in the supply
    private Map<Card,Integer> supply;  // number of cards left in each supply pile

    private Integer currentPlayer;

    // phases
    // 0 - actions
    // 1 - buys
    private Integer phase;

    // players' cards
    private List<List<Card>> hand; // player -> hand
    private List<List<Card>> deck; // player -> deck
    private List<List<Card>> discard; // player -> discard

    // state for current turn
    private Integer actions; // Starts at 1 each turn
    private Integer coins; // Starts at 0 each turn
    private Integer buys; // Starts at 1 each turn
    private List<Card> playedCards;

    // card-specific state
    private Map<Card,Integer> embargoTokens;

    // constants
    private final int handLimit = 5;

    public Game(Integer numPlayers, List<Card> kingdomCards, Integer randomSeed) {
        if (kingdomCards.size() != 10) {
            throw new GameError("must supply 10 kingdom cards");
        }
        if (!(2 <= numPlayers && numPlayers <= 4)) {
            throw new GameError("numPlayers must be between 2 and 4");
        }

        this.kingdomCards = new ArrayList<>(kingdomCards);
        this.numPlayers = numPlayers;
        this.supply = new HashMap<Card,Integer>();
        this.embargoTokens = new HashMap<Card,Integer>();
        this.currentPlayer = 0;
        this.phase = 0;
        this.actions = 0;
        this.coins = 0;
        this.buys = 0;
        this.playedCards = new ArrayList<Card>();

        this.hand = new ArrayList<List<Card>>(numPlayers);
        this.deck = new ArrayList<List<Card>>(numPlayers);
        this.discard = new ArrayList<List<Card>>(numPlayers);
        for (Integer i = 0; i < numPlayers; i++) {
            this.hand.add(i, new ArrayList<Card>());
            this.deck.add(i, new ArrayList<Card>());
            this.discard.add(i, new ArrayList<Card>());
        }

        // Initialize RNG to a known seed
        rng.setSeed(randomSeed);

        // Initialize supply
        this.supply.put(Card.Curse, 30);
        this.supply.put(Card.Estate, 24);
        this.supply.put(Card.Duchy, 12);
        this.supply.put(Card.Province, 12);

        this.supply.put(Card.Copper, 60);
        this.supply.put(Card.Silver, 40);
        this.supply.put(Card.Gold, 30);

        for (Card c : kingdomCards) {
            if (c == Card.Gardens) {
                if (numPlayers == 2) {
                    this.supply.put(c, 8);
                } else {
                    this.supply.put(c, 12);
                }
            } else {
                this.supply.put(c, 10);
            }
        }

        // Initialize decks
        for (int i = 0; i < numPlayers; i++) {
            for (int j = 0; j < 7; j++) {
                this.deck.get(i).add(Card.Copper);
                decrement(this.supply, Card.Copper);
            }
            for (int j = 0; j < 3; j++) {
                this.deck.get(i).add(Card.Estate);
                decrement(this.supply, Card.Estate);
            }
        }

        // shuffle decks
        for (int i = 0; i < numPlayers; i++) {
            this.shuffle(i);
        }

        // deal starting hands
        for (int i = 0; i < numPlayers; i++) {
            this.draw(i, 5);
        }

        this.startTurn();
    }

    // Shuffle a player's deck.
    // Doesn't touch the discard pile or cards in hand/played.
    public void shuffle(int player) {
        List<Card> deck = this.deck.get(player);
        for (int i = 0; i < deck.size(); i++) {
            int j = i + this.rng.nextInt(deck.size() - i);
            swap(deck, i, j);
        }
    }

    // Swap two elements in an array
    public static <T> void swap(List<T> arr, int i, int j) {
        if (i != j) {
            T a = arr.get(i);
            T b = arr.get(j);
            arr.set(i, b);
            arr.set(j, a);
        }
    }

    // Play an action card with index handPos from current player's hand
    // Can only call this method during the action phase.
    public void playAction(int handPos, Object... choices) {
        List<Card> hand = this.hand.get(this.currentPlayer);
        if (!(0 <= handPos && handPos < hand.size())) {
            throw new GameError("invalid handPos");
        }

        // check if card is an action
        Card card = hand.get(handPos);
        if (!card.isAction()) {
            throw new GameError("not an action");
        }

        // check if right phase
        if (this.phase != 0) {
            throw new GameError("can only play actions during the action phase");
        }

        // move the played card out of the hand
        // don't move to played cards until the effect has happened
        // don't move to discard pile until turn is over
        // XXX changes hand indices (bad)
        hand.remove(handPos);

        // do the action
        // returns true if the card should be trashed
        // (if not, add to played cards)
        if (this.doEffect(card, choices) != TRASH) {
            this.playedCards.add(card);
        }

        // Reeduce number of actions left
        this.actions--;
    }

    private final int TRASH = 1;
    private final int DISCARD = 0;

    // Play the treasure card at position handPos in the current player's hand.
    // Calling this method ends the action phase.
    public void playTreasure(int handPos) {
        List<Card> hand = this.hand.get(this.currentPlayer);
        if (!(0 <= handPos && handPos < hand.size())) {
            throw new GameError("invalid handPos");
        }

        // check if card is a treasure
        Card card = hand.get(handPos);
        if (!card.isTreasure()) {
            throw new GameError("not a treasure card");
        }

        // if not in buy phase, advance the phase
        if (this.phase < 1) {
            this.phase = 1;
        }

        // move card to the played cards pile
        // XXX changes hand indices
        // don't move to discard pile until turn is over
        hand.remove(handPos);
        this.playedCards.add(card);

        this.doEffect(card);
    }

    // Gain a card to the player's hand
    private void takeHand(int player, Card card) {
        decrement(this.supply, card);
        this.hand.get(player).add(card);
    }

    // Gain a card to the player's discard pile
    private void takeDiscard(int player, Card card) {
        decrement(this.supply, card);
        this.discard.get(player).add(card);
    }

    private int doEffect(Card playedCard, Object... choices) {
        List<Card> deck = this.deck.get(this.currentPlayer);
        List<Card> hand = this.hand.get(this.currentPlayer);
        List<Card> discard = this.discard.get(this.currentPlayer);

        this.coins += playedCard.coins();

        if (playedCard == Card.Adventurer) {
            // Reveal cards from your deck until you reveal 2 Treasure cards.
            // Put those Treasure cards into your hand and discard the other revealed cards
            // (Revealed cards are not be discarded until the action is complete.)
            List<Card> revealed = new ArrayList<Card>();
            int treasureCards = 0;
            while (treasureCards < 2 && deck.size() >= 1) {
                Card card = deck.get(deck.size()-1);
                deck.remove(deck.size()-1);
                if (card.isTreasure()) {
                    hand.add(card);
                    treasureCards++;
                } else {
                    revealed.add(card);
                }
            }
            discard.addAll(revealed);
        } else if (playedCard == Card.Ambassador) {
            // Reveal a card from your hand.
            // Return up to 2 copies of it from your hand to the Supply.
            // Then each other player gains a copy of it.
            //
            // Choice 0 - Card - which card to reveal
            // Choice 1 - Integer - how many copies to return to supply (0-2)
            Card card = (Card)choices[0];
            Integer num = (Integer)choices[1];
            if (!hand.contains(card)) {
                throw new GameError("ambassador: you don't have that card");
            }
            if (!(0 <= num && num <= 2)) {
                throw new GameError("ambassador: number of cards to return must be 0-2");
            }
            while (hand.contains(card) && num > 0) {
                hand.remove(card);
                increment(this.supply, card);
                num--;
            }
            for (int i = 0; i < this.numPlayers; i++) {
                if (i != this.currentPlayer && this.supplyCount(card) >= 1) {
                    this.takeDiscard(i, card);
                }
            }
        } else if (playedCard == Card.Baron) {
            // +1 Buy.
            // You may discard an Estate card. If you do, +4 Coins.
            // Otherwise, gain an Estate card.
            //
            // Choice 0 - Boolean - discard estate?
            this.buys++;
            int estatePos = hand.indexOf(Card.Estate);
            if (estatePos >= 0 && (Boolean)choices[0]) {
                this.coins += 4;
                hand.remove(estatePos);
            } else if (this.supply.get(Card.Estate) >= 1) {
                decrement(this.supply, Card.Estate);
                hand.add(Card.Estate);
            }
        } else if (playedCard == Card.CouncilRoom) {
            // +4 Cards; +1 Buy. Each other player draws a card.
            this.draw(this.currentPlayer, 4);
            this.buys += 1;
            for (int i = 0; i < this.numPlayers; i++) {
                if (i != this.currentPlayer) {
                    this.draw(i, 1);
                }
            }
        } else if (playedCard == Card.Cutpurse) {
            // +2 Coins.
            // Each other player discards a Copper card (or reveals a hand with no Copper).
            this.coins += 2;
            for (int i = 0; i < this.numPlayers; i++) {
                if (i != this.currentPlayer) {
                    this.hand.get(i).remove(Card.Copper);
                }
            }
        } else if (playedCard == Card.Embargo) {
            // +2 Coins. Trash this card. Put an Embargo token on top of a Supply pile.
            // When a player buys a card, he gains a Curse card per Embargo token on that pile.
            // Choice 0: card to embargo
            Card card = (Card)choices[0];
            this.coins += 2;
            if (!this.supply.containsKey(card)) {
                throw new GameError("embargo: chosen card is not in the supply");
            }
            increment(this.embargoTokens, card);
            return TRASH;
        } else if (playedCard == Card.Feast) {
            // Trash this card. Gain a card costing up to 5.
            // Choice 0 = card to gain
            Card card = (Card)choices[0];
            if (card.cost() > 5) {
                throw new GameError("feast: gained card must cost 5 or less");
            }
            this.takeDiscard(this.currentPlayer, card);
            return TRASH;
        } else if (playedCard == Card.GreatHall) {
            // +1 Card; +1 Action. Worth 1 Victory
            this.draw(this.currentPlayer, 1);
            this.actions += 1;
        } else if (playedCard == Card.Mine) {
            // Trash a Treasure card from your hand.
            // Gain a Treasure card costing up to 3 Coins more; put it into your hand.
            // Choice 0: index of treasure card to trash
            // Choice 1: card to gain

            int treasurePos = (int)choices[0];
            Card newCard = (Card)choices[1];
            if (!(0 <= treasurePos && treasurePos < this.numHandCards())) {
                throw new GameError("mine: no such card");
            }
            if (!hand.get(treasurePos).isTreasure()) {
                throw new GameError("mine: trashed card must be a treasure");
            }
            if (!newCard.isTreasure()) {
                throw new GameError("mine: gained card must be a treasure");
            }
            if (this.supply.get(newCard) < 1) {
                throw new GameError("mine: there is none of that card left");
            }
            decrement(this.supply, newCard);
            hand.set(treasurePos, newCard);
        } else if (playedCard == Card.Market) {
            // +1 Card; +1 Action; +1 Buy; +1 Coin.
            this.coins += 1;
            this.actions += 1;
            this.buys += 1;
            this.draw(this.currentPlayer, 1);
        } else if (playedCard == Card.Smithy) {
            // +3 Cards
            this.draw(this.currentPlayer, 3);
        } else if (playedCard == Card.Village) {
            // +1 Card; +2 Actions.
            this.draw(this.currentPlayer, 1);
            this.actions += 2;
        } else if (playedCard == Card.Gold || playedCard == Card.Silver || playedCard == Card.Copper) {
            // already added coins above, nothing else to do
        } else {
            System.out.printf("unknown card %s\n", playedCard);
        }
        return DISCARD;
    }

    private static void increment(Map<Card,Integer> map, Card key) {
        int val = 0;
        if (map.containsKey(key)) {
            val = map.get(key);
        }
        map.put(key, val+1);
    }

    private static void decrement(Map<Card,Integer> map, Card key) {
        map.put(key, map.get(key) - 1);
    }

    // Buy a card
    public void buyCard(Card card) {
        if (this.buys < 1) {
            throw new GameError("you have no buys left");
        }
        if (this.supply.get(card) == null || this.supply.get(card) < 1) {
            throw new GameError("there are no more of that card left");
        }
        if (this.coins < card.cost()) {
            throw new GameError("not enough coins");
        }
        if (this.phase != 0 && this.phase != 1) {
            throw new GameError("you aren't in the buy phase");
        }
        this.phase = 1;
        // card goes in the discard pile
        this.discard.get(this.currentPlayer).add(card);
        decrement(this.supply, card);
        this.coins -= card.cost();
        this.buys -= 1;

        // if card is embargoed, give the player a curse
        Integer tokens = this.embargoTokens.get(card);
        if (tokens != null) {
            for (int i = 0; i < tokens; i++) {
                if (this.supplyCount(Card.Curse) > 0) {
                    this.takeDiscard(this.currentPlayer, Card.Curse);
                }
            }
        }
    }

    // How many cards current player has in hand
    public int numHandCards() {
        return this.hand.get(this.currentPlayer).size();
    }

    // Get the card at the given position in the current player's hand
    public Card handCard(int pos) {
        List<Card> hand = this.hand.get(this.currentPlayer);
        if (0 <= pos && pos < hand.size()) {
            return hand.get(pos);
        }
        return null;
    }

    // How many of given card are left in supply
    public int supplyCount(Card card) {
        if (this.supply.containsKey(card)) {
            return this.supply.get(card);
        }
        return 0;
    }

    // Count how many cards of a certain type a player has in their hand
    public int handCount(int player, Card card) {
        List<Card> hand = this.hand.get(player);
        int count = 0;
        for (Card x : hand) {
            if (x == card) {
                count++;
            }
        }
        return count;
    }

    // Count how many cards of a certain type a player has in their hand,
    // deck, and discard pile.
    public int fullDeckCount(int player, Card card) {
        List<Card> hand = this.hand.get(player);
        List<Card> deck = this.deck.get(player);
        List<Card> discard = this.discard.get(player);
        int count = 0;
        for (Card x : hand) {
            if (x == card) {
                count++;
            }
        }
        for (Card x : deck) {
            if (x == card) {
                count++;
            }
        }
        for (Card x : discard) {
            if (x == card) {
                count++;
            }
        }
        return count;
    }

    // End the current player's turn
    // Must do phase C and advance to next player;
    // do not advance whose turn if game is over
    public void endTurn() {
        // discard hand
        this.discard.get(this.currentPlayer).addAll(this.hand.get(this.currentPlayer));
        this.hand.get(this.currentPlayer).clear();

        // move played actions to discard pile
        this.discard.get(this.currentPlayer).addAll(this.playedCards);
        this.playedCards.clear();

        // draw five cards
        this.draw(this.currentPlayer, handLimit);

        // advance player
        this.currentPlayer++;
        if (this.currentPlayer >= this.numPlayers) {
            this.currentPlayer = 0;
        }

        this.startTurn();
    }

    private void startTurn() {
        this.phase = 0;
        this.actions = 1;
        this.buys = 1;
        this.coins = 0;
    }

    private void draw(int player, int n) {
        List<Card> deck = this.deck.get(player);
        List<Card> hand = this.hand.get(player);
        List<Card> discard = this.discard.get(player);

        for (int i = 0; i < n; i++) {
            if (deck.size() < 1) {
                deck.addAll(discard);
                discard.clear();
                this.shuffle(player);
            }
            if (deck.size() < 1) {
                break; // uh oh
            }
            Card card = deck.get(deck.size()-1);
            deck.remove(deck.size()-1);
            hand.add(card);
        }
    }

    public boolean isGameOver() {
        // The game ends when either
        //
        // 1) The province stack is empty
        if (this.supply.get(Card.Province) <= 0) {
            return true;
        }

        // 2) Any three supply stacks are empty
        int empty = 0;
        for (Card c : this.kingdomCards) {
            if (this.supply.get(c) <= 0) {
                empty++;
            }
        }
        return empty >= 3;
    }

    // Scores may be negative
    public int scoreFor(int player) {
        int score = 0;
        int garden = 0;
        int deckCount = 0;
        for (Card c : this.hand.get(player)) {
            score += c.score();
            deckCount++;
            if (c == Card.Gardens) {
                garden++;
            }
        }
        for (Card c : this.discard.get(player)) {
            score += c.score();
            deckCount++;
            if (c == Card.Gardens) {
                garden++;
            }
        }
        for (Card c : this.deck.get(player)) {
            score += c.score();
            deckCount++;
            if (c == Card.Gardens) {
                garden++;
            }
        }
        // Gardens: Worth 1 Victory for every 10 cards in your deck (rounded down). 
        int gardenScore = deckCount / 10;
        score += garden * gardenScore;
        return score;
    }

    /* Accessors */

    public int getCoins() { return this.coins; }
    public int getActions() { return this.actions; }
    public int getBuys() { return this.buys; }
    public int getNumPlayers() { return this.numPlayers; }
    public int getCurrentPlayer() { return this.currentPlayer; }
    public int getPhase() { return this.phase; }

    /* convenience functions */    
    static ArrayList<Card> standardCards() {
        return Card.list(
            Card.Adventurer,
            Card.Baron,
            Card.CouncilRoom,
            Card.Feast,
            Card.Gardens,
            Card.GreatHall,
            Card.Market,
            Card.Mine,
            Card.Smithy,
            Card.Village
        );
    }

    /* methods to support testing */
    // Add a card to the player's hand, and return the index of the card
    int takeForTesting(int player, Card card) {
        this.takeHand(player, card);
        return this.hand.get(player).lastIndexOf(card);
    }

    void reshuffleForTesting(int player) {
        this.deck.get(player).addAll(this.discard.get(player));
        this.discard.get(player).clear();
        this.shuffle(player);
    }

    void printHand(int player) {
        System.out.printf("Player 0 hand:");
        for (Card c : this.hand.get(player)) {
            System.out.printf(" %s", c);
        }
        System.out.printf("\n");
    }

    void printDeck(int player) {
        System.out.printf("Player 0 deck:");
        for (Card c : this.deck.get(player)) {
            System.out.printf(" %s", c);
        }
        System.out.printf("\n");
    }
}
