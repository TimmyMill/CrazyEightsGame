package com.timmy;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Gameplay {
    public static ArrayList<Player> playersList;     /* Initiate an ArrayList to store players */
    public static ArrayList<Card> discard;           /* ArrayList for discarded cards */
    public static int cardChoice;
    public static Card stockCard;
    public static Card compChoice;
    public static String winner;
    /*
     * Method to start the game
     */

    public static ArrayList<Player> startGame() {
        Scanner in = new Scanner(System.in);
        playersList = new ArrayList<>(); /* Create ArrayList of Players */
        discard = new ArrayList<>();     /* Create ArrayList to keep the cards that have been played */

        /*
         * We will first ask the user to enter a name. Then they will be give a hand containing 5 cards
         * which will be used to create their player. This will be done again for the computer player.
         */

        System.out.println("Please enter your name: ");
        String name = in.nextLine();
        ArrayList<Card> hand = Hand.dealHand();           /* Call dealHand method and assign it to the 1st player */
        HumanPlayer player = new HumanPlayer(name, hand); /* Create new player */
        playersList.add(player);                          /* Add player to list of players */

        ArrayList<Card> computerHand = Hand.dealHand();
        ComputerPlayer computer = new ComputerPlayer("Computer", computerHand);
        playersList.add(computer);

        return playersList;
    }

    public static int playerMenu() {
        /* Menu */
        System.out.println("\tMenu");
        System.out.println("Please select 1-3");
        System.out.println("(1) Draw card: ");
        System.out.println("(2) Play card: ");
        System.out.println("(3) Pass: \n");

        Scanner in = new Scanner(System.in);
        String choice = in.nextLine();
        while (! choice.matches("[1-3]")) {
            System.out.println("Invalid selection");
            choice = in.nextLine();
        }
        return Integer.parseInt(choice);
    }

    /*
     * Method for each players turn
     */

    public static void playerTurn() {
        Card startCard = Hand.gameDeck.drawCard();
        if (startCard.getRank() == 7) {
            int wild = wildCard();
            startCard.setSuit(wild);
        }
        discard.add(startCard);
        for (Player p : playersList) {
            while (! p.getHand().isEmpty()) {
                humanTurn();
                computerTurn();
            }
        }
    }
    /*
     * Human Turn Method
     */

    public static void humanTurn() {
        Scanner in = new Scanner(System.in);
        Player human = playersList.get(0);
        System.out.println(human.getName() + "'s Turn");
        System.out.println("Hand: " + human.getHand());
        stockCard = discard.get(discard.size() - 1); /* Grabs the last element of the discard pile */
        System.out.println("\nCurrent card:\n" + stockCard + "\n");

        System.out.println("Cards left in gameDeck: " + Hand.gameDeck.deckSize());

        int choice = playerMenu();
        while (choice == 1) {
            if (Hand.gameDeck.getCards().isEmpty()) {
                Hand.reshuffleDeck();
            }
            Card playerDraws = Hand.gameDeck.drawCard();
            human.getHand().add(playerDraws);
            System.out.println(human.getName() + "\nHand: " + human.getHand());

            System.out.println("Cards left in gameDeck: " + Hand.gameDeck.deckSize());

            System.out.println("\nCurrent card:\n" + stockCard + "\n");
            choice = playerMenu();
        }
        if (choice == 2) {
            System.out.println("\nCurrent card:\n" + stockCard + "\n");
            System.out.println("Select which card you'd like to play");
            ArrayList<Card> humanHand = human.getHand();
            for (int i = 0; i < humanHand.size(); i++) {
                System.out.println("(" + (i + 1) + ") " + humanHand.get(i));
            }
            String str = in.nextLine();
            cardChoice = Integer.parseInt(str);
            humanPlayCard();
            /* Exit if hand empty */
            if (human.getHand().isEmpty()) {
                winner = human.getName();
                endGame();
            }
        }
    }

    /*
     * Method to play a card
     */

    public static void humanPlayCard() {
        /*
         * Look through the first players hand
         * Find the element that corresponds to the user's choice
         */

        Player human = playersList.get(0);
        for (int i = 0; i < human.getHand().size(); i++) {
            if (cardChoice == (i + 1)) {
                Card playerCard = human.getHand().get(i); /* Reference to the card the player selected */
                int r = playerCard.getRank();             /* Reference to the rank of the card the player selected */
                int s = playerCard.getSuit();             /* Reference to the suit of the card the player selected */
                int r1 = stockCard.getRank();             /* Reference to the rank of the stock card*/
                int s1 = stockCard.getSuit();             /* Reference to the suit of the stock card*/

                if (r == 7) {
                    int wild = wildCard();          /* Use wildCard method */
                    Card eight = new Card(wild, r); /* Create a new card using the wild value to change the suit to
                                                     * player's selection and the rank of 8 */
                    discard.add(eight);             /* Add the newly created card to the discard pile */
                    human.getHand().remove(i);      /* Remove the card from the player's hand */
                    break;
                }
                if (r == r1 || s == s1) {
                    System.out.println("Playing the " + human.getHand().get(i) + "\n");
                    discard.add(human.getHand().get(i));
                    human.getHand().remove(i);
                    break;
                }
                else {
                    System.out.println("You cannot play this card\n");
                    humanTurn();
                }
            }
        }
    }

    /*
     * Computer Turn Method
     */

    public static void computerTurn() {
        Player computer = playersList.get(1);
        System.out.println(computer.getName() + "'s Turn");
        System.out.println("Cards left in gameDeck: " + Hand.gameDeck.deckSize());
        stockCard = discard.get(discard.size() - 1); /* Grabs the last element of the discard pile */
        System.out.println("\nCurrent card:\n" + stockCard + "\n");
        System.out.println(computer.getHand());

        boolean noMatches = true; /* Boolean value to use if there are no matches in the computer's hand */
        boolean hasWild = false;  /* Boolean value to use if there are no matches but there is a wildcard */
        int isWild = 0;           /* Int value to use if there is a wildcard */

        int suit = computerSuitCheck();

        /* Look through the computer's hand for any playable cards. If there is a playable card, the boolean value of
         * noMatches will be changed to false which will ignore the following if statement outside the for loop.
         */

        for (int i = 0; i < computer.getHand().size(); i++) {
            if (computer.getHand().get(i).getRank() == stockCard.getRank() || computer.getHand().get(i).getSuit() == stockCard.getSuit()) {
                compChoice = computer.getHand().get(i);
                computerPlayCard();
                noMatches = false;
                System.out.println("Cards left in gameDeck: " + Hand.gameDeck.deckSize());
                break;
            }

            /* If one of the cards is an '8', we will change the hasWild boolean value to true and set the isWild variable
             * equal to the current iteration so that we can use it later to access that element if there are no other matches.
             */

            if (computer.getHand().get(i).getRank() == 7) {
                hasWild = true;
                isWild = i;
            }
        }

        /* If there were no matches then we will first check to see if the computer has a wildcard.
         * If there is no wildcard we will draw a card and start the computer's turn over again
         */

        if (noMatches) {

            /* If there were no matches based on rank & suit, but there was a wildcard, then play the wildcard */
            if (hasWild) {
                compChoice = computer.getHand().get(isWild); // The isWild value is used here to access the last element that was an '8'
                computerPlayCard();
            }
            else {
                if (Hand.gameDeck.getCards().isEmpty()) { /* Check to see if the deck is empty before drawing a card */
                    Hand.reshuffleDeck();                 /* If the deck is empty, use the reshuffleDeck method */
                }
                Card c = Hand.gameDeck.drawCard();
                computer.getHand().add(c);
                System.out.println("Cards left in gameDeck: " + Hand.gameDeck.deckSize());
                computerTurn();
            }
        }
    }

    public static void computerPlayCard() {
        Random rand = new Random();
        Player computer = playersList.get(1);
        int r = compChoice.getRank();         /* Reference to the rank of the card the player selected */
        int s = compChoice.getSuit();         /* Reference to the suit of the card the player selected */
        int r1 = stockCard.getRank();         /* Reference to the rank of the stock card*/
        int s1 = stockCard.getSuit();         /* Reference to the suit of the stock card*/

        if (r == 7) {
            int wild = computerSuitCheck();            /* Use wildCard method */
            Card eight = new Card(wild, r);        /* Create a new card using the wild value to change the suit to
                                                    * player's selection and the rank of 8 */
            discard.add(eight);                    /* Add the newly created card to the discard pile */
            computer.getHand().remove(compChoice); /* Remove the card from the player's hand */
        }
        if (r == r1 || s == s1) {
            System.out.println("Playing the " + compChoice + "\n");
            discard.add(compChoice);
            computer.getHand().remove(compChoice);
        }
    }
    public static int computerSuitCheck() {
        Player computer = playersList.get(1);
        int clubs = 0;
        int diamonds = 0;
        int hearts = 0;
        int spades = 0;
        for (int i = 0; i < computer.getHand().size(); i++) {
            switch (computer.getHand().get(i).getSuit()) {
                case 0:
                    clubs++; break;
                case 1:
                    diamonds++; break;
                case 2:
                    hearts++; break;
                case 3:
                    spades++; break;
            }
        }
        int mostOf = Math.max(clubs, Math.max(diamonds, Math.max(hearts, spades)));
        if (mostOf == clubs) mostOf = 0;
        if (mostOf == diamonds) mostOf = 1;
        if (mostOf == hearts) mostOf = 2;
        if (mostOf == spades) mostOf = 3;
        return mostOf;
    }

//    public static int computerSuitCheck() {
//        Player computer = playersList.get(1);
//        int clubs = 0;
//        int diamonds = 0;
//        int hearts = 0;
//        int spades = 0;
//        for (int i = 0; i < computer.getHand().size(); i++) {
//            if (computer.getHand().get(i).getSuit() == 0) {
//                clubs++;
//            }
//            if (computer.getHand().get(i).getSuit() == 1) {
//                diamonds++;
//            }
//            if (computer.getHand().get(i).getSuit() == 2) {
//                hearts++;
//            }
//            if (computer.getHand().get(i).getSuit() == 3) {
//                spades++;
//            }
//        }
//        int mostOf = Math.max(clubs, Math.max(diamonds, Math.max(hearts, spades)));
//        if (mostOf == clubs) mostOf = 0;
//        if (mostOf == diamonds) mostOf = 1;
//        if (mostOf == hearts) mostOf = 2;
//        if (mostOf == spades) mostOf = 3;
//
//        return mostOf;
//    }

    public static int wildCard() {
        Scanner in = new Scanner(System.in);
        System.out.println("What suit would you like to change to?");
        System.out.println("(1) Clubs");
        System.out.println("(2) Diamonds");
        System.out.println("(3) Hearts");
        System.out.println("(4) Spades\n");
        String str = in.nextLine();
        while ( ! str.matches("[1-4]")) {
            System.out.println("Invalid selection");
            str = in.nextLine();
        }
        int suitChange = Integer.parseInt(str);
        if (suitChange == 1) {
            System.out.println("Suit is changed to Clubs");
        }
        if (suitChange == 2) {
            System.out.println("Suit is changed to Diamonds");
        }
        if (suitChange == 3) {
            System.out.println("Suit is changed to Hearts");
        }
        if (suitChange == 4) {
            System.out.println("Suit is changed to Spades");
        }
        suitChange -= 1;
        return suitChange;
    }

    public static void endGame() {
        System.out.println(winner + " wins the game!");
        System.exit(0);
    }

}