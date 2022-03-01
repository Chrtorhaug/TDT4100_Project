package Blackjack;

import java.util.ArrayList;
import java.util.Arrays;

public class Card{

    private int value;
    private char suit;
    private String face;
    
    private ArrayList<String> validFaceList = new ArrayList<>(Arrays.asList("A","2","3","4","5","6","7","8","9","10","J","Q","K"));
    


    public Card(char suit, String face){
        if (isValidFace(face) && isValidSuit(suit)){
            this.face=face;
            this.suit=suit;
            this.value= setValue(face);
        }
        else {throw new IllegalArgumentException();}
    }



    public boolean isValidSuit(char suit) {
        if (suit=='A' || suit=='H' || suit=='D' || suit=='C'){return true;}
        else {return false;}
    }

    public boolean isValidFace(String face) {
        return (validFaceList.contains(face));
    }


    //Alle bildekort får verdi 10       
    //Setter verdi av ess til 1, men vi kan implementere endring i objektet for hånda
    public int setValue(String face) {
        if (validFaceList.indexOf(face) > 9){
            value=10;
        }
        else {value=validFaceList.indexOf(face)+1;
        }
        return value;
    }



    public int getValue() {
        return value;
    }

    public char getSuit() {
        return suit;
    }

    public String getFace() {
        return face;
    }



    @Override
    public String toString() {
        return "Card [face=" + face + ", suit=" + suit + ", value=" + value + "]";
    }


    public static void main(String[] args) {
        Card KingOfClubs = new Card('C',"K");
        System.out.println(KingOfClubs);
    }
    


}