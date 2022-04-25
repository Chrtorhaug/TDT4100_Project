package Blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
public class BlackjackPlayer implements PlayerInterface {

    private List<List<Card>> hands = Arrays.asList(new ArrayList<Card>(), new ArrayList<Card>(), new ArrayList<Card>());
    private String name;
    private double balance;
    private double bet;
    private final double standardBet = 5.0;
    private int rebuys = 0;
    private double sessionStartMoney;
    private boolean playing;
    private int currentHandIndex;
    
    public BlackjackPlayer(double balance, String name, CardDeck deck) {
        
        this.balance = balance;
        this.sessionStartMoney = balance;
        this.name = name;
    }

    @Override
    public String toString() {
        return "BlackjackPlayer [balance=" + balance + ", cardHand=" + hands + ", name=" + name + "]";
    }

    public int getScore(int n) {
        return hands.get(n).stream().mapToInt(c -> c.getValue()).sum();
    }

    public List<Card> getHand(int n) {
        return hands.get(n);
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public Card getCard(int hand, int n) {
        return hands.get(hand).get(n);
    }

    public List<List<Card>> getHands() {
        return hands;
    }

    public double getStandardBet() {
        return standardBet;
    }

    public int getCurrentHandIndex() {
        return currentHandIndex;
    }

    public void setBalance(double bal) {
        this.balance = bal;
    }

    public double getMoneyChange() {
        return balance - (rebuys * 100 + sessionStartMoney);
    }

    public boolean rebuy() {
        if (balance == 0.0) {
            this.balance = 100;
            rebuys++;
            return true;
        }
        else return false;
    }

    public void newHand(CardDeck deck) {
        for (List<Card> hand : hands) {
            hand.clear();
        }
        addCard(deck, 0);
        addCard(deck, 0);

        this.playing = true;
    }

    public void addCard(CardDeck deck, int handIndex) {
        List<Card> hand = hands.get(handIndex);

        if (getScore(handIndex) >= 21) {
            throw new IllegalArgumentException("Du har mer enn 21");
        }
        hand.add(deck.getCard()); 
    }

    public boolean checkPlaying() {
        return !(getScore(0) >= 21 || !playing);
    }
    
    public void hold() {
        this.playing = false;
    }

    public void setAceToOne(List<Card> hand, int handIndex) {
        for (Card card : hand) {
            if (card.setAceToOne(this, handIndex)) {
                return;
            }
        }
    }

    public void findWinner(HandComparator comp, PlayerInterface dealer) {
        int numberOfHands = hands.size();

        for (int i = 1; i < hands.size(); i++) { //Checking how many playing hands the player has
            if (hands.get(i).size() == 0) {
                numberOfHands = i;
                break;
            }
        }

        for (int i = 0; i < numberOfHands; i++) {
            currentHandIndex = i;
            if (hands.get(i).size() == 2 && getScore(i) == 21) { // Checking if Blackjack, pays 3 to 2
                balance += bet * 1.5;
            }
            else if (comp.compare(this, dealer) < 0) { //Check if player wins
                balance += bet;
            }
             else if (comp.compare(this, dealer) >0) { // Check if dealer wins
                balance -= bet;
            }
        }

    }

    public String setBet(String betString) {
        try {
            if (betString.isEmpty()) {
                this.bet = standardBet;
                return " ";
            }
            double betNum = Double.parseDouble(betString);

            if (betNum <= balance && betNum > 0) {
                this.bet = betNum;
                return " ";
            }
            else return "money";
        } 
        catch (Exception e) {
            return "double";
        }   
    }

    public void split(List<Card> hand) {
        if (hand.get(0).getFace().equals("A") && hand.get(0).getFace().equals("A")) { //When you split two Aces
            hand.get(0).setAceToEleven();
        }

        if (canSplit(hand) && hand.equals(getHand(0))) { //Finding the right hand to split
            if (getHand(1).size() == 0) {
                hands.get(1).add(hands.get(0).remove(1));
            }
            else hands.get(2).add(hands.get(0).remove(1));
        }
        else hands.get(2).add(hands.get(1).remove(1));
    }

    public boolean canSplit(List<Card> hand) {
        if (hand.size() == 2 && balance - 2*bet >= 0) {
            if ((hand.get(0).getFace() == hand.get(1).getFace()) || (hand.get(0).getValue() == hand.get(1).getValue())) {
                return true;
            }
        }
        return false;
    }

    public double getBet() {
        return bet;
    }
}