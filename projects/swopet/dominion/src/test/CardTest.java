import static org.junit.Assert.*;
import java.util.Random;
import org.junit.Test;

public class CardTest {
    
    @Test
    public void testAdventurer(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Adventurer card = new Adventurer();
        card.play(game);
        assertEquals(7,game.players[0].hand.size());
    }
    
    @Test
    public void testAmbassador(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Ambassador card = new Ambassador();
        game.players[0].hand.add(new Estate());
        game.players[0].hand.add(new Estate());
        card.play(game);
        assertEquals(5,game.players[0].hand.size()); //this young stunna should remove the Estates from the hand
        
        game.players[0].hand.add(new Curse());
        card.play(game);
        assertEquals(5,game.players[0].hand.size()); //should get rid of the Curse
        
        int handSize = 5;
        for (int i = 0; i < game.players[0].hand.size(); i++){
            if (game.players[0].hand.get(i).name == "Estate"){
                game.players[0].hand.remove(i);
                i--;
                handSize--;
            }
        }
        card.play(game);
        assertEquals(handSize-2,game.players[0].hand.size()); //should get rid of the Coppers
    }
    
    @Test
    public void testBaron(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Baron card = new Baron();
        game.players[0].hand.add(new Estate());
        card.play(game);
        assertEquals(5,game.players[0].hand.size());
        assertEquals(4,game.money);
        game.money = 0;
        int handSize = 5;
        for (int i = 0; i < game.players[0].hand.size(); i++){
            if (game.players[0].hand.get(i).name == "Estate"){
                game.players[0].hand.remove(i);
                i--;
                handSize--;
            }
        }
        card.play(game);
        assertEquals(0,game.money);
        assertEquals(2,game.players[0].discard.size()); //+1 for the Estate discarded earlier, +1 for the Estate gained in this one
    }
    
    @Test
    public void testCouncilRoom(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        CouncilRoom card = new CouncilRoom();
        card.play(game);
        assertEquals(1,game.buys);
        assertEquals(9,game.players[0].hand.size());
        assertEquals(6,game.players[1].hand.size());
    }
    
    @Test
    public void testCutpurse(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Cutpurse card = new Cutpurse();
        card.play(game);
        assertEquals(2,game.money);
        assertEquals(4,game.players[1].hand.size());
    }
    
    @Test
    public void testEmbargo(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Embargo card = new Embargo();
        card.play(game);
        assertEquals(2,game.money);
        assertEquals(1,game.trashMat.size());
    }
    
    @Test
    public void testEmbargoToken(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        EmbargoToken card = new EmbargoToken();
        game.victoryCards.get(0).add(card);
        game.money = 8;
        game.players[0].takeTurn(game);
        assertEquals(9,game.victoryCards.get(3).size()); //player should have snagged a curse card, this is an intentional bug
    }
    
    @Test
    public void testFeast(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Feast card = new Feast();
        card.play(game);
        assertEquals(1,game.trashMat.size());
        assertEquals(1,game.players[0].discard.size()); //player should have gained a card from playing the Feast card, this is an intentional bug
    }
    
    @Test
    public void testGreatHall(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        GreatHall card = new GreatHall();
        card.play(game);
        game.players[0].discard.add(card);
        game.players[0].numCards++;
        assertEquals(1,game.actions);
        assertEquals(6,game.players[0].hand.size());
        assertEquals(4,game.players[0].getVictoryPoints());
    }
    
    @Test
    public void testMine(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Mine card = new Mine();
        card.play(game);
        Boolean containsSilver = false;
        for (int i = 0; i < 5; i++){
            if (game.players[0].hand.get(i).name == "Silver"){
                containsSilver = true;
            }
        }
        assertEquals(true,containsSilver);
        card.play(game);
        Boolean containsGold = false;
        for (int i = 0; i < 5; i++){
            if (game.players[0].hand.get(i).name == "Gold"){
                containsGold = true;
            }
        }
        assertEquals(true,containsGold);
        assertEquals(2,game.trashMat.size()); //intentional bug, doesn't trash the silver when Mine is played
    }
    
    @Test
    public void testMinion(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Minion card = new Minion();
        card.play(game);
        assertEquals(1,game.actions);
        assertEquals(2,game.money);
    }
    
    @Test
    public void testSmithy(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Smithy card = new Smithy();
        card.play(game);
        assertEquals(8,game.players[0].hand.size());
    }
    
    @Test
    public void testVillage(){
        GameState game = new GameState(2);
        game.whoseTurn = 0;
        game.phase = 0;
        Village card = new Village();
        card.play(game);
        assertEquals(6,game.players[0].hand.size());
        assertEquals(2,game.actions);
    }
}
