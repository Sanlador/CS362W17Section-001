package charbonw.dominion;

public enum Card {
	// ID("Name", Cost, Value, Victory Points)
	COPPER("Copper", 0, 1, 0),
	SILVER("Silver", 3, 2, 0),
	GOLD("Gold", 6, 3, 0),
	CURSE("Curse", 0, 0, -1),
	ESTATE("Estate", 2, 0, 1),
	DUCHY("Duchy", 5, 0, 3),
	PROVINCE("Province", 8, 0, 6),
	ADVENTURER("Adventurer", 6, 0, 0) {
		// Reveal cards from your deck until you reveal 2 Treasure cards.
		// Put those treasure cards into your hand and discard the other revealed cards.
		public Card play(Player p) {
			int cnt = 0;
			while (cnt < 2) {
				Card c = p.draw();
				if (c == null)
					break;
				p.reveal(c);
				if (c.type != Type.TREASURE)
					p.discard(c);
				else
					cnt++;
			}
			return this;
		}
	},
	AMBASSADOR("Ambassador", 3, 0, 0) {
		// Reveal a card from your hand.
		// Return up to two copies of it from your hand to the Supply.
		// Then each other player gains a copy of it.
		public Card play(Player p) {
			Card c = p.chooseCard();
			if (c == null)										// if hand is empty
				return this;
			p.reveal(c);
			print("How many copies would you like to return? (0-2)\n");
			int choice = p.prompt(2);
			if (choice > 0) {
				GameState g = p.gameState;
				p.returnSupply(c);
			}
			if (choice > 1)
				p.returnSupply(c);
			for (Player p0 : g.players) {
				if (p0 == p)
					continue;
				p0.gain(c);
			}
			return this;
		}
	},
	BARON("Baron", 4, 0, 0) {
		// +1 Buy
		// You may discard an Estate card.
		// If you do, +$4.
		// Otherwise, gain an Estate card.
		public Card play(Player p) {
			Card c = ESTATE;
			if (!p.handContains(c))
				return this;
			print("Discard an Estate? (y/n)\n");
			if (p.prompt(1) != 0)
				p.addMoney(4);
			else
				p.gain(c);
			return this;
		}
	},
	COUNCILROOM("Council Room", 5, 0, 0) {
		// +4 Cards; +1 Buy
		// Each other player draws a card.
		public Card play(Player p) {
			for (int i = 0; i < 4; i++)
				p.draw();
			p.addBuys(1);
			GameState g = p.gamestate;
			for (Player p0 : g.players) {
				if (p0 == p)
					continue;
				p0.draw();
			}
			return this;
		}
	},
	CUTPURSE("Cutpurse", 4, 0, 0) {
		// +$2
		// Each other player discards a Copper card (or reveals a hand with no Copper).
		public Card play(Player p) {
			p.addMoney(2);
			GameState g = p.gamestate;
			Card c = COPPER;
			for (Player p0 : g.players) {
				if (p0 == p)
					continue;
				if (p0.handContains(c)) {
						p0.discard(c);
				} else {
					for (Card c0 : p0.hand)
						p0.reveal(c0);
				}
			}
			return this;
		}
	},
	EMBARGO("Embargo", 2, 0, 0) {
		// +$2
		// Trash this card.
		// Put an Embargo token on top of a Supply pile.
		// When a player buys a card, he gains a curse card per Embargo token on that pile.
		public Card play(Player p) {
			p.addMoney(2);
			p.trash(this);
			GameState g = p.gameState;
			print("Select a Supply pile to embargo (1-16)\n");
			int choice;
			do {
				choice = p.prompt(1);
			} while (choice < 1 || choice > 16);
			g.embargo(choice);
			return null;
		}
	},
	FEAST("Feast", 4, 0, 0) {
		// trash this card
		// gain a card costing up to $5
		public Card play(Player p) {
			p.trash(this);
			GameState g = p.gameState;
			print("Gain a card from the Supply costing less than $5 (1-16)\n");
			int choice;
			do {
				choice = p.prompt(5);
			} while (g.supply[choice].getCost() <= 5 && g.supply[choice].getAmount());
			p.gain(g.supply[choice]);
			return this;
		}
	},
	GARDENS("Gardens", 4, 0, 0) {
		// Worth 1 victory point for every 10 cards in your deck (rounded down).
		public int getvictory(Player p) {
			return p.getDeckSize() / 10;
		}
	},
	GREATHALL("Great Hall", 3, 0, 0) {
		// 1 Victory Point
		// +1 Card; +1 Action
		public Card play(Player p) {
			p.draw();
			p.addAction(1);
			return this;
		}
		public int getvictory(Player p) {
			return 1;
		}
	},
	MINE("Mine", 5, 0, 0) {
		// Trash a Treasure card from your hand.
		// Gain a Treasure card costing up to $3 more; put it into your hand.
		public Card play(Player p) {
			int cardVal;
			for (int i = 0; i < p.getHandSize(); i++) {
				Card c = p.getHand(i);
				if (c.type == Type.TREASURE) {
					print("Trash " + c.name + "? (y/n)\n");
					if (p.prompt(1) != 0) {
						cardVal = c.cost;
						p.trash(c);
						break;
					}
				}
			}
			print("Choose a Treasure card costing up to $" + cardVal + 3 + "(4-6)\n");
			GameState g = p.gameState;
			int choice, botChoice;
			if (cardVal == 3)									// bot will upgrade if possible
				botChoice = 6;
			else
					botChoice = cardVal + 4;
			do {
				choice = p.prompt(botChoice);
			} while (g.supply[choice].getCost() > cardVal + 3 || g.supply[choice].getAmount() == 0);
			p.gain(g.supply[choice]);
			p.draw();
			return this;
		}
	},
	TREASUREMAP("Treasure Map", 4, 0, 0) {
		// Trash this and another copy of Treasure Map from your hand.
		// If you do, trash two Treasure Maps, gain 4 Gold cards, putting them on top of your deck.
		public Card play(Player p) {
			boolean copy = false;
			Card c;
			for (int i = 0; i < p.getHandSize(); i++) {
				c = p.getHand(i);
				if (c == this) {
					p.trash(c);
					copy = true;
					break;
				}
			}
			p.trash(this);
			if (copy) {
				GameState g = p.gameState;
				p.gain(g.supply[4]);
			}
			return null;
		}
	},
	SMITHY("Smithy", 4, 0, 0) {
		// +3 Cards
		public Card play(Player p) {
			p.draw();
			p.draw();
			p.draw();
			return this;
		}
	},
	VILLAGE("Village", 3, 0, 0) {
		// +1 Card; +2 Actions
		public Card play(Player p) {
			p.draw();
			p.addActions(3);
			return this;
		}
	};
	
	public final String name;
	public final int cost;
	public final int value;
	private final int victory;
	public final Type type;

	private Card(String name, int cost, int value, int victory) {
		this.name = name;
		this.cost = cost;
		this.value = value;
		this.victory = victory;
		if (value > 0)
			this.type = Type.TREASURE;
		else if (victory > 0)
			this.type = Type.VICTORY;
		else
			this.type = Type.ACTION;
	}
	
	public Card play(Player p) {
		print(p + " plays " + this.name);
		p.addMoney(this.value);
		p.discard(this);
		return this;
	}
	
	public int getVictory(Player p) {
		return this.victory;
	}
	
	public String toString() {
		return this.name;
	}
	
	public static enum Type {
		TREASURE, ACTION, VICTORY;
	}

}