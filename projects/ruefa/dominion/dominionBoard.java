import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class dominionBoard
{
	public static Map<Card, Integer> pool;//supply piles
	public static ArrayList<Card> trash;
	public static Player player1;
	public static Player player2;
	public static Map<Card.Name, Card> cardList;
	public static int turn; //turn=1 referes to player1's turn and 2 player2
	public static Map<Card, Integer> embargo;//used to implement the embargo card

	public dominionBoard()
	{
		player1 = new Player();
		player2 = new Player();
		turn=1;//player 1 goes first

		pool = new HashMap<Card, Integer>();
		cardList = Card.createCards();

		embargo = new HashMap<Card, Integer>();
		for(Card card : cardList.values())
			embargo.put(card, 0);

		trash = new ArrayList<Card>();

		//add all the cards to the supply pile
		pool.put(cardList.get(Card.Name.adventurer), 10);
		pool.put(cardList.get(Card.Name.ambassador), 10);
		pool.put(cardList.get(Card.Name.baron), 10);
		pool.put(cardList.get(Card.Name.council_room), 10);
		pool.put(cardList.get(Card.Name.cutpurse), 10);
		pool.put(cardList.get(Card.Name.embargo), 10);
		pool.put(cardList.get(Card.Name.feast), 10);
		pool.put(cardList.get(Card.Name.gardens), 10);
		pool.put(cardList.get(Card.Name.great_hall), 10);
		pool.put(cardList.get(Card.Name.mine), 10);
		pool.put(cardList.get(Card.Name.remodel), 10);
		pool.put(cardList.get(Card.Name.salvager), 10);
		pool.put(cardList.get(Card.Name.smithy), 10);

		pool.put(cardList.get(Card.Name.copper), 60);
		pool.put(cardList.get(Card.Name.silver), 40);
		pool.put(cardList.get(Card.Name.gold), 30);

		pool.put(cardList.get(Card.Name.curse), 10);
		pool.put(cardList.get(Card.Name.estate), 12);
		pool.put(cardList.get(Card.Name.duchy), 12);
		pool.put(cardList.get(Card.Name.province), 12);
	}

	//controls the flow of the game
	public static void game()
	{
		ArrayList<Card> options;
		Card card;
		int randCard;
		Player currPlayer;
		//loop until a game over condition is met
		while(isGameOver() == false)
		{
			currPlayer = whosTurn();
			if(turn==1)
				System.out.println("Player1's turn");
			else
				System.out.println("Player2's turn");

			//actions round
			while(currPlayer.getactions()>0)
			{
				options = currPlayer.actionCards();
				if(options.size()<1)
					break;
				randCard = currPlayer.getrand().nextInt(options.size());
				card = options.get(randCard);
				System.out.println("Play " + card.name);
				playCard(card);
				currPlayer.setactions(currPlayer.getactions()-1);
			}

			//adds up the money in the players hand to be used in the buy round
			for(int i=0;i<currPlayer.gethand().size();i++)
			{
				if(currPlayer.gethand().get(i).type == Card.Type.treasure)
					playCard(currPlayer.gethand().get(i));
			}

			//buy round
			while(currPlayer.getbuys()>0)
			{
				System.out.println("Player's money: " + currPlayer.getmoney());
				options = cardOptions(currPlayer.getmoney());
				if(options.size()<1)
				{
					System.out.println("You can't buy any cards!");
					break;
				}
				randCard = currPlayer.getrand().nextInt(options.size());
				card = options.get(randCard);
				System.out.println("Buy " + card.name);
				buyCard(currPlayer, card);

				currPlayer.setbuys(currPlayer.getbuys()-1);
			}

			//change turn over to other player
			if(turn==1)
				turn=2;
			else
				turn=1;
			//prepare player for its next turn
			currPlayer.cleanup();
			System.out.println();
		}

		//once we leave the loop that means the game is over and we can calculate the winner
		System.out.println(winner());
	}

	//starts the game
	public static void start()
	{
		for(int i=0;i<7;i++)
		{
			player1.getdeck().add(cardList.get(Card.Name.copper));
			player2.getdeck().add(cardList.get(Card.Name.copper));
		}
		for(int i=0;i<3;i++)
		{
			player1.getdeck().add(cardList.get(Card.Name.estate));
			player2.getdeck().add(cardList.get(Card.Name.estate));
		}

		player1.cleanup();
		player2.cleanup();

		game();
	}

	//returns the active Player
	public static Player whosTurn()
	{
		if(turn==1)
			return player1;
		else
			return player2;
	}

	//returns non active player
	public static Player otherPlayer()
	{
		if(turn==1)
			return player2;
		else
			return player1;
	}

	//adds a card to a players discard pile and removes the card from the supply pile
	public static void addCard(Player player, Card card)
	{
		if(pool.get(card) > 0)
		{
			player.discard.add(card);
			pool.put(card, pool.get(card) - 1);
		}
		else
			System.out.println("There are no more " + card.name + " cards");
	}

	//uses the players money to buy a card and then uses addCard()
	public static void buyCard(Player player, Card card)
	{
		if(player.getmoney() >= card.cost && pool.get(card) > 0)
		{
			//embargo implementation
			if(embargo.get(card) > 0)
			{
				System.out.println("Embargo'd! Draw " + embargo.get(card) + " curse cards");
				for(int i=0;i<embargo.get(card);i++)
					player.getdiscard().add(cardList.get(Card.Name.curse));
				embargo.put(card, 0);
			}
			player.setmoney(player.getmoney()-card.cost);
			if(card == cardList.get(Card.Name.curse))
				addCard(otherPlayer(), card);
			else
				addCard(player, card);
		}
		else if(player.getmoney() < card.cost)
		{
			player.setbuys(player.getbuys()+1);
			System.out.println("Insufficient funds");
		}
		else if(pool.get(card) < 1)
		{
			System.out.println("No more " + card.name + " cards");
			player.setbuys(player.getbuys()+1);
		}
		else
		{
			System.out.println("Unknown error");
			player.setbuys(player.getbuys()+1);
		}
	}

	//removes card from players hand and plays the cards action
	public static void playCard(Card card)
	{
		if(whosTurn().gethand().contains(card)==true)
		{
			whosTurn().getdiscard().add(card);
			whosTurn().gethand().remove(card);
			card.name.action();
		}
	}

	//the bots use this to play the game. it returns a list of cards they have enough money to buy
	public static ArrayList<Card> cardOptions(int max)
	{
		ArrayList<Card> options = new ArrayList<Card>();

		for(Card card : cardList.values())
		{
			if((card.cost <= max) && (pool.get(card) > 0))
				options.add(card);
		}
		return options;
	}

	//checks if the game is over
	//game ends when the province pile is empty or when 3 supply piles are empty
	public static boolean isGameOver()
	{
		if (pool.get(cardList.get(Card.Name.province)) == 0)
			return true;
		int emptyPiles = 0;
		for(int number : pool.values())
		{
			if(number == 0)
				emptyPiles++;
			if(emptyPiles >= 3)
				return true;
		}
		return false;
	}

	public static String winner()
	{
		//consolidate player cards into one deck for easier checking
		player1.getdeck().addAll(player1.gethand());
		player1.getdeck().addAll(player1.getdiscard());
		player2.getdeck().addAll(player2.gethand());
		player2.getdeck().addAll(player2.getdiscard());

		turn = 1; //most actions use this so we want to make sure to do player1 first
		for(int i=0;i<player1.getdeck().size();i++)
		{
			if(player1.getdeck().get(i).type == Card.Type.victory)
				player1.getdeck().get(i).name.action();
		}
		turn = 0;
		for(int i=0;i<player2.getdeck().size();i++)
		{
			if(player2.getdeck().get(i).type == Card.Type.victory)
				player2.getdeck().get(i).name.action();
		}

		if(player1.getvp() > player2.getvp())
			return "Player1 wins!";
		else if(player2.getvp() > player1.getvp())
			return "Player2 wins!";
		else
			return "Draw!";
	}
}	
