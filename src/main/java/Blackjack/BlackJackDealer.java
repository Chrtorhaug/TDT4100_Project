package Blackjack;

import java.util.ArrayList;
import java.util.List;

public class BlackJackDealer implements PlayerInterface {

    private List<Card> cardHand = new ArrayList<>();  
    private int score;


    public BlackJackDealer(CardDeck deck){
        this.newHand(deck);
    }

    public void newHand(CardDeck deck) {
        cardHand.clear();
        score=0;
        while (score<17) {
            Card tmp = deck.getCard();
            cardHand.add(tmp);
            if (tmp.getValue()==11 && tmp.getValue()+score>21)
                score++;
            else{
            score+=tmp.getValue();
            }   
        }   
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

    public static void main(String[] args) {
        CardDeck deck = new CardDeck(1);
        BlackJackDealer dealer = new BlackJackDealer(deck);
        System.out.println(dealer.getScore());
    }
    
}