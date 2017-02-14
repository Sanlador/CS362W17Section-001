package cs362.dominion.Cards;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cs362.dominion.Resources.Game;
import cs362.dominion.Resources.Option;
import cs362.dominion.Resources.Player;
import cs362.dominion.Resources.Supply;

public class Ambassador extends Card {
	
		
		public String getDefinition() {
			return "Reveal a card from your hand. Return up to 2 copies of it from your hand to the Supply. Then each other player gains a copy of it.";
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
		
		/**
		 * BUG 2: Ambassador has the wrong cost.
		 */
		public int getCost(){
			//return 1;
			return 4;
		}
		
		public Option playCard(Game game, ArrayList<Player>players, Supply supply){
			// Watch for hands with 0 cards
			if(game.getCurrentPlayer().deck.hand.size()<=0){ return null; }
			Option whichCardOption = new whichCardToReveal(game, players, supply);
			return whichCardOption;
		}
		
		
		/**
		 * Option class allowing user to decide which card to reveal from their hand
		 * @author chrisvlessis
		 */
		private class whichCardToReveal implements Option {
			private Game game;
			private ArrayList<Player> players;
			private Supply supply;
			private ArrayList<Class<? extends Card>> cardOptions;
			
			public whichCardToReveal(Game game, ArrayList<Player>players, Supply supply){
				this.game = game;
				this.players = players;
				this.supply = supply;
				ArrayList<Class<? extends Card>> cardsInHand = this.game.getCurrentPlayer().deck.hand.cardTypesInHand();
				Set<Class<? extends Card>> uniqueCards = new HashSet<Class<? extends Card>>(cardsInHand);
				this.cardOptions = new ArrayList<Class<? extends Card>>(uniqueCards);
				
			}
			
			public String description(){
				return "Which card would you like to reveal from your hand?";
			}
			
			public int numOptions(){
				return this.cardOptions.size();
			}
			
			/**
			 * After a user has selected which card to reveal they will be asked how many of these
			 * cards they would like to return to the supply. This will be done by returning a new
			 * numCardsReturn class
			 */
			public Option decide(int optionNumber) {
				Class<? extends Card> selectedCard = this.cardOptions.get(optionNumber);
				return new numCardsReturn(this.game, this.players, this.supply, selectedCard);
			}

			public ArrayList<String> optionDescriptions() {
				ArrayList<String> options = new ArrayList<String>();
				for(Class<? extends Card> cardType : this.cardOptions){
					try{
						options.add(cardType.newInstance().getName());
					} catch (InstantiationException e) {} catch (IllegalAccessException e) {}
				}
				return options;
			}
		}

		
		/**
		 * This option class allows a user to decide how many cards they would like to return to the supply
		 * @author chrisvlessis
		 */
		private class numCardsReturn implements Option {
			private Game game;
			private ArrayList<Player> players;
			private Supply supply;
			private Class<? extends Card> selectedCardType;
			private ArrayList<Integer> cardOptions;
			
			public numCardsReturn(Game game, ArrayList<Player>players, Supply supply, Class<? extends Card> selectedCardType){
				this.game = game;
				this.players = players;
				this.supply = supply;	
				this.selectedCardType = selectedCardType;
				this.cardOptions = new ArrayList<Integer>();
				this.cardOptions.add(0);
				this.cardOptions.add(1);
				this.cardOptions.add(2);
			}
			
			/**
			 * After the user chooses how many cards they would like to remove, loop through their
			 * hand and remove the card if it exists and return it to the supply.
			 * After this, loop over the players and give them one of these cards from the supply
			 */
			public Option decide(int optionNumber) {
				int toRemove = this.cardOptions.get(optionNumber);
				// Remove cards from players hand 
				for(int i=0; i<toRemove; i++){
					if(!this.game.getCurrentPlayer().deck.hand.handContainsCard(this.selectedCardType)){ return null; }
					// If the hand contains a card of this type take it out and return it to the supply
					if(this.game.getCurrentPlayer().deck.hand.handContainsCard(this.selectedCardType)){
						Card removedCard = this.game.getCurrentPlayer().deck.hand.removeCardFromHand(this.selectedCardType);
						this.supply.addCard(removedCard);
					}
				}
				// Add card from supply to other players hands
				for(Player player : this.players){
					if(player==this.game.getCurrentPlayer()){ continue; }
					// TODO: make sure supply contains card
					if(this.supply.emptyPiles().contains(this.selectedCardType)){continue;}
					player.deck.hand.addCard(this.game.supply.drawCard(this.selectedCardType));
				}
				return null;
			}
			
			public int numOptions(){
				return 3;
			}
			
			public ArrayList<String> optionDescriptions() {
				ArrayList<String> options = new ArrayList<String>();
				for(Integer i : this.cardOptions){
					options.add("Return " + i.toString() + " cards");
				}
				return options;
			}
			
			public String description(){
				return "How many of these cards would you like to return to the supply?";
			}
				
		}
}


