package Blackjack;

import java.util.ArrayList;
import java.util.List;

public class BlackJackDealer implements PlayerInterface {

    private List<Card> cardHand = new ArrayList<>();  

    public BlackJackDealer(CardDeck deck){
        newHand(deck);
    }

    public void newHand(CardDeck deck) {
        cardHand.clear();

        while (getScore(0) < 17) {
            cardHand.add(deck.getCard()); 

            for (Card card : cardHand) {
                if (card.getFace().equals("A") && card.getValue() == 11 && getScore(0) > 21) {
                    card.setAceToOne();
                    break;
                }
            }
        }   
    }
    
	public String getName() {
		return "Dealer";
	}

	public int getScore(int n) {
		return cardHand.stream().mapToInt(c -> c.getValue()).sum();
	}

	public List<Card> getHand(int n) {
		return cardHand;
	}

    public Card getCard(int hand, int n) {
        return cardHand.get(n);
    }
    
    public int getCurrentHandIndex() {
        return 0;
    }
}