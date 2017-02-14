import java.util.Arrays;
import java.util.List;

public enum Card {

	   curse("victory", 10, 0, -1),
	   estate("victory", 2, 0, 1),
	   duchy("victory", 5, 0, 3),
	   province("victory", 8, 1, 6),

	   copper("treasure", 0, 1, 0),
	   silver("treasure", 3, 2, 0),
	   gold("treasure", 6, 3, 0),

	   adventurer("action", 6, 0, 0),
	   /* If no/only 1 treasure found, stop when full deck seen */
	   ambassador("action,attack", 3, 0, 0), /* choice1 = hand#, choice2 = number to return to supply */
	   baron("action", 4, 0, 0), /* choice1: boolean for discard of estate */
	   /* Discard is always of first (lowest index) estate */
	   council_room("action", 5, 0, 0),
	   cutpurse("action,attack", 4, 0, 0),
	   embargo("action", 2, 0, 0), /* choice1 = supply# */
	   feast("action", 4, 0, 0), /* choice1 is supply # of card gained) */
	   gardens("action", 4, 0, 0),
	   great_hall("action,victory", 3, 0, 1),
	   mine("action", 5, 0, 0), /* choice1 is hand# of money to trash, choice2 is supply# of
		    money to put in hand */
	   remodel("action", 4, 0, 0), /* choice1 is hand# of card to remodel, choice2 is supply# */
	   smithy("action", 4, 0, 0),
	   salvager("action", 3, 0, 0);

	   private String type;
	   private int cost;
	   private int treasure;
	   private int victory;
	   Card(String type, int cost, int treasure, int victory) 
	   {
		   this.type = type; 
		   this.cost = cost;
		   this.treasure = treasure;
		   this.victory = victory;
	   }
       public int getCost() { return this.cost; }
       public int getTreasure() { return this.treasure; }
       public int getVictory() { return this.victory; }
       public List<String> getTypes() { return Arrays.asList(this.type.split(",")); }
       public void setVictory(int t) { this.victory = t; }
} 
