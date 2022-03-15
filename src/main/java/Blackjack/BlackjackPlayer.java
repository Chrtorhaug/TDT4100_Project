package Blackjack;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class BlackjackPlayer implements PlayerInterface {

    private List<Card> cardHand = new ArrayList<>();
    private List<Card> cardHand2 = new ArrayList<>();
    private List<Card> cardHand3 = new ArrayList<>();
    private List<Card> cardHand4 = new ArrayList<>();

    private String name;
    private double balance;
    private int score;
    private double bet;
    private boolean playing;
    
    public BlackjackPlayer(double balance, String name, CardDeck deck) {
        this.balance = balance;
        if (name.equals("Dealer")) {
            throw new IllegalArgumentException("Name can not be Dealer.");
        }
        this.name = name;
        cardHand.add(deck.getCard());
        cardHand.add(deck.getCard());   
    }

    @Override
    public String toString() {
        return "BlackjackPlayer [balance=" + balance + ", cardHand=" + cardHand + ", name=" + name + "]";
    }

    public int getScore() {
        return cardHand.stream().map(c -> c.getValue()).reduce(0, Integer::sum);
    }

    public List<Card> getHand() {
        return cardHand;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String score() {
        return "You have: " + score + " points";
    }

    public Card getCard(int n) {
        return cardHand.get(n);
    }

    public int getCardCount() {
        return cardHand.size();
    }

    public void newHand(CardDeck deck) {
        cardHand.clear();
        cardHand.add(deck.getCard());
        cardHand.add(deck.getCard());
        score = cardHand.get(0).getValue() + cardHand.get(1).getValue();
        this.playing = true;
    }

    public void addCard(CardDeck deck) {
        if (!checkPlaying()) {
            throw new IllegalArgumentException("Du har mer enn 21");
        }

        cardHand.add(deck.getCard());

        if (cardHand.get(cardHand.size() - 1).getValue() == 11 && score + cardHand.get(cardHand.size() - 1).getValue() > 21) { //Checking if the new card is Ace
            cardHand.get(cardHand.size() - 1).setAceToOne();
        }
        else score += cardHand.get(cardHand.size() - 1).getValue();

        for (Card card : cardHand) {
            if ( card.getFace().equals("A") && card.getValue()==11 && score>21){
                card.setAceToOne();
                break;
            }
        }
    }

    public boolean checkPlaying() {
        if (score >= 21 || !playing) {
            return false;
        }
        else return true;
    }
    
    public void hold() {
        this.playing = false;
    }

    public void findWinner(HandComparator comp, PlayerInterface dealer) {
        if (comp.compare(this, dealer) < 0){
            balance += bet;
        }
        if (comp.compare(this, dealer) >0) {
            balance -= bet;
        }
    }

    public void setBet(String betString) throws IllegalArgumentException{
        try {
            double betNum;
            betNum = Double.parseDouble(betString);
            if (betNum<balance && betNum>0){
                this.bet=betNum;
            }
            else {throw new IllegalArgumentException();}
        } catch (Exception e) {
            System.out.println("A double must be entered");
            throw new IllegalArgumentException();
        }

        
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