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
        while (getScore() < 17) {
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
    }
    
	public String getName() {
		return "Dealer";
	}

	public int getScore() {
		return cardHand.stream().mapToInt(c -> c.getValue()).sum();
	}

	public List<Card> getHand() {
		return cardHand;
	}

    public static void main(String[] args) {
        CardDeck deck = new CardDeck(1);
        BlackJackDealer dealer = new BlackJackDealer(deck);
        System.out.println(dealer.getScore());
    }
    
}