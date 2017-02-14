package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class Player_test {

	@Test
	public void test() {

		player MyPlayer;
		
		MyPlayer = new player("P1");
		if((MyPlayer.getName() != "P1") || (MyPlayer.getBuys() != 1) || (MyPlayer.getMoney() != 0) || (MyPlayer.getActions() != 1) || (MyPlayer.getHand().size() != 5)){
			System.out.println("Failed player constructor test");
			System.out.println("Name(P1): " + MyPlayer.getName());
			System.out.println("Buys(1): " + MyPlayer.getBuys());
			System.out.println("Actions(1): " + MyPlayer.getActions());
			System.out.println("Money(0): " + MyPlayer.getMoney());
			//fail("Player Constructor test failed");
		}
		else{
			System.out.println("Passed player constructor test");
		}
		
		card card0 = MyPlayer.getDeck().get(0);
		card card1 = MyPlayer.getDeck().get(1);
		card card2 = MyPlayer.getDeck().get(2);
		card card3 = MyPlayer.getDeck().get(3);
		card card4 = MyPlayer.getDeck().get(4);
		MyPlayer.shuffle_deck();
		if((card0 == MyPlayer.getDeck().get(0)) && (card4 == MyPlayer.getDeck().get(4)) && (card1 == MyPlayer.getDeck().get(1)) && (card2 == MyPlayer.getDeck().get(2)) && (card3 == MyPlayer.getDeck().get(3))  ){
			System.out.println("Failed player shuffle deck test");
		}
		else{
			System.out.println("Passed player shuffle deck test");
		}
		
		board b1 = new board();
		MyPlayer.buy(b1);
		MyPlayer.buy(b1);
		MyPlayer.buy(b1);
		MyPlayer.buy(b1);
		MyPlayer.buy(b1);
		if(MyPlayer.getDiscard().size() + MyPlayer.getHand().size() + MyPlayer.getDeck().size() <= 10){
			System.out.println("Failed player buy test");
		}
		else{
			System.out.println("Passed player buy test");
		}
		
		
	}

}
