import java.util.*;

public class Pile
{
	public List<Card> cards;
	public int size;
	public int value;
	public int points;
	public int embargos;

	public Pile()
	{
		cards = new ArrayList<Card>();
		size = 0;
		value = 0;
		points = 0;
		embargos = 0;
	}

	public Pile(Card card, int num)
	{
		cards = new ArrayList<Card>();
		for(int i=0; i < num; i++)
		{
			addCard(card);
		}
		size = num;
		value = 0;
		points = 0;
	}

	public void addCard(Card card)
	{
		cards.add(card);
		size++;
		value += card.value;
		points += card.points;
	}

	public void removeCard(int cardIndex)
	{
		value -= cards.get(cardIndex).value;
		points -= cards.get(cardIndex).points;
		cards.remove(cardIndex);
		size--;
	}

	public Card drawCard()
	{
		Card drawnCard = cards.get(0);
		removeCard(0);
		return drawnCard;
	}

	public int getSize()
	{
		return size;
	}

	public int getValue()
	{
		return value;
	}

	public int getPoints()
	{
		return points;
	}

	public String getName(int index)
	{
		return cards.get(index).name;
	}

	public Card getCard(int cardIndex)
	{
		return cards.get(cardIndex);
	}

	/*public void shufflePile()
	{
		int deckSize = size;
		ArrayList<Card> tempDeck = new ArrayList<Card>();
		while(!cards.isEmpty())
		{
			int index = (int)(Math.random()*deckSize);
			tempDeck.add(cards.get(index));
			cards.remove(index);
		}

		cards = tempDeck;
	}*/
	

	public void shufflePile()
	{
		Collections.shuffle(cards);
	}

	public boolean isEmpty()
	{
		if(size == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public void revealAll()
	{
		System.out.println("Revealing all cards.");
		for(int i=0; i < size; i++)
		{
			System.out.println(cards.get(i).name);
		}
	}

	public void revealCard(int index)
	{
		System.out.println("Revealing " + cards.get(index).name);
	}

	public void print()
	{
		System.out.println("Size: " + size);
		System.out.println("Value: " + value);
		System.out.println("Points: " + points);
	}
}

