package Blackjack;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileHandlerTest {

    private FileHandler handler;


    @BeforeEach
    public void setUp() {
        handler = new FileHandler();
    }

    @Test
    public void testValidatePassword() {
        assertTrue(handler.CheckRegisterOrLogin("Login","UserName","BlackJack123"));
    }
    
}
