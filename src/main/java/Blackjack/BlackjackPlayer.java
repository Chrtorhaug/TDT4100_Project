package Blackjack;

import java.util.ArrayList;
import java.util.List;
public class BlackjackPlayer implements PlayerInterface {

    private List<Card> cardHand = new ArrayList<>();
    private List<List<Card>> hands = new ArrayList<>();

    private String name;
    private double balance;
    private double bet;
    private boolean playing;
    
    public BlackjackPlayer(double balance, String name, CardDeck deck) {
        if (name.equals("Dealer")) {
            throw new IllegalArgumentException("Name can not be Dealer.");
        }
        this.balance = balance;
        this.name = name;
        cardHand.add(deck.getCard());
        cardHand.add(deck.getCard());
        hands.add(cardHand);   
    }

    @Override
    public String toString() {
        return "BlackjackPlayer [balance=" + balance + ", cardHand=" + cardHand + ", name=" + name + "]";
    }

    public int getScore() {
        return cardHand.stream().mapToInt(c -> c.getValue()).sum();
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

    public Card getCard(int n) {
        return cardHand.get(n);
    }

    public int getCardCount() {
        return cardHand.size();
    }

    public List<List<Card>> getHands() {
        return hands;
    }

    public void newHand(CardDeck deck) {
        cardHand.clear();
        cardHand.add(deck.getCard());
        cardHand.add(deck.getCard());

        this.playing = true;
    }

    public void addCard(CardDeck deck) {
        if (!checkPlaying()) {
            throw new IllegalArgumentException("Du har mer enn 21");
        }
        cardHand.add(deck.getCard());

        if (cardHand.get(cardHand.size() - 1).getValue() == 11 && getScore() > 21) { //Checking if the new card is Ace
            cardHand.get(cardHand.size() - 1).setAceToOne();
        }

        for (Card card : cardHand) {
            if (card.getFace().equals("A") && card.getValue() == 11 && getScore() > 21){
                card.setAceToOne();
                break;
            }
        }
    }

    public boolean checkPlaying() {
        if (getScore() >= 21 || !playing) {
            return false;
        }
        else return true;
    }
    
    public void hold() {
        this.playing = false;
    }

    public void findWinner(HandComparator comp, PlayerInterface dealer) {
        if (cardHand.size() == 2 && getScore() == 21) {
            balance += bet * 1.5;
        }
        else if (comp.compare(this, dealer) < 0){
            balance += bet;
        }
         else if (comp.compare(this, dealer) >0) {
            balance -= bet;
        }
    }

    public void setBet(String betString) throws IllegalArgumentException{
        try {
            double betNum;
            betNum = Double.parseDouble(betString);
            if (betNum < balance && betNum > 0){
                this.bet = betNum;
            }
            else throw new IllegalArgumentException();
        } 
        catch (Exception e) {
            System.out.println("A double must be entered");
            throw new IllegalArgumentException();
        }   
    }

    public void split(List<Card> hand) {
        if (canSplit(hand)) {
            List<Card> tempList = new ArrayList<>();
            tempList.add(cardHand.remove(1));
            hands.add(tempList);
        }
    }

    public boolean canSplit(List<Card> hand) {
        if (hand.size() == 2) {
            if (hand.get(0).getValue() == hand.get(1).getValue()) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        CardDeck deck = new CardDeck(1);
        BlackjackPlayer p1 = new BlackjackPlayer(10, "Jens", deck);
        Card ace = new Card('C', "J");
        Card king = new Card('C', "K");
        //System.out.println(p1.getHand());
        //System.out.println(p1.getScore());

        p1.cardHand.clear();
        p1.cardHand.add(ace);
        p1.cardHand.add(king);

        System.out.println(p1.getHands());
        p1.split(p1.getHand(0));
        //System.out.println(p1.getHand());
        //System.out.println(p1.getScore());
        System.out.println(p1.getHands());

    }

}