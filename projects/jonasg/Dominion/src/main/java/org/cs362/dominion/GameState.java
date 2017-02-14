package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.TreeMap;

public class GameState implements Cloneable {
    public List<Player> players = new ArrayList<Player>();
    public List<Card> cards;
    public HashMap<Card, Integer> gameBoard = new HashMap<Card, Integer>();
    public HashMap<Card, Integer> boardEmbargos = new HashMap<Card, Integer>();

    //regular constructor
    public GameState(List<Card> cards) {
	this.cards=cards;
    }

    //clone constructor
    private GameState(List<Card> cards, List<Player> players, HashMap<Card, Integer> gameBoard, HashMap<Card, Integer> boardEmbargos)
    {
	this.cards=cards;
	this.players=players;
	this.gameBoard=gameBoard;
	this.boardEmbargos=boardEmbargos;
    }

    //adds a player to the gamestate
    public void addPlayer(Player player) {
	players.add(player);
    }

    //initialize the kingdom cards
    public void initializeGame(){
	//check number of players
	if (players.size() > 4 || players.size() < 2)
	{
	    System.err.println("the number of players mus be between 2 and 4 ");
	    return;
	}

	//set number of Curse cards the default number of players is 2
	gameBoard.put(Card.getCard(cards, Card.CardName.Curse), 10);

	//set number of Victory cards
	gameBoard.put(Card.getCard(cards, Card.CardName.Province), 8);
	gameBoard.put(Card.getCard(cards, Card.CardName.Duchy), 8);
	gameBoard.put(Card.getCard(cards, Card.CardName.Estate), 8);

	//set number of Treasure cards
	gameBoard.put(Card.getCard(cards, Card.CardName.Gold), 30);
	gameBoard.put(Card.getCard(cards, Card.CardName.Silver), 40);
	gameBoard.put(Card.getCard(cards, Card.CardName.Copper), 46);

	//initialize supply for only two players
	int selectedKindom=0;
	int Kingdom_Cards_Selected=10;
	int random;

	//induced bug, grabs 11 kingdom cards instead of 10
	while (selectedKindom <= Kingdom_Cards_Selected) {
	    random = Randomness.random.nextInt(cards.size());
	    Card tmp = cards.get(random);
	    if(gameBoard.containsKey(tmp) || (tmp == null)) continue;
	    gameBoard.put(tmp, 10);
	    selectedKindom++;
	}

	for(Card card : gameBoard.keySet())
	    boardEmbargos.put(card, 0);

	for (Player player: players) {
	    for (int i = 0; i < 7; i++)
		player.gain(Card.getCard(cards, Card.CardName.Copper));
	    for (int i = 0; i < 3; i++)
		player.gain(Card.getCard(cards, Card.CardName.Estate));

	    //induced bug: player starts with 2 coins
	    player.numActions = 1;
	    player.coins = 0;
	    player.numBuys = 2;
	    //Shuffle your starting 10 cards (7 Coppers & 3 Estates) and place them face-down as your Deck. Draw the top
	    //5 cards as your starting hand
	    for (int i = 0; i < 5; i++) {
		player.drawCard();
	    }
	}
    }

    public HashMap<Player, Integer>  play() {
	int turn = 0;
	while (!isGameOver()) {
	    turn++;
	    for (Player player: players) {
		System.out.println("Player: "+ player.player_username + " is playing");
		//player p plays action card
		player.playKingdomCard();
		//player plays treasure card
		player.playTreasureCard();
		//player buy cards
		player.buyCard((GameState) this);
		//player ends turn
		player.endTurn();
	    }
	}
	return this.getWinners();
    }

    public boolean isSupplyEmpty(Card card) {
	//checks if the supply for a specific card is empty
	if((gameBoard.get(card)==null) || (gameBoard.get(card)<=0))
	    return true;
	return false;
    }

    public boolean reduceSupply(Card card) {
	if(isSupplyEmpty(card))
	    return false;
	gameBoard.put(card, gameBoard.get(card) - 1);
	return true;
    }

    public boolean isGameOver() {
	//if stack of Province cards is empty, the game ends
	if(isSupplyEmpty(Card.getCard(cards, Card.CardName.Province)))
	    return true;
	//if three supply pile are at 0, the game ends
	int emptySupplyPile = 0;
	for (int i: gameBoard.values()){
	    if (i == 0)
		emptySupplyPile++;
	    if (emptySupplyPile >= 3)
		return true;
	}
	return false;
    }

    /* Set HashMap of each player and the score (remember ties!) */
    public HashMap<Player, Integer>  getWinners() {
	HashMap<Player, Integer> playerScore = new HashMap<Player, Integer>();

	//get score for each player
	for (Player p : players) {
	    int score = p.scoreFor();
	    playerScore.put(p, score);
	}
	return playerScore;
    }

    @Override
    public String toString() {

	StringBuilder sb = new StringBuilder();
	if (gameBoard.isEmpty())
	    sb.append("The board game is embty you need to intialize the game!!!!");
	else {
	    for (Player player : players)
		sb.append(" --- " + player.toString() + "\n");
	    sb.append(" --- gameBoard --- \n");
	    sb.append("Cards on the table: \n");
	    sb.append("Card Name \t\t NumberCards: \n");
	    Map<Card, Integer> treeMap = new TreeMap<Card, Integer>(gameBoard);
	    for (Card card : treeMap.keySet())
		sb.append("\t " + card.getCardName() + "\t\t "
			+ treeMap.get(card) + "\n");
	}
	return sb.toString();
    }

    @Override
    public GameState clone() throws CloneNotSupportedException {
	List<Player> clonePlayers = new ArrayList<Player>();
	List<Card> cloneCards = new ArrayList<Card>();
	//fixed method is directly clone the hashmap
	HashMap<Card, Integer> cloneGameBoard = new HashMap<Card, Integer>(gameBoard);
	HashMap<Card, Integer> cloneBoardEmbargos = new HashMap<Card, Integer>(boardEmbargos);
	for (Player player : players)
	    clonePlayers.add((Player) player.clone());
	for (Card card : cards)
	    cloneCards.add((Card) card.clone());
	//this method is broken
	//for (Card card : gameBoard.keySet())
	//    cloneGameBoard.put((Card) card.clone(), gameBoard.get(card));
	final GameState cloneState = new GameState(cloneCards,clonePlayers,cloneGameBoard,boardEmbargos);
	return  (GameState) cloneState;
    }
}
