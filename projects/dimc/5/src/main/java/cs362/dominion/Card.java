package dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public final class Card implements Comparable<Card>{
	public enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** The Treasure cards */
		Gold, Silver, Copper, 
		/** The Victory cards */
		Province,Duchy,Estate,Curse,
		/** The Kingdom cards */
		Adventurer, Smithy, Village, Ambassador, Baron, CouncilRoom, CutPurse, Bazaar, MoneyLender, Gardens, GreatHall, Market, SeaHag;
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

	Card(CardName cardName, Type type, int cost, int score, int treasureValue) {
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
        
        public int getCost() {
		return cost;
	}

	public int getTreasureValue() { return treasureValue; }
	public int score(Player player) { return score; }
	
	static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		/** The Treasure cards  */
		Card o = new Card(CardName.Gold, Type.TREASURE, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.TREASURE, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Copper, Type.TREASURE, 0, 0, 1);
		ret.add(o);

		/** The Victory cards  */ 
		o = new Card(CardName.Province,Type.VICTORY, 	8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY,		5, 1, 0);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY,		2, 3, 0);
		ret.add(o);		
		o = new Card(CardName.Curse,Type.VICTORY,		0, -1, 0);
		ret.add(o);				
		/** The Kingdom cards , it should more than 10 cards*/ 	
		
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
		o = new Card(CardName.CouncilRoom,Type.ACTION,5,0,0);
		ret.add(o);	
		o = new Card(CardName.CutPurse,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Bazaar,Type.ACTION,5,0,0);
		ret.add(o);	
		o = new Card(CardName.MoneyLender,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Gardens,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.GreatHall,Type.ACTION,3,0,0);
		ret.add(o);	
		o = new Card(CardName.Market,Type.ACTION,5,0,0);
		ret.add(o);
		o = new Card(CardName.SeaHag,Type.ACTION,4,0,0);
		ret.add(o);	
		return ret;
	}
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName) {
                    
                //Kingdom Cards
		case Adventurer:
                    int count=0;
                    
	    	  //Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand and discard the other revealed cards.
                    while(count!=2){
                        Card cur = player.drawCard();
                        
                        if(cur.getType()==Type.TREASURE){
                            count++;                                       
                        }
                        else{
                            player.discard(cur);
                        }
                    }
                
			return;
                        
		case Smithy:
	         player.drawCard();
	         player.drawCard();
	         player.drawCard();
			return;
			
		case Village:
			player.drawCard();
			player.numActions+=2;
			return;
                        
                case Ambassador:
                    //Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.
                    Card cur = player.hand.get(0);
                    CardName name = cur.getCardName();
                    player.hand.remove(cur);
                    int val=state.gameBoard.get(Card.getCard(state.cards, name))+1;
                    state.gameBoard.put(Card.getCard(state.cards, name), val);                    
                    count = 1;
                    int i=0;
                    while(count<2){
                        if(cur.equals(player.hand.get(i))){
                            count++;
                            player.hand.remove(cur);
                            state.gameBoard.put(Card.getCard(state.cards, name), val+1);
                        }
                        else{
                            i++;
                        }
                    }
                    for(i=0;i<state.players.size();i++){
                        if(player.player_username.equals(state.players.get(i).player_username)==false){
                            state.players.get(i).gain(Card.getCard(state.cards, name));
                        }                    
                    }
                    val=state.gameBoard.get(Card.getCard(state.cards, name))-state.players.size();
                    state.gameBoard.put(Card.getCard(state.cards, name), val);
			return;
			
                case Baron:
                    //+1 Buy, You may discard an Estate card. If you do, +$4. Otherwise, gain an Estate card.
                    player.numBuys+=1;
                    boolean notFound=true;
                    i=0;                       
                    while(notFound||i<player.hand.size()){      
                        cur=player.hand.get(i);
                        if(cur.cardName==CardName.Estate){
                            player.discard(cur);
                            player.coins+=4;
                            notFound=false;
                        }  
                        i++;
                    }
                    if(notFound==false){
                        player.gain(Card.getCard(state.cards,Card.CardName.Estate));
                        val=state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Estate))-1;
                        state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Estate), val);
                        
                    }                      
			return;
                        
                case CouncilRoom:
                    //+4 Cards; +1 Buy, Each other player draws a card.
                    player.numBuys+=1;
                    for(i=0;i<4;i++){
                        player.drawCard();
                    }
                    for(i=0;i<state.players.size();i++){
                        if(player.player_username.equals(state.players.get(i).player_username)==false){
                            state.players.get(i).drawCard();
                        }                    
                    }       		
			return;
                
                case CutPurse:
                    //+$2, Each other player discards a Copper card (or reveals a hand with no Copper).
                    player.coins+=2;
                    for(i=0;i<state.players.size();i++){
                        if(player.player_username.equals(state.players.get(i).player_username)==false){                           
                            notFound=true;
                            int j=0;                       
                            while(notFound||j<state.players.get(i).hand.size()){       
                                cur=state.players.get(i).hand.get(j);
                                if(cur.cardName==CardName.Copper){
                                    state.players.get(i).discard(cur);                                   
                                    notFound=false;
                                }  
                                j++;
                            }
                        }                    
                    }   
			return;
                        
                case Bazaar:
                    //+1 Card; +2 Actions, +$1
                    player.drawCard();
                    player.numActions+=2;
                    player.coins+=1;
			return;
                        
                case MoneyLender:
                    //Trash a Copper from your hand. If you do, +$3
                    notFound=true;
                    i=0;
                    while(notFound||i<player.hand.size()){
                        cur=player.hand.get(i);
                        if(cur.cardName==CardName.Copper){
                            player.hand.remove(cur);
                            notFound=false;
                            player.coins+=3;
                        }
                        i++;
                    }			
			return;
                        
                case Gardens:
                    //Worth 1 Victory for every 10 cards in your deck (rounded down)
                    count=(int)player.deck.size();
                   // player.vp+=count/10;   //??????????????????????			
			return;
                        
                case GreatHall:
                    //1 Victory Point +1 Card; +1 Action.
                    player.vp+=1;
                    player.numActions+=1;
                    player.drawCard();
			return;
                        
                case Market:
                    //+1 Card; +1 Action; +1 Buy; +$1
                    player.drawCard();
                    player.numActions+=1;
                    player.numBuys+=1;
                    player.coins+=1;
			
			return;
                        
                case SeaHag:
                    //Each other player discards the top card of his deck, then gains a Curse card, putting it on top of his deck
                    for(i=0;i<state.players.size();i++){
                        if(player.player_username.equals(state.players.get(i).player_username)==false){                           
                            Card move = state.players.get(i).deck.get(0);
                            state.players.get(i).deck.remove(0);
                            state.players.get(i).discard.add(move);
                            state.players.get(i).deck.add(0,Card.getCard(state.cards,Card.CardName.Curse));
                            val=state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Curse))-1;
                            state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Curse), val);
                        }                    
                    }
			
			return;
                        
                        
                //Victory Cards
                case Province:
			
			return;
                        
                case Duchy:
			
			return;
                        
                case Estate:
			
			return;
                        
                case Curse:
			
			return;
                        
                        
                //Treasure Cards
                case Gold:
                    player.coins-=3;			
			return;
                        
                case Silver:
                    player.coins-=2;
			return;
                        
                case Copper:
                    player.coins-=1;
			return;
                        
                
                        
                   
			
		default: return;
		}
	}

	public static Card getCard(List<Card> cards,CardName cardName) {
		for(int i=0; i<cards.size();i++){
			if(cards.get(i).cardName.equals(cardName))
					return 	cards.get(i);
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

		sb.append(" \t" + this.getCardName());
		sb.append("-" + this.getType() + " ");
		sb.append("\t\t Cost: " + this.cost + " ");
		sb.append("\t\t Score: " + this.score + " ");
		sb.append("\tTreasure Value: " + this.treasureValue);

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


}
