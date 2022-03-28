package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlackJackDealerTest {
    
    private BlackJackDealer dealer;

    @BeforeEach
    public void setup() {
        CardDeck deck = new CardDeck(1);
        dealer = new BlackJackDealer(deck);
    }

    @Test
    public void testConstructor() {
        assertEquals("Dealer", dealer.getName());

        assertTrue(dealer.getScore(0) >= 17);
        assertTrue(dealer.getHand(0).size() >= 2);
    }
}
