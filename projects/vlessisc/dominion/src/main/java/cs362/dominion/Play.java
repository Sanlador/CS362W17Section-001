package cs362.dominion;
import java.util.ArrayList;
import java.util.Random;

import cs362.dominion.Resources.*;
import cs362.dominion.Cards.*;
import cs362.dominion.Exceptions.ConfiguationException;
import cs362.dominion.Exceptions.InvalidOptionException;
import cs362.dominion.Exceptions.InvalidSelectionException;


public class Play {
		
	public static Boolean randomizeKingdomCards = false;
	public static void main(String args[]) throws ConfiguationException, InstantiationException, IllegalAccessException, InvalidSelectionException{
		
		ArrayList<Class<? extends Card>> kingdomCards = new ArrayList<Class<? extends Card>>();
		
		
		kingdomCards = Play.randomizeKingdomCards();				
		Game game = new Game(2, kingdomCards);
			
		game._debug_GameState=true;
		game._debug_PlayableCards=true;
		game._debug_CurrentHand=true;
		game._debug_EmptyPiles=true;
		Boolean printCardOptions=true;
		
		
		while(!game.isOver()){
			game._printAllDebugs();
			// Get an array of cards which are playable in the given game state
			ArrayList<Class<? extends Card>> playableCardTypes = game.getPlayableCardTypes();
			// If there is no playable cards, end turn and move to the next phase
			if(playableCardTypes.size()<1){
				game.nextPhase();
				continue;
			}
			// From the list of playable cards, pick a random card to play
			Class<? extends Card> playCard = playableCardTypes.get(new Random().nextInt(playableCardTypes.size()));

			Option cardOptions = game.playCard(playCard);
			selectCardOptions(cardOptions, printCardOptions);

		}
		
		Player winner = game.getLeadingPlayer();
		
		System.out.println("***************************************************************");
		System.out.println("GAME OVER");
		System.out.println("***************************************************************");
		game._printPlayerDecks();
		game._printPlayerScores();
		System.out.println("Winner is: " + winner.getName());
		
	
			
	}
	
	/**
	 * get random list of kingdom cards
	 * @return
	 */
	public static ArrayList<Class<? extends Card>> randomizeKingdomCards(){
		// Add all, remove 3 at random
		ArrayList<Class<? extends Card>> kingdomCards = new ArrayList<Class<? extends Card>>();
		kingdomCards.add(Adventurer.class);
		kingdomCards.add(Ambassador.class);
		kingdomCards.add(Baron.class);
		kingdomCards.add(CouncilRoom.class);
		kingdomCards.add(Cutpurse.class);
		kingdomCards.add(Embargo.class);
		kingdomCards.add(Feast.class);
		kingdomCards.add(Gardens.class);
		kingdomCards.add(GreatHall.class);
		kingdomCards.add(Mine.class);
		kingdomCards.add(Smithy.class);
		kingdomCards.add(Bazaar.class);
		kingdomCards.add(Village.class);
		// Remove 3
		kingdomCards.remove(new Random().nextInt(kingdomCards.size()));
		kingdomCards.remove(new Random().nextInt(kingdomCards.size()));
		kingdomCards.remove(new Random().nextInt(kingdomCards.size()));
		
		return kingdomCards;
	}
	
	public static void selectCardOptions(Option cardOptions, Boolean printOptions) throws InstantiationException, IllegalAccessException{
		while(cardOptions!=null){
			if(printOptions){
				System.out.println("SHOWING " + cardOptions.numOptions() + " OPTIONS FOR " + cardOptions.description());
			}
			for(String optionStr : cardOptions.optionDescriptions()){
				if(printOptions){ 
					System.out.println(cardOptions.optionDescriptions().indexOf(optionStr) + ": " + optionStr); 
				}
			}

			int optionChosen = new Random().nextInt(cardOptions.numOptions());
			if(printOptions){ System.out.println("Chose Option: " + optionChosen); }
			try {
				cardOptions = cardOptions.decide(optionChosen);
			} catch (InvalidOptionException e) {
				if(printOptions){ System.out.println("INVALID OPTION EXCEPTION: " + e.getMessage()); }
			}		
		}
	}
	
	
	
}
