package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BlackjackTest {

    @Test
    public void testCard() {
        Card card = new Card('S', "5");
        assertEquals(5, card.getValue());
    }
}
