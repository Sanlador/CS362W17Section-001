package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class PlayDominionTest {

	@Test
	public void test() {
		for(int j=0; j<10; j++){
			try{
				PlayDominion.main(null);
			}
			catch(Exception e){
				fail("Game crashed");
			}
		}
	}
}
