package Blackjack;

import java.util.List;
public interface PlayerInterface {

    public String getName();
    public int getScore(int n);
    public List<Card> getHand(int n);
    public Card getCard(int hand, int n);
    public void newHand(CardDeck deck);
    public int getCurrentHandIndex();
}
