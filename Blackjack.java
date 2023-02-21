import java.util.Scanner;
import lib.Card;
import lib.DeckOfCards;
import lib.Ranks;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class Blackjack {
    private static HashMap<Ranks, Integer> map = new HashMap<>();
    public static void populateMap() {
        map.put(Ranks.ACE, 11);
        map.put(Ranks.TWO, 2);
        map.put(Ranks.THREE, 3);
        map.put(Ranks.FOUR, 4);
        map.put(Ranks.FIVE, 5);
        map.put(Ranks.SIX, 6);
        map.put(Ranks.SEVEN, 7);
        map.put(Ranks.EIGHT, 8);
        map.put(Ranks.NINE, 9);
        map.put(Ranks.TEN, 10);
        map.put(Ranks.JACK, 10);
        map.put(Ranks.QUEEN, 10);
        map.put(Ranks.KING, 10);
    }
    public static Boolean checkBlackjack(List<Card> hand) {
        Card card1 = hand.get(0);
        Card card2 = hand.get(1);

        if (card1.getRank() == Ranks.ACE && (card2.getRank() == Ranks.TEN || card2.getRank() == Ranks.JACK
        || card2.getRank() == Ranks.QUEEN || card2.getRank() == Ranks.KING)) {
            return true;
        } else if ((card1.getRank() == Ranks.TEN || card1.getRank() == Ranks.JACK
        || card1.getRank() == Ranks.QUEEN || card1.getRank() == Ranks.KING) && card2.getRank() == Ranks.ACE) {
            return true;
        } else {
            return false;
        }
    }
    public static int handToInt(List<Card> hand) {
        int total = 0;
        int aceCount = 0;
        Card card = new Card(null, null);
        for (int i = 0; i < hand.size(); i++) {
            card = hand.get(i);
            total += map.get(card.getRank());
            if (card.getRank() == Ranks.ACE) aceCount++;
        }
        if (total > 21 && aceCount > 1) {
            total -= (aceCount - 1) * 10;
        }
        if (total > 21 && aceCount == 1) {
            total -= 10;
        }
        return total;
    }

    public static Boolean validBetCheck(int bet, int houseChips, int playerChips) {
        if (bet < 0 || bet > houseChips || bet > playerChips) return false;
        return true;
    }
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Welcome to the Blackjack table! Would you like to start a game?");
        System.out.print("Enter 'y' or 'n': ");
        String confirm = input.next();

        if (confirm.equals("y")) {
            System.out.println("--------------------------------------------------------");
            populateMap();
            int playerChips = 100;
            int houseChips = 200;

            while (playerChips > 0 && houseChips > 0) {
                DeckOfCards deck = new DeckOfCards();
                deck.shuffle();
                List<Card> playerHand = new ArrayList<>();
                List<Card> houseHand = new ArrayList<>();
                Card playerCard1 = deck.deal();
                Card houseCard1 = deck.deal();
                Card playerCard2 = deck.deal();
                Card houseCard2 = deck.deal();
                Collections.addAll(playerHand, playerCard1, playerCard2);
                Collections.addAll(houseHand, houseCard1, houseCard2);
                int pHandTotal = handToInt(playerHand);
                int hHandTotal = handToInt(houseHand);

                System.out.printf("You have %d chips. The House has %d chips.%n", playerChips, houseChips);
                System.out.print("How many chips would you like to bet?: ");
                int playerBet = input.nextInt();
                while (validBetCheck(playerBet, houseChips, playerChips) == false) {
                    System.out.print("Please enter a valid bet: ");
                    playerBet = input.nextInt();
                }
                System.out.println("You bet " + playerBet + " chips. Dealing cards...");
                System.out.println("--------------------------------------------------------");

                System.out.printf("You are dealt the %s OF %s.%n", playerCard1.getRank(), playerCard1.getSuit());
                System.out.printf("The House is dealt the %s OF %s.%n", houseCard1.getRank(), houseCard1.getSuit());
                System.out.printf("You are dealt the %s OF %s.%n", playerCard2.getRank(), playerCard2.getSuit());
                System.out.println("The House is dealt a (hole) card face down.");
                
                System.out.printf("%nYour hand total: %d%n", pHandTotal);

                Boolean bjHouse = checkBlackjack(houseHand);
                Boolean bjPlayer = checkBlackjack(playerHand);
                if (bjHouse == true && bjPlayer == false) {
                    playerChips -= playerBet;
                    houseChips += playerBet;
                    System.out.printf("The House's hole card is the %s OF %s.%n", houseCard2.getRank(), houseCard2.getSuit());
                    System.out.println("The House has blackjack! You lose the round!");
                    System.out.println("The House wins " + playerBet + " chips.");
                    playerBet = 0;
                }
                else if (bjPlayer == true && bjHouse == false) {
                    playerChips += playerBet * 2;
                    houseChips -= playerBet * 2;
                    System.out.println("Congratulations! You have blackjack! You win the round!");
                    System.out.println("You win " + (playerBet * 2) + " chips.");
                    playerBet = 0;
                } else if (bjPlayer == true && bjHouse == true) {
                    playerBet = 0;
                    System.out.println("Both you and the House have blackjack! The round is a draw!");
                } else {
                    while (pHandTotal <= 21) {
                        System.out.println("Would you like to hit or stand?");
                        System.out.print("Enter 'h' to hit or 's' to stand: ");
                        confirm = input.next();
                        if (confirm.equals("h")) {
                            playerCard1 = deck.deal();
                            playerHand.add(playerCard1);
                            pHandTotal = handToInt(playerHand);
                            System.out.printf("%nYou are dealt the %s OF %s.%n", playerCard1.getRank(), playerCard1.getSuit());
                            System.out.printf("Your hand total: %d%n%n", pHandTotal);
                            if (pHandTotal > 21) {
                                System.out.println("You stand. The House now plays!");
                                System.out.println("--------------------------------------------------------");
                            }
                        } else if (confirm.equals("s")) {
                            System.out.printf("The House now plays.%n");
                            System.out.println("--------------------------------------------------------");
                            break;
                        } else {
                            System.out.println("\nPlease choose a valid move.");
                        }
                    }
                    System.out.printf("The House reveals its hole card. It is the %s OF %s.%n", houseCard2.getRank(), houseCard2.getSuit());
                    System.out.printf("House hand total: %d%n", hHandTotal);
                    while (hHandTotal <= 21) {
                        if (hHandTotal > 16 && hHandTotal <= 21) {
                            System.out.println("The House stands.");
                            break;
                        } else {
                            houseCard1 = deck.deal();
                            houseHand.add(houseCard1);
                            hHandTotal = handToInt(houseHand);
                            System.out.printf("%nThe House is dealt the %s OF %s.%n", houseCard1.getRank(), houseCard1.getSuit());
                            System.out.printf("House hand total: %d%n", hHandTotal);
                            if (hHandTotal > 21) {
                                System.out.println("The House stands.");
                            }
                        }
                    }
                    System.out.println("--------------------------------------------------------");
                    System.out.printf("Your hand totals %d and the House's hand totals %d.%n", pHandTotal, hHandTotal);
                    if (pHandTotal > 21) {
                        playerChips -= playerBet;
                        houseChips += playerBet;
                        System.out.println("You bust and lose the round!");
                        System.out.println("The House wins " + playerBet + " chips from you.\n");
                        playerBet = 0;
                    }
                    if (hHandTotal > pHandTotal && hHandTotal <= 21) {
                        playerChips -= playerBet;
                        houseChips += playerBet;
                        System.out.println("The House has the better hand. You lose the round!\n");
                        System.out.println("The House wins " + playerBet + " chips from you.\n");
                        playerBet = 0;
                    } 
                    if (pHandTotal > hHandTotal && pHandTotal <= 21) {
                        playerChips += playerBet;
                        houseChips -= playerBet;
                        System.out.println("You have the better hand. You win the round!\n");
                        System.out.println("You win " + playerBet + " chips from the House.\n");
                        playerBet = 0;
                    }
                    if (hHandTotal > 21 && pHandTotal <= 21) {
                        playerChips += playerBet;
                        houseChips -= playerBet;
                        System.out.println("You have the better hand. You win the round!\n");
                        System.out.println("You win " + playerBet + " chips from the House.\n");
                        playerBet = 0;
                    }
                    if (pHandTotal <= 21 && hHandTotal <= 21 && pHandTotal == hHandTotal) {
                        playerBet = 0;
                        System.out.println("The game is a draw!\n");
                    }
                }
            }
            if (playerChips <= 0) System.out.println("You are out of chips. You lose!");
            if (houseChips <= 0) System.out.println("The House is out of chips. You win!");
            input.close();
            System.exit(0);
        } else {
            input.close();
            System.out.println("Closing program!");
            System.exit(0);
        }
    }
}