package Blackjack;

import java.util.ArrayList;
import java.util.Arrays;

public class Card {

    private int value;
    private Character suit;
    private String face;
    private final ArrayList<String> validFaceList = new ArrayList<>(Arrays.asList("A","2","3","4","5","6","7","8","9","10","J","Q","K"));

    public Card(Character suit, String face){
        if (isValidFace(face) && isValidSuit(suit)){
            this.face = face;
            this.suit = suit;
            this.value = setValue(face);
        }
        else throw new IllegalArgumentException();
    }

    private boolean isValidSuit(Character suit) {
        if (suit == 'S' || suit == 'H' || suit == 'D' || suit == 'C') {
            return true;
        }
        else return false;
    }

    private boolean isValidFace(String face) {
        return validFaceList.contains(face);
    }

    //Alle bildekort får verdi 10       
    //Setter verdi av ess til 1, men vi kan implementere endring i objektet for hånda
    public int setValue(String face) {
        if (validFaceList.indexOf(face) > 9) {
            value = 10;
        }
        else if (validFaceList.indexOf(face) == 0) {
            value = 11;
        }
        else value = validFaceList.indexOf(face) + 1;

        return value;
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
        //return "Card [face=" + face + ", suit=" + suit + ", value=" + value + "]";
        return "" + face + suit + "|" + value;
    }

    public static void main(String[] args) {
        Card KingOfClubs = new Card('C',"K");
        System.out.println(KingOfClubs);
    }
}