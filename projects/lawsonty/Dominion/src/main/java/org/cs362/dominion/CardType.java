package org.cs362.dominion;

public enum CardType {
    CURSE(0), TREASURE(1), VICTORY(2), ACTION(3), ATTACK(4), DURATION(5);
    public final long cardValue;
    
    CardType(long cardValue){
        this.cardValue = cardValue;
    }
    
    public long getCardTypeValue() {
    	return cardValue;
    }
}
