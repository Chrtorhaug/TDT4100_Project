package Blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    
    private final ArrayList<String> validFaceList = new ArrayList<>(Arrays.asList("A","2","3","4","5","6","7","8","9","10","J","Q","K"));
    //private final ArrayList<Character> suits = new ArrayList<>(Arrays.asList('\u0003', '\u0006', '\u0004', '\u0005'));
    private final ArrayList<Character> suits = new ArrayList<>(Arrays.asList('S', 'H', 'C', 'D'));
    private List<Card> deck = new ArrayList<>();

    public CardDeck(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        
        for (int i = 0; i < n; i++) {
            for (String face : validFaceList) {
                for (char suit : suits) {
                    deck.add(new Card(suit, face));
                }
            }
        }
        Collections.shuffle(deck);
    }

    public List<Card> getDeck() {
        return deck; 
    }

    public Card getCard() {
        return deck.remove(0);
    }

    public int getSize() {
        return deck.size();
    }

    public static void main(String[] args) {
        CardDeck deck = new CardDeck(1);
        System.out.println(deck.getDeck());
    }
}
