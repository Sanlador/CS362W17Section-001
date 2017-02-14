package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class Game_test {

	@Test
	public void test() {
		game MyGame = new game();
		
		if(MyGame.is_over() == true){
			System.out.println("Failed Game is_over test");
		}
		else{
			System.out.println("Passed Game is_over test");
		}
	}

}
