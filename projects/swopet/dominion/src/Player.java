import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Collections;
import java.util.Iterator;

public class Player {
    List<Card> hand;
    List<Card> discard;
    List<Card> deck;
    List<Card> played;
    Boolean hasAdventurer = false;
    int numCopper = 0;
    int numSilver = 0;
    int numGold = 0;
    int numCards = 0;
    int turnsTaken = 0;
    Boolean isBot;
    Boolean didSomething;
    
    public Player(Boolean bot){
        isBot = bot;
        hand = new ArrayList<Card>();
        discard = new ArrayList<Card>();
        for (int i = 0; i < 3; i++){
            discard.add(new Estate());
            numCards++;
        }
        for (int i = 3; i < 10; i++){
            discard.add(new Copper());
            numCopper++;
            numCards++;
        }
        deck = new ArrayList<Card>();
    }
    
    public void takeTurn(GameState game){
        played = new ArrayList<Card>();
        //System.out.println("Player " + game.whoseTurn + "'s Hand:");
        //revealHand();
        actionPhase(game);
        buyPhase(game);
        cleanPhase(game);
        turnsTaken++;
    }
    
    private void actionPhase(GameState game){
        Card card;
        int ind;
        //System.out.println("Action Phase");
        do
        {
            didSomething = false;
            if (game.actions > 0){
                card = selectAction(game);
                if (card != null){
                    System.out.println("Player " + game.whoseTurn + " played " + card.name);
                    if (card.play(game)){
                        if (hand.remove(card)) played.add(card);
                        didSomething = true;
                        game.actions--;
                    }
                    else {
                        System.out.println("You can't play a " + card.name + " during the Action Phase");
                    }
                }
            }
        } while (game.actions > 0 && didSomething);
        game.phase++;
    }
    
    private void buyPhase(GameState game){
        int embargos;
        List<Card> selection;
        Card gain;
        Card card;
        //System.out.println("Buy Phase");
        
        //play all treasures before selecting something to buy
        
        for (int i = 0; i < hand.size(); i++){
            card = hand.get(i);
            if (card.isTreasure()){
                if (card.play(game)){
                    System.out.println("Player " + game.whoseTurn + " played a " + card.name);
                    hand.remove(card);
                    played.add(card);
                    i--;
                }
            }
        }
        //System.out.println(game.money + "$");
        //loop through buy selection until you didn't buy something or are out of buys
        
        do
        {
            didSomething = false;
            selection = selectBuy(game); //gets which pile to buy from
            if (selection != null){
                //check if the pile you're drawing from has an embargo token
                if (selection.get(selection.size()-1).isToken()){
                    embargos = selection.get(selection.size()-1).value;
                    for (int i = 0; i < embargos; i++){
                        gain = gainFrom(game.victoryCards.get(3));
                        if (gain == null) break; //yon pile is empty, proceed
                        else discard.add(gain);
                    }
                }
                gain = gainFrom(selection);
                if (gain != null){
                    System.out.println("Player " + game.whoseTurn + " bought a " + gain.name);
                    discard.add(gain);
                    game.money -= gain.cost;
                    switch (gain.name){
                        case "Copper": numCopper ++;
                                       break;
                        case "Silver": numSilver ++;
                                       break;
                        case "Gold": numGold ++;
                                       break;
                    }
                    game.buys--;
                    didSomething = true;
                }
            }
            didSomething = false;
        } while (game.buys > 0 && didSomething);
        game.phase++;
    }
    
    private void cleanPhase(GameState game){
        //System.out.println("Clean-Up Phase");
        discard.addAll(played);
        discard.addAll(hand);
        hand.clear();
        played.clear();
        hand.addAll(drawCards(5,game.rand));
    }
    
    public List<Card> drawCards(int numCards, Random rand) {
        List<Card> drawnCards = new ArrayList<Card>();
        Card newCard;
        for (int i = 0; i < numCards; i++){
            newCard = drawCard(rand);
            if (newCard == null){
                break;
            }
            else {
                drawnCards.add(newCard);
            }
        }
        return drawnCards;
    }
    
    public Card drawCard(Random rand) {
        if (deck.isEmpty()){
            if (discard.isEmpty()){
                return null;
            }
            else {
                deck = new ArrayList<Card>(discard); //copy constructor copies the pile from the discardPile to the drawPile
                Collections.shuffle(deck,rand);
                discard = new ArrayList<Card>();
            }
        }
        return(deck.remove(0)); //draw from the deck
    }
    
    public void discard(int ind){
        discard.add(hand.remove(ind));
    }
    
    public void revealHand(){ //reveals hand
        for (int i = 0; i < hand.size(); i++){
            System.out.println(hand.get(i).name);
        }
    }
    
    public Card gainFrom(List <Card> gainPile){
        if (gainPile.isEmpty()){
            return null;
        }
        else {
            numCards++;
            return gainPile.remove(0);
        }
    }
    
    public void trash(Card card, GameState game){
        game.trashMat.add(card);
    }
    
    private List<Card> selectBuy(GameState game){
        if (isBot){
            float avgMoney = (float)(1*numCopper + 2*numSilver + 3*numGold)/(float)numCards;
            if (game.money >= 8){
                if (!GameState.emptyPile(game.victoryCards.get(0))) return game.victoryCards.get(0); //this can never be empty since it being empty is a Game-Over condition
            }
            if (game.money >= 6){
                // test Adventurer functionality
                if (!hasAdventurer){
                    for (int i = 0; i < 10; i++){
                        if (!GameState.emptyPile(game.kingdomCards.get(i))){
                            if (game.kingdomCards.get(i).get(0).name == "Adventurer"){
                                hasAdventurer = true;
                                return game.kingdomCards.get(i);
                            }
                        }
                    }
                }
                //*/
                if (avgMoney > 1.2){ //these numbers can be tweaked/randomized for more variation in bot strategy
                    if (!GameState.emptyPile(game.victoryCards.get(1))) return game.victoryCards.get(1); //selected a duchy
                }
                if (avgMoney < 2.0){
                    if (!GameState.emptyPile(game.treasureCards.get(0))) return game.treasureCards.get(0); //selected a gold
                }
            }
            if (avgMoney < 1.5 && game.money >= 3){
                return game.treasureCards.get(1); //selected a silver
            }
            if (numGold == 0 && numSilver == 0 && numCopper <= 2){
                return game.treasureCards.get(2); //trashed too many coppers, pick one up
            }
        }
        return null;
    }
    
    private Card selectAction(GameState game){
        //TODO: make this more robust(instead of obviously just playing the first card )
        for (int i = 0; i < hand.size(); i++){
            if (hand.get(i).isAction()) return hand.get(i);
        }
        return null;
    }
    
    public int getVictoryPoints(){
        int victoryPoints = 0;
        deck.addAll(discard);
        discard.clear();
        deck.addAll(hand);
        hand.clear();
        Card card;
        if (numCards != deck.size()){
            System.out.println("Inconsistent card count! Did you forget to increment numCards somewhere?");
            System.out.println("numCards: " + numCards);
            System.out.println("deck.size(): " + deck.size());
            return -1;
        }
        for (int i = 0; i < numCards; i++){
            card = deck.get(i);
            if (card.isVictory()){
                if (card.name == "Gardens"){
                    victoryPoints += numCards/10;
                }
                else victoryPoints += card.victoryPoints;
            }
        }
        return victoryPoints;
    }
}