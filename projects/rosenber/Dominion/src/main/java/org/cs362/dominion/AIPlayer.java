package org.cs362.dominion;
import java.util.Random;

public class AIPlayer extends Player{

	Random rand;
	
	public AIPlayer(int num){
		super("Player_"+num, true);
		rand = new Random();
	}
	
	public void discardCard(){
		if(hand.size() < 1)
			return;
		boolean repeat;
		int choice = -1;
		do{
			repeat = false;
			try{
				choice = rand.nextInt(hand.size());
				Card d = hand.playCard(choice);
				System.out.println(this.getName() + " is discarding " + d.getName());
				discard.addTop(d);
			}
			catch(Exception e){
				repeat = true;
			}
		}while(repeat);
	}
	
	public Card playCard(){
		if(hand.size() < 1)
			return null;
		boolean repeat;
		int choice = -1;
		Card c = null;
		do{
			if(rand.nextBoolean()){ //choosing to skip
				System.out.println(this.getName()+ " is choosing to skip playing a card");
				return null;
			}
			repeat = false;
			choice = rand.nextInt(hand.size());
			c = hand.getCard(choice);
			if(!c.isCardType(CardType.Action))
				repeat = true;
		}while(repeat);
		Card d =hand.playCard(choice);
		System.out.println(this.getName()+ " is playing "+d.getName());
		return d;
	}
	
	public void buyCard(Board board){
		
		boolean repeat;
		int choice = -1;
		do{
			if(rand.nextBoolean()){ //chose not to buy
				System.out.println(this.getName()+ " is choosing to skip buying a card");
				return;
			}
			repeat = false;
			while(choice < 1){
				choice = rand.nextInt(board.numDecks());
			}
			try{
				Card c = board.lookAtDeck(choice);
				if(c.getCost() <= super.getMoney()){
					System.out.println(this.getName()+" is buying a " + c.getName());
					giveCard(board.draw(choice));
					super.addMoney(c.getCost() * -1);
					int numCurses = board.getDeck(choice).getEmbargos();
					for(int j=0; j<numCurses; j++)
						giveCard(board.draw(board.getDeckIdx("Curse")));
				}
				else
					repeat = true;
			}
			catch(Exception e){
				//deck empty
				repeat = true;
			}
		}while(repeat);
	}
}
