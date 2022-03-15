package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CardTest {

    private Card c1;
    private Card c2;
    private Card c3;

    @BeforeEach
    public void setup() {
        c1 = new Card('S', "5");
        c2 = new Card('D', "K");
        c3 = new Card('C', "A");
    }
    
    @Test
    public void testConstructor() {
        assertEquals("5", c1.getFace());
        assertEquals('S', c1.getSuit());
        assertEquals("S 5", c1.toString());
        
        assertThrows(IllegalArgumentException.class , () -> new Card('A', "3"));
        assertThrows(IllegalArgumentException.class , () -> new Card('D', "1"));
    }

    @Test 
    public void testSetValue() {
        assertEquals(5, c1.getValue());
        assertEquals(10, c2.getValue());
        assertEquals(11, c3.getValue());
    }
}
