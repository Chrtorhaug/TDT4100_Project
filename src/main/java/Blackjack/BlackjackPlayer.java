package Blackjack;

import java.util.ArrayList;
import java.util.List;

public class BlackjackPlayer implements PlayerInterface {

    private List<Card> cardHand = new ArrayList<>();
    private String name;
    private int balance;
    private int score;
    private boolean playing;
    
    public BlackjackPlayer(int balance, String name, CardDeck deck) {
        this.balance = balance;
        this.name = name;
        cardHand.add(deck.getCard());
        cardHand.add(deck.getCard());
        score = cardHand.get(0).getValue() + cardHand.get(1).getValue();    
    }

    @Override
    public String toString() {
        return "BlackjackPlayer [balance=" + balance + ", cardHand=" + cardHand + ", name=" + name + "]";
    }

    public int getScore() {
        return score;
    }

    public List<Card> getHand() {
        return cardHand;
    }

    public String getName() {
        return name;
    }

    public String score() {
        return "You have: " + score + " points";
    }

    public void addCard(CardDeck deck) {
        if (checkState()) {
            throw new IllegalArgumentException("Du har mer enn 21");
        }

        cardHand.add(deck.getCard());
        if (cardHand.get(cardHand.size() - 1).getValue()==11 && score+cardHand.get(cardHand.size() - 1).getValue()>21) { //Checking if the new card is Ace
            score+=1;
        }
        else score += cardHand.get(cardHand.size() - 1).getValue();
    }

    public boolean checkState() {
        if (score >= 21 || !playing) {
            return false;
        }
        else return true;
    }
    
    public void hold() {
        this.playing = false;
    }

    public static void main(String[] args) {
        CardDeck deck = new CardDeck(1);
        BlackjackPlayer p1 = new BlackjackPlayer(10, "Jens", deck);
        System.out.println(p1.getHand());
        System.out.println(p1.score());
        p1.addCard(deck);
        System.out.println(p1.getHand());
        System.out.println(p1.score());
    } 
}