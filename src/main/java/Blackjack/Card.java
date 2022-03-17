package Blackjack;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javafx.scene.image.Image;
public class Card {

    private int value;
    private Character suit;
    private String face;
    private Image cardImage;
    private final ArrayList<String> validFaceList = new ArrayList<>(Arrays.asList("A","2","3","4","5","6","7","8","9","10","J","Q","K"));
    private final ArrayList<Character> validSuitList = new ArrayList<>(Arrays.asList('S', 'H', 'C', 'D'));

    public Card(Character suit, String face){
        if (isValidFace(face) && isValidSuit(suit)) {
            this.face = face;
            this.suit = suit;
            this.value = setValue(face);

            File file = new File("src/main/resources/Carddeck/" + getSuit() + getFace() +".png");
            this.cardImage = new Image(file.toURI().toString());
        }
        else throw new IllegalArgumentException();
    }

    private boolean isValidSuit(Character suit) {
        return validSuitList.contains(suit);
    }

    private boolean isValidFace(String face) {
        return validFaceList.contains(face);
    }

    //Alle bildekort får verdi 10       
    //Setter verdi av ess til 1, men vi kan implementere endring i objektet for hånda
    public int setValue(String face) {
        if (validFaceList.indexOf(face) > 9) {
            return 10;
        }
        else if (validFaceList.indexOf(face) == 0) {
            return 11;
        }
        else return validFaceList.indexOf(face) + 1;
    }

    public void setAceToOne() {
        this.value = 1;
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

    public Image getCardPicture() {
        return cardImage;
    }
    public static void main(String[] args) {
        Card KingOfClubs = new Card('C',"2");
        System.out.println(KingOfClubs);
    }
}