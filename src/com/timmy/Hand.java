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
        for (int i = 0; i < 7; i++) {
            C = gameDeck.drawCard();
            hand.add(C);
        }
        return hand;
    }

    /* If the gameDeck is empty and a player needs to draw a card,
     * then we will reshuffle the discard pile and add it back to the gameDeck
     */

    public static Deck reshuffleDeck() {
        Gameplay.stockCard = Gameplay.discard.remove(Gameplay.discard.size()-1);
        /* References the last card played with the variable stockCard and then removes it from the discard pile */

        Collections.shuffle(Gameplay.discard);        /* Shuffle the discard pile */
        gameDeck.getCards().addAll(Gameplay.discard); /* Add everything from the discard pile to the gameDeck */
        Gameplay.discard.clear();                     /* Clear everything in the discard pile */
        Gameplay.discard.add(Gameplay.stockCard);     /* Add the stock card back to the discard pile */
        return gameDeck;
    }
}