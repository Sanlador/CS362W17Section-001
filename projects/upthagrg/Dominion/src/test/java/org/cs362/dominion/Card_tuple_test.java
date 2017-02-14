package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class Card_tuple_test {
	//fail("Not yet implemented");
	@Test
	public void test() {
		
		card_tuple MyTuple;
		
		MyTuple = new card_tuple("GOLD");
		if((MyTuple.getdc().getName() != "GOLD") || (MyTuple.getrem() != 30)){
			System.out.println("Failed card_tuple constructor test");
		}
		else{
			System.out.println("Passed card_tuple constructor test");
		}
		
		MyTuple.setrem(5);
		if(MyTuple.getrem() != 5){
			System.out.println("Failed card_tuple setrem test");
		}
		else{
			System.out.println("Passed card_tuple setrem test");
		}
		
	}

}
