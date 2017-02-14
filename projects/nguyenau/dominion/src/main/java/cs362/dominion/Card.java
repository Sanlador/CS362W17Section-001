package cs362.dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Collections;

public final class Card implements Comparable<Card>{
	public enum Type {
		ACTION, TREASURE, VICTORY;
	}

	public static enum CardName {
		/** Treasure Cards **/
		Gold, Silver, Cooper,
		
        /** Victory Cards */
		Province, Duchy, Estate, Curse, Gardens,
		
        /** Kingdom Cards **/
		Adventurer, Smithy, Village, Cutpurse, Feast, Ambassador, Baron, CouncilRoom, Embargo, GreatHall, Mine, Minion;
	}
	
    /**
	 * @param enum CardName the name of the card (GOLD, SILVER, cooper, 
	 * @param enum Type the type of the card (Victory, Treasure, or Kingdom
	 * @param int cost the cost of the card
	 * @param int score the score of the card, when the game is finished
	 * @param treasureValue  the treasure value of the treasure cards (cooper, silver, and gold cards)
	 */
	
    private final Type realType;
	private final CardName cardName;
	private final int cost, score, treasureValue;
    private int EmTokens;

	Card(CardName cardName,Type type,int cost,int score,int treasureValue) {
		this.cost = cost;
		this.score = score;
		this.treasureValue = treasureValue;
		this.realType = type;
		this.cardName = cardName;
        this.EmTokens = 0;
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
    
    public void setEmTokens(int newTokens) {
        this.EmTokens = newTokens;
    }
    public int getEmTokens() { return EmTokens; }
    public int getCost() { return cost; }
	public int getTreasureValue() { return treasureValue; }
	public int score() { return score; }
	
	static List<Card> createCards() {
		List<Card> ret = new LinkedList<Card>();

		/** Treasure cards  **/
		Card o = new Card(CardName.Gold,Type.TREASURE,6,0,3);
		ret.add(o);
		o = new Card(CardName.Silver,Type.TREASURE,3,0,2);
		ret.add(o);
		o = new Card(CardName.Cooper,Type.TREASURE,0,0,1);
		ret.add(o);

		/** Victory cards  **/
		o = new Card(CardName.Province,Type.VICTORY,8,6,0);
		ret.add(o);
		o = new Card(CardName.Duchy,Type.VICTORY,5,1,0);
		ret.add(o);		
		o = new Card(CardName.Estate,Type.VICTORY,2,3,0);
		ret.add(o);		
		o = new Card(CardName.Curse,Type.VICTORY,0,-1,0);
		ret.add(o);
        o = new Card(CardName.Gardens,Type.VICTORY,4,0,0);
        ret.add(o);
        
        /** Kingdom cards, at least 10 **/
		o = new Card(CardName.Adventurer,Type.ACTION,6,0,0);
		ret.add(o);
		o = new Card(CardName.Smithy,Type.ACTION,4,0,0);
		ret.add(o);	
		o = new Card(CardName.Village,Type.ACTION,3,0,0);
        ret.add(o);
        o = new Card(CardName.Cutpurse,Type.ACTION,4,0,0);
        ret.add(o);
        o = new Card(CardName.Feast,Type.ACTION,4,0,0);
        ret.add(o);
        o = new Card(CardName.Ambassador,Type.ACTION,3,0,0);
        ret.add(o);
        o = new Card(CardName.Baron,Type.ACTION,4,0,0);
        ret.add(o);
        o = new Card(CardName.GreatHall,Type.ACTION,3,1,0);
        ret.add(o);
        o = new Card(CardName.Embargo,Type.ACTION,2,0,0);
        ret.add(o);
        o = new Card(CardName.CouncilRoom,Type.ACTION,5,0,0);
        ret.add(o);
        o = new Card(CardName.Mine,Type.ACTION,5,0,0);
        ret.add(o);
        o = new Card(CardName.Minion,Type.ACTION,5,0,0);
        ret.add(o);
        
		return ret;
	}
	
	public void play(Player player, GameState state) {
		
		switch(this.cardName) {
            case Adventurer:
                System.out.println("\n[ADVENTURER]");
                System.out.println("- Draw and reveal cards from deck until 2 Treasure cards are drawn.\n" +
	    	  		"- Add revealed Treasure cards to hand, discarding the remainder.\n");
                
                int treasuresRevealed = 0;
                Card cardDrawn = player.drawCard();
                while(cardDrawn != null) {
                    if(cardDrawn.realType == Type.TREASURE) {
                        treasuresRevealed += 1;
                    }
                    else {
                        player.discard(cardDrawn);
                    }
                    if(treasuresRevealed == 2)
                        break;
                    cardDrawn = player.drawCard();
                }
                System.out.println("\n");
                return;
		
            case Smithy:
                System.out.println("\n[SMITHY]");
                System.out.println("- " + player.playerName + " draws +3 Cards.\n");
                
                for (int i=0;i<4;i++) {
                    player.drawCard();
                }
                /** intended bug: player draws 4 cards instead of 3 **/
                System.out.println("\n");
                return;
                
            case Village:
                System.out.println("\n[VILLAGE]");
                System.out.println("- " + player.playerName + " draws +1 Card.");
                System.out.println("- " + player.playerName + " gets +2 Actions.\n");
                
                player.drawCard();
                player.actions += 2;
                return;
                
            case Cutpurse:
                System.out.println("\n[CUTPURSE]");
                System.out.println("- " + player.playerName + " gets +2 Coins.");
                System.out.println("- All other players discard 1 Copper if able.\n");
                
                player.coins += 2;
                Card hC = new Card(CardName.Cooper,Type.TREASURE,0,0,1);
                for(Player p : state.players) {
                    if(p.playerName != player.playerName)
                        p.discard(hC);
                }
                return;
            
            case Feast:
                System.out.println("\n[FEAST]");
                System.out.println("- " + player.playerName + " trashes this card and gains a card up to 5 Coins in value from the supply.");
                
                List<Card> reshuffledCards = new ArrayList<Card>(state.cards);
                Collections.shuffle(reshuffledCards);
                for(Card feastCard : reshuffledCards) {
                    if((feastCard.getCost() > 0) && (feastCard.getCost() < 6)) {
                        if((int)state.gameBoard.get(feastCard) != 0) {
                            player.discard.add(feastCard);
                            System.out.println(player.playerName+" has gained "+feastCard.getCardName()+".\n");
                            state.gameBoard.put(feastCard,state.gameBoard.get(feastCard)-1);
                        }
                        return;
                    }
                }
                return;
            
            case Ambassador:
                System.out.println("\n[AMBASSADOR]");
                System.out.println("- " + player.playerName + " returns up to 2 Cards of the same name from their hand back to the supply.");
                System.out.println("- All other players get a copy of the card(s) returned (from the supply).");
                
                player.hand.remove(this);       // ensures that THIS copy of ambassador (played) is not considered part of the hand
                
                Card ambassadorReturn = player.hand.get(Randomness.nextRandomInt(player.hand.size()));
                int numReturned = 0, occurences = 0;
                
                for(Card c : player.hand) {
                    if(c.getCardName() == ambassadorReturn.getCardName()) {
                        occurences += 1;
                    }
                }
                
                if(occurences > 2)          // we can maximum discard up to 2 of these cards
                    occurences = 2;
                
                for(int i=0;i<occurences;i++) {
                    player.hand.remove(ambassadorReturn);
                    state.gameBoard.put(ambassadorReturn,state.gameBoard.get(ambassadorReturn)+1);
                    System.out.println(player.playerName+" has returned "+ambassadorReturn.getCardName()+" to the supply.");
                }
                
                for(Player p : state.players) {
                    if(state.gameBoard.get(ambassadorReturn) > 0) {
                        p.discard.add(ambassadorReturn);
                        state.gameBoard.put(ambassadorReturn,state.gameBoard.get(ambassadorReturn)-1);
                        System.out.println(p.playerName+" has gained "+ambassadorReturn.getCardName()+".");
                    }
                    else {
                        System.out.println("No more " + ambassadorReturn.getCardName() + " cards left, so no further players can get any.");
                        break;
                    }
                }
                
                // *** intended bug ***: all players gain a copy of card put back into the supply, even the player who returned copies of it!
                
                return;
                
            case Baron:
                System.out.println("\n[BARON]");
                System.out.println("- " + player.playerName + " gets +1 Buy.");
                System.out.println("- " + player.playerName + " can discard an Estate for +4 Coins, or gain an Estate from the supply.\n");
                
                player.actions += 1;
                Card baronCard = getCard(state.cards,CardName.Estate);
                
                // Baron will always be used to +4 Coins unless there is no estate in the hand.
                if(player.hand.contains(baronCard)) {
                    player.discard(baronCard);
                    player.coins += 4;
                    System.out.println(player.playerName+" has gained +4 Coins.\n");
                    return;
                }
                
                // This will execute if there are no Estates in the hand, to grant an Estate
                if((int)state.gameBoard.get(baronCard) > 0) {
                    player.discard.add(baronCard);
                    state.gameBoard.put(baronCard,state.gameBoard.get(baronCard)-1);
                    System.out.println(player.playerName+" has gained "+baronCard.getCardName()+".\n");
                    return;
                }
                
                System.out.println("No estates on the board and no estates in the hand. Baron does nothing.\n");
                return;
                
            case CouncilRoom:
                System.out.println("\n[COUNCIL ROOM]");
                System.out.println("- " + player.playerName + " draws +4 Cards.");
                System.out.println("- " + player.playerName + " gets +1 Buy.");
                System.out.println("- Other players draw +1 Card.\n");
                
                for(int i=0;i<4;i++) {
                    player.drawCard();
                }
                player.buys += 1;
                
                // Draw for all other players
                // ***intended bug*** current player also draws a card!
                for(Player p : state.players) {
                    p.drawCard();
                }
                System.out.println("\n");
                return;
            
            case Embargo:
                System.out.println("\n[EMBARGO]");
                System.out.println("- Trash this card, putting am Embargo token on a stack in the supply.");
                System.out.println("- If a player buys a card from that stack, they will also gain a curse per each Embargo token.\n");
                
                int selectRandom = Randomness.nextRandomInt(state.cards.size());
                
                Card c = state.cards.get(selectRandom);
                int supplyNum = state.gameBoard.get(c);
                c.setEmTokens(c.getEmTokens()+1);
                state.gameBoard.put(c,supplyNum);
                
                c = state.cards.get(selectRandom);
                System.out.println(c.getCardName() + " now has " + c.getEmTokens() + " Embargo tokens.\n");
                
                return;
        
            case GreatHall:
                System.out.println("\n[GREAT HALL]");
                System.out.println("- " + player.playerName + " draws +1 Cards.");
                System.out.println("- " + player.playerName + " gains +1 Action.");
                
                player.drawCard();
                player.actions += 1;
                return;
            
            case Mine:
                System.out.println("\n[MINE]");
                System.out.println("- Trash a Treasure and gain a Treasure worth up to +3 Coins more than it.\n");
                
                List<Card> mineCard = filter(player.hand,Type.TREASURE);
                List<Card> mineGetCard = filter(state.cards,Type.TREASURE);
                if((mineCard.size() > 0) && (mineGetCard.size() > 0)) {
                    for(Card mineRemove : mineCard) {
                        for(Card mineGet : mineGetCard) {
                            if((state.gameBoard.get(mineGet) > 0) && (mineGet.getCost() <= (mineRemove.getCost()+3))){
                                player.hand.remove(mineRemove);
                                System.out.println(player.playerName+" has trashed "+mineRemove.getCardName()+".");
                                player.hand.add(mineGet);
                                state.gameBoard.put(mineGet,state.gameBoard.get(mineGet)-1);
                                System.out.println(player.playerName+" has gained "+mineGet.getCardName()+".\n");
                                return;
                            }
                        }
                    }
                }
                System.out.println("Either no treasures were found in hand or no buyable treasures left on the board. Mine does nothing.\n");
                return;
            
            case Minion:
                System.out.println("\n[MINION]");
                System.out.println("- " + player.playerName + " gets +1 Action.");
                System.out.println("- CHOICE:\n"+"--- "+player.playerName+" gets +2 Coins.\n--- "+player.playerName+" discards their hand, draws +4 Cards, and all other players with 5 or more cards in their hand does the same.\n");
                player.actions += 1;
                
                int minionChoice = Randomness.nextRandomInt(1);
                player.hand.remove(getCard(state.cards,CardName.Minion));
                List<Card> selectedHand = new ArrayList<Card>(player.hand);
                
                if(minionChoice == 0) {
                    for(Card handCard : selectedHand) {
                        player.discard(handCard);
                    }
                    for(int i=0;i<4;i++) {
                        player.drawCard();
                    }
                    for(Player otherPlayer : state.players) {
                        if(otherPlayer.playerName != player.playerName) {
                            selectedHand = new ArrayList<Card>(otherPlayer.hand);
                            for(Card handCard : selectedHand) {
                                otherPlayer.discard(handCard);
                            }
                            for(int i=0;i<4;i++) {
                                otherPlayer.drawCard();
                            }
                        }
                    }
                }
                else {
                    player.coins += 2;
                }
                System.out.println("\n");
                return;
                
            default:
                return;
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
    public static List<Card> filterName(Iterable<Card> cards, CardName name){
		   List<Card> out = new ArrayList<Card>();
		   for(Card c : cards)
			   if(c.getCardName() == name)
				   out.add(c);
		   return out;
	   }
	
    // INFORMATIONAL METHODS
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
		return cardName.compareTo(o.cardName);
	}
}
