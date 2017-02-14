import java.util.*;

public class Card implements Comparable<Card> {
	public static enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		Gold, Silver, Copper, Province, Duchy, Estate, Curse, Adventurer, Smithy, Village, Ambassador, Baron, Council_Room, Cutpurse, Embargo, Feast, Gardens, Great_Hall, Mine, Remodel;
	}
	final Type cardType;
	private CardName cardName;
	int cost, score, treasureValue;
	private Random gen = new Random();

	private Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.cardType = type;
		this.cardName = cardName;
	}
	
	public Type getType() {
		return cardType;
	}

	public CardName getCardName() {
		return cardName;
	}

	public int getCost() {
		return cost;
	}
	
	public int getTreasureValue() {
		return treasureValue;
	}
	
	public int getScore() {
		return score;
	}
	
	public static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		
		o = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);
		ret.add(o);
		
		o = new Card(CardName.Province,Type.VICTORY, 	8, 6, 0);
		ret.add(o);
		
		o = new Card(CardName.Duchy,Type.VICTORY,		5, 1, 0);
		ret.add(o);		
		
		o = new Card(CardName.Estate,Type.VICTORY,		2, 3, 0);
		ret.add(o);		
		
		o = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0);
		ret.add(o);				
		
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
        
		o = new Card(CardName.Great_Hall,Type.ACTION,3,1,0);
        ret.add(o);
        
		o = new Card(CardName.Mine,Type.ACTION,5,0,0);
        ret.add(o);
        
		o = new Card(CardName.Remodel,Type.ACTION,4,0,0);
        ret.add(o);		
		
		return ret;
	}
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName) {
		case Adventurer:
			 int cardCount = 0;
			 Iterator<Card> it = player.deck.iterator();
			 while(it.hasNext()) {
				 Card c = it.next();
				 if(c == getCard(state.cards, CardName.Copper) || c == getCard(state.cards, CardName.Silver) || c == getCard(state.cards, CardName.Gold)) {
					 cardCount++;
					 player.hand.add(c);
					 it.remove();
					 if(cardCount == 2) 
						 break;
				 }
				 else 
					 player.discard(c);
			 }
			 System.out.println("Draw 2 Treasure cards");
			 return;
			 
		case Smithy:
	        player.drawCard();
	        player.drawCard();
	        player.drawCard();
			System.out.println("Draw 3 cards.");
			return;
			
		case Village:
			player.drawCard();
			player.numActions = player.numActions + 2;
			System.out.println("Draw 1 card, and take 2 additional actions");
			return;
			
		case Ambassador:
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
            } 
			else {
                Card chosen = player.hand.get(0);
                state.gameBoard.put(chosen, state.gameBoard.get(chosen) + 1);
                for(Player p : state.players) {
                    if (p != player) {
					p.gain(chosen);
                    state.gameBoard.put(chosen, state.gameBoard.get(chosen) - 1);
					}
				}
                player.hand.remove(chosen);
            }
			System.out.println("Give every other player a copy of a card from your hand");
			return;
			 
		case Baron:
			player.drawCard();
			player.numActions = player.numActions + 2;
			if(getCard(player.hand, CardName.Estate) != null) {
				player.discard(getCard(player.hand, CardName.Estate));
				player.coins = player.coins + 4;
            } 
			else {	
				player.gain(getCard(state.cards, CardName.Estate));
            }
			System.out.println("Draw 1 card and take 2 additional actions");
			System.out.println("Gain 4 coins or gain an Estate");
			return;
			
		case Council_Room:
			player.drawCard();
            player.drawCard();
            player.drawCard();
            player.numBuys++;
            for (Player players : state.players)
            {
                players.drawCard();
            }
            System.out.println("Draw 4 cards");
            System.out.println("Gain 1 extra buy");
            System.out.println("Every other player draws 1 card.");
            return;
			
		case Cutpurse:
			for (Player players : state.players) {
				if(players != player && getCard(players.hand, CardName.Copper) != null) 
					 players.discard(getCard(player.hand, CardName.Copper));
            }
			player.coins = player.coins + 2;
            System.out.println("Gain 2 Coins.");
            System.out.println("Every other player discards a Copper Card.");
            return;
			
		case Embargo:
			player.coins = player.coins + 2;
            player.hand.remove(getCard(player.hand, CardName.Embargo));
			int rand = gen.nextInt(10);
			int card = 0;
			for (Card c : filter(state.cards, Type.ACTION)) {
				if (card == rand) {
					GameState.addEmbargo();
				}
			}
            System.out.println("Gain 2 Coins.");
            System.out.println("The Embargo card is trashed");
            System.out.println("Gain Embargo token on Supply Pile");
            System.out.println("When a player buys a card, they gain a Curse card per Embargo token on that pile");
            return;
			
		case Feast:
			player.playedCards.remove(getCard(player.playedCards, CardName.Feast));
			player.coins = player.coins + 5;
            System.out.println("The feast card is trashed");
            System.out.println("Gain a card costing at most 5 coins");
            return;
			
		case Gardens:
			System.out.println("Worth 1 VICTORY point for every 10 cards the player has.");
            return;
			
		case Great_Hall:
			player.drawCard();
            player.numActions++;
            System.out.println("Worth 1 VICTORY point.");
            System.out.println("Draw 1 card.");
            System.out.println("Gain 1 action.");
            return;
			
		case Mine:
			if(getCard(player.hand, CardName.Silver) != null) {
				player.hand.remove(getCard(player.hand, CardName.Silver));
                player.gain(getCard(state.cards, CardName.Gold));
            } 
			else if(getCard(player.hand, CardName.Copper) != null) {
                player.hand.remove(getCard(player.hand, CardName.Copper));
                player.gain(getCard(state.cards, CardName.Silver));
            }
            System.out.println("Trash a Treasure card.");
            System.out.println("Gain a Treasure card one level higher than the trashed card.");
            return;
			
		case Remodel:
			int cost = player.hand.get(0).getCost();
            System.out.println("Card trashed: " + player.hand.get(0));
			player.hand.remove(0);
			player.coins = player.coins + 2 + cost;
            System.out.println("The player trashes the Card.");
            System.out.println("The player gains a card costing up to more than trashed Card.");
            return;
			
		default:
		}
	}

	static Card getCard(List<Card> cards, CardName cardName) {
		for (Card card : cards) {
			if (card.cardName.equals(cardName)) {
				return card;
			}
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
		if(this == obj) 
			return true;
		if(!super.equals(obj)) 
			return false;
		if(obj instanceof Card) {
			if (cardName.equals(((Card) obj).cardName))
				return true;
		}
			
		return false;
	}
	
	public int compareTo(Card o) {
		return cardName.compareTo(o.cardName);
	}
}
