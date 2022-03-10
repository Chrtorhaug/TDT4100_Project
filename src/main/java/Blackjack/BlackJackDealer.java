package Blackjack;

import java.util.ArrayList;
import java.util.List;

public class BlackJackDealer implements PlayerInterface{

    private List<Card> cardHand = new ArrayList<>();  
    private int score;


    public BlackJackDealer(CardDeck deck){
        cardHand.add(deck.getCard());
        cardHand.add(deck.getCard());
        score = cardHand.get(0).getValue()+cardHand.get(1).getValue();
    }

    public void newHand(CardDeck deck) {
        cardHand.clear();
        cardHand.add(deck.getCard());
        cardHand.add(deck.getCard());
        score = cardHand.get(0).getValue() + cardHand.get(1).getValue();
    }
    
	@Override
	public String getName() {
		return "Dealer";
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public List<Card> getHand() {
		return cardHand;
	}

    public void addCard(CardDeck deck) {
        while (score<17) {
            cardHand.add(deck.getCard());
            if (cardHand.get(-1).getValue()+score>21) {
                score+=1;
            }
            else {
                score+=cardHand.get(-1).getValue();
            }
        }
    }

    public static void main(String[] args) {
        CardDeck deck = new CardDeck(1);
        BlackJackDealer dealer = new BlackJackDealer(deck);
        System.out.println(dealer.getScore());
    }
    
}