package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
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
        player.addCard(deck, 0);

        while (true) {
            try {
                player.addCard(deck, 0);
            } catch (Exception e) {
                assertThrows(IllegalArgumentException.class, () -> player.addCard(deck, 0), "Can't add a card when score is >= 21");
                break;
            }
        }     
    }

    @Test
    public void testFindWinner() {
        HandComparator comp = new HandComparator();
        BlackJackDealer dealer = new BlackJackDealer(deck);
        
        while (true) {
            try {
                player.addCard(deck, 0);
            } catch (Exception e) {
                break;
            }
        } 
        if (player.getScore(0) == 21) {
            assertEquals(0, comp.compare(player, dealer));
        }
        else assertEquals(1, comp.compare(player, dealer));
    }

    @Test
    public void testSetBet() {
        assertEquals(" ", player.setBet(" "));
        assertEquals("double", player.setBet("All in"), "Set bet only takes in a double");
        assertEquals("money", player.setBet("9999"), "Set bet only takes in a double");

        player.setBet(" ");
        assertEquals(player.getStandardBet(), player.getBet());

        player.setBet("15");
        assertEquals(15.0, player.getBet());
    }

    @Test
    public void testSplit() {
        player.newHand(deck);
        while (! player.canSplit(player.getHand(0))) {
            player.newHand(deck);
        }
        Card c1 = player.getCard(0, 0);
        Card c2 = player.getCard(0, 1);
        assertTrue(c1.getFace() == c2.getFace() || c1.getValue() == c2.getValue());

        player.split(player.getHand(0));
        assertEquals(1, player.getHand(0).size());
        assertEquals(1, player.getHand(1).size());
        assertEquals(c1, player.getCard(0, 0));
        assertEquals(c2, player.getCard(1, 0));
    }

    @Test
    public void testCanSplit() {
        Card S3 = new Card('S', "3");
        Card D3 = new Card('D', "3");
        Card SJ = new Card('S', "J");
        Card HK = new Card('H', "K");

        assertTrue(player.canSplit(Arrays.asList(SJ, SJ)));
        assertTrue(player.canSplit(Arrays.asList(S3, D3)));
        assertTrue(player.canSplit(Arrays.asList(SJ, HK))); 

        assertFalse(player.canSplit(Arrays.asList(S3, SJ, HK)), "Can only split hands with 2 cards");
        assertFalse(player.canSplit(Arrays.asList(S3, HK)), "Can't split when the 2 cards don't have the same value or suit");
        assertFalse(player.canSplit(Arrays.asList(S3, SJ)), "Can't split when only the suits are equal");
    }
}
