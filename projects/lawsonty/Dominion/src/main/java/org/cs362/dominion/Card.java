package org.cs362.dominion;

import java.awt.List;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Scanner;


public enum Card {
    COPPER("Copper", EnumSet.of(CardType.TREASURE), 0, 1, 0, 0, 0, 0),
    SILVER("Silver", EnumSet.of(CardType.TREASURE), 3, 2, 0, 0, 0, 0),
    GOLD("Gold", EnumSet.of(CardType.TREASURE), 6, 3, 0, 0, 0, 0),

    ESTATE("Estate", EnumSet.of(CardType.VICTORY), 2, 0, 1, 0, 0, 0),
    DUCHY("Duchy", EnumSet.of(CardType.VICTORY), 5, 0, 3, 0, 0, 0),
    PROVINCE("Province", EnumSet.of(CardType.VICTORY), 8, 0, 6, 0, 0, 0),

    ADVENTURER("Adventurer", EnumSet.of(CardType.ACTION), 6) {
    	public void play(Game game){
    		super.play(game);
    		ArrayList<Card> treasures = new ArrayList<Card>();
    		int i = 0;
    		while (game.getCurrentPlayer().getDeck().size() != 0 && i != 2){
    			Card c = game.getCurrentPlayer().getDeck().take();
    			if (c.getType().contains(CardType.TREASURE)){
    				treasures.add(c);
    				i++;
    			} else {
    				game.getCurrentPlayer().discard.add(c);
    			}
    		}
    		if (i != 2) {
    			game.getCurrentPlayer().getDeck().add(game.getCurrentPlayer().discard);
    			while (game.getCurrentPlayer().getDeck().size() != 0 && i != 2){
        			Card c = game.getCurrentPlayer().getDeck().take();
        			if (c.getType().contains(CardType.TREASURE)){
        				treasures.add(c);
        				i++;
        			} else {
        				game.getCurrentPlayer().discard.add(c);
        			}
    			}
    		}
    		game.getCurrentPlayer().hand.addAll(treasures);
    	}
    },
    COUNCIL_ROOM("Council Room", EnumSet.of(CardType.ACTION), 5, 0, 0, 4, 1, 0){
    	public void play(Game game){
    		super.play(game);
    		for (Player p: game.playersList){
    			if (p != game.getCurrentPlayer())
    				p.draw();
    		}
    	}
    },
    FEAST("Feast", EnumSet.of(CardType.ACTION), 4){
    	public void play(Game game){
    		super.play(game);
    		game.getCurrentPlayer().playArea.remove(this);
    		if (game.bot()) {
    			for (int i = 5; i >= 0; i--){
    				for (Deck e : game.supply){
    					if (e.peek().cost() == i){
    						game.getCurrentPlayer().discard.add(e.take());
    						return;
    					}
    				}
    			}
    		}
    		else {

    	}
    		}
    },
    GARDENS("Gardens", EnumSet.of(CardType.VICTORY), 4),
    MINE("Mine", EnumSet.of(CardType.ACTION), 5){
    	public void play(Game game){
    		super.play(game);
    		boolean check = false;
    		for (Card c : game.getCurrentPlayer().hand){
    			if (c.getType().contains(CardType.TREASURE)){
    				check = true;
    			}
    		}
    		if (!check){
    			System.out.println("You have no Treasure in your hand. Boo hoo");
    			return;
    		}
    		if (game.bot()){
    			for (Card d : EnumSet.of(Card.COPPER, Card.SILVER)){
    				if (game.getCurrentPlayer().hand.remove(d)){
    					if (d == Card.COPPER)
    						game.getCurrentPlayer().hand.add(Card.SILVER);
    					if (d == Card.SILVER)
    						game.getCurrentPlayer().hand.add(Card.GOLD);
    					return;
    				}
    			}
    		}
    		else {
    			}
    		}
    },
    REMODEL("Remodel", EnumSet.of(CardType.ACTION), 4){
    	public void play (Game game){
    		super.play(game);
    		int value = 0;
    		
    		if (game.bot()){
    			if (game.getCurrentPlayer().hand.size() > 0){
    				value = game.getCurrentPlayer().hand.remove(0).cost();
    				for (int i = value + 2; i >= 0; i--){
        				for (Deck e : game.supply){
        					if (e.peek().cost() == i){
        						game.getCurrentPlayer().discard.add(e.take());
        						return;
        					}
        				}
        			}
    			}
    		} else {
    		
    			}
    		}
    },
    SMITHY("Smithy", EnumSet.of(CardType.ACTION), 4, 0, 0, 3, 0, 0),
    VILLAGE("Village", EnumSet.of(CardType.ACTION), 3, 0, 0, 1, 0, 2),
    BARON("Baron", EnumSet.of(CardType.ACTION), 4, 0, 0, 0, 1, 0){
    	public void play(Game game){
    		super.play(game);
    		if (game.getCurrentPlayer().hand.remove(Card.ESTATE)){
    			game.getCurrentPlayer().addGold(4);
    		} else {
    			game.getCurrentPlayer().discard.add(game.victorySupply.get(0).take());
    		}
    	}
    },
    GREAT_HALL("Great Hall", EnumSet.of(CardType.ACTION, CardType.VICTORY), 3, 0, 1, 1, 0, 1),
    MINION("Minion", EnumSet.of(CardType.ACTION, CardType.ATTACK), 5, 0, 0, 0, 0, 1){
    	public void play(Game game){
    		super.play(game);
    		game.getCurrentPlayer().addGold(2);
    		//Does other stuff
    	}
    },
    STEWARD("Steward", EnumSet.of(CardType.ACTION), 3){
    	public void play(Game game){
    		super.play(game);
    		game.getCurrentPlayer().draw(2);
    	}
    },
    CUTPURSE("Cutpurse", EnumSet.of(CardType.ACTION, CardType.ATTACK), 4, 2, 0, 0, 0, 0){
    	public void play(Game game){
    		super.play(game);
    		for (Player p : game.playersList){
    			if (p != game.getCurrentPlayer()){
    				if( p.hand.remove(Card.COPPER)){
    					p.discard.add(Card.COPPER);
    				}
    			}
    		}
    	}
    },
    EMBARGO("Embargo", EnumSet.of(CardType.ACTION), 2, 2, 0, 0, 0, 0){
    	public void play(Game game){
    		super.play(game);
    		game.kingdomCards.get(0).addEmbargo();
    		game.getCurrentPlayer().playArea.remove(this);
    	}
    },
    SALVAGER("Salvager", EnumSet.of(CardType.ACTION), 4, 0, 0, 0, 1, 0){
    	public void play(Game game){
    		super.play(game);
    		for (Card c : game.getCurrentPlayer().hand){
    			if (c != Card.COPPER){
    				game.getCurrentPlayer().addGold(c.cost());
    				game.getCurrentPlayer().hand.remove(c);
    				return;
    			}
    		}
    	}
    },
    SEA_HAG("Sea Hag", EnumSet.of(CardType.ACTION, CardType.ATTACK), 4){
    	public void play(Game game){
    		super.play(game);
    		for (Player p : game.playersList){
    			if (p != game.getCurrentPlayer()){
    				p.discard.add(p.getDeck().take());
    				p.getDeck().add(Card.CURSE);
    			}
    		}
    	}
    },
    TREASURE_MAP("Treasure Map", EnumSet.of(CardType.ACTION), 4){
    	public void play(Game game){
    		
    		super.play(game);
    		if (game.getCurrentPlayer().hand.remove(Card.TREASURE_MAP)){
    			game.getCurrentPlayer().playArea.remove(Card.TREASURE_MAP);
    			for (int i = 0; i < 4; i++){
    				game.getCurrentPlayer().getDeck().add(game.treasureSupply.get(2).take());
    			}
    		}
    	}
    },
    CURSE("Curse", EnumSet.of(CardType.VICTORY), 0, 0, -1, 0, 0, 0);

    private final String name;
    private final int cost;
    private final EnumSet<CardType> type;
    private final int gold;
    private final int victoryPoints;
    private final int draws;
    private final int buys;
    private final int actions;

    Card (String n, EnumSet<CardType> type, int cost, int gold, int victoryPoints, int draws, int buys, int actions) {
        name = n;
        this.type = type;
        this.cost = cost;
        this.gold = gold;
        this.victoryPoints = victoryPoints;
        this.draws = draws;
        this.buys = buys;
        this.actions = actions;

    }
    Card (String n, EnumSet<CardType> type, int cost){
        this(n, type, cost, 0, 0, 0, 0, 0);}
    
    public String getName(){return name;}
    public int cost(){return cost;}
    public EnumSet<CardType> getType(){return type;}
    public int gold() {return gold;}
    public int victoryPoints() {return victoryPoints;}
    public int draws() {return draws;}
    public int buys() {return buys;}
    public int actions() {return actions;}
    
    public void play(Game game){
    	System.out.println("Player " + game.playersList.indexOf(game.getCurrentPlayer()) + " Playing " + this.getName());
    	game.getCurrentPlayer().addGold(gold);
    	game.getCurrentPlayer().addActions(actions);
    	game.getCurrentPlayer().addBuys(buys);
    	game.getCurrentPlayer().draw(draws);
    	game.getCurrentPlayer().playArea.add(this);
    	game.getCurrentPlayer().hand.remove(this);
    	
    }

    
    
    public static String strToName(String n){
    	return n.replace(" ", "_").toUpperCase();
    }
}