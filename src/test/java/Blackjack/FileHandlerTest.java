package Blackjack;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.FileNotFoundException;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileHandlerTest {

    private FileHandler handler;

    @BeforeEach
    public void setUp() throws FileNotFoundException {
        handler = new FileHandler();
        handler.UpdateBalance("UserName",85.3);
        handler.UpdateBalance("Kokos",100.0);
    }


    @Test
    public void testGetBalance() {
        assertEquals("700.7",handler.getBalance("James Bond"));
        assertEquals("85.3",handler.getBalance("UserName"));
    }

    @Test
    public void testUpdateBalance() throws FileNotFoundException {
        handler.UpdateBalance("UserName",116.5);
        assertEquals("116.5",handler.getBalance("UserName"));
        handler.UpdateBalance("Kokos",60.6);
        assertEquals("60.6",handler.getBalance("Kokos"));
    }



    @Test
    public void testValidateUserAtLogin() {
        assertTrue(handler.CheckRegisterOrLogin("Login","UserName","BlackJack123"));
        assertTrue(handler.CheckRegisterOrLogin("Login","Kokos","Blackjack543"));
        assertFalse(handler.CheckRegisterOrLogin("Login","UserName","Password"));
        assertFalse(handler.CheckRegisterOrLogin("Login","Christian","BlackJack123"));

    }

    @Test
    public void updateTopPlayers() {
        List<String> topPlayers = handler.updateTopPlayers();
        assertEquals("James Bond,700.7",topPlayers.get(0));
        assertEquals("UserName,85.3",topPlayers.get(topPlayers.size() - 2));
    }

    @Test
    public void testValidateUserAtRegister() throws FileNotFoundException {
        assertFalse(handler.CheckRegisterOrLogin("Register","MoneyMaker","Password"));
        assertFalse(handler.CheckRegisterOrLogin("Register","JamesBond","MonteCarlo"));
        assertFalse(handler.CheckRegisterOrLogin("Register","MoneyMaker","PlatoOPLomo"));
        assertTrue(handler.CheckRegisterOrLogin("Register","MoneyMaker","PlatoOPLomo123"));
        assertFalse(handler.CheckRegisterOrLogin("Register","MoneyMaker","PlatoOPLomo123"));
        handler.removePlayerJustForTestUse("MoneyMaker");
    }
    
}
