package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardDeckTest {

    private CardDeck deck5;
    private CardDeck deck1;
    
    @BeforeEach
    public void setup() {
        deck5 = new CardDeck(5);
        deck1 = new CardDeck(1);
    }

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new CardDeck(-1));

        assertEquals(52, deck1.getSize());
        assertEquals(260, deck5.getSize());

        CardDeck altDeck = new CardDeck(1);
        assertNotEquals(deck1, altDeck);
        assertEquals(deck1.getSize(), altDeck.getSize());
    }
}
