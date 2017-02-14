//package org.cs362.dominion;
import java.util.*;
import java.util.Random;

public class PlayGame {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int turn = 0, decision = 0, cardnum, check, loopCtrl = 0, randomInt;
		long seed = System.nanoTime();
		Random rand = new Random();
		Card temp;
		System.out.print("Welcome to Dominion!\nThis will be a 2-player game.");

		DominionBoard board = new DominionBoard();
		board.initialize();
		Player player = new Player("PLAYER1", 1, 1, 0);
		Player player2 = new Player("PLAYER2", 1, 1, 0);
		player.buildDeck();
		player2.buildDeck();

		for (int i = 0; i < 5; i++) {
			player.drawCard();
			player2.drawCard();/*
			if (player.deck.isEmpty()) {
				Collections.shuffle(player.discard, new Random(seed));
				for (Card card : player.discard) {
					player.deck.add(card);
				}
				player.discard.clear();
			}
			if (player2.deck.isEmpty()) {
				Collections.shuffle(player2.discard, new Random(seed));
				for (Card card : player2.discard) {
					player2.deck.add(card);
				}
				player2.discard.clear();
			}*/
		}
		while (true) {
			while (turn == 0) {
				System.out.print("\n" + player.player_name + "! It is Your TURN:");
				player.printHand();
				player.printInfo();
				while (player.getActions() > 0) {
					List<Card> actCard = Card.filter(player.hand, Card.Type.ACTION);
					if (actCard.size() == 0)
						break;
					Card c = (Card) actCard.get(0);
					if (c == null)
						break;
					player.activateCard(c, board, player2);
					player.setActions(-1);
				}
				player.setCoins(player.checkTreasure());
				player.printHand();
				System.out.print("\nYou have " + player.getCoins() +" coins to use!");
				while (player.getBuys() > 0) {/*
					if(player.getCoins() >= 8){
						board.buyingCard(1);
						temp = board.buyCards.get(0);
						System.out.println("\nYou just bought " + temp.getCardName()+ "!");
						player.discard.add(temp);
						player.setCoins((-1)*temp.getCost());
						player.setBuys(-1);
					}*/
					randomInt = rand.nextInt(19);
					if (board.buyingCard(randomInt+1) == 0)
						continue;
					if(board.buyCards.get(randomInt).getCost() > player.getCoins())
						continue;
					temp = board.buyCards.get(randomInt);
					System.out.print("\nYou just bought " + temp.getCardName()+"!");
					player.discard.add(temp);
					player.setCoins((-1) * temp.getCost());
					player.setBuys(-1);
				}
				System.out.print("\nYour turn is over, going to new player's turn");
				player.removeHand();
				for(int i = 0; i < 5; i++){
					player.drawCard();
					if(player.deck.isEmpty()){
						Collections.shuffle(player.discard, new Random(seed));
						for (Card card:player.discard){
							player.deck.add(card);
						}
						player.discard.clear();
					}
				}
				player.resetStats();
				turn = 1;
				}
			if (board.num_Province == 0 || board.checkEnd() == 1) {
				int victory1, victory2, numGarden = 0, numGarden2 = 0, deckSize = player.deck.size(), deckSize2 = player2.deck.size(), gVic;
				player.cleanUp();
				player2.cleanUp();
				System.out.print("\nThe game is over, checking players amount of Victory Cards!");
				victory1 = player.checkVictory();
				victory2 = player2.checkVictory();
				for (Card cards : player.deck) {
					if (cards.getCardName() == Card.CardName.Garden)
						numGarden++;
				}
				gVic = (deckSize / 10) * numGarden;
				victory1 = victory1 + gVic;
				for (Card cards : player2.deck) {
					if (cards.getCardName() == Card.CardName.Garden)
						numGarden2++;
				}
				gVic = (deckSize2 / 10) * numGarden2;
				victory2 = victory2 + gVic;
				System.out.print("\n" + player.player_name + " Victory Score: " + victory1 + "\n" + player2.player_name + " Victory Score: " + victory2);
				if (victory1 > victory2)
					System.out.print("\n" + player.player_name + " WINS!");
				else if (victory1 < victory2)
					System.out.print("\n" + player2.player_name + " WINS!");
				else
					System.out.print("\nThe game ends in a DRAW");

				break;
			}
			while(turn == 1) {
				System.out.print("\n" + player2.player_name + "! It is Your TURN:");
				player2.printHand();
				player2.printInfo();
				while (player2.getActions() > 0) {
					List<Card> actCard = Card.filter(player2.hand, Card.Type.ACTION);
					if (actCard.size() == 0)
						break;
					Card c = (Card) actCard.get(0);
					if (c == null)
						break;
					player2.activateCard(c, board, player2);
					player2.setActions(-1);
				}
				player2.setCoins(player2.checkTreasure());
				player2.printHand();
				System.out.print("\nYou have " + player2.getCoins() + " coins to use!");
				while (player2.getBuys() > 0) {/*
					if(player2.getCoins() >= 8){
						board.buyingCard(1);
						temp = board.buyCards.get(0);
						System.out.println("\nYou just bought " + temp.getCardName()+ "!");
						player2.discard.add(temp);
						player2.setCoins((-1)*temp.getCost());
						player2.setBuys(-1);
					}*/
					randomInt = rand.nextInt(19);
					if (board.buyingCard(randomInt) == 0)
						continue;
					if (board.buyCards.get(randomInt).getCost() > player2.getCoins())
						continue;
					temp = board.buyCards.get(randomInt);
					System.out.print("\nYou just bought " + temp.getCardName() + "!");
					player2.discard.add(temp);
					player2.setCoins((-1) * temp.getCost());
					player2.setBuys(-1);
				}
				System.out.print("\nYour turn is over, going to new player's turn");
				player2.removeHand();
				for (int i = 0; i < 5; i++) {
					player2.drawCard();
					if (player2.deck.isEmpty()) {
						Collections.shuffle(player2.discard, new Random(seed));
						for (Card card : player2.discard) {
							player2.deck.add(card);
						}
						player2.discard.clear();
					}
				}
				player2.resetStats();
				turn = 0;
			}
			if (board.num_Province == 0 || board.checkEnd() == 1) {
				int victory1, victory2, numGarden = 0, numGarden2 = 0, deckSize = player.deck.size(), deckSize2 = player2.deck.size(), gVic;
				player.cleanUp();
				player2.cleanUp();
				System.out.print("\nThe game is over, checking players amount of Victory Cards!");
				victory1 = player.checkVictory();
				victory2 = player2.checkVictory();
				for (Card cards : player.deck) {
					if (cards.getCardName() == Card.CardName.Garden)
						numGarden++;
				}
				gVic = (deckSize / 10) * numGarden;
				victory1 = victory1 + gVic;
				for (Card cards : player2.deck) {
					if (cards.getCardName() == Card.CardName.Garden)
						numGarden2++;
				}
				gVic = (deckSize2 / 10) * numGarden2;
				victory2 = victory2 + gVic;
				System.out.print("\n" + player.player_name + " Victory Score: " + victory1 + "\n" + player2.player_name + " Victory Score: " + victory2);
				if (victory1 > victory2)
					System.out.print("\n" + player.player_name + " WINS!");
				else if (victory1 < victory2)
					System.out.print("\n" + player2.player_name + " WINS!");
				else
					System.out.print("\nThe game ends in a DRAW");

				break;
			}
			}
				/*
				System.out.print("\nWhat would you like to do:\n1. Action\n2. Buy\n3. End Turn\n");
				decision = input.nextInt();

				if(decision == 1 && player.getActions() != 0){
					while(loopCtrl == 0){
						System.out.print("\nWhich action card would you like to use (Enter 0 to end this menu):\n ");
						cardnum = input.nextInt();
				//If players does not want to play action card
						if(cardnum == 0){
							player.setActions(((-1)*player.getActions()));
							break;
						}
						System.out.print("You chose card number: " + cardnum);
						check = player.checkTypeAction(cardnum);
						if(check == 1){
					//Make Action happen
							player.activateCard(cardnum, board, player2.hand, player2.discard);
							loopCtrl = 1;
							player.setActions(-1);
						}
						else
							System.out.print("\nThe card you picked was not an ACTION card!");
					}
					loopCtrl = 0;
				}
				else if(decision == 2 && player.getBuys() != 0){
				//Check to see if there are any treasure card in hand and how many
					player.setCoins(player.checkTreasure());
					while(player.getBuys() != 0){
						System.out.print("\nYou have " + player.getCoins() +" coins to use!\nHere is the list of cards:");
						board.printBoard();
						System.out.print("\nWhat do you want to buy (Enter 0 to end this menu): ");
						cardnum = input.nextInt();
							if(cardnum == 0){
								player.setBuys(((-1)*player.getBuys()));
								break;
							}
						temp = board.buyCards.get(cardnum-1);
						if(player.getCoins() < temp.getCost()){
							System.out.print("\nInsufficent funds!");
						}
						else if(board.buyingCard(cardnum) == 0){
							System.out.print("\nThere are no more of those cards left");
						}
						else{
							System.out.print("\nYou just bought " + temp.getCardName()+"!");
							player.discard.add(temp);
							player.setCoins(((-1)*temp.getCost()));
							player.setBuys(-1);
						}
					}
				}

			}//End of Player1 loop

				while (turn == 1) {
					System.out.print("\n" + player2.player_name + "! It is Your TURN:");
					player2.printHand();
					player2.printInfo();
					System.out.print("\nWhat would you like to do:\n1. Action\n2. Buy\n3. End Turn\n");
					decision = input.nextInt();
					if (decision == 1 && player2.getActions() != 0) {
						while (loopCtrl == 0) {
							System.out.print("\nWhich action card would you like to use (Enter 0 to end this menu):\n ");
							cardnum = input.nextInt();
							//If player2s does not want to play action card
							if (cardnum == 0) {
								player2.setActions(((-1) * player2.getActions()));
								break;
							}
							System.out.print("You chose card number: " + cardnum);
							check = player2.checkTypeAction(cardnum);
							if (check == 1) {
								//Make Action happen
								player2.activateCard(cardnum, board, player.hand, player.discard);
								loopCtrl = 1;
								player2.setActions(-1);
							} else
								System.out.print("\nThe card you picked was not an ACTION card!");
						}
						loopCtrl = 0;
					} else if (decision == 2 && player2.getBuys() != 0) {
						//Check to see if there are any treasure card in hand and how many
						player2.setCoins(player2.checkTreasure());
						while (player2.getBuys() != 0) {
							System.out.print("\nYou have " + player2.getCoins() + " coins to use!\nHere is the list of cards:");
							board.printBoard();
							System.out.print("\nWhat do you want to buy (Enter 0 to end this menu): ");
							cardnum = input.nextInt();
							if (cardnum == 0) {
								player2.setBuys(((-1) * player2.getBuys()));
								break;
							}
							temp = board.buyCards.get(cardnum - 1);
							if (player2.getCoins() < temp.getCost()) {
								System.out.print("\nInsufficent funds!");
							} else if (board.buyingCard(cardnum) == 0) {
								System.out.print("\nThere are no more of those cards left");
							} else {
								System.out.print("\nYou just bought " + temp.getCardName() + "!");
								player2.discard.add(temp);
								player2.setCoins(((-1) * temp.getCost()));
								player2.setBuys(-1);
							}
						}
					} else if (decision == 3 || (player2.getActions() + player2.getBuys()) == 0) {
						System.out.print("\nYour turn is over, going to new player's turn");
						player2.removeHand();
						for (int i = 0; i < 5; i++) {
							player2.drawCard();
							if (player2.deck.isEmpty()) {
								Collections.shuffle(player2.discard, new Random(seed));
								for (Card card : player2.discard) {
									player2.deck.add(card);
								}
								player2.discard.clear();
							}
						}
						player2.resetStats();
						turn = 0;
					}
				}//End of player2 loop
				if (board.num_Province == 0 || board.checkEnd() == 1) {
					int victory1, victory2, numGarden = 0, numGarden2 = 0, deckSize = player.deck.size(), deckSize2 = player2.deck.size(), gVic;
					player.cleanUp();
					player2.cleanUp();
					System.out.print("\nThe game is over, checking players amount of Victory Cards!");
					victory1 = player.checkVictory();
					victory2 = player2.checkVictory();
					for (Card cards : player.deck) {
						if (cards.getCardName() == Card.CardName.Garden)
							numGarden++;
					}
					gVic = (deckSize / 10) * numGarden;
					victory1 = victory1 + gVic;
					for (Card cards : player2.deck) {
						if (cards.getCardName() == Card.CardName.Garden)
							numGarden2++;
					}
					gVic = (deckSize2 / 10) * numGarden2;
					victory2 = victory2 + gVic;

					System.out.print("\n" + player.player_name + " Victory Score: " + victory1 + "\n" + player2.player_name + " Victory Score: " + victory2);
					if (victory1 > victory2)
						System.out.print("\n" + player.player_name + " WINS!");
					else if (victory1 < victory2)
						System.out.print("\n" + player2.player_name + " WINS!");
					else
						System.out.print("\nThe game ends in a DRAW");
					input.close();
					System.exit(0);
				}*/
			}//End of entire while loop
		}
