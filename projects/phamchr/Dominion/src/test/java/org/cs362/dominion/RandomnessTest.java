package org.cs362.dominion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.*;


public class RandomnessTest {

	//   public  static void main(String args[]) throws CloneNotSupportedException{
	  @Test
	  public void test4()  throws Throwable  {	   
		    Randomness obj = new Randomness();
		    assertEquals(obj.main(), 0);
	   }
	
}
