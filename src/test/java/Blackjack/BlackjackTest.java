package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BlackjackTest {
    @Test
    public void testGetTall() {
        Card card = new Card();
        assertEquals(0, card.getTall());
    }
}
