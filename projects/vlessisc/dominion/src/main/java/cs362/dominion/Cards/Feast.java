package cs362.dominion.Cards;

import java.util.ArrayList;
import java.util.HashMap;

import cs362.dominion.Exceptions.InvalidOptionException;
import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Resources.Supply;

public class Feast extends Card {

	
	public String getDefinition() {
		return "Trash this card. Gain a card costing up to $5.";
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
	
	public Option playCard(Game game, ArrayList<Player>players, Supply supply){
		game.supply.trashCard(game.getCurrentPlayer().deck.hand.removeCardFromHand(Feast.class));
		return new cardToAdd(game);
	}
	
	
	private class cardToAdd implements Option {
		private Game game;
		private HashMap<Integer, Class<? extends Card>> cardOptions = new HashMap<Integer, Class<? extends Card>>();
		
		public cardToAdd(Game game){
			this.game = game;
			int optionNum=0;
			for(Class<? extends Card> cardType : game.supply.supplyCardTypes()){
				// ignore empty piles
				if(game.supply.cardsInPile(cardType)<1){continue;}
				// dont use cards that cost more than 5 coins
				try { if(cardType.newInstance().getCost()>=6){ continue;} } 
				catch (InstantiationException e) {} catch (IllegalAccessException e) {}
				this.cardOptions.put(optionNum, cardType);
				optionNum++;
			}
		}
		
		public String description() {
			return "Which card would you like to add to your hand?";
		}

		public Option decide(int optionNumber) throws InvalidOptionException {
			if(!this.cardOptions.containsKey(optionNumber)){
				throw new InvalidOptionException("Invalid option selected");
			}
			this.game.getCurrentPlayer().deck.hand.addCard(game.supply.drawCard(this.cardOptions.get(optionNumber)));
			return null;
		}

		public int numOptions() {
			return this.cardOptions.size();
		}

		public ArrayList<String> optionDescriptions() {
			ArrayList<String> options = new ArrayList<String>();
			for(int i : this.cardOptions.keySet()){
				try {
					options.add(i, this.cardOptions.get(i).newInstance().getName());
				} catch (InstantiationException e) {} catch (IllegalAccessException e) {}
			}
			return options;
		}
		
	}
	
	
}
