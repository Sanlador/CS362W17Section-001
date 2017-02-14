//package moretesting;

import java.util.*;

public class main {
	


public static void main(String[] args){

	player obj = new player();
	obj.startRound();
	
	System.out.println("                   !!!## Welcome to Dominion ##!!!");
	System.out.println("  ");
	System.out.println("Keep playing until a pile runs out, whoever has most VP at end wins!");
	System.out.println("--------------------------------------------------------------------");
	System.out.println("     ");
	do{
	obj.playRound();
	obj.compRound();
	}while(obj.coppersize != 0&&obj.silversize != 0&& obj.goldsize !=0&& obj.platinumsize !=0&& obj.estatesize !=0&& obj.duchysize != 0&& obj.provincesize != 0&& obj.colonysize != 0 && obj.cellarsize != 0 && obj.marketsize!= 0 && obj.militiasize != 0 && obj.minesize !=0 && obj.moatsize != 0 && obj.remodelsize != 0 && obj.smithysize != 0 && obj.villagesize != 0 && obj.woodcuttersize !=0 && obj.workshopsize !=0);
	
	System.out.println("           !GAME OVER! ");
	System.out.println("Computer Victory Points: " + obj.compscore);
	System.out.println("Player Victory Points " + obj.playerscore);
	
	if(obj.compscore > obj.playerscore){
		System.out.println("Computer Won! Better Luck next time");
	}
	else if (obj.compscore == obj.playerscore){
		System.out.println("Tie! Everyone is a winner!");
	}
	else{
		System.out.println("You won! Great Job");
	}
	System.out.println("Game Complete. Hope you had fun! ");
	System.out.println("                    Love, Brennan Giles");

  }

	
}
