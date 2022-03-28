package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlackjackPlayerTest {

    private BlackjackPlayer player;
    private CardDeck deck;

    @BeforeEach
    public void setup() {
        deck = new CardDeck(1);
        player = new BlackjackPlayer(100, "Tester", deck);
    }

    @Test
    public void testConstructor() {
        assertThrows(IllegalArgumentException.class, () -> new BlackjackPlayer(100, "Dealer", deck));

        assertEquals("Tester", player.getName());
        assertEquals(100.0, player.getBalance()); 
    }

    @Test
    public void testNewHand() {
        player.newHand(deck);

        assertEquals(2, player.getHand(0).size());
        for (int i = 1; i < player.getHands().size(); i++) {
            assertEquals(0, player.getHand(i).size());
        }

        assertTrue(player.checkPlaying());
    }

    @Test
    public void testAddCard() {
        player.newHand(deck);
        player.addCard(deck, 0);

        
    }

    @Test
    public void testFindWinner() {

    }

    @Test
    public void testSetBet() {

    }

    @Test
    public void testSplit() {

    }

    @Test
    public void testCanSplit() {

    }
}
