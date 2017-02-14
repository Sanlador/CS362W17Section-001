import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;

public class GameState {
    Random rand = new Random();
    int turnNumber;
    int numPlayers;
    int whoseTurn;
    int actions;
    int buys;
    int phase;
    int money;
    String [] possibleKingdomCards = {"Adventurer", "Ambassador", "Baron", "Council Room", "Cutpurse", "Embargo", "Feast", "Gardens", "Great Hall", "Mine", "Minion", "Smithy", "Village"};
    static int numKingdomCards = 13;
    int [] kingdomCardsInPlay;
    Player [] players;
    List<List<Card>> kingdomCards;
    List<List<Card>> treasureCards;
    List<List<Card>> victoryCards;
    List<Card> trashMat;
    
    public GameState(int numPlayers){ //this initializes the GameState for the game to be played in
                                      //lots of repetitive-looking code here for creating the draw piles
        if (2 > numPlayers || 4 < numPlayers){
            System.out.println("Number of players must be between 2 and 4!");
            System.exit(0);
        }
        whoseTurn = rand.nextInt(numPlayers);
        turnNumber = 1;
        this.numPlayers = numPlayers;
        players = new Player[numPlayers];
        for (int i = 0; i < numPlayers; i++){
            players[i] = new Player(true); //everyone is bots
            players[i].hand.addAll(players[i].drawCards(5,rand));
        }
        kingdomCards = createKingdom(numPlayers);
        treasureCards = createTreasureSupply(numPlayers);
        victoryCards = createVictorySupply(numPlayers);
        trashMat = new ArrayList<Card>();
    }
    
    private List<List<Card>> createVictorySupply(int numPlayers){
        List<List<Card>> newVictoryCards = new ArrayList<List<Card>>();
        List<Card> provinceCards = new ArrayList<Card>();
        List<Card> duchyCards = new ArrayList<Card>();
        List<Card> estateCards = new ArrayList<Card>();
        List<Card> curseCards = new ArrayList<Card>();
        for (int i = 0; i < ((numPlayers == 2) ? 8 : 12); i++){
            provinceCards.add(new Province());
            duchyCards.add(new Duchy());
            estateCards.add(new Estate());
        }
        for (int i = 0; i < (numPlayers-1)*10; i ++){
            curseCards.add(new Curse());
        }
        newVictoryCards.add(provinceCards);
        newVictoryCards.add(duchyCards);
        newVictoryCards.add(estateCards);
        newVictoryCards.add(curseCards);
        return newVictoryCards;
    }
    
    private List<List<Card>> createTreasureSupply(int numPlayers){
        List<List<Card>> newTreasureCards = new ArrayList<List<Card>>();
        List<Card> goldCards = new ArrayList<Card>();
        for (int i = 0; i < 30; i++){
            goldCards.add(new Gold());
        }
        List<Card> silverCards = new ArrayList<Card>();
        for (int i = 0; i < 40; i++){
            silverCards.add(new Silver());
        }
        List<Card> copperCards = new ArrayList<Card>();
        for (int i = 0; i < (60 - 7*numPlayers); i++){
            copperCards.add(new Copper());
        }
        newTreasureCards.add(goldCards);
        newTreasureCards.add(silverCards);
        newTreasureCards.add(copperCards);
        return newTreasureCards;
    }
    
    private List<List<Card>> createKingdom(int numPlayers){
        List<Card> newCards;
        List<List<Card>> newKingdomCards = new ArrayList<List<Card>>();
        kingdomCardsInPlay = tenRandomKingdomCards();
        System.out.println("Kingdom Cards in this game:");
        for (int i = 0; i < 10; i++){
            System.out.println((i+1) + ": " + possibleKingdomCards[kingdomCardsInPlay[i]]);
        }
        for (int i = 0; i < 10; i++){
            newCards = new ArrayList<Card>();
            switch (kingdomCardsInPlay[i]){
                case 0: for (int j = 0; j < 10; j++){
                    newCards.add(new Adventurer());
                }
                break;
                case 1: for (int j = 0; j < 10; j++){
                    newCards.add(new Ambassador());
                }
                break;
                case 2: for (int j = 0; j < 10; j++){
                    newCards.add(new Baron());
                }
                break;
                case 3: for (int j = 0; j < 10; j++){
                    newCards.add(new CouncilRoom());
                }
                break;
                case 4: for (int j = 0; j < 10; j++){
                    newCards.add(new Cutpurse());
                }
                break;
                case 5: for (int j = 0; j < 10; j++){
                    newCards.add(new Embargo());
                }
                break;
                case 6: for (int j = 0; j < 10; j++){
                    newCards.add(new Feast());
                }
                break;
                case 7: for (int j = 0; j < ((numPlayers == 2) ? 8 : 12); j++){
                    newCards.add(new Gardens());
                }
                break;
                case 8: for (int j = 0; j < ((numPlayers == 2) ? 8 : 12); j++){
                    newCards.add(new GreatHall());
                }
                break;
                case 9: for (int j = 0; j < 10; j++){
                    newCards.add(new Mine());
                }
                break;
                case 10: for (int j = 0; j < 10; j++){
                    newCards.add(new Minion());
                }
                break;
                case 11: for (int j = 0; j < 10; j++){
                    newCards.add(new Smithy());
                }
                break;
                case 12: for (int j = 0; j < 10; j++){
                    newCards.add(new Village());
                }
                break;
            }
            newKingdomCards.add(newCards);
        }
        return newKingdomCards;
    }
    
    private int [] tenRandomKingdomCards(){ //generates which 10 of the set list of Kingdom Cards to use
        int [] indexArray = new int[10];
        for (int i = 0; i < 10; i++){indexArray[i]=numKingdomCards;}
        int newIndex;
        for (int i = 0; i < 10; i++){
            newIndex = rand.nextInt(numKingdomCards-i);
            for (int j = 0; j < i; j++){ //avoids dupes! This isn't the most efficient way of generating such a list
                                         //I imagine but it works and is uniformly distributed
                if (newIndex >= indexArray[j]){
                    newIndex ++;
                }
            }
            indexArray[i] = newIndex;
            Arrays.sort(indexArray);
        }
        return indexArray;
    }
    
    public Boolean turn(){        //returns true if this is the last turn
        System.out.println("Turn " + turnNumber + ": Player " + whoseTurn + "'s turn");
        actions = 1;
        buys = 1;
        phase = 0;
        money = 0;
        players[whoseTurn].takeTurn(this);
        whoseTurn = (whoseTurn + 1) % numPlayers;
        turnNumber++;
        return gameOver();
    }
    
    private Boolean gameOver(){        //checks to see whether any game-over conditions are met
        //if (turnNumber > 10) return true;
        int emptyPiles = 0;
        if (emptyPile(victoryCards.get(0))){
            return true;
        }
        for (int i = 0; i < 4; i++){
            if (emptyPile(victoryCards.get(i))){
                emptyPiles ++;
            }
        }
        for (int i = 0; i < 3; i++){
            if (emptyPile(treasureCards.get(i))){
                emptyPiles ++;
            }
        }
        for (int i = 0; i < 10; i++){
            if (emptyPile(kingdomCards.get(i))){
                emptyPiles ++;
            }
        }
        if (emptyPiles >= 3){
            return true;
        }
        else return false;
    }
    
    public static Boolean emptyPile(List<Card> pile){
        if (pile.isEmpty()) return true;
        else if (pile.get(0).token) return true; //if there is a token on the pile but no other cards, return true as well
        else return false;
    }
    
    public void printWinner(){
        /*int [] scores = new int[numPlayers];
        int [] order = new int[numPlayers];
        int max = 0;
        int winnerTurns = 0;
        int winner = 0;
        for (int i = 0; i < numPlayers; i++){
            scores[i] = players[i].getVictoryPoints();
            System.out.println("Player " + i + ": " + scores[i] + " Victory Points");
            if (scores[i] > max){
                winner = i;
                winnerTurns = players[i].turnsTaken;
                max = scores[i];
            }
            else if (scores[i] == max){
                if (players[i].turnsTaken < winnerTurns){
                    winner = i;
                    winnerTurns = players[i].turnsTaken;
                    max = scores[i];
                }
            }
        }*/
        int [] winnerData = getWinner();
        System.out.println("Winner: Player " + winnerData[0] + " with " + winnerData[1] + " victory points in " + winnerData[2] + " turns!");
    }
    
    public int [] getWinner(){
        int [] scores = new int[numPlayers];
        int [] order = new int[numPlayers];
        int max = 0;
        int winnerTurns = 0;
        int winner = 0;
        for (int i = 0; i < numPlayers; i++){
            scores[i] = players[i].getVictoryPoints();
            System.out.println("Player " + i + ": " + scores[i] + " Victory Points");
            if (scores[i] > max){
                winner = i;
                winnerTurns = players[i].turnsTaken;
                max = scores[i];
            }
            else if (scores[i] == max){
                if (players[i].turnsTaken < winnerTurns){
                    winner = i;
                    winnerTurns = players[i].turnsTaken;
                    max = scores[i];
                }
            }
        }
        return new int [] {winner,max,winnerTurns};
    }
}