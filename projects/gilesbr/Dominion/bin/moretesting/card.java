//package moretesting;

public class card extends player {
	
	int num = -1;
	String category;
	int value;
	int cost;
	String description;
	int cardadd;
	int actionadd;
	int coinadd;
	int buyadd;
	
	

	public card(){
		}
	
	public static void assignCard(int x, card cardname){
		//x tells us which card type it is assigned as
		//1 treasure copper
		//2 treasure silver
		//3 treasure gold
		//4 treasure platinum
		//5 VP Estate
		//6 VP Duchy
		//7 VP Province
		//8 VP Colony
		//9 Adventurer
		//10 Bureaucrat
		//11 Cellar
		//12 Chancellor
		//13 Chapel
		//14 Council Room
		//15 Feast
		//16 Festival
		//17 Gardens
		//18 Laboratory
		//19 Market
		//20 Smithy
		//21 village
		
		switch (x){
		case 1:
			cardname.num = 1;
			cardname.category = "copper";
			cardname.value = 1;
			cardname.cost = 0;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+1 coin";
			break;
		case 2:
			cardname.num = 2;
			cardname.category = "silver";
			cardname.value = 2;
			cardname.cost = 3;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+2 coin";
			break;
		case 3:
			cardname.num = 3;
			cardname.category = "gold";
			cardname.value = 3;
			cardname.cost = 6;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+3 coin";
			break;
		case 4:
			cardname.num = 4;
			cardname.category = "platinum";
			cardname.value = 5;
			cardname.cost = 9;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+5 coin";
			break;
		case 5:
			cardname.num = 5;
			cardname.category = "estate";
			cardname.value = 1;
			cardname.cost = 2;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+1 VP";
			break;
		case 6:
			cardname.num = 6;
			cardname.category = "duchy";
			cardname.value = 3;
			cardname.cost = 5;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+3 VP";
			break;
		case 7:
			cardname.num = 7;
			cardname.category = "province";
			cardname.value = 6;
			cardname.cost = 8;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+6 VP";
			break;
		case 8:
			cardname.num = 8;
			cardname.category = "colony";
			cardname.value = 10;
			cardname.cost = 11;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+10 VP";
			break;
		case 9:
			cardname.num = 9;
			cardname.category = "militia";
			cardname.value = 0;
			cardname.cost = 4;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 2;
			cardname.description = "+2 coin, each other player discards down to 3 cards in hand";
			break;
		case 10:
			cardname.num = 10;
			cardname.category = "mine";
			cardname.value = 0;
			cardname.cost = 5;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "trash a treasure card from your hand and upgrade it to the next tier";
			break;
		case 11:
			cardname.num = 11;
			cardname.category = "cellar";
			cardname.value = 0;
			cardname.cost = 2;
			cardname.actionadd = 1;
			cardname.cardadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+1 action, discard any number of cards, replace with new cards from deck";
			break;
		case 12:
			cardname.num = 12;
			cardname.category = "moat";
			cardname.value = 0;
			cardname.cost = 2;
			cardname.coinadd = 0;
			cardname.cardadd = 2;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.description = "+2 cards, Reaction: if attacked by another player, block it";
			break;
		case 13:
			cardname.num = 13;
			cardname.category = "remodel";
			cardname.value = 0;
			cardname.cost = 4;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "trash a card from your hand and gain a card costing up to 2 more";
			break;
		case 14:
			cardname.num = 14;
			cardname.category = "woodcutter";
			cardname.value = 0;
			cardname.cost = 3;
			cardname.cardadd = 0;
			cardname.buyadd = 1;
			cardname.actionadd = 0;
			cardname.coinadd = 2;
			cardname.description = "+1 buy, +2 coin";
			break;
		case 15:
			cardname.num = 15;
			cardname.category = "market";
			cardname.value = 0;
			cardname.cost = 5;
			cardname.cardadd = 1;
			cardname.actionadd = 1;
			cardname.buyadd = 1;
			cardname.coinadd = 1;
			cardname.description = "+1 card, +1 action, +1 buy, +1 coin";
			break;
		case 16:
			cardname.num = 16;
			cardname.category = "smithy";
			cardname.value = 0;
			cardname.cost = 4;
			cardname.cardadd = 3;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+3 cards";
			break;
		case 17:
			cardname.num = 17;
			cardname.category = "village";
			cardname.value = 0;
			cardname.cost = 3;
			cardname.cardadd = 1;
			cardname.actionadd = 2;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "+1 card, +2 action";
			break;
		case 18:
			cardname.num = 18;
			cardname.category = "workshop";
			cardname.value = 0;
			cardname.cost = 3;
			cardname.cardadd = 0;
			cardname.actionadd = 0;
			cardname.buyadd = 0;
			cardname.coinadd = 0;
			cardname.description = "gain a card costing up to 4 coin";
			break;
		default:
			System.out.println("card assignment error");
			break;
		}
	}
}
