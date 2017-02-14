package org.cs362.dominion;




public class Card {
	
	public enum CardType {
		action, treasure, victory, actionvictory;
	}
	/*
	public enum CardName {
		//kingdom cards:
		Adventurer, Ambassador, Baron, CouncilRoom, Cutpurse, Embargo, Feast, Gardens, GreatHall, Mine,
		Smithy, Salvager, SeaHag,
		
		//victory cards:
		province, duchy, estate, curse,
		
		//Treasure cards:
		gold, silver, copper;
	}*/
	private String cardName;
	
	private CardType type;
	private int cost;
	private int moneyVal;
	private int victoryPoints;
	private String description;
	
	public String getName() {
		return this.cardName;
	}
	
	public String getType() {
		return this.type.toString();
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public int getMoneyVal() {
		return this.moneyVal;
	}
	
	public int getVicPoint() {
		return this.victoryPoints;
	}
	
	public String getDesc() {
		return this.description;
	}
	
	
	public Card(String inpCrdNm) {
		switch(inpCrdNm) {
		case "Gold":
			this.cardName = inpCrdNm;
			this.type = CardType.treasure;
			this.cost = 6;
			this.moneyVal = 3;
			this.victoryPoints = 0;
			this.description = "Money, durr";
			break;
		case "Silver":
			this.cardName = inpCrdNm;
			this.type = CardType.treasure;
			this.cost = 3;
			this.moneyVal = 2;
			this.victoryPoints = 0;
			this.description = "Money, durr";
			break;
		case "Copper":
			this.cardName = inpCrdNm;
			this.type = CardType.treasure;
			this.cost = 0;
			this.moneyVal = 1;
			this.victoryPoints = 0;
			this.description = "Money, durr";
			break;
		case "Province":
			this.cardName = inpCrdNm;
			this.type = CardType.victory;
			this.cost = 8;
			this.moneyVal = 0;
			this.victoryPoints = 6;
			this.description = "To victory and beyond?";
			break;
		case "Duchy":
			this.cardName = inpCrdNm;
			this.type = CardType.victory;
			this.cost = 5;
			this.moneyVal = 0;
			this.victoryPoints = 3;
			this.description = "To victory and beyond?";
			break;
		case "Estate":
			this.cardName = inpCrdNm;
			this.type = CardType.victory;
			this.cost = 2;
			this.moneyVal = 0;
			this.victoryPoints = 1;
			this.description = "To victory and beyond?";
			break;
		case "Curse":
			this.cardName = inpCrdNm;
			this.type = CardType.victory;
			this.cost = 0;
			this.moneyVal = 0;
			this.victoryPoints = -1;
			this.description = "To victory and beyond?";
			break;
		case "Gardens":
			this.cardName = inpCrdNm;
			this.type = CardType.victory;
			this.cost = 4;
			this.moneyVal = 0;
			this.victoryPoints = 0;
			this.description = "Gain 1 VP per 10 cards you own, rounding down.";
			break;
		case "GreatHall":
			this.cardName = inpCrdNm;
			this.type = CardType.actionvictory;
			this.cost = 3;
			this.moneyVal = 0;
			this.victoryPoints = 1;
			this.description = "Draw 1 card, gain 1 Action";
			break;
		case "Adventurer":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 6;
			this.moneyVal = 0;
			this.victoryPoints = 0;
			this.description = "Reveal cards from your deck until you reveal 2 treasure cards.  Put those treasure cards into your hand and discard the other revealed cards.";
			break;
		case "Ambassador":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 3;
			this.moneyVal = 0;
			this.victoryPoints = 0;
			this.description = "Reveal a card from your hand.  Return up to 2 copies of it from your hand to the Supply.  Then each other player gains a copy of it.";
			break;
		case "Baron":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 4;
			this.moneyVal = 0;
			this.victoryPoints = 0;
			this.description = "+1 Buy, you may discard an estate for 4 money.  If you don't, gain an estate.";
			break;
		case "CouncilRoom":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 5;
			this.moneyVal = 0;
			this.victoryPoints = 0;
			this.description = "Draw 4 cards, +1 Buy, and each other player draws a card.";
			break;
		case "Cutpurse":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 4;
			this.moneyVal = 2;
			this.victoryPoints = 0;
			this.description = "Each other player discards a Copper, or reveals a hand with no copper.";
			break;
		case "Embargo":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 2;
			this.moneyVal = 2;
			this.victoryPoints = 0;
			this.description = "Trash this card.  Put an Embargo token on top of a supply pile.  When a player buys a card, they gain a Curse card per Embargo token on that pile.";
			break;
		case "Feast":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 4;
			this.moneyVal = 0;
			this.victoryPoints = 0;
			this.description = "Trash this card.  Gain a card costing up to 5 coin.";
			break;
		case "Mine":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 5;
			this.moneyVal = 0;
			this.victoryPoints = 0;
			this.description = "You may trash a Treasure from your hand.  Gain a Treasure to your hand costing up to 3 coin more than it.";
			break;
		case "Salvager":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 4;
			this.moneyVal = 0;
			this.victoryPoints = 0;
			this.description = "+1 Buy, trash a card from your hand.  Gain money equal to its cost.";
			break;
		case "SeaHag":
			this.cardName = inpCrdNm;
			this.type = CardType.action;
			this.cost = 4;
			this.moneyVal = 0;
			this.victoryPoints = 0;
			this.description = "Each other player discard the top card of their deck, and gains a curse card to the top of their deck.";
			break;
		}
		
		

	}
	
}
