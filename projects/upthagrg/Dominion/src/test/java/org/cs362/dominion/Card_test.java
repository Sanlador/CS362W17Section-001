package org.cs362.dominion;

import static org.junit.Assert.*;

import org.cs362.dominion.card.Type;
import org.junit.Test;

public class Card_test {
	//fail("Not yet implemented");
	@Test
	public void test() {

		card MyCard;
		
		MyCard = new card();
		if((MyCard.getName() != null) || (MyCard.getCost() != 0) || (MyCard.getTV() != 0) || (MyCard.getVV() != 0) || (MyCard.getType1() != null) || (MyCard.getType2() != null)){
			System.out.println("Failed Card default constructor test");
		}
		else{
			System.out.println("Passed Card default constructor test");
		}
		
		MyCard.setCost(5);
		if(MyCard.getCost() != 5){
			System.out.println("Failed Card setcost test");
		}
		else{
			System.out.println("Passed Card setcost test");
		}
		
		MyCard.setVV(5);
		if(MyCard.getVV() != 5){
			System.out.println("Failed Card setVV test");
		}
		else{
			System.out.println("Passed Card setVV test");
		}
		
		MyCard.setTV(5);
		if(MyCard.getTV() != 5){
			System.out.println("Failed Card TV test");
		}
		else{
			System.out.println("Passed Card TV test");
		}
		
		MyCard.setName("COPPER");
		if(MyCard.getName() != "COPPER"){
			System.out.println("Failed Card setName test");
		}
		else{
			System.out.println("Passed Card setName test");
		}
		
		MyCard.setType1(card.Type.TREASURE);
		if(MyCard.getType1() != card.Type.TREASURE){
			System.out.println("Failed Card setType1 test");
		}
		else{
			System.out.println("Passed Card setType1 test");
		}
		
		MyCard.setType2(card.Type.TREASURE);
		if(MyCard.getType2() != card.Type.TREASURE){
			System.out.println("Failed Card setType2 test");
		}
		else{
			System.out.println("Passed Card setType2 test");
		}
		
		MyCard.setType(card.Type.TREASURE);
		if((MyCard.getType1() != card.Type.TREASURE) || (MyCard.getType2() != null)){
			System.out.println("Failed Card setType test");
		}
		else{
			System.out.println("Passed Card setType test");
		}
		
		MyCard = new card("COPPER", card.Type.TREASURE, 0, 1);
		if((MyCard.getName() != "COPPER") || (MyCard.getCost() != 0) || (MyCard.getTV() != 1) || (MyCard.getVV() != 0) || (MyCard.getType1() != card.Type.TREASURE) || (MyCard.getType2() != null)){
			System.out.println("Failed Card non-default constructor test");
		}
		else{
			System.out.println("Passed Card non-default constructor test");
		}
		
		action_card MyCard2;
		MyCard2 = new action_card("ADVENTURER");
		if((MyCard2.getName() != "ADVENTURER") || (MyCard2.getType1() != card.Type.ACTION) || (MyCard2.getCost() != 6)){
			System.out.println("Failed Action_Card non-default constructor test");
			//MyCard2.printcard();
		}
		else{
			System.out.println("Passed Action_Card non-default constructor test");
		}
		
		
		
	}

}
