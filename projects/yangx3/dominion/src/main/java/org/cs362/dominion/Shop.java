package org.cs362.dominion;
import org.cs362.dominion.Card.Type;
import java.util.*;

//It seems containsKey has the same functionality with get. But if I delete one of them, it causes problem.
//containsKey check if this map contains a mapping for the specified key.
//get returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
public class Shop {
    public static void buying(GameState state, Player player){
        Random ran = new Random();
        while(player.numBuys>0){
            //System.out.println("CaO");
        if(player.coins==0 || player.coins==1){
            player.gain(Card.getCard(state.cards, Card.CardName.Copper));
            state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Copper), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Copper)) - 1 );
            //System.out.println("CaO 0");
        }
        else if(player.coins==2){
            int rand = ran.nextInt(2);
            if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Embargo)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Embargo)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Embargo));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Embargo), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Embargo)) - 1);
                player.coins-=2;
            } else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Estate)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Estate)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Estate));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Estate), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Estate)) - 1);
                player.coins -=2;
            }
        }
        else if (player.coins == 3) {
            int rand = ran.nextInt(4);
            if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Village)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Village)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Village));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Village), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Village)) - 1);
                player.coins -=3;
            } else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Ambassador)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Ambassador)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Ambassador));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Ambassador), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Ambassador)) - 1);
                player.coins -=3;
            } else if (rand == 2 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Great_Hall)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Great_Hall)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Great_Hall));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Great_Hall), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Great_Hall)) - 1);
                player.coins -= 3;
            } else if (rand == 3 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Silver)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Silver)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Silver));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Silver), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Silver)) - 1);
                player.coins-= 3;
            }
        }
        else if (player.coins == 4) {
            int rand = ran.nextInt(5);
            if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Smithy)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Smithy)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Smithy));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Smithy), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Smithy)) - 1);
                player.coins-=4;
            } else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Baron)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Baron)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Baron));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Baron), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Baron)) - 1);
                player.coins-= 4;
            } else if (rand == 2 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Cutpurse)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Cutpurse)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Cutpurse));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Cutpurse), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Cutpurse)) - 1);
                player.coins-=4;
            } else if (rand == 3 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Feast)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Feast)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Feast));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Feast), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Feast)) - 1);
                player.coins-= 4;
            } else if (rand == 4 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Gardens)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gardens)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Gardens));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Gardens), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gardens)) - 1);
                player.coins-=4;
            }
        }
        else if (player.coins == 5) {
            int rand = ran.nextInt(4);
            if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Mine)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Mine)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Mine));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Mine), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Mine)) - 1);
                player.coins-=5;
            } else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Council_Room)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Council_Room)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Council_Room));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Council_Room), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Council_Room)) - 1);
                player.coins-=5;
            } else if (rand == 2 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Duchy)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Duchy)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Duchy));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Duchy), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Duchy)) - 1);
                player.coins -= 5;
            } else if (rand == 3 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Minion)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Minion)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Minion));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Minion), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Minion)) - 1);
                player.coins -= 5;
            }
        }
        else if (player.coins == 6 || player.coins==7) {
            int rand = ran.nextInt(2);
            if (rand == 0 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Gold)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gold)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Gold));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Gold), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Gold)) - 1);
                player.coins -= 6;
            } else if (rand == 1 && state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Adventurer)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Adventurer)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Adventurer));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Adventurer), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Adventurer)) - 1);
                player.coins -= 6;
            }

        } else if (player.coins >= 8){
            if (state.gameBoard.containsKey(Card.getCard(state.cards, Card.CardName.Province)) && state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Province)) > 0) {
                player.gain(Card.getCard(state.cards, Card.CardName.Province));
                state.gameBoard.put(Card.getCard(state.cards, Card.CardName.Province), state.gameBoard.get(Card.getCard(state.cards, Card.CardName.Province)) - 1);
                player.coins -= 8;
            }
        }
        player.numBuys--;
    }
    }
}
