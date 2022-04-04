package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HandComparatorTest {
    
    private HandComparator comp;
    private BlackJackDealer dealer;
    private BlackjackPlayer player;

    @BeforeEach
    public void setup() {
        CardDeck deck = new CardDeck(1);
        comp = new HandComparator();
        dealer = new BlackJackDealer(deck);
        player = new BlackjackPlayer(100, "Tester", deck);
    }

    @Test
    public void testComparator() {
        assertEquals(1, comp.compare(player, dealer));
    }
}
