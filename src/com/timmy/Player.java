package com.timmy;

import java.util.ArrayList;

/* Superclass of players */
public class Player {

    /* Attributes of a player */
    protected String name;
    protected ArrayList<Card> hand;

    /* Getters & Setters */
    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public ArrayList<Card> getHand() {return hand;}
    public void setHand(ArrayList<Card> hand) {this.hand = hand;}
}
