import java.util.ArrayList;
import java.util.Random;

public class Player
{
	public ArrayList<Card> deck, discard, hand;
	public int money, actions, buys, vp;
	private static Random rand;

	//constructor for player object
	public Player()
	{
		deck = new ArrayList<Card>();
		discard = new ArrayList<Card>();
		hand = new ArrayList<Card>();
		rand = new Random();
		money = 0;
		actions = 0;
		buys = 0;
		vp = 0;//victory points
	}

	//draw cards until you are out of cards or you hit numCards
	public void draw(int numCards)
	{
		int n = numCards;

		if(numCards > deck.size()+discard.size())
			n = deck.size()+discard.size();

		for(int i=0;i<n;i++)
		{
			if(deck.size() == 0)
				shuffleDiscard();

			hand.add(deck.get(0));
			deck.remove(0);
		}
	}

	//shuffles the discard pile into the deck
	public void shuffleDiscard()
	{
		int j=0;
		int size = discard.size();

		for(int i=0;i<size; i++)
		{
			j = rand.nextInt(discard.size());
			deck.add(discard.get(j));
			discard.remove(j);
		}	
	}

	//called at the end of each players turn to prepare them for their next turn
	public void cleanup()
	{
		actions = 1;
		buys = 1;
		money = 0;

		discard.addAll(hand);
		hand.clear();

		draw(5);
	}

	//returns list of action cards in a players hand
	public ArrayList<Card> actionCards()
	{
		ArrayList<Card> cards = new ArrayList<Card>();
		for(int i=0;i<hand.size();i++)
		{
			if(hand.get(i).type == Card.Type.action)
				cards.add(hand.get(i));
		}
		return cards;
	}

	//setters and getters for players
	public ArrayList<Card> gethand()
	{
		return hand;
	}

	public ArrayList<Card> getdeck()
	{
		return deck;
	}

	public ArrayList<Card> getdiscard()
	{
		return discard;
	}

	public int getmoney()
	{
		return money;
	}

	public void setmoney(int num)
	{
		money = num;
	}

	public int getactions()
	{
		return actions;
	}

	public void setactions(int num){
		actions = num;
	}

	public int getbuys(){
		return buys;
	}

	public void setbuys(int num){
		buys = num;
	}

	public int getvp(){
		return vp;
	}

	public void setvp(int num){
		vp = num;
	}

	public Random getrand(){
		return rand;
	}
}
