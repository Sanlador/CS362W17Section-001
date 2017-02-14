package dominion;

import java.util.*;

public class Game{
	private ArrayList<Deck> board;
	private ArrayList<Player> players;
	private ArrayList<Integer> embargoToken;

	Game(){
		board = new ArrayList<Deck>();
		players = new ArrayList<Player>();
		embargoToken = new ArrayList<Integer>();
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}

	public ArrayList<Deck> getBoard(){
		return board;
	}
	
	public void fillDeck(Deck deck, Card card, int num){
		for(int i=0; i<num; i++){
			deck.getPile().add(card);
		}
	}

	public void createPlayers(){
		players.add(new Player());
		players.add(new Player());
	}

	public void initializeTokens(){
		for(int i=0; i<20; i++){
			embargoToken.add(0);
		}
	}

	public void createBoard(){
		//Set up the decks that hold the victory cards
		board.add(new Deck(Card.CardName.Duchy));
		board.add(new Deck(Card.CardName.Estate));
		board.add(new Deck(Card.CardName.Province));
		board.add(new Deck(Card.CardName.Curse));
		board.add(new Deck(Card.CardName.Gardens));
		//Set up the decks that hold the treasure cards
		board.add(new Deck(Card.CardName.Gold));
		board.add(new Deck(Card.CardName.Silver));
		board.add(new Deck(Card.CardName.Copper));
		//Set up the decks that hold the kingdom cards
		board.add(new Deck(Card.CardName.Adventurer));
		board.add(new Deck(Card.CardName.Ambassador));
		board.add(new Deck(Card.CardName.Baron));
		board.add(new Deck(Card.CardName.CouncilRoom));
		board.add(new Deck(Card.CardName.Cutpurse));
		board.add(new Deck(Card.CardName.Embargo));
		board.add(new Deck(Card.CardName.Feast));
		board.add(new Deck(Card.CardName.GreatHall));
		board.add(new Deck(Card.CardName.Mine));
		board.add(new Deck(Card.CardName.Smithy));
		board.add(new Deck(Card.CardName.Village));
		board.add(new Deck(Card.CardName.Tribute));
	}

	public void fillVictoryCards(){
		Card duchyCard = new Card(Card.CardName.Duchy, Card.Type.VICTORY, 5, 3, 0); 
		fillDeck(board.get(0), duchyCard, 12);
		Card estateCard = new Card(Card.CardName.Estate, Card.Type.VICTORY, 2, 1, 0);
		fillDeck(board.get(1), estateCard, 24);
		Card provinceCard = new Card(Card.CardName.Province, Card.Type.VICTORY, 8, 6, 0);
		fillDeck(board.get(2), provinceCard, 12);
		Card curseCard = new Card(Card.CardName.Curse, Card.Type.VICTORY, 0, -1, 0);
		fillDeck(board.get(3), curseCard, 10);
		Card gardensCard = new Card(Card.CardName.Gardens, Card.Type.VICTORY, 4, 0, 0);
		fillDeck(board.get(4), gardensCard, 10);
	}

	public void fillTreasureCards(){
		Card goldCard = new Card(Card.CardName.Gold, Card.Type.TREASURE, 6, 0, 3);
		fillDeck(board.get(5), goldCard, 30);
		Card silverCard = new Card(Card.CardName.Silver, Card.Type.TREASURE, 3, 0, 2);
		fillDeck(board.get(6), silverCard, 40);
		Card copperCard = new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1);
		fillDeck(board.get(7), copperCard, 60);
	}

	public void fillActionCards(){
		Card adventurerCard = new Card(Card.CardName.Adventurer, Card.Type.ACTION, 6, 0, 0);
		fillDeck(board.get(8), adventurerCard, 10);
	       	Card ambassadorCard = new Card(Card.CardName.Ambassador, Card.Type.ACTION, 3, 0, 0);
		fillDeck(board.get(9), ambassadorCard, 10);
		Card baronCard = new Card(Card.CardName.Baron, Card.Type.ACTION, 4, 0, 0);
		fillDeck(board.get(10), baronCard, 10);
		Card councilRoomCard = new Card(Card.CardName.CouncilRoom, Card.Type.ACTION, 5, 0, 0);
		fillDeck(board.get(11), councilRoomCard, 10);
		Card cutpurseCard = new Card(Card.CardName.Cutpurse, Card.Type.ACTION, 4, 0, 0);
		fillDeck(board.get(12), cutpurseCard, 10);
		Card embargoCard = new Card(Card.CardName.Embargo, Card.Type.ACTION, 2, 0, 0);
		fillDeck(board.get(13), embargoCard, 10);
		Card feastCard = new Card(Card.CardName.Feast, Card.Type.ACTION, 4, 0, 0);
		fillDeck(board.get(14), feastCard, 10);
		Card greatHallCard = new Card(Card.CardName.GreatHall, Card.Type.ACTION, 3, 1, 0);
	       	fillDeck(board.get(15), greatHallCard, 10);
		Card mineCard = new Card(Card.CardName.Mine, Card.Type.ACTION, 5, 0, 0);
		fillDeck(board.get(16), mineCard, 10);
		Card smithyCard = new Card(Card.CardName.Smithy, Card.Type.ACTION, 4, 0, 0);
		fillDeck(board.get(17), smithyCard, 10);
		Card villageCard = new Card(Card.CardName.Village, Card.Type.ACTION, 3, 0, 0);
		fillDeck(board.get(18), villageCard, 10);
		Card tributeCard = new Card(Card.CardName.Tribute, Card.Type.ACTION, 5, 0, 0);
		fillDeck(board.get(19), tributeCard, 10);
	}

	public void dealHands(){
		for(int j=0; j<2; j++){
			for(int i=0; i<7; i++){
				Card toFill = board.get(7).drawCard();
				players.get(j).getDraw().getPile().add(toFill);
			}
			for(int i=0; i<3; i++){
				Card toFill = board.get(1).drawCard();
				players.get(j).getDraw().getPile().add(toFill);
			}
		}
	}
		
	public void setGame(){
		createBoard();
		createPlayers();
		fillVictoryCards();
		fillTreasureCards();
		fillActionCards();
		initializeTokens();
		dealHands();		
	}

	public boolean checkForEnd(){
		int numEmpty = 0;
		if(board.get(2).getPile().size() == 0){
			return false;
		}else{
			for(int i=board.size()-1; i>=0; i--){
				if(board.get(i).getPile().size() == 0){
					numEmpty++;
				}if(numEmpty == 3){
					return false;
				}		
			}
		}
		return true;
		//check to see if the ending conditions of the game have been reached
	}

	
	public void adventurerAction(int x){
		int numTreasures = 0;
		int index;
		while(numTreasures < 2){
			index = players.get(x).getDraw().getPile().size() - 1;
			if(players.get(x).getDraw().getPile().size() == 0){
				players.get(x).getDiscard().shuffleDeck();
				players.get(x).discardToDraw();
				index = players.get(x).getDraw().getPile().size()-1;
			}
			System.out.println("Revealing: " + players.get(x).getDraw().getPile().get(index).getCardName());
			if(players.get(x).getDraw().getPile().get(index).getType() == Card.Type.TREASURE){
				players.get(x).getHand().getPile().add(players.get(x).getDraw().drawCard());
				numTreasures++;
			}else{
				players.get(x).getDiscard().getPile().add(players.get(x).getDraw().drawCard());
			}
		}
	}

	public void ambassadorAction(int x){
		Card.CardName toSearch;
		Random rand = new Random();
		int numDiscarded = 0;
		int toReplace = 0;
		int toReveal = rand.nextInt(players.get(x).getHand().getPile().size());
		toSearch = players.get(x).getHand().getPile().get(toReveal).getCardName();
		System.out.println("Revealing: " + toSearch);
		for(int i=board.size()-1; i>=0; i--){
			if(toSearch == board.get(i).getName()){
				toReplace = i;
				break;
			}
		}
		for(int i=players.get(x).getHand().getPile().size()-1; i>=0; i--){
			if(players.get(x).getHand().getPile().get(i).getCardName() == toSearch){
				//players.get(x).getDiscard().getPile().add(players.get(x).getHand().getPile().remove(i));
				board.get(toReplace).getPile().add(players.get(x).getHand().getPile().remove(i));
				numDiscarded++;
			}
			if(numDiscarded == 2)
				break;
		}
	}


	public void baronAction(int x){
		players.get(x).setNumBuys(players.get(x).getNumBuys()+1);
		for(int i=players.get(x).getHand().getPile().size()-1; i>=0; i--){
			if(players.get(x).getHand().getPile().get(i).getCardName() == Card.CardName.Estate){
				players.get(x).getDiscard().getPile().add(players.get(x).getHand().getPile().remove(i));
				players.get(x).setCoins(players.get(x).getCoins()+4);
				return;
			}	
		}
		if(board.get(1).getPile().size() != 0)
			players.get(x).getHand().getPile().add(board.get(1).drawCard());
	}

	public void councilRoomAction(int x){
		if(players.get(x).getDraw().getPile().size() < 4){
			players.get(x).getDiscard().shuffleDeck();
			players.get(x).discardToDraw();
		}
		for(int i=0; i<4; i++){
			players.get(x).getHand().getPile().add(players.get(x).getDraw().drawCard());
		}
		players.get(x).setNumBuys(players.get(x).getNumBuys()+1);
		if(x==0){
			if(players.get(1).getDraw().getPile().size() == 0){
				players.get(1).getDiscard().shuffleDeck();
				players.get(1).discardToDraw();
			}
			players.get(1).getHand().getPile().add(players.get(1).getDraw().drawCard());
		}else{
			if(players.get(0).getDraw().getPile().size() == 0){
				players.get(0).getDiscard().shuffleDeck();
				players.get(0).discardToDraw();
			}
			players.get(0).getHand().getPile().add(players.get(0).getDraw().drawCard());
		}
	}

	public void cutpurseAction(int x){
		players.get(x).setCoins(players.get(x).getCoins()+2);
		int other;
		if(x==0)
			other = 1;
		else
			other =0;
		for(int i=players.get(other).getHand().getPile().size()-1; i>=0; i--){
			if(players.get(other).getHand().getPile().get(i).getCardName() == Card.CardName.Copper){
				players.get(other).getDiscard().getPile().add(players.get(other).getHand().getPile().remove(i));
				return;
			}
		}
		System.out.println("Revealing Hand With no Copper:");
		players.get(other).printHand();
	}

	public void embargoAction(int x){
		Random rand = new Random();
		int token = 0;
		while(token == 3){
			token = rand.nextInt(board.size());
		}
		players.get(x).setCoins(players.get(x).getCoins()+2);
		embargoToken.add(token, embargoToken.get(token)+1);
		for(int i=players.get(x).getDiscard().getPile().size()-1; i>=0; i--){
			if(players.get(x).getDiscard().getPile().get(i).getCardName() == Card.CardName.Embargo){
				players.get(x).getDiscard().getPile().remove(i);
				break;
			}
		}
	}

	public void feastAction(int x){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		Random rand = new Random();
		for(int i=0; i<players.get(x).getDiscard().getPile().size(); i++){
			if(players.get(x).getDiscard().getPile().get(i).getCardName() == Card.CardName.Feast){
				players.get(x).getDiscard().getPile().remove(i);
				break;
			}
		}
		for(int i=0; i<board.size(); i++){
			if(board.get(i).getPile().size() != 0){
				if(board.get(i).getPile().get(0).getCost() <= 5){
					temp.add(i);
				}
			}
		}
		if(temp.size() > 0){
			int choice = rand.nextInt(temp.size());
			players.get(x).getDiscard().getPile().add(board.get(temp.get(choice)).drawCard());
		}
	}

	public void greatHallAction(int x){
		players.get(x).setNumActions(players.get(x).getNumActions()+1);
		if(players.get(x).getDraw().getPile().size() == 0){
			players.get(x).getDiscard().shuffleDeck();
			players.get(x).discardToDraw();

		}
		players.get(x).getHand().getPile().add(players.get(x).getDraw().drawCard());
	}

	public void mineAction(int x){
		Random rand = new Random();
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i=0; i<players.get(x).getHand().getPile().size(); i++){
			if((players.get(x).getHand().getPile().get(i).getCardName() == Card.CardName.Copper) ||
			(players.get(x).getHand().getPile().get(i).getCardName() == Card.CardName.Silver)){
				temp.add(i);
			}
		}
		if(temp.size() > 0){
			int choice = rand.nextInt(temp.size());
			if(players.get(x).getHand().getPile().get(temp.get(choice)).getCardName() == Card.CardName.Copper){
				if(board.get(6).getPile().size() > 0){
					for(int i=0; i<players.get(x).getHand().getPile().size(); i++){
						if(players.get(x).getHand().getPile().get(i).getCardName() == Card.CardName.Copper){
							players.get(x).getHand().getPile().remove(i);
							break;
						}
					}
					players.get(x).getDiscard().getPile().add(board.get(6).drawCard());
				}
			}else{
				if(board.get(5).getPile().size() > 0){
					for(int i=0; i<players.get(x).getHand().getPile().size(); i++){
						if(players.get(x).getHand().getPile().get(i).getCardName() == Card.CardName.Silver){
							players.get(x).getHand().getPile().remove(i);
							break;
						}
					}
					players.get(x).getHand().getPile().remove(temp.get(choice));
					players.get(x).getDiscard().getPile().add(board.get(5).drawCard());
				}
			}
		}
	}

	public void smithyAction(int x){
		for(int i=0; i<3; i++){
			if(players.get(x).getDraw().getPile().size() == 0){
				players.get(x).getDiscard().shuffleDeck();
				players.get(x).discardToDraw();
			}
			//players.get(x).getHand().getPile().add(players.get(x).getDraw().getPile().remove(players.get(x).getDraw().getPile().size()-1));
			players.get(x).getHand().getPile().add(players.get(x).getDraw().drawCard());
		}
	}

	public void villageAction(int x){
		if(players.get(x).getDraw().getPile().size() == 0){
			players.get(x).getDiscard().shuffleDeck();
			players.get(x).discardToDraw();
		}
		players.get(x).getHand().getPile().add(players.get(x).getDraw().drawCard());
		players.get(x).setNumActions(players.get(x).getNumActions()+1);
	}

	public void tributeAction(int x){
		int other;
		Card toDiscard;
		if(x==0){
			other = 1;
		}else{
			other = 0;
		}
		for(int i=0; i<2; i++){
			if(players.get(other).getDraw().getPile().size() == 0){
				players.get(other).getDiscard().shuffleDeck();
				players.get(other).discardToDraw();
			}
			toDiscard = players.get(other).getDraw().drawCard();
			System.out.println("Revealing: " + toDiscard.getCardName());
			players.get(other).getDiscard().getPile().add(toDiscard);
		}
	}

	public void runAction(Card.CardName name, int x){
		if(name == Card.CardName.Adventurer){
			adventurerAction(x);
		}else if(name == Card.CardName.Ambassador){
			ambassadorAction(x);
		}else if(name == Card.CardName.Baron){
			baronAction(x);
		}else if(name == Card.CardName.CouncilRoom){
			councilRoomAction(x);
		}else if(name == Card.CardName.Cutpurse){
			cutpurseAction(x);
		}else if(name == Card.CardName.Embargo){
			embargoAction(x);
		}else if(name == Card.CardName.Feast){
			feastAction(x);
		}else if(name == Card.CardName.GreatHall){
			greatHallAction(x);
		}else if(name == Card.CardName.Mine){
			mineAction(x);
		}else if(name == Card.CardName.Smithy){
			smithyAction(x);
		}else if(name == Card.CardName.Village){
			villageAction(x);
		}else if(name == Card.CardName.Tribute){
			tributeAction(x);
		}	
	}

	public void invokeAction(int x){
		Random rand = new Random();
		Card.CardName name;
		int action = 0;
		int toRemove = 0;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		for(int i=players.get(x).getHand().getPile().size()-1; i>=0; i--){
			if(players.get(x).getHand().getPile().get(i).getType() == Card.Type.ACTION){
				temp.add(i);
			}
		}
		if(temp.size() > 0){
			action = rand.nextInt(temp.size());
			toRemove = temp.get(action);
			System.out.println("Playing: " + players.get(x).getHand().getPile().get(temp.get(action)).getCardName() + "\n");
			name = players.get(x).getHand().getPile().get(temp.get(action)).getCardName();
			players.get(x).getDiscard().getPile().add(players.get(x).getHand().getPile().remove(toRemove));
			runAction(name, x);
		}
	}

	public void invokeBuy(int x){
		Random rand = new Random();
		int buy = 0;
		ArrayList<Integer> temp = new ArrayList<Integer>();
		System.out.println("Player " + (x+1) + " COINS: " + players.get(x).getCoins());
		for(int i=board.size()-1; i>=0; i--){
			if(board.get(i).getPile().size() > 0){
				if(players.get(x).getCoins() >= board.get(i).getPile().get(0).getCost()){
					if((i != 3) && (board.get(i).getPile().size() != 0))
						temp.add(i);
				}
			}
		}
		if(temp.size() > 0){
			buy = rand.nextInt(temp.size());
			System.out.println("Buying: " + board.get(temp.get(buy)).getPile().get(0).getCardName() + "\n");
			players.get(x).setCoins(players.get(x).getCoins() - board.get(temp.get(buy)).getPile().get(0).getCost());
			//System.out.println("Player " + (x+1) + " COINS: " + players.get(x).getCoins() + "\n");
			players.get(x).getHand().getPile().add(board.get(temp.get(buy)).drawCard());

			//Test with embargo implementation
			if(embargoToken.get(temp.get(buy)) != 0){
				for(int i=0; i<embargoToken.get(temp.get(buy)); i++){
					if(board.get(3).getPile().size() > 0){
						players.get(x).getHand().getPile().add(board.get(3).drawCard());
					}
				}
			}
		}
	}

	public void takeTurn(int x){
		players.get(x).printHand();
		while(players.get(x).getNumActions() > 0){
			invokeAction(x);
			players.get(x).setNumActions(players.get(x).getNumActions()-1);
		}
		System.out.println("STARTING BUY---");
		players.get(x).printHand();
		players.get(x).totalCoins();
		while(players.get(x).getNumBuys() > 0){
			invokeBuy(x);
			players.get(x).setNumBuys(players.get(x).getNumBuys()-1);
		}
	}

	public void playGame(){
		Random rand = new Random();
		int whoseTurn = 0;
		int n;
		int again = 0;
		Card card = new Card(Card.CardName.Adventurer, Card.Type.ACTION, 0, 0, 0);
		players.get(0).resetPlayer();
		players.get(1).resetPlayer();
		while(checkForEnd()){
			System.out.println("------------------Player 1--------------------");
			while(whoseTurn == 0){
				takeTurn(0);
				if((players.get(0).getDraw().getPile().size()) < 5){
					players.get(0).getDiscard().shuffleDeck();
					players.get(0).discardToDraw();
				}

				whoseTurn = 1;
			}
			if(checkForEnd() == false)
				break;
			players.get(0).resetPlayer();
			System.out.println("----------------------------------------------");
			System.out.println("------------------Player 2--------------------");
			while(whoseTurn == 1){
				takeTurn(1);
				if((players.get(1).getDraw().getPile().size()) < 5){
					players.get(1).getDiscard().shuffleDeck();
					players.get(1).discardToDraw();
				}
				whoseTurn = 0;
			}
			System.out.println("----------------------------------------------");
			players.get(1).resetPlayer();
			again++;
		}
	}
	
	public void whoWon(){
		int points = 0;
		players.get(0).discardToDraw();
		for(int i=0; i<players.get(0).getDraw().getPile().size(); i++){
			points++;
		}
		players.get(0).setVictory(points);
		points = 0;
		players.get(1).discardToDraw();
		for(int i=0; i<players.get(1).getDraw().getPile().size(); i++){
			points++;
		}
		players.get(1).setVictory(points);
		if(players.get(0).getVictory() > players.get(1).getVictory()){
			System.out.println("--------GAME OVER----------");
			System.out.println("Player 1 Points: " + players.get(0).getVictory());
			System.out.println("Player 2 Points: " + players.get(1).getVictory());
			System.out.println("Player 1 Wins!");
		}else{
			System.out.println("--------GAME OVER----------");
			System.out.println("Player 1 Points: " + players.get(0).getVictory());
			System.out.println("Player 2 Points: " + players.get(1).getVictory());
			System.out.println("Player 2 Wins!");
		}
	}

	public static void main(String [] args){
		Game game = new Game();
		game.setGame();
		game.playGame();
		game.whoWon();
	}
};
