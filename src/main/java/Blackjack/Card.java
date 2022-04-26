package Blackjack;

import java.util.Arrays;
import java.util.List;
public class Card {

    private int value;
    private Character suit;
    private String face;
    private PlayerInterface player;
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

    public boolean setAceToOne(int handIndex) {
        if (getFace().equals("A") && getValue() == 11 && player.getScore(handIndex) > 21) {
            this.value = 1;
            return true;
        }
        return false;
    }

    public void setAceToEleven() {
        if (getFace().equals("A") && getValue() == 1) {
            this.value = 11;
        }
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

    public void setPlayer(PlayerInterface player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return "" + suit + face;
    }
}