package com.timmy;

import java.util.ArrayList;

public class Hand {
    public static ArrayList<Card> hand;   /* ArrayList for each player's hand */
    public static Deck deck = new Deck(); /* Create deck object */
    static Card C;                        /* Create card Object */

    /*
     * Deal Hand to each player
     */

    public static ArrayList<Card> dealHand() {
        hand = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            C = deck.drawCard();
            hand.add(C);
        }
        return hand;
    }
}
