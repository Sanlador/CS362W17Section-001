/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dominion;
import java.util.Random; 

/**
 *
 * @author Regex
 */
public class Dominion {
    
    int MAX_PLAYERS = 4;
    int MAX_HAND = 500;
    int MAX_DECK = 500;
    boolean DEBUG = false;
    Random rand = new Random(0); // to get numbers between 0 and 1 thereby replicating the C++ code
    
    
    
    public GameState initializeGame(int numPlayers, int[] kingdomCards, int randomSeed) {    
       
    
    GameState output = new GameState();
    output.numPlayers = numPlayers;

//  //set up random number generator
//  SelectStream(1);
//  PutSeed((long)randomSeed);
//  
  //check number of players
  if (numPlayers > output.MAX_PLAYERS || numPlayers < 2)
    {
        
      System.out.println("");
      System.out.println("wrong number of players: " + numPlayers);
      System.out.println("setting number of players to 2");
      System.out.println("");
      numPlayers = 2;
      //return -1;
    }
  
    //check selected kingdom cards are different
    for (int i = 0; i < 10; i++)      
        for (int j = 0; j < 10; j++)          
            if (j != i && kingdomCards[j] == kingdomCards[i])              
               System.out.println("kingdom cards are not unique! # " + j + " and # " + i + " are the same.");
      


//  //initialize supply
//  ///////////////////////////////
//
//  //set number of Curse cards
    output.supplyCount[Card.curse.ordinal()] = (numPlayers-1)*10;
//
//  //set number of Victory cards
    if (numPlayers == 2){
        output.supplyCount[Card.estate.ordinal()] = 8;
        output.supplyCount[Card.duchy.ordinal()] = 8;
        output.supplyCount[Card.province.ordinal()] = 8;        
    }
    else {
        output.supplyCount[Card.estate.ordinal()] = 12;
        output.supplyCount[Card.duchy.ordinal()] = 12;
        output.supplyCount[Card.province.ordinal()] = 12;  
        
    }
    
//  //set number of Treasure cards
    output.supplyCount[Card.copper.ordinal()] = 60 - (7 * numPlayers);
    output.supplyCount[Card.silver.ordinal()] = 40;
    output.supplyCount[Card.gold.ordinal()] = 30;
  //set number of Kingdom cards
  for (int i = Card.adventurer.ordinal(); i <= Card.treasure_map.ordinal(); i++)       	//loop all cards
        for (int j = 0; j < 10; j++)           		//loop chosen cards
            if (kingdomCards[j] == i)
            {
                //check if card is a 'Victory' Kingdom card
                if (kingdomCards[j] == Card.great_hall.ordinal() || kingdomCards[j] == Card.gardens.ordinal())
                    if (numPlayers == 2) output.supplyCount[i] = 8; 
                    else output.supplyCount[i] = 12; 		
                else output.supplyCount[i] = 10;		
                break;
            }
            //card is not in the set choosen for the game      
            else output.supplyCount[i] = -1;
//
//  ////////////////////////
//  //supply intilization complete
//  //set player decks
    for (int i = 0; i < numPlayers; i++) {
      for (int j = 0; j < 3; j++){
	  output.deck[i][j] = Card.estate.ordinal();
          output.deckCount[i]++;	  
	}
      for (int j = 3; j < 10; j++){
	  output.deck[i][j] = Card.copper.ordinal();
          output.deckCount[i]++;	  		
	}
    }
  //shuffle player decks
  for (int i = 0; i < numPlayers; i++)
      if ( shuffle(i, output) < 0 ) 
          System.out.println("shuffle failed- deck empty");



  //draw player 1 hands
  for (int i = 0; i < output.numPlayers; i++)
    {  
        //initialize hand size to zero
        output.handCount[i] = 0;
        output.discardCount[i] = 0;
        //draw 5 cards
        for (int j = 0; j < 5; j++) drawCard(i, output);
    }
  
//  //set embargo tokens to 0 for all supply piles
    for (int i = 0; i <= Card.treasure_map.ordinal(); i++)
        output.embargoTokens[i] = 0;
//  //initialize first player's turn
    output.outpostPlayed = 0;
    output.phase = 0;
    output.numActions = 1;
    output.numBuys = 1;
    output.playedCardCount = 0;
    output.whoseTurn = 0;
    //output.handCount[output.whoseTurn] = 0;
    

    updateCoins(output.whoseTurn, output, 0); 
    //System.out.println("# province:" + output.supplyCount[Card.province.ordinal()]);
    return output;
    
    }
    public int shuffle(int player, GameState game) {
      if (game.deckCount[player] < 1) return -1;    
      
      // randomly arranging the deck: rand.nextDouble generates a number between 0 and 1
      // and then we multiply it by the total number of cards, rounding down to get a random card
      
      // loop selects a random card, puts it in the new deck, removes it, and
      // then shifts the rest down one so there are no empty spaces. 
      
      int newDeckPos = 0;
      int[] newDeck = new int[MAX_DECK];
      while (game.deckCount[player] > 0) {
        int card = rand.nextInt(game.deckCount[player]);
        newDeck[newDeckPos] = game.deck[player][card];
        newDeckPos++;
        for (int i = card; i < game.deckCount[player]-1; i++) 
            game.deck[player][i] = game.deck[player][i+1];
        game.deckCount[player]--;
      }
      for (int i = 0; i < newDeckPos; i++) {
        game.deck[player][i] = newDeck[i];
        game.deckCount[player]++;
      }

      return 0;
    } 
    public int drawCard(int player, GameState game){	
      if (game.deckCount[player] <= 0){//Deck is empty

        //Step 1 Shuffle the discard pile back into a deck
        
        //Move discard to deck
        for (int i = 0; i < game.discardCount[player];i++){
          game.deck[player][i] = game.discard[player][i];
          game.discard[player][i] = -1;
        }

        game.deckCount[player] = game.discardCount[player];
        game.discardCount[player] = 0;//Reset discard

        //Shuffle the deck
        shuffle(player, game);//Shuffle the deck up and make it so that we can draw

        if (DEBUG) System.out.println("Deck count now: " + game.deckCount[player]);

        game.discardCount[player] = 0;

        //Step 2 Draw Card
        int count = game.handCount[player];//Get current player's hand count
        
        if (DEBUG) System.out.println("Current hand count: " + count);        

        int deckCounter = game.deckCount[player];//Create a holder for the deck count

        if (deckCounter == 0) return -1;

        game.hand[player][count] = game.deck[player][deckCounter - 1];//Add card to hand
        game.deckCount[player]--;
        game.handCount[player]++;//Increment hand count
      }

      else{
        int count = game.handCount[player];//Get current hand count for player
        int deckCounter;
        
        if (DEBUG) System.out.println("Current hand count: " + count);        

        deckCounter = game.deckCount[player];//Create holder for the deck count
        game.hand[player][count] = game.deck[player][deckCounter - 1];//Add card to the hand
        game.deckCount[player]--;
        game.handCount[player]++;//Increment hand count
      }

      return 0;
    }
    public int handCard(int handPos, GameState game) {
        int currentPlayer = whoseTurn(game);
        return game.hand[currentPlayer][handPos];
    }
    public int numHandCards(GameState game) {
      return game.handCount[whoseTurn(game)];
    }
    public int whoseTurn(GameState game){
        return game.whoseTurn;
    }
    public int playCard(int handPos, int choice1, int choice2, int choice3, GameState Game)    {	
      int card;
      int coin_bonus = 0; 		//tracks coins gain from actions

      if (Game.phase != 0) return -1; //check if it is the right phase
      if (Game.numActions < 1 ) return -1;  //check if player has enough actions       
      
      card = handCard(handPos, Game); //get card played

      //check if selected card is an action
      if (card < Card.adventurer.ordinal() || card > Card.treasure_map.ordinal())
          return -1;
        

      //play card
        
      if ( cardEffect(card, choice1, choice2, choice3, Game, handPos, coin_bonus) < 0 )
          return -1;

      //reduce number of actions
      Game.numActions--;

      //update coins (Treasure cards may be added with card draws)
      updateCoins(Game.whoseTurn, Game, coin_bonus);

      return 0;
    }
    public int updateCoins(int player, GameState game, int bonus)
    {
      //reset coin count
      game.coins = 0;

      //add coins for each Treasure card in player's hand
      for (int i = 0; i < game.handCount[player]; i++)
          if (game.hand[player][i] == Card.copper.ordinal()) game.coins += 1;
          else if (game.hand[player][i] == Card.silver.ordinal()) game.coins += 2;
          else if (game.hand[player][i] == Card.gold.ordinal()) game.coins += 3;	

      //add bonus
      game.coins += bonus + game.cardCoinBonus;

      return 0;
    }
    public int cardEffect(int card, int choice1, int choice2, int choice3, GameState Game, int handPos, int bonus){
    int currentPlayer = whoseTurn(Game);
    int nextPlayer = currentPlayer + 1;

    int[] tributeRevealedCards = new int[] {-1, -1};
    int[] temphand = new int[Game.MAX_HAND];// moved above the if statement
    int drawntreasure=0;
    int cardDrawn;
    int z = 0;// this is the counter for the temp hand
    if (nextPlayer > (Game.numPlayers - 1))
      nextPlayer = 0;

    //uses switch to select card and perform actions
    switch( Card.values()[card] ) {
      case adventurer:
        while(drawntreasure<2){
            if (Game.deckCount[currentPlayer] <1)//if the deck is empty we need to shuffle discard and add to deck
              shuffle(currentPlayer, Game);
            drawCard(currentPlayer, Game);
            cardDrawn = Game.hand[currentPlayer][Game.handCount[currentPlayer]-1];//top card of hand is most recently drawn card.
            if (cardDrawn == Card.copper.ordinal() || cardDrawn == Card.silver.ordinal() || cardDrawn == Card.gold.ordinal())
              drawntreasure++;
            else{
              temphand[z]=cardDrawn;
              Game.handCount[currentPlayer]--; //this should just remove the top card (the most recently drawn one).
              z++;
            }
        }
        while(z-1>=0){
          Game.discard[currentPlayer][Game.discardCount[currentPlayer]++]=temphand[z-1]; // discard all cards in play that have been drawn
          z=z-1;
        }
        return 0;

      case ambassador:
        int k = 0;		//used to check if player has enough cards to discard

        if (choice2 > 2 || choice2 < 0)
            return -1;		
        if (choice1 == handPos)
            return -1;
        for (int i = 0; i < Game.handCount[currentPlayer]; i++)
            if (i != handPos && i == Game.hand[currentPlayer][choice1] && i != choice1)
                k++;
        if (k < choice2)
            return -1;	
        if (DEBUG) 
          System.out.println("Player " + currentPlayer + " reveals card number: " + Game.hand[currentPlayer][choice1]);

        //increase supply count for choosen card by amount being discarded
        Game.supplyCount[Game.hand[currentPlayer][choice1]] += choice2;

        //each other player gains a copy of revealed card
        for (int i = 0; i < Game.numPlayers; i++)
            if (i != currentPlayer)
                gainCard(Game.hand[currentPlayer][choice1], Game, 0, i);

        //discard played card from hand
        discardCard(handPos, currentPlayer, Game, 0);			

        //trash copies of cards returned to supply
        for (k = 0; k < choice2; k++)
            for (int i = 0; i < Game.handCount[currentPlayer]; i++)
                if (Game.hand[currentPlayer][i] == Game.hand[currentPlayer][choice1]){
                    discardCard(i, currentPlayer, Game, 1);
                    break;
                  }

        return 0;
        
      case baron:
        Game.numBuys++;//Increase buys by 1!
        if (choice1 > 0){//Boolean true or going to discard an estate
          int p = 0;//Iterator for hand!
          boolean card_not_discarded = true;//Flag for discard set!
          while(card_not_discarded){
            if (Game.hand[currentPlayer][p] == Card.estate.ordinal()){//Found an estate card!
              Game.coins += 4;//Add 4 coins to the amount of coins
              Game.discard[currentPlayer][Game.discardCount[currentPlayer]] = Game.hand[currentPlayer][p];
              Game.discardCount[currentPlayer]++;
              for (;p < Game.handCount[currentPlayer]; p++){
                Game.hand[currentPlayer][p] = Game.hand[currentPlayer][p+1];
              }
              Game.hand[currentPlayer][Game.handCount[currentPlayer]] = -1;
              Game.handCount[currentPlayer]--;
              card_not_discarded = false;//Exit the loop
            }
            else if (p > Game.handCount[currentPlayer]){
              if(DEBUG) {
                System.out.println("No estate cards in your hand, invalid choice\n");
                System.out.println("Must gain an estate if there are any\n");
              }
              if (supplyCount(Card.estate.ordinal(), Game) > 0){
                gainCard(Card.estate.ordinal(), Game, 0, currentPlayer);
                Game.supplyCount[Card.estate.ordinal()]--;//Decrement estates
                if (supplyCount(Card.estate.ordinal(), Game) == 0){
                  isGameOver(Game);
                }
              }
              card_not_discarded = false;//Exit the loop
            }

            else{
              p++;//Next card
            }
          }
        }

        else{
          if (supplyCount(Card.estate.ordinal(), Game) > 0){
            gainCard(Card.estate.ordinal(), Game, 0, currentPlayer);//Gain an estate
            Game.supplyCount[Card.estate.ordinal()]--;//Decrement Estates
            if (supplyCount(Card.estate.ordinal(), Game) == 0){
              isGameOver(Game);
            }
          }
        }


        return 0;

      case cutpurse:

        updateCoins(currentPlayer, Game, 2);
        for (int i = 0; i < Game.numPlayers; i++)
          {
            if (i != currentPlayer)
              {
                for (int w = 0; w < Game.handCount[i]; w++)
                  {
                    if (Game.hand[i][w] == Card.copper.ordinal())
                      {
                        discardCard(w, i, Game, 0);
                        break;
                      }
                    if (w == Game.handCount[i])
                      {
                        for (int f = 0; f < Game.handCount[i]; f++)
                            if (DEBUG)
                              System.out.println("Player " + i + " reveals card number " + Game.hand[i][f]);
                        break;
                      }		
                  }

              }

          }				

        //discard played card from hand
        discardCard(handPos, currentPlayer, Game, 0);			

        return 0;

      case council_room:
        //+4 Cards
        for (int i = 0; i < 4; i++)
            drawCard(currentPlayer, Game);

        //+1 Buy
        Game.numBuys++;

        //Each other player draws a card
        for (int i = 0; i < Game.numPlayers; i++)
            if ( i != currentPlayer )
                drawCard(i, Game);

        //put played card in played card pile
        discardCard(handPos, currentPlayer, Game, 0);

        return 0;

      case embargo: 
        //+2 Coins
        Game.cardCoinBonus = + 2;

        //see if selected pile is in play
        if ( Game.supplyCount[choice1] == -1 )
            return -1;

        //add embargo token to selected supply pile
        Game.embargoTokens[choice1]++;

        //trash card
        discardCard(handPos, currentPlayer, Game, 1);		
        return 0;
        
      case feast:
        //gain card with cost up to 5  

        if (supplyCount(choice1, Game) <= 0){
          if (DEBUG) {
              System.out.println("None of that card left, sorry!\n");
              System.out.println("Cards Left: "+supplyCount(choice1, Game)+"\n");            
          }
          return -1;
        }
        else if (5 < getCost(choice1)){

          if (DEBUG) { 
              System.out.println("Coins: " + Game.coins +" < " + getCost(choice1));            
              System.out.println("That card is too expensive!\n");
          }
          return -1;
        }
        else{
          if (DEBUG)
            System.out.println("Total Deck Count: " + (Game.handCount[currentPlayer] + Game.deckCount[currentPlayer] + Game.discardCount[currentPlayer]));            

          gainCard(choice1, Game, 0, currentPlayer);//Gain the card


          if (DEBUG)
            System.out.println("Total Deck Count: " + (Game.handCount[currentPlayer] + Game.deckCount[currentPlayer] + Game.discardCount[currentPlayer]));          
        
        }   
        discardCard(handPos, currentPlayer, Game, 1);   


        return 0;

      case gardens:
        return -1;


      case great_hall:
        //+1 Card
        drawCard(currentPlayer, Game);

        //+1 Actions
        Game.numActions++;

        //discard card from hand
        discardCard(handPos, currentPlayer, Game, 0);
        return 0;

      case minion:
        //+1 action
        Game.numActions++;

        //discard card from hand
        discardCard(handPos, currentPlayer, Game, 0);

        if (choice1 != 0)
            Game.coins = Game.coins + 2;

        else if (choice2 != 0)		//discard hand, redraw 4, other players with 5+ cards discard hand and draw 4
          {
            //discard hand
            while(numHandCards(Game) > 0)
                discardCard(handPos, currentPlayer, Game, 0);

            //draw 4
            for (int i = 0; i < 4; i++)
                drawCard(currentPlayer, Game);

            //other players discard hand and redraw if hand size > 4
            for (int i = 0; i < Game.numPlayers; i++) {
                if (i != currentPlayer) {
                    if ( Game.handCount[i] > 4 ){
                        //discard hand
                        while( Game.handCount[i] > 0 ) 
                            discardCard(handPos, i, Game, 0);                          

                        //draw 4
                        for (int q = 0; q < 4; q++) 
                            drawCard(i, Game);
                          
                      }
                  }
              }

          }
        return 0;
        /* not implemented because we only do 10 + 3 of my choice
      case mine:
        int j = Game.hand[currentPlayer][choice1];  //store card we will trash

        if (Game.hand[currentPlayer][choice1] < Card.copper.ordinal() || Game.hand[currentPlayer][choice1] > Card.gold.ordinal())
            return -1;

        if (choice2 > Card.treasure_map.ordinal() || choice2 < Card.curse.ordinal())
            return -1;

        if ( (getCost(Game.hand[currentPlayer][choice1]) + 3) > getCost(choice2) )
            return -1;

        gainCard(choice2, Game, 2, currentPlayer);

        //discard card from hand
        discardCard(handPos, currentPlayer, Game, 0);

        //discard trashed card
        for (int i = 0; i < Game.handCount[currentPlayer]; i++)
          {
            if (Game.hand[currentPlayer][i] == j)
              {
                discardCard(i, currentPlayer, Game, 0);			
                break;
              }
          }

        return 0;
        

      case remodel:
        j = Game.hand[currentPlayer][choice1];  //store card we will trash

        if ( (getCost(Game.hand[currentPlayer][choice1]) + 2) > getCost(choice2) )
            return -1;
        
        gainCard(choice2, Game, 0, currentPlayer);

        //discard card from hand
        discardCard(handPos, currentPlayer, Game, 0);

        //discard trashed card
        for (int i = 0; i < Game.handCount[currentPlayer]; i++)
            if (Game.hand[currentPlayer][i] == j){
                discardCard(i, currentPlayer, Game, 0);			
                break;
            }


        return 0;
*/
      case smithy:
        //+3 Cards
        for (int i = 0; i < 3; i++)
            drawCard(currentPlayer, Game);

        //discard card from hand
        discardCard(handPos, currentPlayer, Game, 0);
        return 0;

      case village:
        //+1 Card
        drawCard(currentPlayer, Game);

        //+2 Actions
        Game.numActions = Game.numActions + 2;

        //discard played card from hand
        discardCard(handPos, currentPlayer, Game, 0);
        return 0;


      case steward:
        if (choice1 == 1){
            //+2 cards
            drawCard(currentPlayer, Game);
            drawCard(currentPlayer, Game);
          }
        else if (choice1 == 2)
            Game.coins = Game.coins + 2;
        else{
            //trash 2 cards in hand
            discardCard(choice2, currentPlayer, Game, 1);
            discardCard(choice3, currentPlayer, Game, 1);
          }

        //discard card from hand
        discardCard(handPos, currentPlayer, Game, 0);
        return 0;
/*  not implemented because we only do 10 + 3 of my choice
      case tribute:
        if ((Game.discardCount[nextPlayer] + Game.deckCount[nextPlayer]) <= 1){
          if (Game.deckCount[nextPlayer] > 0){
            tributeRevealedCards[0] = Game.deck[nextPlayer][Game.deckCount[nextPlayer]-1];
            Game.deckCount[nextPlayer]--;
          }
          else if (Game.discardCount[nextPlayer] > 0){
            tributeRevealedCards[0] = Game.discard[nextPlayer][Game.discardCount[nextPlayer]-1];
            Game.discardCount[nextPlayer]--;
          }
          else
            if (DEBUG)
              System.out.println("No cards to reveal\n");
        }

        else{
          if (Game.deckCount[nextPlayer] == 0){
            for (int i = 0; i < Game.discardCount[nextPlayer]; i++){
              Game.deck[nextPlayer][i] = Game.discard[nextPlayer][i];//Move to deck
              Game.deckCount[nextPlayer]++;
              Game.discard[nextPlayer][i] = -1;
              Game.discardCount[nextPlayer]--;
            }

            shuffle(nextPlayer,Game);//Shuffle the deck
          } 
          tributeRevealedCards[0] = Game.deck[nextPlayer][Game.deckCount[nextPlayer]-1];
          Game.deck[nextPlayer][Game.deckCount[nextPlayer]--] = -1;
          Game.deckCount[nextPlayer]--;
          tributeRevealedCards[1] = Game.deck[nextPlayer][Game.deckCount[nextPlayer]-1];
          Game.deck[nextPlayer][Game.deckCount[nextPlayer]--] = -1;
          Game.deckCount[nextPlayer]--;
        }    

        if (tributeRevealedCards[0] == tributeRevealedCards[1]){//If we have a duplicate card, just drop one 
          Game.playedCards[Game.playedCardCount] = tributeRevealedCards[1];
          Game.playedCardCount++;
          tributeRevealedCards[1] = -1;
        }

        for (int i = 0; i <= 2; i ++){//Treasure cards
          if (tributeRevealedCards[i] == Card.copper.ordinal() || tributeRevealedCards[i] == Card.silver.ordinal() || tributeRevealedCards[i] == Card.gold.ordinal())
            Game.coins += 2;
          

          else if (tributeRevealedCards[i] == Card.estate.ordinal() || tributeRevealedCards[i] == Card.duchy.ordinal() || tributeRevealedCards[i] == Card.province.ordinal() || tributeRevealedCards[i] == Card.gardens.ordinal() || tributeRevealedCards[i] == Card.great_hall.ordinal()){//Victory Card Found
            drawCard(currentPlayer, Game);
            drawCard(currentPlayer, Game);
          }
          else{//Action Card
            Game.numActions = Game.numActions + 2;
          }
        }

        return 0;
*/
/*


      case outpost:
        //set outpost flag
        Game.outpostPlayed++;

        //discard card
        discardCard(handPos, currentPlayer, Game, 0);
        return 0;

      case salvager:
        //+1 buy
        Game.numBuys++;

        if (choice1 != 0)
          {
            //gain coins equal to trashed card
            Game.coins = Game.coins + getCost( handCard(choice1, Game) );
            //trash card
            discardCard(choice1, currentPlayer, Game, 1);	
          }

        //discard card
        discardCard(handPos, currentPlayer, Game, 0);
        return 0;

      case sea_hag:
        for (int i = 0; i < Game.numPlayers; i++){
          if (i != currentPlayer){
            Game.discard[i][Game.discardCount[i]] = Game.deck[i][Game.deckCount[i]--];			    
            Game.deckCount[i]--;
            Game.discardCount[i]++;
            Game.deck[i][Game.deckCount[i]--] = Card.curse.ordinal();//Top card now a curse
          }
        }
        return 0;

      case treasure_map:
        //search hand for another treasure_map
        int index = -1;
        for (int i = 0; i < Game.handCount[currentPlayer]; i++)
          {
            if (Game.hand[currentPlayer][i] == Card.treasure_map.ordinal() && i != handPos)
              {
                index = i;
                break;
              }
          }
        if (index > -1)
          {
            //trash both treasure cards
            discardCard(handPos, currentPlayer, Game, 1);
            discardCard(index, currentPlayer, Game, 1);

            //gain 4 Gold cards
            for (int i = 0; i < 4; i++)
                gainCard(Card.gold.ordinal(), Game, 1, currentPlayer);

            //return success
            return 1;
          }

        //no second treasure_map found in hand
        return -1;
*/
      }
    return -1;
    }
    int discardCard(int handPos, int currentPlayer, GameState Game, int trashFlag)
    {
      if (Game.handCount[currentPlayer] < 1)
          return -1;

      //if card is not trashed, added to Played pile 
      if (trashFlag < 1)
        {
          //add card to played pile
          Game.playedCards[Game.playedCardCount] = Game.hand[currentPlayer][handPos]; 
          Game.playedCardCount++;
        }

      //set played card to -1
      Game.hand[currentPlayer][handPos] = -1;

      //remove card from player's hand
      if ( handPos == (Game.handCount[currentPlayer] - 1) ) 	//last card in hand array is played
          Game.handCount[currentPlayer]--;
      else if ( Game.handCount[currentPlayer] == 1 ) 
          Game.handCount[currentPlayer]--;
      else 	
        {
          //replace discarded card with last card in hand
          Game.hand[currentPlayer][handPos] = Game.hand[currentPlayer][ (Game.handCount[currentPlayer] - 1)];
          //set last card to -1 
          Game.hand[currentPlayer][Game.handCount[currentPlayer] - 1] = -1;
          //reduce number of cards in hand
          Game.handCount[currentPlayer]--;
        }

      return 0;
    }
    public int gainCard(int supplyPos, GameState Game, int toFlag, int player)
    {
      //Note: supplyPos is enum of choosen card

      //check if supply pile is empty (0) or card is not used in game (-1)
      if ( supplyCount(supplyPos, Game) < 1 ) return -1;

      //added card for [whoseTurn] current player:
      // toFlag = 0 : add to discard
      // toFlag = 1 : add to deck
      // toFlag = 2 : add to hand

      if (toFlag == 1) {
          Game.deck[ player ][ Game.deckCount[player] ] = supplyPos;
          Game.deckCount[player]++;
        }
      else if (toFlag == 2){
          Game.hand[ player ][ Game.handCount[player] ] = supplyPos;
          Game.handCount[player]++;
        }
      else {
          Game.discard[player][ Game.discardCount[player] ] = supplyPos;
          Game.discardCount[player]++;
        }

      //decrease number in supply pile
      Game.supplyCount[supplyPos]--;

      return 0;
    }
    public int supplyCount(int card, GameState Game) {
      return Game.supplyCount[card];
    }    
    public int getCost(int cardNumber)
    {
      switch( Card.values()[cardNumber]) 
        {
        case curse: return 0;
        case copper: return 0;
        case embargo:  return 2;
        case estate: return 2;
        case great_hall: return 3;
        case steward: return 3;
        case ambassador: return 3;
        case silver: return 3;
        case feast: return 4;
        case gardens: return 4;
        case remodel: return 4;
        case smithy: return 4;
        case village: return 3;
        case baron: return 4;
        case cutpurse: return 4;
        case salvager: return 4;
        case sea_hag: return 4;
        case treasure_map: return 4;
        case outpost: return 5;
        case duchy: return 5;
        case minion: return 5;
        case tribute: return 5;
        case mine: return 5;
        case council_room: return 5;
        case gold: return 6;
        case adventurer: return 6;
        case province: return 8;
        }
      return -1;
    }
    public int endTurn(GameState Game) {
      int k;
      int i;
      int currentPlayer = whoseTurn(Game);

      if (DEBUG){
          System.out.println("endturn start: ");
          Game.reportPlayerCards(Game.whoseTurn);
      } 

      //Discard hand
      for (i = 0; i < Game.handCount[currentPlayer]; i++){
          Game.discard[currentPlayer][Game.discardCount[currentPlayer]++] = Game.hand[currentPlayer][i];//Discard
          Game.hand[currentPlayer][i] = -1;//Set card to -1
      }
      Game.handCount[currentPlayer] = 0;//Reset hand count      
      
      //Discard played pile
      for (i = 0; i < Game.playedCardCount; i++){
          Game.discard[currentPlayer][Game.discardCount[currentPlayer]++] = Game.playedCards[i];
          Game.playedCards[i] = -1;//Set card to -1
      }
      Game.playedCardCount = 0; // reset played pile

      //Code for determining the player
      if (currentPlayer < (Game.numPlayers - 1))
          Game.whoseTurn = currentPlayer + 1;//Still safe to Increment
      else
          Game.whoseTurn = 0;//Max player has been reached, loop back around to player 1
      

      if (DEBUG){
        System.out.println("endturn post-player change: ");
        Game.reportPlayerCards(Game.whoseTurn);
      }
      Game.outpostPlayed = 0;
      Game.phase = 0;
      Game.numActions = 1;
      Game.coins = 0;
      Game.numBuys = 1;
      Game.playedCardCount = 0;
      Game.handCount[Game.whoseTurn] = 0;

      if (DEBUG){
        System.out.println("endturn predraw: ");
        Game.reportPlayerCards(Game.whoseTurn);
      }
      //int k; move to top
      //Next player draws hand
      if (Game.handCount[Game.whoseTurn] != 5)
          for (k = 0; k < 5; k++)
              drawCard(Game.whoseTurn, Game);//Draw a card
        
      if (DEBUG){
        System.out.println("endturn postdraw: ");
        Game.reportPlayerCards(Game.whoseTurn);
      }

      //Update money
      Game.cardCoinBonus = 0;
      updateCoins(Game.whoseTurn, Game , 0);

      return 0;
    }
    public int isGameOver(GameState Game) {
      
    //if stack of Province cards is empty, the game ends

    if (Game.supplyCount[Card.province.ordinal()] == 0) return 1;

    //if three supply pile are at 0, the game ends
    int j = 0;
    for (int i = 0; i < 25; i++) 
        if (Game.supplyCount[i] == 0) j++;            
    if ( j >= 3) return 1;
    return 0;
    }
    public int buyCard(int supplyPos, GameState Game) {
//      System.out.println("entering buy: ");
//      Game.reportPlayerCards();
      if (DEBUG)
        System.out.println("Entering buyCard...\n");

      int who = Game.whoseTurn;

      if (Game.numBuys < 1){
        if (DEBUG)
          System.out.println("You do not have any buys left\n");
        return -1;
      } else if (supplyCount(supplyPos, Game) <1){
        if (DEBUG)
          System.out.println("There are not any of that type of card left\n");
        return -1;
      } else if (Game.coins < getCost(supplyPos)){
        if (DEBUG) 
          System.out.println("You do not have enough money to buy that. You have "+Game.coins+" coins.\n");
        return -1;
      } else {
        //////////////
        // INTENTIONAL BUGS        
        // should be: Game.phase=1;     
        Game.phase=2;
        //////////////
        
        
        //////////////////////////////////
        // INTENTIONAL BUGS        
        // should be:  gainCard(supplyPos%, Game, 0, who);
        gainCard((supplyPos+1)%Card.treasure_map.ordinal(), Game, 0, who); 
        gainCard((supplyPos+2)%Card.treasure_map.ordinal(), Game, 0, who); 
        //////////////////////////////////

        //////////////////////////////////
        // INTENTIONAL BUG
        Game.coins = (Game.coins) - (getCost(supplyPos) -1); // -1 is a bug!         
        //////////////////////////////////
        
        Game.numBuys--;
        //////////////////////////////////
        // INTENTIONAL BUG 
        // no need for additional statement
        Game.numBuys--; 
        //////////////////////////////////
        
        //////////////////////////////////
        // INTENTIONAL BUG
        // embargo tokens are not implemented
        //////////////////////////////////
        
        if (DEBUG)
          System.out.println("You bought the card " +Card.values()[supplyPos]+ " for "+getCost(supplyPos)+" coins. You now have "+Game.numBuys+" buys and "+Game.coins+" coins.\n");
      }
      return 0;
    }
    public int scoreFor (int player, GameState Game) {

      int score = 0;
      //score from hand

      int[] cards = new int[Card.treasure_map.ordinal()];
      
      if (DEBUG){
        System.out.println("");
        System.out.println("");

        System.out.println("Player: " + player + " #cards: " + (Game.handCount[player]+Game.discardCount[player] + Game.discardCount[player] + Game.playedCardCount) );
        System.out.println("hand:");
      }
      for (int i = 0; i < Game.handCount[player]; i++)
        {
          
          if (Game.hand[player][i] == Card.curse.ordinal()) score = score - 1;          
          if (Game.hand[player][i] == Card.estate.ordinal()) score = score + 1;           
          if (Game.hand[player][i] == Card.duchy.ordinal()) score = score + 3;           
          if (Game.hand[player][i] == Card.province.ordinal()) score = score + 6; 
          if (Game.hand[player][i] == Card.great_hall.ordinal()) score = score + 1; 
          
          if (Game.hand[player][i] == Card.gardens.ordinal()) score = score + ( totalDeckCount(player, Game) / 10 ); 
          if (DEBUG){
            System.out.println("#: " + i + " card: " + Game.hand[player][i] + " = " + Card.values()[Game.hand[player][i]]  );
            System.out.println("score: " + score);
            cards[Game.hand[player][i]]++;
          }
        }
      //score from discard
      if (DEBUG)
        System.out.println("discard:");
      
      for (int i = 0; i < Game.discardCount[player]; i++)
        {
          if (Game.discard[player][i] == Card.curse.ordinal()) score = score - 1;          
          if (Game.discard[player][i] == Card.estate.ordinal())score = score + 1;           
          if (Game.discard[player][i] == Card.duchy.ordinal()) score = score + 3;
          
          if (Game.discard[player][i] == Card.province.ordinal()) score = score + 6;
          if (Game.discard[player][i] == Card.great_hall.ordinal()) score = score + 1;
          if (Game.discard[player][i] == Card.gardens.ordinal()) score = score + ( totalDeckCount(player, Game) / 10 );
          if (DEBUG){
            System.out.println("#card: " + i + " : " + Game.discard[player][i] + " = " + Card.values()[Game.discard[player][i]]  );
            System.out.println("score: " + score);
            cards[Game.discard[player][i]]++;
          }
        }
//
      if (DEBUG)
        System.out.println("deck:");
      //score from deck
      for (int i = 0; i < Game.deckCount[player]; i++)
        {
          if (Game.deck[player][i] == Card.curse.ordinal()) score = score - 1;          
          if (Game.deck[player][i] == Card.estate.ordinal()) score = score + 1;           
          if (Game.deck[player][i] == Card.duchy.ordinal()) score = score + 3;          
          if (Game.deck[player][i] == Card.province.ordinal()) score = score + 6;
          if (Game.deck[player][i] == Card.great_hall.ordinal()) score = score + 1; 
          if (Game.deck[player][i] == Card.gardens.ordinal()) score = score + ( totalDeckCount(player, Game) / 10 ); 
          if (DEBUG){
            System.out.println("card: " + i + " : " + Game.deck[player][i] + " = " + Card.values()[Game.deck[player][i]]);
            System.out.println("score: " + score);
            cards[Game.deck[player][i]]++;
          }
        }
      
      if (DEBUG) {
        System.out.println("played: " + Game.playedCardCount);      
        for (int i = 0; i < Game.playedCardCount; i++)
            System.out.println("card: " + i + " : " + Game.playedCards[i] + " = " + Card.values()[Game.playedCards[i]]);
        for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
            System.out.println(Card.values()[i] + " " + cards[i]);
      }

      return score;
    }
    public int fullDeckCount(int player, int card, GameState Game) {
      int count = 0;

      for (int i = 0; i < Game.deckCount[player]; i++)
          if (Game.deck[player][i] == card) count++;

      for (int i = 0; i < Game.handCount[player]; i++)
          if (Game.hand[player][i] == card) count++;        

      for (int i = 0; i < Game.discardCount[player]; i++)        
          if (Game.discard[player][i] == card) count++;        

      return count;
    }
    public int getWinners(int[] players, GameState Game){

        int highScore;
        int currentPlayer;


        //get score for each player
        for (int i = 0; i < Game.numPlayers; i++)
            players[i] = scoreFor (i, Game);

        //find highest score
        int j = 0;
        for (int i = 0; i < Game.numPlayers; i++)
            if (players[i] > players[j])
                j = i;

        highScore = players[j];

        //add 1 to players who had less turns
        currentPlayer = whoseTurn(Game);
        for (int i = 0; i < Game.numPlayers; i++)
            if ( players[i] == highScore && i > currentPlayer )
                players[i]++;

        //find new highest score
        j = 0;
        for (int i = 0; i < Game.numPlayers; i++)
            if ( players[i] > players[j] )
                j = i;

        highScore = players[j];

        //set winners in array to 1 and rest to 0
        for (int i = 0; i < Game.numPlayers; i++)
            if ( players[i] == highScore ) 
                players[i] = 1;
            else
                players[i] = 0;

        return 0;
        
    }

   
    public void assertEquals(boolean expected, boolean output, String message){
        if (expected != output)
            System.out.println(message);
    }
    public void assertEquals(int lhs, int rhs, String message){
        if (lhs != rhs)
            System.out.println(message);
    }
    public void nullifyDecks(GameState Game){          
        for (int i = 0; i < Game.numPlayers; i++){
            nullifyDeck(i,Game);
        }
    }
    public void nullifyDeck(int player, GameState Game){   
        for (int j = 0; j < MAX_DECK; j++){
            Game.hand[player][j] = -1;
            Game.discard[player][j] = -1;
            Game.deck[player][j] = -1;            
        }
        Game.deckCount[player] = 0;
        Game.handCount[player] = 0;
        Game.discardCount[player] = 0;
    }
    public int totalDeckCount(int player, GameState Game){
        int sum = 0;
        for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
            sum += fullDeckCount(player, i, Game);
        return sum;
    } 
    public int getRandomCard(){   
        return rand.nextInt(Card.treasure_map.ordinal());
    }

    public int[] arbitraryPileCount(int[] input, int size){

    int[] pile = new int[Card.treasure_map.ordinal()];
    for (int i = 0; i < size; ++i){
       pile[input[i]]++;
    }
    return pile;
}
    public void compareCardPiles(int[] pileOne, int[] pileTwo, int countOne, int countTwo, String message){
        
        int[] pileOneCounts = arbitraryPileCount(pileOne,countOne);
        int[] pileTwoCounts = arbitraryPileCount(pileTwo,countTwo);
        comparePileCounts(pileOneCounts, pileTwoCounts, message);
    }
    public void comparePileCounts(int[] pileOneCount, int[] pileTwoCount, String message){        
        for (int i = 0; i < Card.treasure_map.ordinal(); ++i)
            if (pileOneCount[i] != pileTwoCount[i])
                System.out.println(message);
    }
    public void fillCardPile(int[] pile, int size){
        for (int i = 0; i < size; ++i)
            pile[i] = getRandomCard();
    }
    public void fillDecks(GameState Game, int numCards){
        for (int i = 0; i < Game.numPlayers; ++i){
            fillCardPile(Game.deck[i], numCards);
            fillCardPile(Game.hand[i], numCards);
            fillCardPile(Game.discard[i], numCards);
            //fillCardPile(Game.playedCards, numCards); // seems to break things
            Game.deckCount[i] = numCards;
            Game.handCount[i] = numCards;
            Game.discardCount[i] = numCards;
            //Game.playedCardCount = numCards;
        }
    }
    public void fillSupply(GameState Game){      
        for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
            Game.supplyCount[i] = 50;        
    }    
    public void testDrawCard(GameState Game){
                
        //drawCard
            // things we need to check:
                //shuffles deck if empty
                    // returns -1 if deck empty after shuffling
                    // if shuffled, should empty discard properly
                //player hand grows by one, deck shrinks by one
                //deck/hand/discard counts updated properly 
                //check the same card that was removed from the deck was added to the hand   
                
        // setting up
        // set all decks, hands, and discards to -1
        for (int i = 0; i < Game.numPlayers; i++){
            nullifyDeck(i,Game);
            // everything should be empty
            if ((Game.handCount[i] != 0) || (Game.discardCount[i] != 0) || (Game.deckCount[i] != 0))
                System.out.println("deck not nullified");
            // all deck, hand, and discards should be set to = -1
            for (int j = 0; j < MAX_DECK; j++)
                if ((Game.hand[i][j] != -1) || (Game.discard[i][j] != -1) || (Game.deck[i][j] != -1))
                    System.out.println("deck not nullified");
        }
        
        // arrays to hold copies of original state for each player
        int[] tempDeck = new int[MAX_DECK];
        int[] tempHand = new int[MAX_DECK];
        int[] tempDiscard = new int[MAX_DECK];
        
        // initilize our temporary deck to non-values 
        for (int i = 0; i < MAX_DECK; i++){
            tempDeck[i] = -1;
            tempHand[i] = -1;
            tempDiscard[i] = -1;
        }
        
        
        // number of random cards we'll put in deck/hand/discard
        int cardsGenerated = rand.nextInt(MAX_DECK/3);
        int tempDeckCount = cardsGenerated;
        int tempHandCount = cardsGenerated;
        int tempDiscardCount = cardsGenerated;
        
        // for each player           
        //Game.numPlayers = 1;
        for (int i = 0; i < Game.numPlayers; i++){
            // randomly insert cards into deck, hand, discard
            fillCardPile(tempDeck, cardsGenerated);
            fillCardPile(tempHand, cardsGenerated);
            fillCardPile(tempDiscard, cardsGenerated);
            
            Game.deck[i] = tempDeck.clone();
            Game.hand[i] = tempHand.clone();
            Game.discard[i] = tempDiscard.clone();
            
            Game.deckCount[i] = cardsGenerated;
            Game.handCount[i] = cardsGenerated;
            Game.discardCount[i] = cardsGenerated;
               
            // make sure actual and temp are identical at the start             
            compareCardPiles(Game.deck[i], tempDeck, Game.deckCount[i], tempDeckCount, "temp and actual deck counts desynced at start");
            compareCardPiles(Game.hand[i], tempHand, Game.handCount[i], tempHandCount, "temp and actual hand counts desynced at start");
            compareCardPiles(Game.discard[i], tempDiscard, Game.discardCount[i], tempDiscardCount, "temp and actual discard counts desynced at start");
            
            
            boolean drawingCards = true;
            while(drawingCards){
                // get current counts for deck, hand, discard
                int initialDeckCount = Game.deckCount[i];
                int initialHandCount = Game.handCount[i];
                int initialDiscardCount = Game.discardCount[i];
                
                int expectedDeckCount = initialDeckCount -1;
                int expectedHandCount = initialHandCount +1;
                int expectedDiscardCount = initialDiscardCount;
                int expectedReturnValue = 0;   
                int topCard = -2; // value for not being able to predict top card
                
                // if deck empty
                if (initialDeckCount == 0){                                        
                    // if deck & discard empty 
                        // hand +0 deck +0 discard +0, return == -1
                    if (initialDiscardCount == 0){
                        expectedReturnValue = -1;
                        expectedDeckCount = initialDeckCount;
                        expectedHandCount = initialHandCount;
                        drawingCards = false;
                    }
                    // discard not empty
                        // discard hand +1, deck == +discard-1, discard == 0
                    else{
                        expectedDeckCount = initialDiscardCount -1;     
                        expectedDiscardCount = 0;
                    }
                }
                // deck not empty
                    //hand +1, deck -1, discard +0, return == 0, can predict top 
                else{
                    // peek to see what card we'll draw next
                    topCard = Game.deck[i][Game.deckCount[i]-1];
                }
                
                int[] expectedDeckCards = arbitraryPileCount(Game.deck[i],Game.deckCount[i]);
                int[] expectedHandCards = arbitraryPileCount(Game.hand[i],Game.handCount[i]);
                
                //comparePileCounts(expectedDeckCards,initialDeckCards, "initial versus expected failed");
                
                // if didn't shuffle check top card with expected piles
                if (topCard != -2){
                    expectedDeckCards[topCard]--;
                    expectedHandCards[topCard]++;
                }
                // actually calling drawCard
                int actualReturnValue = drawCard(i,Game);
                
                // check to see if we predicted the return value
                assertEquals(expectedReturnValue, actualReturnValue, "return value wrong");
                
                // how many cards are actually in our hand after drawing?
                int[] actualDeckCards = arbitraryPileCount(Game.deck[i],Game.deckCount[i]);
                int[] actualHandCards = arbitraryPileCount(Game.hand[i],Game.handCount[i]);
                
                
                // if we didn't shuffle discard into deck, see if our actual card totals changed by how much we thought they would
                if (topCard != -2){
                    comparePileCounts(expectedDeckCards,actualDeckCards, "deck: actual versus expected failed");
                    comparePileCounts(expectedHandCards,actualHandCards, "hand: actual versus expected failed");
                    //System.out.println("deck: expected top card - " + expectedDeckCards[topCard] + " actual top card - " + actualDeckCards[topCard]  );
                }
                // check to see if our expected counts were correct
                assertEquals(expectedDeckCount, Game.deckCount[i], "deck count wrong");
                assertEquals(expectedHandCount, Game.handCount[i], "hand count wrong");
                assertEquals(expectedDiscardCount, Game.discardCount[i], "hand count wrong");
            }
            // compare original state totals to end state totals
            assertEquals(Game.handCount[i], cardsGenerated*3, "not all cards in hand at end");           
        
        }
    }
    public void testGainCard(GameState Game){
        
        //gainCard
        // things we need to check:
            // toFlag: 0 adds to discard, 1 adds to deck, 2 adds to hand (anything else counts as 2)
            // counts of both supply pile and appropriate player pile update correctly
            // the card added to pile was same as supply pile 
            // returns -1 if supply pile count is 0 or fewer 
            
            
        // reset our deck states to counts = 0 and values = -1
        nullifyDecks(Game); 

        
        //fill supplies with enough that we can play with   
        fillSupply(Game);
        
        for (int i = 0; i < 1000; i++){
            int newCard = getRandomCard();
            int newDestination = rand.nextInt(3);
            int desiredPlayer = rand.nextInt(Game.numPlayers);
            int expectedSupply = Game.supplyCount[newCard] -1;
            int expectedReturn = 0;
            
            
            int[] expectedCards;
            int expectedCount;
            
            // Figured out which pile we're comparing to
            // card to discard
            if (newDestination == 0){
                expectedCards = arbitraryPileCount(Game.discard[desiredPlayer],Game.discardCount[desiredPlayer]);
                expectedCount = Game.discardCount[desiredPlayer] +1;
            }
            // card to deck
            else if (newDestination == 1){
                expectedCards = arbitraryPileCount(Game.deck[desiredPlayer],Game.deckCount[desiredPlayer]);
                expectedCount = Game.deckCount[desiredPlayer] +1;
            }
            // card to hand
            else {
                expectedCards = arbitraryPileCount(Game.hand[desiredPlayer],Game.handCount[desiredPlayer]);
                expectedCount = Game.handCount[desiredPlayer] +1;
            }
            
            //
            
            //if supply doesn't have enough. 
            if (Game.supplyCount[newCard] < 1){
                // Adjust values accordingly
                expectedReturn = -1;
                expectedSupply = 0;
                expectedCount--;
            }
            // supply does have enough so increment pool
            else {
                expectedCards[newCard]++;
            }
            
            // actually using our function!
            int actualReturn = gainCard(newCard, Game, newDestination, desiredPlayer);
            
            // now see what actually happened so we can compared to our expectations
            int[] actualCards;
            int actualCount;
            if (newDestination == 0){
                actualCards = arbitraryPileCount(Game.discard[desiredPlayer],Game.discardCount[desiredPlayer]);
                actualCount = Game.discardCount[desiredPlayer];
            }
            else if (newDestination == 1){
                actualCards = arbitraryPileCount(Game.deck[desiredPlayer],Game.deckCount[desiredPlayer]);
                actualCount = Game.deckCount[desiredPlayer];
            }
            else {
                actualCards = arbitraryPileCount(Game.hand[desiredPlayer],Game.handCount[desiredPlayer]);
                actualCount = Game.handCount[desiredPlayer];
            }
            
            comparePileCounts(expectedCards,actualCards, "cards don't match");
            assertEquals(expectedCount, actualCount,"counts don't match");
            assertEquals(expectedReturn, actualReturn,"returns don't match");
            assertEquals(expectedSupply, Game.supplyCount[newCard],"supply counts don't match");


        }
        
        
        
    }
    public void testBuyCard(GameState Game){
        
        //buyCard
            // things we need to check:
                // return -1 if 
                    // number of buys too few
                    // there isn't enough supply of that card
                    // if intended supply pile is invalid (out of range)
                    // if player doesn't have enough money
                // counts of supply pile and player update correctly
                // player actually gains the card they bought
                // player coins reduced by the cost of the card
        
        nullifyDecks(Game);
        fillSupply(Game);
        
        for (int i = 0; i < 100; i++){
            //setup player variables
            Game.numBuys = rand.nextInt(5);
            Game.coins = rand.nextInt(30);
            Game.whoseTurn = rand.nextInt(Game.numPlayers);
            
            //while we're still buying - not checking numBuys because we want to test buyCards with numBuys < 1
            int loops = 0;
            boolean stillBuying = true;
            while (stillBuying){
                loops++;
                // exit the loop after this 
                if (Game.numBuys < 1 || Game.coins < 2 || loops >50)
                    stillBuying = false;
                
                
                // setup this buy attempt's variables 
                int cardChoice = getRandomCard();            
                int[] expectedCards = arbitraryPileCount(Game.discard[Game.whoseTurn],Game.discardCount[Game.whoseTurn]);
                expectedCards[cardChoice]++;
                
                int expectedBuys = Game.numBuys--;
                int expectedCount = Game.discardCount[Game.whoseTurn] +1;
                int expectedCoins = Game.coins - getCost(cardChoice);
                int expectedReturn = 0;
                int expectedSupply = Game.supplyCount[cardChoice] -1;
                
                boolean expectedFailure = false;
                
                // if we do the things buy is supposed to avoid we'll exppect it to fail
                if (Game.numBuys < 1 || Game.supplyCount[cardChoice] <1 || expectedCoins < 0)   
                    expectedFailure = true;
                
                // if we think it'll fail, adjust expectations
                if (expectedFailure == true){
                    expectedSupply++;
                    expectedCount--;
                    expectedReturn = -1;
                    expectedCards[cardChoice]--;
                    expectedCoins = Game.coins;
                    expectedBuys = Game.numBuys;
                    
                }
                
                // actually running BuyCard
                int actualReturn = buyCard(cardChoice, Game);
                
                // actual values of cards
                int[] actualCards = arbitraryPileCount(Game.discard[Game.whoseTurn],Game.discardCount[Game.whoseTurn]);
                int actualBuys = Game.numBuys;
                // did our expectations match the actual results? 
                comparePileCounts(expectedCards,actualCards, "cards don't match");
                assertEquals(expectedCount, Game.discardCount[Game.whoseTurn],"counts don't match");
                assertEquals(expectedReturn, actualReturn,"returns don't match");
                assertEquals(expectedSupply, Game.supplyCount[cardChoice],"supply counts don't match");
                assertEquals(expectedCoins, Game.coins,"coins counts don't match");
                assertEquals(expectedBuys, actualBuys,"buy counts don't match");
                
            }
            
        }
        
        
    }
    public void testShuffle(int player, GameState Game) {
        
        int[] expResult = new int[MAX_DECK]; 
        
        
        for (int i = 0; i < Game.deckCount[player]; ++i)
            expResult[i] = Game.deck[player][i];
        int expLength = Game.deckCount[player];
        
        shuffle(player, Game);
        int[] result = Game.deck[player];
        boolean same = true;        
        for (int i = 0; i < Game.deckCount[player]; ++i){
            if (result[i] != expResult[i]){
                same = false;
                break;
            }
        }
        if (same == true){
            System.out.println("The shuffle didn't do anything! (expected at least some of the time and for small hands)");
            for (int i = 0; i < Game.deckCount[player]; ++i){
                System.out.println("original: " + expResult[i] + " shuffled: " + result[i]);
            }
        }
        assertEquals(expLength, Game.deckCount[player], "Shuffle changed size of deck"); // no point- going to be the same size
        
    }
    public void testDiscardCard(GameState Game){
        
        //discardCard
            // things we need to check:
                // card actually added to play pile or removed entirely depending on trash flag
                // all piles updated correctly - hand goes down, discard goes up by exactly one
                // card actually removed from correct player hand
                // returns -1 if player's hand has no cards in it, 0 otherwise
        
        nullifyDecks(Game);
        fillSupply(Game);
        int numCards = 200;
        fillDecks(Game, numCards);
        
        for (int p = 0; p < Game.numPlayers; ++p){
            for (int i = 0; i < numCards-1; i++){
                //setup player variables
                int player = p;
                int handPos = rand.nextInt(Game.handCount[player]);
                int chosenCard = Game.hand[player][handPos];
                int trash = rand.nextInt(2);
                
                // predicting the values of the inputs
                int[] expectedHand = arbitraryPileCount(Game.hand[player],Game.handCount[player]);
                int expectedHandCount = Game.handCount[player] -1;
                int[] expectedPlayed = arbitraryPileCount(Game.playedCards,Game.playedCardCount);
                int expectedPlayedCount = Game.playedCardCount+1;
                
                int expectedReturn = 0;

                // if player's hand empty
                if (Game.handCount[player] < 1){
                    expectedReturn = -1;
                    expectedHandCount++;
                    expectedPlayedCount = Game.playedCardCount;
                }
                else {
                    expectedHand[chosenCard]--;
                    if (trash == 0) 
                        expectedPlayed[chosenCard]++;                    
                    else
                        expectedPlayedCount--;
                }
                
                // calling discardCard
                int actualReturn = discardCard(handPos, player, Game, trash);
                
                // getting the actual values
                int[] actualPlayed = arbitraryPileCount(Game.playedCards,Game.playedCardCount);
                int actualPlayedCount = Game.playedCardCount;
                int[]actualHand = arbitraryPileCount(Game.hand[player],Game.handCount[player]);
                int actualHandCount = Game.handCount[player];

                // output if anything isn't what we predicted
                comparePileCounts(expectedHand,actualHand, "hands don't match");
                comparePileCounts(expectedPlayed,actualPlayed, "played don't match");
                assertEquals(expectedHandCount, actualHandCount,"hand counts don't match");
                assertEquals(expectedPlayedCount, actualPlayedCount,"played counts don't match");
                assertEquals(expectedReturn, actualReturn,"returns don't match");
            }
        }
                
        
    }
    public void testScoreFor(GameState Game){
        // empty deck hand and discards, or loop through existing gamestates and value current cards manually
        // loop applying randomized gaincards to various players, adding up and seeing what score _should_ be. 
        // apply score for and see if it matches
    
        nullifyDecks(Game);
        int numCards = 250;
        
        
        for (int p = 0; p < Game.numPlayers; ++p){
            for (int i = 0; i < numCards; i++){
                //setup player variables
                
                int newDestination = rand.nextInt(3);
                int cardChoice = getRandomCard();
                
                // predicting the values of the inputs
                if (newDestination == 0){
                    Game.hand[p][Game.handCount[p]] = cardChoice; 
                    Game.handCount[p]++;
                }
                else if (newDestination == 1){
                    Game.deck[p][Game.deckCount[p]] = cardChoice; 
                    Game.deckCount[p]++;
                }
                else {
                    Game.discard[p][Game.discardCount[p]] = cardChoice; 
                    Game.discardCount[p]++;
                    
                }
            }
            
            // pull tmphand deck discard out
            // sum to find total number of cards
            // fill decks
            int[] tmpHand = arbitraryPileCount(Game.hand[p],Game.handCount[p]);
            int[] tmpDeck = arbitraryPileCount(Game.deck[p],Game.deckCount[p]);
            int[] tmpDiscard = arbitraryPileCount(Game.discard[p],Game.discardCount[p]);
            int[] finalPile = new int[Card.treasure_map.ordinal()];
            int cardSum = 0;
            for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i){
                finalPile[i] = tmpHand[i] + tmpDeck[i] + tmpDiscard[i];
                cardSum += finalPile[i];
            }
            int expectedScore = 0;
            for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i){
                
                if (i == Card.curse.ordinal()) expectedScore -= finalPile[i];                
                if (i == Card.estate.ordinal()) expectedScore += finalPile[i];                 
                if (i == Card.duchy.ordinal()) expectedScore +=  finalPile[i]* 3;                 
                if (i == Card.province.ordinal()) expectedScore += finalPile[i] * 6; 
                if (i == Card.great_hall.ordinal()) expectedScore += finalPile[i];
                if (i == Card.gardens.ordinal()) expectedScore += finalPile[i] * totalDeckCount(p,Game)/10;
            }
            
            int actualScore = scoreFor(p, Game);
            assertEquals(cardSum, totalDeckCount(p,Game), "sums not equal: " + cardSum + " " + totalDeckCount(p,Game));
            assertEquals(expectedScore, actualScore, "scores not equal: " + expectedScore + " " + actualScore);
            /*
            if (expectedScore != actualScore){
                for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
                    System.out.println(Card.values()[i]+ " " + finalPile[i] + " " + fullDeckCount(p, i, Game)); 
                
            }
            */
            
        }
}
    public void testUpdateCoins(GameState Game){
        // game.coins should total # copper*1+silver*2+gold*3+bonus - can use fullDeckCount for this
            
        nullifyDecks(Game);
        int numCards = 250;
        fillDecks(Game, 50);
        for (int p = 0; p < Game.numPlayers; ++p){
            int[] handCount = arbitraryPileCount(Game.hand[p],Game.handCount[p]);
            int expectedCoins = 0;
            for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
                if (i == Card.copper.ordinal()) expectedCoins += handCount[i];
                else if (i == Card.silver.ordinal()) expectedCoins += handCount[i]* 2;
                else if (i == Card.gold.ordinal()) expectedCoins += handCount[i] * 3;                
            int bonus = rand.nextInt(50);
            expectedCoins +=bonus;
            updateCoins(p, Game, bonus);
            int actualCoins = Game.coins;
            assertEquals(expectedCoins,actualCoins, "coins not equal: " + expectedCoins + " " + actualCoins);
        }
    }
    public void testGetWinners(GameState Game){
        // highest score wins
        // if same score, look at turns - fewest wins
        // if same # turns, both win
        nullifyDecks(Game);
        int numCards = 250;
        fillDecks(Game, 50);
        
        Game.whoseTurn = rand.nextInt(Game.numPlayers);
        
        int[] expectedPlayers = new int[Game.numPlayers];
        int[] score = new int[Game.numPlayers];
        int[] turns = new int[Game.numPlayers];
        int topScore = 0;
        int numScoreWinners = 0;
        
        // get scores of each player
        for (int i = 0; i < Game.numPlayers; ++i){
            score[i] = scoreFor(i,Game);        
            if (score[i] > topScore)
                topScore = score[i];
        }
        // see who won via score and note who is has had fewest turns
        for (int i = 0; i < Game.numPlayers; ++i){
            if (score[i] == topScore){
                expectedPlayers[i]++;
                numScoreWinners++;
                if (i > Game.whoseTurn)
                    turns[i]++; 
            }  
        }
        
        // if we have a tie
        if (numScoreWinners > 1){ 
            int topWins = 0;
            
            // figure out if anyone actually has both fewest turns & top score
            // might be tied and have same score, so we need to check.
            for (int i = 0; i < Game.numPlayers; ++i){
                if (score[i] == topScore){
                    expectedPlayers[i]+= turns[i];                    
                }
                if (expectedPlayers[i] > topWins)
                    topWins = expectedPlayers[i]; 
            }
            
            // see who had both fewest turns and highest score
            for (int i = 0; i < Game.numPlayers; ++i){
                if (expectedPlayers[i] == topWins)
                    expectedPlayers[i] = 1;
                else
                    expectedPlayers[i] = 0;
            }
        }
            
        int[] actualPlayers = new int[Game.numPlayers];
        
        // actually call our function
        getWinners(actualPlayers,Game);
        
        // for every player, see if we predicted their winning state correctly
        for (int i = 0; i < Game.numPlayers; ++i)
            assertEquals(expectedPlayers[i],actualPlayers[i],i  +  " " + Game.whoseTurn + " wrong winners: " + expectedPlayers[i] + " " + actualPlayers[i] + " " + score[0] + " " + score[1] + " " + score[2] + " " + score[3]);
        
    } 
    public void testEndTurn(GameState Game){
        //endTurn
            // actually moves player's hand and played pile to discard 
            // turn is reset: whoseTurn = next, buys = 1, actions = 1, phase = 0, hand count = 5, played pile count = 0
            // coins properly set
            // new player hand actually has 5 cards and their deck has five fewer- and those cards are same values as drawn deck cards
        
        nullifyDecks(Game);
        int numCards = 50;
        fillDecks(Game, numCards);
        
        Game.whoseTurn = 0;
        for (int p = 0; p < Game.numPlayers; ++p){
            int[] oldHandCards =  arbitraryPileCount(Game.hand[p],Game.handCount[p]);
            int[] oldPlayedCards = arbitraryPileCount(Game.playedCards,Game.playedCardCount);
            int[] expectedPlayedCards = new int[Card.treasure_map.ordinal()];
            int[] expectedOldHandCards = new int[Card.treasure_map.ordinal()];
            int[] expectedOldDiscardCards = arbitraryPileCount(Game.discard[p],Game.discardCount[p]);
            int oldExpectedDiscardCount = Game.discardCount[p] + Game.handCount[p] + Game.playedCardCount;
            fillCardPile(Game.playedCards,numCards);
            
            int newPlayer = p+1;
            if (newPlayer == Game.numPlayers)
                newPlayer = 0;
                      
            int[] expectedNewDeckCards = arbitraryPileCount(Game.deck[newPlayer],Game.deckCount[newPlayer]);
            
            for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
                expectedOldDiscardCards[i] += oldHandCards[i] + oldPlayedCards[i];
            
            endTurn(Game);
            
            int[] newHandCards =  arbitraryPileCount(Game.hand[newPlayer],Game.handCount[newPlayer]);  
            for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
                expectedNewDeckCards[i] -= newHandCards[i];
            
            int[] actualNewDeckCards = arbitraryPileCount(Game.deck[newPlayer],Game.deckCount[newPlayer]);
            int[] actualOldDiscardCards = arbitraryPileCount(Game.discard[p],Game.discardCount[p]);
            int[] actualPlayedCards = arbitraryPileCount(Game.playedCards,Game.playedCardCount);
            int[] actualOldHandCards = arbitraryPileCount(Game.hand[p],Game.handCount[p]);
            int expectedTurn = newPlayer;
            int expectedPhase = 0;
            int expectedActions = 1;
            int expectedCoins = 0;
            int expectedPlayedCount = 0;
            int expectedHandCount = 5;
            for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
                if (i == Card.copper.ordinal()) expectedCoins += newHandCards[i];
                else if (i == Card.silver.ordinal()) expectedCoins += newHandCards[i]* 2;
                else if (i == Card.gold.ordinal()) expectedCoins += newHandCards[i] * 3;   
            
            // our tests to see if our specifications are matched
            comparePileCounts(actualOldDiscardCards, expectedOldDiscardCards, "old discard doesn't't match");
            assertEquals(oldExpectedDiscardCount, oldExpectedDiscardCount, "old discard counts don't match");
            assertEquals(expectedCoins, Game.coins, "coins don't match");
            assertEquals(expectedActions, Game.numActions, "numActionsdon't match");
            assertEquals(expectedPhase, Game.phase, "phases don't match");
            assertEquals(expectedPlayedCount, Game.playedCardCount, "coins don't match");
            comparePileCounts(actualPlayedCards, expectedPlayedCards, "played don't match");
            comparePileCounts(actualOldHandCards, expectedOldHandCards, "old hand don't match");
            comparePileCounts(actualNewDeckCards, expectedNewDeckCards, "new decks don't match");
            assertEquals(expectedHandCount, Game.handCount[newPlayer], "new hand counts don't match");
            
        }
        
        
    }
    public void testIsGameOver(GameState Game){
        // if province supply zero, returns 1
        // if any three supplies are zero, returns one
        // otherwise returns 0
        // test by ramdomly setting province counts/removing cards until empty
        
        // fill up supplies
        fillSupply(Game);
        for (int i = 0; i < 1000; i++){
            int chosenCard = getRandomCard();
            
            //remove cards from random supplies
            Game.supplyCount[chosenCard]--;
        
            int expectedReturn = 0;
            int suppliesEmpty = 0;
            
            // check how many supplies are empty
            for (int j = Card.curse.ordinal(); j < Card.treasure_map.ordinal(); ++j){
                if (Game.supplyCount[j] == 0)
                    suppliesEmpty++;
            }
            // 3 or more supplies are empty
            if (suppliesEmpty > 2)
                expectedReturn = 1;
            
            // provinces empty
            if (Game.supplyCount[Card.province.ordinal()] == 0)
                expectedReturn = 1;
            int actualReturn = isGameOver(Game);
            
            // check if we predicted actual outcome via specifications
            assertEquals(expectedReturn,actualReturn,"returns not equal: " + expectedReturn + " " + actualReturn );
        }
        
    }
    public void testPlayCardAndCardEffect(GameState Game){
        //playCard
            // returns -1 if 
                // wrong phase (must be 0)
                //player has 0 or fewer actions
                // requested card is outside range of possible cards
            // number actions should be one fewer afterward
            // coins should be updated - may be larger than deck's treasure cards because of coin bonus from played cards 
        //cardEffect
            // all 
                    // return 0 if success
                    // most discard themselves so this should be the base assumption. Some trash themselves instead though
            // adventurer: keep drawing and discarding cards until you find 2 treasure cards
                    // discard should increase by the number of non-treasure cards drawn (can't predict which/how many ahead of time- might shuffle)
                    // hand will end up with 2 more copper, silver, or gold so long as there were that many in deck to begin with
                    // deck should decrease by amount gained by hand and by the opposite of how much the discard lost
                    // does this loop infinitely if we have fewer than 2 treasure in deck+discard to start with? 
            // ambassador - return at most 2 coppies of a card from hand to supply, and all opponents gain a copy of that card. If supply empty then cards given out via turn order starting with next player
                    // supply should change by +amount decided to discard -#other players
                            // unless expected supply would be negative, then it should decrease to 0
                    // returns -1 if player doesn't have enough to discard
            // 	Barron - either discard an estate for +4 coins or if you don't have an estate, gain an estate.
                    // if player has estate cards in hand, then
                            // coins should increase by 4 and hand should decrease by one estate 
                    // discard should increase by one estate regardless
            // cutpurse - gives player +2 coins and all other players either lose a copper or reveals their hands 
                    // we can't test revealing cards since that is a printed statement to the console
                    // can check 
                            // if other player hands were reduced by a copper if and only if they had one to begin with
                            // if coins increased by 2
            // council_room - +4 cards + 1 buy, all other players draw a card
                    // see if decks decreased and hands increased by amount expected
                    // see if numbuys increased as expected
            // embargo - +2 coins, trash embargo, adds embargo token to supply pile 
                    // returns -1 if supply is card isn't in play 
                    // player's hand should lose embargo but it should not be added to deck 
                    // coins should increase by 2
                    // embargo token for selected card should increase by one

            // feast - gain card costing up to 5 coins, trash feast 
                    // hand should decrease by feast 
                    // discard should increase by card if chosen one is under 5 coins and supply isn't out
            // gardens
                    // return -1
            // great hall - +1 card, +1 action, may discard for +2 gold
            // minion - +1 action either +2 gold, or discard your hand, draw 4 cards, and everyone else with at least 5 cards in their hand discards their hands and draws 4 cards 
            // smithy draw 3 cards, discard card from hand 
            // villiage draw a card, +2 actions
            // steward: draw 2 cards or +2 gold or trash two cards from hand

        nullifyDecks(Game);
        //phase, coins, num buys, num actions, 
        //return value
        //cards: all player's decks, hands, discards, supply 
        
        
        
        fillSupply(Game);
        int numCards = 200;
        fillDecks(Game, numCards);
        
        
        endTurn(Game);
        endTurn(Game);
        endTurn(Game);
        endTurn(Game);
        
        while(isGameOver(Game) == 0){
            for (int p = 0; p < Game.numPlayers; ++p){
                // reset back to initial state
                
                int failureExpected = 0;
                int expectedReturn = 0;
                int expectedBuys = 1;
                int expectedActions = 0;
                int expectedCoins = Game.coins;
                int handPos = 0;
                boolean noCard = false;
                boolean keepCoins = false;
                int choice1 = 0;
                int choice2 = 0;
                int choice3 = 0;
                int[] expectedEmbargo = new int[Card.treasure_map.ordinal()];
                for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
                    expectedEmbargo[i] = Game.embargoTokens[i];
                
                
                int[] expectedHandCounts = new int[Game.numPlayers];
                int[] expectedDeckCounts = new int[Game.numPlayers];
                int[] expectedDiscardCounts = new int[Game.numPlayers];
                int expectedPlayed = 1;
                for (int i = 0; i < Game.numPlayers; ++i){
                    expectedHandCounts[i] = Game.handCount[i];
                    expectedDeckCounts[i] = Game.deckCount[i];
                    expectedDiscardCounts[i] = Game.discardCount[i];
                }
                expectedHandCounts[p] = Game.handCount[p]-1;
                
                // while haven't found card and haven't reached end of hand
                    // check if is card we can use
                        // if so make predictions
                boolean findingCard = true;
                while (findingCard){   
                    int choiceOne = rand.nextInt(Card.treasure_map.ordinal()); 
                    int choiceTwo = rand.nextInt(Card.treasure_map.ordinal());
                    int choiceThree = rand.nextInt(Card.treasure_map.ordinal());                   
                    
                    // this is where we make predictions for each type of card
                    switch (Card.values()[Game.hand[p][handPos]]) {
                        // adventurer: keep drawing and discarding cards until you find 2 treasure cards
                                // discard should increase by the number of non-treasure cards drawn (can't predict which/how many ahead of time- might shuffle)
                                // hand will end up with 2 more copper, silver, or gold so long as there were that many in deck to begin with
                                // deck should decrease by amount gained by hand and by the opposite of how much the discard lost
                                // does this loop infinitely if we have fewer than 2 treasure in deck+discard to start with? 
                        case adventurer:
                            findingCard = false;
                            break;
                        // ambassador - return at most 2 coppies of a card from hand to supply, and all opponents gain a copy of that card. If supply empty then cards given out via turn order starting with next player
                                // supply should change by +amount decided to discard -#other players
                                        // unless expected supply would be negative, then it should decrease to 0
                                // returns -1 if player doesn't have enough to discard
                        case ambassador:
                            findingCard = false;
                            break;
                        // Barron - either discard an estate for +4 coins or if you don't have an estate, gain an estate.
                                // if player has estate cards in hand, then
                                        // coins should increase by 4 and hand should decrease by one estate 
                                // discard should increase by one estate regardless
                        case baron:
                            findingCard = false;
                            break;
                        // cutpurse - gives player +2 coins and all other players either lose a copper or reveals their hands 
                                // we can't test revealing cards since that is a printed statement to the console
                                // can check 
                                        // if other player hands were reduced by a copper if and only if they had one to begin with
                                        // if coins increased by 2
                        case cutpurse:
                            findingCard = false;
                            break;
                        // council_room - +4 cards + 1 buy, all other players draw a card
                                // see if decks decreased and hands increased by amount expected
                                // see if numbuys increased as expected
                        case council_room:
                            expectedBuys += 1;
                            
                            for (int i = 0; i < Game.numPlayers; ++i)
                                expectedDiscardCounts[i]++;                            
                            expectedDiscardCounts[p] +=4; // +4 cards +1 discarding this card -1
                            findingCard = false;
                            break;
                        // embargo - +2 coins, trash embargo, adds embargo token to supply pile 
                                // returns -1 if supply is card isn't in play 
                                // player's hand should lose embargo but it should not be added to deck 
                                // coins should increase by 2
                                // embargo token for selected card should increase by one
                        case embargo:
                            expectedCoins += 2;
                            keepCoins = true; // not implemented yet
                            if (Game.supplyCount[choiceOne] == -1)
                                failureExpected = -1;
                            else {
                                expectedEmbargo[choiceOne]++;
                                choice1 = choiceOne;
                            }               
                            expectedPlayed = 0;                    
                                    
                            findingCard = false;
                            break;
                        // feast - gain card costing up to 5 coins, trash feast 
                                // hand should decrease by feast 
                                // discard should increase by card if chosen one is under 5 coins and supply isn't out                        
                        case feast:                           
                            if (5 < getCost(choiceOne))
                                failureExpected = -1;
                            else if (Game.supplyCount[choiceOne] < 0)
                                failureExpected = -1;
                            else {
                                expectedDiscardCounts[p]++;
                            }
                            findingCard = false;
                            expectedPlayed = 0;
                            break;
                        // gardens
                                // return -1
                        case gardens:
                            findingCard = false;
                            noCard = true;
                            break;
                        // great hall - +1 card, +1 action, may discard for +2 gold
                        case great_hall:
                            findingCard = false;
                            break;
                        // minion - +1 action either +2 gold, or discard your hand, draw 4 cards, and everyone else with at least 5 cards in their hand discards their hands and draws 4 cards 
                        case minion:
                            findingCard = false;
                            break;
                        // smithy draw 3 cards, discard card from hand
                        case smithy:
                            findingCard = false;
                        
                            break;
                        // villiage draw a card, +2 actions
                        case village:
                            findingCard = false;
                            break;
                        // steward: draw 2 cards or +2 gold or trash two cards from hand
                        case steward:
                            findingCard = false;
                            break;
                        default:
                            break;
                    }
                    
                    // tells us to leave loop and if we can't play any card to skip over checking the return value
                    if (handPos == Game.handCount[p]-1){
                        findingCard = false;
                        noCard = true;
                    }
                    if (findingCard == true)
                        ++handPos;
                    else
                        System.out.println(Card.values()[Game.hand[p][handPos]]);
                    //System.out.println(Game.handCount[p] + " " + handPos);
                }
                
                if (Game.phase !=0)
                    failureExpected = -1;
                if (Game.numActions < 1)
                    failureExpected = -1;
                if (Game.hand[p][handPos] < Card.adventurer.ordinal() || Game.hand[p][handPos] > Card.treasure_map.ordinal())
                    failureExpected = -1;
                if (failureExpected == -1){
                    expectedReturn = -1;
                    expectedActions = 1;
                    expectedHandCounts[p] = 5;
                }
                
                if (noCard == false){
                    int actualReturn = playCard(handPos,choice1,choice2,choice3,Game);
                    assertEquals(expectedReturn,actualReturn, "return unexpected");
                }
                else
                {
                    expectedActions = 1;
                    expectedHandCounts[p] = 5;
                    
                }                
                
                assertEquals(expectedCoins,Game.coins, "coins unexpected: " + expectedCoins + " " + Game.coins );
                assertEquals(expectedActions,Game.numActions, "actions unexpected");
                assertEquals(expectedPlayed,Game.numActions, "played unexpected");
                for (int i = 0; i < Game.numPlayers; ++i){
                    assertEquals(expectedHandCounts[i],Game.handCount[i], i+ " hand unexpected: " + expectedHandCounts[i] + " " + Game.handCount[i]);
                    assertEquals(expectedDeckCounts[i],Game.deckCount[i], "deck unexpected "+ expectedDeckCounts[i] + " " + Game.deckCount[i]);
                    assertEquals(expectedDiscardCounts[i],Game.discardCount[i], "discard unexpected " + expectedDiscardCounts[i] + " " + Game.discardCount[i]);
                }
                for (int i = Card.curse.ordinal(); i < Card.treasure_map.ordinal(); ++i)
                    assertEquals(expectedEmbargo[i],Game.embargoTokens[i], "embargo unexpected: " + expectedEmbargo[i] + " " + Game.embargoTokens[i]);
                
                
                
                endTurn(Game);
            }
            Game.supplyCount[Card.province.ordinal()] = 0;
            
        }
    }
    public void unitTestOne(){
        
        // set kingdom cards
        int[] k = new int[]{Card.adventurer.ordinal(), Card.gardens.ordinal(), 
                Card.embargo.ordinal(), Card.village.ordinal(), Card.minion.ordinal(), 
                Card.mine.ordinal(), Card.cutpurse.ordinal(), Card.sea_hag.ordinal(), 
                Card.tribute.ordinal(), Card.smithy.ordinal()};

        System.out.println("Starting game");
        GameState Game = initializeGame(4,k,0);
  
        //drawCard
            // things we need to check:
                //shuffles deck if empty
                    // returns -1 if deck empty after shuffling
                    // if shuffled, should empty discard properly
                //player hand grows by one, deck shrinks by one
                //deck/hand/discard counts updated properly 
                //check the same card that was removed from the deck was added to the hand   
                
        System.out.println("testing drawCard():");
        for (int i = 0; i < 100; ++i) 
            testDrawCard(Game);
                    
            
        //gainCard
            // things we need to check:
                // toFlag: 0 adds to discard, 1 adds to deck, 2 adds to hand (anything else counts as 2)
                // counts of both supply pile and appropriate player pile update correctly
                // the card added to pile was same as supply pile 
                // returns -1 if supply pile count is 0 or fewer 
            // implementation:
            
        System.out.println("testing gainCard():");
        for (int i = 0; i < 100; ++i) 
            testGainCard(Game);
           
        //buyCard
            // things we need to check:
                // return -1 if 
                    // number of buys too few
                    // there isn't enough supply of that card
                    // if intended supply pile is invalid (out of range)
                    // if player doesn't have enough money
                // counts of supply pile and player update correctly
                // player actually gains the card they bought
                // player coins reduced by the cost of the card
            // implementation:
        
        System.out.println("testing buyCard():");
        System.out.println("(buyCard() is intentionally broken right now");
        for (int i = 0; i < 1; ++i) 
            testBuyCard(Game);
        
        //shuffle
            // things to check:                
                // deck is actually shuffled (might randomly be the same)
                // discard pile is added to deck iff no cards left in deck
                    // discard pile is empty after doing so
                // works for all players
                
        System.out.println("testing shuffle():");
        nullifyDecks(Game);
        fillDecks(Game, 50);
        for (int i = 0; i < 100; ++i) 
            testShuffle(i%Game.numPlayers,Game);
        
        //discardCard
            // things we need to check:
                // card actually added to play pile or removed entirely depending on trash flag
                // all piles updated correctly - hand goes down, discard goes up by exactly one
                // card actually removed from correct player hand
                // returns -1 if player's hand has no cards in it, 0 otherwise
               
        System.out.println("testing discardCard():"); 
        for (int i = 0; i < 1; ++i) 
            testDiscardCard(Game);
       

        
    }
    
    public void unitTestTwo(){
        
        //    set kingdom cards
        int[] k = new int[]{Card.adventurer.ordinal(), Card.gardens.ordinal(), 
                Card.embargo.ordinal(), Card.village.ordinal(), Card.minion.ordinal(), 
                Card.mine.ordinal(), Card.cutpurse.ordinal(), Card.sea_hag.ordinal(), 
                Card.tribute.ordinal(), Card.smithy.ordinal()};

        GameState Game = initializeGame(4,k,0);
        
        // all functions:
        
        //scorefor          
            // empty deck hand and discards, or loop through existing gamestates and value current cards manually
            // loop applying randomized gaincards to various players, adding up and seeing what score _should_ be. 
            // apply score for and see if it matches
        System.out.println("testing scoreFor():");
        for (int i = 0; i < 100; ++i) 
            testScoreFor(Game);
        //updateCoins
            // game.coins should total # copper*1+silver*2+gold*3+bonus - can use fullDeckCount for this
        
        System.out.println("testing updateCoins():");
        for (int i = 0; i < 100; ++i) 
            testUpdateCoins(Game);
        //getWinners
            // highest score wins
            // if same score, look at turns - fewest wins
            // if same # turns, both win
            
        System.out.println("testing getWinners():");
        for (int i = 0; i < 1000; ++i) 
            testGetWinners(Game);
        
        
        //endTurn
            // actually moves player's hand and played pile to discard 
            // turn is reset: whoseTurn = next, buys = 1, actions = 1, phase = 0, hand count = 5, played pile count = 0
            // coins properly set
            // new player hand actually has 5 cards and their deck has five fewer- and those cards are same values as drawn deck cards
        
        System.out.println("testing endTurn():");
        for (int i = 0; i < 100; ++i) 
            testEndTurn(Game);
        System.out.println("testing playCard() & cardEffect():");   
        //playCard
            // returns -1 if 
                // wrong phase (must be 0)
                //player has 0 or fewer actions
                // requested card is outside range of possible cards
            // number actions should be one fewer afterward
            // coins should be updated - may be larger than deck's treasure cards because of coin bonus from played cards 
        //cardEffect
            // all 
                    // return 0 if success
                    // most discard themselves so this should be the base assumption. Some trash themselves instead though
            // adventurer: keep drawing and discarding cards until you find 2 treasure cards
                    // discard should increase by the number of non-treasure cards drawn (can't predict which/how many ahead of time- might shuffle)
                    // hand will end up with 2 more copper, silver, or gold so long as there were that many in deck to begin with
                    // deck should decrease by amount gained by hand and by the opposite of how much the discard lost
                    // does this loop infinitely if we have fewer than 2 treasure in deck+discard to start with? 
            // ambassador - return at most 2 coppies of a card from hand to supply, and all opponents gain a copy of that card. If supply empty then cards given out via turn order starting with next player
                    // supply should change by +amount decided to discard -#other players
                            // unless expected supply would be negative, then it should decrease to 0
                    // returns -1 if player doesn't have enough to discard
            // 	Barron - either discard an estate for +4 coins or if you don't have an estate, gain an estate.
                    // if player has estate cards in hand, then
                            // coins should increase by 4 and hand should decrease by one estate 
                    // discard should increase by one estate regardless
            // cutpurse - gives player +2 coins and all other players either lose a copper or reveals their hands 
                    // we can't test revealing cards since that is a printed statement to the console
                    // can check 
                            // if other player hands were reduced by a copper if and only if they had one to begin with
                            // if coins increased by 2
            // council_room - +4 cards + 1 buy, all other players draw a card
                    // see if decks decreased and hands increased by amount expected
                    // see if numbuys increased as expected
            // embargo - +2 coins, trash embargo, adds embargo token to supply pile 
                    // returns -1 if supply is card isn't in play 
                    // player's hand should lose embargo but it should not be added to deck 
                    // coins should increase by 2
                    // embargo token for selected card should increase by one

            // feast - gain card costing up to 5 coins, trash feast 
                    // hand should decrease by feast 
                    // discard should increase by card if chosen one is under 5 coins and supply isn't out
            // gardens
                    // return -1
            // great hall - +1 card, +1 action, may discard for +2 gold
            // minion - +1 action either +2 gold, or discard your hand, draw 4 cards, and everyone else with at least 5 cards in their hand discards their hands and draws 4 cards 
            // smithy draw 3 cards, discard card from hand 
            // villiage draw a card, +2 actions
            // steward: draw 2 cards or +2 gold or trash two cards from hand
        for (int i = 0; i < 6; ++i) 
            testPlayCardAndCardEffect(Game);
        //isGameOver
            // if province supply zero, returns 1
            // if any three supplies are zero, returns one
            // otherwise returns 0
            // test by ramdomly setting province counts/removing cards until empty
        System.out.println("testing GameOver():");
        for (int i = 0; i < 100; ++i) 
            testIsGameOver(Game);
        //initializeGame
        System.out.println("testing InitilizeGame():");
        
    }
}