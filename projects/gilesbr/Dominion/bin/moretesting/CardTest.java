package moretesting;

import static org.junit.Assert.*;

import org.junit.Test;

public class CardTest extends player {

	@Test
	public void test() {
		card card = new card();
		assertTrue(1 == 1);
		assertTrue(card.num == -1);
		
		card.num = 1;
		card.category = "copper";
		card.value = 1;
		card.cost = 1;
		card.cardadd = 1;
		card.actionadd = 1;
		card.buyadd = 1;
		card.coinadd = 1;
		card.description = "+1 coin";
		
		//cards work
		card[] copperpile = new card[1];
		copperpile[1] = card;
		assertTrue(copperpile[1].num == 1);
		assertTrue(copperpile[1].category == "copper");
		assertTrue(copperpile[1].value == 1);
		assertTrue(copperpile[1].cost == 1);
		assertTrue(copperpile[1].cardadd == 1);
		assertTrue(copperpile[1].actionadd == 1);
		assertTrue(copperpile[1].buyadd == 1);
		assertTrue(copperpile[1].coinadd == 1);
		assertTrue(copperpile[1].description == "+1 coin");
		
		startRound();
		findDeckSizes();
		assertTrue(handsize == 5);
		assertTrue(decksize == 5);
		assertTrue(discardsize == 0);
		assertTrue(coppersize == 46);
		assertTrue(silversize == 40);
		assertTrue(goldsize == 30);
		assertTrue(platinumsize == 20);
		assertTrue(estatesize == 18);
		assertTrue(duchysize == 12);
		assertTrue(provincesize == 12);
		assertTrue(colonysize == 10);
		assertTrue(cellarsize == 10);
		assertTrue(marketsize == 10);
		assertTrue(militiasize == 10);
		assertTrue(minesize == 10);
		assertTrue(moatsize == 10);
		assertTrue(remodelsize == 10);
		assertTrue(smithysize == 10);
		assertTrue(villagesize == 10);
		assertTrue(woodcuttersize == 10);
		assertTrue(workshopsize == 10);
		
		int test = inpVal();
		assertTrue(test > 0);
		assertTrue(test < 100);
		
		card newCard = new card();
		newCard.num = -5;
		hand[20] = newCard;
		moveCard(hand, discard, 20, discardsize);
		assertTrue(discard[discardsize].num == -5);
		
		card actionCard = new card();
		actionCard.coinadd = 10;
		activateCard(actionCard);
		assertTrue(coinbonus == 10);
		
		coin = 100;
		buyCard(colonypile, 10, discardsize);
		assertTrue(discard[discardsize].num == 8); //colony ID is 8
		
		
		
		
	}

}
