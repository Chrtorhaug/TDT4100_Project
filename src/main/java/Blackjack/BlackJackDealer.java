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
            Card tempCard = deck.getCard();
            tempCard.setPlayer(this);
            cardHand.add(tempCard); 

            for (Card card : cardHand) {
                if (card.setAceToOne(0)) {
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