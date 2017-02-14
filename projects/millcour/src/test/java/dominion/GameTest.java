package dominion;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.After;
import org.junit.Before;

public class GameTest {
	
	private Game game;
	
	@Before
	public void setup(){
		this.game = new Game();
		this.game.setGame();
	}

	@After
	public void tearDown(){
		//this.game.close();
		this.game = null;
	}
	
	@Test
	public void playSmithy(){
		game.runAction(Card.CardName.Smithy, 0);
		System.out.println("HERE: " + game.getPlayers().get(0).getDraw().getPile().size());
		System.out.println("HERE: " + game.getPlayers().get(0).getHand().getPile().size());
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		assertEquals(game.getPlayers().get(0).getHand().getPile().size(), 4);
		for(int i=game.getPlayers().get(0).getDraw().getPile().size()-1; i>=0; i--){
			game.getPlayers().get(0).getDraw().getPile().remove(i);
			game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		}
		game.runAction(Card.CardName.Smithy, 0);
		assertEquals(game.getPlayers().get(0).getHand().getPile().size(), 7);
	}

	@Test
	public void playVillage(){
		while(game.getPlayers().get(0).getDraw().getPile().size() > 0){
			game.getPlayers().get(0).getDraw().getPile().remove(0);
		}
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Village, Card.Type.ACTION, 5, 0, 0));
		int compare = game.getPlayers().get(0).getHand().getPile().size();
		game.invokeAction(0);
		int compare2 = game.getPlayers().get(0).getHand().getPile().size();
		assertEquals(game.getPlayers().get(0).getNumActions(), 2);
		assertEquals(compare, compare2);
		game.getPlayers().get(0).getDraw().getPile().add(new Card(Card.CardName.Village, Card.Type.ACTION, 5, 0, 0));
		game.getPlayers().get(0).getDraw().getPile().add(new Card(Card.CardName.Village, Card.Type.ACTION, 5, 0, 0));
		compare = game.getPlayers().get(0).getHand().getPile().size();
		game.invokeAction(0);
		compare2 = game.getPlayers().get(0).getHand().getPile().size();
		assertEquals(compare, compare2);
	}
	
	@Test
	public void playTribute(){
		game.getPlayers().get(1).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		while(game.getPlayers().get(1).getDraw().getPile().size() > 0){
			game.getPlayers().get(1).getDraw().getPile().remove(0);
		}
		int compare = game.getPlayers().get(0).getDraw().getPile().size();
		game.runAction(Card.CardName.Tribute, 0);
		int compare2 = game.getPlayers().get(0).getDraw().getPile().size();
		assertEquals(compare, compare2);
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		for(int i=game.getPlayers().get(0).getDraw().getPile().size()-1; i>=0; i--){
			game.getPlayers().get(0).getDraw().getPile().remove(i);
		}
		compare = game.getPlayers().get(1).getDraw().getPile().size();
		game.runAction(Card.CardName.Tribute, 1);
		compare2 = game.getPlayers().get(1).getDraw().getPile().size();
		assertEquals(compare, compare2);
	}
	
	@Test
	public void playMine(){
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		game.runAction(Card.CardName.Mine, 0);
		for(int i=game.getPlayers().get(0).getHand().getPile().size()-1; i>=0; i--){
				game.getPlayers().get(0).getHand().getPile().remove(i);
		}
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Baron, Card.Type.ACTION, 0, 0, 0));
		game.runAction(Card.CardName.Mine, 0);
		for(int i=game.getPlayers().get(0).getHand().getPile().size()-1; i>=0; i--){
			if(game.getPlayers().get(0).getHand().getPile().get(i).getCardName() == Card.CardName.Copper){
				game.getPlayers().get(0).getHand().getPile().remove(i);
			}
			game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Silver, Card.Type.TREASURE, 0, 0, 0));
		}
		game.runAction(Card.CardName.Mine, 0);
	}
	
	@Test
	public void playGreatHall(){
		int compare = game.getPlayers().get(0).getHand().getPile().size();
		int compare2 = game.getPlayers().get(0).getNumActions();
		game.runAction(Card.CardName.GreatHall, 0);
		assertEquals(compare, compare);
		assertEquals(compare2, 1);
		while(game.getPlayers().get(0).getDraw().getPile().size() >  0){
			game.getPlayers().get(0).getDraw().getPile().remove(0);
		}
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		compare = game.getPlayers().get(0).getHand().getPile().size();
		compare2 = game.getPlayers().get(0).getNumActions();
		game.runAction(Card.CardName.GreatHall, 0);
		assertEquals(compare, 1);
		assertEquals(compare2, 2);
	}
	
	@Test
	public void playFeast(){
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Feast, Card.Type.ACTION, 0, 0, 0));
		int compare = game.getPlayers().get(0).getDiscard().getPile().size();
		game.runAction(Card.CardName.Feast, 0);
		int compare2 = game.getPlayers().get(0).getDiscard().getPile().size();
		assertEquals(compare, compare2);
	}
	
	@Test
	public void playEmbargo(){
		game.runAction(Card.CardName.Embargo, 0);
		assertEquals(game.getPlayers().get(0).getCoins(), 2);
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 1));
		game.getPlayers().get(0).setCoins(0);
		game.runAction(Card.CardName.Embargo, 0);
		assertEquals(game.getPlayers().get(0).getCoins(), 2);
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Embargo, Card.Type.TREASURE, 0, 0, 1));
		game.getPlayers().get(0).setCoins(0);
		game.runAction(Card.CardName.Embargo, 0);
		assertEquals(game.getPlayers().get(0).getCoins(), 2);
	}
	
	@Test
	public void playCutpurse(){
		int numCopper = 0;
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.runAction(Card.CardName.Cutpurse, 0);
		for(int i=0; i<game.getPlayers().get(1).getHand().getPile().size(); i++){
			if(game.getPlayers().get(0).getHand().getPile().get(i).getCardName() == Card.CardName.Copper){
				numCopper++;
			}
		}
		assertEquals(numCopper, 0);
		assertEquals(game.getPlayers().get(0).getCoins(), 2);
		game.runAction(Card.CardName.Cutpurse, 1);
		assertEquals(game.getPlayers().get(0).getCoins(), 2);
	}
	
	@Test
	public void playCouncilRoom(){
		int compare;
		int compare2;
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		for(int i=0; i<game.getPlayers().get(0).getDraw().getPile().size(); i++){
			game.getPlayers().get(0).getDraw().getPile().remove(0);
		}
		compare = game.getPlayers().get(0).getHand().getPile().size();
		game.runAction(Card.CardName.CouncilRoom, 0);
		compare2 = game.getPlayers().get(0).getHand().getPile().size()-4;
		assertEquals(compare, compare2);
		game.runAction(Card.CardName.CouncilRoom, 1);
	}
	
	@Test
	public void playBaron(){
		int numEstate = 0;
		game.getPlayers().get(0).setNumBuys(0);
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Estate, Card.Type.VICTORY, 0, 0, 0));
		game.runAction(Card.CardName.Baron, 0);
		assertEquals(game.getPlayers().get(0).getNumBuys(), 1);
		for(int i=0; i<game.getPlayers().get(0).getHand().getPile().size(); i++){
			numEstate++;
		}
		assertEquals(numEstate, 0);
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.runAction(Card.CardName.Baron, 0);
		assertEquals(game.getPlayers().get(0).getCoins(), 4);
	}
	
	@Test
	public void playAmbassador(){
		for(int i=0; i<game.getPlayers().get(0).getHand().getPile().size(); i++){
			game.getPlayers().get(0).getHand().getPile().remove(0);
		}
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.getPlayers().get(0).getHand().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.runAction(Card.CardName.Ambassador, 0);
		assertEquals(game.getPlayers().get(0).getHand().getPile().size(), 0);
	}
	
	@Test
	public void playAdventurer(){
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.getPlayers().get(0).getDiscard().getPile().add(new Card(Card.CardName.Copper, Card.Type.TREASURE, 0, 0, 0));
		game.runAction(Card.CardName.Adventurer, 0);
		assertEquals(game.getPlayers().get(0).getHand().getPile().size(), 2);
		for(int i=game.getPlayers().get(0).getDraw().getPile().size()-1; i>=0; i--){
			game.getPlayers().get(0).getDraw().getPile().remove(i);
		}
		game.runAction(Card.CardName.Adventurer, 0);
	}
	
	@Test
	public void checkingForEnd(){
		for(int i=game.getBoard().get(2).getPile().size()-1; i>=0 ; i--){
			game.getBoard().get(2).getPile().remove(i);
		}
		assertEquals(game.getBoard().get(2).getPile().size(), 0);
		assertEquals(game.checkForEnd(), false);
		for(int i=9; i>=0; i--){
			game.getBoard().get(13).getPile().remove(i);
			game.getBoard().get(14).getPile().remove(i);
			game.getBoard().get(15).getPile().remove(i);
			game.getBoard().get(2).getPile().add(new Card(Card.CardName.Province, Card.Type.VICTORY, 0, 0, 0));
		}
		assertEquals(game.checkForEnd(), false);
		game.fillActionCards();
		assertEquals(game.checkForEnd(), true);
	}
	
	@Test
	public void invokeBuyRun(){
		game.invokeBuy(0);
		assertEquals(game.getPlayers().get(0).getHand().getPile().size(), 1);
	}
	
	@Test
	public void takeTurnRun(){
		game.takeTurn(0);
		assertEquals(game.getPlayers().get(0).getNumBuys(), 0);
		assertEquals(game.getPlayers().get(0).getNumActions(), 0);
	}
	
	@Test
	public void playGameRun(){
		game.playGame();
		int out = 0;
		if(game.getBoard().get(2).getPile().size() > 0){
			for(int i=game.getBoard().size()-1; i>=0; i--){
				if(game.getBoard().get(i).getPile().size() == 0){
					out++;
				}
			}
			assertEquals(out, 3);
		}else{
			assertEquals(game.getBoard().get(2).getPile().size(), 0);
		}
	}
	
	@Test
	public void checkWhoWon(){
		int points = 0;
		int numGardens = 0;
		int totalCards = 0;
		int GardenPoints = 0;
		game.playGame();
		game.whoWon();
		for(int i=0; i<game.getPlayers().get(0).getHand().getPile().size(); i++){
			points += game.getPlayers().get(0).getHand().getPile().get(i).getScore();
			if(game.getPlayers().get(0).getHand().getPile().get(i).getCardName() == Card.CardName.Gardens)
				numGardens++;
			totalCards++;
		}
		for(int i=0; i<game.getPlayers().get(0).getDraw().getPile().size(); i++){
			points += game.getPlayers().get(0).getDraw().getPile().get(i).getScore();
			if(game.getPlayers().get(0).getDraw().getPile().get(i).getCardName() == Card.CardName.Gardens)
				numGardens++;
			totalCards++;
		}
		for(int i=0; i<game.getPlayers().get(0).getDiscard().getPile().size(); i++){
			points += game.getPlayers().get(0).getDiscard().getPile().get(i).getScore();
			if(game.getPlayers().get(0).getDiscard().getPile().get(i).getCardName() == Card.CardName.Gardens)
				numGardens++;
			totalCards++;
		}
		GardenPoints = (totalCards/10)*numGardens;
		points += GardenPoints;
		assertEquals(points, game.getPlayers().get(0).getVictory());
		points = 0;
		numGardens = 0;
		totalCards = 0;
		for(int i=0; i<game.getPlayers().get(1).getHand().getPile().size(); i++){
			points += game.getPlayers().get(1).getHand().getPile().get(i).getScore();
			if(game.getPlayers().get(1).getHand().getPile().get(i).getCardName() == Card.CardName.Gardens)
				numGardens++;
			totalCards++;
		}
		for(int i=0; i<game.getPlayers().get(1).getDraw().getPile().size(); i++){
			points += game.getPlayers().get(1).getDraw().getPile().get(i).getScore();
			if(game.getPlayers().get(1).getDraw().getPile().get(i).getCardName() == Card.CardName.Gardens)
				numGardens++;
			totalCards++;
		}
		for(int i=0; i<game.getPlayers().get(1).getDiscard().getPile().size(); i++){
			points += game.getPlayers().get(1).getDiscard().getPile().get(i).getScore();
			if(game.getPlayers().get(1).getDiscard().getPile().get(i).getCardName() == Card.CardName.Gardens)
				numGardens++;
			totalCards++;
		}
		GardenPoints = (totalCards/10)*numGardens;
		points += GardenPoints;
		assertEquals(points, game.getPlayers().get(1).getVictory());
	}
	
	@Test
	public void callToMain(){
		//will run more calls to all previous functions
		game.main(null);
	}
}
