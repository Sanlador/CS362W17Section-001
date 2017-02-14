import java.util.*;

public final class Card implements Comparable<Card>{
	public enum Type {
		ACTION, TREASURE, VICTORY
	}

	public enum CardName {
		/** The Treasure cards */
		Gold, Silver, Copper,
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Ambassador, Baron, Council_Room, Cutpurse, Embargo, Feast, Gardens, Great_Hall, Mine, Smithy, Village, Remodel
    }

	/**
	// * @param enum CardName the name of the card (GOLD, SILVER, COPPER,
	// * @param enum Type the type of the card (Victory, Treasure, or Kingdom
	// * @param int cost the cost of the card
	// * @param int score the score of the card, when the game is finished
	// * @param treasureValue  the treasure value of the treasure cards (cooper, silver, and gold cards)
	 */
	final Type realType;
	private final CardName cardName;
	int cost, score, treasureValue;
	private Random gen = new Random();

	private Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.realType = type;
		this.cardName = cardName;
	}

	Type getType() {
		return realType;
	}

	CardName getCardName() {
		return cardName;
	}

    int getTreasureValue() {
		return treasureValue; 
	}

	int getCost() {
	    return cost;
	}

	int score() {
			return score; 
		}
	
	static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

        // The Treasure cards
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);
		ret.add(o);

        // The Victory cards
		o = new Card(CardName.Province,Type.VICTORY, 	8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY,		5, 3, 0);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY,		2, 1, 0);
		ret.add(o);		
		o = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0);
		ret.add(o);

        // The Kingdom cards
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
		ret.add(o);
        o = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
        ret.add(o);
        o = new Card(CardName.Baron,Type.ACTION,4,0,0);
        ret.add(o);
        o = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
        ret.add(o);
        o = new Card(CardName.Cutpurse,Type.ACTION,4,0,2);
        ret.add(o);
        o = new Card(CardName.Embargo,Type.ACTION,2,0,2);
        ret.add(o);
        o = new Card(CardName.Feast,Type.ACTION,4,0,0);
        ret.add(o);
        o = new Card(CardName.Gardens,Type.ACTION,4,0,0);
        ret.add(o);
        o = new Card(CardName.Great_Hall,Type.ACTION,3,1,0);//
        ret.add(o);
        o = new Card(CardName.Mine,Type.ACTION,5,0,0);
        ret.add(o);
        o = new Card(CardName.Remodel,Type.ACTION,4,0,0);
        ret.add(o);

        return ret;
	}
	
	void play(Player player, GameState state) {
		
		switch(this.cardName) {
		    case Adventurer: //DONE
	    	    System.out.println("+2 TREASURE Cards.");
	    	    int cardCount = 0;
                //get two treasure cards from deck
                Iterator<Card> it = player.deck.iterator();
                while(it.hasNext()){
                    Card c = it.next();
                    if(c == getCard(state.cards, CardName.Copper) || c == getCard(state.cards, CardName.Silver) || c == getCard(state.cards, CardName.Gold))
                    {
                        cardCount++;
                        player.hand.add(c);
                        it.remove();
                        if(cardCount == 2) break;
                    } else {
                        player.discard(c);
                        it.remove();
                    }
                }
			return;

            case Smithy: //DONE
                System.out.println("+3 Cards.");
                player.drawCard();
                player.drawCard();
                player.drawCard();
                return;

            case Village: //DONE
                System.out.println("+1 Card. +2 Actions.");
                player.drawCard();
                player.numActions = player.numActions + 2;
                return;

            case Ambassador: //DONE
                System.out.println("Give every other player a copy of a Card");
                //Reveal a card from your hand.
                //Return up to 2 copies of it from your hand to the Supply.
                //Then each other player gains a copy of it.
                if(getCard(player.hand, CardName.Curse) != null){
                    player.hand.remove(getCard(player.hand, CardName.Curse));
                    if(getCard(player.hand, CardName.Curse) != null){
                        player.hand.remove(getCard(player.hand, CardName.Curse));
                        state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Curse), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Curse)) + 1 );
                    }
                    state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Curse), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Curse)) + 1 );
                    for(Player p : state.players){
                        if(state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Curse)) > 0) {
                            state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Curse), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Curse)) - 1 );
                            if(p != player) {
                                p.gain(getCard(player.hand, CardName.Curse));
                            }
                        }
                    }
                } else {
                    Card chosen = player.hand.get(0);
                    state.gameBoard.put(chosen, state.gameBoard.get(chosen) + 1);
                    for(Player p : state.players)
                    {
                        if(p != player) {
                            p.gain(chosen);
                            state.gameBoard.put(chosen, state.gameBoard.get(chosen) - 1);
                        }
                    }
                    player.hand.remove(chosen);
                }
                return;

            case Baron: //DONE
                System.out.println("+1 Card. +2 Buys.");
                System.out.println("+4 coins or gains Estate.");
                player.numBuys = player.numBuys + 1;
                if(getCard(player.hand, CardName.Estate) != null) {
                    player.discard(getCard(player.hand, CardName.Estate));
                    player.coins = player.coins + 4;
                } else player.gain(getCard(state.cards, CardName.Estate));
                return;

            case Council_Room: //DONE
                System.out.println("+4 Card. +1 Buys. Every other player +1 Card.");
                player.drawCard();
                player.drawCard();
                player.drawCard();
                player.numBuys++;
                for (Player players : state.players)
                {
                    players.drawCard();
                }
                return;

            case Cutpurse: //DONE
                System.out.println("+2 Coins. Every other player discards Copper.");
                player.coins = player.coins + 2;
                for (Player players : state.players)
                {
                    if(players != player && getCard(players.hand, CardName.Copper) != null) players.discard(getCard(player.hand, CardName.Copper));
                }
                return;

            case Embargo: //DONE - GameState.addEmbargo not working
                System.out.println("+2 Coins. Trash this Card. Place Embargo Token on Supply Pile");
                System.out.println("When a player buys a card, he gains a Curse card per Embargo token on that pile");
                player.coins = player.coins + 2;
                player.playedCards.remove(getCard(player.playedCards, CardName.Embargo));//trashes the Embargo card
                int rand = gen.nextInt(10);
                int card = 0;
                for(Card c : filter(state.cards, Type.ACTION)){
                    if(card == rand) {
                        GameState.addEmbargo(c);
                    }
                }
                return;

            case Feast: //DONE - doesn't buy card just adds 5 coins
                System.out.println("Trash this Card. Gain a card costing up to 5 coins.");
                player.playedCards.remove(getCard(player.playedCards, CardName.Feast));
                player.playTreasureCard();
                player.coins = player.coins + 5;
                //buy card up to 5 coins.
                //Player.buyCard(state);
                return;

            case Great_Hall: //DONE
                System.out.println("+1 Card. +1 Action. Worth 1 VICTORY point.");
                player.drawCard();
                player.numActions++;
                return;

            case Mine: //DONE
                System.out.println("Trash a Treasure Card. Gain Next Level of Treasure Card ");
                if(getCard(player.hand, CardName.Silver) != null) {
                    player.hand.remove(getCard(player.hand, CardName.Silver));
                    player.gain(getCard(state.cards, CardName.Gold));
                } else if(getCard(player.hand, CardName.Copper) != null) {
                    player.hand.remove(getCard(player.hand, CardName.Copper));
                    player.gain(getCard(state.cards, CardName.Silver));
                    //decrement from gameBoard lolz
                }
                return;

            case Remodel: //DONE --- only adds coins, doesn't buy a card right away (will probably need to make another buyCard(coin)
                System.out.println("Trash a Card. Gain Card costing up to 2 more than Trashed Card");
                int cost = player.hand.get(0).getCost();
                System.out.println("Player trashes: " + player.hand.get(0));
                player.hand.remove(0);
                player.playTreasureCard();
                player.coins = player.coins + 2 + cost;
                //player buys card (cost + 2);
                return;

            default:
        }
	}

	static Card getCard(List<Card> cards,CardName cardName) {
        for (Card card : cards) {
            if (card.cardName.equals(cardName))
                return card;
        }
		return null;
	}

	static List<Card> filter(Iterable<Card> cards, Type target) {
	      List<Card> out = new ArrayList<Card>();
	      for (Card c : cards)
	         if (c.getType() == target)
	            out.add(c);
	      return out;
	}

	@Override
	public String toString() {
        return ("\t" + this.getCardName());
	}

	@Override
	public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (obj instanceof Card) if (cardName.equals(((Card) obj).cardName)) return true;
        return false;
    }

	public int compareTo(Card o) {
		return cardName.compareTo(o.cardName);
	}
}