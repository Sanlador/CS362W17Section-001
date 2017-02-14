package org.cs362.dominion;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public List<Player> playersList;
    public List<Deck> kingdomCards;
    public List<Deck> treasureSupply;
    public List<Deck> victorySupply;
    public List<Deck> supply;
    public Deck curse;
    public boolean started;
    private int turns = 1;
    private boolean bot;

    public Game(Card[] cards){
    	bot = true;
    	this.started = false;
    	this.playersList = new ArrayList<Player>();
    	kingdomCards = new ArrayList<Deck>();
    	setKingdomCards(cards);
    	treasureSupply = new ArrayList<Deck>();
    	setTeasureCards();
    	victorySupply = new ArrayList<Deck>();
    	setvictoryCards();
    	supply = new ArrayList<Deck>();
    	supply.addAll(kingdomCards);
    	supply.addAll(treasureSupply);
    	supply.addAll(victorySupply);
    	
    }
    
    private void setvictoryCards() {
		victorySupply.add(new Deck(Card.ESTATE, 12));
		victorySupply.add(new Deck(Card.DUCHY, 12));
		victorySupply.add(new Deck(Card.PROVINCE, 12));		
	}

	private void setTeasureCards() {
		treasureSupply.add(new Deck(Card.COPPER, 60));
		treasureSupply.add(new Deck(Card.SILVER, 40));
		treasureSupply.add(new Deck(Card.GOLD, 30));
	}

	public boolean start() {
        if (playersList.size() < 2 || playersList.size() > 8) return false;
        else {
        	started = true;
        	curse = new Deck(Card.CURSE, (playersList.size() - 1) * 10);
        	turns = 1;
        	for (Player p : playersList){
        		for (int i = 0; i < 7; i++){
        			p.discard.add(treasureSupply.get(0).take());
        		}
        		for (int i = 0; i < 3; i++){
        			p.discard.add(victorySupply.get(0).take());
        		}
        		p.draw(5);
        	}
        	return true;
        }
    }

    public boolean setKingdomCards(Card[] cards){
    	for (int i = 0; i < 10; i++){
    		kingdomCards.add(new Deck(cards[i], cards[i].getType().contains(CardType.TREASURE) ? 12 : 10));
    		
    	}
    	return true;
    }

    public boolean addPlayer(Player newPlayer){
    	if (started)return false;
    	playersList.add(newPlayer);
		return true;
        
    }
    
    public Player getCurrentPlayer(){
    	return playersList.get(turns % playersList.size());
    }
    
    public boolean bot(){
    	return bot;
    }
    
    public boolean ended(){
    	if (victorySupply.get(2).size() < 1){
    		whoWon();
    		return true;
    	}
    	for (Deck d : kingdomCards){
    		if (d.size() < 1){
    			whoWon();
    			return true;
    		}
    	}
    	return false;
    }
    public Player whoWon(){
    	Player top = null;
    	int most = 0;
    	for (Player p : playersList){
    		int score = countVictory(p);
    		System.out.println(score);
    		if (score > most){
    			top = p;
    			most = score;
    		}
    	}
    	System.out.println("Player "  + playersList.indexOf(top) + " has won with a score of " + most);
    	return top;
    }
    public int countVictory(Player p){
    	//p.cleanup();
    	p.getDeck().getCards().addAll(p.discard);
    	p.discard.clear();
    	p.getDeck().getCards().addAll(p.hand);
    	p.hand.clear();
    	p.getDeck().getCards().addAll(p.playArea);
    	p.playArea.clear();
    	int sum = 0;
    	LinkedList<Card> cards = p.getDeck().getCards();
    	for (Card c : cards){
    		if (c.getType().contains(CardType.VICTORY)){
    			if (c == Card.GARDENS){
    				sum += cards.size() / 10;
    			}
    			sum += c.victoryPoints();
    		}
    	}
    	return sum;
    }
    
    public void nextTurn(){
    	System.out.println("End of Turn");
    	turns++;
    }
}


