package com.timmy;


public class Card {
    private int rank, suit;
    /*
     * Build array that card object will pull rank and suit from.
     * Plurals of suit/rank denotes use of the array not the individual card.
     */

    private static String[] suits = {"Clubs","Diamonds", "Hearts", "Spades"};
    private String[] ranks = {"Ace", "2","3","4", "5", "6", "7", "8", "9","10","Jack","Queen","King"};
    /*
     * Constructor for card object
     */

    public Card(int suit, int rank){
        this.rank = rank;
        this.suit = suit;
    }
    /* Getters & Setters */
    public int getRank() {return rank;}
    public void setRank(int rank) {this.rank = rank;}
    public int getSuit() {return suit;}
    public void setSuit(int suit) {this.suit = suit;}

    @Override
    public String toString() {return ranks[rank] + " of " + suits[suit];}
}
