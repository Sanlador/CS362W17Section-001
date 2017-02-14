package cs362.dominion.Cards;

import java.util.ArrayList;

import cs362.dominion.Exceptions.InvalidOptionException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Resources.Supply;

public class Baron extends Card {
	
	public String getDefinition() {
		return "+1 Buy. You may discard an Estate card. If you do, +$4. Otherwise, gain an Estate card.";
	}
	
	public cardTypes getType(){
		return Card.cardTypes.KINGDOM;
	}
	
	/**
	 * There are 10 of each kingdom card
	 */
	public int getInitialSupply(int numPlayers){
		return 10;
	}

	
	public int getCost(){
		return 4;
	}
	
	public int getBuys(){
		return 1;
	}
	
	
	public Option playCard(Game game, ArrayList<Player>players, Supply supply){
		if(game.getCurrentPlayer().deck.hand.handContainsCard(Estate.class)){
			return new discardEstate(game);
		}
		
		return null;
	}
	
	private class discardEstate implements Option {
		
		private Game game;
		
		public discardEstate(Game game){
			this.game = game;
		}
		
		public Option decide(int optionNumber) throws InvalidOptionException{
			if(optionNumber==1){
				if(game.supply.cardsInPile(Estate.class)>0){
					game.getCurrentPlayer().deck.hand.addCard(game.supply.drawCard(Estate.class));
				}
				return null;
			}
			
			if(optionNumber!=0){ throw new InvalidOptionException("Invalid option selected"); }
			this.game.getCurrentPlayer().deck.hand.discard(Estate.class);
			this.game.getCurrentPlayer().deck.hand.addActionPoints(4);
			return null;
		}

		
		public String description() {
			return "Would you like to discard an estate card for +4 coins?";
		}

		public int numOptions() {
			return 2;
		}

		public ArrayList<String> optionDescriptions() {
			ArrayList<String> options =  new  ArrayList<String>();
			options.add("Yes");
			options.add("No");
			return options;
		}
		
		
	}

}
