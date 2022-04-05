package Blackjack;

import java.util.Arrays;
import java.util.List;
public class Card {

    private int value;
    private Character suit;
    private String face;
    private final List<String> validFaceList = Arrays.asList("A","2","3","4","5","6","7","8","9","10","J","Q","K");
    private final List<Character> validSuitList = Arrays.asList('S', 'H', 'C', 'D');

    public Card(Character suit, String face){
        if (isValidFace(face) && isValidSuit(suit)) {
            this.face = face;
            this.suit = suit;
            this.value = setValue(face);
        }
        else throw new IllegalArgumentException();
    }

    private boolean isValidSuit(Character suit) {
        return validSuitList.contains(suit);
    }

    private boolean isValidFace(String face) {
        return validFaceList.contains(face);
    }

    public int setValue(String face) {
        if (face.equals("10") || face.equals("J") || face.equals("Q") || face.equals("K")) {
            return 10;
        }
        else if (face.equals("A")) {
            return 11;
        }
        else return validFaceList.indexOf(face) + 1;
    }

    public void setAceToOne() {
        this.value = 1;
    }

    public void setAceToEleven() {
        this.value = 11;
    }

    public int getValue() {
        return value;
    }

    public Character getSuit() {
        return suit;
    }

    public String getFace() {
        return face;
    }

    @Override
    public String toString() {
        return "" + suit + " " + face;
    }
}