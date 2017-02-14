package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class Test_all {

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
		
		board b2 = new board();
		MyPlayer.buy(b2);
		MyPlayer.buy(b2);
		MyPlayer.buy(b2);
		MyPlayer.buy(b2);
		MyPlayer.buy(b2);
		if(MyPlayer.getDiscard().size() + MyPlayer.getHand().size() + MyPlayer.getDeck().size() <= 10){
			System.out.println("Failed player buy test");
		}
		else{
			System.out.println("Passed player buy test");
		}
		
		
		
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
		
		
		
		game MyGame = new game();
		
		if(MyGame.is_over() == true){
			System.out.println("Failed Game is_over test");
		}
		else{
			System.out.println("Passed Game is_over test");
		}
		
		
		
		
		player p1 = new player("p1");
		player p2 = new player("p2");
		board b1 = new board();
		
		//adventurer
		p1.getHand().addElement(b1.get_adventurer().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_adventurer().getdc()); //add to action cards
		//p1.print_hand();
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_adventurer().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_adventurer().getdc()); //remove from ac after
		if(p1.getHand().size() != 7){
			System.out.println("Failed adventurer test");
			System.out.println("hand size(7): " + p1.getHand().size());
			//p1.print_hand();
		}
		else{
			System.out.println("Passed adventurer test");
		}
		
		//ambassador
		p1.getHand().addElement(b1.get_ambassador().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_ambassador().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_ambassador().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_ambassador().getdc()); //remove from ac after
		if(p2.getDiscard().size() != 1){
			System.out.println("Failed ambassador test");
			System.out.println("p2 discard size(1): " + p2.getDiscard().size());
			if(p2.getDiscard().size() == 2){
				System.out.println("Ambassador generates an extra card");
			}
		}
		else{
			System.out.println("Passed ambassador test");
		}
		
		//baron
		p1.getHand().addElement(b1.get_baron().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_baron().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_baron().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_baron().getdc()); //remove from ac after
		if(p1.getBuys() != 2){
			System.out.println("Failed baron test");
			System.out.println("Buys(2): " + p1.getBuys());
			if(p1.getBuys() > 2){
				System.out.println("baron gives too many buys");
			}
		}
		else{
			System.out.println("Passed baron test");
		}
		p1.setBuys(1);//reset buys
		
		//council_room
		p1.getHand().addElement(b1.get_council_room().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_council_room().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_council_room().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_council_room().getdc()); //remove from ac after
		if(p1.getBuys() != 2){
			System.out.println("Failed council_room test");
			System.out.println("Buys(2): " + p1.getBuys());
			if(p1.getBuys() > 2){
				System.out.println("council_room gives too many buys");
			}
		}
		else{
			System.out.println("Passed council_room test");
		}
		p1.setBuys(1);//reset buys
		
		//cutpurse
		p1.getHand().addElement(b1.get_cutpurse().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_cutpurse().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_cutpurse().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_cutpurse().getdc()); //remove from ac after
		if(p1.getMoney() != 2){
			System.out.println("Failed cutpurse test");
			System.out.println("Money(2): " + p1.getMoney());
			if(p1.getMoney() > 2){
				System.out.println("cutpurse gives too much money");
			}
			else{
				System.out.println("cutpurse gives too little");
			}
		}
		else{
			System.out.println("Passed cutpurse test");
		}
		p1.getDiscard().clear();//clear discard p1
		p2.getDiscard().clear();//clear discard p2
		
		//feast
		p1.getHand().addElement(b1.get_feast().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_feast().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_feast().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_feast().getdc()); //remove from ac after
		if(p1.getDiscard().size() != 1){
			System.out.println("Failed feast test");
			System.out.println("Discard size(1): " + p1.getDiscard().size());
		}
		else{
			System.out.println("Passed feast test");
		}
		p1.setBuys(1);//reset buys
		p1.setActions(1);//reset actions
		p1.setMoney(0);//reset money
		p1.getDiscard().clear();//clear discard p1
		p2.getDiscard().clear();//clear discard p2
		
		//great_hall
		p1 = new player("P1");//reset p[layer1
		p2 = new player("P2");//reset p[layer2
		p1.getHand().addElement(b1.get_great_hall().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_great_hall().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_great_hall().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_great_hall().getdc()); //remove from ac after
		if(p1.getActions() != 2){
			System.out.println("Failed great_hall test");
			System.out.println("actions(2): " + p1.getActions());
			if(p1.getActions() > 2){
				System.out.println("great_hall gives too many actions");
			}
		}
		else{
			System.out.println("Passed great_hall test");
		}
		p1.setBuys(1);//reset buys
		p1.setActions(1);//reset actions
		p1.setMoney(0);//reset money
		p1.getDiscard().clear();//clear discard p1
		p2.getDiscard().clear();//clear discard p2
		b1.getTrash().clear();//reset trash
		
		//mine
		p1.getHand().addElement(b1.get_mine().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_mine().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_mine().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_mine().getdc()); //remove from ac after
		if(b1.getTrash().size() != 1){
			System.out.println("Failed mine test");
			System.out.println("Trash size(1): " + b1.getTrash().size());
		}
		else{
			System.out.println("Passed mine test");
		}
		p1.setBuys(1);//reset buys
		p1.setActions(1);//reset actions
		p1.setMoney(0);//reset money
		p1.getDiscard().clear();//clear discard p1
		p2.getDiscard().clear();//clear discard p2
		b1.getTrash().clear();//reset trash
		
		//minion
		p1 = new player("P1");//reset p[layer1
		p2 = new player("P2");//reset p[layer2
		p1.getHand().addElement(b1.get_minion().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_minion().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_minion().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_minion().getdc()); //remove from ac after
		if(p1.getActions() != 2){
			System.out.println("Failed minion test");
			System.out.println("Actions(2): " + p1.getActions());
		}
		else{
			System.out.println("Passed minion test");
		}
		p1.setBuys(1);//reset buys
		p1.setActions(1);//reset actions
		p1.setMoney(0);//reset money
		p1.getDiscard().clear();//clear discard p1
		p2.getDiscard().clear();//clear discard p2
		b1.getTrash().clear();//reset trash
		
		//smithy
		p1 = new player("P1");//reset p[layer1
		p2 = new player("P2");//reset p[layer2
		p1.getHand().addElement(b1.get_smithy().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_smithy().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_smithy().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_smithy().getdc()); //remove from ac after
		if(p1.getHand().size() != 8){
			System.out.println("Failed smithy test");
			System.out.println("hand size(8): " + p1.getHand().size());
		}
		else{
			System.out.println("Passed smithy test");
		}
		b1.getTrash().clear();//reset trash
		
		//steward
		p1 = new player("P1");//reset player1
		p2 = new player("P2");//reset player2
		p1.getHand().addElement(b1.get_steward().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_steward().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_steward().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_steward().getdc()); //remove from ac after
		if((p1.getHand().size() == 7) || (p1.getMoney() == 2) || (p1.getHand().size() == 3)){
			System.out.println("Passed steward test");
		}
		else{
			System.out.println("Failed steward test");
		}
		b1.getTrash().clear();//reset trash
		
		//tribute
		p1 = new player("P1");//reset player1
		p2 = new player("P2");//reset player2
		p1.getHand().addElement(b1.get_tribute().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_tribute().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_tribute().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_tribute().getdc()); //remove from ac after
		if(p2.getDiscard().size() != 2){
			System.out.println("Failed tribute test");
			System.out.println("P2 discard size(2): " + p2.getDiscard().size());
		}
		else{
			System.out.println("Passed tribute test");
		}
		b1.getTrash().clear();//reset trash
		
		//village
		p1 = new player("P1");//reset player1
		p2 = new player("P2");//reset player2
		p1.getHand().addElement(b1.get_village().getdc());//add to hand
		p1.getActioncards().addElement((action_card)b1.get_village().getdc()); //add to action cards
		((action_card)(p1.getHand().lastElement())).action(p1, p2, b1);
		p1.getHand().remove(b1.get_village().getdc()); //remove from hand after
		p1.getActioncards().remove((action_card)b1.get_village().getdc()); //remove from ac after
		if(p1.getActions() != 3){
			System.out.println("Failed village test");
			System.out.println("Actions(3): " + p1.getActions());
		}
		else{
			System.out.println("Passed village test");
		}
		b1.getTrash().clear();//reset trash
		
		
		
	}

}
