//package moretesting;

import java.util.*;
import java.util.concurrent.TimeUnit;


public class player extends main {
	
	int actionpoints;
	int buypoints;
	int cards;
	int coin;
	int decksize;
	int handsize;
	int discardsize;
	int handcounter;
	int coinbonus;
	
	//ugh
	int coppersize, silversize, goldsize, platinumsize, estatesize, duchysize, provincesize, colonysize, cellarsize;
	int marketsize, militiasize, minesize, moatsize, remodelsize, smithysize, villagesize, woodcuttersize, workshopsize;

	
	card[] hand = new card[100];
	card[] deck = new card[100];
	card[] discard = new card[100];
	
	card[] copperpile = new card[50];
	card[] silverpile = new card[50];
	card[] goldpile = new card[50];
	card[] platinumpile = new card[50];
	card[] estatepile = new card[50];
	card[] duchypile = new card[50];
	card[] provincepile = new card[50];
	card[] colonypile = new card[50];
	card[] cellarpile = new card[50];
	card[] marketpile = new card[50];
	card[] militiapile = new card[50];
	card[] minepile = new card[50];
	card[] moatpile = new card[50];
	card[] remodelpile = new card[50];
	card[] smithypile = new card[50];
	card[] villagepile = new card[50];
	card[] woodcutterpile = new card[50];
	card[] workshoppile = new card[50];
	
	//computer flags for spice
	boolean gotmarket = false;
	boolean gotsmithy = false;
	boolean gotworkshop = false;
	boolean gotvillage = false;
	boolean gotgold = false;
	boolean gotremodel = false;
	
	int compscore=0;
	int playerscore=0;
	
	public player(){
		Random rand = new Random();
	}
	//initializes game and all the card piles
	public void startRound(){
		
		for(int i = 0; i < 100; i++){
			card cardobj = new card();
			deck[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		
		for(int i = 0; i < 100; i++){
			card cardobj = new card();
			hand[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 100; i++){
			card cardobj = new card();
			discard[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			copperpile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			silverpile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			goldpile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			platinumpile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			estatepile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			duchypile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			provincepile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			colonypile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			cellarpile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			marketpile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			militiapile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			minepile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			moatpile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			remodelpile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			smithypile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			woodcutterpile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			villagepile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		for(int i = 0; i < 50; i++){
			card cardobj = new card();
			workshoppile[i] = cardobj;
			//System.out.println("placeholder" + i);
		}
		
		
		//three estate cards
		
		for(int i = 0; i < 3; i ++){
			card cardobj = new card();
			card.assignCard(5, cardobj);
			deck[i] = cardobj;
			//System.out.println(deck[i].category + i);
		}
		
		
		//seven copper cards
		
		for (int i = 3; i< 10; i ++){
			card cardobj = new card();
			card.assignCard(1, cardobj);
			deck[i] = cardobj;
			//System.out.println(deck[i].category + i);
		}
		
		
		//populate every pile in the middle
		
		for (int i = 0; i < 46; i ++){
			card cardobj = new card();
			card.assignCard(1, cardobj);
			copperpile[i] = cardobj;
		}
		for (int i = 0; i < 40; i ++){
			card cardobj = new card();
			card.assignCard(2, cardobj);
			silverpile[i] = cardobj;
		}
		for (int i = 0; i < 30; i ++){
			card cardobj = new card();
			card.assignCard(3, cardobj);
			goldpile[i] = cardobj;
		}
		for (int i = 0; i < 20; i ++){
			card cardobj = new card();
			card.assignCard(4, cardobj);
			platinumpile[i] = cardobj;
		}
		for (int i = 0; i < 18; i ++){
			card cardobj = new card();
			card.assignCard(5, cardobj);
			estatepile[i] = cardobj;
		}
		for (int i = 0; i < 12; i ++){
			card cardobj = new card();
			card.assignCard(6, cardobj);
			duchypile[i] = cardobj;
		}
		for (int i = 0; i < 12; i ++){
			card cardobj = new card();
			card.assignCard(7, cardobj);
			provincepile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(8, cardobj);
			colonypile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(11, cardobj);
			cellarpile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(15, cardobj);
			marketpile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(9, cardobj);
			militiapile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(10, cardobj);
			minepile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(12, cardobj);
			moatpile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(13, cardobj);
			remodelpile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(16, cardobj);
			smithypile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(17, cardobj);
			villagepile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(14, cardobj);
			woodcutterpile[i] = cardobj;
		}
		for (int i = 0; i < 10; i ++){
			card cardobj = new card();
			card.assignCard(18, cardobj);
			workshoppile[i] = cardobj;
		}
		
		//draw 5 cards into hand
				for(int i = 0; i < 5; i++){
				
					findDeckSizes();
					int n = (int)(Math.random() * decksize);
					moveCard(deck, hand, n, handsize);
					decksize--;
					handsize++;
				}
		
		
	}
	//displays information regarding hand and coin, buys, etc.
	public int getSizeJr(int xSize, card[] pile){
		
		xSize = 0;
		while(pile[xSize].num != -1){
			xSize++;
		}
		
		
		return xSize;
	}
	//finds the sizes of all decks
	public void findDeckSizes(){
		
		//finds the sizes of the three main decks: used for looping through decks
		
		decksize = getSizeJr(decksize, deck);
		handsize = getSizeJr(handsize, hand);
		discardsize = getSizeJr(discardsize, discard);
		coppersize = getSizeJr(coppersize, copperpile);
		silversize = getSizeJr(silversize, silverpile);
		goldsize = getSizeJr(goldsize, goldpile);
		platinumsize = getSizeJr(platinumsize, platinumpile);
		estatesize = getSizeJr(estatesize, estatepile);
		duchysize = getSizeJr(duchysize, duchypile);
		provincesize = getSizeJr(provincesize, provincepile);
		colonysize = getSizeJr(colonysize, colonypile);
		cellarsize = getSizeJr(cellarsize, cellarpile);
		marketsize = getSizeJr(marketsize, marketpile);
		militiasize = getSizeJr(militiasize, militiapile);
		minesize = getSizeJr(minesize, minepile);
		moatsize = getSizeJr(moatsize, moatpile);
		remodelsize = getSizeJr(remodelsize, remodelpile);
		smithysize = getSizeJr(smithysize, smithypile);
		villagesize = getSizeJr(villagesize, villagepile);
		woodcuttersize = getSizeJr(woodcuttersize, woodcutterpile);
		workshopsize = getSizeJr(workshopsize, workshoppile);
		
	}
	//moves a card from deck1 into deck2
	//x is rand num, incsize is the size of the deck you are placing the card into
	public void moveCard(card[] deck1, card[] deck2, int x, int incsize){
		//copies card from deck1 into deck2
	    card obj = new card();
	    obj = deck1[x];
	    deck2[incsize] = obj;
	    
	    //removes card from deck1
	    System.arraycopy(deck1,x+1,deck1,x,deck1.length-1-x);
	
	}
	//validates input
	public int inpVal(){
		
		Scanner reader = new Scanner(System.in);  // Reading from System.in
		int input = reader.nextInt();
		
		//too big too small
		if (input < 1 || input > 100){
			return -1;
		}
		
		else
		return input;
	}
	//displays player stats as round progresses
	public void display(){
		//calc how much stuff in hand
	
		System.out.println("coins: " + coin + " cards: " + cards +" buys: " + buypoints + " actions: " + actionpoints);
		System.out.println("===========================================================");
		for (int i = 0; i < handsize; i ++){
			System.out.println(i+1 + "   " + hand[i].category + "   " + hand[i].description);
		}
	}
	//uses the "action" of a card. STILL NEEDS SPECIAL ACTIONS IMPLEMENTED
	public void activateCard(card cardin){
		//use this when we use an action point on a card
		
		coinbonus += cardin.coinadd;
		actionpoints+= cardin.actionadd;
		buypoints += cardin.buyadd;
		
		System.out.println("You chose " + cardin.category);
		System.out.println(cardin.description);
		
		
		//if we want to add cards
		if(cardin.cardadd >0)
		{
			findDeckSizes();
			
			//make sure deck has cards to draw from
			if(decksize>1)
			{
				//draw as many cards as the action requires
			for (int i = cardin.cardadd; i >0; i--)
				{
				cards += 1;
			
				int n = (int)(Math.random() * decksize);
				moveCard(deck, hand, n, handsize);
				decksize--;
				handsize++;
				System.out.println("Drawing a card...");
				}
			}
			else{
				System.out.println("your deck is out of cards!");
			
				}
		}
		
		//If a card has special abilities they are activated here
		System.out.println("Special abilities beyond basics not included in demo: please pay 15$ to @BrennanGiles for full content ^_^");
		
	}
	//buys a card from pile and places it into pile that owns pilesize
	public void buyCard(card[] pile,int origsize, int size){
		// We need to test if player has enough gold, and if the pile has enough cards in it
		
		//pile has more cards than zero?
		if (pile[0].num == -1){
			System.out.println("Pile is out of cards!");
		}
		else{
			//enough coin?
			if(coin < pile[0].cost){
				System.out.println("Not enough coin dummy");
			}
			//good to go
			else{
				//randomized card for no reason other than i know it works 
				int n = (int)(Math.random() * origsize);
				moveCard(pile, discard, n, size);
				coin = coin - pile[0].cost;
				
			}
		}
		
	}
	//plays a round of dominion
	public void playRound(){
		
		actionpoints = 1;
	    buypoints = 1;
		cards = 5;
		coin = 0;
		coinbonus = 0;
		int input;
	
		
///////////////////////////////////////////////////////////////////////////////////////////////////
		
		do
		{
			//find how much coin in hand
			coin = 0;
			handcounter = 0;
			while(hand[handcounter].num != -1){
				
				if(hand[handcounter].num == 1){
					coin = coin + 1;
				}
				if(hand[handcounter].num == 2){
					coin = coin + 2;
				}
				if(hand[handcounter].num == 3){
					coin = coin + 3;
				}
				if(hand[handcounter].num == 4){
					coin = coin + 5;
				}
				handcounter ++;
			}
		coin = coin + coinbonus;
		display();
		System.out.println("           ! Action Phase !");
		System.out.println("---Chose card (1-n), 0 for no action---");
		
		input = inpVal();
		if( input == -1){ //no action
			System.out.println("No action");
		}
		//not a card in hand
		else if(hand[input-1].num == -1){
			System.out.println("no such card in hand!");
		}
		else{ //action
			//first determine if card has action ability
			//if not just skip
			//if so figure out what that action is and do it
			if(hand[input-1].num < 9){
				System.out.println("Card does not have action ability, skipping...");
			}
			else{
					activateCard(hand[input-1]);
					System.out.println("Moving" + hand[input-1].category + " to discard pile...");
					moveCard(hand, discard, input-1, discardsize);
					handsize--; discardsize++;
				}
			}
		actionpoints--;
		}while (actionpoints >0);
		///////////////////////////////////////////////////////////////////////////////////////////
		do{
		display();
		System.out.println("           ! Buy Phase !");
		System.out.println("---Chose card to buy, 0 for none---");
			
			findDeckSizes();
			System.out.println(1 + " copper		cost " + 0 + "    left in pile: " + coppersize);
			System.out.println(2 + " silver		cost " + 3 + "    left in pile: " + silversize);
			System.out.println(3 + " gold			cost " + 6 + "    left in pile: " + goldsize);
			System.out.println(4 + " platinum		cost " + 9 + "    left in pile: " + platinumsize);
			System.out.println(5 + " estate		cost " + 2 + "    left in pile: " + estatesize);
			System.out.println(6 + " duchy			cost " + 5 + "    left in pile: " + duchysize);
			System.out.println(7 + " province		cost " + 8 + "    left in pile: " + provincesize);
			System.out.println(8 + " colony		cost " + 11 + "   left in pile: " + colonysize);
			System.out.println(9 + " militia		cost " + 4 + "    left in pile: " + militiasize);
			System.out.println(10 + " mine			cost " + 5 + "    left in pile: " + minesize);
			System.out.println(11 + " cellar		cost " + 2 + "    left in pile: " + cellarsize);
			System.out.println(12 + " moat			cost " + 2 + "    left in pile: " + moatsize);
			System.out.println(13 + " remodel		cost " + 4 + "    left in pile: " + remodelsize);
			System.out.println(14 + " woodcutter		cost " + 3 + "    left in pile: " + woodcuttersize);
			System.out.println(15 + " market		cost " + 5 + "    left in pile: " + marketsize);
			System.out.println(16 + " smithy		cost " + 4 + "    left in pile: " + smithysize);
			System.out.println(17 + " village		cost " + 3 + "    left in pile: " + villagesize);
			System.out.println(18 + " workshop		cost " + 3 + "    left in pile: " + workshopsize);
			
			input = inpVal();		
			findDeckSizes();
			
			if( input == -1){ //no buy
				System.out.println("No Buy");
			}
			else
			{ //Buy
				  // move card from chosen pile into discard pile
				  // remove gold from player equal to card cost
				switch(input){
				case 1: buyCard(copperpile, coppersize, discardsize); coppersize--; discardsize++; break;
				case 2: buyCard(silverpile, silversize, discardsize); silversize--; discardsize++; break;
				case 3: buyCard(goldpile, goldsize, discardsize); goldsize--; discardsize++; break;
				case 4: buyCard(platinumpile, platinumsize, discardsize); platinumsize--; discardsize++; break;
				case 5: buyCard(estatepile, estatesize, discardsize); estatesize--; discardsize++; break;
				case 6: buyCard(duchypile, duchysize, discardsize); duchysize--; discardsize++; break;
				case 7: buyCard(provincepile, provincesize, discardsize); provincesize--; discardsize++; break;
				case 8: buyCard(colonypile, colonysize, discardsize); colonysize--; discardsize++; break;
				case 9: buyCard(militiapile, militiasize, discardsize); militiasize--; discardsize++; break;
				case 10: buyCard(minepile, minesize, discardsize); minesize--; discardsize++; break;
				case 11: buyCard(cellarpile, cellarsize, discardsize); cellarsize--; discardsize++; break;
				case 12: buyCard(moatpile, moatsize, discardsize); moatsize--; discardsize++; break;
				case 13: buyCard(remodelpile, remodelsize, discardsize); remodelsize--; discardsize++; break;
				case 14: buyCard(woodcutterpile, woodcuttersize, discardsize); woodcuttersize--; discardsize++; break;
				case 15: buyCard(marketpile, marketsize, discardsize); marketsize--; discardsize++; break;
				case 16: buyCard(smithypile, smithysize, discardsize); smithysize--; discardsize++; break;
				case 17: buyCard(villagepile, villagesize, discardsize); villagesize--; discardsize++; break;
				case 18: buyCard(workshoppile, workshopsize, discardsize); workshopsize--; discardsize++; break;
				default:
					System.out.println("Not valid option, skipping");
				
				}
			}	
		
		buypoints--;
		}while(buypoints >0);
		/////////////////////////////////////////////////////////////////////////////////////////
		display();
		System.out.println("           ! Clean up Phase, turn ending... !");
		//discard hand
		
		System.out.println("Discarding hand and deck...");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Shuffling...");
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while(hand[0].num != -1){
			findDeckSizes();
			int n = (int)(Math.random() * handsize);
			moveCard(hand, discard, n, discardsize);
			handsize--; discardsize++;
		}
		
		//discard deck
		
		while(deck[0].num != -1){
			findDeckSizes();
			int n = (int)(Math.random() * decksize);
			moveCard(deck, discard, n, discardsize);
			decksize--; discardsize++;
		}
		
		//Calculate Player score
		playerscore = 0;
		for(int i = 0; i < discardsize; i++){
			if(discard[i].num == 5) playerscore = playerscore+1;
			if(discard[i].num == 6) playerscore = playerscore+3;
			if(discard[i].num == 7) playerscore = playerscore+6;
			if(discard[i].num == 8) playerscore = playerscore+10;
		}
		
		
		//Move entire discard pile into Deck
		while(discard[0].num != -1){
			findDeckSizes();
			int n = (int)(Math.random() * discardsize);
			moveCard(discard, deck, n, decksize);
			discardsize--; decksize++;
		}
		
		//draw five new cards
		for(int i = 0; i < 5; i++){
				
			findDeckSizes();
			int n = (int)(Math.random() * decksize);
			moveCard(deck, hand, n, handsize);
			decksize--; handsize++;
			}
	
}	
	//plays a round for the computer
	public void compRound(){
		
		
		System.out.println("################################################");
		boolean done = true;
		do{
		int n = (int)(Math.random() * 6);
		
		System.out.println("     !Action Phase for Computer Player!     ");
		System.out.println("Computer player is thinking...");
		try {
			TimeUnit.SECONDS.sleep(3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(n == 0){
			System.out.println("Computer Player Eyes you wearily, and takes no action");
			done = true;
		}
		else if(n == 1 && gotmarket == true){
			System.out.println("Computer Player activates Market, getting a card, coin, buy, and action");
			done = false;
		}
		else if(n == 2 && gotsmithy == true){
			System.out.println("Computer Player activates Smithy and draws three cards");
			done = true;
		}
		else if(n == 3 && gotworkshop == true){
			System.out.println("Computer Player Activates Workshop and gains a Smithy");
			gotsmithy = true;
			done = true;
		}
		else if(n == 4 && gotvillage == true){
			System.out.println("Computer player Activates Village and gains a card + action");
			done = false;
		}
		else if(n == 5 && gotgold == true){
			System.out.println("Computer Player flaunts their gold card at you, but takes no action");
			done = true;
		}
		else if(n == 6 && gotremodel == true){
			System.out.println("Computer player Activates remodel; trashing a copper and gaining a silver");
			done = true;
		}
		else{
			System.out.println("Computer Player chooses no action");
		}
		}while (done == false);
		
		
		//////////////////////////////////////////////////////////////////////////////////////////////
		System.out.println("     !Buy Phase for Computer Player!     ");
		done = true;
		do{
		int n = (int)(Math.random() * 9);
		
		
		System.out.println("Computer player is thinking...");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(n == 0){
			System.out.println("Computer Player Smirks, and buys a duchy");
			System.arraycopy(duchypile,0+1,duchypile,0,duchypile.length-1-0);
			duchysize--;
			compscore = compscore+3;
			done = true;
		}
		else if(n == 1 && gotmarket == false){
			System.out.println("Computer Player buys a Market, and grins slightly");
			System.arraycopy(marketpile,0+1,marketpile,0,marketpile.length-1-0);
			marketsize--;
			gotmarket = true;
			done = true;
		}
		else if(n == 2 && gotsmithy == false){
			System.out.println("Computer Player buys a Smithy, and shuffles it into discard pile whilst deep in thought");
			System.arraycopy(smithypile,0+1,smithypile,0,smithypile.length-1-0);
			smithysize--;
			gotsmithy = true;
			done = true;
		}
		else if(n == 3 && gotworkshop == false){
			System.out.println("Computer Player buys a workshop, and nervously nibbles on a Dorito");
			System.arraycopy(workshoppile,0+1,workshoppile,0,workshoppile.length-1-0);
			workshopsize--;
			gotworkshop = true;
			done = true;
		}
		else if(n == 4 && gotvillage == false){
			System.out.println("Computer player buys a village, and chuckles as it remembers a bad joke");
			System.arraycopy(villagepile,0+1,villagepile,0,villagepile.length-1-0);
			villagesize--;
			gotvillage = true;
			done = true;
		}
		else if(n == 5 && gotgold == false){
			System.out.println("Computer Player taunts you as it buys a gold, triumphantly adding it to it's discard pile");
			System.arraycopy(goldpile,0+1,goldpile,0,goldpile.length-1-0);
			goldsize--;
			gotgold = true;
			done = true;
		}
		else if(n == 6 && gotremodel == true){
			System.out.println("Computer player annoyingly answers a text before finally deciding to purchase a remodel card");
			System.arraycopy(remodelpile,0+1,remodelpile,0,remodelpile.length-1-0);
			remodelsize--;
			gotremodel = true;
			done = true;
		}
		else if(n == 7){
			System.out.println("Computer Player buys a Province, and looks hopeful");
			System.arraycopy(provincepile,0+1,provincepile,0,provincepile.length-1-0);
			provincesize--;
			compscore = compscore + 6;
			done = true;
		}
		else if (n == 8 && gotgold == true){
			System.out.println("Computer Player uses it's gold card to afford a Colony!");
			System.arraycopy(colonypile,0+1,colonypile,0,colonypile.length-1-0);
			colonysize--;
			compscore = compscore+10;
			done = true;
		}
		else if(n == 9){
			System.out.println("Computer Player buys a Province, and looks hopeful");
			System.arraycopy(provincepile,0+1,provincepile,0,provincepile.length-1-0);
			provincesize--;
			compscore = compscore + 6;
			done = true;
		}
		else{
			int m = (int)(Math.random() * 1);
			if (m == 1){
			System.out.println("Computer Player smiles cheerfully and buys a Silver card");
			System.arraycopy(silverpile,0+1,silverpile,0,silverpile.length-1-0);
			silversize--;
			}
			else{
				done = false;
			}
		}
		}while (done == false);
		
		System.out.println("Computer Player discards it's hand and complete's their turn");
	}
			
}
	
	



