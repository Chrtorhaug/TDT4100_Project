package Blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class CredentialsCheck {


    public ArrayList<String> getUserNamesFromFile(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(filename));
        ArrayList<String> UserNameList = new ArrayList<>();
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] lineInfo = line.split(",");
            String UserName = lineInfo[0];

            UserNameList.add(UserName);
        }
        return UserNameList;
        
    }


    private boolean validatePassword(String UserName,String Password) {
        if (Password.length()<10){
            return false;
        }
        if (UserName.equals(Password)){
            return false;
        }
        ArrayList<Character> array = new ArrayList<>();
        for (Character c : Password.toCharArray()) {
            array.add(c);
        }
        if (!array.stream().anyMatch(Character -> java.lang.Character.isDigit(Character))){
            return false;
        }
        else { return true;}
        
        
    }




    public boolean validateUserAtRegister(String UserName, String Password) {
        ArrayList<String> UserNameList = getUserNamesFromFile(filename); //igjen, vi må lage en fil
                                                                        //legge inn james bond og daniel negreanu
        return (!UserNameList.stream().anyMatch(String -> String.equals(UserName)) && validatePassword(UserName, Password));       
    }

    //Metode for å sjekke passord opp mot inntastet brukernavn

    public boolean validateUserAtLogin(String UserName, String Password) {
        ArrayList<String> UserNameList = getUserNamesFromFile(filename); //igjen, vi må lage en fil
        return (UserNameList.stream().anyMatch(String -> String.equals(UserName)) && );
    }
    
}
