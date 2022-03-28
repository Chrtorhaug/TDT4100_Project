package Blackjack;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BlackjackPlayerTest {

    private BlackjackPlayer player;

    @BeforeEach
    public void setup() {
        CardDeck deck = new CardDeck(1);
        player = new BlackjackPlayer(100 ,"Tester", deck);
    }

    @Test
    public void testConstructor() {

    }
}
