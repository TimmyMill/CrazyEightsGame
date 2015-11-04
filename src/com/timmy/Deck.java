package com.timmy;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {

    private ArrayList<Card> cards; //initialize cards arrayList using the card class

    /*
     * Create a gameDeck of cards
     */

    public Deck() {
        cards = new ArrayList<>();

        /*
         * Fill the ArrayList with all of the cards to build a gameDeck
         */

        for (int a = 0; a <= 3; a++) {
            for (int b = 0; b <= 12; b++) {
                cards.add(new Card(a, b));
            }
        }
        Collections.shuffle(cards); // shuffle the gameDeck of cards
    }

    /*
     * Draw card by "removing" it from the filled cards array
     */

    public Card drawCard() {return cards.remove(0);}
    public int deckSize() {return cards.size();}
    public ArrayList<Card> getCards() {return cards;}
}