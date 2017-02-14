/*
  Author: Levi Willmeth
  Written for OSU CS362 - Software Engineering II
  Assignment 1 - Dominion
*/
package dominion;

public enum Card{
  // The Card class represnts a single Dominion card
  COPPER("Copper", 0, 1, 0, "worth 1 money"),
  SILVER("Silver", 3, 2, 0, "worth 2 money"),
  GOLD("Gold", 6, 3, 0, "worth 3 money"),
  ESTATE("Estate", 2, 0, 1, "worth 1 victory point"),
  DUCHY("Duchy", 5, 0, 3, "worth 3 victory points"),
  PROVINCE("Province", 8, 0, 6, "worth 6 victory points"),
  CURSE("Curse", 0, 0, -1, "worth -1 victory points"),
  ADVENTURER("Adventurer", 6, "Reveal cards from your deck until you find "+
  "2 Treasure cards. Put them into your hand and discard the other revealed "+
  "cards."){
    // See http://wiki.dominionstrategy.com/index.php/File:Adventurer.jpg
    public Card play(Player p){
      int needTreasures = 2;
      while(needTreasures > 0){
        Card c = p.draw();
        if(c.givesMoney > 0){
          p.putInHand(c);
          needTreasures--;
        } else {
          p.discard(c);
        }
      }
      return this;
    }
  },AMBASSADOR("Ambassador", 3, "Reveal a card from your hand.  Return up to "+
  "2 copies of it from your hand to the supply.  Each other player gains a "+
  "copy."){
    // See http://wiki.dominionstrategy.com/index.php/File:Ambassador.jpg
    public Card play(Player p){
      Card c = p.chooseHand();
      if(c != null){  // maybe they cancelled
        GameState g = p.gameState;
        g.addCard(c);
        for(int i=0; i<g.numPlayers; i++){
          if(g.players[i] != p){
            g.players[i].takeFreeCard( g.takeCard(c) );
          }
        }
      }
      return this;
    }
  },BARON("Baron", 4, "+1 Buy.  Discard an Estate for +4 money.  Otherwise, "+
  "gain an Estate."){
    // See http://wiki.dominionstrategy.com/index.php/File:Baron.jpg
    public Card play(Player p){
      // +4 money if player discards an estate, otherwise, draw an Estate
      if(p.discardFromHand(Card.ESTATE)){
        p.addMoney(4);
      }else{
        p.discard( p.gameState.takeCard(Card.ESTATE) );
      }
      return this;
    }
  },COUNCILROOM("Council Room", 5, "+4 Cards, +1 Buy.  Each other player "+
  "draws a card."){
    // See http://wiki.dominionstrategy.com/index.php/File:Council_Room.jpg
    public Card play(Player p){
      // +4 cards, +1 buy, each other player draws a card
      for(int i=0; i<4; i++){
        Card c = p.draw();
        System.out.println("You drew a "+c);
        p.putInHand(c);
      }
      p.addBuys(1);
      for(Player other: p.gameState.players){
        if(other != p){
          other.putInHand(other.draw());
          System.out.println(other+" drew a card.");
        }
      }
      return this;
    }
  },CUTPURSE("Cutpurse", 4, "+2 money.  Each other player discards a Copper "+
  "card or reveals a hand with no Copper."){
    // See http://wiki.dominionstrategy.com/index.php/File:Cutpurse.jpg
    public Card play(Player p){
      // +2 money, other players forced to discard a copper or reveal hand
      p.addMoney(2);
      for(Player other: p.gameState.players){
        if(other != p){
          if(!other.discardFromHand(Card.COPPER)) other.seeHand();
        }
      }
      return this;
    }
  },EMBARGO("Embargo", 2, "+2 money.  Trash this card.  Put an Embargo token "+
  "on top of a supply pile.  When any player buys that card, he gains a Curse "+
  "card per Embargo token"){
    // See http://wiki.dominionstrategy.com/index.php/File:Embargo.jpg
    public Card play(Player p){
      // +2 money, trash this card, add embargo token to supply pile
      // When ANY player buys a card from that pile, they also gain a Curse
      // for EACH embargo token on that pile
      p.addMoney(2);
      GameState g = p.gameState;
      // add embargo token
      return null;  // trash this card
    }
  },FEAST("Feast", 4, "Trash this card.  Gain a card costing up to 5 money."){
    // See http://wiki.dominionstrategy.com/index.php/File:Feast.jpg
    public Card play(Player p){
      // Trash this card, gain a card costing up to 5 money
      System.out.println(p+" may choose a card up to 5 money from the supply.");
      int choice;
      do{
        int availCards = p.gameState.listCards();
        System.out.format("Please enter the card number (1-%d) you want, "+
          "or 0 to cancel: ", availCards);
        if(p.ISBOT) choice = p.rand.nextInt(20);
        else choice = p.scan.nextInt();
        if( choice>0 && choice<=availCards){
          Card c = Card.values()[choice-1];
          if(c.costsMoney <= 5){
            if(p.takeFreeCard(p.gameState.takeCard(c))) break;
          }else{
            System.out.format("The %s card costs %d, please choose a "+
            "card that costs no more than 5 money.\n", c, c.costsMoney);
          }
        }
      }while(choice != 0);
      return null;
    }
  },GARDENS("Gardens", 4, "Worth 1 Victory Point per 10 cards, rounded down."){
    // See http://wiki.dominionstrategy.com/index.php/File:Gardens.jpg
    public int getVictoryPoints(Player p){
      // Worth 1 vp per 10 cards you have, rounded down
      return p.countAllCards() / 10;
    }
  },GREAT_HALL("Great Hall", 3, "+1 Action, +1 Card.  Worth 1 Victory Point."){
    // See http://wiki.dominionstrategy.com/index.php/File:Great_Hall.jpg
    public Card play(Player p){
      // +1 card, +1 action, worth 1 vp
      Card c = p.draw();
      System.out.println("You drew a "+c);
      p.putInHand(c);
      p.addActions(1);
      return this;
    };
    public int getVictoryPoints(Player p){
      // worth 1 vp
      return 1;
    }
  },MINE("Mine", 5, "Trash a Treasure card from your hand.  Gain a Treasure "+
  "card to your hand costing up to 3 more."){
    // See http://wiki.dominionstrategy.com/index.php/File:Mine.jpg
    public Card play(Player p){
      // Trash a treasure card from your hand to gain the next better Treasure
      // Put this new card in your HAND
      System.out.println("Trash a treasure card from your hand to gain "+
        "a Treasure card worth up to 3 more than your original Treasure.");
      // not returning the chosen Treasure card, trashes it
      Card c = p.chooseTypeOfCard(Type.TREASURE);
      if(c != null){
        GameState supply = p.gameState;
        if(c == COPPER) p.putInHand(supply.takeCard(SILVER));
        if(c == SILVER) p.putInHand(supply.takeCard(GOLD));
        if(c == GOLD)   p.putInHand(supply.takeCard(GOLD));
      }
      return this; // return the Mine
    }
  },SALVAGER("Salvager", 4, "+1 buy.  Trash a card from your hand.  Gain "+
  "money equal to it's cost."){
    // See http://wiki.dominionstrategy.com/index.php/File:Salvager.jpg
    public Card play(Player p){
      // +1 buy, trash a card and gain money equal to it's cost
      p.addBuys(1);
      Card c = p.chooseHand();
      if(c != null) p.addMoney(c.costsMoney);
      return this;
    }
  },SMITHY("Smithy", 4, "+3 Cards."){
    // See http://wiki.dominionstrategy.com/index.php/File:Smithy.jpg
    public Card play(Player p){
      // +3 cards
      for(int i=0; i<3; i++){
        Card c = p.draw();
        System.out.println("You drew a "+c);
        p.putInHand(c);
      }
      return this;
    }
  },VILLAGE("Village", 3, "+1 Card, +2 Actions"){
    // See http://wiki.dominionstrategy.com/index.php/File:Village.jpg
    public Card play(Player p){
      // +1 card, +2 actions
      Card c = p.draw();
      System.out.println("You drew a "+c);
      p.putInHand(c);
      p.addActions(2);
      return this;
    }
  };


  private final boolean DEBUGGING = true;
  public final String cardName;
  public String desc = "No desc";
  public int costsAction = 0;
  public int costsMoney;
  public int givesActions = 0;
  public int givesCardDraws = 0;
  public int givesMoney = 0;
  public int givesVictoryPoints = 0;
  private Type type;

  private Card(String cName, int cost, String desc){
    this(cName, cost, 0, 0, desc);
  }
  private Card(String cName, int cost, int money, int vp, String desc){
    this.cardName = cName;
    this.costsMoney = cost;
    this.givesMoney = money;
    this.givesVictoryPoints = vp;
    this.desc = desc;
    if(money == 0 && vp == 0){
      this.type = Type.ACTION;
      this.costsAction = 1;
    }else if(vp == 0)
      this.type = Type.TREASURE;
    else
      this.type = Type.VICTORY;
  }

  @Override
  public String toString() {
    return this.cardName;
  }

  public Type getType(){
    return type;
  }

  public Card play(Player p){
    if(DEBUGGING) System.out.println(p+" played a "+cardName);
    if(givesMoney>0) p.addMoney(givesMoney);
    if(givesActions>0) p.addActions(givesActions);
    return this;
  }

  public int getVictoryPoints(Player p){
    return this.givesVictoryPoints;
  }

  public String getDesc(){
    return this.desc;
  }

  public static enum Type {
		ACTION, TREASURE, VICTORY;
	}
}
