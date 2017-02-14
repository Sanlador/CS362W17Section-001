package cs362.dominion.Resources;
import java.util.ArrayList;

import cs362.dominion.Exceptions.InvalidOptionException;


/**
 * Action cards may come with a set of options, of which a user must choose.
 * For example, the ambassador card requires the user first choose a card from their hand. 
 * After a decision is made they must choose whether they want 1 or 2 cards returned to the supply.
 * This class will handle these sorts of scenarios, allowing users to make chained series of 
 * decisions after the playing of a card.
 * 
 * This is definitely not the cleanest way of implementing user options. It is easy, quick and fits the
 * limited use case.
 * 
 * @author chrisvlessis
 */
public interface Option {

	/**
	 * Description of the option
	 * @return
	 */
	public String description();
	
	/**
	 * Each decision may lead to more options.
	 * @return
	 * @throws InvalidOptionException 
	 */
	public Option decide(int optionNumber) throws InvalidOptionException;
	
	/**
	 * returns the number of options available to choose from
	 * @return
	 */
	public int numOptions();
	
	/**
	 * List of available options in plain text
	 */
	public ArrayList<String> optionDescriptions();
	
}
