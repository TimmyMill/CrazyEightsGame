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
//        int choice = playerMenu();
//        while (choice == 1) {
//            Card playerDraws = Hand.gameDeck.drawCard();
//            computer.getHand().add(playerDraws);
//            System.out.println(computer.getHand());
//            choice = playerMenu();
//        }
//        if (choice == 2) {
//            System.out.println("\nCurrent card:\n" + stockCard + "\n");
//            System.out.println("Select which card you'd like to play");
//            ArrayList<Card> cards = computer.getHand();
//            for (int i = 0; i < cards.size(); i++) {
//                System.out.println("(" + (i + 1) + ") " + cards.get(i));
//            }
////            String str = in.nextLine();
////            cardChoice = Integer.parseInt(str);
//            currentTurn = computer.getName();
//            playCard();
//            /* Exit if hand empty */
//            if (computer.getHand().isEmpty()) {
//                winner = computer.getName();
//                endGame();
//            }
//        }