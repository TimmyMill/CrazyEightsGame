package com.timmy;

import java.util.ArrayList;
import java.util.Collections;

public class Hand {

    public static ArrayList<Card> hand;       /* ArrayList for each player's hand */
    public static Deck gameDeck = new Deck(); /* Create gameDeck object */
    static Card C;                            /* Create card Object */

    /*
     * Deal Hand to each player
     */

    public static ArrayList<Card> dealHand() {
        hand = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            C = gameDeck.drawCard();
            hand.add(C);
        }
        return hand;
    }

    public static Deck reshuffleDeck() {
        Collections.shuffle(Gameplay.discard);
        gameDeck.getCards().addAll(Gameplay.discard);
        return gameDeck;
    }
}
