package org.cs362.dominion;



public class Player {
    java.util.List<Card> playArea;
    java.util.List<Card> hand;
    java.util.List<Card> discard;
   
    
    
    private int gold;
    private int buys;
    private int actions;
    private Game game;
    private Deck deck;

    public Player(Game game){
        playArea = new java.util.ArrayList<Card>();
        hand = new java.util.ArrayList<Card>();
        discard = new java.util.ArrayList<Card>();
        this.gold = 0;
        this.actions = 1;
        this.buys = 1;
        deck = new Deck();
        this.game = game;

        game.addPlayer(this);
    }
    public int gold(){
    	return this.gold;
    }
    public int buys() {
    	return buys;
    }
    public int actions(){
    	return actions;
    }
    public boolean hasActions(){
    	if (actions <= 0){
    		return false;
    	} else {
    		for (Card c : hand){
    			if (c.getType().contains(CardType.ACTION)){
    				return true;
    			}
    		}
    		return false;
    	}
    }
    public void addGold(int gold){
    	this.gold += gold;
    }
    public void addBuys(int buys){
    	this.buys += buys;
    }
    public void addActions(int actions){
    	this.actions += actions;
    }
    public void draw(){
    	System.out.println("Player " + game.playersList.indexOf(this) + " deck size " + deck.size());
    	if (deck.size() == 0){
    		//System.out.println(deck.size());
    		deck.add(discard);
    		discard.clear();
    		deck.shuffle();
    	}
    	hand.add(deck.take());
    }
    public void draw(int n){
    	for (int i = 0; i < n; i++){
    		draw();
    	}
    }
    public Deck getDeck(){
    	return deck;
    }
    
    public boolean play(Card c){
    	for (Card e : hand){
    		if (c == e && c.getType().contains(CardType.ACTION)){
    			c.play(game);
    			actions--;
    			return true;
    		}
    	}
    	return false;
    }
    public void spendGold(){

        java.util.List<Card> temp = new java.util.ArrayList<Card>();
    	for (Card c : hand){
    		if (c.getType().contains(CardType.TREASURE)){
    			temp.add(c);
    		}
    	}
    	for (Card c : temp){
    		c.play(game);
    	}
    	temp.clear();
    }
    public boolean buy(Card c){
    	if (c.cost() <= gold) { //add && buys > 0
    		for (Deck d : game.supply){
    			if (d.peek() == c) {
    				discard.add(d.take());
    				buys -= 1;
    				gold -= c.cost();
    				for (int i = 0; i < d.embargo(); i++){
    					discard.add(game.curse.take());
    				}
    				System.out.println("Player " + game.playersList.indexOf(this) + "Bought " + c.getName());
    				return true;
    			}
    		}
    		
    	}
    	return false;
    }
    public void cleanup(){
    	gold = 0;
    	buys = 1;
    	actions = 1;
    	discard.addAll(playArea);
    	discard.addAll(hand);
    	playArea.clear();
    	hand.clear();
    	draw(5);
    }
}