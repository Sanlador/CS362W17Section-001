import java.util.Arrays;
import java.util.List;

public enum Card {
	
	/*something("type", cost, coins, victorypoints, embargotokens); */
	
	/*TREASURES*/
	copper("treasure", 0, 1, 0, 0),
	silver("treasure", 3, 2, 0, 0),
	gold("treasure", 6, 3, 0, 0),
	
	/*VICTORY*/
	estate("victory", 2, 1, 1, 0),
	duchy("victory", 5, 0, 3, 0),
	province("victory", 8, 0, 6, 0),
	curse("victory", 0, 0, -1, 0),
	gardens("victory", 4, 0, 0, 0), 
	
	/*ACTIONS*/
	adventurer("action", 6, 0, 0, 0),
	ambassador("action,attack", 3, 0, 0, 0),
	baron("action", 4, 0, 0, 0),
	council_room("action", 4, 0, 3, 0),
	cutpurse("action,attack", 4, 0, 0, 0),
	embargo("action", 2, 0, 0, 0),
	feast("action", 4, 0, 0, 0),
	great_hall("action,victory", 3, 0, 1, 0),
	mine("action", 5, 0, 0, 0),
	smithy("action,", 4, 0, 0, 0),
	village("action", 3, 0, 0, 0),
	seahag("action,attack", 4, 0, 0, 0);
	
	
	private String type;
	private int cost;
	private int coins;
	private int victory;
	private int embargotokens;
	
	Card(String type, int cost, int coins, int victory, int embargotokens)
	{
		this.type = type;
		this.cost = cost;
		this.coins = coins;
		this.victory = victory;
		this.embargotokens = embargotokens;
	}
	
	public int getCost() { return this.cost; }
    public int getCoins() { return this.coins; }
    public int getVictory() { return this.victory; }
    public void setVictory(int v) {this.victory = v;}
    public int getEmbargo() { return this.embargotokens; }
    public void addEmbargoToCard(){ this.embargotokens += 1; }
    public List<String> getTypes() { return Arrays.asList(this.type.split(",")); }
}
