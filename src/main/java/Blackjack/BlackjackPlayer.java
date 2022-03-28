package Blackjack;

import java.util.ArrayList;
import java.util.List;
public class BlackjackPlayer implements PlayerInterface {

    private List<Card> cardHand = new ArrayList<>();
    private List<List<Card>> hands = new ArrayList<>();

    private String name;
    private double balance;
    private double bet;
    private double standardBet;
    private boolean playing;
    private int currentHandIndex;
    
    public BlackjackPlayer(double balance, String name, CardDeck deck) {
        if (name.equals("Dealer")) {
            throw new IllegalArgumentException("Name can not be Dealer.");
        }
        this.balance = balance;
        this.name = name;
        this.standardBet = 5.0;
        cardHand.add(deck.getCard());
        cardHand.add(deck.getCard());
        hands.add(cardHand);
        hands.add(new ArrayList<>());
        hands.add(new ArrayList<>());   
    }

    @Override
    public String toString() {
        return "BlackjackPlayer [balance=" + balance + ", cardHand=" + cardHand + ", name=" + name + "]";
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

    public int getCardCount() {
        return cardHand.size();
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

    public void newHand(CardDeck deck) {
        cardHand.clear();
        cardHand.add(deck.getCard());
        cardHand.add(deck.getCard());

        if (hands.get(1).size() > 0) {
            hands.get(1).clear();
        }

        this.playing = true;
    }

    public void addCard(CardDeck deck, int handIndex) {
        List<Card> hand = hands.get(handIndex);

        if (getScore(handIndex) >= 21) {
            throw new IllegalArgumentException("Du har mer enn 21");
        }
        hand.add(deck.getCard());

        if (hand.get(hand.size() - 1).getValue() == 11 && getScore(handIndex) > 21) { //Checking if the new card is Ace
            hand.get(hand.size() - 1).setAceToOne();
        }

        for (Card card : hand) {
            if (card.getFace().equals("A") && card.getValue() == 11 && getScore(handIndex) > 21){
                card.setAceToOne();
                break;
            }
        }
    }

    public boolean checkPlaying() {
        if (getScore(0) >= 21 || !playing) {
            return false;
        }
        else return true;
    }
    
    public void hold() {
        this.playing = false;
    }

    public void findWinner(HandComparator comp, PlayerInterface dealer) {
        int numberOfHands = hands.size();

        for (int i = 1; i < hands.size(); i++) {
            if (hands.get(i).size() == 0) {
                numberOfHands = i;
                break;
            }
        }
        //System.out.println(numberOfHands);
        for (int i = 0; i < numberOfHands; i++) {
            currentHandIndex = i;
            if (hands.get(i).size() == 2 && getScore(i) == 21) {
                balance += bet * 1.5;
            }
            else if (comp.compare(this, dealer) < 0){
                balance += bet;
            }
             else if (comp.compare(this, dealer) >0) {
                balance -= bet;
            }
        }
    }

    public String setBet(String betString) {
        try {
            if (betString.isBlank()) {
                return " ";
            }
            
            double betNum;
            betNum = Double.parseDouble(betString);
            if (betNum < balance && betNum > 0){
                this.bet = betNum;
                return " ";
            }
            else return "money";
        } 
        catch (Exception e) {
            //System.out.println("A double must be entered");
            return "double";
        }   
    }

    public void split(List<Card> hand) {
        if (canSplit(hand)) {
            hands.get(1).add(hands.get(0).remove(1));
        }
    }

    public boolean canSplit(List<Card> hand) {
        if (hand.size() == 2 && balance - 2*bet >= 0) {
            if (hand.get(0).getValue() == hand.get(1).getValue()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        CardDeck deck = new CardDeck(5);
        BlackjackPlayer p1 = new BlackjackPlayer(10, "Jens", deck);
        //Card ace = new Card('C', "J");
        //Card king = new Card('C', "K");
        //System.out.println(p1.getHand());
        //System.out.println(p1.getScore());

        //p1.cardHand.clear();
        //p1.cardHand.add(ace);
        //p1.cardHand.add(king);

        //System.out.println(p1.getHands());
        //p1.split(p1.getHand(0));
        //System.out.println(p1.getHand());
        //System.out.println(p1.getScore());
        //System.out.println(p1.getHands());

        p1.setBet("-2");
    }

}