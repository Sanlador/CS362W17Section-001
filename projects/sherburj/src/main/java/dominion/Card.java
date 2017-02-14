package dominion;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public final class Card implements Comparable<Card>, Cloneable{

    static List<Card> filter(List<Card> hand, CardName cardName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
	public static enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold, Silver, Copper, 
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Smithy, Bureaucrat, Cellar, Chancellor,
                Council_Room, Chapel, Feast, Festival, Gardens, 
                Laboratory, Woodcutter, Market, Militia, Village, Advtest;
	}
	/**
	 * @param enum CardName the name of the card (GOLD, SILVER, COPPER, 
	 * @param enum Type the type of the card (Victory, Treasure, or Kingdom
	 * @param int cost the cost of the card
	 * @param int score the score of the card, when the game is finished
	 * @param treasureValue  the treasure value of the treasure cards (copper, silver, and gold cards)
	 */
	private final Type realType;
	private final CardName cardName;
	private final int cost, score, treasureValue;

	private Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.realType = type;
		this.cardName = cardName;
	}

	/**
	 * Returns the type
     * @return 
	 */
	public Type getType() {
		return realType;
	}

	/**
	 * Returns the cardName
     * @return 
	 */
	public CardName getCardName() {
		return cardName;
	}
	/**
	 * Returns the cost
     * @return 
	 */
	public int getCost() {
		return cost;
	}


	/**
	 * Returns the treasureValue
     * @return 
	 */
	public int getTreasureValue() {
		return treasureValue; 
	}
	public int score() { 
			return score; 
		}
	
	public static List<Card> createCards() {
		List<Card> ret = new LinkedList<>();

		/** The Treasure cards  */
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Copper, Type.TREASURE, 1, 0, 1);
		ret.add(o);

		/** The Victory cards  */ 
		o = new Card(CardName.Province,Type.VICTORY, 8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY,5, 3, 0);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY,2, 1, 0);
		ret.add(o);		
		o = new Card(CardName.Curse,Type.VICTORY,0, -1, 0);
		ret.add(o);				
		/** The Kingdom cards , it should more than 10 cards*/ 	
		
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
		ret.add(o);
                o = new Card(CardName.Bureaucrat,Type.ACTION,4,0,0);
		ret.add(o);
                o = new Card(CardName.Cellar,Type.ACTION,2,0,0);
		ret.add(o);
                o = new Card(CardName.Chancellor,Type.ACTION,3,0,0);
		ret.add(o);
                o = new Card(CardName.Chapel,Type.ACTION,2,0,0);
		ret.add(o);
                o = new Card(CardName.Council_Room,Type.ACTION,5,0,0);
		ret.add(o);
                o = new Card(CardName.Feast,Type.ACTION,4,0,0);
		ret.add(o);
                o = new Card(CardName.Festival,Type.ACTION,5,0,0);
		ret.add(o);
                o = new Card(CardName.Gardens,Type.ACTION,4,0,0);
		ret.add(o);
                o = new Card(CardName.Laboratory,Type.ACTION,5,0,0);
		ret.add(o);
                o = new Card(CardName.Market,Type.ACTION,5,0,0);
		ret.add(o);
                o = new Card(CardName.Militia,Type.ACTION,4,0,0);
		ret.add(o);
                o = new Card(CardName.Woodcutter,Type.ACTION,3,0,0);
		ret.add(o);
		return ret;
	}
	
	public void play(Player player, GameState state) {
		int count =0;
		switch(this.cardName) {
		case Adventurer:
                    Card Ihatejava = new Card(CardName.Advtest,Type.TREASURE,0,0,0);
                    for(count =0; count <2; count++){
                        player.drawType(Ihatejava);
                    }
		return;
                
		case Smithy:
	         player.drawCard();
	         player.drawCard();
	         player.drawCard();
			return;
                        
                case Bureaucrat:
                    String name = player.player_username;
                    player.gain(Card.getCard(state.cards, Card.CardName.Silver));
                    Card Ihatejava2 = new Card(CardName.Advtest,Type.VICTORY,0,0,0);
                    for (Player players : state.players){
                        if(player.player_username != name)
                             player.discardType(Ihatejava2);
                    }
		return;
                        
                 case Cellar:
                    player.numActions = player.numActions + 1;
                    int y = player.hand.size();
                    int randomCellar = (int)  Randomness.random.nextInt(y-1);
                        while(randomCellar>0){
                            Card c = player.hand.get(0);
                            player.discard(c);
                            randomCellar--;
                        }
		return;
                        
                case Chancellor:
                    int randomChancel = (int)  Randomness.random.nextInt(4);
                    player.coins = player.coins + 2;
                    if(randomChancel == 0){
                        while(!player.hand.isEmpty()){
                            Card c = player.hand.get(0);
                            player.discard(c);
                        }
                    }
                return;
                        
                
                case Chapel:
                    player.trash(player.hand.get(0));
		return;
                        
                          
                case Council_Room:
                    player.drawCard();
                    player.drawCard();
                    player.drawCard();
                    player.drawCard();
                    player.numBuys = player.numBuys +1;
                    String name2 = player.player_username;
                    for (Player players : state.players){
                        if(player.player_username != name2)
                            players.drawCard();
                    }
		return;
                        
                case Feast:
                    Card c = new Card(CardName.Feast,Type.ACTION,4,0,0);
                    player.trash(c);
                    player.numBuys = player.numBuys+1;
                    player.tempcoins = player.tempcoins + 5;
		return;
                        
                case Festival:
                    player.numActions = player.numActions + 2;
                    player.numBuys = player.numBuys+1;
                    player.coins = player.coins+2;
		return;
                        
                case Laboratory:
                    player.drawCard();
                    player.drawCard();
                    player.numActions = player.numActions + 1;
		return;
                        
                case Militia:
                     player.coins = player.coins+2;
                     String name3 = player.player_username;
                     for (Player players : state.players){
                        while(player.hand.size() > 3){
                            if(player.player_username != name3){
                             Card x = players.hand.get(0);
                             player.discard(x);
                            }
                        }
                     }
		return;
                        
                case Woodcutter:
                    player.numBuys = player.numBuys+1;
                    player.coins = player.coins+2;
		return;
                        
                case Market:
                     player.drawCard();
                     player.numActions = player.numActions + 1;
                     player.numBuys = player.numBuys+1;
                     player.coins = player.coins+1;
		return;     
			
		case Village:
                    player.drawCard();
                    player.numActions = player.numActions + 2;
	
		default:		}
	}

	public static Card getCard(List<Card> cards,CardName cardName) {
		for(int i=0; i<cards.size();i++){
			if(cards.get(i).cardName.equals(cardName))
					return 	cards.get(i);
		}
		
		return null;
		
		
		
	}

	   public static List<Card> filter(Iterable<Card> cards, Type target) {
		      List<Card> out = new ArrayList<>();
		      for (Card c : cards)
		         if (c.getType() == target)
		            out.add(c);
		      return out;
		   }
	// this method will print out all the constant cards ENUM
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append(" \n\t").append(this.getCardName());
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

		return cardName.equals(((Card) obj).cardName);
	}

	@Override
	public int compareTo(Card o) {
		// TODO Auto-generated method stub
		return cardName.compareTo(o.cardName);
	}
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    } 

}
