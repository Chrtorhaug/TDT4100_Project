package Blackjack;

import java.util.List;
public interface PlayerInterface {

    public String getName();
    public int getScore();
    public List<Card> getHand(int n);
    public void newHand(CardDeck deck);
}
