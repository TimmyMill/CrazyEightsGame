package com.timmy;

import java.util.ArrayList;

public class ComputerPlayer extends Player{

    public ComputerPlayer(String name, ArrayList<Card> hand) {
        this.name = name;
        this.hand = hand;
    }

    @Override
    public String toString() {return name + "'s hand: " + hand;}
}