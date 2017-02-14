
package dominion_v2;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
//Much of this was given to us in class by Ali Aburas

public class Card{
	List<Card> deck = new LinkedList<Card>();
	
	public enum CardName
	{
		Gold, Silver, Copper,
		Province, Dutchy, Estate, Curse,
		Adventurer, Bureaucrat, Cellar, Chancellor, Chapel, CouncilRoom, Feast, Festival, Laboratory, Library,
		Market, Smithy, Woodcutter;
	}
	
	public enum Type
	{
		Action, Treasure, Victory;
	}
	
	private int cost;
	private int score;
	private int value;
	private Type type;
	private CardName cardName;

	Card(CardName cardName, Type type, int cost, int score, int value)
	{
		this.cardName = cardName;
		this.type = type;
		this.cost = cost;
		this.score = score;
		this.value = value;
		
	}
	
	public Type getType()
	{
		return type;
	}
	
	public CardName getCardName()
	{
		return cardName;
	}
	
	public int getTreasure()
	{
		return value;
	}
	
	public int playerScore(Player player)
	{
		return score;
	}
	
	static List<Card> createCards()
	{
		List<Card> ret = new LinkedList<Card>();
		
		// treasure Cards
		Card o = new Card(CardName.Gold, Type.Treasure, 6, 0, 3);
		ret.add(o);
		o = new Card(CardName.Silver, Type.Treasure, 3, 0, 2);
		ret.add(o);
		o = new Card(CardName.Copper, Type.Treasure, 0, 0, 1);
		ret.add(o);
		
		//Victory Cards
		o = new Card(CardName.Province,Type.Victory, 8, 6, 0);
		ret.add(o);
		o = new Card(CardName.Dutchy,Type.Victory, 5, 1, 0);
		ret.add(o);
		o = new Card(CardName.Estate,Type.Victory, 2, 3, 0);
		ret.add(o);
		o = new Card(CardName.Curse,Type.Victory, 0, -1, 0);
		ret.add(o);
		
		//Kingdom Cards
		o = new Card(CardName.Adventurer, Type.Action, 6, 0, 0);
		ret.add(o);
		o = new Card(CardName.Bureaucrat, Type.Action, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Cellar, Type.Action, 2, 0, 0);
		ret.add(o);
		o = new Card(CardName.Chancellor, Type.Action, 3, 0, 0);
		ret.add(o);
		o = new Card(CardName.Chapel, Type.Action, 2, 0, 0);
		ret.add(o);
		o = new Card(CardName.CouncilRoom, Type.Action, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Feast, Type.Action, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Festival, Type.Action, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Laboratory, Type.Action, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Library, Type.Action, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Market, Type.Action, 5, 0, 0);
		ret.add(o);
		o = new Card(CardName.Smithy, Type.Action, 4, 0, 0);
		ret.add(o);
		o = new Card(CardName.Woodcutter, Type.Action, 3, 0, 0);
		ret.add(o);
		
		return ret;
	}
//  this gonna be the good one	
	public void play(Player player, GameState state)
	{
		switch(this.cardName){
		case Gold:
			player.coins += 3;
			return;
				
		case Silver:
			player.coins += 2;
			return;
		
		case Copper:
			player.coins +=1;
			return;
			
			
		case Adventurer:
			System.out.println("Reveal cards from the player deck until player reveal 2 Treasure cards. " +
				"Put those Treasure cards into player hand and discard the other revealed cards.");
			return;
				
		case Bureaucrat:
			player.gain(Card.getCard(GameState.cards, Card.CardName.Silver));
			System.out.println("Gain a Silver card, put it on top of your deck. Each other player reveals, " +
				"a Victory card from his hand and puts it on his deck (or reveals a hand with no victory cards)");
			//Player.gain(Card.getCard(GameState.cards, Card.CardName.Silver));	
			return;
			
		case Cellar:	
			player.numActions += 1;
			System.out.println("Discard any number of cards, +1 per card discarded");
			System.out.println("Player chose not to discard any cards.");
			return;
			
		case Chancellor:
			player.coins += 2;
			System.out.println("You may immediately put your deck into the discard pile.");
			System.out.println("Player Decided not to.");
			return;
			
		case Chapel:
			List<Card> vicCards = Card.filter(player.hand, Type.Victory);
			if(vicCards.size() == 0)
			{
				return;
			}
			for(int i = 0; i < vicCards.size(); i++)
			{
				Card c = (Card)vicCards.get(i);
				if(c == null)
				{
					return;
				}
				player.discard(c);
				
			}	
			
			System.out.println("Trash up to 4 cards from your hand");
			//System.out.println("Player discards nothing");
			
			return;	
				
		case CouncilRoom:
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.drawCard();
			player.numBuys += 1;
			System.out.println("Each other player draws a card");
			return;
		
		case Feast:
			System.out.println("Trash this card. Gain a card costing up to 5");
			player.coins += 5;
			return;
		
		
		case Festival:
			player.numActions += 2;
			player.numBuys += 1;
			player.coins += 2;
			return;
		
		case Laboratory:
			player.drawCard();
			player.drawCard();
			player.numActions += 1;
			return;
		
		case Library:
			System.out.println("Draw until you have 7 cards in hand. You may set aside any Action cards" 
			  + " drawn this way, as you draw them, discard the set aside cards after you finish drawing");
			int x = player.hand.size();
			if(x == 7)
			{
				return;
			}
			for(int i = x; i < 7; i++)
			{
				player.drawCard();
			}
			System.out.println("Player has chosen not to discard any cards.");
			return;
			
		case Market:
			player.drawCard();
			player.numActions += 1;
			player.numBuys += 1;
			player.coins += 1;
			return;
		
		case Smithy:
			player.drawCard();
			player.drawCard();
			player.drawCard();
			return;
		
		case Woodcutter:
			player.numBuys += 1;
			player.coins += 2;
			return;
		default:
			break;
		
			
		}
	}
//////////////////////////////////////////////////////////	
	
	public static Card getCard(List<Card> cards, CardName cardName)
	{
		for(int i=0; i<cards.size(); i++)
		{
			if(cards.get(i).cardName.equals(cardName))
			{
				return cards.get(i);
			}
		}
		return null;
	}
	
	public static List<Card> filter(Iterable<Card> cards, Type target)
	{
		List<Card> out = new ArrayList<Card>();
		for (Card c: cards)
		{
			if(c.getType() == target)
			{
				out.add(c);
			}
		}
		return out;
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		
		sb.append(" " + this.getCardName());
		sb.append("-" + this.getType() + " ");
		sb.append(" Cost: " + this.cost + " ");
		sb.append(" Score: " + this.score + " ");
		sb.append(" Treasure Value: " + this.value + "\n");
		
		return sb.toString();

	}
	
	public boolean equals(Object obj)
	{
		if(this == obj) return true;
		if(!super.equals(obj)) return false;
		if(!(obj instanceof Card)) return false;
		
		return cardName.equals(((Card) obj).cardName);
	}
	
	public int compareTo(Card o)
	{
		return cardName.compareTo(o.cardName);
	}
}