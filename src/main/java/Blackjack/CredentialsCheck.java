package Blackjack;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class CredentialsCheck {

    private Map<String,String> getUserNamesFromFile() throws FileNotFoundException {
        
        Scanner scanner = new Scanner(getClass().getResource("DataBlackjack.txt").getPath());
        Map<String, String> UserNameMap = new HashMap<>();
        //Files.lines(Paths.get(getClass().getResource("DataBlackJack.txt").toURI())).toList();
        while (scanner.hasNextLine()){
            String line = scanner.nextLine();
            String[] lineInfo = line.split(",");
            String UserName = lineInfo[0];
            String PassWord = lineInfo[1];

            UserNameMap.put(UserName,PassWord);
        }
        scanner.close();
        return UserNameMap;   
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
        Map<String,String> UserNameMap = getUserNamesFromFile(); //igjen, vi må lage en fil
                                                                        //legge inn james bond og daniel negreanu
        return (!UserNameMap.containsKey(UserName) && validatePassword(UserName, Password));       
    }

    //Metode for å sjekke passord opp mot inntastet brukernavn

    public boolean validateUserAtLogin(String UserName, String Password) {
        Map<String,String> UserNameMap = getUserNamesFromFile(); //igjen, vi må lage en fil
        return (UserNameMap.containsKey(UserName) && UserNameMap.get(UserName).equals(Password));
    }
    
}
