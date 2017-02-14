package Dominion;
import java.lang.reflect.Array;
import java.util.*;


import Dominion.Card.*;

public class Board 
{
	/*
	 * The board contains: 
	 * - Supply
	 * 
	 * Supply contains:
	 * - Treasures (copper, silver and gold)
	 * - Victory Cards (Estate, Duchy and Provinces)
	 * - Kingdom cards
	 * - Curse Cards
	 */
	
	public List<Card> supply = new ArrayList();
	public List<Card> curse = new ArrayList();
	
	public List<String> kList = new ArrayList();

	public List<String> embargo = new ArrayList();
	public int[] em = new int[16];
	
	
	public Board()
	{
		//fill board with standard supply
		
		for (int i = 0; i < 60; i++)
		{
			supply.add(new Card("Copper"));
		}

		for (int i = 0; i < 40; i++)
		{
			supply.add(new Card("Silver"));
		}

		for (int i = 0; i < 30; i++)
		{
			supply.add(new Card("Gold"));
		}
		
		for (int i = 0; i < 24; i++)
		{
			supply.add(new Card("Estate"));
		}
		
		for (int i = 0; i < 12; i++)
		{
			supply.add(new Card("Duchy"));
		}

		for (int i = 0; i < 12; i++)
		{
			supply.add(new Card("Province"));
		}
		
		for (int i = 0; i < 16; i++)
			em[i] = 0;
		
		kList.add("Adventurer");
		kList.add("Ambassador");
		kList.add("Baron");
		kList.add("Council Room");
		kList.add("Cutpurse");
		kList.add("Embargo");
		kList.add("Feast");
		kList.add("Gardens");
		kList.add("Great Hall");
		kList.add("Mine");
		kList.add("Minion");
		kList.add("Smithy");
		kList.add("Steward");
		
		Random r = new Random();
		int rn;
		
		//set list of kingdoms to 10 randomly
		for (int i = 0; i < 3; i++)
		{
			rn = nextInt(kList.size());
			kList.remove(rn);
		}
		
		embargo.add("Copper");
		embargo.add("Silver");
		embargo.add("Gold");
		embargo.add("Estate");
		embargo.add("Duchy");
		embargo.add("Province");
		
		for (int i = 0; i < 10; i++)
		{
			curse.add(new Card("Curse"));
		}
		
		for (int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)
			{
				if (kList.get(i) == "Adventurer")
					supply.add(new Card("Adventurer", 6));
				else if (kList.get(i) == "Ambassador")
					supply.add(new Card("Ambassador", 3));
				else if (kList.get(i) == "Barron")
					supply.add(new Card("Barron",4));
				else if (kList.get(i) == "Council Room")
					supply.add(new Card("Council Room", 5));
				else if (kList.get(i) == "Cutpurse")
					supply.add(new Card("Cutpurse", 4));
				else if (kList.get(i) == "Embargo")
					supply.add(new Card("Embargo", 2));
				else if (kList.get(i) == "Feast")
					supply.add(new Card("Feast", 4));
				else if (kList.get(i) == "Gardens")
					supply.add(new Card("Gardens"));
				else if (kList.get(i) == "Great Hall")
					supply.add(new Card("Great Hall", 3));
				else if (kList.get(i) == "Mine")
					supply.add(new Card("Mine", 5));
				else if (kList.get(i) == "Minion")
					supply.add(new Card("Minion", 5));
				else if (kList.get(i) == "Smithy")
					supply.add(new Card("Smithy", 4));
				else if (kList.get(i) == "Steward")
					supply.add(new Card("Steward", 3));
			}
		}
	}
	
	
	private int nextInt(int size) {
		// TODO Auto-generated method stub
		return 0;
	}


	//counts empty supply piles and determines if the game is over
	public boolean gameEnd()
	{
		int supplyEmpty = 0;
		List<String> check = new ArrayList();
		boolean pEmpty = false;
		boolean checkS = false;
		
		for (int i = 0; i < 10; i++)
		{
			checkS = false;
			for (int j = 0; j < supply.size(); j++)
			{
				if (supply.get(j).getName() == kList.get(i))
					checkS = true;
			}
			if (checkS == false)
				supplyEmpty++;
		}
		
		if (Card.search(new Card("Copper"), supply) == false)
			supplyEmpty++;
		else if (Card.search(new Card("Silver"), supply) == false)
			supplyEmpty++;
		else if (Card.search(new Card("Gold"), supply) == false)
			supplyEmpty++;
		else if (Card.search(new Card("Estate"), supply) == false)
			supplyEmpty++;
		else if (Card.search(new Card("Duchy"), supply) == false)
			supplyEmpty++;
		
		if (curse.isEmpty() == true)
			supplyEmpty++;
		
		if (Card.search(new Card("Province"), supply) == false)
			pEmpty = true;

		if (supplyEmpty >= 3 || pEmpty == true)
			return true;
		else
			return false;
	}
	
	
	//returns a list of what cards in the supply are available to be bought
	public List<String> available(int wealth)
	{
		List<String> a = new ArrayList();
		Card temp = null;
		
		for (int i = 0; i < supply.size(); i++)
		{
			if (!a.contains(supply.get(i).getName()) && wealth >= supply.get(i).getCost())
			{
				a.add(supply.get(i).getName());
			}
		}
		
		return a;
	}
	
	
	//buying action
	public void buy(int wealth, String c, List<Card> hand)
	{
		boolean control = false;
		int i = 0;
		while (control == false && i < supply.size())
		{
			if (c == supply.get(i).getName())
			{
				hand.add(supply.get(i));
				wealth -= supply.get(i).getCost(); 
				control = true;
				int emToken = checkEmbargo(supply.get(i).getName());
				//bug; must be AFTEr emToken's instantiation, else it can call a
				//nonexistant element
				supply.remove(i);
				if (curse.isEmpty() == false)
				{
					//account for the number of tokens being greater than the number in the pile
					if (emToken > curse.size())
						emToken = curse.size();
					for (int j = 0; j < emToken; j++)
					{
						hand.add(curse.get(0));
						curse.remove(0);
					}
				}
			}
			i++;
		}
	}
	
	private int checkEmbargo(String c)
	{
		for (int i = 0; i < embargo.size(); i++)
		{
			//account for curse to be empty
			if (c == embargo.get(i) && !curse.isEmpty())
			{
				if (em[i] > 0)
					System.out.println("There is an embargo on " + c + " You receive " + String.format("%d", em[i]) + " curse cards");
				return em[i];
			}
			else 
				return 0;
		}
		return 0;
	}
	
}
