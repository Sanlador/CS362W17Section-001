package org.cs362.dominion;

import static org.junit.Assert.*;

import org.junit.Test;

public class Actions_test {

	@Test
	public void test() {
			
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
