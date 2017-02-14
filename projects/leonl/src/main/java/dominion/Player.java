package dominion;

import java.util.*;

public class Player implements Cloneable{
	ArrayList<Card> hand;// int hand[MAX_PLAYERS][MAX_HAND];
	LinkedList<Card> deck;// int deck[MAX_PLAYERS][MAX_DECK];
	List<Card> discard; // int discard[MAX_PLAYERS][MAX_DECK];
	ArrayList<Card> playedCards;
	private Random gen;

	String player_username;
	int numActions;
	int numBuys;
	int coins;
	private final GameState gameState;

	
	Player(GameState gameState,String player_username) {
		this.player_username = player_username;
		this.gameState = gameState;
        discard = new ArrayList<Card>();
        playedCards = new ArrayList<Card>();
        deck = new LinkedList<Card>();
        hand = new ArrayList<Card>();
        gen = new Random();
    }

	final Card drawCard() {
		if (deck.isEmpty()) {// Deck is empty
			// Step 1 Shuffle the discard pile back into a deck
			System.out.println("\nReshuffle the deck of the player "
					+ player_username + " to draw FIVE cards");
			while (discard.size() > 0) {
				int ndx = gen.nextInt(discard.size());
				// Move discard to deck
				deck.add(discard.remove(ndx));
			}
		}
		Card toDraw = deck.poll();//card at top of deck
		// Add card to hand and hand count automatically will be incremented since we use List
		hand.add(toDraw);
		System.out.println(player_username + " draws" + toDraw);
		Collections.sort(hand);
		return toDraw;
	}
	
	final void initializePlayerTurn() {
		numActions = 1;
		coins = 0;
		numBuys = 1;
		//Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck. Draw the top
		//5 cards as your starting hand
		for (int i = 0; i < 5; i++) {
			drawCard();
		}
	}

	 //card goes in discard,
	final boolean gain(Card card) {
		discard.add(card);
		System.out.println(this.player_username + " gains " + card);
		return true;
	}

	//Discard hand
	void discard(Card card) {
		hand.remove(card);
		discard.add(card);
		System.out.println(player_username + " discards " + card);
	}

	void playKingdomCard() {
		while (numActions > 0) {
			List<Card> actionCards = Card.filter(hand, Card.Type.ACTION);
			Iterator<Card> it = actionCards.iterator();
			while(it.hasNext()){
				Card c = it.next();
				if(c == Card.getCard(gameState.cards, Card.CardName.Gardens)){
					it.remove();
				}
			}
			if (actionCards.size() == 0)
				return;

			Card c = actionCards.get(0);

            if (c == null)
				return;
			System.out.println("Player.actionPhase Card:" + c.toString());

			playedCards.add(c);
			hand.remove(c);

			numActions -= 1;

			c.play(this, gameState);//plays the kingdom card
		}
	}

	final int scoreFor() {
		int numGardens = 0;
		int numCards = 0;
		int score = 0;
		//score from hand
		for (Card c : hand)
			score += c.score();
			numCards++;
		//score from discard
		for (Card c : discard){
			if(Card.getCard(discard, Card.CardName.Gardens) != null) {
				if(c == Card.getCard(discard ,Card.CardName.Gardens)) numGardens++;
			}
			score += c.score();
			numCards++;
		}
		//score from deck
		for (Card c : deck){
			if(Card.getCard(deck, Card.CardName.Gardens) != null){
				if(c == Card.getCard(discard ,Card.CardName.Gardens)) numGardens++;
			}
			score += c.score();
			numCards++;
		}
		//System.out.println("numGardens: " + numGardens);
		if(numGardens > 0){
			score += numGardens*(numCards/10);
		}

		return score;
	}
	   
	void playTreasureCard() {
		List<Card> treasureCards = Card.filter(hand, Card.Type.TREASURE);

		if(treasureCards.size() == 0) return;

		System.out.println("Treasure Cards Played:");
		for(Card c: treasureCards)
		{
			coins += c.getTreasureValue();
			System.out.println(c.toString());
			playedCards.add(c);
			hand.remove(c);
		}
		System.out.println(player_username + "'s COINS: " + coins + "\n");
	}

    void buyCard(GameState state) {
		while(numBuys > 0 && coins > 0) {
			if (coins == 0){
				gain(Card.getCard(state.cards, Card.CardName.Cooper));
				state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Cooper), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Cooper)) - 1 );
			}
			else if (coins == 1) {
				gain(Card.getCard(state.cards, Card.CardName.Cooper));
				state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Cooper), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Cooper)) - 1 );
			}
			else if (coins == 2) {
			    int rand = gen.nextInt(2);
				if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Embargo)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Embargo)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Embargo));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Embargo), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Embargo)) - 1);
					coins = coins - 2;
				} else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Estate)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Estate)) > 0) {
                    gain(Card.getCard(state.cards, Card.CardName.Estate));
                    state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Estate), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Estate)) - 1);
                    coins = coins - 2;
                }
			}
			else if (coins == 3) {//randomly choose 3 cost cards?
				int rand = gen.nextInt(4);
				if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Village)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Village)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Village));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Village), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Village)) - 1);
					coins = coins - 3;
				} else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Ambassador)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Ambassador)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Ambassador));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Ambassador), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Ambassador)) - 1);
					coins = coins - 3;
				} else if (rand == 2 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Great_Hall)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Great_Hall)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Great_Hall));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Great_Hall), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Great_Hall)) - 1);
					coins = coins - 3;
				} else if (rand == 3 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Silver)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Silver)) > 0) {
                    gain(Card.getCard(state.cards, Card.CardName.Silver));
                    state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Silver), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Silver)) - 1);
                    coins = coins - 3;
                }

			} else if (coins == 4) {//randomly choose one of these
                int rand = gen.nextInt(6);
				if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Smithy)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Smithy)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Smithy));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Smithy), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Smithy)) - 1);
					coins = coins - 4;
				} else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Baron)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Baron)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Baron));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Baron), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Baron)) - 1);
					coins = coins - 4;
				} else if (rand == 2 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Cutpurse)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Cutpurse)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Cutpurse));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Cutpurse), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Cutpurse)) - 1);
					coins = coins - 4;
				} else if (rand == 3 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Feast)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Feast)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Feast));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Feast), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Feast)) - 1);
					coins = coins - 4;
				} else if (rand == 4 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Steward)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Steward)) > 0) {
                    gain(Card.getCard(state.cards, Card.CardName.Steward));
                    state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Steward), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Steward)) - 1);
                    coins = coins - 4;
                } else if (rand == 5 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Gardens)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gardens)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Gardens));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Gardens), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gardens)) - 1);
					coins = coins - 4;
				}
			} else if (coins == 5) {//randomly choose one of these
                int rand = gen.nextInt(3);
				if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Mine)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Mine)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Mine));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Mine), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Mine)) - 1);
					coins = coins - 5;
				} else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Council_Room)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Council_Room)) > 0) {
                    gain(Card.getCard(state.cards, Card.CardName.Council_Room));
                    state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Council_Room), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Council_Room)) - 1);
                    coins = coins - 5;
                } else if (rand == 2 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Duchy)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Duchy)) > 0) {
                    gain(Card.getCard(state.cards, Card.CardName.Duchy));
                    state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Duchy), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Duchy)) - 1);
                    coins = coins - 5;
                }
			} else if (coins == 6) {
			    int rand = gen.nextInt(2);
				if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Gold)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gold)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Gold));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Gold), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gold)) - 1);
					coins = coins - 6;
				} else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Adventurer)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Adventurer)) > 0) {
                    gain(Card.getCard(state.cards, Card.CardName.Adventurer));
                    state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Adventurer), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Adventurer)) - 1);
                    coins = coins - 6;
                }
			} else if (coins == 7) {
                if (state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Gold)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gold)) > 0) {
                    gain(Card.getCard(state.cards, Card.CardName.Gold));
                    state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Gold), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gold)) - 1);
                    coins = coins - 6;
                }
			} else if (coins >= 8){
				if (state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Province)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Province)) > 0) {
					gain(Card.getCard(state.cards, Card.CardName.Province));
					state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Province), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Province)) - 1);
					coins = coins - 8;
				}
		    }
			numBuys--;
		}
	}

	final void endTurn() {
		coins = 0;
		numActions = 1;
		numBuys = 1;
		for(Card c: playedCards)
		{
			discard.add(c);
		}
		for(Card c: hand)
		{
			discard.add(c);
		}
		playedCards.clear();
		hand.clear();
		drawCard();
		drawCard();
		drawCard();
		drawCard();
		drawCard();
	}

	void printStateGame() {
		System.out.println(" --- " + this.player_username + " --- ");
		System.out.println(" --- --------------------------- --- ");
		System.out.println(this.gameState.toString());
		System.out.println(" --- --------------------------- --- ");
	}

	@Override
	public String toString() {
        String sb = ("\n------- " + this.player_username + " -------") +
                "\nnumActions: " + this.numActions +
                ", coins: " + this.coins +
                ", numBuys: " + this.numBuys +
                "\nHand: " + this.hand +
                "\nDiscard: " + this.discard +
                "\nDeck: " + this.deck +
                "\nPlayed Cards: " + this.playedCards +
                "\n";

        return sb;
	}
	   
	protected Player clone() throws CloneNotSupportedException {
		Player clonePlayer = (Player) super.clone();
		clonePlayer.hand = new ArrayList<Card>(hand);// int hand[MAX_PLAYERS][MAX_HAND];
		clonePlayer.deck = new LinkedList<Card>(deck);// int
		// deck[MAX_PLAYERS][MAX_DECK];
		clonePlayer.discard = new ArrayList<Card>(discard); // int
		// discard[MAX_PLAYERS][MAX_DECK];
		clonePlayer.playedCards = new ArrayList<Card>(playedCards);
		return clonePlayer;
	}
}
