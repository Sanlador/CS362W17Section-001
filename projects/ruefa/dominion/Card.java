import java.util.Map;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;

public class Card
{
	public final Type type;
	public final Name name;
	public final int cost;
	public static Map<Name, Card> cardList;//maps the cards name with the card

	//3 types of dominion cards
	public enum Type
	{
		victory, treasure, action
	};

	public Card(Type type, Name name, int cost)
	{
		this.type = type;
		this.name = name;
		this.cost = cost;
	}

	//Name enum for cards
	//contains the "actions" for each card
	public enum Name
	{
		//Reveal cards from your deck until you reveal 2 Treasure cards. Put those Treasure cards into your hand
		//and discard the other revealed cards.
		adventurer{
			public void action(){
				Player player = dominionBoard.whosTurn();
				int treasureCount = 0;

				while(treasureCount < 2)
				{
					if(player.deck.get(0).type == Card.Type.treasure)
					{
						player.gethand().add(player.deck.get(0));
						player.getdeck().remove(0);
						treasureCount++;
					}
					else
					{
						player.getdiscard().add(player.deck.get(0));
						player.getdeck().remove(0);
					}
				}
			}
		},
		//Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply.
		//Then each other player gains a copy of it.
		ambassador{
			public void action(){
				Player player = dominionBoard.whosTurn();
				Player otherPlayer = dominionBoard.otherPlayer();
				int cardNum = player.getrand().nextInt(player.gethand().size());

				Card card = player.gethand().get(cardNum);
				player.gethand().remove(cardNum);
				dominionBoard.pool.put(card, dominionBoard.pool.get(card)+2);
				dominionBoard.addCard(otherPlayer, card);
			}
		},
		//+1 Buy, You may discard an Estate card. If you do, +4 Coins. Otherwise, gain an Estate card.
		baron{
			public void action(){
				Player player = dominionBoard.whosTurn();
				player.buys += 1;
				if(player.hand.contains(dominionBoard.cardList.get(Card.Name.estate)))
				{
					player.discard.add(cardList.get(Card.Name.estate));
					player.hand.remove(cardList.get(Card.Name.estate));
					player.money += 4;
				}
				else
					dominionBoard.addCard(player, dominionBoard.cardList.get(Card.Name.estate));
			}
		},
		//+4 Cards, +1 Buy, Each other player draws a card.
		council_room{
			public void action(){
				Player player = dominionBoard.whosTurn();
				Player otherPlayer = dominionBoard.otherPlayer();

				player.draw(4);
				player.buys += 1;
				otherPlayer.draw(1);
			}
		},
		//+2 Coins, Each other player discards a Copper card (or reveals a hand with no Copper).
		cutpurse{
			public void action(){
				Player player = dominionBoard.whosTurn();
				Player otherPlayer = dominionBoard.otherPlayer();
				Card copper = dominionBoard.cardList.get(Card.Name.copper);

				player.setmoney(player.getmoney()+2);
				if(otherPlayer.gethand().contains(copper))
				{
					otherPlayer.getdiscard().add(copper);
					otherPlayer.gethand().remove(copper);
				}
			}
		},
		//+2 Coins, Trash this card. Put an Embargo token on top of a Supply pile.
		//When a player buys a card, he gains a Curse card per Embargo token on that pile.
		embargo{
			public void action(){
				Player player = dominionBoard.whosTurn();
				
				player.setmoney(player.getmoney()+2);

				ArrayList<Card> cards = new ArrayList<Card>(dominionBoard.pool.keySet());
				int numCard = player.getrand().nextInt(cards.size());
				Card card = cards.get(numCard);
				dominionBoard.embargo.put(card, dominionBoard.embargo.get(card)+1);
			}
		},
		//Trash this card. Gain a card costing up to 5 Coins.
		feast{
			public void action(){
				Player player = dominionBoard.whosTurn();

				Card thisCard = dominionBoard.cardList.get(Card.Name.feast);
				dominionBoard.trash.add(thisCard);
				player.getdiscard().remove(thisCard);

				ArrayList<Card> options = dominionBoard.cardOptions(5);
				Card pickedCard = options.get(player.getrand().nextInt(5));
				dominionBoard.addCard(player, pickedCard);
			}
		},
		//Variable, Worth 1 Victory for every 10 cards in your deck (rounded down).
		gardens{
			public void action(){
				Player player = dominionBoard.whosTurn();
				int cards = player.gethand().size();
				cards += player.getdiscard().size();
				cards += player.getdeck().size();
				player.setvp(player.getvp()+cards/10);
			}
		},
		//1 Victory, +1 Card, +1 Action.
		great_hall{
			public void action(){
				Player player = dominionBoard.whosTurn();

				player.setvp(player.getvp()+1);
				player.setactions(player.getactions()+1);
				player.draw(1);
			}
		},
		//Trash a Treasure card from your hand. Gain a Treasure card costing up to 3 Coins more; 
		//put it into your hand.
		mine{
			public void action(){
				Player player = dominionBoard.whosTurn();
				int value = 0;

				for(int i=0;i<player.gethand().size();i++)
				{
					if(player.gethand().get(i).type == Card.Type.treasure)
					{
						value = player.gethand().get(i).cost;
						dominionBoard.trash.add(player.gethand().get(i));
						player.gethand().remove(i);
					}
				}

				ArrayList<Card> options = dominionBoard.cardOptions(value+3);
				Card card;
				int cardNum;
				do{
					cardNum = player.getrand().nextInt(options.size());
					card = options.get(cardNum);
				}while(card.type != Card.Type.treasure);
				player.gethand().add(card);
				dominionBoard.pool.put(card, dominionBoard.pool.get(card)-1);
			}
		},
		//Trash a card from your hand. Gain a card costing up to 2 Coins more than the trashed card.
		remodel{
			public void action(){
				Player player = dominionBoard.whosTurn();
				int value = 0;

				int cardNum = player.getrand().nextInt(player.gethand().size());
				value = player.gethand().get(cardNum).cost;
				dominionBoard.trash.add(player.gethand().get(cardNum));
				player.gethand().remove(cardNum);

				ArrayList<Card> options = dominionBoard.cardOptions(value+2);
				cardNum = player.getrand().nextInt(options.size());
				dominionBoard.addCard(player, options.get(cardNum));
			}
		},
		//+1 Buy, Trash a card from your hand. +Coins equal to its cost.
		salvager{
			public void action(){
				Player player = dominionBoard.whosTurn();

				player.setbuys(player.getbuys()+1);

				int cardNum = player.getrand().nextInt(player.gethand().size());
				player.setmoney(player.getmoney()+player.gethand().get(cardNum).cost);
				dominionBoard.trash.add(player.gethand().get(cardNum));
				player.gethand().remove(cardNum);
			}
		},
		//draw 3 cards
		smithy{
			public void action(){
				Player player = dominionBoard.whosTurn();
				player.draw(3);
			}
		},

		//+1 money
		copper{
			public void action(){
				Player player = dominionBoard.whosTurn();
				player.setmoney(player.getmoney() + 1);
			}
		},
		//+2 money
		silver{
			public void action(){
				Player player = dominionBoard.whosTurn();
				player.money += 2;
			}
		},
		//+3 money
		gold{
			public void action(){
				Player player = dominionBoard.whosTurn();
				player.setmoney(player.getmoney()+3);
			}
		},
		//-1 vp
		curse{
			public void action(){
				Player player = dominionBoard.whosTurn();
				player.setvp(player.getvp()-1);
			}
		},
		//+1 vp
		estate{
			public void action(){
				Player player = dominionBoard.whosTurn();
				player.setvp(player.getvp()+1);
			}
		},
		//+3 vp
		duchy{
			public void action(){
				Player player = dominionBoard.whosTurn();
				player.setvp(player.getvp()+3);
			}
		},
		//+6 vp
		province{
			public void action(){
				Player player = dominionBoard.whosTurn();
				player.setvp(player.getvp()+6);
			}
		};

		public abstract void action();
	}

	//creates all the cards
	//returns a hashmap used to reference cards by Name
	public static Map<Name, Card> createCards()
	{
		cardList = new HashMap<Name, Card>();
		
		cardList.put(Name.adventurer, new Card(Type.action, Name.adventurer, 6));
		cardList.put(Name.ambassador, new Card(Type.action, Name.ambassador, 3));
		cardList.put(Name.baron, new Card(Type.action, Name.baron, 4));
		cardList.put(Name.council_room, new Card(Type.action, Name.council_room, 5));
		cardList.put(Name.cutpurse, new Card(Type.action, Name.cutpurse, 4));
		cardList.put(Name.embargo, new Card(Type.action, Name.embargo, 2));
		cardList.put(Name.feast, new Card(Type.action, Name.feast, 4));
		cardList.put(Name.gardens, new Card(Type.victory, Name.gardens, 4));
		cardList.put(Name.great_hall, new Card(Type.action, Name.great_hall, 3));
		cardList.put(Name.mine, new Card(Type.action, Name.mine, 5));
		cardList.put(Name.remodel, new Card(Type.action, Name.remodel, 4));
		cardList.put(Name.salvager, new Card(Type.action, Name.salvager, 4));
		cardList.put(Name.smithy, new Card(Type.action, Name.smithy, 4));

		cardList.put(Name.copper, new Card(Type.treasure, Name.copper, 0));
		cardList.put(Name.silver, new Card(Type.treasure, Name.silver, 3));
		cardList.put(Name.gold, new Card(Type.treasure, Name.gold, 6));

		cardList.put(Name.curse, new Card(Type.victory, Name.curse, 0));
		cardList.put(Name.estate, new Card(Type.victory, Name.estate, 2));
		cardList.put(Name.duchy, new Card(Type.victory, Name.duchy, 5));
		cardList.put(Name.province, new Card(Type.victory, Name.province, 8));
		return cardList;
	}
}
