package org.cs362.dominion;
import java.util.*;



public final class Card implements Comparable<Card>, Cloneable{
	public enum Type {
		ACTION, TREASURE, VICTORY
	}
	public enum CardName {
		/** The Treasure cards */
		Gold, Silver, Copper,
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer,  Ambassador, Baron, Council_Room, Cutpurse, Embargo, Feast, Gardens, Great_Hall, Mine, Minion, Smithy, Village,
	}
	/**
	 * @param enum CardName the name of the card (GOLD, SILVER, COPPER, 
	 * @param enum Type the type of the card (Victory, Treasure, or Kingdom
	 * @param int cost the cost of the card
	 * @param int score the score of the card, when the game is finished
	 * @param treasureValue  the treasure value of the treasure cards (cooper, silver, and gold cards)
	 */
	private final Type realType;
	private final CardName cardName;
	private final int cost, score, treasureValue;

	private Random ran=new Random();

	private Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.realType = type;
		this.cardName = cardName;
	}

	/**
	 * Returns the type
	 */
	public Type getType() {
		return realType;
	}

	/**
	 * Returns the cardName
	 */
	public CardName getCardName() {
		return cardName;
	}
	/**
	 * Returns the cost
	 */
	public int getCost() {
		return cost;
	}


	/**
	 * Returns the treasureValue
	 */
	public int getTreasureValue() {
		return treasureValue; 
	}
	public int score() { 
			return score; 
		}
	
	public static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		/** The Treasure cards  */
		Card Gold = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);

		Card Silver = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);

		Card Copper = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);


		/** The Victory cards  */ 
		Card Province = new Card(CardName.Province,Type.VICTORY, 	8, 6, 0);
		Card Duchy = new Card(CardName.Duchy,Type.VICTORY,		5, 3, 0);
		Card Estate = new Card(CardName.Estate,Type.VICTORY,		2, 1, 0);
		Card Curse = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0);
		/** The Kingdom cards , it should more than 10 cards*/
		Card Adventurer = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		Card Smithy = new Card(CardName.Smithy,Type.ACTION,4,0,0);
        Card Ambassador = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
        Card Baron = new Card(CardName.Baron,Type.ACTION,4,0,0);
        Card Council_Room = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
        Card Cutpurse = new Card(CardName.Cutpurse,Type.ACTION,4,0,2);
        Card Embargo = new Card(CardName.Embargo,Type.ACTION,2,0,2);
        Card Gardens = new Card(CardName.Gardens,Type.ACTION,4,0,0);
        Card Great_Hall = new Card(CardName.Great_Hall,Type.ACTION,3,1,0);//
        Card Mine = new Card(CardName.Mine,Type.ACTION,5,0,0);
        Card Minion = new Card(CardName.Minion,Type.ACTION,5,0,0);
		Card Feast = new Card(CardName.Feast,Type.ACTION,4,0,0);
		Card Village = new Card(CardName.Village,Type.ACTION,3,0,0);
		//13 card selected
		ret.add(Gold);
		ret.add(Silver);
		ret.add(Copper);
		ret.add(Province);
		ret.add(Duchy);
		ret.add(Estate);
		ret.add(Curse);
		ret.add(Adventurer);
		ret.add(Smithy);
		ret.add(Ambassador);
		ret.add(Baron);
		ret.add(Council_Room);
		ret.add(Cutpurse);
		ret.add(Embargo);
		ret.add(Gardens);
		ret.add(Great_Hall);
		ret.add(Mine);
		ret.add(Minion);
		ret.add(Feast);
		ret.add(Village);

        return ret;
	}
	
	public void play(Player player, GameState state) {

		switch (this.cardName) {
			case Adventurer:
				int count = 0;
				//Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
				 System.out.println("TODO Adventurer Code******************************************");
				 System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
				 		"Put those Treasure cards into player hand and discard the other revealed cards.");
				Iterator<Card> Iter = player.deck.iterator();
				while (Iter.hasNext()) {
					Card newCard = Iter.next();
					if (newCard == getCard(state.cards, CardName.Gold) || newCard == getCard(state.cards, CardName.Silver) || newCard == getCard(state.cards, CardName.Copper)) {
						count = count + 1;
						player.hand.add(newCard);
						Iter.remove();
						if (count == 2) {
							break;
						}
					} else {
						player.discard(newCard);
					}
				}
				return;

			case Smithy:
				System.out.println("TODO Smithy Code******************************************");
				player.drawCard();
				player.drawCard();
				player.drawCard();
				return;

			case Village:
				System.out.println("TODO Village Code******************************************");
				System.out.println("The player draw +1 Card.");
				System.out.println("The player gets +2 play Actions.");
				player.drawCard();
				player.numActions += 2;
				return;

		    case Ambassador:
			System.out.println("TODO Ambassador Code******************************************");
    		if(player.hand.size() > 0){
                Card c= player.hand.get(0);
                CardName cardname = c.getCardName();
                state.gameBoard.put(getCard(player.hand, cardname), state.gameBoard.get(getCard(state.cards,cardname)) +1);
                for (Player p : state.players) {
                    if(p!=player) {
                            player.gain(getCard(state.cards,cardname));
                        state.gameBoard.put(getCard(state.cards, cardname), state.gameBoard.get(getCard(state.cards,cardname)) -1);
                    }
                }
            }
                return;

		case Baron:
			System.out.println("TODO Baron Code******************************************");
			System.out.println("The player draw +1 Card.");
			System.out.println("The player gets +2 play Actions.");
			System.out.println("Get four coins or Get Card Estate!");
			if(getCard(player.hand, CardName.Estate)!=null){
				player.discard(getCard(player.hand, CardName.Estate));
				player.coins+=4;
			}
			else{
				player.gain(getCard(state.cards, CardName.Estate));
			}

			return;

		case Council_Room:
			System.out.println("TODO Council_Room Code******************************************");
			System.out.println("The player draw +4 Card.");
			System.out.println("The player gets +1 play Buy.");
			System.out.println("Other Players draw one card.");
			for(int i =0; i<4;i++){
				player.drawCard();
				}
				player.numBuys+=1;
				for(Player players : state.players){
					players.drawCard();
				}
			return;

		case Cutpurse:
			System.out.println("TODO Cutpurse Code******************************************");
			System.out.println("The player gets +2 coins, and discard copper!");
            player.coins+=2;
            for(Player players : state.players){
            	if(players!=player&&getCard(players.hand,CardName.Copper)!=null){
            		player.discard(getCard(player.hand, CardName.Copper));
				}
			}
			return;

        	case Embargo:
			int NewCardNum = 0;
			int newRandom=ran.nextInt(10);
			System.out.println("TODO Embargo Code******************************************");
			System.out.println("The player gets +2 coins and discard a card.");
			System.out.println("Whenever players buy a card, get a curse card per Embargo card.");
			player.coins+=2;
			player.playedCards.remove(getCard(player.playedCards, CardName.Embargo));

			for(Card newCard : filter(state.cards, Type.ACTION)){
				if(NewCardNum==newRandom){
					GameState.addEmbargo(newCard);
				}
			}
			return;

		case Gardens:
			System.out.println("TODO Gardens Code******************************************");
			System.out.println("10 cards in the deck (Round down), get one score!");
			return;

		case Great_Hall:
			System.out.println("TODO Great_Hall Code******************************************");
			System.out.println("The player draw +1 Card.");
			System.out.println("The player gets +1  action.");
            System.out.println("The player gets +1  score.");
			player.drawCard();
			player.numActions+=1;
			

			return;

		case Mine:
			System.out.println("TODO Mine Code******************************************");
			System.out.println("Player discards a treasure card and gains a better one.");
			if(getCard(player.hand, CardName.Silver) != null) {
				player.hand.remove(getCard(player.hand, CardName.Silver));
				player.gain(getCard(state.cards, CardName.Gold));
			}
			else if(getCard(player.hand, CardName.Copper) != null) {
				player.hand.remove(getCard(player.hand, CardName.Copper));
				player.gain(getCard(state.cards, CardName.Silver));
			}
				return;

		case Minion:
			System.out.println("TODO Minion Code******************************************");
			System.out.println("The player gets +1  action.");
            int y = ran.nextInt(2);
            if(y==0 || player.hand==null) {
                player.coins+=2;
            }
            else {
                for(int i=0; i<player.hand.size();i++) {
                    player.discard(player.hand.get(i));
                }
                for (int i=0; i<4;i++){
                player.drawCard();
                }
                for(Player p : state.players) {
                    if(p.hand.size()>4) {
                        for(int i=0; i<p.hand.size();i++) {
                            player.discard(p.hand.get(i));
                        }
                        for (int i=0; i<4;i++){
                            player.drawCard();
                            }
                    }
                }
            }
            return;

		case Feast:
			System.out.println("TODO Feast Code******************************************");
			System.out.println("Discard this card and gain a card costing up to 5.");
			player.playedCards.remove(getCard(player.playedCards, CardName.Feast));
			player.coins +=5;
			return;
		default: return;
		}
	}

	public static Card getCard(List<Card> cards,CardName cardName) {
		for(int i=0; i<cards.size();i++){
			if(cards.get(i).cardName.equals(cardName))
					return cards.get(i);
		}
			return null;		
	}

	   public static List<Card> filter(Iterable<Card> cards, Type target) {
		      List<Card> out = new ArrayList<Card>();
		      for (Card c : cards)
		         if (c.getType() == target)
		            out.add(c);
		      return out;
		   }

	// this method will print out all the constant cards ENUM
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(" \n\t" + this.getCardName());
//		sb.append("-" + this.getType() + " ");
//		sb.append("\t\t Cost: " + this.cost + " ");
//		sb.append("\t\t Score: " + this.score + " ");
//		sb.append("\tTreasure Value: " + this.treasureValue);

		return sb.toString();

	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(!(obj instanceof Card)) return false;
		if(cardName.equals(((Card) obj).cardName)) return true;

		return false;//cardName.equals(((Card) obj).cardName);
	}


	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    } 

}
