package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class Board_test {
	//fail("Not yet implemented");
	@Test
	public void test() {
		
		board MyBoard;
		
		MyBoard = new board();
		if((MyBoard.get_num_out() != 0) || (MyBoard.get_adventurer().getrem() != 10) || (MyBoard.get_ambassador().getrem() != 10) || (MyBoard.get_village().getrem() != 10) || (MyBoard.get_tribute().getrem() != 10) || (MyBoard.get_smithy().getrem() != 10)){
			System.out.println("Failed Board default constructor test");
		}
		else{
			System.out.println("Passed Board default constructor test");
		}
		
		MyBoard.set_num_out(2);
		if(MyBoard.get_num_out() != 2){
			System.out.println("Failed set_num_out");
		}
		else{
			System.out.println("Passed Board set_num_out test");
		}
		
		if(MyBoard.getTrash().size() != 0){
			System.out.println("Failed Trash Test");
		}
		else{
			System.out.println("Passed Trash Test");
		}
		
	}

}
