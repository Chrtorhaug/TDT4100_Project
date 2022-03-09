package Blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CardDeck {
    
    private final ArrayList<String> validFaceList = new ArrayList<>(Arrays.asList("A","2","3","4","5","6","7","8","9","10","J","Q","K"));
    private final ArrayList<Character> suits = new ArrayList<>(Arrays.asList('H', 'S', 'D', 'C'));
    private List<Card> deck = new ArrayList<>();

    public CardDeck(int n) {
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
